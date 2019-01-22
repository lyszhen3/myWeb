package com.lin.core.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lys on 2019/1/21.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Component
public class MyHandler extends TextWebSocketHandler {
	List<WebSocketSession> webSocketSessions = new ArrayList<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		webSocketSessions.add(session);
//		super.afterConnectionEstablished(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		TextMessage returnMessage = new TextMessage(message.getPayload()
				+ " received at server");
		System.out.println("socketsession:"+session.getId());
		session.sendMessage(returnMessage);
	}


	public void sendMsgBySessionId(String id,String text){
		for (WebSocketSession webSocketSession : webSocketSessions) {

			String sessionId = (String)webSocketSession.getAttributes().get("session_id");
			if(id.equals(sessionId)){

				TextMessage textMessage = new TextMessage(text);
				try {
					webSocketSession.sendMessage(textMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
