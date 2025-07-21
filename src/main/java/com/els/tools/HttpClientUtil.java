package com.els.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

@Slf4j
public class HttpClientUtil {
    /**
     * HttpClient 调用 WebService
     * @param wsUrl webService地址，格式：http://ip:port/xxx/xxx/soap?wsdl
     * @param json格式的入参
     * @return
     */
    public static String callServiceHC(String wsUrl, String jsonStr) {
        String xml = createSoapContent(jsonStr);
        String returnDatabase = doPostSoap(wsUrl, xml, "");
        log.info("returnDatabase===>{}", returnDatabase);
        return returnDatabase;
    }

    /**
     * 根据拼接 xml 字符串
     * @param input
     * @return
     */
    public static String createSoapContent(String jsonStr) {
        log.info("开始拼接请求报文");
        //开始拼接请求报文
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:zys=\"http://www.chenjy.com.cn/\">\n");
        stringBuilder.append("<soapenv:Header/>\n");
        stringBuilder.append("<soapenv:Body>\n");
        stringBuilder.append("<cjy:CallInterface>\n");
        stringBuilder.append("<cjy:msgHeader><![CDATA[\n");
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        stringBuilder.append("<root>\n");
        stringBuilder.append("<serverName>getInfo</serverName>\n");
        stringBuilder.append("<format>xml</format>\n");
        stringBuilder.append("<callOperator>测试</callOperator>\n");
        stringBuilder.append("<certificate>AcsaoP21Lxw5KAoQu6SLs624bhGjwNL0DzxsQ9a7B/HbqNsPPcA==</certificate>\n");
        stringBuilder.append("</root>\n");
        stringBuilder.append("]]></cjy:msgHeader>\n");
        stringBuilder.append("<cjy:msgBody><![CDATA[\n");
        stringBuilder.append( jsonStr+ "\n");
        stringBuilder.append("]]></cjy:msgBody>\n");
        stringBuilder.append("</cjy:CallInterface>\n");
        stringBuilder.append("</soapenv:Body>\n");
        stringBuilder.append("</soapenv:Envelope>");
        log.info("拼接后的参数"+stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * HTTPClient 调用 WebService
     * @param url
     * @param soap
     * @param SOAPAction
     * @return
     */
    public static String doPostSoap(String url, String soap, String SOAPAction) {
        //请求体
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            httpPost.setHeader("SOAPAction", SOAPAction);
            StringEntity data = new StringEntity(soap,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retStr;
    }
}

