package com.els.tools;

import com.els.framework.codegenerate.database.DbReadTableUtil;
import com.els.framework.codegenerate.generate.pojo.ColumnVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串驼峰转下划线，下划线转驼峰
 */
public class StringUtil {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static Pattern pattern = Pattern.compile("^(\\d+.*|-\\d+.*)");

    public static String humpToLine(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        if (isAllUpperCase(str)) {
            str = str.toLowerCase();
        }

        if (str.contains("_")) {
            return str;
        }

        Matcher matcher = humpPattern.matcher(str);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public static String lineToHump(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        if (isAllUpperCase(str)) {
            str = str.toLowerCase();
        }
        if (!str.contains("_")) {
            return str;
        }
        str = str.toLowerCase();
        String[] split = str.split("_");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                buffer.append(split[i]);
            } else {
                String ss = split[i];
                if (startsWithNum(ss)) {
                    buffer.append(ss);
                } else {
                    char[] chars = ss.toCharArray();
                    chars[0] -= 32;
                    buffer.append(new String(chars));
                }

            }
        }
        return buffer.toString();
    }

    /**
     * 判断字符串是否以数字开头
     *
     * @param str
     * @return
     */
    public static boolean startsWithNum(String str) {
        boolean flag = false;
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            flag = true;
        }
        return flag;
    }

    public static boolean isAllUpperCase(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static List<ColumnVo> generateSql() {
        Map<String, String> map = new HashMap<>();

        map.put("TRAFFIC_FEE_FLAG", "VARCHAR2");
        map.put("TRANSFERRED_VEHICLE_OFFICE", "VARCHAR2");
        map.put("VEHICLE_IDENTIFICATION_NO", "VARCHAR2");
        map.put("VEHICLE_TYPE", "VARCHAR2");
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
        map.put("RELATION_ID", "VARCHAR2");
        map.put("IS_DELETED", "NUMBER");
        map.put("CHECK_NUMBER", "VARCHAR2");
        map.put("BAND_MODEL", "VARCHAR2");
        map.put("BLUE_INVOICE_CODE", "VARCHAR2");
        map.put("BLUE_INVOICE_NO", "VARCHAR2");
        map.put("BUSINESS_UNIT", "VARCHAR2");
        map.put("BUSINESS_UNIT_ADDRESS", "VARCHAR2");
        map.put("BUSINESS_UNIT_BANK_AND_ACCOUNT", "VARCHAR2");
        map.put("BUSINESS_UNIT_PHONE", "VARCHAR2");
        map.put("BUSINESS_UNIT_TAX_NO", "VARCHAR2");
        map.put("BUYER_ACCOUNT", "VARCHAR2");
        map.put("BUYER_ADDRESS_PHONE", "VARCHAR2");
        map.put("BUYER_NAME", "VARCHAR2");
        map.put("BUYER_PHONE", "VARCHAR2");
        map.put("BUYER_TAX_NO", "VARCHAR2");
        map.put("BUYER_UNIT_CODE_OR_ID_NO", "VARCHAR2");
        map.put("BUYER_UNIT_OR_INDIVIDUAL", "VARCHAR2");
        map.put("BUYER_UNIT_OR_INDIVIDUAL_ADDRESS", "VARCHAR2");
        map.put("CANCELLATION_MARK", "VARCHAR2");
        map.put("CAR_PRICE", "VARCHAR2");
        map.put("CERTIFICATE_OF_IMPORT", "VARCHAR2");
        map.put("CHECK_CODE", "VARCHAR2");
        map.put("COMMODITY_INSPECTION_NO", "VARCHAR2");
        map.put("DETAILED_LIST_TYPE", "VARCHAR2");
        map.put("DRAWER", "VARCHAR2");
        map.put("ENGINE_NO", "VARCHAR2");
        map.put("ID_NO", "VARCHAR2");
        map.put("INVOICE_AMOUNT", "VARCHAR2");
        map.put("INVOICE_CODE", "VARCHAR2");
        map.put("INVOICE_DATE", "VARCHAR2");
        map.put("INVOICE_NO", "VARCHAR2");
        map.put("E_INVOICE_NO", "VARCHAR2");
        map.put("INVOICE_TYPE", "VARCHAR2");
        map.put("LEMON_MARKET", "VARCHAR2");
        map.put("LEMON_MARKET_ADDRESS", "VARCHAR2");
        map.put("LEMON_MARKET_BANK_AND_ACCOUNT", "VARCHAR2");
        map.put("LEMON_MARKET_PHONE", "VARCHAR2");
        map.put("LEMON_MARKET_TAX_NO", "VARCHAR2");
        map.put("LICENSE_PLATE", "VARCHAR2");
        map.put("LIMITED_PEOPLE_COUNT", "VARCHAR2");
        map.put("MACHINE_NO", "VARCHAR2");
        map.put("OFD_URL", "VARCHAR2");
        map.put("PDF_URL", "VARCHAR2");
        map.put("PAYEE", "VARCHAR2");
        map.put("PRODUCE_AREA", "VARCHAR2");
        map.put("QUALIFIED_NO", "VARCHAR2");
        map.put("REGISTRATION_NO", "VARCHAR2");
        map.put("REMARK", "VARCHAR2");
        map.put("REVIEWER", "VARCHAR2");
        map.put("SALER_ACCOUNT", "VARCHAR2");
        map.put("SALER_ADDRESS", "VARCHAR2");
        map.put("SALER_ADDRESS_PHONE", "VARCHAR2");
        map.put("SALER_BANK_ACCOUNT", "VARCHAR2");
        map.put("SALER_BANK_NAME", "VARCHAR2");
        map.put("SALER_NAME", "VARCHAR2");
        map.put("SALER_PHONE", "VARCHAR2");
        map.put("SALER_TAX_NO", "VARCHAR2");
        map.put("SALER_UNIT_CODE_OR_ID_NO", "VARCHAR2");
        map.put("SALER_UNIT_OR_INDIVIDUAL", "VARCHAR2");
        map.put("SALER_UNIT_OR_INDIVIDUAL_ADDRESS", "VARCHAR2");
        map.put("TAX_AMOUNT", "VARCHAR2");
        map.put("TAX_AUTHORITY_CODE", "VARCHAR2");
        map.put("TAX_AUTHORITY_NAME", "VARCHAR2");
        map.put("TAX_PAYMENT_CERTIFICATE_NO", "VARCHAR2");
        map.put("TAX_RATE", "VARCHAR2");
        map.put("TONNAGE", "VARCHAR2");
        map.put("TOTAL_AMOUNT", "VARCHAR2");

        Map<String, String> map2 = new HashMap<>();

        map2.put("TRAFFIC_FEE_FLAG","通行费标志");
        map2.put("TRANSFERRED_VEHICLE_OFFICE","转入地车辆车管所名称");
        map2.put("VEHICLE_IDENTIFICATION_NO","车辆识别代号/车架号码");
        map2.put("VEHICLE_TYPE","车辆类型");
        map2.put("ID","ID");
        map2.put("TEMPLATE_NUMBER","模板编码");
        map2.put("TEMPLATE_NAME","模板名称");
        map2.put("TEMPLATE_VERSION","模板版本号");
        map2.put("TEMPLATE_ACCOUNT","模板账号");
        map2.put("ELS_ACCOUNT","els账号");
        map2.put("BUS_ACCOUNT","BUS_ACCOUNT");
        map2.put("TO_ELS_ACCOUNT","供应商els账号");
        map2.put("SUPPLIER_CODE","供应商编码");
        map2.put("SUPPLIER_NAME","供应商名称");
        map2.put("BUSINESS_TYPE","业务类型");
        map2.put("CREATE_BY","创建人");
        map2.put("CREATE_BY_ID","创建人ID");
        map2.put("CREATE_TIME","创建时间");
        map2.put("CURRENT_BY_ID","当前人ID");
        map2.put("UPDATE_BY","修改人");
        map2.put("UPDATE_BY_ID","修改人ID");
        map2.put("UPDATE_TIME","修改时间");
        map2.put("FBK1","备用字段");
        map2.put("FBK2","备用字段");
        map2.put("FBK3","备用字段");
        map2.put("FBK4","备用字段");
        map2.put("FBK5","备用字段");
        map2.put("FBK6","备用字段");
        map2.put("FBK7","备用字段");
        map2.put("FBK8","备用字段");
        map2.put("FBK9","备用字段");
        map2.put("FBK10","备用字段");
        map2.put("RELATION_ID","关联ID");
        map2.put("IS_DELETED","是否删除");
        map2.put("CHECK_NUMBER","查验次数");
        map2.put("BAND_MODEL","厂牌型号");
        map2.put("BLUE_INVOICE_CODE","蓝票发票代码");
        map2.put("BLUE_INVOICE_NO","蓝票发票号码");
        map2.put("BUSINESS_UNIT","经营、拍卖单位");
        map2.put("BUSINESS_UNIT_ADDRESS","经营、拍卖单位地址");
        map2.put("BUSINESS_UNIT_BANK_AND_ACCOUNT","开户银行及账号");
        map2.put("BUSINESS_UNIT_PHONE","经营、拍卖单位电话");
        map2.put("BUSINESS_UNIT_TAX_NO","经营、拍卖单位纳税");
        map2.put("BUYER_ACCOUNT","购方开户行及账号");
        map2.put("BUYER_ADDRESS_PHONE","购方地址、电话");
        map2.put("BUYER_NAME","购方名称");
        map2.put("BUYER_PHONE","买方电话");
        map2.put("BUYER_TAX_NO","购方税号");
        map2.put("BUYER_UNIT_CODE_OR_ID_NO","买方单位代码/身份证号");
        map2.put("BUYER_UNIT_OR_INDIVIDUAL","买方单位/个人");
        map2.put("BUYER_UNIT_OR_INDIVIDUAL_ADDRESS","买方单位/个人住址");
        map2.put("CANCELLATION_MARK","作废标志");
        map2.put("CAR_PRICE","车价合计");
        map2.put("CERTIFICATE_OF_IMPORT","进口证明书号");
        map2.put("CHECK_CODE","校验码");
        map2.put("COMMODITY_INSPECTION_NO","商检单号");
        map2.put("DETAILED_LIST_TYPE","销货清单");
        map2.put("DRAWER","开票人");
        map2.put("ENGINE_NO","发动机号");
        map2.put("ID_NO","购方身份证号/组织机构代码");
        map2.put("INVOICE_AMOUNT","发票金额");
        map2.put("INVOICE_CODE","发票代码");
        map2.put("INVOICE_DATE","开票日期");
        map2.put("INVOICE_NO","发票号码");
        map2.put("E_INVOICE_NO","全电纸票号码");
        map2.put("INVOICE_TYPE","发票种类");
        map2.put("LEMON_MARKET","二手车市场");
        map2.put("LEMON_MARKET_ADDRESS","二手车市场地址");
        map2.put("LEMON_MARKET_BANK_AND_ACCOUNT","二手车市场开户银行及账号");
        map2.put("LEMON_MARKET_PHONE","二手车市场电话");
        map2.put("LEMON_MARKET_TAX_NO","二手车市场纳税人识别号");
        map2.put("LICENSE_PLATE","车牌照号");
        map2.put("LIMITED_PEOPLE_COUNT","限乘人数");
        map2.put("MACHINE_NO","机器编号");
        map2.put("OFD_URL","ofd板式文件下载地址");
        map2.put("PDF_URL","pdf板式文件下载地址");
        map2.put("PAYEE","收款人");
        map2.put("PRODUCE_AREA","产地");
        map2.put("QUALIFIED_NO","合格证号");
        map2.put("REGISTRATION_NO","登记证号");
        map2.put("REMARK","备注");
        map2.put("REVIEWER","复核人");
        map2.put("SALER_ACCOUNT","销方开户行及账号");
        map2.put("SALER_ADDRESS","销方地址");
        map2.put("SALER_ADDRESS_PHONE","销方地址、 电话");
        map2.put("SALER_BANK_ACCOUNT","销方开户账号");
        map2.put("SALER_BANK_NAME","销方开户银行");
        map2.put("SALER_NAME","销方名称");
        map2.put("SALER_PHONE","卖方电话");
        map2.put("SALER_TAX_NO","销方税号");
        map2.put("SALER_UNIT_CODE_OR_ID_NO","卖方单位代码/身份证号");
        map2.put("SALER_UNIT_OR_INDIVIDUAL","卖方单位/个人");
        map2.put("SALER_UNIT_OR_INDIVIDUAL_ADDRESS","卖方单位/个人住址");
        map2.put("TAX_AMOUNT","发票税额");
        map2.put("TAX_AUTHORITY_CODE","主管税务机关代码");
        map2.put("TAX_AUTHORITY_NAME","主管税务机关名称");
        map2.put("TAX_PAYMENT_CERTIFICATE_NO","完税凭证号码");
        map2.put("TAX_RATE","税率");
        map2.put("TONNAGE","吨位");
        map2.put("TOTAL_AMOUNT","价税合计");



        List<ColumnVo> columnList = new ArrayList<>();
        getHeadLineList().forEach(s -> {
            String v = map.get(s.toUpperCase());
            String c = map2.get(s.toUpperCase());
            if (Strings.isNotEmpty(v)){
                ColumnVo columnVo = new ColumnVo();
                columnVo.setFieldDbName(s.toLowerCase());
                columnVo.setFieldDbType(v.toLowerCase());
                columnVo.setFieldName(DbReadTableUtil.tableColumnName2CodeFieldName(s.toLowerCase()));
                columnVo.setFieldType(DbReadTableUtil.getFiledCodeType(v.toLowerCase(), null, null));
                columnVo.setFiledComment(c);
                columnList.add(columnVo);
            }
        });

        return columnList;
    }

    public static List<ColumnVo> generateSqlItem() {
        Map<String, String> map = new HashMap<>();
        map.put("ID","VARCHAR2");
        map.put("HEAD_ID","VARCHAR2");
        map.put("ELS_ACCOUNT","VARCHAR2");
        map.put("BUS_ACCOUNT","VARCHAR2");
        map.put("TO_ELS_ACCOUNT","VARCHAR2");
        map.put("FBK1","VARCHAR2");
        map.put("FBK2","VARCHAR2");
        map.put("FBK3","VARCHAR2");
        map.put("FBK4","VARCHAR2");
        map.put("FBK5","VARCHAR2");
        map.put("CREATE_BY","VARCHAR2");
        map.put("CREATE_BY_ID","VARCHAR2");
        map.put("CREATE_TIME","DATE");
        map.put("UPDATE_BY","VARCHAR2");
        map.put("UPDATE_BY_ID","VARCHAR2");
        map.put("UPDATE_TIME","DATE");
        map.put("IS_DELETED","NUMBER");

        map.put("CONTRACT_NUMBER","VARCHAR2");
        map.put("ZPLX","VARCHAR2");
        map.put("XH","VARCHAR2");
        map.put("ZSDW","VARCHAR2");
        map.put("ZSSL","VARCHAR2");
        map.put("ZSBYJJE","VARCHAR2");
        map.put("ZSKSRQ","VARCHAR2");
        map.put("ZSJSRQ","VARCHAR2");
        map.put("YSYJE","VARCHAR2");
        map.put("SYKSYJE","VARCHAR2");
        map.put("ZPMS","VARCHAR2");
        map.put("YSYSL","VARCHAR2");
        map.put("SYKSYSL","VARCHAR2");

        Map<String, String> map2 = new HashMap<>();

        map2.put("ID","ID");
        map2.put("RELATION_ID","关联ID");
        map2.put("HEAD_ID","头ID");
        map2.put("ELS_ACCOUNT","els账号");
        map2.put("BUS_ACCOUNT","BUS_ACCOUNT");
        map2.put("TO_ELS_ACCOUNT","供应商els账号");
        map2.put("SUPPLIER_CODE","供应商编码");
        map2.put("SUPPLIER_NAME","供应商名称");
        map2.put("FBK1","备用字段");
        map2.put("FBK2","备用字段");
        map2.put("FBK3","备用字段");
        map2.put("FBK4","备用字段");
        map2.put("FBK5","备用字段");
        map2.put("CREATE_BY","创建人");
        map2.put("CREATE_BY_ID","创建人ID");
        map2.put("CREATE_TIME","创建时间");
        map2.put("UPDATE_BY","修改人");
        map2.put("UPDATE_BY_ID","修改人ID");
        map2.put("UPDATE_TIME","修改时间");
        map2.put("IS_DELETED","是否删除");

        map2.put("CONTRACT_NUMBER","合同号");
        map2.put("ZPLX","赠品类型");
        map2.put("XH","行号");
        map2.put("ZSDW","赠送单位");
        map2.put("ZSSL","赠送数量");
        map2.put("ZSBYJJE","赠送备用金金额");
        map2.put("ZSKSRQ","赠送开始日期");
        map2.put("ZSJSRQ","赠送结束日期");
        map2.put("YSYJE","已使用金额");
        map2.put("SYKSYJE","剩余可使用金额");
        map2.put("ZPMS","赠品描述");
        map2.put("YSYSL","已使用数量");
        map2.put("SYKSYSL","剩余可使用数量");


        List<ColumnVo> columnList = new ArrayList<>();
        getItemLineList().forEach(s -> {
            String v = map.get(s.toUpperCase());
            String c = map2.get(s.toUpperCase());
            if (Strings.isNotEmpty(v)){
                ColumnVo columnVo = new ColumnVo();
                columnVo.setFieldDbName(s.toLowerCase());
                columnVo.setFieldDbType(v.toLowerCase());
                columnVo.setFieldName(DbReadTableUtil.tableColumnName2CodeFieldName(s.toLowerCase()));
                columnVo.setFieldType(DbReadTableUtil.getFiledCodeType(v.toLowerCase(), null, null));
                columnVo.setFiledComment(c);
                columnList.add(columnVo);
            }
        });

        return columnList;
    }


    public static List<String> getItemLineList() {
        List<String> a = new ArrayList<>();
        a.add("id");
        a.add("relation_id");
        a.add("head_id");
        a.add("els_account");
        a.add("bus_account");
        a.add("to_els_account");
        a.add("supplier_code");
        a.add("supplier_name");
        a.add("fbk1");
        a.add("fbk2");
        a.add("fbk3");
        a.add("fbk4");
        a.add("fbk5");
        a.add("create_by");
        a.add("create_by_id");
        a.add("create_time");
        a.add("update_by");
        a.add("update_by_id");
        a.add("update_time");
        a.add("is_deleted");


        a.add("contract_number");
        a.add("zplx");
        a.add("xh");
        a.add("zsdw");
        a.add("zssl");
        a.add("zsbyjje");
        a.add("zsksrq");
        a.add("zsjsrq");
        a.add("ysyje");
        a.add("syksyje");
        a.add("zpms");
        a.add("ysysl");
        a.add("syksysl");

        return a;
    }

    public static List<String> getHeadLineList() {
        List<String> a = new ArrayList<>();
        a.add("id");
        a.add("template_number");
        a.add("template_name");
        a.add("template_version");
        a.add("template_account");
        a.add("els_account");
        a.add("bus_account");
        a.add("to_els_account");
        a.add("supplier_code");
        a.add("supplier_name");
        a.add("business_type");
        a.add("create_by");
        a.add("create_by_id");
        a.add("create_time");
        a.add("current_by_id");
        a.add("update_by");
        a.add("update_by_id");
        a.add("update_time");
        a.add("fbk1");
        a.add("fbk2");
        a.add("fbk3");
        a.add("fbk4");
        a.add("fbk5");
        a.add("fbk6");
        a.add("fbk7");
        a.add("fbk8");
        a.add("fbk9");
        a.add("fbk10");
        a.add("relation_id");
        a.add("is_deleted");
        a.add("check_number");
        a.add("band_model");
        a.add("blue_invoice_code");
        a.add("blue_invoice_no");
        a.add("business_unit");
        a.add("business_unit_address");
        a.add("business_unit_bank_and_account");
        a.add("business_unit_phone");
        a.add("business_unit_tax_no");
        a.add("buyer_account");
        a.add("buyer_address_phone");
        a.add("buyer_name");
        a.add("buyer_phone");
        a.add("buyer_tax_no");
        a.add("buyer_unit_code_or_id_no");
        a.add("buyer_unit_or_individual");
        a.add("buyer_unit_or_individual_address");
        a.add("cancellation_mark");
        a.add("car_price");
        a.add("certificate_of_import");
        a.add("check_code");
        a.add("commodity_inspection_no");
        a.add("detailed_list_type");
        a.add("drawer");
        a.add("engine_no");
        a.add("id_no");
        a.add("invoice_amount");
        a.add("invoice_code");
        a.add("invoice_date");
        a.add("invoice_no");
        a.add("e_invoice_no");
        a.add("invoice_type");
        a.add("lemon_market");
        a.add("lemon_market_address");
        a.add("lemon_market_bank_and_account");
        a.add("lemon_market_phone");
        a.add("lemon_market_tax_no");
        a.add("license_plate");
        a.add("limited_people_count");
        a.add("machine_no");
        a.add("ofd_url");
        a.add("pdf_url");
        a.add("payee");
        a.add("produce_area");
        a.add("qualified_no");
        a.add("registration_no");
        a.add("remark");
        a.add("reviewer");
        a.add("saler_account");
        a.add("saler_address");
        a.add("saler_address_phone");
        a.add("saler_bank_account");
        a.add("saler_bank_name");
        a.add("saler_name");
        a.add("saler_phone");
        a.add("saler_tax_no");
        a.add("saler_unit_code_or_id_no");
        a.add("saler_unit_or_individual");
        a.add("saler_unit_or_individual_address");
        a.add("tax_amount");
        a.add("tax_authority_code");
        a.add("tax_authority_name");
        a.add("tax_payment_certificate_no");
        a.add("tax_rate");
        a.add("tonnage");
        a.add("total_amount");
        a.add("traffic_fee_flag");
        a.add("transferred_vehicle_office");
        a.add("vehicle_identification_no");
        a.add("vehicle_type");
        return a;

    }


    public static List<String> getItemList() {
        List<String> a = new ArrayList<>();
        a.add("detailNo");
        a.add("goodsName");
        a.add("goodsTaxCode");
        a.add("detailAmount");
        a.add("num");
        a.add("taxRate");
        a.add("taxAmount");
        a.add("taxUnitPrice");
        a.add("taxDetailAmount");
        a.add("unitPrice");
        a.add("specificationModel");
        a.add("unit");
        a.add("plateNo");
        a.add("type");
        a.add("trafficDateStart");
        a.add("trafficDateEnd");
        a.add("zeroTaxRateFlag");
        a.add("internationalOrInternal");
        a.add("gpNumber");
        a.add("name");
        a.add("idNumber");
        a.add("eTicketNumber");
        a.add("departureDate");
        a.add("departureTime");
        a.add("departureStation");
        a.add("destinationStation");
        a.add("trainNumber");
        a.add("trainCarriage");
        a.add("seatNumber");
        a.add("seatLevel");
        a.add("taxRate");
        return a;
    }


    public static List<String> getHeadList() {
        List<String> a = new ArrayList<>();
        a.add("checkNumber");
        a.add("bandModel");
        a.add("blueInvoiceCode");
        a.add("blueInvoiceNo");
        a.add("businessUnit");
        a.add("businessUnitAddress");
        a.add("businessUnitBankAndAccount");
        a.add("businessUnitPhone");
        a.add("businessUnitTaxNo");
        a.add("buyerAccount");
        a.add("buyerAddressPhone");
        a.add("buyerName");
        a.add("buyerPhone");
        a.add("buyerTaxNo");
        a.add("buyerUnitCodeOrIdNo");
        a.add("buyerUnitOrIndividual");
        a.add("buyerUnitOrIndividualAddress");
        a.add("cancellationMark");
        a.add("carPrice");
        a.add("certificateOfImport");
        a.add("checkCode");
        a.add("commodityInspectionNo");
        a.add("detailedListType");
        a.add("drawer");
        a.add("engineNo");
        a.add("idNo");
        a.add("invoiceAmount");
        a.add("invoiceCode");
        a.add("invoiceDate");
        a.add("invoiceNo");
        a.add("eInvoiceNo");
        a.add("invoiceType");
        a.add("lemonMarket");
        a.add("lemonMarketAddress");
        a.add("lemonMarketBankAndAccount");
        a.add("lemonMarketPhone");
        a.add("lemonMarketTaxNo");
        a.add("licensePlate");
        a.add("limitedPeopleCount");
        a.add("machineNo");
        a.add("ofdUrl");
        a.add("pdfUrl");
        a.add("payee");
        a.add("produceArea");
        a.add("qualifiedNo");
        a.add("registrationNo");
        a.add("remark");
        a.add("reviewer");
        a.add("salerAccount");
        a.add("salerAddress");
        a.add("salerAddressPhone");
        a.add("salerBankAccount");
        a.add("salerBankName");
        a.add("salerName");
        a.add("salerPhone");
        a.add("salerPhone");
        a.add("salerTaxNo");
        a.add("salerUnitCodeOrIdNo");
        a.add("salerUnitOrIndividual");
        a.add("salerUnitOrIndividualAddress");
        a.add("taxAmount");
        a.add("taxAuthorityCode");
        a.add("taxAuthorityName");
        a.add("taxPaymentCertificateNo");
        a.add("taxRate");
        a.add("tonnage");
        a.add("totalAmount");
        a.add("trafficFeeFlag");
        a.add("transferredVehicleOffice");
        a.add("vehicleIdentificationNo");
        a.add("vehicleType");
        return a;
    }


    public static void main(String[] args) {
        List<String> a = getItemLineList();
        for (String s : a) {
            System.out.println(s + "   " + lineToHump(s));
        }

    }
}
