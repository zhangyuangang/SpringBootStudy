package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.fidbaccess.wrapper.FIDBAccess;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.fidbaccess.core.FIDBAccessImpl;
import com.nari.cloud.fidbaccess.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wrpower.pjc_project.entity.PjcProject;

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

    //按时间获取工程名和ID
    public Map<String, String> getFutureProjectMapByDate(String dateMin, String dateMax) {
        Map<String, String> projectMap = new HashMap<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = formatter.parse(dateMin);
            endDate = formatter.parse(dateMax);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (beginDate == null || endDate == null) {
            return projectMap;
        }

        try {
            List<FutureProjectInfo> list = m_fidbAccess.queryForFutureProjectList();
            for (FutureProjectInfo futureProjectInfo : list) {
                if (futureProjectInfo.getProject_type().equals(MgDefineHeader.MG_PROJECT_FUTURE) &&
                    futureProjectInfo.getForecast_start_time().compareTo(beginDate) >= 0 &&
                    futureProjectInfo.getForecast_start_time().compareTo(endDate) < 0 ) {
                    String name = futureProjectInfo.getProject_name();
                    String id = futureProjectInfo.getProject_id();
                    projectMap.put(name, id);
                }
            }
        } catch (
                NRDataAccessException e) {
            System.out.println("queryForFutureProjectList发生错误:" + e.getMessage());
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
    public Map<Boolean, String> insertFutureProject(PjcProject pjcProject, String userName, String userId, String orgId) {
        Map<Boolean, String> resMap = new HashMap<>();
        List<FutureProjectInfo> futureProList = new ArrayList<>();
        String projectName = pjcProject.getName();
        if (projectMap.containsKey(projectName)) {
            resMap.put(false, projectName + "工程重名!");
        } else {
            FutureProjectInfo futureProjectInfo = myProToFuturePro(pjcProject, userName, userId, orgId);
            futureProList.add(futureProjectInfo);
        }

        try {
            List<FuncReturn> results = m_fidbAccess.InsertFutureProject(futureProList);
            for (FuncReturn result : results) {
                if (result.isRetCode()) {
                    resMap.put(true, result.getProjectId());
                } else {
                    resMap.put(false, result.getFuncErrMsg() + " error");
                }
            }
        } catch (NRDataAccessException e) {
            System.out.println("InsertFutureProject发生错误:" + e.getMessage());
        }
        return resMap;
    }


    //批量更新投运工程属性，成功返回工程id列表
    // 工程一旦创建只能修改以下属性：
    // 1. 工程名称 project_name
    // 2. 未来时段 forecast_start_time,forecast_end_time
    // 3. 描述信息 desc
    public Map<Boolean, String> updateFutureProject(String projectId, String userName, String userId, PjcProject pjcProject) {
        Map<Boolean, String> resMap = new HashMap<>();
        List<FutureProjectUpdateInfo> updateProList = new ArrayList<>();
        FutureProjectUpdateInfo futureProjectUpdateInfo = new FutureProjectUpdateInfo();
        CreateUserInfo createUserInfo = new CreateUserInfo();
        createUserInfo.setUserId(userId);
        createUserInfo.setUserName(userName);
        futureProjectUpdateInfo.setCreateUserInfo(createUserInfo);
        futureProjectUpdateInfo.setProject_id(projectId);

        if (pjcProject.getName() != null) {
            futureProjectUpdateInfo.setProject_name(pjcProject.getName());
            futureProjectUpdateInfo.setDesc(pjcProject.getName());
        }
        if (pjcProject.getInservicedate() != null) {
            futureProjectUpdateInfo.setForecast_start_time(BasicManage.shortDataStrToDate(pjcProject.getInservicedate()));
        }
        if (pjcProject.getOutservicedate() != null) {
            futureProjectUpdateInfo.setForecast_end_time(BasicManage.shortDataStrToDate(pjcProject.getOutservicedate()));
        }
        updateProList.add(futureProjectUpdateInfo);

        try {
            List<FuncReturn> results = m_fidbAccess.UpdateFutureProject(updateProList);
            for (FuncReturn funcReturn : results) {
                resMap.put(funcReturn.isRetCode(), funcReturn.getFuncErrMsg());
            }
        } catch (NRDataAccessException e) {
            System.out.println("UpdateFutureProject发生错误:" + e.getMessage());
        }
        return resMap;
    }

    //批量删除投运工程，成功返回retCode列表
    public Map<Boolean, String> deleteFutureProject(String projectId) {
        Map<Boolean, String> resMap = new HashMap<>();
        List<String> projectIdList = new ArrayList<>();
        projectIdList.add(projectId);
        try {
            List<FuncReturn> result = m_fidbAccess.DeleteFutureProject(projectIdList, MgDefineHeader.MG_PROJECT_DELETE);
            for (FuncReturn funcReturn : result) {
                resMap.put(funcReturn.isRetCode(), funcReturn.getFuncErrMsg());
            }
        } catch (NRDataAccessException e) {
            System.out.println("UpdateFutureProject发生错误:" + e.getMessage());
        }
        return resMap;
    }

    //从FutureProjectInfo映射到MyProjectInfo
    private PjcProject futureProToMyPro(FutureProjectInfo futureProjectInfo) {
        PjcProject myProjectInfo = new PjcProject();
        myProjectInfo.setUuid(futureProjectInfo.getProject_id());//如：PROJECT_F_1
        myProjectInfo.setAuthor(futureProjectInfo.getCreateUserInfo().getUserName());
        myProjectInfo.setName(futureProjectInfo.getProject_name());
        myProjectInfo.setInservicedate(BasicManage.dataToShortDataStr(futureProjectInfo.getForecast_start_time()));
        myProjectInfo.setOutservicedate(BasicManage.dataToShortDataStr(futureProjectInfo.getForecast_end_time()));
        // 正式的时候，需要删除
        myProjectInfo.setOwner(futureProjectInfo.getArea_code().substring(0, 2));//如：宁波地调
        myProjectInfo.setOwneruuid(ownerMap.get(futureProjectInfo.getArea_code()));
        myProjectInfo.setStatus(BasicManage.projectStatusInt2CN(futureProjectInfo.getStatus_flag()));
        return myProjectInfo;
    }

    //从MyProjectInfo映射到FutureProjectInfo
    private FutureProjectInfo myProToFuturePro(PjcProject myProjectInfo, String userName, String userId, String orgid) {
        FutureProjectInfo futureProjectInfo = new FutureProjectInfo();
        //createUserInfo目前设置为固定
        CreateUserInfo createUserInfo = new CreateUserInfo();

        createUserInfo.setUserId(userId);
        createUserInfo.setUserName(userName);
        futureProjectInfo.setCreateUserInfo(createUserInfo);

        if (myProjectInfo.getInservicedate() != null)
            futureProjectInfo.setForecast_start_time(BasicManage.shortDataStrToDate(myProjectInfo.getInservicedate()));

        if (myProjectInfo.getName() != null)
            futureProjectInfo.setProject_name(myProjectInfo.getName());

        if (myProjectInfo.getOutservicedate() != null)
            futureProjectInfo.setForecast_end_time(BasicManage.shortDataStrToDate(myProjectInfo.getOutservicedate()));

        if (myProjectInfo.getOwneruuid() != null)
            futureProjectInfo.setArea_code(orgid.substring(4));

        futureProjectInfo.setCreate_time(new Date());

        futureProjectInfo.setProject_type(MgDefineHeader.MG_PROJECT_FUTURE);// 工程类型，投运计划都是MG_PROJECT_FUTURE
        futureProjectInfo.setIs_reform(MgDefineHeader.MG_PROJECT_FUTURE_REFORM);
        futureProjectInfo.setStatus_flag(MgProjectStatus.MG_PROJECT_EDIT); // 新建工程 默认是编辑中
        return futureProjectInfo;
    }

    // 查询速度太慢，是否能优化
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

        List<Object> objectList = new ArrayList<>();
        objectMap.put("acline", objectList);
        objectMap.put("breaker", objectList);
        objectMap.put("busbar", objectList);
        objectMap.put("compensator", objectList);
        objectMap.put("generator", objectList);
        objectMap.put("linepcompensator", objectList);
        objectMap.put("linescompensator", objectList);
        objectMap.put("substation", objectList);
        objectMap.put("transformer", objectList);
        objectMap.put("groundbranch", objectList);
        objectMap.put("littlegen", objectList);

        for (Map.Entry<String, List<String>> entry : devAndIdMap.entrySet()) {
            String dkyTableName = entry.getKey();
            List<String> keyIdList = entry.getValue();
            switch (dkyTableName) {
                case "SG_DEV_ACLINE_B":             //线路
                    AclineManage aclineManage = new AclineManage();
                    objectMap.put("acline", aclineManage.selectAclineListByIdList(projectId, userName, userId, keyIdList));
                    break;
                case "SG_DEV_BREAKER_B":            //开关
                    BreakerManage breakerManage = new BreakerManage();
                    objectMap.put("breaker", breakerManage.selectBreakerListByIdList(projectId, userName, userId, keyIdList));
                    break;
                case "SG_DEV_BUSBAR_B":             //母线
                    BusbarManage busbarManage = new BusbarManage();
                    objectMap.put("busbar", busbarManage.selectBusbarListByIdList(projectId, userName, userId, keyIdList));
                    break;
                case "SG_DEV_SHUNTREACTOR_B":       //容抗器   并联电抗器表 一部分包含低压容抗器 一部分包含线路高抗
                    //低压容抗器
                    CompensatorManage compensatorManage = new CompensatorManage();
                    objectMap.put("compensator", compensatorManage.selectCompensatorListByIdList(projectId, userName, userId, keyIdList));
                    //线路高抗
                    LinepCompensatorManage linepcompensatorManage = new LinepCompensatorManage();
                    objectMap.put("linepcompensator", linepcompensatorManage.selectLinepCompensatorListByIdList(projectId, userName, userId, keyIdList));
                    break;
                case "SG_DEV_SHUNTCAPACITOR_B":       //并联电容器   并联电抗器表包含低压容抗器
                    //低压容抗器
                    CompensatorManage manage = new CompensatorManage();
                    objectMap.put("compensator", manage.selectCompensatorListByIdList(projectId, userName, userId, keyIdList));
                    break;
                case "SG_DEV_GENERATOR_B":          //发电机
                    GeneratorManage generatorManage = new GeneratorManage();
                    objectMap.put("generator", generatorManage.selectGeneratorListByIdList(projectId, userName, userId, keyIdList));
                    break;
                case "SG_DEV_SERIESCAPACITOR_B":    //线路串补
                    LinesCompensatorManage linesCompensatorManage = new LinesCompensatorManage();
                    objectMap.put("linescompensator", linesCompensatorManage.selectLinesCompensatorListByIdList(projectId, userName, userId, keyIdList));
                    break;
                case "SG_CON_SUBSTATION_B":             //厂站
                case "SG_CON_PLANT_B":                  //厂站
                case "SG_CON_CONVERSUBSTATION_B":       //厂站
                    SubstationManage substationManage = new SubstationManage();
                    objectMap.put("substation", substationManage.selectSubstationListByIdList(projectId, userName, userId, keyIdList));
                    break;
                case "SG_DEV_PWRTRANSFM_B":         //变压器
                    TransformerManage transformerManage = new TransformerManage();
                    objectMap.put("transformer", transformerManage.selectTransformerListByIdList(projectId, userName, userId, keyIdList));
                    break;
//                case "ZJ_DEV_LITTLEGEN_G":         //小电源
//                    LittleGenManage littleGenManage = new LittleGenManage();
//                    objectMap.put("littlegen", littleGenManage.selectLittleGenListByIdList(projectId, userName, userId, keyIdList));
//                    break;
//                case "ZJ_DEV_GROUNDBRANCH_G":         //对地支路
//                    GroundBranchManage groundBranchManage = new GroundBranchManage();
//                    objectMap.put("groundbranch", groundBranchManage.selectGroundBranchListByIdList(projectId, userName, userId, keyIdList));
//                    break;
            }
        }
        return objectMap;
    }


    public Map<String, List<Object>> queryProjectDetailInfoMapByDate(String projectId, String userName, String userId, String dateMin, String dateMax) {

        Map<String, List<String>> devAndIdMap = getProjectDetailId(projectId);
        Map<String, List<Object>> objectMap = new HashMap<>();

        List<Object> objectList = new ArrayList<>();
        objectMap.put("acline", objectList);
        objectMap.put("breaker", objectList);
        objectMap.put("busbar", objectList);
        objectMap.put("compensator", objectList);
        objectMap.put("generator", objectList);
        objectMap.put("linepcompensator", objectList);
        objectMap.put("linescompensator", objectList);
        objectMap.put("substation", objectList);
        objectMap.put("transformer", objectList);
        objectMap.put("groundbranch", objectList);
        objectMap.put("littlegen", objectList);

        for (Map.Entry<String, List<String>> entry : devAndIdMap.entrySet()) {
            String dkyTableName = entry.getKey();
            List<String> keyIdList = entry.getValue();
            switch (dkyTableName) {
                case "SG_DEV_ACLINE_B":             //线路
                    AclineManage aclineManage = new AclineManage();
                    objectMap.put("acline", aclineManage.selectAclineListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
                case "SG_DEV_BREAKER_B":            //开关
                    BreakerManage breakerManage = new BreakerManage();
                    objectMap.put("breaker", breakerManage.selectBreakerListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
                case "SG_DEV_BUSBAR_B":             //母线
                    BusbarManage busbarManage = new BusbarManage();
                    objectMap.put("busbar", busbarManage.selectBusbarListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
                case "SG_DEV_SHUNTREACTOR_B":       //容抗器   并联电抗器表 一部分包含低压容抗器 一部分包含线路高抗
                    //低压容抗器
                    CompensatorManage compensatorManage = new CompensatorManage();
                    objectMap.put("compensator", compensatorManage.selectCompensatorListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    //线路高抗
                    LinepCompensatorManage linepcompensatorManage = new LinepCompensatorManage();
                    objectMap.put("linepcompensator", linepcompensatorManage.selectLinepCompensatorListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
                case "SG_DEV_SHUNTCAPACITOR_B":       //并联电容器   并联电抗器表包含低压容抗器
                    //低压容抗器
                    CompensatorManage manage = new CompensatorManage();
                    objectMap.put("compensator", manage.selectCompensatorListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
                case "SG_DEV_GENERATOR_B":          //发电机
                    GeneratorManage generatorManage = new GeneratorManage();
                    objectMap.put("generator", generatorManage.selectGeneratorListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
                case "SG_DEV_SERIESCAPACITOR_B":    //线路串补
                    LinesCompensatorManage linesCompensatorManage = new LinesCompensatorManage();
                    objectMap.put("linescompensator", linesCompensatorManage.selectLinesCompensatorListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
                case "SG_CON_SUBSTATION_B":             //厂站
                case "SG_CON_PLANT_B":                  //厂站
                case "SG_CON_CONVERSUBSTATION_B":       //厂站
                    SubstationManage substationManage = new SubstationManage();
                    objectMap.put("substation", substationManage.selectSubstationListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
                case "SG_DEV_PWRTRANSFM_B":         //变压器
                    TransformerManage transformerManage = new TransformerManage();
                    objectMap.put("transformer", transformerManage.selectTransformerListByIdListAndDate(projectId, userName, userId, keyIdList, dateMin, dateMax));
                    break;
//                case "ZJ_DEV_LITTLEGEN_G":         //小电源
//                    LittleGenManage littleGenManage = new LittleGenManage();
//                    objectMap.put("littlegen", littleGenManage.selectLittleGenListByIdListAndDate(projectId, userName, userId, keyIdList,dateMin,dateMax));
//                    break;
//                case "ZJ_DEV_GROUNDBRANCH_G":         //对地支路
//                    GroundBranchManage groundBranchManage = new GroundBranchManage();
//                    objectMap.put("groundbranch", groundBranchManage.selectGroundBranchListByIdListAndDate(projectId, userName, userId, keyIdList,dateMin,dateMax));
//                    break;
            }
        }
        return objectMap;
    }

}
