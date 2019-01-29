package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.tool.NRDataAccessException;

import java.util.*;

public class OwnerManage {

    static {
        NRDBAccessManage.NRDBAccess().setProxyIp("10.33.3.31");
    }
    public OwnerManage() {
    }

    //获取OwnerMap < code,name>
    static Map<String,String> getOwnerMap()
    {
        Map<String,String> ownerMap = new HashMap<>();
        String sql="select id, name_abbreviation from SG_ORG_DCC_B where parent_id= '0021330000' and org_type = '1004'";
        try {
            List<Map<String, Object>> map = NRDBAccessManage.NRDBAccess().queryForList(sql,null);
            for (Map<String, Object> stringObjectMap : map) {
                String idStr = stringObjectMap.get("id").toString();
                idStr = idStr.substring(idStr.length() - 6, idStr.length() - 1);//取后6位
                String name_abbreviation = stringObjectMap.get("name_abbreviation").toString();
                name_abbreviation = name_abbreviation.substring(0,4);
                ownerMap.put(idStr, name_abbreviation);
            }
        }catch (NRDataAccessException e){
            System.out.println("getOwnerMap发生错误:" + e.getMessage());
            ownerMap.put("error","getOwnerMap发生错误:" + e.getMessage());
        }
        return ownerMap;
    }


}
