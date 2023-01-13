package com.yoon.dixit.user;

import com.yoon.dixit.user.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private static final List<User> users = new ArrayList<>();


    public User login(String id) {
        User user = getUser(id);

        if (Objects.isNull(user)) {
            user = User.builder()
                    .id(id)
                    .build();
        }

        users.add(user);

        return user;
    }

    public void logout(String id) {
        users.remove(getUser(id));
    }

    public User getUser(String id) {

        return users.stream()
                .filter(user -> StringUtils.equals(user.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid user id " + id));
    }

    public boolean isFirst(String id) {
        return StringUtils.equals(users.get(0).getId(), id);
    }

    public void shuffleOrder() {
        Collections.shuffle(users);
    }

    synchronized public int getUserCount() {
        return users.size();
    }

    public void clearUsers() {
        users.clear();
    }
}
