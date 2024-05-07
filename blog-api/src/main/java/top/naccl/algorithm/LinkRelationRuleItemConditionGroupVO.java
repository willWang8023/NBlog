package top.naccl.algorithm;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class LinkRelationRuleItemConditionGroupVO {

    private String linkType;

    private List<LinkRelationRuleItemConditionVO> ruleItemConditionVOList;
    private List<LinkRelationRuleItemConditionGroupVO> groupChildrenList;

    public List<LinkRelationRuleItemConditionGroupVO> getGroupChildrenList() {
        return CollectionUtils.isEmpty(groupChildrenList) ? new ArrayList<>() : groupChildrenList;
    }


    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public List<LinkRelationRuleItemConditionVO> getRuleItemConditionVOList() {
        return CollectionUtils.isEmpty(ruleItemConditionVOList) ? new ArrayList<>() : ruleItemConditionVOList;
    }

    public void setRuleItemConditionVOList(List<LinkRelationRuleItemConditionVO> ruleItemConditionVOList) {
        this.ruleItemConditionVOList = ruleItemConditionVOList;
    }

    public void setGroupChildrenList(List<LinkRelationRuleItemConditionGroupVO> groupChildrenList) {
        this.groupChildrenList = groupChildrenList;
    }
}
