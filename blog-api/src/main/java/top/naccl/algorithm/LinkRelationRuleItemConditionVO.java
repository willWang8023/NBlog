package top.naccl.algorithm;

import java.util.List;

public class LinkRelationRuleItemConditionVO {

    private Long id;
    private Long parentId;
    private Long ruleItemId;
    private Long ruleSceneId;
    private String domainModelCode;
    private String conditionCode;
    private String operator;
    private Integer valueType;
    private String value;
    private List<String> values;
    private String linkType;
    private Integer type;
    private Integer subType;
    private List<LinkRelationRuleItemConditionVO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getRuleItemId() {
        return ruleItemId;
    }

    public void setRuleItemId(Long ruleItemId) {
        this.ruleItemId = ruleItemId;
    }

    public Long getRuleSceneId() {
        return ruleSceneId;
    }

    public void setRuleSceneId(Long ruleSceneId) {
        this.ruleSceneId = ruleSceneId;
    }

    public String getDomainModelCode() {
        return domainModelCode;
    }

    public void setDomainModelCode(String domainModelCode) {
        this.domainModelCode = domainModelCode;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public List<LinkRelationRuleItemConditionVO> getChildren() {
        return children;
    }

    public void setChildren(List<LinkRelationRuleItemConditionVO> children) {
        this.children = children;
    }
}
