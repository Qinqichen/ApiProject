package com.api.apiproject.controller;


import com.api.apiproject.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

@Controller
@RequestMapping("/file")
public class FileController {


    @Value("${file.upload.url}")
    private String uploadFilePath;



    @PostMapping("/upload/{path}")
    @ResponseBody
    public R uploadFile(@PathVariable String path, @RequestParam("file")MultipartFile files[], @RequestParam("token")String token){

        if (!"qin1997".equals(token)){
            return R.error().message("你个傻逼还想非法侵入？！");
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (!StringUtils.hasText(fileName)){
                Date today = new Date();
                fileName = new SimpleDateFormat("yyyy-MM-dd").format(today);
            }

            File destPath = new File(uploadFilePath.concat("/").concat(path).concat("/").concat(fileName));

            if (!destPath.getParentFile().exists()){
                destPath.getParentFile().mkdirs();
            }

            try{
                file.transferTo(destPath);
            } catch (Exception e) {
                return R.error().message(fileName+"\t\n"+e.getMessage());
            }

        }

        return R.ok().message("上传成功");
    }








}
