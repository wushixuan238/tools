
package com.els.framework.codegenerate.generate.util;

import org.apache.commons.lang.StringUtils;

public class SqlStringConverter {

    public static String getYorN(String paramString) {

        if (("YES".equals(paramString)) || ("yes".equals(paramString)) || ("y".equals(paramString))
            || ("Y".equals(paramString)) || ("f".equals(paramString))) {

            return "Y";

        }

        if (("NO".equals(paramString)) || ("N".equals(paramString)) || ("no".equals(paramString))
            || ("n".equals(paramString)) || ("t".equals(paramString))) {

            return "N";

        }

        return null;

    }

    public static String defaultEmpty(String paramString) {

        if (StringUtils.isBlank(paramString)) {

            return "";

        }

        return paramString;

    }

    public static String appendQuotes(String paramString) {

        return "'" + paramString + "'";

    }

}
