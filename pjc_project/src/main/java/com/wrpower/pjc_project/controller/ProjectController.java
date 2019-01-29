package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.PjcProject;
import com.wrpower.pjc_project.service.service_nr.NRDBAccessManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrpower.pjc_project.service.service_nr.PjcProjectManage;

import java.util.*;

@RestController
public class ProjectController {

    @RequestMapping("/selectFutureProject")   // 在前端已ok
    public List<PjcProject> getFutureProjectList() {
        PjcProjectManage pjcProjectManage = new PjcProjectManage();
        return pjcProjectManage.getFutureProjectList();
    }

    //根据dateMin和dateMax筛选该日期范围内的工程
    @RequestMapping("/getFutureProjectMapByDate")
    public Map<String, String> getFutureProjectMapByDate(@RequestParam(value = "dateMin") String dateMin,
                                                       @RequestParam(value = "dateMax") String dateMax) {
        PjcProjectManage pjcProjectManage = new PjcProjectManage();
        return pjcProjectManage.getFutureProjectMapByDate(dateMin,dateMax);
    }

    @RequestMapping("/insertFutureProject") // 在前端已ok
    public Map<Boolean, String> insertFutureProjectList(@RequestParam(value = "userName") String userName,
                                                        @RequestParam(value = "userId") String userId,
                                                        @RequestParam(value = "orgId") String orgId,
                                                        @RequestBody PjcProject pjcProject) {
        PjcProjectManage pjcProjectManage = new PjcProjectManage();
        return pjcProjectManage.insertFutureProject(pjcProject, userName, userId, orgId);
    }

    @RequestMapping("/deleteFutureProject") // 在前端已ok
    public Map<Boolean, String> deleteFutureProjectList(@RequestParam(value = "projectId") String projectId) {
        PjcProjectManage pjcProjectManage = new PjcProjectManage();
        return pjcProjectManage.deleteFutureProject(projectId);
    }

    @RequestMapping("/updateFutureProject") // 在前端已ok
    public Map<Boolean, String> updateFutureProjectList(@RequestParam(value = "projectId") String projectId,
                                                        @RequestParam(value = "userName") String userName,
                                                        @RequestParam(value = "userId") String userId,
                                                        @RequestBody PjcProject pjcProject) {
        PjcProjectManage pjcProjectManage = new PjcProjectManage();
        return pjcProjectManage.updateFutureProject(projectId, userName, userId, pjcProject);
    }

    //    获取工程下所有设备
    @RequestMapping("/getProjectDetailList")
    public Map<String, List<Object>> getProjectDetailList(@RequestParam(value = "projectId") String projectId,
                                                          @RequestParam(value = "userName") String userName,
                                                          @RequestParam(value = "userId") String userId) {
        NRDBAccessManage.clearProjectId();
        NRDBAccessManage.setProjectId(projectId, userName, userId);
        PjcProjectManage pjcProjectManage = new PjcProjectManage();
        return pjcProjectManage.queryProjectDetailInfoMap(projectId, userName, userId);
    }

    //    获取工程下所有设备
    @RequestMapping("/getProjectDetailListByDate")
    public Map<String, List<Object>> getProjectDetailListByDate(@RequestParam(value = "projectId") String projectId,
                                                                @RequestParam(value = "userName") String userName,
                                                                @RequestParam(value = "userId") String userId,
                                                                @RequestParam(value = "dateMin") String dateMin,
                                                                @RequestParam(value = "dateMax") String dateMax) {
        NRDBAccessManage.clearProjectId();
        NRDBAccessManage.setProjectId(projectId, userName, userId);
        PjcProjectManage pjcProjectManage = new PjcProjectManage();
        return pjcProjectManage.queryProjectDetailInfoMapByDate(projectId, userName, userId,dateMin,dateMax);
    }


//    @RequestMapping("/queryProjectDetailList")
//    public Map<String, List<String>> queryProjectDetailList(@RequestParam(value = "projectId") String projectId,
//                                                            @RequestParam(value = "userName") String userName,
//                                                            @RequestParam(value = "userId") String userId) {
//        PjcProjectManage projectManage = new PjcProjectManage();
//        return projectManage.getProjectDetailId(projectId);
//    }

}
