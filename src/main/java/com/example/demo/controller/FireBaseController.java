package com.example.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FCMSendMessageDTO;
import com.example.demo.util.FireBaseUtil;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@RestController
@Slf4j
public class FireBaseController {

	//渠道名字，也是APP的名字
    public static String appName = "FCM后台新增的项目名称";

    @PostMapping(value = "/pushFireBaseAll")
    public void pushFireBaseAll(@RequestBody FCMSendMessageDTO fcmSendMessageReqDTO) {
        log.info("进入批量FireBase推送 pushFireBaseAll:[{}]", fcmSendMessageReqDTO.toString());
        //添加tokens
        List<String> tokens = Arrays.asList(fcmSendMessageReqDTO.getTokens().split(","));
        //设置Java代理,端口号是代理软件开放的端口，这个很重要。
//        System.setProperty("proxyHost", "127.0.0.1");
//        System.setProperty("proxyPort", "8081");
        //如果FirebaseApp没有初始化
        if (!FireBaseUtil.isInit(appName)) {
            String jsonPath = "fcm/test01-c6e32-firebase-adminsdk-7916k-39f3d691a9.json";
            String dataUrl = "https://test01-c6e32.firebaseio.com";
            //初始化FirebaseApp
            try {
                FireBaseUtil.initSDK(jsonPath, dataUrl, appName);
                FireBaseUtil.registrationTopic(appName, tokens, fcmSendMessageReqDTO.getTopic());  //设置主题
                FireBaseUtil.sendTopicMes(appName, fcmSendMessageReqDTO.getTopic(), fcmSendMessageReqDTO.getTitle(), fcmSendMessageReqDTO.getBody());    //按主题推送
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("如果FirebaseApp已经初始化");
            try {
                FireBaseUtil.registrationTopic(appName, tokens, fcmSendMessageReqDTO.getTopic());  //设置主题
                FireBaseUtil.sendTopicMes(appName, fcmSendMessageReqDTO.getTopic(), fcmSendMessageReqDTO.getTitle(), fcmSendMessageReqDTO.getBody());    //按主题推送
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping(value = "/pushFireBase")
    public void pushFireBase(@RequestBody FCMSendMessageDTO fcmSendMessageReqDTO) {
        log.info("进入批量FireBase推送 pushFireBaseAll:[{}]", fcmSendMessageReqDTO.toString());
        //添加tokens
        List<String> tokens = Arrays.asList(fcmSendMessageReqDTO.getTokens().split(","));
        //设置Java代理,端口号是代理软件开放的端口，这个很重要。
//        System.setProperty("proxyHost", "127.0.0.1");
//        System.setProperty("proxyPort", "8081");
        //如果FirebaseApp没有初始化
        if (!FireBaseUtil.isInit(appName)) {
        	String jsonPath = "fcm/test01-c6e32-firebase-adminsdk-7916k-39f3d691a9.json";
            String dataUrl = "https://test01-c6e32.firebaseio.com";
            //初始化FirebaseApp
            try {
                FireBaseUtil.initSDK(jsonPath, dataUrl, appName);
                FireBaseUtil.pushSingle(appName, tokens.get(0), fcmSendMessageReqDTO.getTitle(), fcmSendMessageReqDTO.getBody());  //单推
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("如果FirebaseApp已经初始化");
            try {
                FireBaseUtil.pushSingle(appName, tokens.get(0), fcmSendMessageReqDTO.getTitle(), fcmSendMessageReqDTO.getBody());  //单推
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
