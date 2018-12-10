package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.domain.PjcProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrpower.pjc_project.service.PjcProjectManage;

import java.util.List;

@Configuration
@RestController
public class ProjectController {

    @Autowired
    PjcProjectManage pjcProjectManage;

    @RequestMapping("/getFutureProjectList")
    public List<PjcProject> getFutureProjectList(@RequestParam(value = "sort",defaultValue = "nnn") String sort){
        return pjcProjectManage.getFutureProjectList();
    }

    @RequestMapping("/insertFutureProjectList")
    public List<String> insertFutureProjectList(@RequestBody List<PjcProject> myProList){
        return pjcProjectManage.insertFutureProject(myProList);
    }

    @RequestMapping("/deleteFutureProjectList")
    public List<Boolean>  deleteFutureProjectList(@RequestBody List<String> myProList){
        return pjcProjectManage.deleteFutureProject(myProList);
    }

    @RequestMapping("/updateFutureProjectList")
    public List<String> updateFutureProjectList(@RequestBody List<PjcProject> myProList){
        return pjcProjectManage.updateFutureProject(myProList);
    }

    @RequestMapping("/test")
    public String test(){
        return  "hello world";
    }

}
