package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.core.NRBeanRowMapper;
import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.*;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.entity.Acline;
import com.wrpower.pjc_project.service.ReadConfigFile;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.*;

public class AclineManage {

    //    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    // 这个用来记录 线路类对象的属性，对应调控云中的表和字段名称
    private Map<String, Pair<String, String>> aclineAttToDkyTableMap = ReadConfigFile.getAttToDkyTableMap("acline");
    // 线路类对象的属性，对应调控云中该字段类型
    private Map<String, Integer> aclineAttAndTypeMap = ReadConfigFile.getAttAndTypeMap("acline");
    private Map<String, String> aclineNameAndIdMap = selectAclineNameAndIdMap();

//    static {
//        m_nrdbAccess.setProxyIp("10.33.3.31");
//    }

    public static Map<String, String> selectAclineNameAndIdMap() {
        Map<String, String> aclineNameAndIdMap = new HashMap<>();
        String sql = " select id, name from SG_DEV_ACLINE_B ";
        try {
            List<Map<String, Object>> map = NRDBAccessManage.NRDBAccess().queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name = stringObjectMap.get("name").toString();
                aclineNameAndIdMap.put(name, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("aclineNameAndIdMap:" + e.getMessage());
        }
        return aclineNameAndIdMap;
    }

    /**
     * 根据uuid查询线路信息
     *
     * @param uuid String
     * @return Acline
     */
    public Acline selectAclineInfoByUuid(String projectId, String userName, String userId, String uuid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        Acline acline = new Acline();
        if (uuid == null)
            return acline;
        String sqlStr = "select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.start_st_id frombusnameuuid, " +
                "t1.end_st_id  tobusnameuuid, " +
                "t1.running_state servicestatus, " +
                "t1.length design_length, " +
                "t1.zj_sfsjllx iszonelink, " +
                "t2.imaxshort_10 current_emergency_10, " +
                "t2.imaxshort_20 current_emergency_20, " +
                "t2.imaxshort_30 current_emergency_30, " +
                "t2.imaxshort_40 current_emergency_40, " +
                "t2.imaxlong_10 current_rated_10, " +
                "t2.imaxlong_20 current_rated_20, " +
                "t2.imaxlong_30 current_rated_30, " +
                "t2.imaxlong_40 current_rated_40, " +
                "t2.zj_zxdn_pu design_bch, " +
                "t2.zj_lxdn_pu design_bch0, " +
                "t2.zj_zxdz_pu design_r, " +
                "t2.zj_lxdz_pu design_ro, " +
                "t2.zj_zxdk_pu design_x," +
                "t2.zj_lxdk_pu design_x0 " +
                "from SG_DEV_ACLINE_B t1, SG_DEV_ACLINE_P t2 " +
                "where t1.id = t2.id " +
                "and t1.id = '" + uuid + "' ";

        // G表数据
//        String sqlStr = "select t1.id uuid, " +
//                "t1.name name, " +
//                "t1.expiry_date outservicedate, " +
//                "t1.operate_date inservicedate, " +
//                "t1.voltage_type voltagelevel, " +
//                "t1.start_st_id frombusnameuuid, " +
//                "t1.end_st_id  tobusnameuuid, " +
//                "t1.running_state servicestatus, " +
//                "t1.length design_length, " +
//                "t1.zj_sfsjllx iszonelink, " +
//                "t2.imaxshort_10 current_emergency_10, " +
//                "t2.imaxshort_20 current_emergency_20, " +
//                "t2.imaxshort_30 current_emergency_30, " +
//                "t2.imaxshort_40 current_emergency_40, " +
//                "t2.imaxlong_10 current_rated_10, " +
//                "t2.imaxlong_20 current_rated_20, " +
//                "t2.imaxlong_30 current_rated_30, " +
//                "t2.imaxlong_40 current_rated_40, " +
//                "t2.zj_zxdn_pu design_bch, " +
//                "t2.zj_lxdn_pu design_bch0, " +
//                "t2.zj_zxdz_pu design_r, " +
//                "t2.zj_lxdz_pu design_ro, " +
//                "t2.zj_zxdk_pu design_x, " +
//                "t2.zj_lxdk_pu design_x0, " +
//                "t3.*  " +
////                "t3.dispatcheruuid dispatcheruuid, " +
////                "t3.bpaid bpaid, " +
////                "t3.branchnum branchnum, " +
////                "t3.design_bch design_bch, " +
////                "t3.design_bch0 design_bch0, " +
////                "t3.design_bch0_to design_bch0_to, " +
////                "t3.design_bch_to design_bch_to, " +
////                "t3.design_emergencycurrent design_emergencycurrent, " +
////                "t3.design_r design_r, " +
////                "t3.design_ro design_ro, " +
////                "t3.design_ratedcurrent design_ratedcurrent, " +
////                "t3.design_x design_x, " +
////                "t3.design_x0 design_x0, " +
////                "t3.frwarnningcurrent frwarnningcurrent, " +
////                "t3.groupuuid groupuuid, " +
////                "t3.isdbc isdbc, " +
////                "t3.isecard isecard, " +
////                "t3.linestatus linestatus, " +
////                "t3.parameterkind parameterkind, " +
////                "t3.project_bch project_bch, " +
////                "t3.project_bch0 project_bch0, " +
////                "t3.project_bch0_to project_bch0_to, " +
////                "t3.project_bch_to project_bch_to, " +
////                "t3.project_emergencycurrent project_emergencycurrent, " +
////                "t3.project_length project_length, " +
////                "t3.project_r project_r, " +
////                "t3.project_r0 project_r0, " +
////                "t3.project_ratedcurrent project_ratedcurrent, " +
////                "t3.project_x project_x, " +
////                "t3.project_x0 project_x0, " +
////                "t3.projectuuid projectuuid, " +
////                "t3.design_x design_x, " +
////                "t3.real_bch real_bch, " +
////                "t3.real_bch0 real_bch0, " +
////                "t3.real_bch0_to real_bch0_to, " +
////                "t3.real_bch_to real_bch_to, " +
////                "t3.real_emergencycurrent real_emergencycurrent, " +
////                "t3.real_length real_length, " +
////                "t3.real_phcapacity0_to real_phcapacity0_to, " +
////                "t3.real_phcapacity_to real_phcapacity_to, " +
////                "t3.real_r real_r, " +
////                "t3.real_r0 real_r0, " +
////                "t3.real_ratedcurrent real_ratedcurrent, " +
////                "t3.real_x real_x, " +
////                "t3.real_x0 real_x0, " +
////                "t3.summer_emergency_current summer_emergency_current, " +
////                "t3.summer_rate_current summer_rate_current, " +
////                "t3.towarnningcurrent towarnningcurrent, " +
////                "t3.updatedate updatedate, " +
////                "t3.usedname usedname, " +
////                "t3.versionuuid versionuuid, " +
////                "t3.winter_emergency_current winter_emergency_current, " +
////                "t3.winter_rate_current winter_rate_current, " +
////                "t3.ftpattachmentnumbers ftpattachmentnumbers " +
//                "from SG_DEV_ACLINE_B t1, SG_DEV_ACLINE_P t2, ZJ_DEV_ACLINE_G t3 " +
//                "where t3.id = t1.id " +
//                "and t3.id = t2.id " +
//                "and t3.id = '" + uuid + "' ";
        NRBeanRowMapper<Acline> org_acline = new NRBeanRowMapper<>(Acline.class);
        System.out.println("sqlStr:" + sqlStr);

        try {
            acline = NRDBAccessManage.NRDBAccess().queryForObject(sqlStr, null, org_acline);
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        acline.setInservicedate(BasicManage.longToDataStr(acline.getInservicedate()));
        acline.setOutservicedate(BasicManage.longToDataStr(acline.getOutservicedate()));
        acline.setServicestatus(BasicManage.devStateCodeToString(acline.getServicestatus()));

        System.out.println("==========" + "id:" + acline.getUuid() + "  name:" + acline.getName() + "time:" + acline.getInservicedate() + " " + acline.getOutservicedate());


        return acline;
    }

    /**
     * 通过工程id，查询线路信息
     * 查询返回所有线路实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectAclineList(String projectId, String userName, String userId) {
        List<Object> aclineList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();

        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.dispatch_org_id dispatcheruuid, " +
                "t1.start_st_id frombusnameuuid, " +
                "t1.end_st_id  tobusnameuuid, " +
                "t1.running_state servicestatus, " +
                "t1.length design_length, " +
                "t1.zj_sfsjllx iszonelink, " +
                "t2.imaxshort_10 current_emergency_10, " +
                "t2.imaxshort_20 current_emergency_20, " +
                "t2.imaxshort_30 current_emergency_30, " +
                "t2.imaxshort_40 current_emergency_40, " +
                "t2.imaxlong_10 current_rated_10, " +
                "t2.imaxlong_20 current_rated_20, " +
                "t2.imaxlong_30 current_rated_30, " +
                "t2.imaxlong_40 current_rated_40, " +
                "t2.zj_zxdn_pu design_bch, " +
                "t2.zj_lxdn_pu design_bch0, " +
                "t2.zj_zxdz_pu design_r, " +
                "t2.zj_lxdz_pu design_ro, " +
                "t2.zj_zxdk_pu design_x," +
                "t2.zj_lxdk_pu design_x0 " +
                "from SG_DEV_ACLINE_B t1, SG_DEV_ACLINE_P t2 " +
                "where t1.id = t2.id ");

        // G表数据

//        sqlList.add("select b.id uuid, " +
//                "b.name name, " +
//                "b.expiry_date outservicedate, " +
//                "b.operate_date inservicedate, " +
//                "b.voltage_type voltagelevel, " +
//                "b.start_st_id frombusnameuuid, " +
//                "b.end_st_id  tobusnameuuid, " +
//                "b.running_state servicestatus, " +
//                "b.length design_length, " +
//                "b.zj_sfsjllx iszonelink, " +
//                "t2.imaxshort_10 current_emergency_10, " +
//                "t2.imaxshort_20 current_emergency_20, " +
//                "t2.imaxshort_30 current_emergency_30, " +
//                "t2.imaxshort_40 current_emergency_40, " +
//                "t2.imaxlong_10 current_rated_10, " +
//                "t2.imaxlong_20 current_rated_20, " +
//                "t2.imaxlong_30 current_rated_30, " +
//                "t2.imaxlong_40 current_rated_40, " +
//                "t2.zj_zxdn_pu design_bch, " +
//                "t2.zj_lxdn_pu design_bch0, " +
//                "t2.zj_zxdz_pu design_r, " +
//                "t2.zj_lxdz_pu design_ro, " +
//                "t2.zj_zxdk_pu design_x, " +
//                "t2.zj_lxdk_pu design_x0, " +
//                "g.*  " +
////                "g.dispatcheruuid dispatcheruuid, " +
////                "g.bpaid bpaid, " +
////                "g.branchnum branchnum, " +
////                "g.design_bch design_bch, " +
////                "g.design_bch0 design_bch0, " +
////                "g.design_bch0_to design_bch0_to, " +
////                "g.design_bch_to design_bch_to, " +
////                "g.design_emergencycurrent design_emergencycurrent, " +
////                "g.design_r design_r, " +
////                "g.design_ro design_ro, " +
////                "g.design_ratedcurrent design_ratedcurrent, " +
////                "g.design_x design_x, " +
////                "g.design_x0 design_x0, " +
////                "g.frwarnningcurrent frwarnningcurrent, " +
////                "g.groupuuid groupuuid, " +
////                "g.isdbc isdbc, " +
////                "g.isecard isecard, " +
////                "g.linestatus linestatus, " +
////                "g.parameterkind parameterkind, " +
////                "g.project_bch project_bch, " +
////                "g.project_bch0 project_bch0, " +
////                "g.project_bch0_to project_bch0_to, " +
////                "g.project_bch_to project_bch_to, " +
////                "g.project_emergencycurrent project_emergencycurrent, " +
////                "g.project_length project_length, " +
////                "g.project_r project_r, " +
////                "g.project_r0 project_r0, " +
////                "g.project_ratedcurrent project_ratedcurrent, " +
////                "g.project_x project_x, " +
////                "g.project_x0 project_x0, " +
////                "g.projectuuid projectuuid, " +
////                "g.design_x design_x, " +
////                "g.real_bch real_bch, " +
////                "g.real_bch0 real_bch0, " +
////                "g.real_bch0_to real_bch0_to, " +
////                "g.real_bch_to real_bch_to, " +
////                "g.real_emergencycurrent real_emergencycurrent, " +
////                "g.real_length real_length, " +
////                "g.real_phcapacity0_to real_phcapacity0_to, " +
////                "g.real_phcapacity_to real_phcapacity_to, " +
////                "g.real_r real_r, " +
////                "g.real_r0 real_r0, " +
////                "g.real_ratedcurrent real_ratedcurrent, " +
////                "g.real_x real_x, " +
////                "g.real_x0 real_x0, " +
////                "g.summer_emergency_current summer_emergency_current, " +
////                "g.summer_rate_current summer_rate_current, " +
////                "g.towarnningcurrent towarnningcurrent, " +
////                "g.updatedate updatedate, " +
////                "g.usedname usedname, " +
////                "g.versionuuid versionuuid, " +
////                "g.winter_emergency_current winter_emergency_current, " +
////                "g.winter_rate_current winter_rate_current, " +
////                "g.ftpattachmentnumbers ftpattachmentnumbers " +
//                "from SG_DEV_ACLINE_B b, SG_DEV_ACLINE_P p ,ZJ_DEV_ACLINE_G g" +
//                "where g.id = b.id " +
//                "and g.id = t2.id ");
        NRBeanRowMapper<Acline> org_acline = new NRBeanRowMapper<>(Acline.class);
        System.out.println("sqlStr:" + sqlList.get(0));

        try {
            for (String sqlStr : sqlList) {
                aclineList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_acline));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : aclineList) {
            Acline acline = (Acline) object;
            acline.setInservicedate(BasicManage.longToDataStr(acline.getInservicedate()));
            acline.setOutservicedate(BasicManage.longToDataStr(acline.getOutservicedate()));
            acline.setServicestatus(BasicManage.devStateCodeToString(acline.getServicestatus()));

            System.out.println("==========" + "id:" + acline.getUuid() + "  name:" + acline.getName() + "time:" + acline.getInservicedate() + " " + acline.getOutservicedate());
        }
        return aclineList;
    }


    /**
     * 通过工程id，查询线路信息
     * 查询返回所有线路实体对象结构的集合
     * 参数：需要所属工程ID，用户信息
     *
     * @param projectId
     * @param userName
     * @param userId
     * @return
     */
    public List<Object> selectAclineListByIdList(String projectId, String userName, String userId, List<String> keyIdList) {
        List<Object> aclineList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        if (keyIdList.size() == 0)
            return aclineList;
        String idListStr = BasicManage.join(keyIdList);
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.start_st_id frombusnameuuid, " +
                "t1.end_st_id  tobusnameuuid, " +
                "t1.running_state servicestatus, " +
                "t1.length design_length, " +
                "t1.zj_sfsjllx iszonelink, " +
                "t2.imaxshort_10 current_emergency_10, " +
                "t2.imaxshort_20 current_emergency_20, " +
                "t2.imaxshort_30 current_emergency_30, " +
                "t2.imaxshort_40 current_emergency_40, " +
                "t2.imaxlong_10 current_rated_10, " +
                "t2.imaxlong_20 current_rated_20, " +
                "t2.imaxlong_30 current_rated_30, " +
                "t2.imaxlong_40 current_rated_40, " +
                "t2.zj_zxdn_pu design_bch, " +
                "t2.zj_lxdn_pu design_bch0, " +
                "t2.zj_zxdz_pu design_r, " +
                "t2.zj_lxdz_pu design_ro, " +
                "t2.zj_zxdk_pu design_x, " +
                "t2.zj_lxdk_pu design_x0 " +
                "from SG_DEV_ACLINE_B t1, SG_DEV_ACLINE_P t2 " +
                "where t1.id = t2.id " +
                "and t1.id in (" + idListStr + ") ");

        // 查询包含G表数据
//        sqlList.add("select b.id uuid, " +
//                "b.name name, " +
//                "b.expiry_date outservicedate, " +
//                "b.operate_date inservicedate, " +
//                "b.voltage_type voltagelevel, " +
//                "b.start_st_id frombusnameuuid, " +
//                "b.end_st_id  tobusnameuuid, " +
//                "b.running_state servicestatus, " +
//                "b.length design_length, " +
//                "b.zj_sfsjllx iszonelink, " +
//                "t2.imaxshort_10 current_emergency_10, " +
//                "t2.imaxshort_20 current_emergency_20, " +
//                "t2.imaxshort_30 current_emergency_30, " +
//                "t2.imaxshort_40 current_emergency_40, " +
//                "t2.imaxlong_10 current_rated_10, " +
//                "t2.imaxlong_20 current_rated_20, " +
//                "t2.imaxlong_30 current_rated_30, " +
//                "t2.imaxlong_40 current_rated_40, " +
//                "t2.zj_zxdn_pu design_bch, " +
//                "t2.zj_lxdn_pu design_bch0, " +
//                "t2.zj_zxdz_pu design_r, " +
//                "t2.zj_lxdz_pu design_ro, " +
//                "t2.zj_zxdk_pu design_x, " +
//                "t2.zj_lxdk_pu design_x0, " +
//                "g.*  " +
////                "g.dispatcheruuid dispatcheruuid, " +
////                "g.bpaid bpaid, " +
////                "g.branchnum branchnum, " +
////                "g.design_bch design_bch, " +
////                "g.design_bch0 design_bch0, " +
////                "g.design_bch0_to design_bch0_to, " +
////                "g.design_bch_to design_bch_to, " +
////                "g.design_emergencycurrent design_emergencycurrent, " +
////                "g.design_r design_r, " +
////                "g.design_ro design_ro, " +
////                "g.design_ratedcurrent design_ratedcurrent, " +
////                "g.design_x design_x, " +
////                "g.design_x0 design_x0, " +
////                "g.frwarnningcurrent frwarnningcurrent, " +
////                "g.groupuuid groupuuid, " +
////                "g.isdbc isdbc, " +
////                "g.isecard isecard, " +
////                "g.linestatus linestatus, " +
////                "g.parameterkind parameterkind, " +
////                "g.project_bch project_bch, " +
////                "g.project_bch0 project_bch0, " +
////                "g.project_bch0_to project_bch0_to, " +
////                "g.project_bch_to project_bch_to, " +
////                "g.project_emergencycurrent project_emergencycurrent, " +
////                "g.project_length project_length, " +
////                "g.project_r project_r, " +
////                "g.project_r0 project_r0, " +
////                "g.project_ratedcurrent project_ratedcurrent, " +
////                "g.project_x project_x, " +
////                "g.project_x0 project_x0, " +
////                "g.projectuuid projectuuid, " +
////                "g.design_x design_x, " +
////                "g.real_bch real_bch, " +
////                "g.real_bch0 real_bch0, " +
////                "g.real_bch0_to real_bch0_to, " +
////                "g.real_bch_to real_bch_to, " +
////                "g.real_emergencycurrent real_emergencycurrent, " +
////                "g.real_length real_length, " +
////                "g.real_phcapacity0_to real_phcapacity0_to, " +
////                "g.real_phcapacity_to real_phcapacity_to, " +
////                "g.real_r real_r, " +
////                "g.real_r0 real_r0, " +
////                "g.real_ratedcurrent real_ratedcurrent, " +
////                "g.real_x real_x, " +
////                "g.real_x0 real_x0, " +
////                "g.summer_emergency_current summer_emergency_current, " +
////                "g.summer_rate_current summer_rate_current, " +
////                "g.towarnningcurrent towarnningcurrent, " +
////                "g.updatedate updatedate, " +
////                "g.usedname usedname, " +
////                "g.versionuuid versionuuid, " +
////                "g.winter_emergency_current winter_emergency_current, " +
////                "g.winter_rate_current winter_rate_current, " +
////                "g.ftpattachmentnumbers ftpattachmentnumbers " +
//                "from SG_DEV_ACLINE_B b, SG_DEV_ACLINE_P p, ZJ_DEV_ACLINE_G g " +
//                "where g.id = b.id " +
//                "and g.id = t2.id " +
//                "and g.id in (" + idListStr + ") ");
        NRBeanRowMapper<Acline> org_acline = new NRBeanRowMapper<>(Acline.class);
        System.out.println("sqlStr:" + sqlList.get(0));

        try {
            for (String sqlStr : sqlList) {
                aclineList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_acline));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : aclineList) {
            Acline acline = (Acline) object;
            acline.setInservicedate(BasicManage.longToDataStr(acline.getInservicedate()));
            acline.setOutservicedate(BasicManage.longToDataStr(acline.getOutservicedate()));
            acline.setServicestatus(BasicManage.devStateCodeToString(acline.getServicestatus()));
            System.out.println("==========" + "id:" + acline.getUuid() + "  name:" + acline.getName() + "time:" + acline.getInservicedate() + " " + acline.getOutservicedate());

        }
        return aclineList;
    }


