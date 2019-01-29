package com.wrpower.pjc_project.controller;

import com.wrpower.pjc_project.entity.Transformer;
import com.wrpower.pjc_project.service.service_nr.TransformerManage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TransformerController {

    @RequestMapping("/selectTransformer")
    public List<Object> selectTransformer(@RequestParam(value = "projectId") String projectId,
                                          @RequestParam(value = "userName") String userName,
                                          @RequestParam(value = "userId") String userId) {
        TransformerManage transformerManage = new TransformerManage();
        return transformerManage.selectTransformerList(projectId, userName, userId);
    }

    @RequestMapping("/selectTransformerNameAndIdMap")
    public Map<String, String> selectTransformerNameAndIdMap() {
        return TransformerManage.selectTransformerNameAndIdMap();
    }

    @RequestMapping("/insertTransformer")
    public Map<Boolean, String> insertTransformer(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "orgId") String orgId,
                                                  @RequestBody Transformer transformer) {
        TransformerManage transformerManage = new TransformerManage();
        return transformerManage.insertTransformer(projectId, userName, userId, orgId, transformer);
    }

    @RequestMapping("/deleteTransformer")
    public Map<Boolean, String> deleteTransformer(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "devId") String devId) {
        TransformerManage transformerManage = new TransformerManage();
        return transformerManage.deleteTransformer(projectId, userName, userId, devId);
    }

    @RequestMapping("/updateTransformer")
    public Map<Boolean, String> updateTransformer(@RequestParam(value = "projectId") String projectId,
                                                  @RequestParam(value = "userName") String userName,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "devId") String devId,
                                                  @RequestBody Map<String, String> changNameAndValueMap) {
        TransformerManage transformerManage = new TransformerManage();
        return transformerManage.updateTransformer(projectId, userName, userId, devId, changNameAndValueMap);
    }

    @RequestMapping("/selectTransformerInfoByUuid")
    public Transformer selectTransformerInfoByUuid(@RequestParam(value = "projectId") String projectId,
                                                   @RequestParam(value = "userName") String userName,
                                                   @RequestParam(value = "userId") String userId,
                                                   @RequestParam(value = "devId") String devId) {
        TransformerManage transformerManage = new TransformerManage();
        return transformerManage.selectTransformerInfoByUuid(projectId,userName,userId,devId);
    }


}
