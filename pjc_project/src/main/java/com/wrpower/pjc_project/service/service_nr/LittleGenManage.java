package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.entity.LittleGen;
import com.wrpower.pjc_project.service.ReadConfigFile;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LittleGenManage {

//    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    // 这个用来记录 小电源类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> littleGenAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("littlegen");
    // 小电源类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> littleGenAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("littlegen");
    private Map<String, String> littleGenNameAndIdMap = selectLittleGenNameAndIdMap();

//    static {
//        m_nrdbAccess.setProxyIp("10.33.3.31");
//    }

    public static Map<String, String> selectLittleGenNameAndIdMap() {
        Map<String, String> littleGenNameAndIdMap = new HashMap<>();
        String sql = " select id, nameuuid from ZJ_DEV_LITTLEGEN_G ";
        try {
            List<Map<String, Object>> map = NRDBAccessManage.NRDBAccess().queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                littleGenNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("littleGenNameAndIdMap:" + e.getMessage());
        }
        return littleGenNameAndIdMap;
    }

    /**
     * 根据uuid查询小电源信息
     *
     * @param uuid String
     * @return LittleGen
     */
    public LittleGen selectLittleGenInfoByUuid(String projectId, String userName, String userId, String uuid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        LittleGen littleGen = new LittleGen();
        if (uuid == null)
            return littleGen;
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        String sqlStr = "select * " +
                "from ZJ_DEV_LITTLEGEN_G " +
                "where id = '" + uuid + "' ";
        NRBeanRowMapper<LittleGen> org_LittleGen = new NRBeanRowMapper<>(LittleGen.class);

        try {
            littleGen = NRDBAccessManage.NRDBAccess().queryForObject(sqlStr, null, org_LittleGen);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        littleGen.setInservicedate(BasicManage.longToDataStr(littleGen.getInservicedate()));
        littleGen.setOutservicedate(BasicManage.longToDataStr(littleGen.getOutservicedate()));
        littleGen.setServicestatus(BasicManage.devStateCodeToString(littleGen.getServicestatus()));

        System.out.println("==========" + "id:" + littleGen.getUuid() + "  name:" + littleGen.getNameuuid() + "time:" + littleGen.getInservicedate() + " " + littleGen.getOutservicedate());

        return littleGen;
    }

    /**
     * 通过工程id，查询小电源信息
     * 查询返回所有小电源实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectLittleGenList(String projectId, String userName, String userId) {
        List<Object> littleGenList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
        sqlList.add("select *  " +
                "from ZJ_DEV_LITTLEGEN_G ");
        NRBeanRowMapper<LittleGen> org_littleGen = new NRBeanRowMapper<>(LittleGen.class);

        try {
            for (String sqlStr : sqlList) {
                littleGenList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_littleGen));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : littleGenList) {
            LittleGen littleGen = (LittleGen) object;
            littleGen.setInservicedate(BasicManage.longToDataStr(littleGen.getInservicedate()));
            littleGen.setOutservicedate(BasicManage.longToDataStr(littleGen.getOutservicedate()));
            littleGen.setServicestatus(BasicManage.devStateCodeToString(littleGen.getServicestatus()));
            System.out.println("==========" + "id:" + littleGen.getUuid() + "  name:" + littleGen.getNameuuid());
        }
        return littleGenList;
    }


    /**
     * 通过工程id，查询小电源信息
     * 查询返回所有小电源实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectLittleGenListByIdList(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> littleGenList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        String idListStr = BasicManage.join(keyIdList);
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * " +
                "from ZJ_DEV_LITTLEGEN_G " +
                "where id in (" + idListStr + ") ");
        NRBeanRowMapper<LittleGen> org_littleGen = new NRBeanRowMapper<>(LittleGen.class);
        try {
            for (String sqlStr : sqlList) {
                littleGenList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_littleGen));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : littleGenList) {
            LittleGen littleGen = (LittleGen) object;
            littleGen.setInservicedate(BasicManage.longToDataStr(littleGen.getInservicedate()));
            littleGen.setOutservicedate(BasicManage.longToDataStr(littleGen.getOutservicedate()));
            littleGen.setServicestatus(BasicManage.devStateCodeToString(littleGen.getServicestatus()));
            System.out.println("==========" + "id:" + littleGen.getUuid() + "  name:" + littleGen.getNameuuid());
        }
        return littleGenList;
    }


    /**
     * 更新小电源信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId            String
     * @param userName             String
     * @param userId               String
     * @param changNameAndValueMap String
     * @return List<Boolean>
     */
    public Map<Boolean, String> updateLittleGen(String projectId, String userName, String userId, String LittleGenId, Map<String, String> changNameAndValueMap) {

        Map<Boolean, String> resMap = new HashMap<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(LittleGenId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {
            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            Pair<String, String> tableAndColumName = littleGenAttToDkyTableMap.get(columnName);
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

            Integer type = littleGenAttAndTypeMap.get(columnName);
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


    public Map<Boolean, String> insertLittleGen(String projectId, String userName, String userId, String orgId, LittleGen littleGen) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        List<RecordModifyResult> result;
        Map<Boolean, String> resMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 变压器重名 直接返回 <false,msg>
        if (littleGenAttToDkyTableMap == null || littleGenAttAndTypeMap == null) {
            resMap.put(false, "attrToDkyTable文件中littleGen表数据为空！");
            return resMap;
        }

        if (littleGenNameAndIdMap.containsKey(littleGen.getNameuuid())) {
            resMap.put(false, "小电源重名！");
            return resMap;
        }

        for (Field field : littleGen.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(littleGen);
                if (fieldValue == null)
                    continue;
                if (!littleGenAttToDkyTableMap.containsKey(fieldName) || !littleGenAttAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = littleGenAttToDkyTableMap.get(fieldName);
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
                int dkyColumnType = littleGenAttAndTypeMap.get(fieldName);
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

        //小电源 直接插入G表
        List<RecordColumnInfo> recordList_g = recordColumnInfoMap.get("ZJ_DEV_LITTLEGEN_G");
        RecordInfo recordInfo_g = new RecordInfo();
        recordInfo_g.setRecordColumnInfo(recordList_g);
        recordInfo_g.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_g);

        String insertLittleGenId = "";
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = NRDBAccessManage.NRDBAccess().InsertRecord("ZJ_DEV_LITTLEGEN_G", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("ZJ_DEV_LITTLEGEN_G===================" + re.getErr_msg() + " " + re.isIs_success());
                if (!re.isIs_success()) {
                    resMap.put(false, re.getErr_msg());
                    return resMap;
                }
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertLittleGenId = rt.get(j);
                    littleGenNameAndIdMap.put(littleGen.getNameuuid(), insertLittleGenId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        resMap.put(true, insertLittleGenId);
        return resMap;
    }


    public Map<Boolean, String> deleteLittleGen(String projectId, String userName, String userId, String littleGenId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<Boolean, String> resMap = new HashMap<>();
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(littleGenId);
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
            result = NRDBAccessManage.NRDBAccess().DeleteRecord("ZJ_DEV_LITTLEGEN_G", recordInfoList);
            for (RecordModifyResult re : result) {
                resMap.put(re.isIs_success(), re.getErr_msg());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resMap;
    }


}
