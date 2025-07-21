package com.els.framework.codegenerate.generate.impl;

import java.util.*;

import com.els.tools.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.els.framework.codegenerate.config.ResourceConfig;
import com.els.framework.codegenerate.database.DbReadTableUtil;
import com.els.framework.codegenerate.generate.IGenerate;
import com.els.framework.codegenerate.generate.impl.framework.FrameworkFileUtil;
import com.els.framework.codegenerate.generate.pojo.ColumnVo;
import com.els.framework.codegenerate.generate.pojo.TableVo;
import com.els.framework.codegenerate.generate.template.TemplateFileConfig;
import com.els.framework.codegenerate.generate.util.NonceUtils;

public class CodeGenerateOne extends FrameworkFileUtil implements IGenerate {
    private static final Logger logger = LoggerFactory.getLogger(CodeGenerateOne.class);
    private TableVo tableVo;
    private List<ColumnVo> columns;
    private List<ColumnVo> originalColumns;

    public CodeGenerateOne(TableVo tableVo) {

        this.tableVo = tableVo;
    }

    public CodeGenerateOne(TableVo tableVo, List<ColumnVo> columns, List<ColumnVo> originalColumns) {

        this.tableVo = tableVo;

        this.columns = columns;

        this.originalColumns = originalColumns;
    }


    public void columns() {
        if (tableVo.getTableName().toLowerCase().endsWith("head")){
            this.columns = StringUtil.generateSql();
        }else {
            this.columns = StringUtil.generateSqlItem();
        }
        ColumnVo columnVo = new ColumnVo();
        columnVo.setFieldName(DbReadTableUtil.tableColumnName2CodeFieldName("ID".toLowerCase()));
        columnVo.setFieldType(DbReadTableUtil.getFiledCodeType("VARCHAR2".toLowerCase(), null, null));
        this.originalColumns = Collections.singletonList(columnVo);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Map<String, Object> configMap() throws Exception {

        HashMap localHashMap = new HashMap();

        localHashMap.put("bussiPackage", ResourceConfig.basePackage);

        localHashMap.put("entityPackage", this.tableVo.getEntityPackage());

        localHashMap.put("entityName", this.tableVo.getEntityName());

        localHashMap.put("tableName", this.tableVo.getTableName());

        localHashMap.put("primaryKeyField", ResourceConfig.dbTableId);

        if (this.tableVo.getFieldRequiredNum() == null) {

            this.tableVo.setFieldRequiredNum(Integer.valueOf(StringUtils.isNotEmpty(ResourceConfig.FieldRequiredNum)
                    ? Integer.parseInt(ResourceConfig.FieldRequiredNum) : -1));
        }

        if (this.tableVo.getSearchFieldNum() == null) {

            this.tableVo.setSearchFieldNum(Integer.valueOf(StringUtils.isNotEmpty(ResourceConfig.pageSearchFieldNum)
                    ? Integer.parseInt(ResourceConfig.pageSearchFieldNum) : -1));
        }

        if (this.tableVo.getFieldRowNum() == null) {

            this.tableVo.setFieldRowNum(Integer.valueOf(Integer.parseInt(ResourceConfig.FieldRowNum)));
        }

        localHashMap.put("tableVo", this.tableVo);
        try {

            if ((this.columns == null) || (this.columns.size() == 0)) {

                this.columns = DbReadTableUtil.queryTableColumns(this.tableVo.getTableName());
            }

            localHashMap.put("columns", this.columns);

            if ((this.originalColumns == null) || (this.originalColumns.size() == 0)) {

                this.originalColumns = DbReadTableUtil.getTableFields(this.tableVo.getTableName());
            }

            localHashMap.put("originalColumns", this.originalColumns);

            for (ColumnVo localColumnVo : this.originalColumns) {

                if (localColumnVo.getFieldName().toLowerCase()
                        .equals(ResourceConfig.dbTableId.toLowerCase())) {

                    localHashMap.put("primaryKeyPolicy", localColumnVo.getFieldType());
                }
            }
        } catch (Exception localException) {

            throw localException;
        }

        long l = NonceUtils.randomNextLong() + NonceUtils.currentTime();

        localHashMap.put("serialVersionUID", String.valueOf(l));

        logger.info("code template data: " + localHashMap.toString());

        return localHashMap;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void generateCodeFile(boolean custom) throws Exception {

        logger.info("----els---Code----Generation----[单表模型:" + this.tableVo.getTableName() + "]------- 生成中。。。");

        String str1 = ResourceConfig.projectPath;
        if (custom){
            columns();
        }
        Map localMap = configMap();

        String str2 = ResourceConfig.templatePath;

        if (getPathSuffix(str2, "/").equals("els/code-template")) {

            str2 = "/" + getPathSuffix(str2, "/") + "/one";
        }

        TemplateFileConfig locala = new TemplateFileConfig(str2);

        generateFile(locala, str1, localMap);

        logger.info("----els----Code----Generation-----[单表模型：" + this.tableVo.getTableName() + "]------ 生成完成。。。");
    }

    @Override
    public void generateCodeFile(String projectPath, String templatePath) throws Exception {

        if ((projectPath != null) && (!"".equals(projectPath))) {

            ResourceConfig.a(projectPath);
        }

        if ((templatePath != null) && (!"".equals(templatePath))) {

            ResourceConfig.b(templatePath);
        }

        generateCodeFile(false);
    }

    public static void main(String[] args) {

        System.out.println("----els--------- Code------------- Generation -----[单表模型]------- 生成中。。。");

        TableVo localTableVo = new TableVo();

        localTableVo.setTableName("demo");

        localTableVo.setPrimaryKeyPolicy("uuid");

        localTableVo.setEntityPackage("test");

        localTableVo.setEntityName("ELSDemo");

        localTableVo.setFtlDescription("els 测试demo");
        try {

            new CodeGenerateOne(localTableVo).generateCodeFile(false);
        } catch (Exception localException) {

            localException.printStackTrace();
        }

        System.out.println("----els--------- Code------------- Generation -----[单表模型]------- 生成完成。。。");
    }
}