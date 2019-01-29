package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.domain.Generator;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorManage {

    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    // 这个用来记录 发电机类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> generatorAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("generator");
    // 发电机类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> generatorAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("generator");
    private Map<String, String> generatorNameAndIdMap = selectGeneratorNameAndIdMap();

    static {
        m_nrdbAccess.setProxyIp("10.33.3.31");
    }

    public static Map<String, String> selectGeneratorNameAndIdMap() {
        Map<String, String> generatorNameAndIdMap = new HashMap<>();
        String sql = " select id, name from SG_DEV_GENERATOR_B ";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                generatorNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("generatorNameAndIdMap:" + e.getMessage());
        }
        return generatorNameAndIdMap;
    }

    /**
     * 根据uuid查询发电机信息
     *
     * @param uuid String
     * @return Generator
     */
    public Generator selectGeneratorInfoByUuid(String uuid) {
        Generator generator = new Generator();
        if (uuid == null)
            return generator;

        String sqlStr = "select b.id uuid, " +
                "b.name name, " +
                "b.expiry_date outservicedate, " +
                "b.operate_date inservicedate, " +
                "b.voltage_type voltagelevel, " +
                "b.dispatch_org_id dispatcheruuid, " +
                "b.owner owneruuid, " +
                "b.st_id substationuuid, " +
                "b.gn_type type, " +
                "b.running_state servicestatus, " +
                "b.power_rate design_ratedpf, " +
                "b.mva_rate design_rateds, " +
                "b.terminal_voltage basevoltage, " +
                "p.q_max design_maxq, " +
                "p.p_min design_minp, " +
                "p.q_min design_minq, " +
                "p.p_max design_ratedmw " +
                "from SG_DEV_GENERATOR_B b, SG_DEV_GENERATOR_P p " +
                "where b.id = p.id " +
                "and b.id = '" + uuid + "' ";
        NRBeanRowMapper<Generator> org_generator = new NRBeanRowMapper<>(Generator.class);

        try {
            generator = m_nrdbAccess.queryForObject(sqlStr, null, org_generator);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return generator;
    }

    /**
     * 通过工程id，查询发电机信息
     * 查询返回所有发电机实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectGeneratorList(String projectId, String userName, String userId) {
        List<Object> generatorList = new ArrayList<>();
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
                "b.gn_type type, " +
                "b.running_state servicestatus, " +
                "b.power_rate design_ratedpf, " +
                "b.mva_rate design_rateds, " +
                "b.terminal_voltage basevoltage, " +
                "p.q_max design_maxq, " +
                "p.p_min design_minp, " +
                "p.q_min design_minq, " +
                "p.p_max design_ratedmw " +
                "from SG_DEV_GENERATOR_B b, SG_DEV_GENERATOR_P p " +
                "where b.id = p.id " );
        NRBeanRowMapper<Generator> org_generator = new NRBeanRowMapper<>(Generator.class);

        try {
            for (String sqlStr : sqlList) {
                generatorList.addAll(m_nrdbAccess.query(sqlStr, null, org_generator));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : generatorList) {
            Generator generator = (Generator) object;
            System.out.println("==========" + "id:" + generator.getUuid() + "  name:" + generator.getName());
        }
        return generatorList;
    }


    /**
     * 通过工程id，查询发电机信息
     * 查询返回所有发电机实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectGeneratorListById(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> generatorList = new ArrayList<>();
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
                "b.gn_type type, " +
                "b.running_state servicestatus, " +
                "b.power_rate design_ratedpf, " +
                "b.mva_rate design_rateds, " +
                "b.terminal_voltage basevoltage, " +
                "p.q_max design_maxq, " +
                "p.p_min design_minp, " +
                "p.q_min design_minq, " +
                "p.p_max design_ratedmw " +
                "from SG_DEV_GENERATOR_B b, SG_DEV_GENERATOR_P p " +
                "where b.id = p.id " +
                "and b.id in (" + idListStr + ") ");
        NRBeanRowMapper<Generator> org_generator = new NRBeanRowMapper<>(Generator.class);
        try {
            for (String sqlStr : sqlList) {
                generatorList.addAll(m_nrdbAccess.query(sqlStr, null, org_generator));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : generatorList) {
            Generator generator = (Generator) object;
            System.out.println("==========" + "id:" + generator.getUuid() + "  name:" + generator.getName());
        }
        return generatorList;
    }


    /**
     * 更新发电机信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueMap
     * @return
     */
    public List<Boolean> updateGenerator(String projectId, String userName, String userId, String generatorId, Map<String, String> changNameAndValueMap) {

        List<Boolean> resList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(generatorId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {
            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            Pair<String, String> tableAndColumName = generatorAttToDkyTableMap.get(columnName);
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

            Integer type = generatorAttAndTypeMap.get(columnName);
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


    public List<Boolean> insertGenerator(String projectId, String userName, String userId, Generator generator) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("test_user");
        userInfo.setUser_name("1");

        List<RecordModifyResult> result;
        List<Boolean> resList = new ArrayList<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 发电机重名 直接返回空的list
        if (generatorAttToDkyTableMap == null || generatorAttAndTypeMap == null || generatorNameAndIdMap.containsKey(generator.getName()))
            return resList;

        for (Field field : generator.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(generator);
                if (fieldValue == null)
                    continue;
                if (!generatorAttToDkyTableMap.containsKey(fieldName) || !generatorAttAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = generatorAttToDkyTableMap.get(fieldName);
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
                int dkyColumnType = generatorAttAndTypeMap.get(fieldName);
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

        List<RecordColumnInfo> generatorRecordList_b = recordColumnInfoMap.get("SG_DEV_GENERATOR_B");
        generatorRecordList_b.add(stampColumnInfo);
        RecordInfo recordInfo_b = new RecordInfo();
        recordInfo_b.setRecordColumnInfo(generatorRecordList_b);
        recordInfo_b.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_b);

        String insertGeneratorId = "";
        // 设置所属工程信息
        m_nrdbAccess.clearProjectId();
        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = m_nrdbAccess.InsertRecord("SG_DEV_GENERATOR_B", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertGeneratorId = rt.get(j);
                    generatorNameAndIdMap.put(generator.getName(), insertGeneratorId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

        // 更新P表
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!insertGeneratorId.isEmpty()) {
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            columnInfo.setColumn_name("ID");
            columnInfo.setIs_key(true);
            columnInfo.setColumn_value(insertGeneratorId);
            columnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

            List<RecordColumnInfo> generatorRecordList_p = recordColumnInfoMap.get("SG_DEV_GENERATOR_P");
            generatorRecordList_p.add(stampColumnInfo);
            generatorRecordList_p.add(columnInfo);

            RecordInfo recordInfo_p = new RecordInfo();
            recordInfo_p.setRecordColumnInfo(generatorRecordList_p);
            recordInfo_p.setRegion_id("330000");
            List<RecordInfo> recordInfoList_p = new ArrayList<>();
            recordInfoList_p.add(recordInfo_p);
            // 设置所属工程信息
            m_nrdbAccess.clearProjectId();
            m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
            try {
                result = m_nrdbAccess.UpdateRecord("SG_DEV_GENERATOR_P", recordInfoList_p);
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


    public List<Boolean> deleteGenerator(String projectId, String userName, String userId, String generatorId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("1");
        userInfo.setUser_name("test_user");

        List<Boolean> resList = new ArrayList<>();
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(generatorId);
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
            result = m_nrdbAccess.DeleteRecord("SG_DEV_GENERATOR_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resList.add(re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resList;
    }

}
