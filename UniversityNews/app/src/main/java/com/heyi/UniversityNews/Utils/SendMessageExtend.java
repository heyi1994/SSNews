package com.heyi.UniversityNews.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by heyi on 2016/11/29.
 */

public class SendMessageExtend {
    private static final String SIGN_METHOD_MD5 = "md5";
    private static final String SIGN_METHOD_HMAC = "hmac";
    private static final String CHARSET_UTF8 = "utf-8";
    private static final String CONTENT_ENCODING_GZIP = "gzip";

    private static final String serverUrl = "http://gw.api.taobao.com/router/rest";
    private static final String appKey = "23549560";  //你的appkey
    private static final String appSecret = "dd2885e51ba4575b778b58f262e913e9"; //你的appSecret
    private static final String sessionKey = "";
    //业务参数
    private static String extend = "";
    private static String sms_type = "normal";
    private static String sms_free_sign_name = "阿里大鱼";
    private static String sms_param = "";
    private static String rec_num = "";
    private static String sms_template_code = "";

    /**
     * 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。
     * 群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。示例：18600000000,13911111111,13322222222
     * 是否必须：必须
     * @param rec_num
     */
    public static void setRecNum(String rec_num) {
        SendMessageExtend.rec_num = rec_num;
    }

    /**
     * 短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
     * 是否必须：必须
     * @param sms_template_code
     */
    public static void setSmsTemplateCode(String sms_template_code) {
        SendMessageExtend.sms_template_code = sms_template_code;
    }

    /**
     * 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
     * 示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，
     * 传参时需传入{"code":"1234","product":"alidayu"}
     * 是否必须：可选
     * @param sms_param
     */
    public static void setSmsParam(String NickName,String number) {
        SendMessageExtend.sms_param = "{\"name\":"+"\""+NickName+"\","+"\"code\":"+"\""+number+"\"}";
    }

    /**
     * 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。如“阿里大鱼”已在短信签名管理中通过审核，
     * 则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
     * 是否必须：必须
     * @param sms_free_sign_name
     */
    public static void setSmsFreeSignName(String sms_free_sign_name) {
        SendMessageExtend.sms_free_sign_name = sms_free_sign_name;
    }

    /**
     * 公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，在消息返回时，
     * 该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
     * 是否必须：可选
     * @param extend
     */
    public static void setExtend(String extend) {
        SendMessageExtend.extend = extend;
    }





    public static String SendMsg() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        // 公共参数
        params.put("method", "alibaba.aliqin.fc.sms.num.send");
        params.put("app_key", appKey);
        params.put("session", sessionKey);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        params.put("timestamp", df.format(new Date()));
        params.put("format", "json");
        params.put("v", "2.0");
        params.put("sign_method", "hmac");
        // 业务参数
        params.put("extend", extend);
        params.put("sms_type", sms_type);
        params.put("sms_free_sign_name", sms_free_sign_name);
        params.put("sms_param", sms_param);
        params.put("rec_num", rec_num);
        params.put("sms_template_code", sms_template_code);
        // 签名参数
        params.put("sign", signTopRequest(params, appSecret, SIGN_METHOD_HMAC));
        // 请用API
        return callApi(new URL(serverUrl), params);
    }







    private static String callApi(URL url, Map<String, String> params) throws IOException {
        String query = buildQuery(params, CHARSET_UTF8);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(CHARSET_UTF8);
        }

        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Host", url.getHost());
            conn.setRequestProperty("Accept", "text/xml,text/javascript");
            conn.setRequestProperty("User-Agent", "top-sdk-java");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET_UTF8);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (isNotEmpty(name) && isNotEmpty(value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }

    private static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        if (conn.getResponseCode() < 400) {
            String contentEncoding = conn.getContentEncoding();
            if (CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
                return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
            } else {
                return getStreamAsString(conn.getInputStream(), charset);
            }
        } else {// Client Error 4xx and Server Error 5xx
            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = CHARSET_UTF8;
        if (isNotEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (isNotEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }
        return charset;
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * 对TOP请求进行签名。
     */
    private static String signTopRequest(Map<String, String> params, String secret, String signMethod) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if (SIGN_METHOD_MD5.equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (isNotEmpty(key) && isNotEmpty(value)) {
                query.append(key).append(value);
            }
        }
        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if (SIGN_METHOD_HMAC.equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }
        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    /**
     * 把字节流转换为十六进制表示方式。
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
    /**
     * 对字节流进行HMAC_MD5摘要。
     */
    private static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    /**
     * 对字符串采用UTF-8编码后，用MD5进行摘要。
     */
    private static byte[] encryptMD5(String data) throws IOException {
        return encryptMD5(data.getBytes(CHARSET_UTF8));
    }
    /**
     * 对字节流进行MD5摘要。
     */
    private static byte[] encryptMD5(byte[] data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data);
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    private static boolean isNotEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return true;
            }
        }
        return false;
    }
}
