package com.example.demo.service;

import com.example.demo.dto.FCMSendMessageDTO;

public interface IFireBaseMessageService {

	void pushFireBase(FCMSendMessageDTO fcmSendMessageReqDTO);

}