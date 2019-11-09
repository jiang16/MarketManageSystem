package cn.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.market.service.FileService;
import cn.market.util.Result;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {
 
		@Autowired
		private FileService fileService;
		
    @PostMapping(value = "/getFileData")
    public Result uploadFile(@RequestParam("file") MultipartFile file){
              
      return fileService.getFileDate(file);
        
    }
}