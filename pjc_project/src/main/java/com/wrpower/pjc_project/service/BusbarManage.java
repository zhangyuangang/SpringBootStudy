package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.domain.Busbar;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.*;

public class BusbarManage {

    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();

    // 这个用来记录 母线类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> busbarAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("busbar");
    // 母线类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> attAndTypeMap = ReadConfigFile.getAttAndTypeMap("busbar");
    private Map<String, String> busbarNameAndIdMap = selectBusbarNameAndIdMap();

    static {
        m_nrdbAccess.setProxyIp("10.33.3.31");
    }

    public static Map<String, String> selectBusbarNameAndIdMap() {
        Map<String, String> busbarNameAndIdMap = new HashMap<>();
        String sql = " select id, name from SG_DEV_BUSBAR_B ";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                busbarNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("busbarNameAndIdMap:" + e.getMessage());
            busbarNameAndIdMap.put("error", "busbarNameAndIdMap发生错误:" + e.getMessage());
        }
        return busbarNameAndIdMap;
    }

    /**
     * 根据uuid查询母线信息
     *
     * @param uuid
     * @return
     */
    public Busbar selectBusbarInfoByUuid(String uuid) {
        Busbar busbar = new Busbar();
        if (uuid == null)
            return busbar;

        String sqlStr = "select b.id uuid, " +
                "b.name name, " +
                "b.expiry_date outservicedate, " +
                "b.operate_date inservicedate, " +
                "b.voltage_type voltagelevel, " +
                "b.st_id substationuuid, " +
                "b.owner owneruuid, " +
                "b.dispatch_org_id dispatcheruuid, " +
                "b.running_state servicestatus, " +
                "m.base_v basevoltage, " +
                "m.check_v_max volhi, " +
                "m.check_v_min volho " +
                "from SG_DEV_BUSBAR_B b, SG_DEV_BUSBAR_M m " +
                "where b.id = m.id " +
                "and b.id = '" + uuid + "' ";
        NRBeanRowMapper<Busbar> org_busbar = new NRBeanRowMapper<>(Busbar.class);

        try {
            busbar = m_nrdbAccess.queryForObject(sqlStr, null, org_busbar);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return busbar;
    }


    /**
     * 通过工程id，查询母线信息
     * 查询返回所有母线实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectBusbarList(String projectId, String userName, String userId) {
        List<Object> busbarList = new ArrayList<>();
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
                "b.owner owneruuid, " +
                "b.dispatch_org_id dispatcheruuid, " +
                "b.running_state servicestatus, " +
                "m.base_v basevoltage, " +
                "m.check_v_max volhi, " +
                "m.check_v_min vollo " +
                "from SG_DEV_BUSBAR_B b, SG_DEV_BUSBAR_M m " +
                "where b.id = m.id ");
        NRBeanRowMapper<Busbar> org_busbar = new NRBeanRowMapper<>(Busbar.class);

        try {
            for (String sqlStr : sqlList) {
                busbarList.addAll(m_nrdbAccess.query(sqlStr, null, org_busbar));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : busbarList) {
            Busbar busbar = (Busbar) object;
            System.out.println("==========" + "id:" + busbar.getUuid() + "  name:" + busbar.getName());
        }
        return busbarList;
    }


    /**
     * 通过工程id，查询母线信息
     * 查询返回所有母线实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectBusbarListById(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> busbarList = new ArrayList<>();
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
                "b.owner owneruuid, " +
                "b.dispatch_org_id dispatcheruuid, " +
                "b.running_state servicestatus, " +
                "m.base_v basevoltage, " +
                "m.check_v_max volhi, " +
                "m.check_v_min vollo " +
                "from SG_DEV_BUSBAR_B b, SG_DEV_BUSBAR_M m " +
                "where b.id = m.id " +
                "and b.id in (" + idListStr + ") ");
        NRBeanRowMapper<Busbar> org_busbar = new NRBeanRowMapper<>(Busbar.class);
        try {
            for (String sqlStr : sqlList) {
                busbarList.addAll(m_nrdbAccess.query(sqlStr, null, org_busbar));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : busbarList) {
            Busbar busbar = (Busbar) object;
            System.out.println("==========" + "id:" + busbar.getUuid() + "  name:" + busbar.getName());
        }
        return busbarList;
    }

    /**
     * 更新母线信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueMap
     * @return
     */
    public List<Boolean> updateBusbar(String projectId, String userName, String userId, String busbarId, Map<String, String> changNameAndValueMap) {

        List<Boolean> resList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(busbarId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {
            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            Pair<String, String> tableAndColumName = busbarAttToDkyTableMap.get(columnName);
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

            Integer type = attAndTypeMap.get(columnName);
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

    public List<Boolean> updateBusTest(String projectId, String subId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo); //根据实际情况修改工程ID

        List<RecordInfo> recordInfoList = new ArrayList<>();
        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(subId);
        columnInfo.setIs_key(true);
        columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        recordColumnInfo.add(columnInfo);

        RecordColumnInfo columnInfo1 = new RecordColumnInfo();
        columnInfo1.setColumn_name("NAME");
        columnInfo1.setColumn_value("test20181225");
        columnInfo1.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        recordColumnInfo.add(columnInfo1);

        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordColumnInfo(recordColumnInfo);
        recordInfo.setRegion_id("330000");
        recordInfoList.add(recordInfo);

        List<Boolean> resList = new ArrayList<>();

        List<RecordModifyResult> result;
        try {
            result = m_nrdbAccess.UpdateRecord("SG_DEV_BUSBAR_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resList.add(re.isIs_success());
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }

    /**
     * 批量新增母线信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Boolean> insertBusbar(String projectId, String userName, String userId, Busbar busbar) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");

        List<RecordModifyResult> result;
        List<Boolean> resList = new ArrayList<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
//        // 测试
//        Busbar busbar = new Busbar();
//        busbar.setName("testBusbar1");
//        busbar.setDispatcheruuid("0021330881");
//        busbar.setOwneruuid("330881");
//        busbar.setOutservicedate("2018-12-27 10:10:10");
//        busbar.setInservicedate("2018-12-26 10:10:10");
//        busbar.setServicestatus("1");
//        busbar.setSubstationuuid("01113300000142");
//        busbar.setVoltagelevel("1008");
//        busbar.setBasevoltage("220.0");
//        busbar.setVolhi("1");
//        busbar.setVollo("1");

        // 母线重名 直接返回空的list
        if (busbarAttToDkyTableMap == null || busbarNameAndIdMap.containsKey(busbar.getName()))
            return resList;

        for (Field field : busbar.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(busbar);
                if (fieldValue == null)
                    continue;
                if (!busbarAttToDkyTableMap.containsKey(fieldName) || !attAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = busbarAttToDkyTableMap.get(fieldName);
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
                int dkyColumnType = attAndTypeMap.get(fieldName);
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

        List<RecordColumnInfo> busbarRecordList_b = recordColumnInfoMap.get("SG_DEV_BUSBAR_B");
        busbarRecordList_b.add(stampColumnInfo);
        RecordInfo recordInfo_b = new RecordInfo();
        recordInfo_b.setRecordColumnInfo(busbarRecordList_b);
        recordInfo_b.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_b);

        String insertBusbarId = "";
        // 设置所属工程信息
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = m_nrdbAccess.InsertRecord("SG_DEV_BUSBAR_B", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertBusbarId = rt.get(j);
                    busbarNameAndIdMap.put(busbar.getName(), insertBusbarId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

        // 更新M表
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!insertBusbarId.isEmpty()) {
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            columnInfo.setColumn_name("ID");
            columnInfo.setIs_key(true);
            columnInfo.setColumn_value(insertBusbarId);
            columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

            List<RecordColumnInfo> busbarRecordList_m = recordColumnInfoMap.get("SG_DEV_BUSBAR_M");
            busbarRecordList_m.add(stampColumnInfo);
            busbarRecordList_m.add(columnInfo);

            RecordInfo recordInfo_m = new RecordInfo();
            recordInfo_m.setRecordColumnInfo(busbarRecordList_m);
            recordInfo_m.setRegion_id("330000");
            List<RecordInfo> recordInfoList_m = new ArrayList<>();
            recordInfoList_m.add(recordInfo_m);
            // 设置所属工程信息
            m_nrdbAccess.clearProjectId();
            m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
            try {
                result = m_nrdbAccess.UpdateRecord("SG_DEV_BUSBAR_M", recordInfoList_m);
                for (RecordModifyResult re : result) {
                    resList.add(re.isIs_success());
                    System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
                }
            } catch (NRDataAccessException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }


    /**
     * 批量删除母线信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Boolean> deleteBusbar(String projectId, String userName, String userId, String busbarId) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("1");
        userInfo.setUser_name("test_user");

        List<Boolean> resList = new ArrayList<>();
        // 所有删除数据对应 Map<类型、List<数据> > 考虑到可能批量删除不同厂站类型的数据
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(busbarId);
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
            result = m_nrdbAccess.DeleteRecord("SG_DEV_BUSBAR_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resList.add(re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }
}
