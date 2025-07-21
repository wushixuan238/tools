package com.els.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;

public class OauthParam {
    private LinkedHashMap<String, String> param = new LinkedHashMap();
    private String oauthTokenSecret;
    private String oauthConsumerSecret;
    private String url;
    private String method;

    public OauthParam() {
    }

    public static OauthParam newInstance(String strOauthParam) {
        OauthParam oauthParam = new OauthParam();
        String[] params = StringUtils.split(StringUtils.substringAfter(strOauthParam, "OAuth "), ",");
        String[] var3 = params;
        int var4 = params.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String p = var3[var5];
            String[] tmp = StringUtils.split(p, "=");
            oauthParam.param.put(tmp[0], StringUtils.remove(tmp[1], "\""));
        }

        return oauthParam;
    }

    public void setParam(LinkedHashMap<String, String> param) {
        this.param = param;
    }

    public String getOauthConsumerKey() {
        return decodeUrlUtf8(this.param.get("oauth_consumer_key"));
    }

    public String getOauthToken() {
        return decodeUrlUtf8(this.param.get("oauth_token"));
    }

    public String getOauthNonce() {
        return decodeUrlUtf8(this.param.get("oauth_nonce"));
    }

    public String getOauthSignature() {
        return decodeUrlUtf8(this.param.get("oauth_signature"));
    }

    public String getOauthTimestamp() {
        return decodeUrlUtf8(this.param.get("oauth_timestamp"));
    }

    public String getOauthTokenSecret() {
        return this.oauthTokenSecret;
    }

    public void setOauthTokenSecret(String oauthTokenSecret) {
        this.oauthTokenSecret = oauthTokenSecret;
    }

    public String getOauthConsumerSecret() {
        return this.oauthConsumerSecret;
    }

    public void setOauthConsumerSecret(String oauthConsumerSecret) {
        this.oauthConsumerSecret = oauthConsumerSecret;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LinkedHashMap<String, String> getParam() {
        return this.param;
    }


    public static String encodeUrlUtf8(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String decodeUrlUtf8(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
