package com.pzg.code.login.http;

import com.alibaba.fastjson.JSON;
import com.pzg.code.commons.utils.CustomException;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 调用第三方的Http请求
 * @Description:
 * @version
 * @ModifiedBy:
 */
public class HttpCoon {
    /**
     * 接口调用 GET
     * @param urlStr
     * @return
     */
    public static String httpURLConnectionGET(String urlStr,Map<String,Object> paramMap) throws CustomException {
        HttpURLConnection connection=null;
        BufferedReader br = null;
        try {
            if (paramMap!=null){
                // 将参数输出到连接
                StringBuffer param = new StringBuffer();
                for (Object key : paramMap.keySet()) {
                    Object result = paramMap.get(key);
                    if(key!=null && result!=null){
                        param.append(key.toString());
                        param.append("=");
                        param.append(URLEncoder.encode(result.toString(), "UTF-8"));
                        param.append("&");
                    }
                }
                if (param.length()>1){
                    param.delete(param.length()-1,param.length());
                }
                urlStr=urlStr+"?"+param;
            }
            // 把字符串转换为URL请求地址
            URL url = new URL(urlStr);
            // 打开连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(4000);
            // 连接会话
            connection.connect();
            // 获取输入流
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            // 循环读取流
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("远程GET连接调用失败");
        }finally {
            // 关闭流
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new CustomException("流关闭异常");
                }
            }
            // 断开连接
            if(connection!=null){
                connection.disconnect();
            }
        }
    }

    /**
     * 接口调用  POST
     * @param urlStr
     * @param paramMap
     * @return
     */
    public static String httpURLConnectionPOST (String urlStr,Map<String,Object> paramMap) throws CustomException {
        HttpURLConnection connection=null;
        DataOutputStream dataout = null;
        BufferedReader bf = null;
        try {
            URL url = new URL(urlStr);
            // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 此时cnnection只是为一个连接对象,待连接中

            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);

            // 设置连接输入流为true
            connection.setDoInput(true);

            // 设置请求方式为post
            connection.setRequestMethod("POST");

            // post请求缓存设为false
            connection.setUseCaches(false);

            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);

            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
            // ;charset=utf-8 必须要，不然妙兜那边会出现乱码【★★★★★】x-www-form-urlencoded
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();

            if(paramMap!=null){
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            dataout = new DataOutputStream(connection.getOutputStream());
                // 将参数输出到连接
                StringBuffer param = new StringBuffer();
                for (Object key : paramMap.keySet()) {
                    Object result = paramMap.get(key);
                    if(key!=null && result!=null){
                        param.append(key.toString());
                        param.append("=");
                        param.append(URLEncoder.encode(result.toString(), "UTF-8"));
                        param.append("&");
                    }
                }
                if (param.length()>1){
                    param.delete(param.length()-1,param.length());
                }


            dataout.writeBytes(param.toString());
            // 输出完成后刷新并关闭流
            dataout.flush();
            }
            // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
            bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            // 用来存储响应数据
            StringBuilder sb = new StringBuilder();
            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("远程POST连接调用失败");
        }
        finally {
            try {
            //关闭流
                if(dataout!=null){
                    dataout.close();
                }
                if(bf!=null){
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomException("流关闭异常");
            }
            if(connection!=null){
                // 销毁连接
                connection.disconnect();
            }
        }
    }

    /**
     * 接口调用  POST
     * @param urlStr
     * @param paramMap
     * @return
     */
    public static String httpURLConnectionPOSTTimeOut(String urlStr,Map<String,Object> paramMap) throws CustomException {
        HttpURLConnection connection=null;
        DataOutputStream dataout = null;
        BufferedReader bf = null;
        try {
            URL url = new URL(urlStr);
            // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(50000);
            // 此时cnnection只是为一个连接对象,待连接中

            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);

            // 设置连接输入流为true
            connection.setDoInput(true);

            // 设置请求方式为post
            connection.setRequestMethod("POST");

            // post请求缓存设为false
            connection.setUseCaches(false);

            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);

            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
            // ;charset=utf-8 必须要，不然妙兜那边会出现乱码【★★★★★】x-www-form-urlencoded
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();

            if(paramMap!=null){
                // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
                dataout = new DataOutputStream(connection.getOutputStream());
                // 将参数输出到连接
                StringBuffer param = new StringBuffer();
                for (Object key : paramMap.keySet()) {
                    Object result = paramMap.get(key);
                    if(key!=null && result!=null){
                        param.append(key.toString());
                        param.append("=");
                        param.append(URLEncoder.encode(result.toString(), "UTF-8"));
                        param.append("&");
                    }
                }
                if (param.length()>1){
                    param.delete(param.length()-1,param.length());
                }


                dataout.writeBytes(param.toString());
                // 输出完成后刷新并关闭流
                dataout.flush();
            }
            // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
            bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            // 用来存储响应数据
            StringBuilder sb = new StringBuilder();
            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("远程POST连接调用失败");
        }
        finally {
            try {
                //关闭流
                if(dataout!=null){
                    dataout.close();
                }
                if(bf!=null){
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomException("流关闭异常");
            }
            if(connection!=null){
                // 销毁连接
                connection.disconnect();
            }
        }
    }

    /**
     * 接口调用  POST
     * @param urlStr
     * @param paramMap
     * @return
     */
    public static String httpURLConnectionPOSTJSON (String urlStr,Map<String,Object> paramMap) throws CustomException {
        HttpURLConnection connection=null;
        DataOutputStream dataout = null;
        BufferedReader bf = null;
        try {
            URL url = new URL(urlStr);

            // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(4000);
            // 此时cnnection只是为一个连接对象,待连接中

            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);

            // 设置连接输入流为true
            connection.setDoInput(true);

            // 设置请求方式为post
            connection.setRequestMethod("POST");

            // post请求缓存设为false
            connection.setUseCaches(false);

            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);

            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
            // ;charset=utf-8 必须要，不然妙兜那边会出现乱码【★★★★★】
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setRequestProperty("Authorization-Type", "Basic YWRtaW46YWRtaW4=");

            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();

            if(paramMap!=null){
                // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
                dataout = new DataOutputStream(connection.getOutputStream());
                dataout.writeBytes(JSON.toJSONString(paramMap));
                // 输出完成后刷新并关闭流
                dataout.flush();
            }
            // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
            bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            // 用来存储响应数据
            StringBuilder sb = new StringBuilder();
            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("远程POST连接调用失败");
        }
        finally {
            try {
                //关闭流
                if(dataout!=null){
                    dataout.close();
                }
                if(bf!=null){
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomException("流关闭异常");
            }
            if(connection!=null){
                // 销毁连接
                connection.disconnect();
            }
        }
    }

    /**
     * 国路安消息通道接口调用，获得共享交换日志信息
     * @param urlStr
     * @return
     */
    public static String httpURLConnectionPOSTLogin (String urlStr,String key,String auth) throws IOException, CustomException {
        String result = null;
        HttpClient httpClient = new HttpClient();
        //需要验证
        //设置http头
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("X-Zato-PubSub-Key", key));
        headers.add(new Header("Authorization", auth));
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        PostMethod method = new PostMethod(urlStr);
        method.setDoAuthentication(true);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                throw new CustomException(method.getStatusLine()+"");
            } else {
                result = new String(method.getResponseBody(), "utf-8");
            }
        } finally {
            method.releaseConnection();
        }
        return result;
    }
}