    public List<Object> selectAclineListByIdListAndDate(String projectId, String userName, String userId, List<String> keyIdList, String dateMin, String dateMax) {
        List<Object> aclineList = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        if (keyIdList.size() == 0)
            return aclineList;
        String idListStr = BasicManage.join(keyIdList);
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);

        List<String> sqlList = new ArrayList<>();
        sqlList.add("select t1.id uuid, " +
                "t1.name name, " +
                "t1.expiry_date outservicedate, " +
                "t1.operate_date inservicedate, " +
                "t1.voltage_type voltagelevel, " +
                "t1.start_st_id frombusnameuuid, " +
                "t1.end_st_id  tobusnameuuid, " +
                "t1.running_state servicestatus, " +
                "t1.length design_length, " +
                "t1.zj_sfsjllx iszonelink, " +
                "t2.imaxshort_10 current_emergency_10, " +
                "t2.imaxshort_20 current_emergency_20, " +
                "t2.imaxshort_30 current_emergency_30, " +
                "t2.imaxshort_40 current_emergency_40, " +
                "t2.imaxlong_10 current_rated_10, " +
                "t2.imaxlong_20 current_rated_20, " +
                "t2.imaxlong_30 current_rated_30, " +
                "t2.imaxlong_40 current_rated_40, " +
                "t2.zj_zxdn_pu design_bch, " +
                "t2.zj_lxdn_pu design_bch0, " +
                "t2.zj_zxdz_pu design_r, " +
                "t2.zj_lxdz_pu design_ro, " +
                "t2.zj_zxdk_pu design_x, " +
                "t2.zj_lxdk_pu design_x0 " +
                "from SG_DEV_ACLINE_B t1, SG_DEV_ACLINE_P t2 " +
                "where t1.id = t2.id " +
                "and t1.operate_date between to_date('" + dateMin + "',\"yyyy-MM-DD\") " +
                "and to_date('" + dateMax + "',\"yyyy-MM-DD\") " +
                "and t1.id in (" + idListStr + ") ");

