package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.domain.Breaker;
import javafx.util.Pair;
import java.lang.reflect.Field;
import java.util.*;

public class BreakerManage {
    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    // 这个用来记录 开关类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> breakerAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("breaker");
    // 开关类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> breakerAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("breaker");
    private Map<String, String> breakerNameAndIdMap = selectBreakerNameAndIdMap();


    static {
        m_nrdbAccess.setProxyIp("10.33.3.31");
    }

    public static Map<String, String> selectBreakerNameAndIdMap() {
        Map<String, String> breakerNameAndIdMap = new HashMap<>();
        String sql = " select id, name from SG_DEV_BREAKER_B ";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                breakerNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("breakerNameAndIdMap:" + e.getMessage());
        }
        return breakerNameAndIdMap;
    }

    /**
     * 根据uuid查询开关信息
     *
     * @param uuid String
     * @return Breaker
     */
    public Breaker selectBreakerInfoByUuid(String uuid) {
        Breaker breaker = new Breaker();
        if (uuid == null)
            return breaker;

        String sqlStr = "select b.id uuid, " +
                "b.name name, " +
                "b.expiry_date outservicedate, " +
                "b.operate_date inservicedate, " +
                "b.voltage_type voltagelevel, " +
                "b.dispatch_org_id dispatcheruuid, " +
                "b.owner owneruuid, " +
                "b.st_id substationuuid, " +
                "b.running_state servicestatus " +
                "from SG_DEV_BREAKER_B b " +
                "where b.id = '" + uuid + "' ";
        NRBeanRowMapper<Breaker> org_breaker = new NRBeanRowMapper<>(Breaker.class);

        try {
            breaker = m_nrdbAccess.queryForObject(sqlStr, null, org_breaker);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return breaker;
    }

    /**
     * 通过工程id，查询开关信息
     * 查询返回所有开关实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectBreakerList(String projectId, String userName, String userId) {
        List<Object> breakerList = new ArrayList<>();
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
                "b.dispatch_org_id dispatcheruuid, " +
                "b.owner owneruuid, " +
                "b.st_id substationuuid, " +
                "b.running_state servicestatus " +
                "from SG_DEV_BREAKER_B b ");
        NRBeanRowMapper<Breaker> org_breaker = new NRBeanRowMapper<>(Breaker.class);

        try {
            for (String sqlStr : sqlList) {
                breakerList.addAll(m_nrdbAccess.query(sqlStr, null, org_breaker));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : breakerList) {
            Breaker breaker = (Breaker) object;
            System.out.println("==========" + "id:" + breaker.getUuid() + "  name:" + breaker.getName());
        }
        return breakerList;
    }


    /**
     * 通过工程id，查询开关信息
     * 查询返回所有开关实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectBreakerListById(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> breakerList = new ArrayList<>();
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
                "b.dispatch_org_id dispatcheruuid, " +
                "b.owner owneruuid, " +
                "b.st_id substationuuid, " +
                "b.running_state servicestatus " +
                "from SG_DEV_BREAKER_B b " +
                "and b.id in (" + idListStr + ") ");
        NRBeanRowMapper<Breaker> org_breaker = new NRBeanRowMapper<>(Breaker.class);
        try {
            for (String sqlStr : sqlList) {
                breakerList.addAll(m_nrdbAccess.query(sqlStr, null, org_breaker));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : breakerList) {
            Breaker breaker = (Breaker) object;
            System.out.println("==========" + "id:" + breaker.getUuid() + "  name:" + breaker.getName());
        }
        return breakerList;
    }


    /**
     * 更新开关信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueMap
     * @return
     */
    public List<Boolean> updateBreaker(String projectId, String userName, String userId, String breakerId, Map<String, String> changNameAndValueMap) {

        List<Boolean> resList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(breakerId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {
            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            Pair<String, String> tableAndColumName = breakerAttToDkyTableMap.get(columnName);
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

            Integer type = breakerAttAndTypeMap.get(columnName);
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


    public List<Boolean> insertBreaker(String projectId, String userName, String userId, Breaker breaker) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");

        List<RecordModifyResult> result;
        List<Boolean> resList = new ArrayList<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 开关重名 直接返回空的list
        if (breakerAttToDkyTableMap == null || breakerAttAndTypeMap == null || breakerNameAndIdMap.containsKey(breaker.getName()))
            return resList;

        for (Field field : breaker.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(breaker);
                if (fieldValue == null)
                    continue;
                if (!breakerAttToDkyTableMap.containsKey(fieldName) || !breakerAttAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = breakerAttToDkyTableMap.get(fieldName);
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
                int dkyColumnType = breakerAttAndTypeMap.get(fieldName);
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

        List<RecordColumnInfo> breakerRecordList_b = recordColumnInfoMap.get("SG_DEV_BREAKER_B");
        breakerRecordList_b.add(stampColumnInfo);
        RecordInfo recordInfo_b = new RecordInfo();
        recordInfo_b.setRecordColumnInfo(breakerRecordList_b);
        recordInfo_b.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_b);

        String insertBreakerId = "";
        // 设置所属工程信息
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = m_nrdbAccess.InsertRecord("SG_DEV_BREAKER_B", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertBreakerId = rt.get(j);
                    breakerNameAndIdMap.put(breaker.getName(), insertBreakerId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

        return resList;
    }


    public List<Boolean> deleteBreaker(String projectId, String userName, String userId, String breakerId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("1");
        userInfo.setUser_name("test_user");

        List<Boolean> resList = new ArrayList<>();
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(breakerId);
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
            result = m_nrdbAccess.DeleteRecord("SG_DEV_BREAKER_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resList.add(re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }

}
