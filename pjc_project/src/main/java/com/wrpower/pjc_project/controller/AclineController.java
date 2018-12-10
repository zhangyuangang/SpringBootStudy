package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.service.AclineManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AclineController {

    @Autowired
    AclineManage aclineManage;

    @RequestMapping("/getAclineName")
    public String getAcline() {
        return aclineManage.getAclineName();
    }
}
