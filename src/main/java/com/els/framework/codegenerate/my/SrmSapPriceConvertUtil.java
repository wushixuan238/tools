package com.els.framework.codegenerate.my;

import java.math.BigDecimal;

/**
 * srm与SAP预估价换算
 */
public class SrmSapPriceConvertUtil {

    /**
     * 小数点后保留最小位数
     */
    private static final int MIN_POINT_LENGTH = 2;

    /**
     * 根据SRM价格获取SAP价格单位： 价格单位值的长度 = sourceNum的小数部分的长度（整数的价格单位是1）
     *
     * @param sourceNum
     * @return
     */
    public static String computeUnitPriceBySrmPrice(BigDecimal sourceNum) {
        if (sourceNum == null) {
            return null;
        }

        int scale = sourceNum.stripTrailingZeros().scale();

        if (scale < MIN_POINT_LENGTH) {
            return "1";
        }

        StringBuilder zeroStr = new StringBuilder();
        for (int i = 0; i < scale - MIN_POINT_LENGTH; i++) {
            zeroStr.append("0");
        }

        return "1" + zeroStr;
    }

    /**
     * SRM价格转SAP价格
     *
     * @param sourceNum
     * @param unitPrice
     * @return
     */
    public static String convertToSapPriceBySrmPriceAndUnitPrice(BigDecimal sourceNum, BigDecimal unitPrice) {

        BigDecimal temp = sourceNum.stripTrailingZeros().multiply(unitPrice).setScale(MIN_POINT_LENGTH, BigDecimal.ROUND_UP);
        String newStr = temp.toString();

        String[] split = newStr.split("\\.");
        // 小数点前的数据
        String pre = split[0];
        // 小数点后的数据，补零操作
        StringBuilder last = new StringBuilder();
        if (split.length < 2) {
            for (int i = 0; i < MIN_POINT_LENGTH; i++) {
                last.append("0");
            }
        } else {
            last = new StringBuilder(split[1]);
            for (int i = 0; i < MIN_POINT_LENGTH - last.length(); i++) {
                last.append("0");
            }
        }
        return pre + "." + last.toString();
    }


    /**
     * SAP价格转SRM价格
     *
     * @param sourceNum
     * @param unitPrice
     * @return
     */
    public static String convertToSrmPriceBySapPriceAndUnitPrice(BigDecimal sourceNum, BigDecimal unitPrice) {
        return sourceNum.divide(unitPrice).toString();
//        //小数位数
//        int count = countTrailingZeros(Integer.parseInt(unitPrice)) + 1;
//        String newStr = Double.toString(sourceNum.doubleValue());
//        String[] split = newStr.split("\\.");
//        // 小数点前的数据
//        String pre = split[0];
//        // 小数点后的数据
//        String last = split[1];
//        //目标值的实际小数位
//        int indexCountOri = last.length();
//        if (count == indexCountOri) {
//            return sourceNum.toString();
//        }
//        if (indexCountOri > count) {
//            //数据问题
//            return null;
//        }
//
//        int needIndexCount = count - indexCountOri;
//        //结果小于1
//        if (needIndexCount >= pre.length()) {
//            StringBuilder zeroStr = new StringBuilder();
//            for (int n = 0; n < needIndexCount - pre.length(); n++) {
//                zeroStr.append("0");
//            }
//            return "0." + zeroStr + pre + last;
//        }
//        //结果大于1
//        //插入小数点的位置
//        int pointIndex = pre.length() - needIndexCount;
//        String newPre = insertCharAtPosition(pre.replace(".", ""), '.', pointIndex);
//        return newPre + last;
    }


}
