package cn.jpush.api;

import java.util.Map;

import com.google.gson.Gson;

/**
 * The entrance of JPush API library.
 *
 */
public class JPushClient extends BaseClient {

	protected static HttpPostClient httpClient = new HttpPostClient();

	public JPushClient(String masterSecret, String appKey) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
	}
	
	public JPushClient(String masterSecret, String appKey, long timeToLive) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		this.timeToLive = timeToLive;
	}

	public JPushClient(String masterSecret, String appKey, DeviceEnum device) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		devices.add(device);
	}

	public JPushClient(String masterSecret, String appKey, long timeToLive, DeviceEnum device) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		this.timeToLive = timeToLive;
		this.devices.add(device);
	}

	/*
	 * @description 发�?�带IMEI的�?�知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithImei(String sendNo, String imei, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendNotification(p, sendNo, msgTitle, msgContent, 0, null);
	}

	/*
	 * @params builderId通知栏样�?
	 * @description 发�?�带IMEI的�?�知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithImei(String sendNo, String imei, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @description 发�?�带IMEI的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithImei(String sendNo, String imei, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, null, null);
	}

	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发�?�带IMEI的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithImei(String sendNo, String imei, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	/*
	 * @description 发�?�带TAG的�?�知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(String sendNo, String tag, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendNotification(p, sendNo, msgTitle, msgContent, 0, null);
	}

	/*
	 * @params builderId通知栏样�?
	 * @description 发�?�带TAG的�?�知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(String sendNo, String tag, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @description 发�?�带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(String sendNo, String tag, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, null, null);
	}

	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发�?�带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(String sendNo, String tag, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	/*
	 * @description 发�?�带ALIAS的�?�知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(String sendNo, String alias, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendNotification(p, sendNo, msgTitle, msgContent, 0, null);
	}

	/*
	 * @params builderId通知栏样�?
	 * @description 发�?�带ALIAS的�?�知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(String sendNo, String alias, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @description 发�?�带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(String sendNo, String alias, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, null, null);
	}

	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发�?�带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(String sendNo, String alias, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	/*
	 * @description 发�?�带AppKey的�?�知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(String sendNo, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendNotification(p, sendNo, msgTitle, msgContent, 0, null);
	}

	/*
	 * @params builderId通知栏样�?
	 * @description 发�?�带AppKey的�?�知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(String sendNo, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @description 发�?�带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(String sendNo, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, null, null);
	}

	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发�?�带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(String sendNo, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	protected MessageResult sendCustomMessage(CustomMessageParams p, String sendNo, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		if (null != msgContentType) {
			p.getMsgContent().setContentType(msgContentType);
		}
		if (null != extra) {
			p.getMsgContent().setExtra(extra);
		}
		return sendMessage(p, sendNo, msgTitle, msgContent);
	}

	protected MessageResult sendNotification(NotifyMessageParams p, String sendNo, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		p.getMsgContent().setBuilderId(builderId);
		if (null != extra) {
			p.getMsgContent().setExtra(extra);
		}
		return sendMessage(p, sendNo, msgTitle, msgContent);
	}

	protected MessageResult sendMessage(MessageParams p,String sendNo, String msgTitle, String msgContent) {
		p.setSendNo(sendNo);
		p.setAppKey(this.getAppKey());
		p.setMasterSecret(this.masterSecret);
		p.setTimeToLive(this.timeToLive);
		p.setSendDescription(this.getSendDescription());
		for (DeviceEnum device : this.getDevices()) {
			p.addPlatform(device);
		}
		if (null != msgTitle) {
			p.getMsgContent().setTitle(msgTitle);
		}
		p.getMsgContent().setMessage(msgContent);

		return sendMessage(p);
	}

	protected MessageResult sendMessage(MessageParams params) {
	
		
		return httpClient.post(BaseURL.ALL_PATH, this.enableSSL, params);
	}

}
