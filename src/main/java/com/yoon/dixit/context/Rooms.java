package com.yoon.dixit.context;

import com.yoon.dixit.room.vo.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Rooms {

    private final List<Room> rooms = new ArrayList<>();

    public void add(Room room) {
        rooms.add(room);
    }

    public void remove(int id) {
        remove(getOrThrow(id));
    }

    public void remove(Room room) {
        rooms.remove(room);
    }

    public Room getOrThrow(int id) {
        return get(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("Invalid room id '%d'.", id))
        );
    }

    public Optional<Room> get(int id) {
        return rooms.stream()
                .filter(room -> id == room.getId())
                .findFirst();
    }
}
