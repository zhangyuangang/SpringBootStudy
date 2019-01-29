package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.Acline;
import com.wrpower.pjc_project.service.service_nr.AclineManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AclineController {

    @RequestMapping("/selectAcline")
    public List<Object> selectAcline(@RequestParam(value = "projectId") String projectId,
                                     @RequestParam(value = "userName") String userName,
                                     @RequestParam(value = "userId") String userId) {
        AclineManage aclineManage = new AclineManage();
        return aclineManage.selectAclineList(projectId, userName, userId);
    }

    @RequestMapping("/selectAclineNameAndIdMap")
    public Map<String, String> selectAclineNameAndIdMap() {
        return AclineManage.selectAclineNameAndIdMap();
    }

    @RequestMapping("/insertAcline")
    public Map<Boolean, String> insertAcline(@RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "orgId") String orgId,
                                             @RequestBody Acline acline) {
        AclineManage aclineManage = new AclineManage();
        return aclineManage.insertAcline(projectId, userName, userId, orgId, acline);
    }

    @RequestMapping("/deleteAcline")
    public Map<Boolean, String> deleteAcline(@RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "devId") String devId) {
        AclineManage aclineManage = new AclineManage();
        return aclineManage.deleteAcline(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateAcline")
    public Map<Boolean, String> updateAcline(@RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "devId") String devId,
                                             @RequestBody Map<String, String> changNameAndValueMap) {
        AclineManage aclineManage = new AclineManage();
        return aclineManage.updateAcline(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectAclineInfoByUuid")
    public Acline selectAclineInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                         @RequestParam(value = "userName") String userName,
                                         @RequestParam(value = "userId") String userId,
                                         @RequestParam(value = "devId") String devId) {
        AclineManage aclineManage = new AclineManage();
        return aclineManage.selectAclineInfoByUuid(projectId,userName,userId,devId);
    }
}
