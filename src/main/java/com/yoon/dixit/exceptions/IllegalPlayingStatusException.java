package com.yoon.dixit.exceptions;

import com.yoon.dixit.enums.Step;
import com.yoon.dixit.user.enums.PlayingStatus;

public class IllegalPlayingStatusException extends IllegalStateException {

    public IllegalPlayingStatusException(PlayingStatus expected, PlayingStatus actual) {
        super(String.format("Playing status '%s' is expected. but '%s'", expected.name(), actual.name()));
    }
    public IllegalPlayingStatusException(Step required, Step actual) {
        super(String.format("Playing status '%s' is expected. but '%s'", required.name(), actual.name()));
    }
}