        // 查询包含G表数据
//        sqlList.add("select b.id uuid, " +
//                "b.name name, " +
//                "b.expiry_date outservicedate, " +
//                "b.operate_date inservicedate, " +
//                "b.voltage_type voltagelevel, " +
//                "b.start_st_id frombusnameuuid, " +
//                "b.end_st_id  tobusnameuuid, " +
//                "b.running_state servicestatus, " +
//                "b.length design_length, " +
//                "b.zj_sfsjllx iszonelink, " +
//                "t2.imaxshort_10 current_emergency_10, " +
//                "t2.imaxshort_20 current_emergency_20, " +
//                "t2.imaxshort_30 current_emergency_30, " +
//                "t2.imaxshort_40 current_emergency_40, " +
//                "t2.imaxlong_10 current_rated_10, " +
//                "t2.imaxlong_20 current_rated_20, " +
//                "t2.imaxlong_30 current_rated_30, " +
//                "t2.imaxlong_40 current_rated_40, " +
//                "t2.zj_zxdn_pu design_bch, " +
//                "t2.zj_lxdn_pu design_bch0, " +
//                "t2.zj_zxdz_pu design_r, " +
//                "t2.zj_lxdz_pu design_ro, " +
//                "t2.zj_zxdk_pu design_x, " +
//                "t2.zj_lxdk_pu design_x0, " +
//                "g.*  " +
////                "g.dispatcheruuid dispatcheruuid, " +
////                "g.bpaid bpaid, " +
////                "g.branchnum branchnum, " +
////                "g.design_bch design_bch, " +
////                "g.design_bch0 design_bch0, " +
////                "g.design_bch0_to design_bch0_to, " +
////                "g.design_bch_to design_bch_to, " +
////                "g.design_emergencycurrent design_emergencycurrent, " +
////                "g.design_r design_r, " +
////                "g.design_ro design_ro, " +
////                "g.design_ratedcurrent design_ratedcurrent, " +
////                "g.design_x design_x, " +
////                "g.design_x0 design_x0, " +
////                "g.frwarnningcurrent frwarnningcurrent, " +
////                "g.groupuuid groupuuid, " +
////                "g.isdbc isdbc, " +
////                "g.isecard isecard, " +
////                "g.linestatus linestatus, " +
////                "g.parameterkind parameterkind, " +
////                "g.project_bch project_bch, " +
////                "g.project_bch0 project_bch0, " +
////                "g.project_bch0_to project_bch0_to, " +
////                "g.project_bch_to project_bch_to, " +
////                "g.project_emergencycurrent project_emergencycurrent, " +
////                "g.project_length project_length, " +
////                "g.project_r project_r, " +
////                "g.project_r0 project_r0, " +
////                "g.project_ratedcurrent project_ratedcurrent, " +
////                "g.project_x project_x, " +
////                "g.project_x0 project_x0, " +
////                "g.projectuuid projectuuid, " +
////                "g.design_x design_x, " +
////                "g.real_bch real_bch, " +
////                "g.real_bch0 real_bch0, " +
////                "g.real_bch0_to real_bch0_to, " +
////                "g.real_bch_to real_bch_to, " +
////                "g.real_emergencycurrent real_emergencycurrent, " +
////                "g.real_length real_length, " +
////                "g.real_phcapacity0_to real_phcapacity0_to, " +
////                "g.real_phcapacity_to real_phcapacity_to, " +
////                "g.real_r real_r, " +
////                "g.real_r0 real_r0, " +
////                "g.real_ratedcurrent real_ratedcurrent, " +
////                "g.real_x real_x, " +
////                "g.real_x0 real_x0, " +
////                "g.summer_emergency_current summer_emergency_current, " +
////                "g.summer_rate_current summer_rate_current, " +
////                "g.towarnningcurrent towarnningcurrent, " +
////                "g.updatedate updatedate, " +
////                "g.usedname usedname, " +
////                "g.versionuuid versionuuid, " +
////                "g.winter_emergency_current winter_emergency_current, " +
////                "g.winter_rate_current winter_rate_current, " +
////                "g.ftpattachmentnumbers ftpattachmentnumbers " +
//                "from SG_DEV_ACLINE_B b, SG_DEV_ACLINE_P p, ZJ_DEV_ACLINE_G g " +
//                "where g.id = b.id " +
//                "and g.id = t2.id " +
//                "and g.id in (" + idListStr + ") ");
        NRBeanRowMapper<Acline> org_acline = new NRBeanRowMapper<>(Acline.class);
        System.out.println("sqlStr:" + sqlList.get(0));

