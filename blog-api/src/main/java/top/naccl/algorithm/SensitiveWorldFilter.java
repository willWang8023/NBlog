package top.naccl.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
 * 中 = {
 *      isEnd = 0
 *      国 = {
 *          isEnd = 1
 *          人 = {isEnd = 0
 *              民 = {
 *                  isEnd = 1
 *              }
 *          }
 *          男  = {
 *              isEnd = 0
 *              人 = {
 *                  isEnd = 1
 *              }
 *          }
 *      }
 * }
 * 五 = {
 *  isEnd = 0
 *  星 = {
 *      isEnd = 0
 *      红 = {
 *          isEnd = 0
 *          旗 = {
 *              isEnd = 1
 *          }
 *      }
 *  }
 * }
 *
 * @author willwang
 * @version 1.0
 * @date 2024年4月8日 下午3:04:20
 */
public class SensitiveWorldFilter {

    public static void main(String[] args){
        Set<String> sensitiveWorldSet = new HashSet<>();
        sensitiveWorldSet.add("中国人民");
        sensitiveWorldSet.add("中国男人");
        sensitiveWorldSet.add("五星红旗");

        Map<String, Object> sensitiveWorldMapTree = addSensitiveWordToHashMap(sensitiveWorldSet);
        System.out.println("sensitiveWorld:"+ JSON.toJSONString(sensitiveWorldMapTree));
    }

    /**
     * @param keyWordSet 敏感词库
     */
    private static Map<String, Object> addSensitiveWordToHashMap(Set<String> keyWordSet) {
        Map<String, Object> sensitiveWordMap = new HashMap<>(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key;
        Map<String, Object> nowMap;
        Map<String, Object> newWordMap;
        //迭代keyWordSet
        for (String s : keyWordSet) {
            key = s;    //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(String.valueOf(keyChar));       //获取

                if (wordMap != null) {        //如果存在该key，直接赋值
                    nowMap = (Map<String, Object>)wordMap;
                } else {
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWordMap = new HashMap<>();
                    newWordMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(String.valueOf(keyChar), newWordMap);
                    nowMap = newWordMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
        return sensitiveWordMap;
    }

}
