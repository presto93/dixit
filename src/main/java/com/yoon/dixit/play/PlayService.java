package com.yoon.dixit.play;

import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.enums.PlayingStatus;
import com.yoon.dixit.user.enums.ReadyStatus;
import com.yoon.dixit.user.service.UserService;
import com.yoon.dixit.user.service.UsersService;
import com.yoon.dixit.user.vo.User;
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

    private final List<Card> selectedCard = new ArrayList<>();
    private Card targetCard = null;
    private final LinkedList<Integer> displayOrder = new LinkedList<>();

    private static PlayingStatus playingStatus = PlayingStatus.WAITING;

    private static final int MAX_NUMBER_OF_CARD = 6;
    private static final int TOTAL_NUMBER_OF_CARD = 50;
    private static final int MIN_PAYER_COUNT = 3;

    // get cards from file
    // cards to immutable list

    public void initGame() {
        // for test
        if (CollectionUtils.isEmpty(cards)) {
            for (int i = 0; i < TOTAL_NUMBER_OF_CARD; i++) {
                cards.add(Card.builder()
                        .id(i)
                        .build());
            }
            dummy.addAll(cards);
        }
        // for test

        Collections.shuffle(dummy);
        dummy.addAll(selectedCard);
    }

    synchronized public List<Card> getCards() {

        List<Card> userCard = new ArrayList<>();

        for (int i = 0; i < MAX_NUMBER_OF_CARD; i++) {
            userCard.add(dummy.pollFirst());
        }

        return userCard;
    }

    public void arrangeGame() {
        displayOrder.clear();
        int playerCount = usersService.getUserCount();

        for (int i = 0; i < playerCount; i++) {
            displayOrder.add(i);
        }

        Collections.shuffle(displayOrder);
    }

    public void selectTargetCard(int cardId) {
        targetCard = getCard(cardId);
        targetCard.setTarget(true);
        selectCard(targetCard);
    }

    private Card getCard(int cardId) {
        return cards.get(cardId);
    }

    public void selectCard(String userId, int cardId) {

        if (usersService.isLeader(userId)) {
            arrangeGame();
            selectTargetCard(cardId);

        } else {

            selectCard(getCard(cardId));
        }
    }

    public void selectCard(Card card) {
        selectedCard.add(card);
    }

    public Card finishGame() {
        return getDisplayCard();
    }

    public Card getDisplayCard() {
        return selectedCard.get(displayOrder.remove());
    }

    public PlayingStatus changeReadyStatus(String userId, boolean isReady) {
        if (isReady) {
            userService.ready(userId);
        } else {
            userService.unready(userId);
        }

        playingStatus = usersService.getReadyStatus(MIN_PAYER_COUNT) == ReadyStatus.ALL ? PlayingStatus.READY : PlayingStatus.WAITING;

        if (playingStatus == PlayingStatus.READY) {
            startGame();
        }

        return playingStatus;
    }

    public PlayingStatus getPlayingStatus() {
        return playingStatus;
    }

    public void startGame() {
        initGame();
        usersService.startGame();
        playingStatus = PlayingStatus.PLAYING;
    }
}
