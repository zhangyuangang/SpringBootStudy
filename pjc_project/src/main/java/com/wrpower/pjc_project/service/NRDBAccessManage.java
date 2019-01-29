package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;

public class NRDBAccessManage {

    public static NRDBAccess nrdbAccess = new NRDBAccessImpl();
    static {
        nrdbAccess.setProxyIp("10.33.3.31");
    }

    public static NRDBAccess getNrdbAccess() {
        return nrdbAccess;
    }
}
