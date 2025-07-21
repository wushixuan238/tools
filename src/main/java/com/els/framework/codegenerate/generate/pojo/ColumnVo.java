package com.els.framework.codegenerate.generate.pojo;

public class ColumnVo extends CgFormColumnExtendVo {
    public static final String OPTION_REQUIRED = "required:true";
    public static final String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
    public static final String TYPE_INT = "INT";
    public static final String TYPE_DATETIME = "DATETIME";
    public static final String TYPE_JSON = "JSON";
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_LONGTEXT = "LONGTEXT";
    private String fieldDbName;
    private String fieldName;
    private String filedComment = "";

    private String fieldType = "";

    private String fieldDbType = "";

    private String charmaxLength = "";
    private String precision;
    private String scale;
    private String nullable;
    private String classType = "";

    private String classTypeRow = "";

    private String optionType = "";

    public String getFieldDbType() {

        return this.fieldDbType;
    }
    
    public String getFieldType2() {
        return this.fieldType.substring(this.fieldType.lastIndexOf(".") + 1);
    }

    public void setFieldDbType(String fieldDbType) {
        if (TYPE_INT.contentEquals(fieldDbType)) {
    		this.fieldDbType = "INTEGER";
        } else if (TYPE_DATETIME.contentEquals(fieldDbType)) {
    		this.fieldDbType = "TIMESTAMP";
    	} else if (TYPE_JSON.contentEquals(fieldDbType) || TYPE_TEXT.contentEquals(fieldDbType) || TYPE_LONGTEXT.contentEquals(fieldDbType)){
            this.fieldDbType = "VARCHAR";
        } else {
    		this.fieldDbType = fieldDbType;
    	}
    }

    public String getNullable() {

        return this.nullable;
    }

    public void setNullable(String nullable) {

        this.nullable = nullable;
    }

    public String getPrecision() {

        return this.precision;
    }

    public String getScale() {

        return this.scale;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getOptionType() {
        return this.optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getClassType() {
        return this.classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFiledComment() {
        return this.filedComment;
    }

    public void setFiledComment(String filedComment) {
        this.filedComment = filedComment;
    }

    public String getClassTypeRow() {
        if ((this.classType != null) && (this.classType.indexOf("easyui-") >= 0)) {
            return this.classType.replaceAll("easyui-", "");
        }
        return this.classTypeRow;
    }

    public void setClassTypeRow(String classTypeRow) {
        this.classTypeRow = classTypeRow;
    }

    public String getCharmaxLength() {
        if ((this.charmaxLength == null) || ("0".equals(this.charmaxLength))) {
            return "";
        }
        return this.charmaxLength;
    }

    public void setCharmaxLength(String charmaxLength) {
        this.charmaxLength = charmaxLength;
    }

    public String getFieldDbName() {
        return this.fieldDbName;
    }

    public void setFieldDbName(String fieldDbName) {
        this.fieldDbName = fieldDbName;
    }

    @Override
    public String toString() {
        return "{\"fieldDbName\":\"" + this.fieldDbName + "\",\"fieldName\":\"" + this.fieldName
            + "\",\"filedComment\":\"" + this.filedComment + "\",\"fieldType\":\"" + this.fieldType
            + "\",\"fieldDbType\":\"" + this.fieldDbType + "\",\"classType\":\"" + this.classType
            + "\",\"classTypeRow\":\"" + this.classTypeRow + "\",\"optionType\":\"" + this.optionType
            + "\",\"charmaxLength\":\"" + this.charmaxLength + "\",\"precision\":\"" + this.precision
            + "\",\"scale\":\"" + this.scale + "\",\"nullable\":\"" + this.nullable + "\",\"fieldLength\":\""
            + this.fieldLength + "\",\"fieldHref\":\"" + this.fieldHref + "\",\"fieldValidType\":\""
            + this.fieldValidType + "\",\"fieldDefault\":\"" + this.fieldDefault + "\",\"fieldShowType\":\""
            + this.fieldShowType + "\",\"fieldOrderNum\":\"" + this.fieldOrderNum + "\",\"isKey\":\"" + this.isKey
            + "\",\"isShow\":\"" + this.isShow + "\",\"isShowList\":\"" + this.isShowList + "\",\"isQuery\":\""
            + this.isQuery + "\",\"queryMode\":\"" + this.queryMode + "\",\"dictField\":\"" + this.dictField
            + "\",\"dictTable\":\"" + this.dictTable + "\",\"dictText\":\"" + this.dictText + "\"}";
    }
}
