package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.GroundBranch;
import com.wrpower.pjc_project.service.service_nr.GroundBranchManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GroundBranchController {

    @RequestMapping("/selectGroundBranch")
    public List<Object> selectGroundBranch(@RequestParam(value = "projectId") String projectId,
                                           @RequestParam(value = "userName") String userName,
                                           @RequestParam(value = "userId") String userId) {
        GroundBranchManage groundBranchManage = new GroundBranchManage();
        return groundBranchManage.selectGroundBranchList(projectId, userName, userId);
    }

    @RequestMapping("/selectGroundBranchNameAndIdMap")
    public Map<String, String> selectGroundBranchNameAndIdMap() {
        return GroundBranchManage.selectGroundBranchNameAndIdMap();
    }

    @RequestMapping("/insertGroundBranch")
    public Map<Boolean, String> insertGroundBranch(@RequestParam(value = "projectId") String projectId,
                                                   @RequestParam(value = "userName") String userName,
                                                   @RequestParam(value = "userId") String userId,
                                                   @RequestParam(value = "orgId") String orgId,
                                                   @RequestBody GroundBranch groundBranch) {
        GroundBranchManage groundBranchManage = new GroundBranchManage();
        return groundBranchManage.insertGroundBranch(projectId, userName, userId, orgId, groundBranch);
    }

    @RequestMapping("/deleteGroundBranch")
    public Map<Boolean, String> deleteGroundBranch(@RequestParam(value = "projectId") String projectId,
                                                   @RequestParam(value = "userName") String userName,
                                                   @RequestParam(value = "userId") String userId,
                                                   @RequestParam(value = "devId") String devId) {
        GroundBranchManage groundBranchManage = new GroundBranchManage();
        return groundBranchManage.deleteGroundBranch(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateGroundBranch")
    public Map<Boolean, String> updateGroundBranch(@RequestParam(value = "projectId") String projectId,
                                                   @RequestParam(value = "userName") String userName,
                                                   @RequestParam(value = "userId") String userId,
                                                   @RequestParam(value = "devId") String devId,
                                                   @RequestBody Map<String, String> changNameAndValueMap) {
        GroundBranchManage groundBranchManage = new GroundBranchManage();
        return groundBranchManage.updateGroundBranch(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectGroundBranchInfoByUuid")
    public GroundBranch selectGroundBranchInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                                     @RequestParam(value = "userName") String userName,
                                                     @RequestParam(value = "userId") String userId,
                                                     @RequestParam(value = "devId") String devId) {
        GroundBranchManage groundBranchManage = new GroundBranchManage();
        return groundBranchManage.selectGroundBranchInfoByUuid(projectId, userName, userId, devId);
    }
}
