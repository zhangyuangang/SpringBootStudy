package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.entity.Busbar;
import com.wrpower.pjc_project.service.ReadConfigFile;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.*;

public class BusbarManage {

//    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();

    // 这个用来记录 母线类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> busbarAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("busbar");
    // 母线类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> busbarAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("busbar");
    private Map<String, String> busbarNameAndIdMap = selectBusbarNameAndIdMap();

//    static {
//        m_nrdbAccess.setProxyIp("10.33.3.31");
//    }

    public static Map<String, String> selectBusbarNameAndIdMap() {
        Map<String, String> busbarNameAndIdMap = new HashMap<>();
        String sql = " select id, name from SG_DEV_BUSBAR_B ";
        try {
            List<Map<String, Object>> map = NRDBAccessManage.NRDBAccess().queryForList(sql, null);
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
    public Busbar selectBusbarInfoByUuid(String projectId, String userName, String userId, String uuid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Busbar busbar = new Busbar();
        if (uuid == null)
            return busbar;
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
//        String sqlStr = "select b.id uuid, " +
//                "b.name name, " +
//                "b.expiry_date outservicedate, " +
//                "b.operate_date inservicedate, " +
//                "b.voltage_type voltagelevel, " +
//                "b.st_id substationuuid, " +
//                "b.running_state servicestatus, " +
//                "m.base_v basevoltage, " +
//                "m.check_v_max volhi, " +
//                "m.check_v_min volho," +
//                "g.* " +
//                "from SG_DEV_BUSBAR_B b, SG_DEV_BUSBAR_M m, ZJ_DEV_BUSBAR_G g " +
//                "where g.id = b.id " +
//                "and g.id = m.id " +
//                "and g.id = '" + uuid + "' ";

        String sqlStr = "select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.st_id substationuuid, " +
                "t1.owner owneruuid, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.running_state servicestatus, " +
                "t2.base_v basevoltage, " +
                "t2.check_v_max volhi, " +
                "t2.check_v_min volho " +
                "from SG_DEV_BUSBAR_B t1, SG_DEV_BUSBAR_M t2 " +
                "where t1.id = t2.id " +
                "and t1.id = '" + uuid + "' ";
        NRBeanRowMapper<Busbar> org_busbar = new NRBeanRowMapper<>(Busbar.class);
        System.out.println("sqlStr:" + sqlStr );

        try {
            busbar = NRDBAccessManage.NRDBAccess().queryForObject(sqlStr, null, org_busbar);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        busbar.setInservicedate(BasicManage.longToDataStr(busbar.getInservicedate()));
        busbar.setOutservicedate(BasicManage.longToDataStr(busbar.getOutservicedate()));
        busbar.setServicestatus( BasicManage.devStateCodeToString( busbar.getServicestatus() ) );

        System.out.println("==========" + "id:" + busbar.getUuid() + "  name:" + busbar.getName() + "time:" + busbar.getInservicedate() + " " + busbar.getOutservicedate());

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
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
//        sqlList.add("select b.id uuid, " +
//                "b.name name, " +
//                "b.expiry_date outservicedate, " +
//                "b.operate_date inservicedate, " +
//                "b.voltage_type voltagelevel, " +
//                "b.st_id substationuuid, " +
//                "b.running_state servicestatus, " +
//                "m.base_v basevoltage, " +
//                "m.check_v_max volhi, " +
//                "m.check_v_min vollo," +
//                "g.* " +
//                "from SG_DEV_BUSBAR_B b, SG_DEV_BUSBAR_M m, ZJ_DEV_BUSBAR_G g " +
//                "where g.id = b.id " +
//                "and g.id = m.id");

        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.st_id substationuuid, " +
                "t1.owner owneruuid, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.running_state servicestatus, " +
                "t2.base_v basevoltage, " +
                "t2.check_v_max volhi, " +
                "t2.check_v_min vollo " +
                "from SG_DEV_BUSBAR_B t1, SG_DEV_BUSBAR_M t2 " +
                "where t1.id = t2.id ");
        NRBeanRowMapper<Busbar> org_busbar = new NRBeanRowMapper<>(Busbar.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                busbarList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_busbar));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : busbarList) {
            Busbar busbar = (Busbar) object;
            busbar.setInservicedate(BasicManage.longToDataStr(busbar.getInservicedate()));
            busbar.setOutservicedate(BasicManage.longToDataStr(busbar.getOutservicedate()));
            busbar.setServicestatus( BasicManage.devStateCodeToString( busbar.getServicestatus() ) );

            System.out.println("==========" + "id:" + busbar.getUuid() + "  name:" + busbar.getName() + "time:" + busbar.getInservicedate() + " " + busbar.getOutservicedate());
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
    public List<Object> selectBusbarListByIdList(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> busbarList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        if (keyIdList.size() == 0)
            return busbarList;
        String idListStr = BasicManage.join(keyIdList);
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
//        sqlList.add("select b.id uuid, " +
//                "b.name name, " +
//                "b.expiry_date outservicedate, " +
//                "b.operate_date inservicedate, " +
//                "b.voltage_type voltagelevel, " +
//                "b.st_id substationuuid, " +
//                "b.running_state servicestatus, " +
//                "m.base_v basevoltage, " +
//                "m.check_v_max volhi, " +
//                "m.check_v_min vollo," +
//                "g.* " +
//                "from SG_DEV_BUSBAR_B b, SG_DEV_BUSBAR_M m, ZJ_DEV_BUSBAR_G g " +
//                "where g.id = b.id " +
//                "and g.id = m.id" +
//                "and g.id in (" + idListStr + ") ");
        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.st_id substationuuid, " +
                "t1.owner owneruuid, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.running_state servicestatus, " +
                "t2.base_v basevoltage, " +
                "t2.check_v_max volhi, " +
                "t2.check_v_min vollo " +
                "from SG_DEV_BUSBAR_B t1, SG_DEV_BUSBAR_M t2 " +
                "where t1.id = t2.id " +
                "and t1.id in (" + idListStr + ") ");
        NRBeanRowMapper<Busbar> org_busbar = new NRBeanRowMapper<>(Busbar.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                busbarList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_busbar));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : busbarList) {
            Busbar busbar = (Busbar) object;
            busbar.setInservicedate(BasicManage.longToDataStr(busbar.getInservicedate()));
            busbar.setOutservicedate(BasicManage.longToDataStr(busbar.getOutservicedate()));
            busbar.setServicestatus( BasicManage.devStateCodeToString( busbar.getServicestatus() ) );

            System.out.println("==========" + "id:" + busbar.getUuid() + "  name:" + busbar.getName() + "time:" + busbar.getInservicedate() + " " + busbar.getOutservicedate());
        }
        return busbarList;
    }


    public List<Object> selectBusbarListByIdListAndDate(String projectId, String userName, String userId, List<String> keyIdList,String dateMin, String dateMax) {
        List<Object> busbarList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        if (keyIdList.size() == 0)
            return busbarList;
        String idListStr = BasicManage.join(keyIdList);
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
//        sqlList.add("select b.id uuid, " +
//                "b.name name, " +
//                "b.expiry_date outservicedate, " +
//                "b.operate_date inservicedate, " +
//                "b.voltage_type voltagelevel, " +
//                "b.st_id substationuuid, " +
//                "b.running_state servicestatus, " +
//                "m.base_v basevoltage, " +
//                "m.check_v_max volhi, " +
//                "m.check_v_min vollo," +
//                "g.* " +
//                "from SG_DEV_BUSBAR_B b, SG_DEV_BUSBAR_M m, ZJ_DEV_BUSBAR_G g " +
//                "where g.id = b.id " +
//                "and g.id = m.id" +
//                "and g.id in (" + idListStr + ") ");
        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.st_id substationuuid, " +
                "t1.owner owneruuid, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.running_state servicestatus, " +
                "t2.base_v basevoltage, " +
                "t2.check_v_max volhi, " +
                "t2.check_v_min vollo " +
                "from SG_DEV_BUSBAR_B t1, SG_DEV_BUSBAR_M t2 " +
                "where t1.id = t2.id " +
                "and t1.operate_date between to_date('"+dateMin + "',\"yyyy-MM-DD\") " +
                "and to_date('"+ dateMax + "',\"yyyy-MM-DD\") " +
                "and t1.id in (" + idListStr + ") ");
        NRBeanRowMapper<Busbar> org_busbar = new NRBeanRowMapper<>(Busbar.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                busbarList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_busbar));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : busbarList) {
            Busbar busbar = (Busbar) object;
            busbar.setInservicedate(BasicManage.longToDataStr(busbar.getInservicedate()));
            busbar.setOutservicedate(BasicManage.longToDataStr(busbar.getOutservicedate()));
            busbar.setServicestatus( BasicManage.devStateCodeToString( busbar.getServicestatus() ) );

            System.out.println("==========" + "id:" + busbar.getUuid() + "  name:" + busbar.getName() + "time:" + busbar.getInservicedate() + " " + busbar.getOutservicedate());
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
    public Map<Boolean, String> updateBusbar(String projectId, String userName, String userId, String busbarId, Map<String, String> changNameAndValueMap) {

        Map<Boolean, String> resMap = new HashMap<>();
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

            Integer type = busbarAttAndTypeMap.get(columnName);
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
     * 批量新增母线信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public Map<Boolean, String> insertBusbar(String projectId, String userName, String userId, String orgId, Busbar busbar) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<Boolean, String> resMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 母线重名 直接返回空的list

        if (busbarAttToDkyTableMap == null || busbarAttAndTypeMap == null ) {
            resMap.put(false, "attrToDkyTable文件中busbar表数据为空！");
            return resMap;
        }

        if (busbarNameAndIdMap.containsKey(busbar.getName())) {
            resMap.put(false, "母线重名！");
            return resMap;
        }
        for (Field field : busbar.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(busbar);
                if (fieldValue == null)
                    continue;
                if (!busbarAttToDkyTableMap.containsKey(fieldName) || !busbarAttAndTypeMap.containsKey(fieldName)) {
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
                if ("servicestatus".equals(fieldName))       //前端传过来的是 待投运 和 投运
                {
                    fieldValue = BasicManage.devStateStringToCode( fieldValue );
                }
                String dkyTableName = pair.getKey();
                String dkyColumnName = pair.getValue();
                int dkyColumnType = busbarAttAndTypeMap.get(fieldName);
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
        dispatchColumnInfo.setColumn_name("dispatch_org_id");
        dispatchColumnInfo.setColumn_value(orgId);
        dispatchColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);
        // 设置owner
        RecordColumnInfo ownerColumnInfo = new RecordColumnInfo();
        ownerColumnInfo.setColumn_name("owner");
        ownerColumnInfo.setColumn_value(orgId.substring(4));
        ownerColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        List<RecordColumnInfo> recordList_b = recordColumnInfoMap.get("SG_DEV_BUSBAR_B");
        recordList_b.add(stampColumnInfo);
        recordList_b.add(dispatchColumnInfo);
        recordList_b.add(ownerColumnInfo);
        RecordInfo recordInfo_b = new RecordInfo();
        recordInfo_b.setRecordColumnInfo(recordList_b);
        recordInfo_b.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_b);

        String insertBusbarId = "";
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;

        try {
            result = NRDBAccessManage.NRDBAccess().InsertRecord("SG_DEV_BUSBAR_B", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("SG_DEV_BUSBAR_B===================" + re.getErr_msg() + " " + re.isIs_success());
                if (!re.isIs_success()) {
                    resMap.put(false, "SG_DEV_BUSBAR_B表插入失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
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

        // 如果插入失败，没有返回id，则直接返回错误
        if (insertBusbarId.isEmpty()) {
            resMap.put(false, "SG_DEV_BUSBAR_B表插入失败！错误原因：insertAclineId 为空！");
            return resMap;
        }

        // 更新M表
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_value(insertBusbarId);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        List<RecordColumnInfo> recordList_m = recordColumnInfoMap.get("SG_DEV_BUSBAR_M");
        recordList_m.add(stampColumnInfo);
        recordList_m.add(idColumnInfo);

        RecordInfo recordInfo_m = new RecordInfo();
        recordInfo_m.setRecordColumnInfo(recordList_m);
        recordInfo_m.setRegion_id("330000");
        List<RecordInfo> recordInfoList_m = new ArrayList<>();
        recordInfoList_m.add(recordInfo_m);
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = NRDBAccessManage.NRDBAccess().UpdateRecord("SG_DEV_BUSBAR_M", recordInfoList_m);
            for (RecordModifyResult re : result) {
                if (!re.isIs_success()) {
                    resMap.put(false, "SG_DEV_BUSBAR_M表更新失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
                System.out.println("SG_DEV_BUSBAR_M===================" + re.getErr_msg() + " " + re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

//        // 更新G表
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        List<RecordColumnInfo> recordList_g = recordColumnInfoMap.get("ZJ_DEV_BUSBAR_G");
//        recordList_g.add(idColumnInfo);
//
//        RecordInfo recordInfo_g = new RecordInfo();
//        recordInfo_g.setRecordColumnInfo(recordList_g);
//        recordInfo_g.setRegion_id("330000");
//        List<RecordInfo> recordInfoList_g = new ArrayList<>();
//        recordInfoList_g.add(recordInfo_g);
//        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
//        try {
//            result = NRDBAccessManage.NRDBAccess().UpdateRecord("ZJ_DEV_BUSBAR_G", recordInfoList_g);
//            for (RecordModifyResult re : result) {
//                if (!re.isIs_success()) {
//                    resMap.put(false, "ZJ_DEV_BUSBAR_G表更新失败！错误原因：" + re.getErr_msg());
//                    return resMap;
//                }
//                System.out.println("ZJ_DEV_BUSBAR_G===================" + re.getErr_msg() + " " + re.isIs_success());
//            }
//        } catch (NRDataAccessException e) {
//            e.printStackTrace();
//        }

        resMap.put(true, insertBusbarId);
        return resMap;
    }

    /**
     * 批量删除母线信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public Map<Boolean, String> deleteBusbar(String projectId, String userName, String userId, String devId) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<Boolean, String> resMap = new HashMap<>();
        // 所有删除数据对应 Map<类型、List<数据> > 考虑到可能批量删除不同厂站类型的数据
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(devId);
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
            result = NRDBAccessManage.NRDBAccess().DeleteRecord("SG_DEV_BUSBAR_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resMap.put(re.isIs_success(), re.getErr_msg());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resMap;
    }
}
