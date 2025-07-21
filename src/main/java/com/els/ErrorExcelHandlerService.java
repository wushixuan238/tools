package com.els;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.els.common.util.RedisUtil;
import com.els.common.util.SpringContextUtils;
import com.els.common.util.SysUtil;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ErrorExcelHandlerService {




    public void errorAdd(ExcelImportDTO excelImportDTO, String msg, Map<String, Object> data) {
        data.put("this.getErrorStatus()", "导入失败");
        data.put("this.getErrorTitle()", msg);
        excelImportDTO.getErrorDataList().add(data);
    }
}
