package com.yoon.dixit.socket.controller;

import com.yoon.dixit.socket.dto.SocketDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class SocketController {

    @MessageMapping("receive")
    @SendTo("/send")
    public SocketDto SocketHandler(SocketDto socketDto) {
        return socketDto;
    }
}
