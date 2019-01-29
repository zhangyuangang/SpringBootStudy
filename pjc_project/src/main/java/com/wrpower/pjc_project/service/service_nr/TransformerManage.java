package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.entity.Transformer;
import com.wrpower.pjc_project.service.ReadConfigFile;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.*;

public class TransformerManage {

//    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    // 这个用来记录 变压器类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> transformerAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("transformer");
    // 变压器类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> transformerAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("transformer");
    private Map<String, String> transformerNameAndIdMap = selectTransformerNameAndIdMap();

//    static {
//        m_nrdbAccess.setProxyIp("10.33.3.31");
//    }

    public static Map<String, String> selectTransformerNameAndIdMap() {
        Map<String, String> transformerNameAndIdMap = new HashMap<>();
        String sql = " select id, name from SG_DEV_PWRTRANSFM_B ";
        try {
            List<Map<String, Object>> map = NRDBAccessManage.NRDBAccess().queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                transformerNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("transformerNameAndIdMap:" + e.getMessage());
        }
        return transformerNameAndIdMap;
    }

    /**
     * 根据uuid查询变压器信息
     *
     * @param uuid String
     * @return Transformer
     */
    public Transformer selectTransformerInfoByUuid(String projectId, String userName, String userId, String uuid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        Transformer transformer = new Transformer();
        if (uuid == null)
            return transformer;

//        String sqlStr = "select b.id uuid, " +
//                "b.name name, " +
//                "b.expiry_date outservicedate, " +
//                "b.operate_date inservicedate, " +
//                "b.voltage_type voltagelevel, " +
//                "b.st_id substationuuid, " +
//                "b.usage type, " +
//                "b.wind_type istran3, " +
//                "b.running_state servicestatus, " +
//                "b.zj_rzjxfs windingconnection, " +
//                "p.zj_kzdlbfb design_i0, " +
//                "p.zj_kzsh design_p0, " +
//                "p.zj_dlsh_gz design_pk12, " +
//                "p.zj_dlsh_gd design_pk13, " +
//                "p.zj_dlsh_zd design_pk23, " +
//                "p.zj_dldy_gz design_uk12, " +
//                "p.zj_dldy_gd design_uk13, " +
//                "p.zj_dldy_zd design_uk23, " +
//                "p.zj_gfhbs_40 emergencyratio40," +
//                "g.* " +
//                "from SG_DEV_PWRTRANSFM_B b, SG_DEV_PWRTRANSFM_P p, ZJ_DEV_TRANSFORMER_G g " +
//                "where g.id = b.id " +
//                "and g.id = p.id " +
//                "and g.id = '" + uuid + "' ";

        String sqlStr = "select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.owner owneruuid, " +
                "t1.st_id substationuuid, " +
                "t1.usage type, " +
                "t1.wind_type istran3, " +
                "t1.running_state servicestatus, " +
                "t1.zj_rzjxfs windingconnection, " +
                "t2.zj_kzdlbfb design_i0, " +
                "t2.zj_kzsh design_p0, " +
                "t2.zj_dlsh_gz design_pk12, " +
                "t2.zj_dlsh_gd design_pk13, " +
                "t2.zj_dlsh_zd design_pk23, " +
                "t2.zj_dldy_gz design_uk12, " +
                "t2.zj_dldy_gd design_uk13, " +
                "t2.zj_dldy_zd design_uk23, " +
                "t2.zj_gfhbs_40 emergencyratio40 " +
                "from SG_DEV_PWRTRANSFM_B t1, SG_DEV_PWRTRANSFM_P t2 " +
                "where t1.id = t2.id " +
                "and t1.id = '" + uuid + "' ";
        NRBeanRowMapper<Transformer> org_transformer = new NRBeanRowMapper<>(Transformer.class);
        System.out.println("sqlStr:" + sqlStr );

        try {
            transformer = NRDBAccessManage.NRDBAccess().queryForObject(sqlStr, null, org_transformer);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        transformer.setInservicedate(BasicManage.longToDataStr(transformer.getInservicedate()));
        transformer.setOutservicedate(BasicManage.longToDataStr(transformer.getOutservicedate()));
        transformer.setServicestatus(BasicManage.devStateCodeToString(transformer.getServicestatus()));
        transformer.setIstran3(transformer.getIstran3().equals("1") ? "是" : "否");
        transformer.setType(BasicManage.tranTypeNameToCode(transformer.getType()));

        System.out.println("==========" + "id:" + transformer.getUuid() + "  name:" + transformer.getName() + "time:" + transformer.getInservicedate() + " " + transformer.getOutservicedate());

        return transformer;
    }

    /**
     * 通过工程id，查询变压器信息
     * 查询返回所有变压器实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectTransformerList(String projectId, String userName, String userId) {
        List<Object> transformerList = new ArrayList<>();
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
//                "b.usage type, " +
//                "b.wind_type istran3, " +
//                "b.running_state servicestatus, " +
//                "b.zj_rzjxfs windingconnection, " +
//                "p.zj_kzdlbfb design_i0, " +
//                "p.zj_kzsh design_p0, " +
//                "p.zj_dlsh_gz design_pk12, " +
//                "p.zj_dlsh_gd design_pk13, " +
//                "p.zj_dlsh_zd design_pk23, " +
//                "p.zj_dldy_gz design_uk12, " +
//                "p.zj_dldy_gd design_uk13, " +
//                "p.zj_dldy_zd design_uk23, " +
//                "p.zj_gfhbs_40 emergencyratio40," +
//                "g.* " +
//                "from SG_DEV_PWRTRANSFM_B b, SG_DEV_PWRTRANSFM_P p, ZJ_DEV_TRANSFORMER_G g  " +
//                "where g.id = b.id " +
//                "and g.id = p.id");

        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.owner owneruuid, " +
                "t1.st_id substationuuid, " +
                "t1.usage type, " +
                "t1.wind_type istran3, " +
                "t1.running_state servicestatus, " +
                "t1.zj_rzjxfs windingconnection, " +
                "t2.zj_kzdlbfb design_i0, " +
                "t2.zj_kzsh design_p0, " +
                "t2.zj_dlsh_gz design_pk12, " +
                "t2.zj_dlsh_gd design_pk13, " +
                "t2.zj_dlsh_zd design_pk23, " +
                "t2.zj_dldy_gz design_uk12, " +
                "t2.zj_dldy_gd design_uk13, " +
                "t2.zj_dldy_zd design_uk23, " +
                "t2.zj_gfhbs_40 emergencyratio40 " +
                "from SG_DEV_PWRTRANSFM_B t1, SG_DEV_PWRTRANSFM_P t2 " +
                "where t1.id = t2.id ");
        NRBeanRowMapper<Transformer> org_transformer = new NRBeanRowMapper<>(Transformer.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                transformerList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_transformer));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : transformerList) {
            Transformer transformer = (Transformer) object;
            transformer.setInservicedate(BasicManage.longToDataStr(transformer.getInservicedate()));
            transformer.setOutservicedate(BasicManage.longToDataStr(transformer.getOutservicedate()));
            transformer.setServicestatus(BasicManage.devStateCodeToString(transformer.getServicestatus()));
            transformer.setIstran3(transformer.getIstran3().equals("1") ? "是" : "否");
            transformer.setType(BasicManage.tranTypeNameToCode(transformer.getType()));
            System.out.println("==========" + "id:" + transformer.getUuid() + "  name:" + transformer.getName() + "time:" + transformer.getInservicedate() + " " + transformer.getOutservicedate());
        }
        return transformerList;
    }


    /**
     * 通过工程id，查询变压器信息
     * 查询返回所有变压器实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectTransformerListByIdList(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> transformerList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        if (keyIdList.size() == 0)
            return transformerList;
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
//                "b.usage type, " +
//                "b.wind_type istran3, " +
//                "b.running_state servicestatus, " +
//                "b.zj_rzjxfs windingconnection, " +
//                "p.zj_kzdlbfb design_i0, " +
//                "p.zj_kzsh design_p0, " +
//                "p.zj_dlsh_gz design_pk12, " +
//                "p.zj_dlsh_gd design_pk13, " +
//                "p.zj_dlsh_zd design_pk23, " +
//                "p.zj_dldy_gz design_uk12, " +
//                "p.zj_dldy_gd design_uk13, " +
//                "p.zj_dldy_zd design_uk23, " +
//                "p.zj_gfhbs_40 emergencyratio40, " +
//                "g.* " +
//                "from SG_DEV_PWRTRANSFM_B b, SG_DEV_PWRTRANSFM_P p, ZJ_DEV_TRANSFORMER_G g " +
//                "where g.id = b.id " +
//                "where g.id = p.id " +
//                "and g.id in (" + idListStr + ") ");

        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.owner owneruuid, " +
                "t1.st_id substationuuid, " +
                "t1.usage type, " +
                "t1.wind_type istran3, " +
                "t1.running_state servicestatus, " +
                "t1.zj_rzjxfs windingconnection, " +
                "t2.zj_kzdlbfb design_i0, " +
                "t2.zj_kzsh design_p0, " +
                "t2.zj_dlsh_gz design_pk12, " +
                "t2.zj_dlsh_gd design_pk13, " +
                "t2.zj_dlsh_zd design_pk23, " +
                "t2.zj_dldy_gz design_uk12, " +
                "t2.zj_dldy_gd design_uk13, " +
                "t2.zj_dldy_zd design_uk23, " +
                "t2.zj_gfhbs_40 emergencyratio40 " +
                "from SG_DEV_PWRTRANSFM_B t1, SG_DEV_PWRTRANSFM_P t2 " +
                "where t1.id = t2.id " +
                "and t1.id in (" + idListStr + ") ");
        NRBeanRowMapper<Transformer> org_transformer = new NRBeanRowMapper<>(Transformer.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                transformerList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_transformer));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : transformerList) {
            Transformer transformer = (Transformer) object;
            transformer.setInservicedate(BasicManage.longToDataStr(transformer.getInservicedate()));
            transformer.setOutservicedate(BasicManage.longToDataStr(transformer.getOutservicedate()));
            transformer.setServicestatus(BasicManage.devStateCodeToString(transformer.getServicestatus()));
            transformer.setIstran3(transformer.getIstran3().equals("1") ? "是" : "否");
            transformer.setType(BasicManage.tranTypeNameToCode(transformer.getType()));

            System.out.println("==========" + "id:" + transformer.getUuid() + "  name:" + transformer.getName() + "time:" + transformer.getInservicedate() + " " + transformer.getOutservicedate());
        }
        return transformerList;
    }


    public List<Object> selectTransformerListByIdListAndDate(String projectId, String userName, String userId, List<String> keyIdList,String dateMin, String dateMax) {
        List<Object> transformerList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        if (keyIdList.size() == 0)
            return transformerList;
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
//                "b.usage type, " +
//                "b.wind_type istran3, " +
//                "b.running_state servicestatus, " +
//                "b.zj_rzjxfs windingconnection, " +
//                "p.zj_kzdlbfb design_i0, " +
//                "p.zj_kzsh design_p0, " +
//                "p.zj_dlsh_gz design_pk12, " +
//                "p.zj_dlsh_gd design_pk13, " +
//                "p.zj_dlsh_zd design_pk23, " +
//                "p.zj_dldy_gz design_uk12, " +
//                "p.zj_dldy_gd design_uk13, " +
//                "p.zj_dldy_zd design_uk23, " +
//                "p.zj_gfhbs_40 emergencyratio40, " +
//                "g.* " +
//                "from SG_DEV_PWRTRANSFM_B b, SG_DEV_PWRTRANSFM_P p, ZJ_DEV_TRANSFORMER_G g " +
//                "where g.id = b.id " +
//                "where g.id = p.id " +
//                "and g.id in (" + idListStr + ") ");

        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.owner owneruuid, " +
                "t1.st_id substationuuid, " +
                "t1.usage type, " +
                "t1.wind_type istran3, " +
                "t1.running_state servicestatus, " +
                "t1.zj_rzjxfs windingconnection, " +
                "t2.zj_kzdlbfb design_i0, " +
                "t2.zj_kzsh design_p0, " +
                "t2.zj_dlsh_gz design_pk12, " +
                "t2.zj_dlsh_gd design_pk13, " +
                "t2.zj_dlsh_zd design_pk23, " +
                "t2.zj_dldy_gz design_uk12, " +
                "t2.zj_dldy_gd design_uk13, " +
                "t2.zj_dldy_zd design_uk23, " +
                "t2.zj_gfhbs_40 emergencyratio40 " +
                "from SG_DEV_PWRTRANSFM_B t1, SG_DEV_PWRTRANSFM_P t2 " +
                "where t1.id = t2.id " +
                "and t1.operate_date between to_date('"+dateMin + "',\"yyyy-MM-DD\") " +
                "and to_date('"+ dateMax + "',\"yyyy-MM-DD\") " +
                "and t1.id in (" + idListStr + ") ");
        NRBeanRowMapper<Transformer> org_transformer = new NRBeanRowMapper<>(Transformer.class);
        System.out.println("sqlStr:" + sqlList.get(0) );

        try {
            for (String sqlStr : sqlList) {
                transformerList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_transformer));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : transformerList) {
            Transformer transformer = (Transformer) object;
            transformer.setInservicedate(BasicManage.longToDataStr(transformer.getInservicedate()));
            transformer.setOutservicedate(BasicManage.longToDataStr(transformer.getOutservicedate()));
            transformer.setServicestatus(BasicManage.devStateCodeToString(transformer.getServicestatus()));
            transformer.setIstran3(transformer.getIstran3().equals("1") ? "是" : "否");
            transformer.setType(BasicManage.tranTypeNameToCode(transformer.getType()));

            System.out.println("==========" + "id:" + transformer.getUuid() + "  name:" + transformer.getName() + "time:" + transformer.getInservicedate() + " " + transformer.getOutservicedate());
        }
        return transformerList;
    }


    /**
     * 更新变压器信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueMap
     * @return
     */
    public Map<Boolean, String> updateTransformer(String projectId, String userName, String userId, String transformerId, Map<String, String> changNameAndValueMap) {

        Map<Boolean, String> resMap = new HashMap<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(transformerId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {
            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            Pair<String, String> tableAndColumName = transformerAttToDkyTableMap.get(columnName);
            String dkyTable = tableAndColumName.getKey();
            String dkyColumnName = tableAndColumName.getValue();
            columnInfo.setColumn_name(dkyColumnName);

            // 根据 不同的列名 获取 类型 getColumn_type
            if ("voltagelevel".equals(columnName))       //前端传过来的是电压等级  而不是 code
            {
                columnValue = BasicManage.volNameToCodeMap.get(columnValue);
            }
            if ("inservicedate".equals(columnName) ||
                "outservicedate".equals(columnName))  // 前端传过来的是 2019:01:01 转成 2019:01:01 00:00:00
            {
                columnValue = BasicManage.shortDateStrToLongDateStr(columnValue);
            }
            if ("servicestatus".equals(columnName))       //前端传过来的是 待投运 和 投运
            {
                columnValue = BasicManage.devStateStringToCode(columnValue);
            }
            if ("istran3".equals(columnName))       //前端传过来的是 是、否  转成 1、0
            {
                columnValue = columnValue.equals("是") ? "1" : "0";
            }
            if ("type".equals(columnName))       //前端传过来的是 "主变"、"联变"、"升压变" 转成 1001 2001 3001
            {
                columnValue = BasicManage.tranTypeNameToCode(columnValue);
            }
            //这里应该根据不同的列 进行值的转化 成调控云要求的数值
            columnInfo.setColumn_value(columnValue);

            Integer type = transformerAttAndTypeMap.get(columnName);
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


    public Map<Boolean, String> insertTransformer(String projectId, String userName, String userId, String orgId, Transformer transformer) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        List<RecordModifyResult> result;
        Map<Boolean, String> resMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 变压器重名 直接返回空的list
        if (transformerAttToDkyTableMap == null || transformerAttAndTypeMap == null) {
            resMap.put(false, "attrToDkyTable文件中transformer表数据为空！");
            return resMap;
        }
        if (transformerNameAndIdMap.containsKey(transformer.getName())) {
            resMap.put(false, "变压器重名！");
            return resMap;
        }
        for (Field field : transformer.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(transformer);
                if (fieldValue == null)
                    continue;
                if (!transformerAttToDkyTableMap.containsKey(fieldName) || !transformerAttAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = transformerAttToDkyTableMap.get(fieldName);
                //  Pair<  调控云表名  表中属性 >
                if (pair == null)
                    continue;
                if ("voltagelevel".equals(fieldName))       //前端传过来的是电压等级  而不是 code
                {
                    fieldValue = BasicManage.volNameToCodeMap.get(fieldName);
                }
                if ("inservicedate".equals(fieldName) ||
                    "outservicedate".equals(fieldName))  // 前端传过来的是 2019:01:01 转成 2019:01:01 00:00:00
                {
                    fieldValue = BasicManage.shortDateStrToLongDateStr(fieldValue);
                }
                if ("servicestatus".equals(fieldName))       //前端传过来的是 待投运 和 投运
                {
                    fieldValue = BasicManage.devStateStringToCode(fieldValue);
                }
                if ("istran3".equals(fieldName))       //前端传过来的是 是、否  转成 1、0
                {
                    fieldValue = fieldValue.equals("是") ? "1" : "0";
                }
                if ("type".equals(fieldName))       //前端传过来的是 "主变"、"联变"、"升压变" 转成 1001 2001 3001
                {
                    fieldValue = BasicManage.tranTypeNameToCode(fieldValue);
                }
                String dkyTableName = pair.getKey();
                String dkyColumnName = pair.getValue();
                int dkyColumnType = transformerAttAndTypeMap.get(fieldName);
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

        List<RecordColumnInfo> recordList_b = recordColumnInfoMap.get("SG_DEV_PWRTRANSFM_B");
        recordList_b.add(stampColumnInfo);
        recordList_b.add(dispatchColumnInfo);
        recordList_b.add(ownerColumnInfo);
        RecordInfo recordInfo_b = new RecordInfo();
        recordInfo_b.setRecordColumnInfo(recordList_b);
        recordInfo_b.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_b);

        String insertTransformerId = "";
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = NRDBAccessManage.NRDBAccess().InsertRecord("SG_DEV_PWRTRANSFM_B", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("===================" + re.getErr_msg() + " " + re.isIs_success());
                if (!re.isIs_success()) {
                    resMap.put(false, "SG_DEV_PWRTRANSFM_B表插入失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertTransformerId = rt.get(j);
                    transformerNameAndIdMap.put(transformer.getName(), insertTransformerId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        // 如果插入失败，没有返回id，则直接返回错误
        if (insertTransformerId.isEmpty()) {
            resMap.put(false, "SG_DEV_PWRTRANSFM_B表插入失败！错误原因：insertTransformerId 为空！");
            return resMap;
        }

        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_value(insertTransformerId);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        // 更新P表
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<RecordColumnInfo> transformerRecordList_p = recordColumnInfoMap.get("SG_DEV_PWRTRANSFM_P");
        transformerRecordList_p.add(stampColumnInfo);
        transformerRecordList_p.add(idColumnInfo);
        RecordInfo recordInfo_p = new RecordInfo();
        recordInfo_p.setRecordColumnInfo(transformerRecordList_p);
        recordInfo_p.setRegion_id("330000");
        List<RecordInfo> recordInfoList_p = new ArrayList<>();
        recordInfoList_p.add(recordInfo_p);
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = NRDBAccessManage.NRDBAccess().UpdateRecord("SG_DEV_PWRTRANSFM_P", recordInfoList_p);
            for (RecordModifyResult re : result) {
                System.out.println("SG_DEV_PWRTRANSFM_P===================" + re.getErr_msg() + " " + re.isIs_success());
                if (!re.isIs_success()) {
                    resMap.put(false, "SG_DEV_PWRTRANSFM_P表更新失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

//        // 更新TRANSFMWD_B表
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        List<RecordColumnInfo> transformerRecordList_tb = recordColumnInfoMap.get("SG_DEV_TRANSFMWD_B");
//        transformerRecordList_tb.add(stampColumnInfo);
//        transformerRecordList_tb.add(idColumnInfo);
//        RecordInfo recordInfo_tb = new RecordInfo();
//        recordInfo_tb.setRecordColumnInfo(transformerRecordList_tb);
//        recordInfo_tb.setRegion_id("330000");
//        List<RecordInfo> recordInfoList_tb = new ArrayList<>();
//        recordInfoList_tb.add(recordInfo_tb);
//        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
//        try {
//            result = NRDBAccessManage.NRDBAccess().UpdateRecord("SG_DEV_TRANSFMWD_B", recordInfoList_tb);
//            for (RecordModifyResult re : result) {
//                System.out.println("SG_DEV_TRANSFMWD_B===================" + re.getErr_msg() + " " + re.isIs_success());
//            }
//        } catch (NRDataAccessException e) {
//            e.printStackTrace();
//        }

        // 更新G表
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<RecordColumnInfo> transformerRecordList_g = recordColumnInfoMap.get("ZJ_DEV_TRANSFORMER_G");
        transformerRecordList_g.add(idColumnInfo);
        RecordInfo recordInfo_g = new RecordInfo();
        recordInfo_g.setRecordColumnInfo(transformerRecordList_g);
        recordInfo_g.setRegion_id("330000");
        List<RecordInfo> recordInfoList_g = new ArrayList<>();
        recordInfoList_g.add(recordInfo_g);
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = NRDBAccessManage.NRDBAccess().UpdateRecord("ZJ_DEV_TRANSFORMER_G", recordInfoList_g);
            for (RecordModifyResult re : result) {
                System.out.println("ZJ_DEV_TRANSFORMER_G===================" + re.getErr_msg() + " " + re.isIs_success());
                if (!re.isIs_success()) {
                    resMap.put(false, "ZJ_DEV_TRANSFORMER_G表更新失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        resMap.put(true, insertTransformerId);
        return resMap;
    }


    public Map<Boolean, String> deleteTransformer(String projectId, String userName, String userId, String transformerId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<Boolean, String> resMap = new HashMap<>();
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(transformerId);
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
            result = NRDBAccessManage.NRDBAccess().DeleteRecord("SG_DEV_PWRTRANSFM_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resMap.put(re.isIs_success(), re.getErr_msg());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resMap;
    }

}
