package com.yoon.dixit.play;

import com.yoon.dixit.play.dto.ShuffleResultDto;
import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/play")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlayController {

    private final PlayService playService;

    private final UserService userService;


    @GetMapping("/start")
    public void start() {
        System.out.println("start!!");
//        playService.initGame();
    }

    @GetMapping("/card")
    public ShuffleResultDto getCards(@RequestParam String userId) {

        return ShuffleResultDto.builder()
                .cards(playService.getCards())
                .first(userService.isLeader(userId))
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
