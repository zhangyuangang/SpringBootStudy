package com.wrpower.pjc_project.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class PjcProject {
    private String author;
    private String name;
    private String onlyversion;
    private String inservicedate;
    private String outservicedate;
    private String owneruuid;   //owner 的Id
    private String owner;       // 中文名称
    private String projectsource;
    private String projecttype;
    private String status;
    private String uuid;
    private String voltagelevel;
    private String version;
    private int is_reform;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInservicedate() {
        return inservicedate;
    }

    public void setInservicedate(String inservicedate) {
        this.inservicedate = inservicedate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnlyversion() {
        return onlyversion;
    }

    public void setOnlyversion(String onlyversion) {
        this.onlyversion = onlyversion;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProjectsource() {
        return projectsource;
    }

    public void setProjectsource(String projectsource) {
        this.projectsource = projectsource;
    }

    public String getProjecttype() {
        return projecttype;
    }

    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getIs_reform() {
        return is_reform;
    }

    public void setIs_reform(int is_reform) {
        this.is_reform = is_reform;
    }
}
