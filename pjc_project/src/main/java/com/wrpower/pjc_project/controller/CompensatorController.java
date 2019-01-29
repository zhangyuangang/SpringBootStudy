package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.Compensator;
import com.wrpower.pjc_project.service.service_nr.CompensatorManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class CompensatorController {

    @RequestMapping("/selectCompensator")
    public List<Object> selectCompensator(@RequestParam(value = "projectId") String projectId,
                                          @RequestParam(value = "userName") String userName,
                                          @RequestParam(value = "userId") String userId) {
        CompensatorManage compensatorManage = new CompensatorManage();
        return compensatorManage.selectCompensatorList(projectId, userName, userId);
    }

    @RequestMapping("/selectCompensatorNameAndIdMap")
    public Map<String, String> selectCompensatorNameAndIdMap() {
        return CompensatorManage.selectCompensatorNameAndIdMap();
    }

    @RequestMapping("/insertCompensator")
    public Map<Boolean, String> insertCompensator(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "orgId") String orgId,
                                                  @RequestBody Compensator compensator) {
        CompensatorManage compensatorManage = new CompensatorManage();
        return compensatorManage.insertCompensator(projectId, userName, userId, orgId, compensator);
    }

    @RequestMapping("/deleteCompensator")
    public Map<Boolean, String> deleteCompensator(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "devId") String devId) {
        CompensatorManage compensatorManage = new CompensatorManage();
        return compensatorManage.deleteCompensator(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateCompensator")
    public Map<Boolean, String> updateCompensator(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "devId") String devId,
                                                  @RequestParam(value = "compensatorType") String compensatorType,
                                                  @RequestBody Map<String, String> changNameAndValueMap) {
        CompensatorManage compensatorManage = new CompensatorManage();
        return compensatorManage.updateCompensator(projectId, userName, userId, devId, compensatorType, changNameAndValueMap);
    }

    @RequestMapping("/selectCompensatorInfoByUuid")
    public Compensator selectCompensatorInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                                   @RequestParam(value = "userName") String userName,
                                                   @RequestParam(value = "userId") String userId,
                                                   @RequestParam(value = "devId") String devId) {
        CompensatorManage compensatorManage = new CompensatorManage();
        return compensatorManage.selectCompensatorInfoByUuid(projectId, userName, userId, devId);
    }
}
