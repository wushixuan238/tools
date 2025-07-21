package com.els;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelImportDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String excelBusinessOptType;
        private String excelBusinessOptTypeName;
        private String errorExcelRunnerId;
        private boolean saveToDb;
        private List<Map<String, Object>> dataList;
        private int totalCount;
        private int successCount;
        private int failCount;
        private final List<String> errorTitleList = new ArrayList();
        private final List<Map<String, Object>> errorDataList = new ArrayList();
        private final Map<String, List<Map<String, Object>>> oldDataMap = new HashMap();
        private Map<String, Object> otherRequestParam;

        private void setErrorTitleList(List<String> errorTitleList) {
        }

        private void setErrorDataList(List<Map<String, Object>> errorDataList) {
        }

    public ExcelImportDTO() {
        }

        public String getExcelBusinessOptType() {
            return this.excelBusinessOptType;
        }

        public String getExcelBusinessOptTypeName() {
            return this.excelBusinessOptTypeName;
        }

        public String getErrorExcelRunnerId() {
            return this.errorExcelRunnerId;
        }

        public boolean isSaveToDb() {
            return this.saveToDb;
        }

        public List<Map<String, Object>> getDataList() {
            return this.dataList;
        }

        public int getTotalCount() {
            return this.totalCount;
        }

        public int getSuccessCount() {
            return this.successCount;
        }

        public int getFailCount() {
            return this.failCount;
        }

        public List<String> getErrorTitleList() {
            return this.errorTitleList;
        }

        public List<Map<String, Object>> getErrorDataList() {
            return this.errorDataList;
        }

        public Map<String, List<Map<String, Object>>> getOldDataMap() {
            return this.oldDataMap;
        }

        public Map<String, Object> getOtherRequestParam() {
            return this.otherRequestParam;
        }

        public ExcelImportDTO setExcelBusinessOptType(String excelBusinessOptType) {
            this.excelBusinessOptType = excelBusinessOptType;
            return this;
        }

        public ExcelImportDTO setExcelBusinessOptTypeName(String excelBusinessOptTypeName) {
            this.excelBusinessOptTypeName = excelBusinessOptTypeName;
            return this;
        }

        public ExcelImportDTO setErrorExcelRunnerId(String errorExcelRunnerId) {
            this.errorExcelRunnerId = errorExcelRunnerId;
            return this;
        }

        public ExcelImportDTO setSaveToDb(boolean saveToDb) {
            this.saveToDb = saveToDb;
            return this;
        }

        public ExcelImportDTO setDataList(List<Map<String, Object>> dataList) {
            this.dataList = dataList;
            return this;
        }

        public ExcelImportDTO setTotalCount(int totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public ExcelImportDTO setSuccessCount(int successCount) {
            this.successCount = successCount;
            return this;
        }

        public ExcelImportDTO setFailCount(int failCount) {
            this.failCount = failCount;
            return this;
        }

        public ExcelImportDTO setOtherRequestParam(Map<String, Object> otherRequestParam) {
            this.otherRequestParam = otherRequestParam;
            return this;
        }

    public void errorAdd(ExcelImportDTO excelImportDTO, String msg, Map<String, Object> data) {
        data.put("getErrorStatus", "导入失败");
        data.put("getErrorTitle()", msg);
        excelImportDTO.getErrorDataList().add(data);
    }
}
