package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.domain.Compensator;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.*;

public class CompensatorManage {

    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    // 这个用来记录 容抗器类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> compensatorAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("compensator");
    // 容抗器类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> compensatorAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("compensator");
    private Map<String, String> compensatorNameAndIdMap = selectCompensatorNameAndIdMap();

    static {
        m_nrdbAccess.setProxyIp("10.33.3.31");
    }

    public static Map<String, String> selectCompensatorNameAndIdMap() {
        Map<String, String> compensatorNameAndIdMap = new HashMap<>();
        String sql = " select id, name from SG_DEV_SHUNTREACTOR_B ";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                compensatorNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("compensatorNameAndIdMap:" + e.getMessage());
        }
        return compensatorNameAndIdMap;
    }

    /**
     * 根据uuid查询容抗器信息
     *
     * @param uuid String
     * @return Compensator
     */
    public Compensator selectCompensatorInfoByUuid(String uuid) {
        Compensator compensator = new Compensator();
        if (uuid == null)
            return compensator;

        String sqlStr = "select b.id uuid, " +
                "b.name name, " +
                "b.expiry_date outservicedate, " +
                "b.operate_date inservicedate, " +
                "b.voltage_type voltagelevel, " +
                "b.st_id substationuuid, " +
                "b.running_state servicestatus " +
                "b.capacity_rate design_rateds " +
                "from SG_DEV_SHUNTREACTOR_B b " +
                "where b.id = '" + uuid + "' ";
        NRBeanRowMapper<Compensator> org_compensator = new NRBeanRowMapper<>(Compensator.class);

        try {
            compensator = m_nrdbAccess.queryForObject(sqlStr, null, org_compensator);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return compensator;
    }

    /**
     * 通过工程id，查询容抗器信息
     * 查询返回所有容抗器实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectCompensatorList(String projectId, String userName, String userId) {
        List<Object> compensatorList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("1");
        userInfo.setUser_name("test_user");

        //这里需要传一个工程Id
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
        sqlList.add("select b.id uuid, " +
                "b.name name, " +
                "b.expiry_date outservicedate, " +
                "b.operate_date inservicedate, " +
                "b.voltage_type voltagelevel, " +
                "b.st_id substationuuid, " +
                "b.running_state servicestatus " +
                "b.capacity_rate design_rateds " +
                "from SG_DEV_SHUNTREACTOR_B b ");
        NRBeanRowMapper<Compensator> org_compensator = new NRBeanRowMapper<>(Compensator.class);

        try {
            for (String sqlStr : sqlList) {
                compensatorList.addAll(m_nrdbAccess.query(sqlStr, null, org_compensator));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : compensatorList) {
            Compensator compensator = (Compensator) object;
            System.out.println("==========" + "id:" + compensator.getUuid() + "  name:" + compensator.getName());
        }
        return compensatorList;
    }


    /**
     * 通过工程id，查询容抗器信息
     * 查询返回所有容抗器实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectCompensatorListById(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> compensatorList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("1");
        userInfo.setUser_name("test_user");

        String idListStr = BasicManage.join(keyIdList);
        //这里需要传一个工程Id
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
        sqlList.add("select b.id uuid, " +
                "b.name name, " +
                "b.expiry_date outservicedate, " +
                "b.operate_date inservicedate, " +
                "b.voltage_type voltagelevel, " +
                "b.st_id substationuuid, " +
                "b.running_state servicestatus " +
                "b.capacity_rate design_rateds " +
                "from SG_DEV_SHUNTREACTOR_B b " +
                "and b.id in (" + idListStr + ") ");
        NRBeanRowMapper<Compensator> org_compensator = new NRBeanRowMapper<>(Compensator.class);
        try {
            for (String sqlStr : sqlList) {
                compensatorList.addAll(m_nrdbAccess.query(sqlStr, null, org_compensator));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : compensatorList) {
            Compensator compensator = (Compensator) object;
            System.out.println("==========" + "id:" + compensator.getUuid() + "  name:" + compensator.getName());
        }
        return compensatorList;
    }


    /**
     * 更新容抗器信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueMap
     * @return
     */
    public List<Boolean> updateCompensator(String projectId, String userName, String userId, String compensatorId, Map<String, String> changNameAndValueMap) {

        List<Boolean> resList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(compensatorId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {
            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            Pair<String, String> tableAndColumName = compensatorAttToDkyTableMap.get(columnName);
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

            Integer type = compensatorAttAndTypeMap.get(columnName);
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

        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo); //根据实际情况修改工程ID
        List<RecordModifyResult> result;
        try {
            for (Map.Entry<String, List<RecordInfo>> entry : tableRecordInfoMap.entrySet()) {
                result = m_nrdbAccess.UpdateRecord(entry.getKey(), entry.getValue());
                for (RecordModifyResult re : result) {
                    resList.add(re.isIs_success());
                    System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
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
        return resList;
    }


    public List<Boolean> insertCompensator(String projectId, String userName, String userId, Compensator compensator) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");

        List<RecordModifyResult> result;
        List<Boolean> resList = new ArrayList<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 容抗器重名 直接返回空的list
        if (compensatorAttToDkyTableMap == null || compensatorAttAndTypeMap == null || compensatorNameAndIdMap.containsKey(compensator.getName()))
            return resList;

        for (Field field : compensator.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(compensator);
                if (fieldValue == null)
                    continue;
                if (!compensatorAttToDkyTableMap.containsKey(fieldName) || !compensatorAttAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = compensatorAttToDkyTableMap.get(fieldName);
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
                int dkyColumnType = compensatorAttAndTypeMap.get(fieldName);
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
        // 设置stamp
        RecordColumnInfo stampColumnInfo = new RecordColumnInfo();
        stampColumnInfo.setColumn_name("stamp");
        stampColumnInfo.setColumn_value(BasicManage.getSTAMP("330881", "1"));
        stampColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        List<RecordColumnInfo> compensatorRecordList_b = recordColumnInfoMap.get("SG_DEV_SHUNTREACTOR_B");
        compensatorRecordList_b.add(stampColumnInfo);
        RecordInfo recordInfo_b = new RecordInfo();
        recordInfo_b.setRecordColumnInfo(compensatorRecordList_b);
        recordInfo_b.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_b);

        String insertCompensatorId = "";
        // 设置所属工程信息
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = m_nrdbAccess.InsertRecord("SG_DEV_SHUNTREACTOR_B", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertCompensatorId = rt.get(j);
                    compensatorNameAndIdMap.put(compensator.getName(), insertCompensatorId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

        return resList;
    }


    public List<Boolean> deleteCompensator(String projectId, String userName, String userId, String compensatorId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("1");
        userInfo.setUser_name("test_user");

        List<Boolean> resList = new ArrayList<>();
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(compensatorId);
        columnInfo.setIs_key(true);
        columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        recordColumnInfo.add(columnInfo);
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordColumnInfo(recordColumnInfo);
        recordInfo.setRegion_id("330000");
        recordInfoList.add(recordInfo);

        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;
        try {
            result = m_nrdbAccess.DeleteRecord("SG_DEV_SHUNTREACTOR_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resList.add(re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }


}
