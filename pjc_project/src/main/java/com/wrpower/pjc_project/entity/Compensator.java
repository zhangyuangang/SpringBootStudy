package com.wrpower.pjc_project.entity;

/**
 * 容抗器类
 */
public class Compensator {
    private String busnameuuid;             //所属母线uuid
    private String compensatorindex;        //补偿器编号
    private String controlbusuuid;          //控制母线uuid
    private String controlratio;            //控制系数
    private String design_cap;              //设计串抗率百分比
    private String design_rateds;           //计额定容量MVA
    private String dispatchername;          //调度命名
    private String groupuuid;               //所属分组uuid
    private String inservicedate;           //投产年月
    private String isdbc;                   //是否入库
    private String kind;                    //补偿器类型
    private String name;                    //低压容抗名称
    private String outservicedate;          //退役年月
    private String parameterkind;           //参数类型
    private String project_cap;             //工程串抗率百分比
    private String project_rateds;          //工程额定容量MVA
    private String projectuuid;             //所属工程uuid
    private String servicestatus;           //投运状态
    private String usedname;                //曾用名
    private String uuid;                    //uuid
    private String modeluuid;               //
    private String versionuuid;             //所属版本uuid
    private String voltagelevel;            //电压等级
    private String ftpattachmentnumbers;    //附件编号

    public String getBusnameuuid() {
        return busnameuuid;
    }

    public void setBusnameuuid(String busnameuuid) {
        this.busnameuuid = busnameuuid;
    }

    public String getCompensatorindex() {
        return compensatorindex;
    }

    public void setCompensatorindex(String compensatorindex) {
        this.compensatorindex = compensatorindex;
    }

    public String getControlbusuuid() {
        return controlbusuuid;
    }

    public void setControlbusuuid(String controlbusuuid) {
        this.controlbusuuid = controlbusuuid;
    }

    public String getControlratio() {
        return controlratio;
    }

    public void setControlratio(String controlratio) {
        this.controlratio = controlratio;
    }

    public String getDesign_cap() {
        return design_cap;
    }

    public void setDesign_cap(String design_cap) {
        this.design_cap = design_cap;
    }

    public String getDesign_rateds() {
        return design_rateds;
    }

    public void setDesign_rateds(String design_rateds) {
        this.design_rateds = design_rateds;
    }

    public String getDispatchername() {
        return dispatchername;
    }

    public void setDispatchername(String dispatchername) {
        this.dispatchername = dispatchername;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public String getParameterkind() {
        return parameterkind;
    }

    public void setParameterkind(String parameterkind) {
        this.parameterkind = parameterkind;
    }

    public String getProject_cap() {
        return project_cap;
    }

    public void setProject_cap(String project_cap) {
        this.project_cap = project_cap;
    }

    public String getProject_rateds() {
        return project_rateds;
    }

    public void setProject_rateds(String project_rateds) {
        this.project_rateds = project_rateds;
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

    public String getFtpattachmentnumbers() {
        return ftpattachmentnumbers;
    }

    public void setFtpattachmentnumbers(String ftpattachmentnumbers) {
        this.ftpattachmentnumbers = ftpattachmentnumbers;
    }
}
