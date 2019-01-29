package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.PjcProject;
import com.wrpower.pjc_project.service.service_dm.PjcProjectServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class JdbcController {

    @RequestMapping("testJdbcConnect")
    public List<PjcProject> testJdbcConnect()
    {
        PjcProjectServiceImpl projectService = new PjcProjectServiceImpl();
        return projectService.queryAllPjcProject();
    }

}
