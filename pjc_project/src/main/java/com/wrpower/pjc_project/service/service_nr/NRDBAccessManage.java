package com.wrpower.pjc_project.service.service_nr;

import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.DefineHeader;
import com.nari.cloud.dbaccess.model.UserInfo;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;

public class NRDBAccessManage {

    public static NRDBAccess nrdbAccess = new NRDBAccessImpl();
    static {
        nrdbAccess.setProxyIp("10.33.3.31");
    }

    public static NRDBAccess NRDBAccess()
    {
        return nrdbAccess;
    }

    public static void clearProjectId()
    {
        nrdbAccess.clearProjectId();
    }

    public static void setProjectId(String projectId, String userName, String userId)
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(userId);
        userInfo.setUser_name(userName);
        nrdbAccess.setProjectId( DefineHeader.FUTURE_PROJECT_ID, projectId, userInfo);
    }

}
