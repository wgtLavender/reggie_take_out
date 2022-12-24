//package com.queen.utils;
//
//import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
//import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.DefaultProfile;
//
///**
// * 短信发送工具类
// */
//public class SMSUtils {
//
//	/**
//	 * 发送短信
//	 * @param signName 签名
//	 * @param templateCode 模板
//	 * @param phoneNumbers 手机号
//	 * @param param 参数
//	 */
//	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
//		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
//		IAcsClient client = new DefaultAcsClient(profile);
//
//		SendSmsRequest sendSmsRequest = new SendSmsRequest();
////		request.setSysRegionId("cn-hangzhou");
//		sendSmsRequest.setPhoneNumbers(phoneNumbers);
//		sendSmsRequest.setSignName(signName);
//		sendSmsRequest.setTemplateCode(templateCode);
//		sendSmsRequest.setTemplateParam("{\"code\":\""+param+"\"}");
//		try {
//			client.getAcsResponse(sendSmsRequest);
//			System.out.println("短信发送成功");
//		}catch (ClientException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
