package com.yoon.dixit.play;

import com.yoon.dixit.play.dto.ShuffleResultDto;
import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController("/play")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PlayController {

    private final PlayService playService;

    private final UserService userService;


    @GetMapping("/start")
    public void start() {
        playService.initGame();
    }

    @GetMapping("/card")
    public ShuffleResultDto getCards(@RequestParam String userId) {

        return ShuffleResultDto.builder()
                .cards(playService.getCards())
                .first(userService.isFirst(userId))
                .build();
    }

    @GetMapping("/select-target")
    public void selectTargetCard(@RequestParam int cardId) {
        playService.arrangeGame();
        playService.selectTargetCard(cardId);
    }

    @GetMapping("/select")
    public void selectCard(@RequestParam int cardId) {
        playService.selectCard(cardId);
    }

    @GetMapping("/finish")
    public Card finishGame() {
        return playService.finishGame();
    }
}
