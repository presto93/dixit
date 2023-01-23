package com.yoon.dixit.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum Step {
    WAITING(0),
    READY(1),
    DISTRIBUTION(2),
    BET(3),
    SELECTION(4),
    SCORING(5);

    private final int order;

    private final static Map<Integer, Step> byOrderMap = new HashMap<>();

    static {
        for (Step value : Step.values()) {
            byOrderMap.put(value.order, value);
        }
    }

    public Step next() {
        return byOrderMap.get((this.order + 1) % values().length);
    }
}