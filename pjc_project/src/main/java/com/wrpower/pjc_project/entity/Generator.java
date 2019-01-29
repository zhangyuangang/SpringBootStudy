package com.wrpower.pjc_project.entity;

/**
 * 发电机类
 */
public class Generator {

    private String basevoltage;             //	机端电压kV
    private String bpaid;                   //	bpa编号
    private String busname;                 //	母线名称
    private String cap_maxq1;               //	无功能力曲线折点1无功上限
    private String cap_maxq2;               //	无功能力曲线折点2无功上限
    private String cap_maxq3;               //	无功能力曲线折点3无功上限
    private String cap_maxq4;               //	无功能力曲线折点4无功上限
    private String cap_minq1;               //	无功能力曲线折点1无功下限
    private String cap_minq2;               //	无功能力曲线折点2无功下限
    private String cap_minq3;               //	无功能力曲线折点3无功下限
    private String cap_minq4;               //	无功能力曲线折点4无功下限
    private String cap_p1;                  //	无功能力曲线折点1有功
    private String cap_p2;                  //	无功能力曲线折点2有功
    private String cap_p3;                  //	无功能力曲线折点3有功
    private String cap_p4;                  //	无功能力曲线折点4有功
    private String controlbusfactor;        //	控制母线参与因子
    private String controlbusuuid;          //	控制母线uuid
    private String design_maxq;             //	设计无功上限MVar
    private String design_minp;             //	设计有功下限Mw
    private String design_minq;             //	设计无功下限MVar
    private String design_ratedmw;          //	设计额定功率Mw
    private String design_ratedpf;          //	设计额定功率因数Cos
    private String design_rateds;           //	设计额定容量Mva
    private String design_x;                //	设计正序电抗标幺值
    private String design_x0;               //	设计零序电抗标幺值
    private String design_x2;               //	设计负序电抗标幺值
    private String dispatcheruuid;          //	所属调度uuid
    private String genindex;                //	发电机编号
    private String genp;                    //出力有功Mw
    private String genq;                    //	出力无功MVar
    private String groupuuid;               //	所属分组uuid
    private String inservicedate;           //	投产年月
    private String isdbc;                   //	是否入库
    private String isslack;                 //	是否平衡机
    private String maxp1;                   //	夏季最大有功Mw
    private String maxp2;                   //	冬季最大有功Mw
    private String name;                    //	发电机名称
    private String outservicedate;          //	退役年月
    private String owneruuid;               //	所有者uuid
    private String parameterkind;           //	参数类型
    private String project_maxq;            //	工程无功上限MVar
    private String project_minp;            //	工程有功下限Mw
    private String project_minq;            //	工程无功下限MVar
    private String project_ratedmw;         //	工程额定功率Mw
    private String project_ratedpf;         //	工程额定功率因数Cos
    private String project_rateds;          //	工程额定容量Mva
    private String project_x;               //	工程正序电抗标幺值
    private String project_x0;              //	工程零序电抗标幺值
    private String project_x2;              //	工程负序电抗标幺值
    private String projectuuid;             //	所属工程uuid
    private String servicestatus;           //	投运状态
    private String subloadp;                //	厂用电有功Mw
    private String subloadq;                //	厂用电无功MVar
    private String substationuuid;          //	所属厂站uuid
    private String swivalue;                //	暂态模型
    private String type;                    //	机组类型
    private String updatedate;              //	更新日期
    private String usedname;                //	曾用名
    private String uuid;                    //uuid
    private String modeluuid;               //
    private String versionuuid;             //	所属版本uuid
    private String volhi;                   //	电压上限标幺值
    private String vollo;                   //	电压下限标幺值
    private String voltagelevel;            //	电压等级
    private String ftpattachmentnumbers;    //附件编号

    public String getBasevoltage() {
        return basevoltage;
    }

    public void setBasevoltage(String basevoltage) {
        this.basevoltage = basevoltage;
    }

    public String getBpaid() {
        return bpaid;
    }

    public void setBpaid(String bpaid) {
        this.bpaid = bpaid;
    }

    public String getBusname() {
        return busname;
    }

    public void setBusname(String busname) {
        this.busname = busname;
    }

    public String getCap_maxq1() {
        return cap_maxq1;
    }

    public void setCap_maxq1(String cap_maxq1) {
        this.cap_maxq1 = cap_maxq1;
    }

    public String getCap_maxq2() {
        return cap_maxq2;
    }

