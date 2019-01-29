package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;


public class DevDetailQuery {

    private static NRDBAccess m_nrdbAccess = new NRDBAccessImpl();

    static {
        m_nrdbAccess.setProxyIp("10.33.3.31");
    }



}
