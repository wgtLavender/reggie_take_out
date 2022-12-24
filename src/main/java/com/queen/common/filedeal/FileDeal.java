package com.queen.common.filedeal;

import com.queen.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author
 * @date 2022/12/08/20:51
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class FileDeal {

    @Value("${reggie.imagePath}")
    private String imagePath;

    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf('.');
        String substring = originalFilename.substring(i);

        String uuid = UUID.randomUUID().toString() + substring;
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            multipartFile.transferTo(new File(imagePath + uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(uuid);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //输入流 读取文件内容
             fileInputStream = new FileInputStream(new File(imagePath + name));
            //输出流 文件内容返回给浏览器
            outputStream = response.getOutputStream();
            response.setContentType("image/jepg");
            int len = 0;
            byte[] bytes = new byte[1024];
           while ( (len = fileInputStream.read(bytes)) != -1) {
               outputStream.write(bytes,0,len);
               outputStream.flush();
           }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

//        return R.success(outputStream);
    }

}
