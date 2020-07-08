package com.thz.controller;

import com.thz.entity.User;
import com.thz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/crud", method = { RequestMethod.GET, RequestMethod.POST })
public class CRUDController {

    @Autowired
    private UserService userservice;

    public CRUDController(UserService userservice) {
        this.userservice = userservice;
    }

    @RequestMapping("/ListUser")
    @ResponseBody
    public List<User> ListUser(){
        return userservice.queryAllByLimit(0,100);
    }

    @RequestMapping("/ListUserByname")
    @ResponseBody
    public List<User> ListUserByname(String name){
        return userservice.queryByUserName(name);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(int id) {
        Boolean result = userservice.deleteById(id);
        if (result) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(User user) {
        int result = userservice.update(user);
        if (result >= 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }

    }

//    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    public User insert(User user) {
//        return userservice.insert(user);
//    }


}