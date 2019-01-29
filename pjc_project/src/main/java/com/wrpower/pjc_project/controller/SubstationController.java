package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.Substation;
import com.wrpower.pjc_project.service.service_nr.SubstationManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SubstationController {

    /**
     * @param projectId
     * @param userName
     * @param userId
     * @return List<Substation>
     */
    @RequestMapping("/selectSubstation")    //  已调试ok
    public List<Object> getSubstations(@RequestParam(value = "projectId") String projectId,
                                       @RequestParam(value = "userName") String userName,
                                       @RequestParam(value = "userId") String userId) {
        SubstationManage substationManage = new SubstationManage();
        return substationManage.selectSubstationList(projectId, userName, userId);
    }

    @RequestMapping("/selectSubstationNameAndIdMap")
    public Map<String, String> selectSubstationNameAndIdMap() {
        return SubstationManage.selectSubstationNameAndIdMap();
    }

    /**
     * @param projectId
     * @param userName
     * @param userId
     * @param substation
     * @return Map<Boolean   ,       String>
     */
    @RequestMapping("/insertSubstation")
    public Map<Boolean, String> insertSubstations(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "orgId") String orgId,
                                                  @RequestBody Substation substation) {
        SubstationManage substationManage = new SubstationManage();
        return substationManage.insertSubstation(projectId, userName, userId, orgId, substation);
    }

    /**
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueMap
     * @return Map<Boolean   ,       String>
     */
    @RequestMapping("/updateSubstation")
    public Map<Boolean, String> updateSubstations(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "substationType") String substationType,
                                                  @RequestParam(value = "devId") String devId,
                                                  @RequestBody Map<String, String> changNameAndValueMap) {
        SubstationManage substationManage = new SubstationManage();
        return substationManage.updateSubstation(projectId, userName, userId, substationType, devId, changNameAndValueMap);
    }

    /**
     * @param projectId
     * @param userName
     * @param userId
     * @param substationType
     * @param devId
     * @return List<Boolean>
     */
    @RequestMapping("/deleteSubstation")
    public Map<Boolean, String> deleteSubstations(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "substationType") String substationType,
                                                  @RequestParam(value = "devId") String devId) {
        SubstationManage substationManage = new SubstationManage();
        return substationManage.deleteSubstation(projectId, userName, userId, substationType, devId);
    }




    @RequestMapping("/selectSubstationInfoByUuid")
    public Substation selectSubstationInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                         @RequestParam(value = "userName") String userName,
                                         @RequestParam(value = "userId") String userId,
                                         @RequestParam(value = "devId") String devId) {
        SubstationManage substationManage = new SubstationManage();
        return substationManage.selectSubstationInfoByUuid(projectId, userName, userId, devId);
    }

}
