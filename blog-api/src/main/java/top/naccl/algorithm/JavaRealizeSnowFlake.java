package top.naccl.algorithm;

/**
 * snowflake算法由twitter公司出品，原始版本是scala版，用于生成分布式ID，结构图：
 * -------------------------------------------------------------------------------------
 * snowflake-64bit
 *                            41bit-时间戳                                 12bit-序列号
 *          _______________________^_____________________                 _____^_____
 *         /                                             \               /           \
 *     0 - 00000000 000000000 00000000 00000000 00000000 0-00000000 00 - 00000000 0000
 *     ^                                                   \____ ____/
 *     |                                                        V
 * 1bit-不用                                                10bit-工作机器id
 * -------------------------------------------------------------------------------------
 * 算法描述：
 * 最高位是符号位，始终为0，不可用。
 * 41位的时间序列，精确到毫秒级，41位的长度可以使用69年。时间位还有一个很重要的作用是可以根据时间进行排序。
 * 10位的机器标识，10位的长度最多支持部署1024个节点
 * 12位的计数序列号，序列号即一系列的自增id，可以支持同一节点同一毫秒生成多个ID序号，12位的计数序列号支持每个节点每毫秒产生4096个ID序号
 * 位运算细节
 * |------------|---------------|---------------------------------------------------------------|
 * |	<<		|	左移			|	空位补0，被移除的高位丢弃。										|
 * |	>>		|	右移			|	被移位的二进制最高位是0，右移后，空缺位补0；最高位是1，空缺位补1。	|
 * |	>>>		|	无符号右移	|	被移位二进制最高位无论是0或者是1，空缺位都用0补。					|
 * |	&		|	与			|	任何二进制位和0进行&运算，结果是0;和1进行&运算结果是原值。			|
 * |	|		|	或 			|	任何二进制位和0进行|运算，结果是原值;和1进行|运算结果是1。			|
 * |	^		|	异或			|	任何相同二进制位进行^运算，结果是0;不相同二进制位^运算结果是1。		|
 * |    ~       |   取反			|	0变1，1变0													|
 * |------------|---------------|---------------------------------------------------------------|
 */
public class JavaRealizeSnowFlake {

    // 起始时间戳(2021-04-18)
    private final static long originalTimestamp = 1618675200000L;
    // 每一部分占用的位数
    private final static long dataCenterIdBits = 5L;
    private final static long workerIdBits = 5L;
    private final static long sequenceBits = 12L;

    // 每一部分的最大值
    // 结果是31
    private final static long maxDataCenterId = ~(-1L << dataCenterIdBits);
    // 结果是31
    private final static long maxWorkerId = ~(-1L << workerIdBits);
    // 这里为4095 (0b111111111111=0xfff=4095)
    private final static long maxSequence = ~(-1L << sequenceBits);

    // 每一部分向左的位移
    private final static long workerIdShift = sequenceBits;
    private final static long dataCenterIdShift = workerIdBits + sequenceBits;
    private final static long timestampShift = dataCenterIdBits + workerIdBits + sequenceBits;

    // 数据中心ID(0~31)
    private final long dataCenterId;
    // 机器ID(0~31)
    private final long workerId;
    // 毫秒内序列号(0~4095)
    private long sequence = 0L;
    // 上一次的时间戳
    private long lastTimestamp = -1L;

    /**
     * 构造方法
     * @param dataCenterId 数据中心ID(0~31)
     * @param workerId  工作ID(0~31)
     */
    public JavaRealizeSnowFlake(long dataCenterId,long workerId){
        if(dataCenterId > maxDataCenterId || dataCenterId < 0){
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        if(workerId > maxWorkerId || workerId < 0){
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    public synchronized long nextId(){
        long timestamp = timeGen();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，这个时候应当抛出异常
        if(timestamp < lastTimestamp){
            throw new RuntimeException(String.format(
                    "Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        if(timestamp == lastTimestamp){
            sequence = (sequence + 1) & maxSequence;
            // 毫秒内序列溢出
            if(sequence == 0L){
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tillNextMillis(lastTimestamp);
            }
        }else{
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }
        // 上一次生成ID的时间戳
        lastTimestamp = timestamp;
        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - originalTimestamp) << timestampShift)
                |(dataCenterId << dataCenterIdShift)
                |(workerId << workerIdShift)
                |sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tillNextMillis(long lastTimestamp){
        long timestamp = timeGen();
        while(timestamp <= lastTimestamp){
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间戳
     * @return long
     */
    private long timeGen(){
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {
        JavaRealizeSnowFlake idWorker = new JavaRealizeSnowFlake(0,0);
        for(int i = 0; i < 10; i++){
            long id = idWorker.nextId();
            Thread.sleep(1);
            System.out.println(id);
        }
    }

}
