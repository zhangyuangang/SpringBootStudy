package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;

import java.util.*;

public class OwnerManage {

    static private NRDBAccess nrdbAccess = new NRDBAccessImpl();
    {
        nrdbAccess.setProxyIp("10.33.3.31");
    }
    public OwnerManage() {
    }

    //获取OwnerMap
    static Map<String,String> getOwnerMap()
    {
        Map<String,String> ownerMap = new HashMap<>();
        String sql="select id, name_abbreviation from SG_ORG_DCC_B where parent_id= '0021330000' and org_type = '1004'";
        try {
            List<Map<String, Object>> map = nrdbAccess.queryForList(sql,null);
            for (int i = 0; i < map.size(); i++) {
                String idStr = map.get(i).get("id").toString();
                idStr = idStr.substring( idStr.length()-6,idStr.length()-1 );//取后6位
                String name_abbreviation = map.get(i).get("name_abbreviation").toString();
                ownerMap.put(name_abbreviation,idStr);
            }
        }catch (NRDataAccessException e){
            System.out.println("getOwnerMap发生错误:" + e.getMessage());
            ownerMap.put("error","getOwnerMap发生错误:" + e.getMessage());
        }
        return ownerMap;
    }
}