    public void setCap_maxq2(String cap_maxq2) {
        this.cap_maxq2 = cap_maxq2;
    }

    public String getCap_maxq3() {
        return cap_maxq3;
    }

    public void setCap_maxq3(String cap_maxq3) {
        this.cap_maxq3 = cap_maxq3;
    }

    public String getCap_maxq4() {
        return cap_maxq4;
    }

    public void setCap_maxq4(String cap_maxq4) {
        this.cap_maxq4 = cap_maxq4;
    }

    public String getCap_minq1() {
        return cap_minq1;
    }

    public void setCap_minq1(String cap_minq1) {
        this.cap_minq1 = cap_minq1;
    }

    public String getCap_minq2() {
        return cap_minq2;
    }

    public void setCap_minq2(String cap_minq2) {
        this.cap_minq2 = cap_minq2;
    }

    public String getCap_minq3() {
        return cap_minq3;
    }

    public void setCap_minq3(String cap_minq3) {
        this.cap_minq3 = cap_minq3;
    }

    public String getCap_minq4() {
        return cap_minq4;
    }

    public void setCap_minq4(String cap_minq4) {
        this.cap_minq4 = cap_minq4;
    }

    public String getCap_p1() {
        return cap_p1;
    }

    public void setCap_p1(String cap_p1) {
        this.cap_p1 = cap_p1;
    }

    public String getCap_p2() {
        return cap_p2;
    }

    public void setCap_p2(String cap_p2) {
        this.cap_p2 = cap_p2;
    }

    public String getCap_p3() {
        return cap_p3;
    }

    public void setCap_p3(String cap_p3) {
        this.cap_p3 = cap_p3;
    }

    public String getCap_p4() {
        return cap_p4;
    }

    public void setCap_p4(String cap_p4) {
        this.cap_p4 = cap_p4;
    }

    public String getControlbusfactor() {
        return controlbusfactor;
    }

    public void setControlbusfactor(String controlbusfactor) {
        this.controlbusfactor = controlbusfactor;
    }

    public String getControlbusuuid() {
        return controlbusuuid;
    }

    public void setControlbusuuid(String controlbusuuid) {
        this.controlbusuuid = controlbusuuid;
    }

    public String getDesign_maxq() {
        return design_maxq;
    }

    public void setDesign_maxq(String design_maxq) {
        this.design_maxq = design_maxq;
    }

    public String getDesign_minp() {
        return design_minp;
    }

    public void setDesign_minp(String design_minp) {
        this.design_minp = design_minp;
    }

    public String getDesign_minq() {
        return design_minq;
    }

    public void setDesign_minq(String design_minq) {
        this.design_minq = design_minq;
    }

    public String getDesign_ratedmw() {
        return design_ratedmw;
    }

    public void setDesign_ratedmw(String design_ratedmw) {
        this.design_ratedmw = design_ratedmw;
    }

    public String getDesign_ratedpf() {
        return design_ratedpf;
    }

    public void setDesign_ratedpf(String design_ratedpf) {
        this.design_ratedpf = design_ratedpf;
    }

    public String getDesign_rateds() {
        return design_rateds;
    }

    public void setDesign_rateds(String design_rateds) {
        this.design_rateds = design_rateds;
    }

    public String getDesign_x() {
        return design_x;
    }

    public void setDesign_x(String design_x) {
        this.design_x = design_x;
    }

    public String getDesign_x0() {
        return design_x0;
    }

    public void setDesign_x0(String design_x0) {
        this.design_x0 = design_x0;
    }

    public String getDesign_x2() {
        return design_x2;
    }

    public void setDesign_x2(String design_x2) {
        this.design_x2 = design_x2;
    }

    public String getDispatcheruuid() {
        return dispatcheruuid;
    }

    public void setDispatcheruuid(String dispatcheruuid) {
        this.dispatcheruuid = dispatcheruuid;
    }

    public String getGenindex() {
        return genindex;
    }

    public void setGenindex(String genindex) {
        this.genindex = genindex;
    }

    public String getGenp() {
        return genp;
    }

    public void setGenp(String genp) {
        this.genp = genp;
    }

    public String getGenq() {
        return genq;
    }

    public void setGenq(String genq) {
        this.genq = genq;
    }

    public String getGroupuuid() {
        return groupuuid;
    }

    public void setGroupuuid(String groupuuid) {
        this.groupuuid = groupuuid;
    }

    public String getInservicedate() {
        return inservicedate;
    }

