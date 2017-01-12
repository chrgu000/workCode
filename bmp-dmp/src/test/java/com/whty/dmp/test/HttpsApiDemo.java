package com.whty.dmp.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zouyan on 2016/10/26.
 */
public class HttpsApiDemo {
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
        HttpClient httpClient = new DefaultHttpClient();
      enableSSL(httpClient);
    //    HttpClient httpClient = CertificateValidationIgnored.getNoCertificateHttpClient();
        try {
            HttpResponse response = null;
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
            httpClient.getConnectionManager().shutdown();
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
        HttpClient httpClient = new DefaultHttpClient();
        enableSSL(httpClient);
  //      HttpClient httpClient = CertificateValidationIgnored.getNoCertificateHttpClient();
        HttpResponse response = null;
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
            httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * 访问https的网站
     *
     * @param httpclient
     */
    public static void enableSSL(HttpClient httpclient) {
        // 调用ssl
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{truseAllManager}, null);
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme https = new Scheme("https", sf, 443);
            httpclient.getConnectionManager().getSchemeRegistry().register(https);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写验证方法，取消检测ssl
     */
    private static TrustManager truseAllManager = new X509TrustManager() {
        public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };
}
