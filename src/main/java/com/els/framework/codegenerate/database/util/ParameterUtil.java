package com.els.framework.codegenerate.database.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class ParameterUtil {
    public static String getParamsInsertValueSql(String[] paramArrayOfString) {

        StringBuffer localStringBuffer = new StringBuffer();

        for (String str : paramArrayOfString) {

            if (StringUtils.isNotBlank(str)) {

                localStringBuffer.append(",");

                localStringBuffer.append("'");

                localStringBuffer.append(str.trim());

                localStringBuffer.append("'");
            }
        }

        return localStringBuffer.toString().substring(1);
    }

    public static String str2Camle(String paramString) {

        if (StringUtils.isNotBlank(paramString)) {

            paramString = paramString.substring(0, 1).toLowerCase() + paramString.substring(1);
        }

        return paramString;
    }

    public static Integer defaultZero(Integer paramInteger) {

        if (paramInteger == null) {

            return Integer.valueOf(0);
        }

        return paramInteger;
    }

    public static boolean checkParamExists(String paramString, String[] paramArrayOfString) {

        if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {

            return false;
        }

        for (int i = 0; i < paramArrayOfString.length; i++) {

            String str = paramArrayOfString[i];

            if (str.equals(paramString)) {

                return true;
            }
        }

        return false;
    }

    public static boolean checkParamExists(String paramString, List<String> paramList) {

        String[] arrayOfString = new String[0];

        if (paramList != null) {

            arrayOfString = (String[])paramList.toArray();
        }

        if ((arrayOfString == null) || (arrayOfString.length == 0)) {

            return false;
        }

        for (int i = 0; i < arrayOfString.length; i++) {

            String str = arrayOfString[i];

            if (str.equals(paramString)) {

                return true;
            }
        }

        return false;
    }
}
