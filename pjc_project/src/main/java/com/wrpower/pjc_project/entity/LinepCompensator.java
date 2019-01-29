package com.wrpower.pjc_project.entity;

/**
 * 线路高抗类
 */
public class LinepCompensator {

    private String design_midphx;    	    //设计中性点小电抗有名值
    private String design_rateds;    	    //设计额定容量Mva
    private String groupuuid;    	        //分组编号uuid
    private String inservicedate;    	    //投产年月
    private String isdbc;    	            //是否入库
    private String linenameuuid;    	    //所属线路uuid
    private String name;    	            //线路高抗名称
    private String outservicedate;    	    //退役年月
    private String parameterkind;    	    //参数类型
    private String project_midphx;    	    //工程中性点小电抗有名值
    private String project_rateds;    	    //工程额定容量Mva
    private String projectuuid;    	        //所属工程uuid
    private String servicestatus;    	    //投运状态
    private String substationuuid;    	    //变电站uuid
    private String usedname;    	        //曾用名
    private String uuid;    	            //uuid
    private String modeluuid;
    private String versionuuid;    	        //所属版本uuid
    private String voltagelevel;    	    //电压等级
    private String x0;    	                //零序电抗
    private String ftpattachmentnumbers;    //附件编号

    public String getDesign_midphx() {
        return design_midphx;
    }

    public void setDesign_midphx(String design_midphx) {
        this.design_midphx = design_midphx;
    }

    public String getDesign_rateds() {
        return design_rateds;
    }

    public void setDesign_rateds(String design_rateds) {
        this.design_rateds = design_rateds;
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

    public String getLinenameuuid() {
        return linenameuuid;
    }

    public void setLinenameuuid(String linenameuuid) {
        this.linenameuuid = linenameuuid;
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

    public String getProject_midphx() {
        return project_midphx;
    }

    public void setProject_midphx(String project_midphx) {
        this.project_midphx = project_midphx;
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

    public String getSubstationuuid() {
        return substationuuid;
    }

    public void setSubstationuuid(String substationuuid) {
        this.substationuuid = substationuuid;
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
