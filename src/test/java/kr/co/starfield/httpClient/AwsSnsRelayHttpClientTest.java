package kr.co.starfield.httpClient;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.starfield.rest.controller.BaseTest;

public class AwsSnsRelayHttpClientTest extends BaseTest {
	
	@Autowired
	AwsSnsRelayHttpClient awsSnsRelayHttpClient;

	@Test
	public void testPost() throws Exception {

		Map<String,Object> params = new HashMap<>();
		params.put("dvic_id", "04157df48a46a705_7432140858e246d0");
		params.put("push_token", "cjEV3c0uxj0:APA91bHJt-YHejHiQGlw1C2DAFy6nYeXWt_9z3m4QFTMaB7P63NIsfWglJDYJa2go7L2VITL5SpEkUq3r4MwEVRoOtLj8HAitYQkYXCsyOn75RtUnPr9R8tRfai9Vn1ldA0pnQOjyFWN");
		params.put("alram_yn", true);
		params.put("pltf_type", 2);
		
		System.out.println(awsSnsRelayHttpClient.post("/awssns/rest/endpoints", params));
		
	}
	
	@Test
	public void testPut() throws Exception {

		String endArn = "arn:aws:sns:ap-northeast-2:435370370756:endpoint/APNS_SANDBOX/starfieldpushserver_ios_dev/6b7c847c-afb1-3882-a819-ee53a5cfeff4";
		
		Map<String,Object> params = new HashMap<>();
		params.put("dvic_id", "04157df48a46a705_7432140858e246d0");
		params.put("push_token", "cjEV3c0uxj0:APA91bHJt-YHejHiQGlw1C2DAFy6nYeXWt_9z3m4QFTMaB7P63NIsfWglJDYJa2go7L2VITL5SpEkUq3r4MwEVRoOtLj8HAitYQkYXCsyOn75RtUnPr9R8tRfai9Vn1ldA0pnQOjyFWN");
		params.put("alram_yn", true);
		params.put("pltf_type", 2);
		params.put("isAlramYnChange", true);
		params.put("isPushTokenChange", true);
		
		System.out.println(awsSnsRelayHttpClient.put("/awssns/rest/endpoints/"+URLEncoder.encode(endArn, "UTF-8"), params));
		
	}
	
//	@Test
//	public void testDelete() throws Exception {
//
//		String endArn = "arn:aws:sns:ap-northeast-2:435370370756:endpoint/APNS_SANDBOX/starfieldpushserver_ios_dev/6b7c847c-afb1-3882-a819-ee53a5cfeff4";
//		
//		System.out.println(awsSnsRelayHttpClient.delete("/awssns/rest/endpoints/"+URLEncoder.encode(endArn, "UTF-8")));
//		
//	}

	@Override
	public void setup() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
