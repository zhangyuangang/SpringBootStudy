package com.wrpower.pjc_project.domain;

/**
 * 开关类
 */

public class Breaker {

    private String dispatcheruuid;          //调度管辖权uuid
    private String groupuuid;               //所属编组uuid
    private String inservicedate;          //投产年月
    private String isdbc;                   //是否入库
    private String name;                    //开关名称
    private String outservicedate;          //退役年月
    private String owneruuid;               //	所有者uuid
    private String projectuuid;             //	所属工程uuid
    private String servicestatus;           //	投运状态
    private String substationuuid;          //	变电站uuid
    private String topoauuid;               //	始端节点uuid
    private String topobuuid;               //	末端节点uuid
    private String type;                    //	类型
    private String usedname;                //	曾用名
    private String uuid;                    //	uuid
    private String modeluuid;               //	调度云表中对应的uuid
    private String versionuuid;             //	所属版本uuid
    private String voltagelevel;            //	电压等级
    private String x;                       //	正序电抗标幺值
    private String x0;                      //	零序电抗标幺值
    private String ftpattachmentnumbers;    //	附件编号


    public String getDispatcheruuid() {
        return dispatcheruuid;
    }

    public void setDispatcheruuid(String dispatcheruuid) {
        this.dispatcheruuid = dispatcheruuid;
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

    public String getSubstationuuid() {
        return substationuuid;
    }

    public void setSubstationuuid(String substationuuid) {
        this.substationuuid = substationuuid;
    }

    public String getTopoauuid() {
        return topoauuid;
    }

    public void setTopoauuid(String topoauuid) {
        this.topoauuid = topoauuid;
    }

    public String getTopobuuid() {
        return topobuuid;
    }

    public void setTopobuuid(String topobuuid) {
        this.topobuuid = topobuuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getVoltagelevel() {
        return voltagelevel;
    }

    public void setVoltagelevel(String voltagelevel) {
        this.voltagelevel = voltagelevel;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getX0() {
        return x0;
    }

    public void setX0(String x0) {
        this.x0 = x0;
    }

    public String getFtpattachmentnumbers() {
        return ftpattachmentnumbers;
    }

    public void setFtpattachmentnumbers(String ftpattachmentnumbers) {
        this.ftpattachmentnumbers = ftpattachmentnumbers;
    }
}
