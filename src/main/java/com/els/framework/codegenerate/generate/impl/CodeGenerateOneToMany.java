package com.els.framework.codegenerate.generate.impl;

import com.els.framework.codegenerate.config.ResourceConfig;
import com.els.framework.codegenerate.database.DbReadTableUtil;
import com.els.framework.codegenerate.generate.IGenerate;
import com.els.framework.codegenerate.generate.impl.framework.FrameworkFileUtil;
import com.els.framework.codegenerate.generate.pojo.ColumnVo;
import com.els.framework.codegenerate.generate.pojo.onetomany.MainTableVo;
import com.els.framework.codegenerate.generate.pojo.onetomany.SubTableVo;
import com.els.framework.codegenerate.generate.template.TemplateFileConfig;
import com.els.framework.codegenerate.generate.util.NonceUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CodeGenerateOneToMany extends FrameworkFileUtil implements IGenerate {
    private static final Logger logger = LoggerFactory.getLogger(CodeGenerateOneToMany.class);
    private MainTableVo mainTableVo;
    private List<ColumnVo> tableColumns;
    private List<ColumnVo> tableFields;
    private List<SubTableVo> subTableVoList;


    public CodeGenerateOneToMany(MainTableVo mainTableVo, List<SubTableVo> subTables) {

        this.subTableVoList = subTables;

        this.mainTableVo = mainTableVo;
    }

    public CodeGenerateOneToMany(MainTableVo mainTableVo, List<ColumnVo> mainColums, List<ColumnVo> originalMainColumns,
        List<SubTableVo> subTables) {

        this.mainTableVo = mainTableVo;

        this.tableColumns = mainColums;

        this.tableFields = originalMainColumns;

        this.subTableVoList = subTables;
    }

    public void columns() {


        Map<String, String> map = new HashMap<>();

        map.put("ID", "VARCHAR2");
        map.put("TEMPLATE_NUMBER", "VARCHAR2");
        map.put("TEMPLATE_NAME", "VARCHAR2");
        map.put("TEMPLATE_VERSION", "VARCHAR2");
        map.put("TEMPLATE_ACCOUNT", "VARCHAR2");
        map.put("ELS_ACCOUNT", "VARCHAR2");
        map.put("BUS_ACCOUNT", "VARCHAR2");
        map.put("TO_ELS_ACCOUNT", "VARCHAR2");
        map.put("SUPPLIER_CODE", "VARCHAR2");
        map.put("SUPPLIER_NAME", "VARCHAR2");
        map.put("BUSINESS_TYPE", "VARCHAR2");
        map.put("AMOUNT_INCLUDE_TAX", "VARCHAR2");
        map.put("SELLER_NAME", "VARCHAR2");
        map.put("COMPLIANCE_STATUS", "VARCHAR2");
        map.put("AMOUNT_EXCLUDE_TAX", "VARCHAR2");
        map.put("SELLER_ADDRESS_AND_PHONE", "VARCHAR2");
        map.put("COLLECTED", "VARCHAR2");
        map.put("RECONCILE_STATUS", "VARCHAR2");
        map.put("BUYER_TAX_ID", "VARCHAR2");
        map.put("PARSE_STATUS", "VARCHAR2");
        map.put("TAX_STATUS", "VARCHAR2");
        map.put("DEVICE_NUM", "VARCHAR2");
        map.put("PAYEE", "VARCHAR2");
        map.put("VERIFY_STATUS", "VARCHAR2");
        map.put("PDF_PATH", "VARCHAR2");
        map.put("BUYER_ADDRESS_AND_PHONE", "VARCHAR2");
        map.put("SELLER_TAX_ID", "VARCHAR2");
        map.put("INVOICE_NUMBER", "VARCHAR2");
        map.put("INVOICE_TYPE", "VARCHAR2");
        map.put("SUBMITTED_AT", "VARCHAR2");
        map.put("XML_PATH", "VARCHAR2");
        map.put("SELLER_BANK_AND_ACCOUNT", "VARCHAR2");
        map.put("SERIAL_NUMBER", "VARCHAR2");
        map.put("DRAWER", "VARCHAR2");
        map.put("REVIEWER", "VARCHAR2");
        map.put("BUYER_NAME", "VARCHAR2");
        map.put("INVOICE_DATE", "VARCHAR2");
        map.put("INVOICE_CODE", "VARCHAR2");
        map.put("GROUP_NUMBER", "VARCHAR2");
        map.put("CHECK_CODE", "VARCHAR2");
        map.put("COLLECT_TYPE", "VARCHAR2");
        map.put("SUBMITTED", "VARCHAR2");
        map.put("BUYER_BANK_AND_CCOUNT", "VARCHAR2");
        map.put("OFD_PATH", "VARCHAR2");
        map.put("REMARKS", "VARCHAR2");
        map.put("AMOUNT_TAX", "VARCHAR2");
        map.put("EXTRA_INFO", "VARCHAR2");
        map.put("OCR_FILE_STREAM", "VARCHAR2");
        map.put("PDF_FILE_STREAM", "VARCHAR2");
        map.put("OFD_FILE_STREAM", "VARCHAR2");
        map.put("QUANTITY", "VARCHAR2");
        map.put("PRICE_EXCLUDE_TAX", "VARCHAR2");
        map.put("SPECIFICATION", "VARCHAR2");
        map.put("TAX_CODE", "VARCHAR2");
        map.put("TAX_RATE", "VARCHAR2");
        map.put("ITEM_NAME", "VARCHAR2");
        map.put("UNIT", "VARCHAR2");
        map.put("LINE_ATTRIBUTE", "VARCHAR2");
        map.put("LINE_NUMBER", "VARCHAR2");
        map.put("CREATE_BY", "VARCHAR2");
        map.put("CREATE_BY_ID", "VARCHAR2");
        map.put("CREATE_TIME", "DATE");
        map.put("CURRENT_BY_ID", "VARCHAR2");
        map.put("UPDATE_BY", "VARCHAR2");
        map.put("UPDATE_BY_ID", "VARCHAR2");
        map.put("UPDATE_TIME", "DATE");
        map.put("FBK1", "VARCHAR2");
        map.put("FBK2", "VARCHAR2");
        map.put("FBK3", "VARCHAR2");
        map.put("FBK4", "VARCHAR2");
        map.put("FBK5", "VARCHAR2");
        map.put("FBK6", "VARCHAR2");
        map.put("FBK7", "VARCHAR2");
        map.put("FBK8", "VARCHAR2");
        map.put("FBK9", "VARCHAR2");
        map.put("FBK10", "VARCHAR2");

        List<ColumnVo> columnVos = new ArrayList<>();
        map.forEach((k,v)->{
            ColumnVo columnVo = new ColumnVo();

            columnVo.setFieldName(k);
            columnVo.setFieldType(v);

            columnVos.add(columnVo);
        });

        this.tableColumns = columnVos;
    }
    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
    public Map<String, Object> configMap() throws Exception {

        HashMap localHashMap = new HashMap();

        localHashMap.put("bussiPackage", ResourceConfig.basePackage);

        localHashMap.put("entityPackage", this.mainTableVo.getEntityPackage());

        localHashMap.put("entityName", this.mainTableVo.getEntityName());

        localHashMap.put("tableName", this.mainTableVo.getTableName());

        localHashMap.put("ftl_description", this.mainTableVo.getFtlDescription());

        localHashMap.put("primaryKeyField", ResourceConfig.dbTableId);

        localHashMap.put("left","${");

        localHashMap.put("right","}");

        if (this.mainTableVo.getFieldRequiredNum() == null) {

            this.mainTableVo.setFieldRequiredNum(4);
        }

        if (this.mainTableVo.getSearchFieldNum() == null) {

            this.mainTableVo.setSearchFieldNum(Integer.valueOf(StringUtils.isNotEmpty(ResourceConfig.pageSearchFieldNum)
                ? Integer.parseInt(ResourceConfig.pageSearchFieldNum) : -1));
        }

        if (this.mainTableVo.getFieldRowNum() == null) {

            this.mainTableVo.setFieldRowNum(1);
        }

        localHashMap.put("tableVo", this.mainTableVo);
        try {

            if ((this.tableColumns == null) || (this.tableColumns.size() == 0)) {

                this.tableColumns = DbReadTableUtil.queryTableColumns(this.mainTableVo.getTableName());
            }

            if ((this.tableFields == null) || (this.tableFields.size() == 0)) {

                this.tableFields = DbReadTableUtil.getTableFields(this.mainTableVo.getTableName());
            }

            localHashMap.put("columns", this.tableColumns);

            localHashMap.put("originalColumns", this.tableFields);

            for (Iterator localIterator = this.tableFields.iterator(); localIterator.hasNext();) {
                ColumnVo localObject1 = (ColumnVo)localIterator.next();

                if (((ColumnVo)localObject1).getFieldName().toLowerCase()
                    .equals(ResourceConfig.dbTableId.toLowerCase())) {
                    localHashMap.put("primaryKeyPolicy", ((ColumnVo)localObject1).getFieldType());
                }
            }

            Object localObject1;
            Iterator localIterator = this.subTableVoList.iterator();
            while ( localIterator.hasNext()) {
                localObject1 = (SubTableVo)localIterator.next();

                if ((((SubTableVo)localObject1).getColums() == null)
                    || (((SubTableVo)localObject1).getColums().size() == 0)) {

                    List<ColumnVo> localObject2 = DbReadTableUtil.queryTableColumns(((SubTableVo)localObject1).getTableName());

                    ((SubTableVo)localObject1).setColums((List)localObject2);
                }

                if ((((SubTableVo)localObject1).getOriginalColumns() == null)
                    || (((SubTableVo)localObject1).getOriginalColumns().size() == 0)) {

                    List<ColumnVo> localObject2 = DbReadTableUtil.getTableFields(((SubTableVo)localObject1).getTableName());

                    ((SubTableVo)localObject1).setOriginalColumns((List)localObject2);
                }

                String[] localObject2 = ((SubTableVo)localObject1).getForeignKeys();

                ArrayList localArrayList = new ArrayList();

                for (String str : localObject2) {

                    localArrayList.add(DbReadTableUtil.tableColumnName2CodeField(str));
                }

                ((SubTableVo)localObject1).setForeignKeys((String[])localArrayList.toArray(new String[0]));

                ((SubTableVo)localObject1).setOriginalForeignKeys((String[])localObject2);
            }

            localHashMap.put("subTables", this.subTableVoList);
        } catch (Exception localException) {

            throw localException;
        }

        long l = NonceUtils.randomNextLong() + NonceUtils.currentTime();

        localHashMap.put("serialVersionUID", String.valueOf(l));

        logger.info("code template data: " + localHashMap.toString());

        return localHashMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void generateCodeFile(boolean custom) throws Exception {

        logger.info("----els---Code----Generation----[一对多模型:" + this.mainTableVo.getTableName() + "]------- 生成中。。。");

        String str1 = ResourceConfig.projectPath;
        columns();
        Map localMap = configMap();

        String str2 = ResourceConfig.templatePath;

        if (getPathSuffix(str2, "/").equals("els/code-template")) {

            str2 = "/" + getPathSuffix(str2, "/") + "/onetomany";
        }

        TemplateFileConfig locala = new TemplateFileConfig(str2);

        generateFile(locala, str1, localMap);

        logger.info("----els----Code----Generation-----[一对多模型：" + this.mainTableVo.getTableName() + "]------ 生成完成。。。");
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
}
