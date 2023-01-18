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

        log.info(session + " 클라이언트 접속");

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
        log.info(session + " 클라이언트 접속 해제");
        webSocketSessions.remove(session);
    }

    private TextMessage getLoggedOutMessage() throws JsonProcessingException {

        System.out.println("!!!! getLoggedOutMessage !!!!");

        if (loggedOutMessage == null) {
            loggedOutMessage = new TextMessage(objectMapper.writeValueAsString(EMPTY_USER));
        }
        return loggedOutMessage;
    }

}
