package com.unacademy.controller;


import com.unacademy.dto.response.BaseResponseDto;
import com.unacademy.dto.response.ResponseDto;
import com.unacademy.pojos.UnacademyUser;
import com.unacademy.service.interfaces.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    UserDao userDao;


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public BaseResponseDto addUser(@RequestBody UnacademyUser UnacademyUser) {
        log.info(String.valueOf(UnacademyUser));
        ModelMapper modelMapper = new ModelMapper();
        UnacademyUser unacademyUser = modelMapper.map(UnacademyUser, UnacademyUser.class);
        Boolean result = userDao.saveUser(unacademyUser);
        if (result) {

            ResponseDto responseDto = new ResponseDto("Added",200);
            return responseDto;

        } else {
            return new ResponseDto("Not Added",200);
        }

    }


    @RequestMapping(value = "/findUser/{email_id}", method = RequestMethod.GET)
    public BaseResponseDto findUser(@PathVariable(value = "email_id") String emailId) {
        log.info(emailId);
        try {
            UnacademyUser result = userDao.findByEmailId(emailId);
            log.info(String.valueOf(result));
            Map<String,Object> response = new HashMap<>();
            response.put("data", result);
            ResponseDto responseDto = new ResponseDto("SuccessFul",200,response);
            return responseDto;
        }
        catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            response.put("status", 500);
            response.put("data", "");
            ResponseDto responseDto = new ResponseDto("InternalServerError",500,response.toMap());
            return responseDto;
        }
    }


    @RequestMapping(value = "/deleteKey", method = RequestMethod.DELETE)
    public BaseResponseDto deleteUserByKey(@RequestParam(value = "key") String key) {
        try {
            boolean response = userDao.deleteKey(key);
            if(response) {
                return new ResponseDto("Key Deleted SuccessFully", 200);
            }
            return new ResponseDto("Key Not Deleted", 404);
        } catch (Exception e) {
            return new ResponseDto(e.getMessage(),500);
        }
    }


    @RequestMapping(value = "/getUserByRange", method = RequestMethod.GET)
    public BaseResponseDto getUserByRange(@RequestParam (value = "lower") long lower,@RequestParam (value = "upper") long upper) {
        try {
            List<UnacademyUser> results = userDao.getAllUserByRange(lower,upper);
            log.info(String.valueOf(results));
            Map<String,Object> response = new HashMap<>();
            response.put("data", results);
            ResponseDto responseDto = new ResponseDto("SuccessFul",200,response);
            return responseDto;
        }
        catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            response.put("status", 500);
            response.put("data", "");
            ResponseDto responseDto = new ResponseDto("InternalServerError",500,response.toMap());
            return responseDto;
        }
    }


    @RequestMapping(value = "/rank_of_user", method = RequestMethod.POST)
    public BaseResponseDto rankOfUser(@RequestBody UnacademyUser UnacademyUser) {
        log.info(String.valueOf(UnacademyUser));
        ModelMapper modelMapper = new ModelMapper();
        UnacademyUser unacademyUser = modelMapper.map(UnacademyUser, UnacademyUser.class);
        long result = userDao.rankOfUser(unacademyUser);
        log.info(String.valueOf(result));
        if (result != Long.MIN_VALUE) {
            Map<String,Object> response = new HashMap<>();
            response.put("rank Of User", result);
            ResponseDto responseDto = new ResponseDto("SuccessFul",200,response);
            return responseDto;
        } else {
            return new ResponseDto("User Not Found", 404);
        }
    }


    @RequestMapping(value = "/add_user_set", method = RequestMethod.POST)
    public BaseResponseDto addUserZset(@RequestBody UnacademyUser UnacademyUser) {
        log.info(String.valueOf(UnacademyUser));
        ModelMapper modelMapper = new ModelMapper();
        UnacademyUser unacademyUser = modelMapper.map(UnacademyUser, UnacademyUser.class);
        boolean result = userDao.zAddUser(unacademyUser,unacademyUser.getScore());
        log.info(String.valueOf(result));
        if (result) {
            ResponseDto responseDto = new ResponseDto("User Added SuccessFul",200);
            return responseDto;
        } else {
            return new ResponseDto("User Not Created", 500);
        }
    }


//    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
//    public BaseResponseDto updateUser(@RequestBody UnacademyUser UnacademyUser) {
//        log.info(String.valueOf(UnacademyUser));
//        ModelMapper modelMapper = new ModelMapper();
//        UnacademyUser unacademyUser = modelMapper.map(UnacademyUser, UnacademyUser.class);
//        boolean result = userDao.updateUser(unacademyUser);
//        log.info(String.valueOf(result));
//        if (result) {
//            ResponseDto responseDto = new ResponseDto("User updated SuccessFul",200);
//            return responseDto;
//        } else {
//            return new ResponseDto("User is Not updated", 500);
//        }
//    }


    @RequestMapping(value = "/setExpireInHour", method = RequestMethod.GET)
    public BaseResponseDto setExpire(@RequestParam(value = "expire_in_hour") long timeOut,@RequestParam(value = "key") String key ) {
        log.info("timeout"+timeOut);
        try {
            boolean result = userDao.setExpire(timeOut,key);
            if(result) {
                return new ResponseDto("Expiry Time  Set", 200);
            }
            return new ResponseDto("Expiry Time Not Set", 400);
        } catch (Exception e) {
            return new ResponseDto("Expiry Time Not Set", 500);
        }
    }


}

