package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.nari.cloud.fidbaccess.model.MgProjectStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BasicManage {

    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();
    private static List<String> DkyTableList = new ArrayList<>();

    static {
        m_nrdbAccess.setProxyIp("10.33.3.31");
        DkyTableList.add("SG_DEV_ACLINE_B");
        DkyTableList.add("SG_DEV_BREAKER_B");
        DkyTableList.add("SG_DEV_BUSBAR_B");
        DkyTableList.add("SG_DEV_SHUNTREACTOR_B");
        DkyTableList.add("SG_DEV_SHUNTCAPACITOR_B");
        DkyTableList.add("SG_DEV_GENERATOR_B");
        DkyTableList.add("SG_DEV_SERIESCAPACITOR_B");
        DkyTableList.add("SG_CON_PLANT_B");
        DkyTableList.add("SG_CON_SUBSTATION_B");
        DkyTableList.add("SG_CON_CONVERSUBSTATION_B");
        DkyTableList.add("SG_DEV_PWRTRANSFM_B");
        DkyTableList.add("ZJ_DEV_LITTLEGEN_G");
        DkyTableList.add("ZJ_DEV_GROUNDBRANCH_G");
    }

    public static List<String> getDkyTableList() {
        return DkyTableList;
    }

    public static String join(List<String> strList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strList) {
            str = "'" + str + "'";
            stringBuilder.append(str).append(",");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }


    private static Map<Integer, Map<String, String>> volMap = getVolTypeMap();
    // 电压等级code与名称对应map
    public static Map<String, String> volCodeToNameMap = volMap.get(1);
    public static Map<String, String> volNameToCodeMap = volMap.get(2);

    private static Map<Integer, Map<String, String>> ownerMap = getOwnerMap();
    //  owner code与名称对应map
    public static Map<String, String> ownerCodeToNameMap = ownerMap.get(1);
    public static Map<String, String> ownerNameToCodeMap = ownerMap.get(2);


    //工程状态code 和name的map
    public static Map<String, String> projectStateMap = getRunningStateMap();


    /**
     * @param ownerId String
     * @param userId  String
     * @return String
     */
    public static String getSTAMP(String ownerId, String userId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String s = ownerId + "_" + userId + "_" + date;
        return s;
    }

    /**
     * 获取ower id与中文名称的map 例如 < 330100,杭州地调 >
     *
     * @return Map<Integer                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                               Map                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                               String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String> >
     */
    public static Map<Integer, Map<String, String>> getOwnerMap() {
        Map<Integer, Map<String, String>> map = new HashMap<>();
        Map<String, String> codeToNameMap = new HashMap<>();
        Map<String, String> nameToCodeMap = new HashMap<>();
        String sql = "select owner, name_abbreviation from SG_ORG_DCC_B where parent_id= '0021330000' and org_type = '1004'";
        try {
            List<Map<String, Object>> resultMap = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : resultMap) {
                String code = stringObjectMap.get("owner").toString();
                String name = stringObjectMap.get("name_abbreviation").toString();
                nameToCodeMap.put(name, code);
                codeToNameMap.put(code, name);
            }
        } catch (NRDataAccessException e) {
            System.out.println("getOwnerMap发生错误:" + e.getMessage());
        }
        map.put(1, codeToNameMap);
        map.put(2, nameToCodeMap);
        return map;
    }

    /**
     * 返回厂站所有的id与name
     *
     * @return Map<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String>
     */
    public static Map<String, String> getSubstationMap() {
        Map<String, String> substationMap = new HashMap<>();
        String sql = "select id, name from sg_con_commonsubstation_b";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (int i = 0; i < map.size(); i++) {
                String id = map.get(i).get("id").toString();
                String name = map.get(i).get("name").toString();
                substationMap.put(id, name);
            }
        } catch (NRDataAccessException e) {
            System.out.println("getSubstationMap发生错误:" + e.getMessage());
            substationMap.put("error", "getSubstationMap发生错误:" + e.getMessage());
        }
        return substationMap;
    }

    /**
     * @return 返回厂站类型code对应的名称键值对 Map<String, String>
     */
    public static Map<String, String> getSubTypeMap() {
        Map<String, String> subTypeMap = new HashMap<>();
        String sql = "select distinct code, name from sg_dic_plantstationtype";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String code = stringObjectMap.get("code").toString();
                String name = stringObjectMap.get("name").toString();
                subTypeMap.put(code, name);
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
        return subTypeMap;
    }

    /**
     * 返回调度管辖权对应的code和name
     *
     * @return Map<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String>
     */
    public static Map<String, String> getDispatchMap() {
        Map<String, String> dispatchMap = new HashMap<>();
        String sql = "select id, name_abbreviation from SG_ORG_DCC_B where parent_id= '0021330000' and org_type = '1004'";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : map) {
                String id = stringObjectMap.get("id").toString();
                String name_abbreviation = stringObjectMap.get("name_abbreviation").toString();
                dispatchMap.put(name_abbreviation, id);
            }
        } catch (NRDataAccessException e) {
            System.out.println("getDispatchMap发生错误:" + e.getMessage());
            dispatchMap.put("error", "getDispatchMap发生错误:" + e.getMessage());
        }
        return dispatchMap;
    }

    /**
     * 获取电压等级 code 和name 的map
     *
     * @return Map<Integer   , Map   <   String   ,   String>>
     */
    public static Map<Integer, Map<String, String>> getVolTypeMap() {
        Map<Integer, Map<String, String>> map = new HashMap<>();
        Map<String, String> nameToCodeMap = new HashMap<>();
        Map<String, String> codeToNameMap = new HashMap<>();
        String sql = "select code, name from sg_dic_voltagetype";
        try {
            List<Map<String, Object>> resultMap = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : resultMap) {
                String code = stringObjectMap.get("code").toString();
                String name = stringObjectMap.get("name").toString();
                name = name.replace("kV", "");
                nameToCodeMap.put(name, code);
                codeToNameMap.put(code, name);
            }
        } catch (NRDataAccessException e) {
            System.out.println("getVolTypeMap发生错误:" + e.getMessage());
        }
        map.put(1, codeToNameMap);
        map.put(2, nameToCodeMap);
        return map;
    }

    public static Map<String, String> getVolAndTypeMap() {
        Map<String, String> map = new HashMap<>();
        String sql = "select code, name from sg_dic_voltagetype";
        try {
            List<Map<String, Object>> resultMap = m_nrdbAccess.queryForList(sql, null);
            for (Map<String, Object> stringObjectMap : resultMap) {
                String code = stringObjectMap.get("code").toString();
                String name = stringObjectMap.get("name").toString();
                map.put(code, name);
            }
        } catch (NRDataAccessException e) {
            System.out.println("getVolTypeMap发生错误:" + e.getMessage());
        }
        return map;
    }

    /**
     * 通过中文名称 如 发电厂 返回对应的厂站类型code
     *
     * @param name String
     * @return String
     */
    public static String getSubTypeCodeByName(String name) {
        if ("发电厂".equals(name))
            return "1001";
        Map<String, String> subTypeMap = getSubTypeMap();
        for (Map.Entry<String, String> entry : subTypeMap.entrySet()) {
            if (name.equals(entry.getValue()))
                return entry.getKey();
        }
        return "";
    }

    /**
     * 通过厂站类型code，返回厂站类型中文名称
     *
     * @param code String
     * @return String
     */
    static public String getSubTypeNameByCode(String code) {
        Map<String, String> subTypeMap = getSubTypeMap();
        String name = subTypeMap.get(code);
        return name;
    }

    /**
     * 通过 厂站类型 code返回 类型 （1 、2 、3 ）
     *
     * @param code String
     * @return String
     */
    static public int getSubTypeByCode(String code) {
        if ("1001".equals(code)) {
            return 1;
        } else if (code.startsWith("2") || code.isEmpty())
            return 2;
        else if (code.startsWith("3"))
            return 3;
        else
            return 2;
    }

    static int projectStatusCN2Int(String statusName) {
        if ("编辑中".equals(statusName))
            return MgProjectStatus.MG_PROJECT_EDIT;
        else if ("待审核".equals(statusName))
            return MgProjectStatus.MG_PROJECT_AUDIT;
        else if ("已审核".equals(statusName))
            return MgProjectStatus.MG_PROJECT_AUDITED;
        return MgProjectStatus.MG_PROJECT_EDIT;
    }

    static String projectStatusInt2CN(int status) {
        if (MgProjectStatus.MG_PROJECT_EDIT == status)
            return "编辑中";
        else if (MgProjectStatus.MG_PROJECT_AUDIT == status)
            return "待审核";
        else if (MgProjectStatus.MG_PROJECT_AUDITED == status)
            return "已审核";
        return "编辑中";
    }

    /**
     * 获取工程状态 map < 规划 、在建、在运、检修、退运、报废 >
     *
     * @return Map<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String>
     */
    public static Map<String, String> getRunningStateMap() {
        Map<String, String> runningStateMap = new HashMap<>();
        String sql = "select code, name from sg_dic_state";
        try {
            List<Map<String, Object>> map = m_nrdbAccess.queryForList(sql, null);
            for (int i = 0; i < map.size(); i++) {
                String code = map.get(i).get("code").toString();
                String name = map.get(i).get("name").toString();
                runningStateMap.put(name, code);
            }
        } catch (NRDataAccessException e) {
            System.out.println("getRunningStateMap发生错误:" + e.getMessage());
            runningStateMap.put("error", "getRunningStateMap发生错误:" + e.getMessage());
        }
        return runningStateMap;
    }


    /**
     * 前端设备设备状态 只有 待投运和投运  调控云中是 规划 、在建、在运、检修、退运、报废
     *
     * @param stateCode
     * @return
     */
    public static String devStateCodeToString(String stateCode) {
        if( stateCode == null )
            return "";
        if (stateCode.equals("1003"))
            return "投运";
        else
            return "待投运";
    }

    /**
     * 前端设备设备状态 只有 待投运和投运  调控云中是 规划 、在建、在运、检修、退运、报废
     *
     * @param stateStr
     * @return
     */
    public static String devStateStringToCode(String stateStr) {
        if( stateStr == null )
            return "";
        if (stateStr.equals("待投运"))
            return "1001";
        else
            return "1003";
    }


    /**
     * @param str
     * @return Long
     */
    public static Long getTime(String str) {
        Date date = new Date();
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDT = new Date();
        try {
            startDT = myFormatter.parse("1970-01-01");
            date = myFormatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (date.getTime() - startDT.getTime()) / 1000;
    }

    /**
     * String yyyy-mm-dd  ===>  yyyy-mm-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static String shortDateStrToLongDateStr(String dateStr) {
        String longDate = "";
        SimpleDateFormat shortFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat longFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = shortFormatter.parse(dateStr);
            longDate = longFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longDate;
    }

    /**
     * String   yyyy-mm-dd HH:mm:ss   ===>   yyyy-mm-dd
     *
     * @param dateStr String
     * @return String
     */
    public static String longDateStrToShortDateStr(String dateStr) {
        String shortDate = "";
        SimpleDateFormat shortFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat longFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = longFormatter.parse(dateStr);
            shortDate = shortFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return shortDate;
    }


    /**
     * "2018-12-04" => Date
     *
     * @param time String
     * @return Date
     */
    public static Date shortDataStrToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            System.out.println("日期转换发生错误:" + e.getMessage());
        }
        return date;
    }


    /**
     * Date => "2018-12-04"
     *
     * @param date Date
     * @return String
     */
    public static String dataToShortDataStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * 1441026486000  => yyyy-MM-dd
     *
     * @param longStr long
     * @return String
     */
    public static String longToDataStr(String longStr) {
        if( longStr == null )
            return "";
        long mseconds = Long.parseLong(longStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(mseconds * 1000);
        return sdf.format(date);
    }


    public static String tranTypeCodeToName(String code) {
        switch (code) {
            case "3001":
                return "主变";
            case "1001":
                return "联变";
            case "2001":
                return "升压变";
            default:
                return "";
        }
    }

    public static String tranTypeNameToCode(String name) {
        switch (name) {
            case "主变":
                return "3001";
            case "联变":
                return "1001";
            case "升压变":
                return "2001";
            default:
                return "";
        }
    }

}
