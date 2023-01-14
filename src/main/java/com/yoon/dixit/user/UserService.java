package com.yoon.dixit.user;

import com.yoon.dixit.user.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private static final List<User> users = new ArrayList<>();


    public User login(String id) {
        Optional<User> userOptional = getUser(id);
        User user;

        if (userOptional.isPresent()) {

            user = userOptional.get();

        } else {
            user = User.builder()
                    .id(id)
                    .build();
            users.add(user);
        }

        return user;
    }

    public void logout(String id) {
        getUser(id).ifPresent(users::remove);
    }

    public Optional<User> getUser(String id) {

        return users.stream()
                .filter(user -> StringUtils.equals(user.getId(), id))
                .findFirst();
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
