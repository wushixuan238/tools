package com.els;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PurchaseInformationRecordsExcelHandler extends ErrorExcelHandlerService{

    void test(){

        Map<String, Object> data = new HashMap<>();
        data.put("taxRate","12121");
        ExcelImportDTO excelImportDTO = new ExcelImportDTO();
        errorAdd(excelImportDTO,"hsdsjfks",data);
        System.out.println(JSONObject.toJSONString(excelImportDTO));
    }
}
