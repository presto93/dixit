package com.yoon.dixit.play;

import com.yoon.dixit.play.vo.Card;
import com.yoon.dixit.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayService {
    private final UserService userService;


    private final LinkedList<Card> cards = new LinkedList<>();
    private final List<Card> selectedCard = new ArrayList<>();
    private Card targetCard = null;
    private final LinkedList<Integer> displayOrder = new LinkedList<>();

    private static boolean nowPlaying = false;

    private static final int MAX_NUMBER_OF_CARD = 6;
    private static final int TOTAL_NUMBER_OF_CARD = 50;

    // get cards from file
    // cards to immutable list

    public void initGame() {
        // for test
        cards.clear();
        for (int i = 0; i < TOTAL_NUMBER_OF_CARD; i++) {
            cards.add(Card.builder()
                    .id(i)
                    .build());
        }
        // for test

        Collections.shuffle(cards);
    }

    synchronized public boolean isNowPlaying() {
        return nowPlaying;
    }

    synchronized public List<Card> getCards() {

        List<Card> userCard = new ArrayList<>();

        for (int i = 0; i < MAX_NUMBER_OF_CARD; i++) {
            userCard.add(cards.remove());
        }

        return userCard;
    }

    public void arrangeGame() {
        displayOrder.clear();
        int playerCount = userService.getUserCount();

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
        return cards.stream()
                .filter(c -> c.getId() == cardId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid card " + cardId));
    }

    public void selectCard(int cardId) {
        selectCard(getCard(cardId));
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
}
