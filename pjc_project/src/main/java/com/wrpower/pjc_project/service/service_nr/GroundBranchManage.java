package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.entity.GroundBranch;
import com.wrpower.pjc_project.service.ReadConfigFile;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroundBranchManage {

//    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    // 这个用来记录 对地支路类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> groundBranchAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("groundbranch");
    // 对地支路类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> groundBranchAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("groundbranch");
    private Map<String, String> groundBranchNameAndIdMap = selectGroundBranchNameAndIdMap();

//    static {
//        m_nrdbAccess.setProxyIp("10.33.3.31");
//    }

    public static Map<String, String> selectGroundBranchNameAndIdMap() {
        Map<String, String> groundBranchNameAndIdMap = new HashMap<>();
        String sql = " select id, nameuuid from ZJ_DEV_GROUNDBRANCH_G ";
        try {
            List<Map<String, Object>> map = NRDBAccessManage.NRDBAccess().queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                groundBranchNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("groundBranchNameAndIdMap:" + e.getMessage());
        }
        return groundBranchNameAndIdMap;
    }

    /**
     * 根据uuid查询对地支路信息
     *
     * @param uuid String
     * @return GroundBranch
     */
    public GroundBranch selectGroundBranchInfoByUuid(String projectId, String userName, String userId, String uuid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        GroundBranch groundBranch = new GroundBranch();
        if (uuid == null)
            return groundBranch;
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        String sqlStr = "select * " +
                "from ZJ_DEV_GROUNDBRANCH_G " +
                "where id = '" + uuid + "' ";
        NRBeanRowMapper<GroundBranch> org_GroundBranch = new NRBeanRowMapper<>(GroundBranch.class);

        try {
            groundBranch = NRDBAccessManage.NRDBAccess().queryForObject(sqlStr, null, org_GroundBranch);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        groundBranch.setInservicedate(BasicManage.longToDataStr(groundBranch.getInservicedate()));
        groundBranch.setOutservicedate(BasicManage.longToDataStr(groundBranch.getOutservicedate()));
        groundBranch.setServicestatus(BasicManage.devStateCodeToString(groundBranch.getServicestatus()));

        System.out.println("==========" + "id:" + groundBranch.getUuid() + "  name:" + groundBranch.getNameuuid() + "time:" + groundBranch.getInservicedate() + " " + groundBranch.getOutservicedate());

        return groundBranch;
    }

    /**
     * 通过工程id，查询对地支路信息
     * 查询返回所有对地支路实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectGroundBranchList(String projectId, String userName, String userId) {
        List<Object> groundBranchList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
        sqlList.add("select *  " +
                "from ZJ_DEV_GROUNDBRANCH_G ");
        NRBeanRowMapper<GroundBranch> org_groundBranch = new NRBeanRowMapper<>(GroundBranch.class);

        try {
            for (String sqlStr : sqlList) {
                groundBranchList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_groundBranch));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : groundBranchList) {
            GroundBranch groundBranch = (GroundBranch) object;
            groundBranch.setInservicedate(BasicManage.longToDataStr(groundBranch.getInservicedate()));
            groundBranch.setOutservicedate(BasicManage.longToDataStr(groundBranch.getOutservicedate()));
            groundBranch.setServicestatus(BasicManage.devStateCodeToString(groundBranch.getServicestatus()));
            System.out.println("==========" + "id:" + groundBranch.getUuid() + "  name:" + groundBranch.getNameuuid());
        }
        return groundBranchList;
    }


    /**
     * 通过工程id，查询对地支路信息
     * 查询返回所有对地支路实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectGroundBranchListByIdList(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> groundBranchList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        String idListStr = BasicManage.join(keyIdList);
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * " +
                "from ZJ_DEV_GROUNDBRANCH_G " +
                "where id in (" + idListStr + ") ");
        NRBeanRowMapper<GroundBranch> org_groundBranch = new NRBeanRowMapper<>(GroundBranch.class);
        try {
            for (String sqlStr : sqlList) {
                groundBranchList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_groundBranch));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : groundBranchList) {
            GroundBranch groundBranch = (GroundBranch) object;
            groundBranch.setInservicedate(BasicManage.longToDataStr(groundBranch.getInservicedate()));
            groundBranch.setOutservicedate(BasicManage.longToDataStr(groundBranch.getOutservicedate()));
            groundBranch.setServicestatus(BasicManage.devStateCodeToString(groundBranch.getServicestatus()));
            System.out.println("==========" + "id:" + groundBranch.getUuid() + "  name:" + groundBranch.getNameuuid());
        }
        return groundBranchList;
    }


    /**
     * 更新对地支路信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId            String
     * @param userName             String
     * @param userId               String
     * @param changNameAndValueMap String
     * @return List<Boolean>
     */
    public Map<Boolean, String> updateGroundBranch(String projectId, String userName, String userId, String GroundBranchId, Map<String, String> changNameAndValueMap) {

        Map<Boolean, String> resMap = new HashMap<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(GroundBranchId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {
            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            Pair<String, String> tableAndColumName = groundBranchAttToDkyTableMap.get(columnName);
            String dkyTable = tableAndColumName.getKey();
            String dkyColumnName = tableAndColumName.getValue();
            columnInfo.setColumn_name(dkyColumnName);

            // 根据 不同的列名 获取 类型 getColumn_type
            if ("voltagelevel".equals(columnName))       //前端传过来的是电压等级  而不是 code
            {
                columnValue = BasicManage.volNameToCodeMap.get(columnValue);
            }
            if ("inservicedate".equals(columnName) || "outservicedate".equals(columnValue)) {
                columnValue = BasicManage.shortDateStrToLongDateStr(columnValue);
            }
            //这里应该根据不同的列 进行值的转化 成调控云要求的数值
            columnInfo.setColumn_value(columnValue);

            Integer type = groundBranchAttAndTypeMap.get(columnName);
            columnInfo.setColumn_type(type);

            if (!recordColumnInfoMap.containsKey(dkyTable)) {
                List<RecordColumnInfo> recordColumnInfoList = new ArrayList<>();
                recordColumnInfoList.add(columnInfo);
                recordColumnInfoMap.put(dkyTable, recordColumnInfoList);
            } else {
                recordColumnInfoMap.get(dkyTable).add(columnInfo);
            }
        }

        for (Map.Entry<String, List<RecordColumnInfo>> entry : recordColumnInfoMap.entrySet()) {
            String dkyTableName = entry.getKey();
            List<RecordColumnInfo> recordColumnInfos = entry.getValue();
            recordColumnInfos.add(idColumnInfo);

            RecordInfo recordInfo = new RecordInfo();
            recordInfo.setRecordColumnInfo(entry.getValue());
            recordInfo.setRegion_id("330000");
            if (!tableRecordInfoMap.containsKey(dkyTableName)) {
                List<RecordInfo> recordInfoList = new ArrayList<>();
                recordInfoList.add(recordInfo);
                tableRecordInfoMap.put(dkyTableName, recordInfoList);
            } else {
                tableRecordInfoMap.get(dkyTableName).add(recordInfo);
            }
        }

//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo); //根据实际情况修改工程ID
        List<RecordModifyResult> result;
        try {
            for (Map.Entry<String, List<RecordInfo>> entry : tableRecordInfoMap.entrySet()) {
                result = NRDBAccessManage.NRDBAccess().UpdateRecord(entry.getKey(), entry.getValue());
                for (RecordModifyResult re : result) {
                    resMap.put(re.isIs_success(), entry.getKey() + re.getErr_msg());
                    System.out.println(entry.getKey() + "===================" + re.getErr_msg() + " " + re.isIs_success());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resMap;
    }


    public Map<Boolean, String> insertGroundBranch(String projectId, String userName, String userId, String orgId, GroundBranch groundBranch) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        List<RecordModifyResult> result;
        Map<Boolean, String> resMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 变压器重名 直接返回 <false,msg>
        if (groundBranchAttToDkyTableMap == null || groundBranchAttAndTypeMap == null) {
            resMap.put(false, "attrToDkyTable文件中groundBranch表数据为空！");
            return resMap;
        }

        if (groundBranchNameAndIdMap.containsKey(groundBranch.getNameuuid())) {
            resMap.put(false, "对地支路重名！");
            return resMap;
        }

        for (Field field : groundBranch.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(groundBranch);
                if (fieldValue == null)
                    continue;
                if (!groundBranchAttToDkyTableMap.containsKey(fieldName) || !groundBranchAttAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = groundBranchAttToDkyTableMap.get(fieldName);
                //  Pair<  调控云表名  表中属性 >
                if (pair == null)
                    continue;

                if ("voltagelevel".equals(fieldName))       //前端传过来的是电压等级  而不是 code
                {
                    fieldValue = BasicManage.volNameToCodeMap.get(fieldName);
                }
                if ("inservicedate".equals(fieldName) || "outservicedate".equals(fieldName)) {
                    fieldValue = BasicManage.shortDateStrToLongDateStr(fieldValue);
                }
                String dkyTableName = pair.getKey();
                String dkyColumnName = pair.getValue();
                int dkyColumnType = groundBranchAttAndTypeMap.get(fieldName);
                RecordColumnInfo columnInfo = new RecordColumnInfo();
                columnInfo.setColumn_name(dkyColumnName);
                columnInfo.setColumn_value(fieldValue);
                columnInfo.setColumn_type(dkyColumnType);
                System.out.println("fieldName: " + fieldName + " fieldValue:" + fieldValue + " dkyTableName:" + dkyTableName + " setColumn_name:" + dkyColumnName + " dkyColumnType:" + dkyColumnType);

                if (!recordColumnInfoMap.containsKey(dkyTableName)) {
                    List<RecordColumnInfo> recordColumnInfoList = new ArrayList<>();
                    recordColumnInfoList.add(columnInfo);
                    recordColumnInfoMap.put(dkyTableName, recordColumnInfoList);
                } else
                    recordColumnInfoMap.get(dkyTableName).add(columnInfo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //对地支路 直接插入G表
        List<RecordColumnInfo> recordList_g = recordColumnInfoMap.get("ZJ_DEV_GROUNDBRANCH_G");
        RecordInfo recordInfo_g = new RecordInfo();
        recordInfo_g.setRecordColumnInfo(recordList_g);
        recordInfo_g.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_g);

        String insertGroundBranchId = "";
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = NRDBAccessManage.NRDBAccess().InsertRecord("ZJ_DEV_GROUNDBRANCH_G", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("ZJ_DEV_GROUNDBRANCH_G===================" + re.getErr_msg() + " " + re.isIs_success());
                if (!re.isIs_success()) {
                    resMap.put(false, re.getErr_msg());
                    return resMap;
                }
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertGroundBranchId = rt.get(j);
                    groundBranchNameAndIdMap.put(groundBranch.getNameuuid(), insertGroundBranchId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        resMap.put(true, insertGroundBranchId);
        return resMap;
    }


    public Map<Boolean, String> deleteGroundBranch(String projectId, String userName, String userId, String groundBranchId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<Boolean, String> resMap = new HashMap<>();
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(groundBranchId);
        columnInfo.setIs_key(true);
        columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        recordColumnInfo.add(columnInfo);
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordColumnInfo(recordColumnInfo);
        recordInfo.setRegion_id("330000");
        recordInfoList.add(recordInfo);

//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;
        try {
            result = NRDBAccessManage.NRDBAccess().DeleteRecord("ZJ_DEV_GROUNDBRANCH_G", recordInfoList);
            for (RecordModifyResult re : result) {
                resMap.put(re.isIs_success(), re.getErr_msg());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resMap;
    }


}
