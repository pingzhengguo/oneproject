package com.pzg.code.login.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class MD5SignUtil {

    public static void main(String[] args) throws UnsupportedEncodingException {
//        HashMap<String, String> map=new HashMap<String, String>();
//        map.put("name", "小明");
//        map.put("age", "12");
//        map.put("sex", "男");
//        map.put("school", "xxx中学");
//        map.put("address", "xxx小区");
//        /***MD5签名与验签**/
//        String key="rA8ksaXKErbkzWKXrchqc2AgtUSJnt";
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
//        String sign = sign(map,key,date);//生成签名
//        map.put("timestamp", date);
//        Map<String, Object> verify = verify(map,key,sign);//校验签名
//        System.out.println("result="+verify.get("result")+"/messege="+verify.get("messege"));
        System.out.println(UUIDUtils.create());
        System.out.println(DigestUtils.md5Hex("111111".getBytes("UTF-8")));
    }


    /**
     * 方法描述:将字符串MD5加码 生成32位md5码
     *
     * @author leon 2016年10月10日 下午3:02:30
     * @param inStr
     * @return
     */
    public static String md5(String inStr) {
        try {
            return DigestUtils.md5Hex(inStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }
    }

    /**
     * 方法描述:签名字符串
     *
     * @author leon 2016年10月10日 下午2:54:47
     * @param params 需要签名的参数
     * @param appSecret 签名密钥
     * @return
     */
    public static String sign(HashMap<String, String> params, String appSecret,String timestamp) {
        // 将map中的value等于null的 替换成""
        HashMap<String, String> map = new HashMap<String, String>();
        for (String key : params.keySet()) {
            String val = params.get(key);
            map.put(key, val == null?"":val);
        }

        StringBuilder valueSb = new StringBuilder();
        map.put("appSecret", appSecret);
        map.put("timestamp", timestamp);
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(map);
        Set<Entry<String, String>> entrys = sortParams.entrySet();
        // 遍历排序的字典,并拼接value1+value2......格式
        for (Entry<String, String> entry : entrys) {
            valueSb.append(entry.getValue());
        }
        params.remove("appSecret");
        return md5(valueSb.toString());
    }

    /**
     * 方法描述:验证签名
     *
     * @author leon 2016年10月10日 下午2:31:23
     * @param appSecret 加密秘钥
     * @return
     * @throws Exception
     */
    public static Map<String, Object> verify(HashMap<String, String> params, String appSecret, String sign){
        // 将map中的value等于null的 替换成""
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        for (String key : params.keySet()) {
            String val = params.get(key);
            paramsMap.put(key, val == null?"":val);
        }

        Map<String,Object> map =new HashMap<String,Object>();
        String time = paramsMap.get("timestamp");

        Date dt=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date timestamp = null;
        try {
            timestamp = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("messege", "时间戳转换异常");
            return map;
        }
        if((dt.getTime()-timestamp.getTime())/60000>5){//验证时间戳是否超过五分钟
            map.put("result", false);
            map.put("messege", "时间戳超过五分钟");
            return map;
        }

        paramsMap.put("appSecret", appSecret);

        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(paramsMap);
        Set<Entry<String, String>> entrys = sortParams.entrySet();

        // 遍历排序的字典,并拼接value1+value2......格式
        StringBuilder valueSb = new StringBuilder();
        for (Entry<String, String> entry : entrys) {
            valueSb.append(entry.getValue());
        }

        String mysign = md5(valueSb.toString());
        if (mysign.equals(sign)) {
            map.put("result", true);
            map.put("messege", "签名验证成功");
            return map;
        } else {
            map.put("result", false);
            map.put("messege", "签名验证失败");
            return map;
        }

    }

}
