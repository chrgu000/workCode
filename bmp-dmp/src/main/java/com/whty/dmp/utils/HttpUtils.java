package com.whty.dmp.utils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

/**
 * httpcliet 工具类
 * 
 * @author chenjp
 * @date 2015年6月18日
 */
public class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static HttpUtils instance;

	public HttpUtils() {

	}

	public static HttpUtils getInstance() {
		if (instance == null) {
			instance = new HttpUtils();
		}
		return instance;
	}

	/**
	 * 区域平台请求分发
	 * 
	 * @param httpUrl
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public String httpPost(String httpUrl, String json) throws Exception {
		logger.info("Post请求httpUrl:" + httpUrl);
		
		//使用google中guava中的stopwatch 来记录执行时间
        Stopwatch stopwatch = Stopwatch.createStarted();
		String responseResult = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();
			// 设置请求和传输超时
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			StringEntity entity = new StringEntity(json, ContentType.create("application/json", Consts.UTF_8));

			// 采用post方式提交
			HttpPost httpPost = new HttpPost(httpUrl);
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			responseResult = EntityUtils.toString(resEntity, "utf-8");
			httpPost.releaseConnection();
		} catch (Throwable e) {
			logger.error("调用接口出错：" + e);
		} finally {
			if(httpClient != null){
				httpClient.close();
			}
		}
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Http请求耗时==>：" + millis);
		return responseResult;
	}

	/**
	 * 使用http get 方法请求，结果返回字符串
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String httpGet(String url) throws Exception {
		logger.info("Get请求httpUrl:" + url);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseResult = null;
		try {
			// get请求
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();

			// 发送请求
			httpGet.setConfig(requestConfig);
			HttpResponse httpresponse = httpClient.execute(httpGet);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			responseResult = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			logger.error("调用接口出错：" + e);
		} finally {
			if(httpClient != null){
				httpClient.close();
			}
		}
		return responseResult;
	}
	
	/**
	 * 区域平台请求分发
	 * 
	 * @param httpUrl
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public String httpPost(String httpUrl, Map<String,String> map) throws Exception {
		logger.info("Post请求httpUrl:" + httpUrl);
		
		//使用google中guava中的stopwatch 来记录执行时间
        Stopwatch stopwatch = Stopwatch.createStarted();
		String responseResult = null;
		CloseableHttpClient httpClient = null;
		List<BasicNameValuePair> params;
		try {
			httpClient = createSSLInsecureClient();
			// 设置请求和传输超时
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			if(map != null){
				params = new ArrayList<BasicNameValuePair>();
				for(String key:map.keySet()){
					params.add(new BasicNameValuePair(key, map.get(key)));
				}
			}
			else{
				return "请求content为空";
			}
			// 采用post方式提交
			HttpPost httpPost = new HttpPost(httpUrl);
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (null != resEntity) {
				responseResult = EntityUtils.toString(resEntity, "UTF-8");
			}
			if (response.getStatusLine().getStatusCode() != 200) {
				responseResult += "返回状态异常:";
				logger.error("调用接口错误--statusCode:"+response.getStatusLine().getStatusCode()+"\r\n"+responseResult);
				logger.error(responseResult+"\r\n"+JsonUtils.ojbTojson(map));
			}
			httpPost.releaseConnection();
		} catch (Throwable e) {
			logger.error("调用接口出错：" + e);
			responseResult = "请求异常错误";
		} finally {
			if(httpClient != null){
				httpClient.close();
			}
		}
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Http请求耗时==>：" + millis);
		return responseResult;
	}
	
	/**
     * 访问https的网站
     */
	private static CloseableHttpClient createSSLInsecureClient() {
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
            return httpclient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

	public static void main(String[] args) {

		String url = "http://test.wuhaneduyun.cn:13022/aamty/allAccount/queryPlatformInfo";
		HttpUtils httpClientUtils = HttpUtils.getInstance();
		Map<String,String> map = new HashMap<String,String>();
		map.put("platformCode", "420100");
		try {
			String result = httpClientUtils.httpPost(url, JsonUtils.ojbTojson(map));
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
