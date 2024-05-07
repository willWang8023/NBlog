package top.naccl.algorithm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToTree {

    public static void main(String[] args) {
        List<LinkRelationRuleItemConditionVO> ruleItemCondList = new ArrayList<>();
//        4 and 5 and 6 or 7 or 8 and 9
        LinkRelationRuleItemConditionVO ruleItemCond4 = new LinkRelationRuleItemConditionVO();
        ruleItemCond4.setConditionCode("4");
        ruleItemCond4.setLinkType("and");
        ruleItemCondList.add(ruleItemCond4);
        LinkRelationRuleItemConditionVO ruleItemCond5 = new LinkRelationRuleItemConditionVO();
        ruleItemCond5.setConditionCode("5");
        ruleItemCond5.setLinkType("and");
        ruleItemCondList.add(ruleItemCond5);
        LinkRelationRuleItemConditionVO ruleItemCond6 = new LinkRelationRuleItemConditionVO();
        ruleItemCond6.setConditionCode("6");
        ruleItemCond6.setLinkType("or");
        ruleItemCondList.add(ruleItemCond6);
        LinkRelationRuleItemConditionVO ruleItemCond7 = new LinkRelationRuleItemConditionVO();
        ruleItemCond7.setConditionCode("7");
        ruleItemCond7.setLinkType("or");
        ruleItemCondList.add(ruleItemCond7);
        LinkRelationRuleItemConditionVO ruleItemCond8 = new LinkRelationRuleItemConditionVO();
        ruleItemCond8.setConditionCode("8");
        ruleItemCond8.setLinkType("and");
        ruleItemCondList.add(ruleItemCond8);
        LinkRelationRuleItemConditionVO ruleItemCond9 = new LinkRelationRuleItemConditionVO();
        ruleItemCond9.setConditionCode("9");
        ruleItemCond9.setLinkType("and");
        ruleItemCondList.add(ruleItemCond9);

//        4 and 5 and (6 or 7) or 8 and 9
//        LinkRelationRuleItemConditionVO ruleItemCond4 = new LinkRelationRuleItemConditionVO();
//        ruleItemCond4.setConditionCode("4");
//        ruleItemCond4.setLinkType("and");
//        ruleItemCondList.add(ruleItemCond4);
//        LinkRelationRuleItemConditionVO ruleItemCond5 = new LinkRelationRuleItemConditionVO();
//        ruleItemCond5.setConditionCode("5");
//        ruleItemCond5.setLinkType("and");
//        ruleItemCondList.add(ruleItemCond5);
//        List<LinkRelationRuleItemConditionVO> childrenList = ruleItemCond5.getChildren();
//        if(CollectionUtils.isEmpty(childrenList)) {
//            childrenList = new ArrayList<>();
//        }
//        LinkRelationRuleItemConditionVO ruleItemCond6 = new LinkRelationRuleItemConditionVO();
//        ruleItemCond6.setConditionCode("6");
//        ruleItemCond6.setLinkType("or");
//        childrenList.add(ruleItemCond6);
//        LinkRelationRuleItemConditionVO ruleItemCond7 = new LinkRelationRuleItemConditionVO();
//        ruleItemCond7.setConditionCode("7");
//        ruleItemCond7.setLinkType("or");
//        childrenList.add(ruleItemCond7);
//
//        ruleItemCond5.setChildren(childrenList);
//        LinkRelationRuleItemConditionVO ruleItemCond8 = new LinkRelationRuleItemConditionVO();
//        ruleItemCond8.setConditionCode("8");
//        ruleItemCond8.setLinkType("and");
//        ruleItemCondList.add(ruleItemCond8);
//        LinkRelationRuleItemConditionVO ruleItemCond9 = new LinkRelationRuleItemConditionVO();
//        ruleItemCond9.setConditionCode("9");
//        ruleItemCond9.setLinkType("and");
//        ruleItemCondList.add(ruleItemCond9);

        LinkRelationRuleItemConditionGroupVO groupVO = new LinkRelationRuleItemConditionGroupVO();
        toAdvTree(groupVO, ruleItemCondList);
        System.out.println("计算结果：" + JSON.toJSONString(groupVO, SerializerFeature.PrettyFormat));
    }



    private static String toAdvTree(LinkRelationRuleItemConditionGroupVO groupVO,
                                    List<LinkRelationRuleItemConditionVO> ruleItemCondList) {
        String preLinkType = null;
        String linkType = null;
        // 获取当前组的条件List
        List<LinkRelationRuleItemConditionVO> itemConditionVOList = groupVO.getRuleItemConditionVOList();

        for (int i = 0; i < ruleItemCondList.size(); i++) {
            LinkRelationRuleItemConditionVO ruleItemConditionVO = ruleItemCondList.get(i);
            // 获取当前连接
            linkType = ruleItemConditionVO.getLinkType();
            // 如果前一个条件是and，将当前条件添加到当前组的条件集合中
            if (StringUtils.isEmpty(preLinkType) || "and".equals(preLinkType)) {
                itemConditionVOList.add(ruleItemConditionVO);
                groupVO.setRuleItemConditionVOList(itemConditionVOList);
            }
            // 如果之前的关联是and，当前条件是or,将之前的条件组降级为children
            if ("and".equals(preLinkType) && "or".equals(linkType)) {
                moveToChildren(groupVO);
                // 清空原来的条件list
                itemConditionVOList = new ArrayList<>();
            }
            // 如果之前条件是or，当前条件也是or,将当前条件添加到当前group的条件集合中
            if ("or".equals(preLinkType) && "or".equals(linkType)) {
                // 将条件添加到条件list中
                itemConditionVOList.add(ruleItemConditionVO);
                groupVO.setRuleItemConditionVOList(itemConditionVOList);
            }

            if ("or".equals(preLinkType) && "and".equals(linkType)) {
                moveToChildren(groupVO);
                // 清空原来的条件list
                itemConditionVOList = new ArrayList<>();
                itemConditionVOList.add(ruleItemConditionVO);
            }
            // 如果是最后一个，并且子组不为空，将当前条件转化为组中
            if (i + 1 == ruleItemCondList.size() && !CollectionUtils.isEmpty(groupVO.getGroupChildrenList())) {
                condMoveToChildren(preLinkType, groupVO);
                System.out.println("计算结果：" + JSON.toJSONString(groupVO, SerializerFeature.PrettyFormat));
                return linkType;
            }
            groupVO.setLinkType(preLinkType);
            // 用作下一条数据的上一个连接
            preLinkType = linkType;

            // 如果存在子级，创建一个子级group传入下级处理，将返回的结果，放入当前group的子级中
            if (!CollectionUtils.isEmpty(ruleItemConditionVO.getChildren())) {
                LinkRelationRuleItemConditionGroupVO childrenGroupVO = new LinkRelationRuleItemConditionGroupVO();
                preLinkType = toAdvTree(childrenGroupVO, ruleItemConditionVO.getChildren());
                List<LinkRelationRuleItemConditionGroupVO> groupChildrenList = groupVO.getGroupChildrenList();
                groupChildrenList.add(childrenGroupVO);
                groupVO.setGroupChildrenList(groupChildrenList);
                // 清除子级数据
                ruleItemConditionVO.setChildren(null);
            }
        }

        return linkType;
    }


    private static void moveToChildren(LinkRelationRuleItemConditionGroupVO groupVO) {
        LinkRelationRuleItemConditionGroupVO childrenGroupVO = new LinkRelationRuleItemConditionGroupVO();
        // 获取上一个组的关联关系
        String groupLinkType = getInnerLastChildrenGroupLinkType(groupVO.getGroupChildrenList());

        childrenGroupVO.setLinkType(groupVO.getLinkType());
        childrenGroupVO.setRuleItemConditionVOList(groupVO.getRuleItemConditionVOList());
        childrenGroupVO.setGroupChildrenList(groupVO.getGroupChildrenList());

        // 将childrenGroupVO作为groupVO的子级
        groupVO.setGroupChildrenList(new ArrayList<>(Collections.singletonList(childrenGroupVO)));
        // 清除原来的条件
        groupVO.setRuleItemConditionVOList(null);

        // 设置和上一个组的关联关系
        groupVO.setLinkType(groupLinkType);
    }

    private static void condMoveToChildren(String preLinkType, LinkRelationRuleItemConditionGroupVO groupVO) {
        LinkRelationRuleItemConditionGroupVO childrenGroupVO = new LinkRelationRuleItemConditionGroupVO();
        // 获取上一个组的关联关系
        String groupLinkType = getInnerLastChildrenGroupLinkType(groupVO.getGroupChildrenList());

        childrenGroupVO.setLinkType(preLinkType);
        childrenGroupVO.setRuleItemConditionVOList(groupVO.getRuleItemConditionVOList());

        // 将childrenGroupVO添加到groupVO的子级
        List<LinkRelationRuleItemConditionGroupVO> groupChildrenList = groupVO.getGroupChildrenList();
        groupChildrenList.add(childrenGroupVO);
        groupVO.setGroupChildrenList(groupChildrenList);
        // 清除原来的条件
        groupVO.setRuleItemConditionVOList(null);

        // 设置和上一个组的关联关系
        groupVO.setLinkType(groupLinkType);
    }

    private static String getInnerLastChildrenGroupLinkType(List<LinkRelationRuleItemConditionGroupVO> groupVOList) {
        String groupLinkType = null;
        if(CollectionUtils.isEmpty(groupVOList)){
            return groupLinkType;
        }
        LinkRelationRuleItemConditionGroupVO ruleItemConditionGroupVO = groupVOList.stream().reduce((first, second) -> second).orElse(null);
        if (ObjectUtils.isEmpty(ruleItemConditionGroupVO)) {
            return groupLinkType;
        }

        if (ObjectUtils.isNotEmpty(ruleItemConditionGroupVO)) {
            LinkRelationRuleItemConditionVO lastItemCond = ruleItemConditionGroupVO.getRuleItemConditionVOList().stream().reduce((first, second) -> second).orElse(null);
            if (ObjectUtils.isNotEmpty(lastItemCond)) {
                groupLinkType = lastItemCond.getLinkType();
            }
        }
        List<LinkRelationRuleItemConditionGroupVO> childrenGroupList = ruleItemConditionGroupVO.getGroupChildrenList();
        if (!CollectionUtils.isEmpty(childrenGroupList)) {
            groupLinkType = getInnerLastChildrenGroupLinkType(childrenGroupList);
        }
        return groupLinkType;
    }

}


