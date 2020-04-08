package com.example.demo.dto;

import lombok.Data;

@Data
/**
 * FCM推送消息请求实体
 * @author atoht
 *
 */
public class FCMSendMessageDTO {

	/** 消息标题 */
    private String title;
	/** 消息内容 */
    private String body;
    /** 用户token集合 */
    private String tokens;
    /** 主题 */
    private String topic;
}
