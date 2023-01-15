package com.yoon.dixit.play;

import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.enums.PlayingStatus;
import com.yoon.dixit.user.enums.ReadyStatus;
import com.yoon.dixit.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/play")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlayController {

    private final PlayService playService;

    private final UserService userService;


    @PutMapping("/ready")
    public ReadyStatus ready(@RequestParam String userId) {
        return playService.ready(userId);
    }

    @GetMapping("/status")
    public PlayingStatus getPlayingStatus() {
        return playService.getPlayingStatus();
    }

    @PostMapping("/start")
    public void startGame() {
        playService.startGame();
    }

    @GetMapping("/card")
    public List<Card> getCards() {

        return playService.getCards();
    }

    @PostMapping("/select")
    public void selectCard(@RequestParam String userId, @RequestParam int cardId) {
        playService.selectCard(userId, cardId);
    }

    @GetMapping("/finish")
    public Card finishGame() {
        return playService.finishGame();
    }
}
