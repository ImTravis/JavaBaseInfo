package com.example.demo.baiduAI;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.redis.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明:百度AI API
 */
@Component
public class CarRecognizeApi {

    @Value("${baidu.appId}")
    private String appId;

    //百度云官网获取的 API Key
    @Value("${baidu.apiKey}")
    private String apiKey;

    //百度云官网获取的 Securet Key
    @Value("${baidu.secretKey}")
    private String secretKey;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * accessToken缓存的 Key
     */
    private final String ACCESS_TOKEN_SAULT="ACCESS_TOKEN";

    private final long ACCESS_TOKEN_EXPIRE_TIME=60*60*60*29;//29天

    private final String GET_AUTH_URL="https://aip.baidubce.com/oauth/2.0/token?";

    private final String RECOG_LICENSE_PLATE="https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";

    private final String RECOG_CAR="https://aip.baidubce.com/rest/2.0/image-classify/v1/car";

    /**
     * 从Redis 获取 accessToken,缓存中不存在，则重新发送请求获取
     */
    public String getCacheAuth() {
        if(redisUtil.hasKey(ACCESS_TOKEN_SAULT)){
            return String.valueOf(redisUtil.get(ACCESS_TOKEN_SAULT));
        }else{
            return this.getAccessToken();
        }
    }

    /**
     * 百度AI 获取accessToken
     * @return accessToken
     */
    public String getAccessToken(){
        String accessToken=null;
        // 获取token地址
        String getAccessTokenUrl = GET_AUTH_URL
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + apiKey
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + secretKey;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            accessToken = jsonObject.getString("access_token");

            //该token有一定的有效期，需要自行管理，当失效时需重新获取.(百度API ，Access Token的有效期(秒为单位，一般为1个月)；)
            redisUtil.set(ACCESS_TOKEN_SAULT,accessToken,ACCESS_TOKEN_EXPIRE_TIME);
            return accessToken;

        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
            return accessToken;
        }
    }

    /**
     * 车牌识别
     * @param filePath ,multiDetect（"true"或"false"）
     *  multiDetect
     *  是否检测多张车牌，默认为false，当置为true的时候可以对一张图片内的多张车牌进行识别
     * @return
     */
    public JSONObject licensePlate(String filePath, String multiDetect) {
        JSONObject jsonObject = null;
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            /**
             * multi_detect 是否检测多张车牌，默认为false，当置为true的时候可以对一张图片内的多张车牌进行识别
             */
            String param = "image=" + imgParam + "&multi_detect="+multiDetect;

            String accessToken = getCacheAuth();
            String result = HttpUtil.post(RECOG_LICENSE_PLATE,accessToken,param);
            jsonObject = JSONObject.parseObject(result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return jsonObject;
        }
    }

    /**
     * 车牌识别
     * @param imgData 图片字节
     * @return
     */
    public JSONObject licensePlate(byte[] imgData){
        JSONObject jsonObject = null;
        try {
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            /**
             * multi_detect 是否检测多张车牌，默认为false，当置为true的时候可以对一张图片内的多张车牌进行识别
             */
            String param = "image=" + imgParam + "&multi_detect=false";

            String accessToken = getCacheAuth();
            String result = HttpUtil.post(RECOG_LICENSE_PLATE,accessToken,param);
            jsonObject = JSONObject.parseObject(result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return jsonObject;
        }
    }

    /**
     * 车牌识别
     * @param filePath
     * @return
     */
    public JSONObject licensePlate(String filePath){
        return this.licensePlate(filePath,"false");
    }


    /**
     * ---------------------------------------------车型识别----------------------------------------
     */

    /**
     * 车型识别
     * @param imgData 文件字节数 file.getBytes()
     * @return
     */
    public JSONObject carProfile(byte[] imgData) {
        JSONObject jsonObject;
        try {
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            /**
             * top_num 返回预测得分top结果数，如果为空或小于等于0默认为5；如果大于20默认20
             * baike_num 返回百科信息的结果数，默认值为0，不返回；2为返回前2个结果的百科信息，以此类推。
             */
            String param = "image=" + imgParam + "&top_num=" + 5+"&baike_num="+0;

            String accessToken = getCacheAuth();
            String result = HttpUtil.post(RECOG_CAR,accessToken,param);

            jsonObject = JSONObject.parseObject(result);
            JSONArray carInfos = jsonObject.getJSONArray("result");
//            List<JSONObject> list = JSONObject.parseArray(carInfos.toJSONString(),JSONObject.class);
            if(CollectionUtils.isNotEmpty(carInfos)){
                JSONObject carInfo = (JSONObject)carInfos.get(0);
                carInfo.put("color_result",jsonObject.getString("color_result"));
                return carInfo;//返回可能性最大的
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 车型识别
     * @param filePath  文件路径
     * @return
     */
    public JSONObject carProfile(String filePath){
        try{
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            return this.carProfile(imgData);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        String json="[{\"log_id\": 5560710411233945786, \"words_result\": {\"color\": \"blue\", \"number\": \"冀E5942Z\", \"probability\": [0.9999995231628418, 0.9999960660934448, 0.999994158744812, 0.9999933242797852, 0.9999858140945435, 0.9998863935470581, 0.9998308420181274], \"vertexes_location\": [{\"y\": 68, \"x\": 185}, {\"y\": 70, \"x\": 743}, {\"y\": 238, \"x\": 741}, {\"y\": 236, \"x\": 184}]}},{\"log_id\": 5560710411233945786, \"words_result\": {\"color\": \"blue\", \"number\": \"冀E5942Z\", \"probability\": [0.9999995231628418, 0.9999960660934448, 0.999994158744812, 0.9999933242797852, 0.9999858140945435, 0.9998863935470581, 0.9998308420181274], \"vertexes_location\": [{\"y\": 68, \"x\": 185}, {\"y\": 70, \"x\": 743}, {\"y\": 238, \"x\": 741}, {\"y\": 236, \"x\": 184}]}}]";
        JSONArray jsonArray = JSONObject.parseArray(json);
        String a = "1.644965414016042e-05";
        System.out.print("\n"+scientificNotation2String(Double.valueOf(a),8));

    }
    public static String scientificNotation2String(Double d, int newValue) {
        String value = null;
        NumberFormat nf = NumberFormat.getInstance();
        // 设置此格式中不使用分组
        nf.setGroupingUsed(false);
        // 设置数的小数部分所允许的最大位数。
        nf.setMaximumFractionDigits(newValue);
        value = nf.format(d);
        return value;
    }



}
