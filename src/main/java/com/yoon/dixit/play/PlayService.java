package com.yoon.dixit.play;

import com.yoon.dixit.exceptions.IllegalPlayingStatusException;
import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.enums.PlayingStatus;
import com.yoon.dixit.user.enums.ReadyStatus;
import com.yoon.dixit.user.service.UserService;
import com.yoon.dixit.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlayService {
    private final UserService userService;
    private final UsersService usersService;

    private static final List<Card> cards = new ArrayList<>();
    private static final LinkedList<Card> dummy = new LinkedList<>();

    private final List<Card> selectedCards = new ArrayList<>();
    private Card targetCard = null;
    private final LinkedList<Integer> displayOrder = new LinkedList<>();

    private static PlayingStatus playingStatus = PlayingStatus.WAITING;

    private static final int MAX_NUMBER_OF_CARD = 6;
    private static final int TOTAL_NUMBER_OF_CARD = 50;
    private static final int MIN_PAYER_COUNT = 3;

    // get cards from file
    // cards to immutable list

    public void initGame() {

        if (playingStatus != PlayingStatus.READY) {
            throw new IllegalPlayingStatusException(PlayingStatus.READY, playingStatus);
        }

        System.out.println("init game start!");

        System.out.println(cards);
        System.out.println(dummy);
        // for test
        if (CollectionUtils.isEmpty(cards)) {
            for (int i = 0; i < TOTAL_NUMBER_OF_CARD / 2; i++) {
                cards.add(Card.builder()
                        .filename(String.format("h-%d.png", i))
                        .id(i)
                        .build());
                cards.add(Card.builder()
                        .filename(String.format("v-%d.png", i))
                        .id(i)
                        .build());
            }
            dummy.addAll(cards);
        }
        // for test

        Collections.shuffle(dummy);
        dummy.addAll(selectedCards);
        System.out.println(cards);
        System.out.println(dummy);
        System.out.println("init game end!");
    }

    synchronized public List<Card> getCards() {

        if (playingStatus != PlayingStatus.CHECK_CARD) {
            throw new IllegalPlayingStatusException(PlayingStatus.READY, playingStatus);
        }

        List<Card> userCard = new ArrayList<>();

        for (int i = 0; i < MAX_NUMBER_OF_CARD; i++) {
            userCard.add(dummy.remove());
        }

        return userCard;
    }

    public void arrangeGame() {


        if (playingStatus != PlayingStatus.DISPLAY_CARD) {
            throw new IllegalPlayingStatusException(PlayingStatus.READY, playingStatus);
        }


        displayOrder.clear();
        int playerCount = usersService.getUserCount();

        for (int i = 0; i < playerCount; i++) {
            displayOrder.add(i);
        }

        Collections.shuffle(displayOrder);
    }

    public void selectTargetCard(int cardId) {

        if (playingStatus != PlayingStatus.CHECK_CARD) {
            throw new IllegalPlayingStatusException(PlayingStatus.READY, playingStatus);
        }

        targetCard = getCard(cardId);
        targetCard.setTarget(true);
        selectCard(targetCard);
    }

    private Card getCard(int cardId) {
        return cards.get(cardId);
    }

    public PlayingStatus selectCard(String userId, int cardId) {

        if (usersService.isLeader(userId)) {
            arrangeGame();
            selectTargetCard(cardId);

        } else {
            selectCard(getCard(cardId));
        }

        playingStatus = CollectionUtils.size(selectedCards) == usersService.getUserCount() ? PlayingStatus.SELECTED_CARD : PlayingStatus.CHECK_CARD;

        return playingStatus;
    }

    public void selectCard(Card card) {
        selectedCards.add(card);
    }

    public Card finishGame() {
        return getDisplayCard();
    }

    public Card getDisplayCard() {
        return selectedCards.get(displayOrder.remove());
    }

    public PlayingStatus changeReadyStatus(String userId, boolean isReady) {
        if (isReady) {
            userService.ready(userId);
        } else {
            userService.unready(userId);
        }

        if (usersService.getReadyStatus(MIN_PAYER_COUNT) == ReadyStatus.ALL) {
            playingStatus = PlayingStatus.READY;
            startGame();
        } else {
            playingStatus = PlayingStatus.WAITING;
        }

        return playingStatus;
    }

    public PlayingStatus getPlayingStatus() {
        return playingStatus;
    }

    public void startGame() {
        initGame();
        usersService.startGame();
        playingStatus = PlayingStatus.CHECK_CARD;
    }
}
