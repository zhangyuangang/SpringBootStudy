package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.Breaker;
import com.wrpower.pjc_project.service.service_nr.BreakerManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BreakerController {

    @RequestMapping("/selectBreaker")
    public List<Object> selectBreaker(@RequestParam(value = "projectId") String projectId,
                                      @RequestParam(value = "userName") String userName,
                                      @RequestParam(value = "userId") String userId) {
        BreakerManage breakerManage = new BreakerManage();
        return breakerManage.selectBreakerList(projectId, userName, userId);
    }

    @RequestMapping("/selectBreakerNameAndIdMap")
    public Map<String, String> selectBreakerNameAndIdMap() {
        return BreakerManage.selectBreakerNameAndIdMap();
    }

    @RequestMapping("/insertBreaker")
    public Map<Boolean, String> insertBreaker(@RequestParam(value = "projectId") String projectId,
                                              @RequestParam(value = "userName") String userName,
                                              @RequestParam(value = "userId") String userId,
                                              @RequestParam(value = "orgId") String orgId,
                                              @RequestBody Breaker breaker) {
        BreakerManage breakerManage = new BreakerManage();
        return breakerManage.insertBreaker(projectId, userName, userId, orgId, breaker);
    }

    @RequestMapping("/deleteBreaker")
    public Map<Boolean, String> deleteBreaker(@RequestParam(value = "projectId") String projectId,
                                              @RequestParam(value = "userName") String userName,
                                              @RequestParam(value = "userId") String userId,
                                              @RequestParam(value = "devId") String devId) {
        BreakerManage breakerManage = new BreakerManage();
        return breakerManage.deleteBreaker(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateBreaker")
    public Map<Boolean, String> updateBreaker(@RequestParam(value = "projectId") String projectId,
                                              @RequestParam(value = "userName") String userName,
                                              @RequestParam(value = "userId") String userId,
                                              @RequestParam(value = "devId") String devId,
                                              @RequestBody Map<String, String> changNameAndValueMap) {
        BreakerManage breakerManage = new BreakerManage();
        return breakerManage.updateBreaker(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectBreakerInfoByUuid")
    public Breaker selectBreakerInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                           @RequestParam(value = "userName") String userName,
                                           @RequestParam(value = "userId") String userId,
                                           @RequestParam(value = "devId") String devId) {
        BreakerManage breakerManage = new BreakerManage();
        return breakerManage.selectBreakerInfoByUuid(projectId,userName, userId,devId);
    }
}