        try {
            for (String sqlStr : sqlList) {
                aclineList.addAll(NRDBAccessManage.NRDBAccess().query(sqlStr, null, org_acline));
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        for (Object object : aclineList) {
            Acline acline = (Acline) object;
            acline.setInservicedate(BasicManage.longToDataStr(acline.getInservicedate()));
            acline.setOutservicedate(BasicManage.longToDataStr(acline.getOutservicedate()));
            acline.setServicestatus(BasicManage.devStateCodeToString(acline.getServicestatus()));
            System.out.println("==========" + "id:" + acline.getUuid() + "  name:" + acline.getName() + "time:" + acline.getInservicedate() + " " + acline.getOutservicedate());

        }
        return aclineList;
    }


    // 根据线路ID 查询该线路的首末端对应厂站的信息
    public List<Object> selectSubstationListByAclineId(String projectId, String userName, String userId, String devId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);
        //这里需要传一个工程Id
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<String> substationList = new ArrayList<>();
        String sql = "select start_st_id, end_st_id from SG_DEV_ACLINE_B ";
        try {
            List<Map<String, Object>> resultMap = NRDBAccessManage.NRDBAccess().queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : resultMap) {
                substationList.add( stringObjectMap.get("start_st_id").toString() );
                substationList.add( stringObjectMap.get("end_st_id").toString() );
            }
        } catch (NRDataAccessException e) {
            System.out.println("SG_DEV_ACLINE_B发生错误:" + e.getMessage());
        }

