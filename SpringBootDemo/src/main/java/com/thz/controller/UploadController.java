package com.thz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class UploadController {


    @RequestMapping("/uploadPage")
    public String uploadPage() throws Exception {
//        if(true){
//            throw new Exception("some exception");
//        }
        return "/uploadPage";
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest req, @RequestParam("file") MultipartFile file, Model m) {
        try {
            //根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = System.currentTimeMillis()+file.getOriginalFilename();
            //通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
            String destFileName=req.getServletContext().getRealPath("")+"uploaded"+File.separator+fileName;
            //第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录
            System.out.println(destFileName);
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            System.out.println(destFile);
            //把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);
            System.out.println(fileName);
            //m.addAttribute("fileName",fileName);
            m.addAttribute("fileName",fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }
        return "/showImg";
    }


}

