package com.whty.dmp.test;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Created by zouyan on 2016/10/26.
 */
public class NHttpsApiDemo {
    public static void main(String[] args) {
        publish();
        subscription();
    }

    public static void subscription() {
     //   String url = "https://111.47.123.42:18449/dataCenterApi/api/subscription";
          String url = "https://111.47.123.42:18448/dataCenterApi/api/subscription";
        //   String url = "http://datacenterapi.d.huijiaoyun.com:7789/dataCenterApi/api/subscription";
        String serviceCode = "1ff811f998c44f1faf4cbdf448e72738";
        String token = "c5742ed3e26f446ea514a30b716812ab";
        int isTest = 0;
        url += "?serviceCode=" + serviceCode + "&token=" + token + "&isTest=" + isTest;
        CloseableHttpClient httpClient = createSSLInsecureClient();
        try {
            CloseableHttpResponse response = null;
            String responseString = null;
            HttpGet httpget = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout
                    (30000).setConnectTimeout(30000).build();
            httpget.setConfig(requestConfig);
            response = httpClient.execute(httpget);
            if (response.getEntity() != null) {
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            System.out.println("订阅状态码：" + response.getStatusLine().getStatusCode());
            System.out.println("订阅信息：\n" + responseString);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void publish() {
    //    String url = "https://111.47.123.42:18449/dataCenterApi/api/publish";
        String url = "https://111.47.123.42:18448/dataCenterApi/api/publish";

        String serviceCode = "4f3b5de0ec4c47aeaa3bc6ab65a0f733";
        String message = "[{" +
                "" +
                " \"obj\": {" +
                "    \"id\": \"F259F9A32CD27692E000000123\"," +
                "    \"name\": \"x的家长123\"," +
                "    \"credType\": \"1\"," +
                "    \"idCardNo\": \"123\"," +
                "    \"sex\": \"1\"," +
                "    \"emails\": \"hsyxb01@163.com\"," +
                "    \"userType\": \"3\"" +
                "  }," +
                "  \"operatorType\": 1" +
               ",    \"platCode\": \"eduYun\"" +
                "}]";
        Map map = new HashMap<String, String>();
        map.put("serviceCode", serviceCode);
        map.put("messageList", message);
//        map.put("isTest", "1");
        CloseableHttpClient httpClient = createSSLInsecureClient();
  //      HttpClient httpClient = CertificateValidationIgnored.getNoCertificateHttpClient();
        CloseableHttpResponse response = null;
        String responseString = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout
                    (30000).setConnectTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            List nvps = new ArrayList();
            nvps.add(new BasicNameValuePair("serviceCode", serviceCode));
            nvps.add(new BasicNameValuePair("messageList", message));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("发布成功");
            } else {
                System.out.println("发布错误状态码：" + response.getStatusLine().getStatusCode());
                if (response.getEntity() != null) {
                    responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
                System.out.println("发布错误信息：\n" + responseString);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 访问https的网站
     */
     public static CloseableHttpClient createSSLInsecureClient() {
        try {
            // 创建安全套接字对象
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            // 获取分层tls/ssl连接
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
}
