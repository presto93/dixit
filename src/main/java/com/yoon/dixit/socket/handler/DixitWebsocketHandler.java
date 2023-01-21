package com.yoon.dixit.socket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoon.dixit.socket.dto.SocketDto;
import com.yoon.dixit.user.dto.UserDto;
import com.yoon.dixit.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DixitWebsocketHandler extends TextWebSocketHandler {

    private static final List<WebSocketSession> webSocketSessions = new ArrayList<>();

    private final UsersService usersService;
    private final ObjectMapper objectMapper;

    private static final UserDto EMPTY_USER = UserDto.builder().build();
    private TextMessage loggedOutMessage;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = message.getPayload();
        System.out.println(userId + " sended message");

        SocketDto receivedMessage = objectMapper.readValue(message.getPayload(), SocketDto.class);

        if (!StringUtils.equals(receivedMessage.getAction(), "LOGOUT")) {
            receivedMessage.setLeader(usersService.isLeader(receivedMessage.getUserId()));
        }
        TextMessage responseMessage = new TextMessage(objectMapper.writeValueAsString(receivedMessage));

        for (WebSocketSession webSocketSession : webSocketSessions) {
            webSocketSession.sendMessage(responseMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
    }

    private TextMessage getLoggedOutMessage() throws JsonProcessingException {
        if (loggedOutMessage == null) {
            loggedOutMessage = new TextMessage(objectMapper.writeValueAsString(EMPTY_USER));
        }
        return loggedOutMessage;
    }

}

/*
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-0.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-1.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-2.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-3.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-4.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-5.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-6.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-7.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-8.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-9.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-10.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-11.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-12.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-13.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-14.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-15.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-16.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-17.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-18.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-19.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-20.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-21.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-22.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-23.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\h-24.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-0.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-1.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-2.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-3.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-4.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-5.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-6.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-7.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-8.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-9.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-10.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-11.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-12.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-13.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-14.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-15.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-16.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-17.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-18.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-19.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-20.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-21.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-22.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-23.png",
"C:\Users\USER\projects\dixit\src\main\resources\static\img\v-24.png",



// */
