package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.Busbar;
import com.wrpower.pjc_project.service.service_nr.BusbarManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BusbarController {

    @RequestMapping("/selectBusbar")  //  调试ok
    public List<Object> selectBusbarTest(@RequestParam(value = "projectId") String projectId,
                                         @RequestParam(value = "userName") String userName,
                                         @RequestParam(value = "userId") String userId) {
        BusbarManage busbarManage = new BusbarManage();
        return busbarManage.selectBusbarList(projectId, userName, userId);
    }

    @RequestMapping("/selectBusbarNameAndIdMap")    //调试ok
    public Map<String, String> selectBusbarNameAndIdMap() {
        return BusbarManage.selectBusbarNameAndIdMap();
    }

    @RequestMapping("/insertBusbar")
    public Map<Boolean, String> insertBusbar(@RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "orgId") String orgId,
                                             @RequestBody Busbar busbar) {
        BusbarManage busbarManage = new BusbarManage();
        return busbarManage.insertBusbar(projectId, userName, userId, orgId, busbar);
    }

    /**
     * @param projectId
     * @param userName
     * @param userId
     * @param devId
     * @return
     */
    @RequestMapping("/deleteBusbar")
    public Map<Boolean, String> deleteBusTest(@RequestParam(value = "projectId") String projectId,
                                              @RequestParam(value = "userName") String userName,
                                              @RequestParam(value = "userId") String userId,
                                              @RequestParam(value = "devId") String devId) {
        BusbarManage busbarManage = new BusbarManage();
        return busbarManage.deleteBusbar(projectId, userName, userId, devId);
    }

    /**
     * @param projectId
     * @param userName
     * @param userId
     * @param devId
     * @param changNameAndValueMap
     * @return
     */
    @RequestMapping("/updateBusbar")
    public Map<Boolean, String> updateBusbar(@RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "userName") String userName,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "devId") String devId,
                                             @RequestBody Map<String, String> changNameAndValueMap) {
        BusbarManage busbarManage = new BusbarManage();
        return busbarManage.updateBusbar(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectBusbarInfoByUuid")
    public Busbar selectBusbarInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                         @RequestParam(value = "userName") String userName,
                                         @RequestParam(value = "userId") String userId,
                                         @RequestParam(value = "devId") String devId) {
        BusbarManage busbarManage = new BusbarManage();
        return busbarManage.selectBusbarInfoByUuid(projectId, userName, userId, devId);
    }

}