        SubstationManage substationManage = new SubstationManage();
        return substationManage.selectSubstationListByIdList(projectId,userName,userId,substationList);
    }


    /**
     * 更新线路信息
     * 参数：需要所属工程ID，用户信息, 设备更新的字段与对应值的键值对map，批量更新使用List
     *
     * @param projectId
     * @param userName
     * @param userId
     * @param changNameAndValueMap
     * @return
     */
    public Map<Boolean, String> updateAcline(String projectId, String userName, String userId, String aclineId, Map<String, String> changNameAndValueMap) {

        Map<Boolean, String> resMap = new HashMap<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<String, List<RecordInfo>> tableRecordInfoMap = new HashMap<>();
        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();
        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setColumn_value(aclineId);
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        for (Map.Entry<String, String> changNameAndValue : changNameAndValueMap.entrySet()) {
            String columnName = changNameAndValue.getKey();
            String columnValue = changNameAndValue.getValue();
            RecordColumnInfo columnInfo = new RecordColumnInfo();
            Pair<String, String> tableAndColumName = aclineAttToDkyTableMap.get(columnName);
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

            Integer type = aclineAttAndTypeMap.get(columnName);
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

    public Map<Boolean, String> insertAcline(String projectId, String userName, String userId, String orgId, Acline acline) {

        Map<Boolean, String> resMap = new HashMap<>();

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_name(userName);
        userInfo.setUser_id(userId);

        Map<String, List<RecordColumnInfo>> recordColumnInfoMap = new HashMap<>();

        // 线路重名 直接返回空的list
        if (aclineAttToDkyTableMap == null || aclineAttAndTypeMap == null) {
            resMap.put(false, "attrToDkyTable文件中acline表数据为空！");
            return resMap;
        }
        if (aclineNameAndIdMap.containsKey(acline.getName())) {
            resMap.put(false, "线路重名！");
            return resMap;
        }

        for (Field field : acline.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.toString();
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                String fieldValue = (String) field.get(acline);
                if (fieldValue == null)
                    continue;
                if (!aclineAttToDkyTableMap.containsKey(fieldName) || !aclineAttAndTypeMap.containsKey(fieldName)) {
                    continue;
                }
                Pair<String, String> pair = aclineAttToDkyTableMap.get(fieldName);
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
                    fieldValue = BasicManage.devStateStringToCode(fieldValue);
                }
                String dkyTableName = pair.getKey();
                String dkyColumnName = pair.getValue();
                int dkyColumnType = aclineAttAndTypeMap.get(fieldName);
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
        // 设置model
        RecordColumnInfo modelColumnInfo = new RecordColumnInfo();
        modelColumnInfo.setColumn_name("model");
        modelColumnInfo.setColumn_value("其他");
        modelColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        List<RecordColumnInfo> recordList_b = recordColumnInfoMap.get("SG_DEV_ACLINE_B");
        recordList_b.add(stampColumnInfo);
        recordList_b.add(dispatchColumnInfo);
        recordList_b.add(ownerColumnInfo);
        recordList_b.add(modelColumnInfo);
        RecordInfo recordInfo_b = new RecordInfo();
        recordInfo_b.setRecordColumnInfo(recordList_b);
        recordInfo_b.setRegion_id("330000");
        List<RecordInfo> recordInfoList_b = new ArrayList<>();
        recordInfoList_b.add(recordInfo_b);

        String insertAclineId = "";
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        List<RecordModifyResult> result;
        try {
            result = NRDBAccessManage.NRDBAccess().InsertRecord("SG_DEV_ACLINE_B", recordInfoList_b);
            for (RecordModifyResult re : result) {
                System.out.println("SG_DEV_ACLINE_B===================" + re.getErr_msg() + " " + re.isIs_success());
                if (!re.isIs_success()) {
                    resMap.put(false, "SG_DEV_ACLINE_B表插入失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
                List<String> rt = re.getKeyColumns();
                for (int j = 0; j < rt.size(); ++j) {
                    System.out.println("re.getKeyColumns():" + j + " " + rt.get(j));
                    insertAclineId = rt.get(j);
                    aclineNameAndIdMap.put(acline.getName(), insertAclineId);
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        // 如果插入失败，没有返回id，则直接返回错误
        if (insertAclineId.isEmpty()) {
            resMap.put(false, "SG_DEV_ACLINE_B表插入失败！错误原因：insertAclineId 为空！");
            return resMap;
        }

        RecordColumnInfo idColumnInfo = new RecordColumnInfo();
        idColumnInfo.setColumn_name("ID");
        idColumnInfo.setIs_key(true);
        idColumnInfo.setColumn_value(insertAclineId);
        idColumnInfo.setColumn_type(DefineHeader.JDBC_DATATYPE_STRING);

        // 更新P表
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<RecordColumnInfo> recordList_p = recordColumnInfoMap.get("SG_DEV_ACLINE_P");
        recordList_p.add(stampColumnInfo);
        recordList_p.add(idColumnInfo);
        RecordInfo recordInfo_p = new RecordInfo();
        recordInfo_p.setRecordColumnInfo(recordList_p);
        recordInfo_p.setRegion_id("330000");
        List<RecordInfo> recordInfoList_p = new ArrayList<>();
        recordInfoList_p.add(recordInfo_p);
        // 设置所属工程信息
//        m_nrdbAccess.clearProjectId();
//        m_nrdbAccess.setProjectId(DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
        try {
            result = NRDBAccessManage.NRDBAccess().UpdateRecord("SG_DEV_ACLINE_P", recordInfoList_p);
            for (RecordModifyResult re : result) {
                if (!re.isIs_success()) {
                    resMap.put(false, "SG_DEV_ACLINE_P表更新失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
                System.out.println("SG_DEV_ACLINE_P===================" + re.getErr_msg() + " " + re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }

        // 更新G表
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<RecordColumnInfo> recordList_g = recordColumnInfoMap.get("ZJ_DEV_ACLINE_G");
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
            result = NRDBAccessManage.NRDBAccess().UpdateRecord("ZJ_DEV_ACLINE_G", recordInfoList_g);
            for (RecordModifyResult re : result) {
                if (!re.isIs_success()) {
                    resMap.put(false, "ZJ_DEV_ACLINE_G表更新失败！错误原因：" + re.getErr_msg());
                    return resMap;
                }
                System.out.println("ZJ_DEV_ACLINE_G===================" + re.getErr_msg() + " " + re.isIs_success());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        resMap.put(true, insertAclineId);
        return resMap;
    }

    public Map<Boolean, String> deleteAcline(String projectId, String userName, String userId, String aclineId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);

        Map<Boolean, String> resMap = new HashMap<>();
        List<RecordInfo> recordInfoList = new ArrayList<>();

        List<RecordColumnInfo> recordColumnInfo = new ArrayList<>();
        RecordColumnInfo columnInfo = new RecordColumnInfo();
        columnInfo.setColumn_name("ID");
        columnInfo.setColumn_value(aclineId);
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
            result = NRDBAccessManage.NRDBAccess().DeleteRecord("SG_DEV_ACLINE_B", recordInfoList);
            for (RecordModifyResult re : result) {
                resMap.put(re.isIs_success(), re.getErr_msg());
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return resMap;
    }


}
