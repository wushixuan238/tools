package com.els.tools;

import lombok.Data;

/**
 * 供应商主数据信息变更记录
 */
@Data
public class FieldRecordVo {

    /** 分组 */
    private String group;

    /** 行id */
    private String itemId;

    /** 修改类型（1:update、2:add、3:delete） */
    private String updateType;

    /** 变化的字段 */
    private String field;

    /** 变化的字段值 */
    private String fieldValue;

    /** 变化的字段原始值 */
    private String initialValue;

    /** 变化的字段中文名*/
    private String fieldName;

}