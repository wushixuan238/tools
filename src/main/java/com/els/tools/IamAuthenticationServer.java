package com.els.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 对接IAM统一认证使用，私有化部署需去掉，防止环境变量导致项目启动失败
 */
public class IamAuthenticationServer {

    String iamAuthUrl ="http://172.16.1.100:32763";
    String iamClientId ="E41YNNJL5L";
    String iamClientSecret="4820c80d-ca84-43a6-bfa2-6d9c7777f2b6";


    private RestTemplate restTemplate = new RestTemplate();

    //https://iam-uat.sxc.sh/api/v1/oauth/token?client_id=YFM9A75SX2&client_secret=822b5093-62b1-499a-97cd-e2223694ec23&code=95f9cc28-e1d6-449f-b6b2-30df063be1cd
    public String getIamAccessToken(String code) {
        validateParam(code);
        String authUrl = iamAuthUrl +
                "/api/v1/oauth/token?client_id=" +
                iamClientId +
                "&client_secret=" +
                iamClientSecret +
                "&code=" +
                code;


        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(authUrl, JSONObject.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return Objects.requireNonNull(responseEntity.getBody()).getJSONObject("data").getString("access_token");
        } else {
//            throw new CommonException("400", "获取IAM AccessToken失败");
        }

        return "";
    }

    private void validateParam(String code) {
        if (StringUtils.isBlank(code)) {
//            throw new CommonException("参数不存在或已过有效期。{}", code);
        }
    }


    //https://iam-uat.sxc.sh/api/v1/user/get_usertoken?access_token=f7a55fb8-2475-4ce9-b8dd-a70e559b939b
    public String getIamUserToken(String code, String accessToken) {
        validateParam(code);
        validateParam(accessToken);

        String authUrl = iamAuthUrl +
                "/api/v1/user/get_usertoken?access_token=" +
                accessToken;

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        Map<Object, Object> map = new HashMap<>();
        map.put("code", code);

        String body = JSON.toJSONString(map);

        JSONObject jsonObj = JSONObject.parseObject(body);

        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(authUrl, formEntity, JSONObject.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Map<String, String> data = (HashMap<String, String>) responseEntity.getBody().get("data");
            if (data == null) {
//                throw new CommonException("400", "获取IAM UserToken失败");
            }
            return data.get("user_token");
        } else {
//            throw new CommonException("400", "获取IAM UserToken失败");
        }

        return "";

    }


    //https://iam-uat.sxc.sh/api/v1/user/get_info2?access_token=ACCESS_TOKEN
    public IamUserInfo getIamUserInfo(String accessToken, String userToken) {
        validateParam(accessToken);
        validateParam(userToken);

        String authUrl = iamAuthUrl +
                "/api/v1/user/get_info2?access_token=" +
                accessToken;

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        Map<Object, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        String body = JSON.toJSONString(map);

        JSONObject jsonObj = JSONObject.parseObject(body);

        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(authUrl, formEntity, JSONObject.class);


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return JSON.parseObject(JSON.toJSONString(responseEntity.getBody().get("data")), IamUserInfo.class);

        } else {
//            throw new CommonException("400", "获取IAM UserInfo失败");
        }
        return new IamUserInfo();
    }


    //http://choerodon-gateway8080.saiciaas-findev.njpk-uat-a.sxc.sh/oauth/findev/login        post
//    public void loginFinDev(String username, String password) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//        params.add("username", username);
//        params.add("password", password);
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//        String response = restTemplate.postForObject(loginUrl, requestEntity, String.class);
//
//        System.out.println("response = " + response);
//    }


    //通过userToken 获取csp token  TODO 租户信息同步异步在iam服务解决
    //http://user-common8080.c01.shjq-uat-a.sxc.sh/v2/oauth2/getJWTV1?userToken=9e9848d4f6c44848afeafbc588178ff4&expire=72000
//    public String getCspToken(String userToken) {
//        validateParam(userToken);
//        String authUrl = cspUrl +
//                "/v2/oauth2/getJWTV1?userToken=" +
//                userToken +
//                "&expire=72000";
//
//        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(authUrl, JSONObject.class);
//
//        if (responseEntity.getStatusCode().is2xxSuccessful()) {
//            return JSON.parseObject(JSON.toJSONString(responseEntity.getBody().get("data")), String.class);
//
//        } else {
//            throw new CommonException("400", "获取CSP token失败");
//        }
//
//    }

    //通过csp token  获取用户租户
    //http://console.csp-uat.sxc.sh/fsh_nav/menu/getMenus
//    public List<CspTenantVo> getCspTenant(String cspToken) {
//        validateParam(cspToken);
//
//        String authUrl = cspConsoleUrl + "/fsh_nav/menu/getMenus";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + cspToken);
//        ResponseEntity<JSONObject> response = restTemplate.exchange(authUrl, HttpMethod.GET, new HttpEntity<String>(headers), JSONObject.class);
//        Map data1 = JSON.parseObject(JSON.toJSONString(response.getBody().get("data")), Map.class);
//        return (List<CspTenantVo>) data1.get("tenantModels");
//
//    }


    public void logoutIamServer(String accessToken, String userToken) {
        String url = iamAuthUrl + "/api/v1/user/logout?access_token=" + accessToken;
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        Map<Object, Object> map = new HashMap<>();
        map.put("user_token", userToken);

        String body = JSON.toJSONString(map);

        JSONObject jsonObj = JSONObject.parseObject(body);

        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, formEntity, JSONObject.class);

    }


}
