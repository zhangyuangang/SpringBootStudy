package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import com.wrpower.pjc_project.domain.Acline;
import org.springframework.stereotype.Service;

@Service
public class AclineManage {

    static private NRDBAccess nrdbAccess = new NRDBAccessImpl();
    {
        nrdbAccess.setProxyIp("10.33.3.31");
    }

    public AclineManage()
    {
    }

    public String getAclineName()
    {
        Acline acline = new Acline();
        acline.setName("test acline~~~");
        return acline.getName();
    }
}
