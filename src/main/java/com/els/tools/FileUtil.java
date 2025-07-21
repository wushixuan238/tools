package com.els.tools;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.els.config.mybatis.TenantContext;
import jdk.nashorn.internal.objects.NativeString;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    /**
     * @param url   http:xxx/file?name=xxx.docx
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String url, String savePath) throws Exception {
        URI uri = new URI(url);
        String query = uri.getQuery();
        String param = "";
        //获取文件名称及扩展名
        String originalFilename = "";
        String[] pairs;
        if (query.contains("&amp;")) {
            pairs = query.split("&amp;");
        } else {
            pairs = query.split("&");
        }
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
            String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
            if ("fileName".equals(key)) {
                originalFilename = value;
            } else {
                param = Strings.isEmpty(param) ? pair : param + "&" + pair;
            }
        }
        //重新组装有效url
        String newUrl = String.format("%s://%s%s?%s", uri.getScheme(), uri.getHost(), uri.getPath(), param);
        HttpURLConnection conn = (HttpURLConnection) new URL(newUrl).openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream input = conn.getInputStream();
//        List<Object> objects = EasyExcel.read(input).head(ProjectPartsEntity.class).sheet().headRowNumber(1).doReadSync();
        //获取自己数组
        byte[] getData = readInputStream(input);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }


        File file = new File(saveDir + File.separator + originalFilename);
        FileOutputStream output = new FileOutputStream(file);
        output.write(getData);
        if (output != null) {
            output.close();
        }
        if (input != null) {
            input.close();
        }
        System.out.println("download success!!");
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[10240];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    public static void main(String[] args) {
        String urlStr = "https://rongxin-cs.51qqt.com/els/attachment/purchaseAttachment/download?id=1824359867489288194&fileName=附件11.xlsx";
        try {
            downLoadFromUrl(urlStr, "./");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
