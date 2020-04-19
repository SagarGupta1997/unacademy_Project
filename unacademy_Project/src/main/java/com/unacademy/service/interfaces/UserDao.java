package com.unacademy.service.interfaces;

import com.unacademy.pojos.UnacademyUser;

import java.util.List;


public interface UserDao {

    public Boolean saveUser(UnacademyUser unacademyUser) ;

    public UnacademyUser findByEmailId(String name) ;

    public Boolean deleteKey(String key_name);

    public boolean updateUser(UnacademyUser unacademyUser);

    public boolean zAddUser(UnacademyUser unacademyUser, double score);

    public long rankOfUser(UnacademyUser unacademyUser);

    public List<UnacademyUser> getAllUserByRange(long lower, long upper);

    public boolean setExpire(long timeOut, String key_name);


}
