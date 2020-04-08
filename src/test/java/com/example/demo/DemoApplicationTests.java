package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.FCMSendMessageDTO;
import com.example.demo.service.IFireBaseMessageService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	IFireBaseMessageService iFireBaseMessageService;
	
	@Test
	void contextLoads() {
		FCMSendMessageDTO dto = new FCMSendMessageDTO();
		dto.setTitle("testTitle");
		dto.setBody("testBody");
		dto.setTokens("fBxK5n2KTUerJEsidQQx12:APA91bGA3g7Epy9Hz-u0dh-6DBBMkWkn3TyUimWbquCEc7aE-sknu9yLBL_PBobXjmfPrK0m3gb2EwqbCBQwnROORZL-F6yPeRRF0BO7sy7-j84Opa69PsEOGkLNQXhnLy_3uAxjfJ8i");
		dto.setTopic("testTopic");
		iFireBaseMessageService.pushFireBase(dto);
		
	}

}
