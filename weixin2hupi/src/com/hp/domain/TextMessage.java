package com.hp.domain;

public class TextMessage extends BaseMessage {

	private String Content;//文本内容
	private Long MsgId;//64位 消息ID
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Long getMsgId() {
		return MsgId;
	}
	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}

	
}
