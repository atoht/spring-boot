package com.example.demo.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.ClassPathResource;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.google.firebase.messaging.TopicManagementResponse;

public class FireBaseUtil {

	//存放多个实例的Map
    private static Map<String, FirebaseApp> firebaseAppMap = new HashMap<>();
    //获取AndroidConfig.Builder对象
    private static com.google.firebase.messaging.AndroidConfig.Builder androidConfigBuilder=AndroidConfig.builder();
    //获取AndroidNotification.Builder对象
    private static AndroidNotification.Builder androidNotifiBuilder=AndroidNotification.builder();
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = { MESSAGING_SCOPE };

    /**
     * 判断SDK是否初始化
     * @param appName
     * @return
     */
    public static boolean isInit(String appName) {
        return firebaseAppMap.get(appName) != null;
    }

    /**
     * 初始化SDK
     * @param jsonPath      JSON路径
     * @param dataUrl       firebase数据库
     * @param appName       APP名字
     * @throws IOException
     */
    public static void initSDK(String jsonPath, String dataUrl,String appName) throws IOException {
//        InputStream serviceAccount = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsonPath);
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl(dataUrl)
//                .build();
        //初始化firebaseApp
//        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        
//        FileInputStream refreshToken = new FileInputStream(jsonPath);

        ClassPathResource cpr = new ClassPathResource(jsonPath);
        InputStream fileInputStream = cpr.getInputStream();
        	
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(fileInputStream))
            .setDatabaseUrl(dataUrl)
            .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        
        //存放
        firebaseAppMap.put(appName,firebaseApp);
    }

    /**
     * 单设备推送
     * @param appName      应用的名字
     * @param token        注册token
     * @param title        推送题目
     * @param bady         推送内容
     * @return
     * @throws IOException
     * @throws FirebaseMessagingException
     */
    public static void pushSingle(String appName, String token, String title, String body) throws IOException, FirebaseMessagingException{
//    	token = getAccessToken();
        //获取实例
        FirebaseApp firebaseApp = firebaseAppMap.get(appName);
        //实例为空的情况
        if (firebaseApp == null) {
            return;
        }
//        //构建消息内容
//        Message message = Message.builder().setNotification(new Notification(title,body))
//                .setToken(token)
//                .build();
//        //发送后，返回messageID
//        String response = FirebaseMessaging.getInstance().send(message);
//        System.out.println("单个设备推送成功 : "+response);
        
        List<String> registrationTokens = Arrays.asList(
        		"cUs4l7J0RfWnzQKnt9_dv1:APA91bGvMAfbh8JsSAJwco42wNVM2IMTLBLu6xIWlUn9uO_W4gh2puPeA3r26k1DUULyF1YCxZ36PE184ccRbtTZvh6q9DGwYYdmHqgFSETQOMfe7zZSbrk8fEyoMdhdHJooBW9WpF6W",
        		token,
        		"cbVm2t2_um2ZyymW4b1yw-:APA91bE-SyN6Fhf_9GKn-AMtY-HPdkOQciodaekCLSQyiTFvhUXPfS5T_UfmvxYcl9Yv-NdeaQ7MV2OElaHdhTuA5OCfJQwVXCe5vkDvtm10E11nBsbCkb6vIyvfcb0rdnaRUpSASF4t"
        	);
        
        List<Message> messages = Arrays.asList(
                Message.builder()
                    .setNotification(new Notification(title,body))
                    .setToken(registrationTokens.get(0))
                    .build(),
                // ...
                Message.builder()
                    .setNotification(new Notification(title,body))
                    .setToken(registrationTokens.get(1))
                    .build(),
                Message.builder()
                .setNotification(new Notification(title,body))
                .setToken(registrationTokens.get(2))
                .build()
            );

	     // See documentation on defining a message payload.
        MulticastMessage message = MulticastMessage.builder()
        		.setNotification(new Notification(title,body))
//        	    .putData("score", "850")
//        	    .putData("time", "2:45")
        	    .addAllTokens(registrationTokens)
        	    .build();
	
	     // Send a message to the device corresponding to the provided
	     // registration token.
	     BatchResponse response = FirebaseMessaging.getInstance(firebaseApp).sendAll(messages);
	     if (response.getFailureCount() > 0) {
	       List<SendResponse> responses = response.getResponses();
	       List<String> failedTokens = new ArrayList<>();
	       for (int i = 0; i < responses.size(); i++) {
	         if (!responses.get(i).isSuccessful()) {
	           // The order of responses corresponds to the order of the registration tokens.
	           failedTokens.add(registrationTokens.get(i));
	         }
	       }

	       System.out.println("List of tokens that caused failures: " + failedTokens);
	     }
	     // Response is a message ID string.
	     System.out.println("Successfully sent message: " + response);
    }

    /**
     * 给设备订阅主题
     * @param appName     应用的名字
     * @param tokens      设备的token,最大1000个
     * @param topic       要添加的主题
     * @return
     * @throws FirebaseMessagingException
     * @throws IOException
     */
    public static void registrationTopic(String appName, List<String> tokens, String topic) throws FirebaseMessagingException, IOException {
        //获取实例
        FirebaseApp firebaseApp = firebaseAppMap.get(appName);
        //实例不存在的情况
        if(firebaseApp == null) {
            return;
        }
        //订阅，返回主题管理结果对象。
        TopicManagementResponse response = FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(tokens, topic);
        System.out.println("添加设备主题，成功：" + response.getSuccessCount() + ",失败：" + response.getFailureCount());
    }
    /**
     * 按主题推送
     * @param appName      应用的名字
     * @param topic        主题的名字
     * @param title        消息题目
     * @param body         消息体
     * @return
     * @throws FirebaseMessagingException
     * @throws IOException
     */
    public static void sendTopicMes(String appName, String topic, String title, String body) throws FirebaseMessagingException, IOException {
        //获取实例
        FirebaseApp firebaseApp = firebaseAppMap.get(appName);
        //实例不存在的情况
        if(firebaseApp == null) {
            return;
        }
        //构建消息
        Message message = Message.builder()
                .setNotification(new Notification(title,body))
                .setTopic(topic)
                .build();
        //发送后，返回messageID
        String response = FirebaseMessaging.getInstance(firebaseApp).send(message);
        System.out.println("主题推送成功: " + response);
    }
    
    
