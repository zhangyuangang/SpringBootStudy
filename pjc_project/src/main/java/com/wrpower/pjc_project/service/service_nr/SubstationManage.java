package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.entity.Substation;
import com.wrpower.pjc_project.service.ReadConfigFile;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.*;


public class SubstationManage {

//    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    private Map<String, String> volTypeMap = BasicManage.volNameToCodeMap;

    // 这个用来记录 厂站类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> substationAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("substation");
    // 厂站类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> substationAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("substation");
    private Map<String, String> substationNameAndIdMap = selectSubstationNameAndIdMap();


//    static {
//        m_nrdbAccess.setProxyIp("10.33.3.31");
//    }

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
            List<Map<String, Object>> map = NRDBAccessManage.NRDBAccess().queryForList(sql, null);
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
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        //这里 厂站分成了 发电厂 变电站 换流站   考虑 通过参数，判断是查询哪一个表
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select id uuid,name,expiry_date outservicedate,operate_date inservicedate,max_voltage_type    voltagelevel,plant_type kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_plant_b " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,top_ac_voltage_type voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_substation_b " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,dc_voltage_type     voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_conversubstation_b ");
        NRBeanRowMapper<Substation> org_substation = new NRBeanRowMapper<>(Substation.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                substationList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_substation));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : substationList) {
            Substation substation = (Substation) object;
            substation.setInservicedate(BasicManage.longToDataStr(substation.getInservicedate()));
            substation.setOutservicedate(BasicManage.longToDataStr(substation.getOutservicedate()));
            substation.setServicestatus(BasicManage.devStateCodeToString(substation.getServicestatus()));

            System.out.println("==========" + "id:" + substation.getUuid() + "  name:" + substation.getName() + "time:" + substation.getInservicedate() + " " + substation.getOutservicedate());
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
    public List<Object> selectSubstationListByIdList(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> substationList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        if (keyIdList.size() == 0)
            return substationList;
        String idListStr = BasicManage.join(keyIdList);

        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        //这里 厂站分成了 发电厂 变电站 换流站   考虑 通过参数，判断是查询哪一个表
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select id uuid,name,expiry_date outservicedate,operate_date inservicedate,max_voltage_type    voltagelevel,plant_type kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_plant_b where id in (" + idListStr + ") " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,top_ac_voltage_type voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_substation_b where id in (" + idListStr + ")" +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,dc_voltage_type     voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_conversubstation_b where id in (" + idListStr + ")");
        NRBeanRowMapper<Substation> org_substation = new NRBeanRowMapper<>(Substation.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                substationList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_substation));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : substationList) {
            Substation substation = (Substation) object;
            substation.setInservicedate(BasicManage.longToDataStr(substation.getInservicedate()));
            substation.setOutservicedate(BasicManage.longToDataStr(substation.getOutservicedate()));
            substation.setServicestatus(BasicManage.devStateCodeToString(substation.getServicestatus()));

            System.out.println("==========" + "id:" + substation.getUuid() + "  name:" + substation.getName() + "time:" + substation.getInservicedate() + " " + substation.getOutservicedate());
        }

        return substationList;
    }


    public List<Object> selectSubstationListByIdListAndDate(String projectId, String userName, String userId, List<String> keyIdList,String dateMin, String dateMax) {
        List<Object> substationList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        if (keyIdList.size() == 0)
            return substationList;
        String idListStr = BasicManage.join(keyIdList);

        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        //这里 厂站分成了 发电厂 变电站 换流站   考虑 通过参数，判断是查询哪一个表
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select id uuid,name,expiry_date outservicedate,operate_date inservicedate,max_voltage_type    voltagelevel,plant_type kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_plant_b where id in (" + idListStr + ") and t1.operate_date between to_date('"+ dateMin + "',\"yyyy-MM-DD\") and to_date('"+ dateMax + "',\"yyyy-MM-DD\")" +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,top_ac_voltage_type voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_substation_b where id in (" + idListStr + ") and t1.operate_date between to_date('"+ dateMin + "',\"yyyy-MM-DD\") and to_date('"+ dateMax + "',\"yyyy-MM-DD\")" +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,dc_voltage_type     voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_conversubstation_b where id in (" + idListStr + ") and t1.operate_date between to_date('"+ dateMin + "',\"yyyy-MM-DD\") and to_date('"+ dateMax + "',\"yyyy-MM-DD\")");
        NRBeanRowMapper<Substation> org_substation = new NRBeanRowMapper<>(Substation.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                substationList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_substation));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : substationList) {
            Substation substation = (Substation) object;
            substation.setInservicedate(BasicManage.longToDataStr(substation.getInservicedate()));
            substation.setOutservicedate(BasicManage.longToDataStr(substation.getOutservicedate()));
            substation.setServicestatus(BasicManage.devStateCodeToString(substation.getServicestatus()));

            System.out.println("==========" + "id:" + substation.getUuid() + "  name:" + substation.getName() + "time:" + substation.getInservicedate() + " " + substation.getOutservicedate());
        }

        return substationList;
    }


    public Substation selectSubstationInfoByUuid(String projectId, String userName, String userId, String uuid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Substation substation = new Substation();
        if (uuid == null)
            return substation;

        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        //这里 厂站分成了 发电厂 变电站 换流站   考虑 通过参数，判断是查询哪一个表
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select id uuid,name,expiry_date outservicedate,operate_date inservicedate,max_voltage_type    voltagelevel,plant_type kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_plant_b where id = '" + uuid + "' " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,top_ac_voltage_type voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_substation_b where id = '" + uuid + "' " +
                " union " +
                "select id uuid,name,expiry_date outservicedate,operate_date inservicedate,dc_voltage_type     voltagelevel,type       kind,dcc_id dispatcheruuid,owner owneruuid,operate_state servicestatus from sg_con_conversubstation_b where id = '" + uuid + "'");
        NRBeanRowMapper<Substation> org_substation = new NRBeanRowMapper<>(Substation.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                substation = NRDBAccessManage.NRDBAccess().queryForObject(sqlStr, null, org_substation);
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        substation.setInservicedate(BasicManage.longToDataStr(substation.getInservicedate()));
        substation.setOutservicedate(BasicManage.longToDataStr(substation.getOutservicedate()));
        substation.setServicestatus(BasicManage.devStateCodeToString(substation.getServicestatus()));
        substation.setKind(BasicManage.getSubTypeNameByCode(substation.getKind()));

        System.out.println("==========" + "id:" + substation.getUuid() + "  name:" + substation.getName() + "time:" + substation.getInservicedate() + " " + substation.getOutservicedate());

        return substation;
    }


    /**
     * 更新厂站信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueMap
     * @return
     */
    public Map<Boolean, String> updateSubstation(String projectId, String userName, String userId, String substationType, String substationId, Map<String, String> changNameAndValueMap) {

        Map<Boolean, String> resMap = new HashMap<>();

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(substationId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        String tableName = "";

        switch (substationType)         //  根据厂站类型，对应调控云中不同的表
        {
            case "发电厂":
                tableName = "SG_CON_PLANT_B";
                break;
            case "变电站":
                tableName = "SG_CON_SUBSTATION_B";
                break;
            case "换流站":
                tableName = "SG_CON_CONVERSUBSTATION_B";
                break;
        }

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {

            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            Pair<String, String> tableAndColumName = substationAttToDkyTableMap.get(columnName);
            String dkyTable = tableAndColumName.getKey();
            String dkyColumnName = tableAndColumName.getValue();
            if (dkyTable.equals("SG_CON_PLANT_B")) {
                dkyTable = tableName;
            }
            // 根据 不同的列名 获取 类型 getColumn_type
            if ("inservicedate".equals(columnName) || "outservicedate".equals(columnValue)) {
                columnValue = BasicManage.shortDateStrToLongDateStr(columnValue);
            }
            if ("voltagelevel".equals(columnName))       //前端传过来的是电压等级  而不是 code
            {
                columnValue = BasicManage.volNameToCodeMap.get(columnValue);
                switch (dkyTable)         // 根据不同厂站类型，电压等级 换成相应表中栏位的名称
                {
                    case "SG_CON_PLANT_B":
                        dkyColumnName = "MAX_VOLTAGE_TYPE";
                        break;
                    case "SG_CON_SUBSTATION_B":
                        dkyColumnName = "TOP_AC_VOLTAGE_TYPE";
                        break;
                    case "SG_CON_CONVERSUBSTATION_B":
                        dkyColumnName = "DC_VOLTAGE_TYPE";
                        break;
                }
            }
            if ("kind".equals(columnName)) {
                switch (dkyTable)     // 根据不同厂站类型，类型 换成相应表中栏位的名称
                {
                    case "SG_CON_PLANT_B":
                        dkyColumnName = "PLANT_TYPE";
                        break;
                    case "SG_CON_SUBSTATION_B":
                    case "SG_CON_CONVERSUBSTATION_B":
                        dkyColumnName = "TYPE";
                        break;
                }
                columnValue = BasicManage.getSubTypeCodeByName(columnValue);
            }
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            columnInfo.setColumn_name(dkyColumnName);
            columnInfo.setColumn_value(columnValue);
            int dkyColumnType = substationAttAndTypeMap.get(columnName);
            columnInfo.setColumn_type(dkyColumnType);

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
//            String columnName = changNameAndValue.getKey();
//            String columnValue = changNameAndValue.getValue();
//            RecordColumnInfo columnInfo = new RecordColumnInfo();
//
//            columnInfo.setColumn_name(columnName);
//            //这里应该根据不同的列 进行值的转化 成调控云要求的数值
//            columnInfo.setColumn_value(columnValue);
//            if ("id".equals(columnName)) {
//                columnInfo.setIs_key(true);
//            }
//            // 根据 不同的列名 获取 类型 getColumn_type
//            columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
//
//            recordColumnInfo1.add(columnInfo);
//            if ("kind".equals(columnName)) {
//                switch (BasicManage.getSubTypeByCode(BasicManage.getSubTypeCodeByName(columnValue))) {
//                    case 1:
//                        tableName = "SG_CON_PLANT_B";
//                        break;
//                    case 2:
//                        tableName = "SG_CON_SUBSTATION_B";
//                        break;
//                    case 3:
//                        tableName = "SG_CON_CONVERSUBSTATION_B";
//                        break;
//                }
//            }

//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo); //根据实际情况修改工程ID
        List<RecordModifyResult> result;
        try {
            for (Map.Entry<String, List<RecordInfo>> entry : tableRecordInfoMap.entrySet()) {
                result = NRDBAccessManage.NRDBAccess().UpdateRecord(entry.getKey(), entry.getValue());
                for (RecordModifyResult re : result) {
                    resMap.put(re.isIs_success(), entry.getKey() + re.getErr_msg());
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
        return resMap;
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
    public Map<Boolean, String> insertSubstation(String projectId, String userName, String userId, String orgId, Substation substation) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<Boolean, String> resMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 变压器重名 直接返回空的list
        if (substationAttToDkyTableMap == null || substationAttAndTypeMap == null) {
            resMap.put(false, "attrToDkyTable文件中substation表数据为空！");
            return resMap;
        }

        if (substationNameAndIdMap.containsKey(substation.getName())) {
            resMap.put(false, "变压器重名！");
            return resMap;
        }

        String tableName = "";
        switch (substation.getKind())         //  根据厂站类型，对应调控云中不同的表
        {
            case "发电厂":
                tableName = "SG_CON_PLANT_B";
                break;
            case "变电站":
                tableName = "SG_CON_SUBSTATION_B";
                break;
            case "换流站":
                tableName = "SG_CON_CONVERSUBSTATION_B";
                break;
        }
        if (tableName.isEmpty()) {
            resMap.put(false, "厂站类型不正确！");
            return resMap;
        }
        for (Field field : substation.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(substation);
                if (fieldValue == null)
                    continue;
                if (!substationAttToDkyTableMap.containsKey(fieldName) || !substationAttAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = substationAttToDkyTableMap.get(fieldName);
                //  Pair<  调控云表名  表中属性 >
                if (pair == null)
                    continue;
                String dkyTableName = pair.getKey();
                String dkyColumnName = pair.getValue();

                if (dkyTableName.equals("SG_CON_PLANT_B")) {
                    dkyTableName = tableName;
                }

                if ("voltagelevel".equals(fieldName))       //前端传过来的是电压等级  而不是 code
                {
                    fieldValue = BasicManage.volNameToCodeMap.get(fieldName);
                    switch (dkyTableName)         // 根据不同厂站类型，电压等级 换成相应表中栏位的名称
                    {
                        case "SG_CON_PLANT_B":
                            dkyColumnName = "MAX_VOLTAGE_TYPE";
                            break;
                        case "SG_CON_SUBSTATION_B":
                            dkyColumnName = "TOP_AC_VOLTAGE_TYPE";
                            break;
                        case "SG_CON_CONVERSUBSTATION_B":
                            dkyColumnName = "DC_VOLTAGE_TYPE";
                            break;
                    }
                }
                if ("kind".equals(fieldName)) {
                    switch (dkyTableName)     // 根据不同厂站类型，类型 换成相应表中栏位的名称
                    {
                        case "SG_CON_PLANT_B":
                            dkyColumnName = "PLANT_TYPE";
                            break;
                        case "SG_CON_SUBSTATION_B":
                        case "SG_CON_CONVERSUBSTATION_B":
                            dkyColumnName = "TYPE";
                            break;
                    }
                    fieldValue = BasicManage.getSubTypeCodeByName(fieldValue);
                }
                if ("inservicedate".equals(fieldName) || "outservicedate".equals(fieldName)) {
                    fieldValue = BasicManage.shortDateStrToLongDateStr(fieldValue);
                }
                if ("servicestatus".equals(fieldName))       //前端传过来的是 待投运 和 投运
                {
                    fieldValue = BasicManage.devStateStringToCode(fieldValue);
                }

                int dkyColumnType = substationAttAndTypeMap.get(fieldName);
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
        // 设置dispatch_org_id
        RecordColumnInfo dispatchColumnInfo = new RecordColumnInfo();
        dispatchColumnInfo.setColumn_name("dcc_id");
        dispatchColumnInfo.setColumn_value(orgId);
        dispatchColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        // 设置owner
        RecordColumnInfo ownerColumnInfo = new RecordColumnInfo();
        ownerColumnInfo.setColumn_name("owner");
        ownerColumnInfo.setColumn_value(orgId.substring(4));
        ownerColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        // 设置厂站简称
        RecordColumnInfo abbrNameColumnInfo = new RecordColumnInfo();
        abbrNameColumnInfo.setColumn_name("name_abbreviation");
        abbrNameColumnInfo.setColumn_value(substation.getAbbrname());
        abbrNameColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);


        List<RecordColumnInfo> recordList_b = recordColumnInfoMap.get(tableName);
        recordList_b.add(stampColumnInfo);
        recordList_b.add(dispatchColumnInfo);
        recordList_b.add(ownerColumnInfo);
        if (tableName.equals("SG_CON_PLANT_B")) {
            recordList_b.add(abbrNameColumnInfo);
        }
        RecordInfo recordInfo_b = new RecordInfo();
        recordInfo_b.setRecordColumnInfo(recordList_b);
        recordInfo_b.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_b);

        String insertSubstationId = "";
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;

        try {
            result = NRDBAccessManage.NRDBAccess().InsertRecord(tableName, recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println(tableName + "===================" + re.getErr_msg() + " " + re.isIs_success());
                if (!re.isIs_success()) {
                    resMap.put(false, tableName + "表插入失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertSubstationId = rt.get(j);
                    substationNameAndIdMap.put(substation.getName(), insertSubstationId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

        // 如果插入失败，没有返回id，则直接返回错误
        if (insertSubstationId.isEmpty()) {
            resMap.put(false, tableName + "表插入失败！错误原因：insertSubstationId 为空！");
            return resMap;
        }

        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_value(insertSubstationId);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        // 更新G表
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<RecordColumnInfo> recordList_g = recordColumnInfoMap.get("ZJ_CON_COMMONSUBSTATION_G");
        recordList_g.add(idColumnInfo);

        RecordInfo recordInfo_g = new RecordInfo();
        recordInfo_g.setRecordColumnInfo(recordList_g);
        recordInfo_g.setRegion_id("330000");
        List<RecordInfo> recordInfoList_g = new ArrayList<>();
        recordInfoList_g.add(recordInfo_g);
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = NRDBAccessManage.NRDBAccess().UpdateRecord("ZJ_CON_commonSUBSTATION_G", recordInfoList_g);
            for (RecordModifyResult re : result) {
                if (!re.isIs_success()) {
                    resMap.put(false, "ZJ_CON_commonSUBSTATION_G表更新失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
                System.out.println("ZJ_CON_commonSUBSTATION_G===================" + re.getErr_msg() + " " + re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

        resMap.put(true, insertSubstationId);
        return resMap;

//        UserInfo userInfo = new UserInfo();
//        userInfo.setUser_id(userId);
//        userInfo.setUser_name(userName);
//
//        List<Boolean> resList = new ArrayList<>();
//        // 所有新增数据对应 Map<类型、List<数据> > 考虑到可能批量插入不同厂站类型的数据
//        List<RecordInfo> recordInfoList = new ArrayList<>();
//
//
//        String tableName = "";
//        String owner = substation.getOwneruuid();
//        String stamp = BasicManage.getSTAMP("330881", "1");
//
//        List<String> valueList = new ArrayList<>();
//        valueList.add(substation.getName());
//        //电压等级转换code码
//        valueList.add(volTypeMap.get(substation.getVoltagelevel()));
//        // 厂站类型转换code码
//        String code = BasicManage.getSubTypeCodeByName(substation.getKind());
//        valueList.add(code);
//        //dispatcher 调度管辖权转换code码
//        valueList.add(orgId);
//        //owner 转换code
////            valueList.add(ownerMap.get(sub.getOwneruuid()));
//        valueList.add(orgId.substring(4));
////            valueList.add(sub.getServicestatus());
//        valueList.add("1003");
//
//        valueList.add(stamp);
//        valueList.add(BasicManage.shortDateStrToLongDateStr(substation.getInservicedate()));
//        valueList.add(BasicManage.shortDateStrToLongDateStr(substation.getOutservicedate()));
//
//        List<String> dbFieldList = null;
//        switch (BasicManage.getSubTypeByCode(code)) {
//            case 1:
//                dbFieldList = plantFieldList;
//                valueList.add(substation.getName());
//                tableName = "SG_CON_PLANT_B";
//                break;
//            case 2:
//                dbFieldList = acStFieldList;
//                tableName = "SG_CON_SUBSTATION_B";
//                break;
//            case 3:
//                dbFieldList = dcStFieldList;
//                tableName = "SG_CON_CONVERSUBSTATION_B";
//                break;
//        }
//
//        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
//        for (int index = 0; index < valueList.size(); ++index) {
//            RecordColumnInfo columnInfo = new RecordColumnInfo();
//            assert dbFieldList != null;
//            columnInfo.setColumn_name(dbFieldList.get(index));
//            columnInfo.setColumn_value(valueList.get(index));
//            columnInfo.setColumn_type(typeList.get(index));
//            recordColumnInfo.add(columnInfo);
//        }
//
//        RecordInfo recordInfo = new RecordInfo();
//        recordInfo.setRecordColumnInfo(recordColumnInfo);
//        recordInfo.setRegion_id("330000");
//
//        recordInfoList.add(recordInfo);
//
//
//        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
//        List<RecordModifyResult> result;
//        try {
//            result = NRDBAccessManage.NRDBAccess().InsertRecord(tableName, recordInfoList);
//            for (RecordModifyResult re : result) {
//                resList.add(re.isIs_success());
//                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
//            }
//
//        } catch (NRDataAccessException e) {
//            e.printStackTrace();
//        }
//        return resList;
    }

    /**
     * 批量删除厂站信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param substationType
     * @param substationId
     * @return
     */
    public Map<Boolean, String> deleteSubstation(String projectId, String userName, String userId, String substationType, String substationId) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<Boolean, String> resMap = new HashMap<>();
        List<RecordInfo> recordInfoList = new ArrayList<>();

        String tableName = "";
        switch (substationType)         //  根据厂站类型，对应调控云中不同的表
        {
            case "发电厂":
                tableName = "SG_CON_PLANT_B";
                break;
            case "变电站":
                tableName = "SG_CON_SUBSTATION_B";
                break;
            case "换流站":
                tableName = "SG_CON_CONVERSUBSTATION_B";
                break;
        }
        if (tableName.isEmpty()) {
            resMap.put(false, "厂站类型不正确！");
            return resMap;
        }
        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(substationId);
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
            result = NRDBAccessManage.NRDBAccess().DeleteRecord(tableName, recordInfoList);
            for (RecordModifyResult re : result) {
                resMap.put(re.isIs_success(), re.getErr_msg());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resMap;
    }

}
