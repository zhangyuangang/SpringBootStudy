package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.LittleGen;
import com.wrpower.pjc_project.service.service_nr.LittleGenManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LittleGenController {

    @RequestMapping("/selectLittleGen")
    public List<Object> selectLittleGen(@RequestParam(value = "projectId") String projectId,
                                     @RequestParam(value = "userName") String userName,
                                     @RequestParam(value = "userId") String userId) {
        LittleGenManage littleGenManage = new LittleGenManage();
        return littleGenManage.selectLittleGenList(projectId, userName, userId);
    }

    @RequestMapping("/selectLittleGenNameAndIdMap")
    public Map<String, String> selectLittleGenNameAndIdMap() {
        return LittleGenManage.selectLittleGenNameAndIdMap();
    }

    @RequestMapping("/insertLittleGen")
    public Map<Boolean, String> insertLittleGen(@RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "orgId") String orgId,
                                             @RequestBody LittleGen littleGen) {
        LittleGenManage littleGenManage = new LittleGenManage();
        return littleGenManage.insertLittleGen(projectId, userName, userId, orgId, littleGen);
    }

    @RequestMapping("/deleteLittleGen")
    public Map<Boolean, String> deleteLittleGen(@RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "devId") String devId) {
        LittleGenManage littleGenManage = new LittleGenManage();
        return littleGenManage.deleteLittleGen(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateLittleGen")
    public Map<Boolean, String> updateLittleGen(@RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "devId") String devId,
                                             @RequestBody Map<String, String> changNameAndValueMap) {
        LittleGenManage littleGenManage = new LittleGenManage();
        return littleGenManage.updateLittleGen(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectLittleGenInfoByUuid")
    public LittleGen selectLittleGenInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                         @RequestParam(value = "userName") String userName,
                                         @RequestParam(value = "userId") String userId,
                                         @RequestParam(value = "devId") String devId) {
        LittleGenManage littleGenManage = new LittleGenManage();
        return littleGenManage.selectLittleGenInfoByUuid(projectId,userName,userId,devId);
    }
}
