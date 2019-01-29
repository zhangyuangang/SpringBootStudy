package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.service.service_nr.BasicManage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BasicController {

    @RequestMapping("/getVolTypeMap")
    public Map<String, String> getVolTypeMap() {
        return BasicManage.getVolAndTypeMap();
    }

    @RequestMapping("/getOwnerMap")
    public Map getOwnerMap() {
        return BasicManage.getOwnerMap();
    }

    @RequestMapping("/getSubstationMap")
    public Map getSubstationMap() {
        return BasicManage.getSubstationMap();
    }

    @RequestMapping("/stamp")
    public String getStamp(){
        String stamp = BasicManage.getSTAMP("330000","0");
        return stamp;
    }

    @RequestMapping("/getSubstationTypeMap")
    public Map getSubstationTypeMap() {
        return BasicManage.getSubTypeMap();
    }

    @RequestMapping("/getTime")
    public Long  getTime() {
        return BasicManage.getTime( "2019-01-16");
    }

    @RequestMapping("/shortDateStrToLongDateStr")
    public String  shortDateStrToLongDateStr(@RequestParam("dateStr") String longStr ) {
        return BasicManage.shortDateStrToLongDateStr( longStr);
    }

    @RequestMapping("/longDateStrToShortDateStr")
    public String  longDateStrToShortDateStr(@RequestParam("dateStr") String longStr ) {
        return BasicManage.longDateStrToShortDateStr( longStr);
    }
}
