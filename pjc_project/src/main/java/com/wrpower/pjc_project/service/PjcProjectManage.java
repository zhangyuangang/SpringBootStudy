package com.wrpower.pjc_project.service;

import com.nari.cloud.fidbaccess.wrapper.FIDBAccess;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.fidbaccess.core.FIDBAccessImpl;
import com.nari.cloud.fidbaccess.model.*;

import java.text.SimpleDateFormat;
import java.util.*;

import com.wrpower.pjc_project.domain.PjcProject;

public class PjcProjectManage {

    private static FIDBAccess m_fidbAccess = new FIDBAccessImpl();
    private Map<String, String> projectMap = getProjectMap();
    private Map<String, String> ownerMap = BasicManage.ownerNameToCodeMap;

    static {
        m_fidbAccess.setProxyIp("10.33.3.31");
    }

    //获取所有投运工程--projectMap
    private Map<String, String> getProjectMap() {
        Map<String, String> projectMap = new HashMap<>();
        try {
            List<FutureProjectInfo> list = m_fidbAccess.queryForFutureProjectList();
            for (FutureProjectInfo futureProjectInfo : list) {
                String name = futureProjectInfo.getProject_name();
                String id = futureProjectInfo.getProject_id();
                projectMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("queryForFutureProjectList发生错误:" + e.getMessage());
            projectMap.put("error", "queryForFutureProjectList发生错误:" + e.getMessage());
        }
        return projectMap;
    }

    //获得所有工程属性列表
    public List<PjcProject> getFutureProjectList() {
        List<PjcProject> projectList = new ArrayList<>();
        try {
            List<FutureProjectInfo> list = m_fidbAccess.queryForFutureProjectList();
            for (FutureProjectInfo futureProjectInfo : list) {
                if (futureProjectInfo.getProject_type().equals(MgDefineHeader.MG_PROJECT_FUTURE)) {
                    PjcProject myProjectInfo = futureProToMyPro(futureProjectInfo);
                    projectList.add(myProjectInfo);
                }
            }
        } catch (NRDataAccessException e) {
            System.out.println("queryForFutureProjectList发生错误:" + e.getMessage());
        }
        return projectList;
    }

    //根据工程id，获得某个工程属性
    public PjcProject getFutureProject(String projectId) {
        PjcProject pjcProject = new PjcProject();
        try {
            FutureProjectInfo futureProjectInfo = m_fidbAccess.queryForFutureProject(projectId);
            pjcProject = futureProToMyPro(futureProjectInfo);
        } catch (NRDataAccessException e) {
            System.out.println("queryForFutureProject发生错误:" + e.getMessage());
        }
        return pjcProject;
    }


    //批量新建投运工程，成功返回新建工程id列表
    public Map<String, List<String>> insertFutureProject(List<PjcProject> myProList) {
        List<String> resTrueList = new ArrayList<>();
        List<String> resFalseList = new ArrayList<>();
        Map<String, List<String>> resMap = new HashMap<>();
        List<FutureProjectInfo> futureProList = new ArrayList<>();
        for (PjcProject pjcProject : myProList) {
            String projectName = pjcProject.getName();
            if (projectMap.containsKey(projectName)) {
                resFalseList.add(projectName + "工程重名!");
            } else {
                FutureProjectInfo futureProjectInfo = myProToFuturePro(pjcProject);
                futureProList.add(futureProjectInfo);
            }
        }
        try {
            List<FuncReturn> results = m_fidbAccess.InsertFutureProject(futureProList);
            for (FuncReturn result : results) {
                if (result.isRetCode()) {
                    resTrueList.add(result.getProjectId());
                } else {
                    resFalseList.add(result.getFuncErrMsg() + " error");
                }
            }
        } catch (NRDataAccessException e) {
            System.out.println("InsertFutureProject发生错误:" + e.getMessage());
        }
        resMap.put("success", resTrueList);
        resMap.put("failure", resFalseList);
        return resMap;
    }


    //批量更新投运工程属性，成功返回工程id列表
    // 工程一旦创建只能修改以下属性：
    // 1. 工程名称 project_name
    // 2. 未来时段 forecast_start_time,forecast_end_time
    // 3. 描述信息 desc
    // 4. 用户信息 createUserInfo
    public List<Boolean> updateFutureProject(List<PjcProject> myProList) {
        List<Boolean> listRes = new ArrayList<>();
        List<FutureProjectUpdateInfo> updateProList = new ArrayList<>();
        for (PjcProject pjcProject : myProList) {
            FutureProjectUpdateInfo futureProjectUpdateInfo = new FutureProjectUpdateInfo();

            futureProjectUpdateInfo.setProject_id(pjcProject.getUuid());
            if (pjcProject.getName() != null) {
                futureProjectUpdateInfo.setProject_name(pjcProject.getName());
            }
            if (pjcProject.getInservicedate() != null) {
                futureProjectUpdateInfo.setForecast_start_time(BasicManage.shortDataStrToDate(pjcProject.getInservicedate()));
            }
            if (pjcProject.getOutservicedate() != null) {
                futureProjectUpdateInfo.setForecast_end_time(BasicManage.shortDataStrToDate(pjcProject.getOutservicedate()));
            }
            CreateUserInfo createUserInfo = new CreateUserInfo();
            createUserInfo.setUserId("1");
            createUserInfo.setUserName("test_user");
            futureProjectUpdateInfo.setCreateUserInfo(createUserInfo);
            futureProjectUpdateInfo.setDesc(pjcProject.getName());
            updateProList.add(futureProjectUpdateInfo);
        }
        try {
            List<FuncReturn> results = m_fidbAccess.UpdateFutureProject(updateProList);
            for (FuncReturn funcReturn : results) {
                listRes.add(funcReturn.isRetCode());
            }
        } catch (NRDataAccessException e) {
            System.out.println("UpdateFutureProject发生错误:" + e.getMessage());
        }
        return listRes;
    }

    //批量删除投运工程，成功返回retCode列表
    public List<Boolean> deleteFutureProject(List<String> projectIdList) {
        List<Boolean> listRes = new ArrayList<>();
        try {
            List<FuncReturn> result = m_fidbAccess.DeleteFutureProject(projectIdList, MgDefineHeader.MG_PROJECT_DELETE);
            for (FuncReturn funcReturn : result) {
                boolean retCode = funcReturn.isRetCode();
                listRes.add(retCode);
            }
        } catch (NRDataAccessException e) {
            System.out.println("UpdateFutureProject发生错误:" + e.getMessage());
        }
        return listRes;
    }


    //从FutureProjectInfo映射到MyProjectInfo
    private PjcProject futureProToMyPro(FutureProjectInfo futureProjectInfo) {
        PjcProject myProjectInfo = new PjcProject();
        myProjectInfo.setUuid(futureProjectInfo.getProject_id());//如：PROJECT_F_1
        myProjectInfo.setAuthor(futureProjectInfo.getCreateUserInfo().getUserName());
        myProjectInfo.setName(futureProjectInfo.getProject_name());
        myProjectInfo.setInservicedate(BasicManage.dataToShortDataStr(futureProjectInfo.getForecast_start_time()));
        myProjectInfo.setOutservicedate(BasicManage.dataToShortDataStr(futureProjectInfo.getForecast_end_time()));
        myProjectInfo.setOwner(futureProjectInfo.getArea_code().substring(0, 2));//如：宁波地调
        myProjectInfo.setOwneruuid(ownerMap.get(futureProjectInfo.getArea_code()));
        myProjectInfo.setStatus(BasicManage.projectStatusInt2CN(futureProjectInfo.getStatus_flag()));
        return myProjectInfo;
    }

    //从MyProjectInfo映射到FutureProjectInfo
    private FutureProjectInfo myProToFuturePro(PjcProject myProjectInfo) {
        FutureProjectInfo futureProjectInfo = new FutureProjectInfo();
        //createUserInfo目前设置为固定
        CreateUserInfo createUserInfo = new CreateUserInfo();
        createUserInfo.setUserId("1");
        createUserInfo.setUserName("test_user");
        futureProjectInfo.setCreateUserInfo(createUserInfo);

        if (myProjectInfo.getInservicedate() != null)
            futureProjectInfo.setForecast_start_time(BasicManage.shortDataStrToDate(myProjectInfo.getInservicedate()));

        if (myProjectInfo.getName() != null)
            futureProjectInfo.setProject_name(myProjectInfo.getName());

        if (myProjectInfo.getOutservicedate() != null)
            futureProjectInfo.setForecast_end_time(BasicManage.shortDataStrToDate(myProjectInfo.getOutservicedate()));

        if (myProjectInfo.getOwneruuid() != null)
            futureProjectInfo.setArea_code(myProjectInfo.getOwneruuid());

        futureProjectInfo.setProject_type(MgDefineHeader.MG_PROJECT_FUTURE);// 工程类型，投运计划都是MG_PROJECT_FUTURE
        futureProjectInfo.setIs_reform(MgDefineHeader.MG_PROJECT_FUTURE_REFORM);
        futureProjectInfo.setStatus_flag(BasicManage.projectStatusCN2Int(myProjectInfo.getStatus()));
        return futureProjectInfo;
    }


    public Map<String, List<String>> getProjectDetailId(String projectId) {
        List<String> dkyTable = BasicManage.getDkyTableList();
        Map<String, List<String>> resultMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(projectId);
        try {
            List<EquipListInfo> resultList = m_fidbAccess.QueryProjectEquipInfoProject(list);
            for (EquipListInfo equipListInfo : resultList) {
                System.out.println("***********projectId:" + equipListInfo.getFuncReturn().getProjectId());
                System.out.println("***********msg:" + equipListInfo.getFuncReturn().getFuncErrMsg());
                System.out.println("***********retCode:" + equipListInfo.getFuncReturn().isRetCode());
                System.out.println("***********equipListInfo.getEquipInfo().size():" + equipListInfo.getEquipInfo().size());
                if (equipListInfo.getFuncReturn().isRetCode()) {
                    for (int k = 0; k < equipListInfo.getEquipInfo().size(); k++) {
                        String tableName = equipListInfo.getEquipInfo().get(k).getEquipTableName();
                        if (!dkyTable.contains(tableName))
                            continue;
                        List<String> idList = equipListInfo.getEquipInfo().get(k).getKeyId();
                        System.out.println("tableName:" + equipListInfo.getEquipInfo().get(k).getEquipTableName() + "----" + (k + 1));
                        System.out.println("key_id:" + equipListInfo.getEquipInfo().get(k).getKeyId() + "----" + (k + 1));
                        resultMap.put(tableName, idList);
                    }
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resultMap;
    }


    public Map<String, List<Object>> queryProjectDetailInfoMap(String projectId, String userName, String userId) {

        Map<String, List<String>> devAndIdMap = getProjectDetailId(projectId);
        Map<String, List<Object>> objectMap = new HashMap<>();

        List<String> substationIdList = new ArrayList<>();
        if( devAndIdMap.containsKey("SG_CON_PLANT_B")) {
            substationIdList.addAll( devAndIdMap.get("SG_CON_PLANT_B") );
            devAndIdMap.remove("SG_CON_PLANT_B");
        }
        if( devAndIdMap.containsKey("SG_CON_SUBSTATION_B")){
            substationIdList.addAll( devAndIdMap.get("SG_CON_SUBSTATION_B") );
            devAndIdMap.remove("SG_CON_SUBSTATION_B");
        }
        if( devAndIdMap.containsKey("SG_CON_CONVERSUBSTATION_B")) {
            substationIdList.addAll(devAndIdMap.get("SG_CON_CONVERSUBSTATION_B"));
            devAndIdMap.remove("SG_CON_CONVERSUBSTATION_B");
        }
        devAndIdMap.put("SG_CON_SUBSTATION_B",substationIdList);

        for (Map.Entry<String, List<String>> entry : devAndIdMap.entrySet()) {
            String dkyTableName = entry.getKey();
            List<String> keyIdList = entry.getValue();
            switch (dkyTableName) {
                case "SG_DEV_ACLINE_B":             //线路
                    break;
                case "SG_DEV_BREAKER_B":            //开关
                    break;
                case "SG_DEV_BUSBAR_B":             //母线
                    BusbarManage busbarManage = new BusbarManage();
                    objectMap.put("busbar", busbarManage.selectBusbarListById(projectId, userName, userId, keyIdList));
                    break;
                case "SG_DEV_SHUNTREACTOR_B":       //容抗器
                    break;
                case "SG_DEV_GENERATOR_B":          //发电机
                    break;
                case "SG_DEV_SERIESREACTOR_B":      //线路高抗
                    break;
                case "SG_DEV_SERIESCAPACITOR_B":    //线路串补
                    break;
                case "SG_CON_SUBSTATION_B":         //厂站
                    SubstationManage substationManage = new SubstationManage();
                    objectMap.put("substation", substationManage.selectSubstationList(projectId, userName, userId));
                    break;
                case "SG_DEV_PWRTRANSFM_B":         //变压器
                    break;
            }
        }
        List<Object> objectList = new ArrayList<>();
        objectMap.put("acline", objectList);
        objectMap.put("breaker", objectList);
        objectMap.put("change", objectList);
        objectMap.put("compensator", objectList);
        objectMap.put("generator", objectList);
        objectMap.put("groundbranch", objectList);
        objectMap.put("linepcompensator", objectList);
        objectMap.put("linescompensator", objectList);
        objectMap.put("littlegen", objectList);
        objectMap.put("load", objectList);
        objectMap.put("outservice", objectList);
        objectMap.put("runway", objectList);
        objectMap.put("transformer", objectList);
        return objectMap;
    }

}
