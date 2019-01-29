package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.LinesCompensator;
import com.wrpower.pjc_project.service.service_nr.LinesCompensatorManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class LinesCompensatorController {


    @RequestMapping("/selectLinesCompensator")
    public List<Object> selectLinesCompensator(@RequestParam(value = "projectId") String projectId,
                                               @RequestParam(value = "userName") String userName,
                                               @RequestParam(value = "userId") String userId) {
        LinesCompensatorManage linesCompensatorManage = new LinesCompensatorManage();
        return linesCompensatorManage.selectLinesCompensatorList(projectId, userName, userId);
    }

    @RequestMapping("/selectLinesCompensatorNameAndIdMap")
    public Map<String, String> selectLinesCompensatorNameAndIdMap() {
        return LinesCompensatorManage.selectLinesCompensatorNameAndIdMap();
    }

    @RequestMapping("/insertLinesCompensator")
    public Map<Boolean, String> insertLinesCompensator(@RequestParam(value = "projectId") String projectId,
                                                       @RequestParam(value = "userName") String userName,
                                                       @RequestParam(value = "userId") String userId,
                                                       @RequestParam(value = "orgId") String orgId,
                                                       @RequestBody LinesCompensator linesCompensator) {
        LinesCompensatorManage linesCompensatorManage = new LinesCompensatorManage();
        return linesCompensatorManage.insertLinesCompensator(projectId, userName, userId, orgId, linesCompensator);
    }

    @RequestMapping("/deleteLinesCompensator")
    public Map<Boolean, String> deleteLinesCompensator(@RequestParam(value = "projectId") String projectId,
                                                       @RequestParam(value = "userName") String userName,
                                                       @RequestParam(value = "userId") String userId,
                                                       @RequestParam(value = "devId") String devId) {
        LinesCompensatorManage linesCompensatorManage = new LinesCompensatorManage();
        return linesCompensatorManage.deleteLinesCompensator(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateLinesCompensator")
    public Map<Boolean, String> updateLinesCompensator(@RequestParam(value = "projectId") String projectId,
                                                       @RequestParam(value = "userName") String userName,
                                                       @RequestParam(value = "userId") String userId,
                                                       @RequestParam(value = "devId") String devId,
                                                       @RequestBody Map<String, String> changNameAndValueMap) {
        LinesCompensatorManage linesCompensatorManage = new LinesCompensatorManage();
        return linesCompensatorManage.updateLinesCompensator(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectLinesCompensatorInfoByUuid")
    public LinesCompensator selectLinesCompensatorInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                                             @RequestParam(value = "userName") String userName,
                                                             @RequestParam(value = "userId") String userId,
                                                             @RequestParam(value = "devId") String devId) {
        LinesCompensatorManage linesCompensatorManage = new LinesCompensatorManage();
        return linesCompensatorManage.selectLinesCompensatorInfoByUuid(projectId,userName,userId,devId);
    }

}