//    public void pushMessage(ConsumerRecord<Long, String> consumerRecord) {
//        try {
//            PushMessageEvent pushMessageEvent = JSON.parseObject(consumerRecord.value(), PushMessageEvent.class);
//            String topic = "";
//            if (null != pushMessageEvent && null != pushMessageEvent.getUserId()) {
//                topic = "9";
//                ApsAlert apsAlert = ApsAlert.builder() //ios格式
//                        .setTitle(pushMessageEvent.getTitle())
//                        .setBody(pushMessageEvent.getContent())
//                        .setLaunchImage(pushMessageEvent.getImage())
//                        .build();
//                AndroidNotification androidNotification = AndroidNotification.builder() //andorid格式
//                        .setTitle(pushMessageEvent.getTitle())
//                        .setBody(pushMessageEvent.getContent())
//                        .setIcon(pushMessageEvent.getImage())
//
//                        .build();
//
//                Message message = Message.builder()
//                        .setApnsConfig(ApnsConfig.builder()
//                                .putHeader("apns-priority", "10")
//                                .setAps(Aps.builder()
//                                        .setAlert(apsAlert)
//                                        .setBadge(1)
//                                        .build())
//                                .build())
//                        .setAndroidConfig(AndroidConfig.builder().setNotification(androidNotification).build())
//                        .putData("type",pushMessageEvent.getPushType().toString())
//                        .setTopic(topic)
//                        .build();
//
//                String response = FirebaseMessaging.getInstance().send(message);
//                System.out.println("Successfully sent message: " + response);
//            }
//
//        } catch (Exception e) {
//            log.error("exception:", e);
//        }

//    }
}
