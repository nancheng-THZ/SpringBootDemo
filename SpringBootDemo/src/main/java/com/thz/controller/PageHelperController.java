package com.thz.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thz.entity.User;
import com.thz.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/crudPage")
public class PageHelperController {
    @Resource
    UserService userService;


    @RequestMapping("/home")
    public String uploadPage() throws Exception {
        System.out.println("主页");
        return "html/pageHelper";
    }


    @RequestMapping("/listUser")
    public String listUser(Model m, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        PageHelper.startPage(start,size,"id asc");
        List<User> list = userService.findAll();
        for (User us : list) {
            System.out.println(us);
        }
        PageInfo<User> page = new PageInfo<>(list);
        m.addAttribute("page", page);
        return "html/pageHelper";
    }


    @RequestMapping("/addUser")
    public String addUser(User user){
        userService.insert(user);
        return "redirect:/crudPage/listUser";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(int id){
        userService.deleteById(id);
        return "redirect:/crudPage/listUser";
    }

    @RequestMapping("/editUser")
    public String editUser(Model model,User user){
        model.addAttribute("user",user);
        return "html/editor";
    }

    @RequestMapping("/updateUser")
    public String updateUser(User user){
        userService.update(user);
        return "redirect:/crudPage/listUser";
    }

}
