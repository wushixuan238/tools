package com.els.tools;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InterfaceUtil {

    private static String AppUrl ="https://api-sit.51qqt.com/";
    private static String AppKey ="8649f187";
    private static String AppSecret ="163b01696dbb41f3aac6c7c0d55f54a5";

    public static JSONObject getAuthObj() {

        StringBuilder url = new StringBuilder(AppUrl);
        url.append("/els/openApi/getToken").append("?key=").append(AppKey).append("&secret=").append(AppSecret);
        String result = HttpUtil.get(url.toString());
        JSONObject resultObj = JSONObject.parseObject(result);
        int code = resultObj.getIntValue("code");
        if (code == 200) {
            JSONObject authObj = new JSONObject();
            String token = resultObj.getJSONObject("result").getString("token");
            authObj.put("token", token);
            authObj.put("appUrl", AppUrl);
            return authObj;
        }

        return null;
    }



    public JSONObject callInterface(JSONObject body) {
        JSONObject param = new JSONObject();
        param.put("bus_account", 1698378);
        param.put("interface_code","JK20240701000001");
        param.put("body", body);

        System.out.println(param.toJSONString());
        JSONObject authObj = getAuthObj();
        String result = HttpRequest.post(authObj.getString("appUrl") + "/els/openApi/invoke").header("token", authObj.getString("token"))
                .body(param.toJSONString()).execute().body();
        return JSONObject.parseObject(result);
    }


    public static JSONObject callInterfaceNew(MultiValueMap<String, Object> param) {
        RestTemplate yourRestTemplate = new RestTemplate();
        param.add("bus_account", 1698378);
        param.add("interface_code","JK20240701000001");
        JSONObject authObj = getAuthObj();

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", authObj.getString("token"));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(param, headers);
        ResponseEntity<String> obj = yourRestTemplate.exchange(authObj.getString("appUrl") + "/els/openApi/invoke", HttpMethod.POST, entity, String.class);
        return JSONObject.parseObject(obj.getBody());
    }

    public static JSONObject post(JSONObject param) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Access-Token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjI4NDg2OTksImFjY291bnQiOiIzMDAzNzNfMTAwMSJ9.8MrZbREMugLp7O7kFbUurO4nUZCJKjA6JLyoIy7G6mQ" );
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(param, headers);
//        ResponseEntity<String> obj = yourRestTemplate.exchange("http://srm.21yangjie.com:8000/els/report/jmreport/exportAllExcelStream", HttpMethod.POST, entity, String.class);


        String url = "http://srm.21yangjie.com:8000/els/report/jmreport/exportAllExcelStream";
        RequestCallback requestCallback = request -> {
            HttpHeaders httpHeaders = request.getHeaders();
            httpHeaders.set(HttpHeaders.ACCEPT, "application/pdf");
            httpHeaders.set(HttpHeaders.ACCEPT, "application/pdf");
        };

        ResponseExtractor<Void> responseExtractor = response -> {
            File destFile = new File("c:/d/");
            try (InputStream inputStream = response.getBody();
                 FileOutputStream outputStream = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return null;
        };

        restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);


        return null;
    }

}
