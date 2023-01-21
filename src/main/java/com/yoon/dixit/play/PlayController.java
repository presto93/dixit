package com.yoon.dixit.play;

import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.enums.PlayingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/play")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlayController {

    private final PlayService playService;

    @PutMapping("/ready/{isReady}")
    public PlayingStatus ready(@RequestParam String userId, @PathVariable Boolean isReady) {
        return playService.changeReadyStatus(userId, isReady);
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
    public PlayingStatus selectCard(@RequestParam String userId, @RequestParam int cardId) {
        return playService.selectCard(userId, cardId);
    }

    @GetMapping("/finish")
    public Card finishGame() {
        return playService.finishGame();
    }
}
