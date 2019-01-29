package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.Generator;
import com.wrpower.pjc_project.service.service_nr.GeneratorManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class GeneratorController {

    @RequestMapping("/selectGenerator")
    public List<Object> selectGenerator(@RequestParam(value = "projectId") String projectId,
                                        @RequestParam(value = "userName") String userName,
                                        @RequestParam(value = "userId") String userId) {
        GeneratorManage generatorManage = new GeneratorManage();
        return generatorManage.selectGeneratorList(projectId, userName, userId);
    }

    @RequestMapping("/selectGeneratorNameAndIdMap")
    public Map<String, String> selectGeneratorNameAndIdMap() {
        return GeneratorManage.selectGeneratorNameAndIdMap();
    }

    @RequestMapping("/insertGenerator")
    public Map<Boolean, String> insertGenerator(@RequestParam(value = "projectId") String projectId,
                                                @RequestParam(value = "userName") String userName,
                                                @RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "orgId") String orgId,
                                                @RequestBody Generator generator) {
        GeneratorManage generatorManage = new GeneratorManage();
        return generatorManage.insertGenerator(projectId, userName, userId, orgId, generator);
    }

    @RequestMapping("/deleteGenerator")
    public Map<Boolean, String> deleteGenerator(@RequestParam(value = "projectId") String projectId,
                                                @RequestParam(value = "userName") String userName,
                                                @RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "devId") String devId) {
        GeneratorManage generatorManage = new GeneratorManage();
        return generatorManage.deleteGenerator(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateGenerator")
    public Map<Boolean, String> updateGenerator(@RequestParam(value = "projectId") String projectId,
                                                @RequestParam(value = "userName") String userName,
                                                @RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "devId") String devId,
                                                @RequestBody Map<String, String> changNameAndValueMap) {
        GeneratorManage generatorManage = new GeneratorManage();
        return generatorManage.updateGenerator(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectGeneratorInfoByUuid")
    public Generator selectGeneratorInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                               @RequestParam(value = "userName") String userName,
                                               @RequestParam(value = "userId") String userId,
                                               @RequestParam(value = "devId") String devId) {
        GeneratorManage generatorManage = new GeneratorManage();
        return generatorManage.selectGeneratorInfoByUuid(projectId, userName, userId, devId);
    }


}
