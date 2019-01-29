package com.wrpower.pjc_project.domain;

/**
 * 对地支路类
 */
public class GroundBranch {

    private String groupuuid;               //所属分组uuid
    private String inservicedate;           //	投产年月
    private String isdbc;                   //	是否入库
    private String nameuuid;                //	所属母线名称uuid
    private String outservicedate;          //	退役年月
    private String projectuuid;             //	所属工程uuid
    private String servicestatus;           //	投运状态
    private String usedname;                //曾用名
    private String uuid;                    //	uuid
    private String modeluuid;               //
    private String versionuuid;             //	所属版本uuid
    private String voltagelevel;            //	电压等级
    private String x0;                      //	零序电抗标幺值
    private String ftpattachmentnumbers;    //	附件编号

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

    public String getNameuuid() {
        return nameuuid;
    }

    public void setNameuuid(String nameuuid) {
        this.nameuuid = nameuuid;
    }

    public String getOutservicedate() {
        return outservicedate;
    }

    public void setOutservicedate(String outservicedate) {
        this.outservicedate = outservicedate;
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
