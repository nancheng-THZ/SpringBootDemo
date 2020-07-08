package com.thz.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thz.entity.User;
import com.thz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:thz
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@Controller
//@RequestMapping("/testBoot")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUser/{id}")
    @ResponseBody
    public String GetUser(@PathVariable int id){
        return userService.Sel(id).toString();
    }


    @RequestMapping("addUser")
    public String addUser(User user){
        System.out.println("addUser");
        System.out.println(user);
        userService.insert(user);
        return "redirect:index.html";
    }


    @ResponseBody
    @RequestMapping("QueryAjax")
    public JSON QueryAjax(){
        System.out.println("QueryAjax");
        List<User> industries = userService.queryAllByLimit(0,100);
        JSON json=(JSON) JSON.toJSON(industries);
        System.out.println(json);
        return json;
    }


    /**
     * 删除数据
     *
     * @return
     */
    @RequestMapping("DelAjax")
    @ResponseBody
    public Map DelAjax(@RequestBody JSONObject data) {
        Integer id = Integer.parseInt(data.getString("id"));
        System.out.println("DelAjax-------"+id);
        Boolean flag = userService.deleteById(id);
        Map<String, String> map = new HashMap<String,String>();
        if (flag){
            map.put("message","success");
            System.out.println("删除成功");
        }else {
            map.put("message","faile");
            System.out.println("删除失败");
        }
        return map;
    }


    /**
     * 修改数据
     *
     * @return
     */
    @RequestMapping("UpdateAjax")
    public String UpdateAjax(@RequestBody JSONObject data,Model model) {
        Integer id = Integer.parseInt(data.getString("id"));
        System.out.println("UpdateAjax-------"+id);
        User user = userService.queryById(id);
        model.addAttribute("user", user);
        return "/editor";
    }
    @RequestMapping("UpdateAjax1")
    public String UpdateAjax1(int id, Model model){
        System.out.println("UpdateAjax1-------"+id);
        User user = userService.queryById(id);
        model.addAttribute("user", user);
        return "/editor";
    }

    @RequestMapping("updateDo")
    public String updateDo(User user){
        System.out.println("updateDo-------"+user);
        userService.update(user);
        return "redirect:/index.html";
    }




    /**
     *
     * @return 视图
     */
    @RequestMapping("goHome")
    public String goHome(Model model){
        System.out.println("去主页");
        model.addAttribute("name","thymeleaf");
        return "html/hello";
    }

}