package com.wrpower.pjc_project.entity;

/**
 * 线路型号类
 */

public class AclineModel {

    private String bch;                 //电纳标幺值
    private String emergencycurrent;    //短时电流A
    private String name;                //电路型号名称
    private String r;                   //电阻标幺值
    private String ratedcurrent;        //额定电流A
    private String uuid;                //	uuid
    private String voltagelevel;        //	电压等级
    private String X;                   //	电抗标幺值


    public String getBch() {
        return bch;
    }

    public void setBch(String bch) {
        this.bch = bch;
    }

    public String getEmergencycurrent() {
        return emergencycurrent;
    }

    public void setEmergencycurrent(String emergencycurrent) {
        this.emergencycurrent = emergencycurrent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getRatedcurrent() {
        return ratedcurrent;
    }

    public void setRatedcurrent(String ratedcurrent) {
        this.ratedcurrent = ratedcurrent;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVoltagelevel() {
        return voltagelevel;
    }

    public void setVoltagelevel(String voltagelevel) {
        this.voltagelevel = voltagelevel;
    }

    public String getX() {
        return X;
    }

    public void setX(String x) {
        X = x;
    }
}
