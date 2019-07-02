package com.example.demo.baiduAI;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明 百度AI 工具类调用
 */
@Component
public class BaiAIUtils {

    @Autowired
    CarRecognizeApi carRecognizeApi;

    /**
     * 车辆识别
     * @param imgBytes
     * @return {"score":0.9970504641532898,"color_result":"香槟色","year":"2013-2016","car_number":"京Q951A0","name":"别克君越"}
     */
    public JSONObject recogCar(byte[] imgBytes) {
        JSONObject result = new JSONObject();
        try {
            JSONObject carLicense = carRecognizeApi.licensePlate(imgBytes);//车牌信息
            JSONObject carInfo = carRecognizeApi.carProfile(imgBytes);//车型信息
            if (carLicense != null) {
                JSONObject jsonObject = carLicense.getJSONObject("words_result");
                if (jsonObject != null) {
                    String carNumber = jsonObject.getString("number");
                    result.put("car_number", carNumber);
                }
            }
            if (carInfo != null) {
                result.putAll(carInfo);
            }
            return result;
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
}
