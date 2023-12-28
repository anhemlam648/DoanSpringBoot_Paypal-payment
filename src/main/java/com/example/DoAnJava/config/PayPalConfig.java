package com.example.DoAnJava.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayPalConfig {
    //truyền giá trị vào biến client từ application.properties
    @Value("${paypal.client.id}")
    private String clientId;
    //truyền giá trị vào biến secret từ application.properties
    @Value("${paypal.client.secret}")
    private String clientSecret;
    //truyền giá trị vào biến model từ application.properties
    @Value("${paypal.mode}")
    private String mode;

    //Đánh dấu phương thức paypalSdkConfig
    @Bean
    public Map paypalSdkConfig() {
        Map configMap = new HashMap<>(); // lưu trữ cấu hình
        configMap.put("mode", mode); // thêm khóa mode điều chỉnh cấu hình
        return configMap;
    }

    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        // Khởi tạo OAuthTokenCredential
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig()); //Phương thức này trả về một đối tượng của lớp OAuthTokenCredential chứa cấu hình paypalSdkConfig()
    }

    //PayPalRESTException ném ngoại lệ
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken()); //Tạo một đối tượng APIContext với AccessToken từ đối tượng OAuthTokenCredential.
        context.setConfigurationMap(paypalSdkConfig()); //Đặt cấu hình cho APIContext bằng cách sử dụng setConfigurationMap() và truyền vào đối tượng Map
        return context;
    }
}
