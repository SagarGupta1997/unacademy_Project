package com.unacademy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unacademy.pojos.UnacademyUser;
import com.unacademy.repository.UserRepository;
import com.unacademy.service.interfaces.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final String KEY = "user";
    private static final String KEY_Set = "user_set";

    public Boolean saveUser(UnacademyUser unacademyUser) {
        try {
            Map userHash = new ObjectMapper().convertValue(unacademyUser, Map.class);
            return redisTemplate.opsForHash().putIfAbsent(KEY, unacademyUser.getEmail(), userHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UnacademyUser findByEmailId(String emailId) {

        Map userMap = (Map) redisTemplate.opsForHash().get(KEY, emailId);
        log.info(String.valueOf(userMap));
        UnacademyUser unacademyUser = new ObjectMapper().convertValue(userMap, UnacademyUser.class);
        return unacademyUser;
    }

    @Override
    public Boolean deleteKey(String key_name) {
        try {
            return redisTemplate.delete(key_name);
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean updateUser(UnacademyUser unacademyUser) {
        try {
            Map userHash = new ObjectMapper().convertValue(unacademyUser, Map.class);
            redisTemplate.opsForValue().set(KEY, userHash);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean zAddUser(UnacademyUser unacademyUser, double score) {
        try {
            Map userHash = new ObjectMapper().convertValue(unacademyUser, Map.class);
            return redisTemplate.opsForZSet().add(KEY_Set, userHash, score);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long rankOfUser(UnacademyUser unacademyUser) {
        try {
            Map userHash = new ObjectMapper().convertValue(unacademyUser, Map.class);
            long rank = redisTemplate.opsForZSet().rank(KEY_Set, userHash);
            return rank;

        } catch (Exception e) {
            e.printStackTrace();
            return Long.MIN_VALUE;
        }
    }

    @Override
    public List<UnacademyUser> getAllUserByRange(long lower, long upper) {
        try {
            Set<Map> users = redisTemplate.opsForZSet().range(KEY_Set, lower, upper);
            List<UnacademyUser> unacademyUserList = new ArrayList<>();
            for (Map user : users) {
                UnacademyUser unacademyUser = new ObjectMapper().convertValue(user, UnacademyUser.class);
                unacademyUserList.add(unacademyUser);
            }
            return unacademyUserList;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean setExpire(long timeOut, String key_name) {
        redisTemplate.expire(KEY, timeOut, TimeUnit.HOURS);
        try {
            return redisTemplate.expire(key_name, timeOut, TimeUnit.HOURS);

        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }


}
