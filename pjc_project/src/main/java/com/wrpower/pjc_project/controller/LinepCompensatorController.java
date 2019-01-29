package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.LinepCompensator;
import com.wrpower.pjc_project.service.service_nr.LinepCompensatorManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LinepCompensatorController {


    @RequestMapping("/selectLinepCompensator")
    public List<Object> selectLinepCompensator(@RequestParam(value = "projectId") String projectId,
                                               @RequestParam(value = "userName") String userName,
                                               @RequestParam(value = "userId") String userId) {
        LinepCompensatorManage linepCompensatorManage = new LinepCompensatorManage();
        return linepCompensatorManage.selectLinepCompensatorList(projectId, userName, userId);
    }

    @RequestMapping("/selectLinepCompensatorNameAndIdMap")
    public Map<String, String> selectLinepCompensatorNameAndIdMap() {
        return LinepCompensatorManage.selectLinepCompensatorNameAndIdMap();
    }

    @RequestMapping("/insertLinepCompensator")
    public Map<Boolean, String> insertLinepCompensator(@RequestParam(value = "projectId") String projectId,
                                                @RequestParam(value = "userName") String userName,
                                                @RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "orgId") String orgId,
                                                @RequestBody LinepCompensator linepCompensator) {
        LinepCompensatorManage linepCompensatorManage = new LinepCompensatorManage();
        return linepCompensatorManage.insertLinepCompensator(projectId, userName, userId, orgId, linepCompensator);
    }

    @RequestMapping("/deleteLinepCompensator")
    public Map<Boolean, String> deleteLinepCompensator(@RequestParam(value = "projectId") String projectId,
                                                @RequestParam(value = "userName") String userName,
                                                @RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "devId") String devId) {
        LinepCompensatorManage linepCompensatorManage = new LinepCompensatorManage();
        return linepCompensatorManage.deleteLinepCompensator(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateLinepCompensator")
    public Map<Boolean, String> updateLinepCompensator(@RequestParam(value = "projectId") String projectId,
                                                @RequestParam(value = "userName") String userName,
                                                @RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "devId") String devId,
                                                @RequestBody Map<String, String> changNameAndValueMap) {
        LinepCompensatorManage linepCompensatorManage = new LinepCompensatorManage();
        return linepCompensatorManage.updateLinepCompensator(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectLinepCompensatorInfoByUuid")
    public LinepCompensator selectLinepCompensatorInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                                             @RequestParam(value = "userName") String userName,
                                                             @RequestParam(value = "userId") String userId,
                                                             @RequestParam(value = "devId") String devId) {
        LinepCompensatorManage linepCompensatorManage = new LinepCompensatorManage();
        return linepCompensatorManage.selectLinepCompensatorInfoByUuid(projectId,userName,userId,devId);
    }

}
