package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.domain.Substation;

import java.util.*;


public class SubstationManage {

    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    private Map<String, String> volTypeMap = BasicManage.volNameToCodeMap;
    private Map<String, String> dispatchMap = BasicManage.getDispatchMap();
    private Map<String, String> ownerMap = BasicManage.ownerNameToCodeMap;


    private List<String> plantFieldList = new ArrayList<>();
    private List<String> acStFieldList = new ArrayList<>();
    private List<String> dcStFieldList = new ArrayList<>();
    private List<Integer> typeList = new ArrayList<>();

    static {
        m_nrdbAccess.setProxyIp("10.33.3.31");
    }

    {
        plantFieldList.add("name");
        plantFieldList.add("max_voltage_type");
        plantFieldList.add("plant_type");
        plantFieldList.add("dcc_id");
        plantFieldList.add("owner");
        plantFieldList.add("operate_state");
        plantFieldList.add("stamp");
        plantFieldList.add("operate_date");
        plantFieldList.add("expiry_date");
        plantFieldList.add("name_abbreviation");

        acStFieldList.add("name");
        acStFieldList.add("top_ac_voltage_type");
        acStFieldList.add("type");
        acStFieldList.add("dcc_id");
        acStFieldList.add("owner");
        acStFieldList.add("operate_state");
        acStFieldList.add("stamp");
        acStFieldList.add("operate_date");
        acStFieldList.add("expiry_date");

        dcStFieldList.add("name");
        dcStFieldList.add("dc_voltage_type");
        dcStFieldList.add("type");
        dcStFieldList.add("dcc_id");
        dcStFieldList.add("owner");
        dcStFieldList.add("operate_state");
        dcStFieldList.add("stamp");
        dcStFieldList.add("operate_date");
        dcStFieldList.add("expiry_date");

        typeList.add(DefineHeader.JDBC_DATATYPE_STRING);
        typeList.add(DefineHeader.JDBC_DATATYPE_INT);
        typeList.add(DefineHeader.JDBC_DATATYPE_INT);
        typeList.add(DefineHeader.JDBC_DATATYPE_STRING);
        typeList.add(DefineHeader.JDBC_DATATYPE_STRING);
        typeList.add(DefineHeader.JDBC_DATATYPE_INT);
        typeList.add(DefineHeader.JDBC_DATATYPE_STRING);
        typeList.add(DefineHeader.JDBC_DATATYPE_DATETIME);
        typeList.add(DefineHeader.JDBC_DATATYPE_DATETIME);
        typeList.add(DefineHeader.JDBC_DATATYPE_STRING);
    }

