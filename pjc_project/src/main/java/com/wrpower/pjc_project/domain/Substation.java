package com.wrpower.pjc_project.domain;

/**
 * 厂站类
 */
public class Substation {
    private String uuid;
    private String name;
    private String outservicedate;
    private String inservicedate;
    private String voltagelevel;
    private String kind;
    private String dispatcheruuid;          //调度管辖权
    private String owneruuid;
    private String servicestatus;
    private String projectuuid;
    private String bpaid;                   //BPA编号
    private String abbrname;                //厂站简称
    private String assetname;               //厂站资产名
    private String groupuuid;               //所属分组
    private String isdbc;                   //是否入库
    private String supplycity;              //供电县城
    private String usedname;                //曾用名
    private String modeluuid;               //供电县城
    private String versionuuid;             //所属版本uuid
    private String ftpattachmentnumbers;    //附件编号
    private String busbarconfiguration1;    //主接线形式1
    private String busbarconfiguration2;    //主接线形式2
    private String busbarconfiguration3;    //主接线形式3
    private String busbarconfiguration4;    //主接线形式4
    private String minratedcurrent1;        //最小开关遮断电流kA1
    private String minratedcurrent2;        //最小开关遮断电流kA2
    private String minratedcurrent3;        //最小开关遮断电流kA3
    private String minratedcurrent4;        //最小开关遮断电流kA4

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getInservicedate() {
        return inservicedate;
    }

    public void setInservicedate(String inservicedate) {
        this.inservicedate = inservicedate;
    }

    public String getVoltagelevel() {
        return voltagelevel;
    }

    public void setVoltagelevel(String voltagelevel) {
        this.voltagelevel = voltagelevel;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDispatcheruuid() {
        return dispatcheruuid;
    }

    public void setDispatcheruuid(String dispatcheruuid) {
        this.dispatcheruuid = dispatcheruuid;
    }

    public String getOwneruuid() {
        return owneruuid;
    }

    public void setOwneruuid(String owneruuid) {
        this.owneruuid = owneruuid;
    }

    public String getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(String servicestatus) {
        this.servicestatus = servicestatus;
    }

    public String getProjectuuid() {
        return projectuuid;
    }

    public void setProjectuuid(String projectuuid) {
        this.projectuuid = projectuuid;
    }

    public String getBpaid() {
        return bpaid;
    }

    public void setBpaid(String bpaid) {
        this.bpaid = bpaid;
    }

    public String getAbbrname() {
        return abbrname;
    }

    public void setAbbrname(String abbrname) {
        this.abbrname = abbrname;
    }

    public String getAssetname() {
        return assetname;
    }

    public void setAssetname(String assetname) {
        this.assetname = assetname;
    }

    public String getGroupuuid() {
        return groupuuid;
    }

    public void setGroupuuid(String groupuuid) {
        this.groupuuid = groupuuid;
    }

    public String getIsdbc() {
        return isdbc;
    }

    public void setIsdbc(String isdbc) {
        this.isdbc = isdbc;
    }

    public String getSupplycity() {
        return supplycity;
    }

    public void setSupplycity(String supplycity) {
        this.supplycity = supplycity;
    }

    public String getUsedname() {
        return usedname;
    }

    public void setUsedname(String usedname) {
        this.usedname = usedname;
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

    public String getFtpattachmentnumbers() {
        return ftpattachmentnumbers;
    }

    public void setFtpattachmentnumbers(String ftpattachmentnumbers) {
        this.ftpattachmentnumbers = ftpattachmentnumbers;
    }

    public String getBusbarconfiguration1() {
        return busbarconfiguration1;
    }

    public void setBusbarconfiguration1(String busbarconfiguration1) {
        this.busbarconfiguration1 = busbarconfiguration1;
    }

    public String getBusbarconfiguration2() {
        return busbarconfiguration2;
    }

    public void setBusbarconfiguration2(String busbarconfiguration2) {
        this.busbarconfiguration2 = busbarconfiguration2;
    }

    public String getBusbarconfiguration3() {
        return busbarconfiguration3;
    }

    public void setBusbarconfiguration3(String busbarconfiguration3) {
        this.busbarconfiguration3 = busbarconfiguration3;
    }

    public String getBusbarconfiguration4() {
        return busbarconfiguration4;
    }

    public void setBusbarconfiguration4(String busbarconfiguration4) {
        this.busbarconfiguration4 = busbarconfiguration4;
    }

    public String getMinratedcurrent1() {
        return minratedcurrent1;
    }

    public void setMinratedcurrent1(String minratedcurrent1) {
        this.minratedcurrent1 = minratedcurrent1;
    }

    public String getMinratedcurrent2() {
        return minratedcurrent2;
    }

    public void setMinratedcurrent2(String minratedcurrent2) {
        this.minratedcurrent2 = minratedcurrent2;
    }

    public String getMinratedcurrent3() {
        return minratedcurrent3;
    }

    public void setMinratedcurrent3(String minratedcurrent3) {
        this.minratedcurrent3 = minratedcurrent3;
    }

    public String getMinratedcurrent4() {
        return minratedcurrent4;
    }

    public void setMinratedcurrent4(String minratedcurrent4) {
        this.minratedcurrent4 = minratedcurrent4;
    }
}
