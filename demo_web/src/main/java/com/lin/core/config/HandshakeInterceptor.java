package com.lin.core.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

   @Override
   public boolean beforeHandshake(ServerHttpRequest request,
                                  ServerHttpResponse response, WebSocketHandler wsHandler,
                                  Map<String, Object> attributes) throws Exception {
      System.out.println("Before Handshake");
      ServletServerHttpRequest request1 = (ServletServerHttpRequest)request;
      String id = request1.getServletRequest().getSession().getId();
      System.out.println("session_id"+id);
      attributes.put("session_id",id);
      return super.beforeHandshake(request, response, wsHandler, attributes);
   }

   @Override
   public void afterHandshake(ServerHttpRequest request,
         ServerHttpResponse response, WebSocketHandler wsHandler,
         Exception ex) {
      System.out.println("After Handshake");
      super.afterHandshake(request, response, wsHandler, ex);
   }

}