    /**
     * @return
     */
    public static Map<String, String> selectSubstationNameAndIdMap() {
        Map<String, String> substationNameAndIdMap = new HashMap<>();
        String sql = "select id, name from SG_CON_PLANT_B " +
                " union " +
                "select id, name from SG_CON_SUBSTATION_B " +
                " union " +
                "select id, name from SG_CON_CONVERSUBSTATION_B";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                substationNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("substationNameAndIdMap发生错误:" + e.getMessage());
            substationNameAndIdMap.put("error", "substationNameAndIdMap发生错误:" + e.getMessage());
        }
        return substationNameAndIdMap;
    }

    /**
     * 通过工程id，查询厂站信息
     * 查询返回所有厂站实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectSubstationList(String projectId, String userName, String userId) {
        List<Object> substationList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        //这里需要传一个工程Id
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        //这里 厂站分成了 发电厂 变电站 换流站   考虑 通过参数，判断是查询哪一个表
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select id uuid,name,expiry_date outservicedate,operate_date inservicedate,max_voltage_type    voltagelevel,plant_type kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_plant_b where name like '%test%' " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,top_ac_voltage_type voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_substation_b where name like '%test%' " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,dc_voltage_type     voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_conversubstation_b where name like '%test%' ");
        NRBeanRowMapper<Substation> org_substation = new NRBeanRowMapper<>(Substation.class);
        try {
            for (String sqlStr : sqlList) {
                substationList.addAll(m_nrdbAccess.query(sqlStr, null, org_substation));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : substationList) {
            Substation substation = (Substation) object;
            System.out.println("==========" + "id:" + substation.getUuid() + "  name:" + substation.getName());
        }
        return substationList;
    }


    /**
     * 通过工程id，查询厂站信息
     * 查询返回所有厂站实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectSubstationById(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> substationList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        String idListStr = BasicManage.join(keyIdList);

        //这里需要传一个工程Id
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        //这里 厂站分成了 发电厂 变电站 换流站   考虑 通过参数，判断是查询哪一个表
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select id uuid,name,expiry_date outservicedate,operate_date inservicedate,max_voltage_type    voltagelevel,plant_type kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_plant_b where name like '%test%' " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,top_ac_voltage_type voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_substation_b where name like '%test%' " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,dc_voltage_type     voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_conversubstation_b where name like '%test%' ");
        NRBeanRowMapper<Substation> org_substation = new NRBeanRowMapper<>(Substation.class);
        try {
            for (String sqlStr : sqlList) {
                substationList.addAll(m_nrdbAccess.query(sqlStr, null, org_substation));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : substationList) {
            Substation substation = (Substation) object;
            System.out.println("==========" + "id:" + substation.getUuid() + "  name:" + substation.getName());
        }
        return substationList;
    }

    public List<Substation> selectSubTest(String projectId) {
        List<Substation> TestList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");

        //这里需要传一个工程Id
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        //这里 厂站分成了 发电厂 变电站 换流站   考虑 通过参数，判断是查询哪一个表
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select id uuid,name,expiry_date outservicedate,operate_date inservicedate,max_voltage_type    voltagelevel,plant_type kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_plant_b  ");
        NRBeanRowMapper<Substation> org_substation = new NRBeanRowMapper<>(Substation.class);

        try {
            for (String sqlStr : sqlList) {
                TestList.addAll(m_nrdbAccess.query(sqlStr, null, org_substation));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Substation demo : TestList) {
            System.out.println("==========" + "id:" + demo.getUuid() + "  name:" + demo.getName());
        }
        return TestList;
    }

    /**
     * 更新厂站信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueList
     * @return
     */
    public List<Boolean> updateSubstationList(String projectId, String userName, String userId, List<Map<String, String>> changNameAndValueList) {

        List<Boolean> resList = new ArrayList<>();

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();

        for (Map<String, String> changNameAndValueMap : changNameAndValueList) {

            List<RecordColumnInfo> recordColumnInfo1 = new ArrayList<>();
            String tableName = "";

            for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {

                String columnName = changNameAndValue.getKey();
                String columnValue = changNameAndValue.getValue();
                RecordColumnInfo columnInfo = new RecordColumnInfo();

                columnInfo.setColumn_name(columnName);
                //这里应该根据不同的列 进行值的转化 成调控云要求的数值
                columnInfo.setColumn_value(columnValue);
                if ("id".equals(columnName)) {
                    columnInfo.setIs_key(true);
                }
                // 根据 不同的列名 获取 类型 getColumn_type
                columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

                recordColumnInfo1.add(columnInfo);
                if ("kind".equals(columnName)) {
                    switch (BasicManage.getSubTypeByCode(BasicManage.getSubTypeCodeByName(columnValue))) {
                        case 1:
                            tableName = "SG_CON_PLANT_B";
                            break;
                        case 2:
                            tableName = "SG_CON_SUBSTATION_B";
                            break;
                        case 3:
                            tableName = "SG_CON_CONVERSUBSTATION_B";
                            break;
                    }
                }
            }
            RecordInfo recordInfo = new RecordInfo();
            recordInfo.setRecordColumnInfo(recordColumnInfo1);
            recordInfo.setRegion_id("330000");

            if (!tableRecordInfoMap.containsKey(tableName)) {
                List<RecordInfo> recordInfoList = new ArrayList<>();
                recordInfoList.add(recordInfo);
                tableRecordInfoMap.put(tableName, recordInfoList);
            } else {
                tableRecordInfoMap.get(tableName).add(recordInfo);
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
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }

    public List<Boolean> updateSubTest(String projectId, String subId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo); //根据实际情况修改工程ID

        List<RecordInfo> recordInfoList = new ArrayList<>();
        List<RecordColumnInfo> recordColumnInfo1 = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(subId);
        columnInfo.setIs_key(true);
        columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        recordColumnInfo1.add(columnInfo);

        RecordColumnInfo columnInfo1 = new RecordColumnInfo();
        columnInfo1.setColumn_name("NAME");
        columnInfo1.setColumn_value("test20181221");
        columnInfo1.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        recordColumnInfo1.add(columnInfo1);

        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordColumnInfo(recordColumnInfo1);
        recordInfo.setRegion_id("330000");
        recordInfoList.add(recordInfo);

        List<Boolean> resList = new ArrayList<>();

        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;
        try {
            result = m_nrdbAccess.UpdateRecord("SG_CON_PLANT_B", recordInfoList);
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
     * 名称  电压等级code  厂站类型code  管辖权code  ownerCode(管辖权后6位)
     * state(1001)  stamp  operateDate(2018-12-22 10:10:10)  expiryDate
     * 新增厂站信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param substation
     * @return
     */
    public List<Boolean> insertSubstationList(String projectId, String userName, String userId, Substation substation) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("1");
        userInfo.setUser_name("test_user");

        List<Boolean> resList = new ArrayList<>();
        // 所有新增数据对应 Map<类型、List<数据> > 考虑到可能批量插入不同厂站类型的数据
        List<RecordInfo> recordInfoList = new ArrayList<>();


        String tableName = "";
        String owner = substation.getOwneruuid();
        String stamp = BasicManage.getSTAMP("330881", "1");

        List<String> valueList = new ArrayList<>();
        valueList.add(substation.getName());
        //电压等级转换code码
        valueList.add(volTypeMap.get(substation.getVoltagelevel()));
        // 厂站类型转换code码
        String code = BasicManage.getSubTypeCodeByName(substation.getKind());
        valueList.add(code);
        //dispatcher 调度管辖权转换code码
//            valueList.add(dispatchMap.get(sub.getDispatcheruuid()));
        valueList.add("0021330881");
        //owner 转换code
//            valueList.add(ownerMap.get(sub.getOwneruuid()));
        valueList.add("330881");
//            valueList.add(sub.getServicestatus());
        valueList.add("1003");

        valueList.add(stamp);
        valueList.add(BasicManage.shortDateStrToLongDateStr(substation.getInservicedate()));
        valueList.add(BasicManage.shortDateStrToLongDateStr(substation.getOutservicedate()));

        List<String> dbFieldList = null;
        switch (BasicManage.getSubTypeByCode(code)) {
            case 1:
                dbFieldList = plantFieldList;
                valueList.add(substation.getName());
                tableName = "SG_CON_PLANT_B";
                break;
            case 2:
                dbFieldList = acStFieldList;
                tableName = "SG_CON_SUBSTATION_B";
                break;
            case 3:
                dbFieldList = dcStFieldList;
                tableName = "SG_CON_CONVERSUBSTATION_B";
                break;
        }

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        for (int index = 0; index < valueList.size(); ++index) {
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            assert dbFieldList != null;
            columnInfo.setColumn_name(dbFieldList.get(index));
            columnInfo.setColumn_value(valueList.get(index));
            columnInfo.setColumn_type(typeList.get(index));
            recordColumnInfo.add(columnInfo);
        }

        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordColumnInfo(recordColumnInfo);
        recordInfo.setRegion_id("330000");

        recordInfoList.add(recordInfo);


        // 设置所属工程信息
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;
        try {
            result = m_nrdbAccess.InsertRecord(tableName, recordInfoList);
            for (RecordModifyResult re : result) {
                resList.add(re.isIs_success());
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
            }

        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }


    public List<Boolean> insertSubTest(String projectId) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");

        List<Boolean> resList = new ArrayList<>();

        // 设置所属工程信息
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;
        try {
            result = m_nrdbAccess.InsertRecord("SG_CON_PLANT_B", getTestRecord());
            for (RecordModifyResult re : result) {
                resList.add(re.isIs_success());
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }


    private List<RecordInfo> getTestRecord() {
        List<RecordInfo> list = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        valueList.add("test20181220");          //名称
        valueList.add("1008");                  // 电压code
        valueList.add("1001");                  //
        valueList.add("0021330881");
        valueList.add("330881");
        valueList.add("1003");
        valueList.add(BasicManage.getSTAMP("330881", "1"));
        valueList.add("2019-12-22 00:00:00");
        valueList.add("2019-12-23 00:00:00");
        valueList.add("test20181220");

        List<String> dbFieldList = plantFieldList;

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        for (int index = 0; index < valueList.size(); ++index) {
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            assert dbFieldList != null;
            columnInfo.setColumn_name(dbFieldList.get(index));
            columnInfo.setColumn_value(valueList.get(index));
            columnInfo.setColumn_type(typeList.get(index));
            recordColumnInfo.add(columnInfo);
        }
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordColumnInfo(recordColumnInfo);
        recordInfo.setRegion_id("330000");

        list.add(recordInfo);
        return list;
    }

    /**
     * 批量删除厂站信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param subsIdAndKindMap
     * @return
     */
    public List<Boolean> deleteSubstationList(String projectId, String userName, String userId, Map<String, String> subsIdAndKindMap) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        List<Boolean> resList = new ArrayList<>();
        // 所有删除数据对应 Map<类型、List<数据> > 考虑到可能批量删除不同厂站类型的数据
        Map<String, List<RecordInfo>> recordInfoMap = new HashMap<>();

        for (Map.Entry<String, String> entry : subsIdAndKindMap.entrySet()) {
            String tableName = "";
            String id = entry.getKey();
            String kind = entry.getValue();
            String code = BasicManage.getSubTypeCodeByName(kind);
            int type = BasicManage.getSubTypeByCode(code);
            List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            columnInfo.setColumn_name("ID");
            columnInfo.setColumn_value(id);
            columnInfo.setIs_key(true);
            columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
            recordColumnInfo.add(columnInfo);
            RecordInfo recordInfo = new RecordInfo();
            recordInfo.setRecordColumnInfo(recordColumnInfo);
            recordInfo.setRegion_id("330000");
            switch (type) {
                case 1:
                    tableName = "SG_CON_PLANT_B";
                    break;
                case 2:
                    tableName = "SG_CON_SUBSTATION_B";
                    break;
                case 3:
                    tableName = "SG_CON_CONVERSUBSTATION_B";
                    break;
            }

            if (!recordInfoMap.containsKey(tableName)) {
                List<RecordInfo> recordInfoList = new ArrayList<>();
                recordInfoList.add(recordInfo);
                recordInfoMap.put(tableName, recordInfoList);
            } else {
                recordInfoMap.get(tableName).add(recordInfo);
            }
        }

        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;
        try {
            for (Map.Entry<String, List<RecordInfo>> entry : recordInfoMap.entrySet()) {
                result = m_nrdbAccess.DeleteRecord(entry.getKey(), entry.getValue());
                for (RecordModifyResult re : result) {
                    resList.add(re.isIs_success());
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }

    public List<Boolean> deleteSubTest(String projectId, String subId) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");

        List<Boolean> resList = new ArrayList<>();
        // 所有删除数据对应 Map<类型、List<数据> > 考虑到可能批量删除不同厂站类型的数据
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(subId);
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
            result = m_nrdbAccess.DeleteRecord("SG_CON_PLANT_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resList.add(re.isIs_success());
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }

}