    public void setInservicedate(String inservicedate) {
        this.inservicedate = inservicedate;
    }

    public String getIsdbc() {
        return isdbc;
    }

    public void setIsdbc(String isdbc) {
        this.isdbc = isdbc;
    }

    public String getIsslack() {
        return isslack;
    }

    public void setIsslack(String isslack) {
        this.isslack = isslack;
    }

    public String getMaxp1() {
        return maxp1;
    }

    public void setMaxp1(String maxp1) {
        this.maxp1 = maxp1;
    }

    public String getMaxp2() {
        return maxp2;
    }

    public void setMaxp2(String maxp2) {
        this.maxp2 = maxp2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutservicedate() {
        return outservicedate;
    }

    public void setOutservicedate(String outservicedate) {
        this.outservicedate = outservicedate;
    }

    public String getOwneruuid() {
        return owneruuid;
    }

    public void setOwneruuid(String owneruuid) {
        this.owneruuid = owneruuid;
    }

    public String getParameterkind() {
        return parameterkind;
    }

    public void setParameterkind(String parameterkind) {
        this.parameterkind = parameterkind;
    }

    public String getProject_maxq() {
        return project_maxq;
    }

    public void setProject_maxq(String project_maxq) {
        this.project_maxq = project_maxq;
    }

    public String getProject_minp() {
        return project_minp;
    }

    public void setProject_minp(String project_minp) {
        this.project_minp = project_minp;
    }

    public String getProject_minq() {
        return project_minq;
    }

    public void setProject_minq(String project_minq) {
        this.project_minq = project_minq;
    }

    public String getProject_ratedmw() {
        return project_ratedmw;
    }

    public void setProject_ratedmw(String project_ratedmw) {
        this.project_ratedmw = project_ratedmw;
    }

    public String getProject_ratedpf() {
        return project_ratedpf;
    }

    public void setProject_ratedpf(String project_ratedpf) {
        this.project_ratedpf = project_ratedpf;
    }

    public String getProject_rateds() {
        return project_rateds;
    }

    public void setProject_rateds(String project_rateds) {
        this.project_rateds = project_rateds;
    }

    public String getProject_x() {
        return project_x;
    }

    public void setProject_x(String project_x) {
        this.project_x = project_x;
    }

    public String getProject_x0() {
        return project_x0;
    }

    public void setProject_x0(String project_x0) {
        this.project_x0 = project_x0;
    }

    public String getProject_x2() {
        return project_x2;
    }

    public void setProject_x2(String project_x2) {
        this.project_x2 = project_x2;
    }

    public String getProjectuuid() {
        return projectuuid;
    }

    public void setProjectuuid(String projectuuid) {
        this.projectuuid = projectuuid;
    }

    public String getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(String servicestatus) {
        this.servicestatus = servicestatus;
    }

    public String getSubloadp() {
        return subloadp;
    }

    public void setSubloadp(String subloadp) {
        this.subloadp = subloadp;
    }

    public String getSubloadq() {
        return subloadq;
    }

    public void setSubloadq(String subloadq) {
        this.subloadq = subloadq;
    }

    public String getSubstationuuid() {
        return substationuuid;
    }

    public void setSubstationuuid(String substationuuid) {
        this.substationuuid = substationuuid;
    }

    public String getSwivalue() {
        return swivalue;
    }

    public void setSwivalue(String swivalue) {
        this.swivalue = swivalue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getUsedname() {
        return usedname;
    }

    public void setUsedname(String usedname) {
        this.usedname = usedname;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getModeluuid() {
        return modeluuid;
    }

    public void setModeluuid(String modeluuid) {
        this.modeluuid = modeluuid;
    }

    public String getVersionuuid() {
        return versionuuid;
    }

    public void setVersionuuid(String versionuuid) {
        this.versionuuid = versionuuid;
    }

    public String getVolhi() {
        return volhi;
    }

    public void setVolhi(String volhi) {
        this.volhi = volhi;
    }

    public String getVollo() {
        return vollo;
    }

    public void setVollo(String vollo) {
        this.vollo = vollo;
    }

    public String getVoltagelevel() {
        return voltagelevel;
    }

    public void setVoltagelevel(String voltagelevel) {
        this.voltagelevel = voltagelevel;
    }

    public String getFtpattachmentnumbers() {
        return ftpattachmentnumbers;
    }

    public void setFtpattachmentnumbers(String ftpattachmentnumbers) {
        this.ftpattachmentnumbers = ftpattachmentnumbers;
    }
}
