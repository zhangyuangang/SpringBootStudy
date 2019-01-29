package com.wrpower.pjc_project.entity;

/**
 * 变压器
 */
public class Transformer {

    private String bpaid;    //	BPA别名
    private String controltapchanger;    //	可调抽头侧
    private String currentstep;    //	实际运行档位
    private String currentvoltage;    //	实际运行电压
    private String design_b;    //	设计励磁电纳标幺值
    private String design_emergencyratio;    //	设计过负荷倍数
    private String design_g;    //	设计励磁电导标幺值
    private String design_i0;    //	设计I0空载电流百分比
    private String design_midphr;    //	设计中性点小电阻有名值
    private String design_midphx;    //	设计中性点小电抗有名值
    private String design_p0;    //	设计P0空载损耗kW
    private String design_pk12;    //	设计Pk高中短路损耗kW
    private String design_pk13;    //	设计Pk高低短路损耗kW
    private String design_pk23;    //	设计Pk中低短路损耗kW
    private String design_startratio;    //	设计起始倍数
    private String design_uk12;    //	设计Uk高中短路电压百分比
    private String design_uk13;    //	设计Uk高低短路电压百分比
    private String design_uk23;    //	设计Uk中低短路电压百分比
    private String design_wind1_r;    //	设计高压正序电阻标幺值
    private String design_wind1_r0;    //	设计高压零序电阻标幺值
    private String design_wind1_rateds;    //	设计高压额定容量Mva
    private String design_wind1_ratedvoltage;    //	设计高压额定电压
    private String design_wind1_x;    //	设计高压正序电抗标幺值
    private String design_wind1_x0;    //	设计PSSE高压正序电抗标幺值
    private String design_wind1_x0_psse;    //	设计高压零序电抗标幺值
    private String design_wind1_x_psse;    //	设计PSSE高压零序电抗标幺值
    private String design_wind2_r;    //	设计中压正序电阻标幺值
    private String design_wind2_r0;    // 	设计中压零序电阻标幺值
    private String design_wind2_rateds;    // 	设计中压额定容量Mva
    private String design_wind2_ratedvoltage;    // 	设计中压额定电压
    private String design_wind2_x;    // 	设计中压正序电抗标幺值
    private String design_wind2_x0;    // 	设计中压零序电抗标幺值
    private String design_wind2_x0_psse;    // 	设计PSSE中压零序电抗标幺值
    private String design_wind2_x_psse;    // 	设计PSSE中压正序电抗标幺值
    private String design_wind3_r;    // 	设计低压正序电阻标幺值
    private String design_wind3_r0;    // 	设计低压零序电阻标幺值
    private String design_wind3_rateds;    // 	设计低压额定容量Mva
    private String design_wind3_ratedvoltage;    // 	设计低压额定电压
    private String design_wind3_x;    // 	设计低压正序电抗标幺值
    private String design_wind3_x0;    // 	设计低压零序电抗标幺值
    private String design_wind3_x0_psse;    // 	设计PSSE低压零序电抗标幺值
    private String design_wind3_x_psse;    // 	设计PSSE低压正序电抗标幺值
    private String dispatcheruuid;    //	调度管辖权uuid
    private String emergencyratio30;    // 	过负荷倍数30度
    private String emergencyratio40;    // 	过负荷倍数40度
    private String groupuuid;    //	所属分组uuid
    private String highstep;    // 	最大档位
    private String inservicedate;    //	投产年月
    private String isdbc;    //	是否入库
    private String isoltc;    //	是否有载调压
    private String isphase3;    //	是否三相变
    private String isself;    //	是否自耦变
    private String istran3;    //	是否三卷变
    private String lowstep;    // 	最小档位
    private String midbasekv;    // 	中性点基准电压kV
    private String midbpaid;    //	中性点bpa编号
    private String midname;    //	中性点名称
    private String model;    //	变压器型号
    private String name;    //	变压器名称
    private String normalstep;    // 	额定档位
    private String outservicedate;    //	退役年月
    private String owneruuid;    //	所有者uuid
    private String parameterkind;    //	参数类型
    private String project_b;    // 	工程励磁电纳标幺值
    private String project_emergencyratio;    // 	工程过负荷倍数
    private String project_g;    // 	工程励磁电导标幺值
    private String project_i0;    // 	工程I0空载电流百分比
    private String project_midphr;    // 	工程中性点小电阻有名值
    private String project_midphx;    // 	工程中性点小电抗有名值
    private String project_p0;    // 	工程P0空载损耗kW
    private String project_pk12;    // 	工程Pk高中短路损耗kW
    private String project_pk13;    // 	工程Pk高低短路损耗kW
    private String project_pk23;    // 	工程Pk中低短路损耗kW
    private String project_startratio;    // 	工程起始倍数
    private String project_uk12;    // 	工程Uk高中短路电压百分比
    private String project_uk13;    // 	工程Uk高低短路电压百分比
    private String project_uk23;    // 	工程Uk中低短路电压百分比
    private String project_wind1_r;    // 	工程高压正序电阻标幺值
    private String project_wind1_r0;    // 	工程高压零序电阻标幺值
    private String project_wind1_rateds;    // 	工程高压额定容量Mva
    private String project_wind1_ratedvoltage;    // 	工程高压额定电压
    private String project_wind1_x;    // 	工程高压正序电抗标幺值
    private String project_wind1_x0;    // 	工程高压零序电抗标幺值
    private String project_wind1_x0_psse;    // 	工程PSSE高压零序电抗标幺值
    private String project_wind1_x_psse;    // 	工程PSSE高压正序电抗标幺值
    private String project_wind2_r;    // 	工程中压正序电阻标幺值
    private String project_wind2_r0;    // 	工程中压零序电阻标幺值
    private String project_wind2_rateds;    // 	工程中压额定容量Mva
    private String project_wind2_ratedvoltage;    // 	工程中压额定电压
    private String project_wind2_x;    // 	工程中压正序电抗标幺值
    private String project_wind2_x0;    // 	工程中压零序电抗标幺值
    private String project_wind2_x0_psse;    // 	工程PSSE中压零序电抗标幺值
    private String project_wind2_x_psse;    // 	工程PSSE中压正序电抗标幺值
    private String project_wind3_r;    // 	工程低压正序电阻标幺值
    private String project_wind3_r0;    // 	工程低压零序电阻标幺值
    private String project_wind3_rateds;    // 	工程低压额定容量Mva
    private String project_wind3_ratedvoltage;    // 	工程低压额定电压
    private String project_wind3_x;    // 	工程低压正序电抗标幺值
    private String project_wind3_x0;    // 	工程低压零序电抗标幺值
    private String project_wind3_x0_psse;    // 	工程PSSE低压零序电抗标幺值
    private String project_wind3_x_psse;    // 	工程PSSE低压正序电抗标幺值
    private String projectuuid;    //	所属工程uuid
    private String servicestatus;    //	投运状态
    private String startratio30;    // 	起始倍数30度
    private String startratio40;    // 	起始倍数40度
    private String stepvoltage;    // 	电压步长百分比
    private String substationuuid;    //	所属厂站uuid
    private String tranindex;    //	主变编号
    private String type;    //	类型
    private String updatedate;    //	更新日期
    private String usedname;    //	曾用名
    private String uuid;    //	uuid
    private String modeluuid;    //
    private String versionuuid;    //	所属版本uuid
    private String voltagelevel;    //	电压等级
    private String wind1_busnameuuid;    //	高压母线名uuid
    private String wind2_busnameuuid;    //	中压母线名uuid
    private String wind3_busnameuuid;    //	低压母线名uuid
    private String windingconnection;    //	连接组别
    private String ftpattachmentnumbers;    //附件编号

    public String getBpaid() {
        return bpaid;
    }

    public void setBpaid(String bpaid) {
        this.bpaid = bpaid;
    }

    public String getControltapchanger() {
        return controltapchanger;
    }

    public void setControltapchanger(String controltapchanger) {
        this.controltapchanger = controltapchanger;
    }

    public String getCurrentstep() {
        return currentstep;
    }

    public void setCurrentstep(String currentstep) {
        this.currentstep = currentstep;
    }

    public String getCurrentvoltage() {
        return currentvoltage;
    }

    public void setCurrentvoltage(String currentvoltage) {
        this.currentvoltage = currentvoltage;
    }

    public String getDesign_b() {
        return design_b;
    }

    public void setDesign_b(String design_b) {
        this.design_b = design_b;
    }

    public String getDesign_emergencyratio() {
        return design_emergencyratio;
    }

    public void setDesign_emergencyratio(String design_emergencyratio) {
        this.design_emergencyratio = design_emergencyratio;
    }

    public String getDesign_g() {
        return design_g;
    }

    public void setDesign_g(String design_g) {
        this.design_g = design_g;
    }

    public String getDesign_i0() {
        return design_i0;
    }

    public void setDesign_i0(String design_i0) {
        this.design_i0 = design_i0;
    }

    public String getDesign_midphr() {
        return design_midphr;
    }

    public void setDesign_midphr(String design_midphr) {
        this.design_midphr = design_midphr;
    }

    public String getDesign_midphx() {
        return design_midphx;
    }

    public void setDesign_midphx(String design_midphx) {
        this.design_midphx = design_midphx;
    }

    public String getDesign_p0() {
        return design_p0;
    }

    public void setDesign_p0(String design_p0) {
        this.design_p0 = design_p0;
    }

    public String getDesign_pk12() {
        return design_pk12;
    }

    public void setDesign_pk12(String design_pk12) {
        this.design_pk12 = design_pk12;
    }

    public String getDesign_pk13() {
        return design_pk13;
    }

    public void setDesign_pk13(String design_pk13) {
        this.design_pk13 = design_pk13;
    }

    public String getDesign_pk23() {
        return design_pk23;
    }

    public void setDesign_pk23(String design_pk23) {
        this.design_pk23 = design_pk23;
    }

    public String getDesign_startratio() {
        return design_startratio;
    }

    public void setDesign_startratio(String design_startratio) {
        this.design_startratio = design_startratio;
    }

    public String getDesign_uk12() {
        return design_uk12;
    }

    public void setDesign_uk12(String design_uk12) {
        this.design_uk12 = design_uk12;
    }

    public String getDesign_uk13() {
        return design_uk13;
    }

    public void setDesign_uk13(String design_uk13) {
        this.design_uk13 = design_uk13;
    }

    public String getDesign_uk23() {
        return design_uk23;
    }

    public void setDesign_uk23(String design_uk23) {
        this.design_uk23 = design_uk23;
    }

    public String getDesign_wind1_r() {
        return design_wind1_r;
    }

    public void setDesign_wind1_r(String design_wind1_r) {
        this.design_wind1_r = design_wind1_r;
    }

    public String getDesign_wind1_r0() {
        return design_wind1_r0;
    }

    public void setDesign_wind1_r0(String design_wind1_r0) {
        this.design_wind1_r0 = design_wind1_r0;
    }

    public String getDesign_wind1_rateds() {
        return design_wind1_rateds;
    }

    public void setDesign_wind1_rateds(String design_wind1_rateds) {
        this.design_wind1_rateds = design_wind1_rateds;
    }

    public String getDesign_wind1_ratedvoltage() {
        return design_wind1_ratedvoltage;
    }

    public void setDesign_wind1_ratedvoltage(String design_wind1_ratedvoltage) {
        this.design_wind1_ratedvoltage = design_wind1_ratedvoltage;
    }

    public String getDesign_wind1_x() {
        return design_wind1_x;
    }

    public void setDesign_wind1_x(String design_wind1_x) {
        this.design_wind1_x = design_wind1_x;
    }

    public String getDesign_wind1_x0() {
        return design_wind1_x0;
    }

    public void setDesign_wind1_x0(String design_wind1_x0) {
        this.design_wind1_x0 = design_wind1_x0;
    }

    public String getDesign_wind1_x0_psse() {
        return design_wind1_x0_psse;
    }

    public void setDesign_wind1_x0_psse(String design_wind1_x0_psse) {
        this.design_wind1_x0_psse = design_wind1_x0_psse;
    }

    public String getDesign_wind1_x_psse() {
        return design_wind1_x_psse;
    }

    public void setDesign_wind1_x_psse(String design_wind1_x_psse) {
        this.design_wind1_x_psse = design_wind1_x_psse;
    }

    public String getDesign_wind2_r() {
        return design_wind2_r;
    }

    public void setDesign_wind2_r(String design_wind2_r) {
        this.design_wind2_r = design_wind2_r;
    }

    public String getDesign_wind2_r0() {
        return design_wind2_r0;
    }

    public void setDesign_wind2_r0(String design_wind2_r0) {
        this.design_wind2_r0 = design_wind2_r0;
    }

    public String getDesign_wind2_rateds() {
        return design_wind2_rateds;
    }

    public void setDesign_wind2_rateds(String design_wind2_rateds) {
        this.design_wind2_rateds = design_wind2_rateds;
    }

    public String getDesign_wind2_ratedvoltage() {
        return design_wind2_ratedvoltage;
    }

    public void setDesign_wind2_ratedvoltage(String design_wind2_ratedvoltage) {
        this.design_wind2_ratedvoltage = design_wind2_ratedvoltage;
    }

    public String getDesign_wind2_x() {
        return design_wind2_x;
    }

    public void setDesign_wind2_x(String design_wind2_x) {
        this.design_wind2_x = design_wind2_x;
    }

    public String getDesign_wind2_x0() {
        return design_wind2_x0;
    }

    public void setDesign_wind2_x0(String design_wind2_x0) {
        this.design_wind2_x0 = design_wind2_x0;
    }

    public String getDesign_wind2_x0_psse() {
        return design_wind2_x0_psse;
    }

    public void setDesign_wind2_x0_psse(String design_wind2_x0_psse) {
        this.design_wind2_x0_psse = design_wind2_x0_psse;
    }

    public String getDesign_wind2_x_psse() {
        return design_wind2_x_psse;
    }

    public void setDesign_wind2_x_psse(String design_wind2_x_psse) {
        this.design_wind2_x_psse = design_wind2_x_psse;
    }

    public String getDesign_wind3_r() {
        return design_wind3_r;
    }

    public void setDesign_wind3_r(String design_wind3_r) {
        this.design_wind3_r = design_wind3_r;
    }

    public String getDesign_wind3_r0() {
        return design_wind3_r0;
    }

    public void setDesign_wind3_r0(String design_wind3_r0) {
        this.design_wind3_r0 = design_wind3_r0;
    }

    public String getDesign_wind3_rateds() {
        return design_wind3_rateds;
    }

    public void setDesign_wind3_rateds(String design_wind3_rateds) {
        this.design_wind3_rateds = design_wind3_rateds;
    }

    public String getDesign_wind3_ratedvoltage() {
        return design_wind3_ratedvoltage;
    }

    public void setDesign_wind3_ratedvoltage(String design_wind3_ratedvoltage) {
        this.design_wind3_ratedvoltage = design_wind3_ratedvoltage;
    }

    public String getDesign_wind3_x() {
        return design_wind3_x;
    }

    public void setDesign_wind3_x(String design_wind3_x) {
        this.design_wind3_x = design_wind3_x;
    }

    public String getDesign_wind3_x0() {
        return design_wind3_x0;
    }

    public void setDesign_wind3_x0(String design_wind3_x0) {
        this.design_wind3_x0 = design_wind3_x0;
    }

    public String getDesign_wind3_x0_psse() {
        return design_wind3_x0_psse;
    }

    public void setDesign_wind3_x0_psse(String design_wind3_x0_psse) {
        this.design_wind3_x0_psse = design_wind3_x0_psse;
    }

    public String getDesign_wind3_x_psse() {
        return design_wind3_x_psse;
    }

    public void setDesign_wind3_x_psse(String design_wind3_x_psse) {
        this.design_wind3_x_psse = design_wind3_x_psse;
    }

    public String getDispatcheruuid() {
        return dispatcheruuid;
    }

    public void setDispatcheruuid(String dispatcheruuid) {
        this.dispatcheruuid = dispatcheruuid;
    }

    public String getEmergencyratio30() {
        return emergencyratio30;
    }

    public void setEmergencyratio30(String emergencyratio30) {
        this.emergencyratio30 = emergencyratio30;
    }

    public String getEmergencyratio40() {
        return emergencyratio40;
    }

    public void setEmergencyratio40(String emergencyratio40) {
        this.emergencyratio40 = emergencyratio40;
    }

    public String getGroupuuid() {
        return groupuuid;
    }

    public void setGroupuuid(String groupuuid) {
        this.groupuuid = groupuuid;
    }

    public String getHighstep() {
        return highstep;
    }

    public void setHighstep(String highstep) {
        this.highstep = highstep;
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

    public String getIsoltc() {
        return isoltc;
    }

    public void setIsoltc(String isoltc) {
        this.isoltc = isoltc;
    }

    public String getIsphase3() {
        return isphase3;
    }

    public void setIsphase3(String isphase3) {
        this.isphase3 = isphase3;
    }

    public String getIsself() {
        return isself;
    }

    public void setIsself(String isself) {
        this.isself = isself;
    }

    public String getIstran3() {
        return istran3;
    }

    public void setIstran3(String istran3) {
        this.istran3 = istran3;
    }

    public String getLowstep() {
        return lowstep;
    }

    public void setLowstep(String lowstep) {
        this.lowstep = lowstep;
    }

    public String getMidbasekv() {
        return midbasekv;
    }

    public void setMidbasekv(String midbasekv) {
        this.midbasekv = midbasekv;
    }

    public String getMidbpaid() {
        return midbpaid;
    }

    public void setMidbpaid(String midbpaid) {
        this.midbpaid = midbpaid;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalstep() {
        return normalstep;
    }

    public void setNormalstep(String normalstep) {
        this.normalstep = normalstep;
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

    public String getProject_b() {
        return project_b;
    }

    public void setProject_b(String project_b) {
        this.project_b = project_b;
    }

    public String getProject_emergencyratio() {
        return project_emergencyratio;
    }

    public void setProject_emergencyratio(String project_emergencyratio) {
        this.project_emergencyratio = project_emergencyratio;
    }

    public String getProject_g() {
        return project_g;
    }

    public void setProject_g(String project_g) {
        this.project_g = project_g;
    }

    public String getProject_i0() {
        return project_i0;
    }

    public void setProject_i0(String project_i0) {
        this.project_i0 = project_i0;
    }

    public String getProject_midphr() {
        return project_midphr;
    }

    public void setProject_midphr(String project_midphr) {
        this.project_midphr = project_midphr;
    }

    public String getProject_midphx() {
        return project_midphx;
    }

    public void setProject_midphx(String project_midphx) {
        this.project_midphx = project_midphx;
    }

    public String getProject_p0() {
        return project_p0;
    }

    public void setProject_p0(String project_p0) {
        this.project_p0 = project_p0;
    }

    public String getProject_pk12() {
        return project_pk12;
    }

    public void setProject_pk12(String project_pk12) {
        this.project_pk12 = project_pk12;
    }

    public String getProject_pk13() {
        return project_pk13;
    }

    public void setProject_pk13(String project_pk13) {
        this.project_pk13 = project_pk13;
    }

    public String getProject_pk23() {
        return project_pk23;
    }

    public void setProject_pk23(String project_pk23) {
        this.project_pk23 = project_pk23;
    }

    public String getProject_startratio() {
        return project_startratio;
    }

    public void setProject_startratio(String project_startratio) {
        this.project_startratio = project_startratio;
    }

    public String getProject_uk12() {
        return project_uk12;
    }

    public void setProject_uk12(String project_uk12) {
        this.project_uk12 = project_uk12;
    }

    public String getProject_uk13() {
        return project_uk13;
    }

    public void setProject_uk13(String project_uk13) {
        this.project_uk13 = project_uk13;
    }

    public String getProject_uk23() {
        return project_uk23;
    }

    public void setProject_uk23(String project_uk23) {
        this.project_uk23 = project_uk23;
    }

    public String getProject_wind1_r() {
        return project_wind1_r;
    }

    public void setProject_wind1_r(String project_wind1_r) {
        this.project_wind1_r = project_wind1_r;
    }

    public String getProject_wind1_r0() {
        return project_wind1_r0;
    }

    public void setProject_wind1_r0(String project_wind1_r0) {
        this.project_wind1_r0 = project_wind1_r0;
    }

    public String getProject_wind1_rateds() {
        return project_wind1_rateds;
    }

    public void setProject_wind1_rateds(String project_wind1_rateds) {
        this.project_wind1_rateds = project_wind1_rateds;
    }

    public String getProject_wind1_ratedvoltage() {
        return project_wind1_ratedvoltage;
    }

    public void setProject_wind1_ratedvoltage(String project_wind1_ratedvoltage) {
        this.project_wind1_ratedvoltage = project_wind1_ratedvoltage;
    }

    public String getProject_wind1_x() {
        return project_wind1_x;
    }

    public void setProject_wind1_x(String project_wind1_x) {
        this.project_wind1_x = project_wind1_x;
    }

    public String getProject_wind1_x0() {
        return project_wind1_x0;
    }

    public void setProject_wind1_x0(String project_wind1_x0) {
        this.project_wind1_x0 = project_wind1_x0;
    }

    public String getProject_wind1_x0_psse() {
        return project_wind1_x0_psse;
    }

    public void setProject_wind1_x0_psse(String project_wind1_x0_psse) {
        this.project_wind1_x0_psse = project_wind1_x0_psse;
    }

    public String getProject_wind1_x_psse() {
        return project_wind1_x_psse;
    }

    public void setProject_wind1_x_psse(String project_wind1_x_psse) {
        this.project_wind1_x_psse = project_wind1_x_psse;
    }

    public String getProject_wind2_r() {
        return project_wind2_r;
    }

    public void setProject_wind2_r(String project_wind2_r) {
        this.project_wind2_r = project_wind2_r;
    }

    public String getProject_wind2_r0() {
        return project_wind2_r0;
    }

    public void setProject_wind2_r0(String project_wind2_r0) {
        this.project_wind2_r0 = project_wind2_r0;
    }

    public String getProject_wind2_rateds() {
        return project_wind2_rateds;
    }

    public void setProject_wind2_rateds(String project_wind2_rateds) {
        this.project_wind2_rateds = project_wind2_rateds;
    }

    public String getProject_wind2_ratedvoltage() {
        return project_wind2_ratedvoltage;
    }

    public void setProject_wind2_ratedvoltage(String project_wind2_ratedvoltage) {
        this.project_wind2_ratedvoltage = project_wind2_ratedvoltage;
    }

    public String getProject_wind2_x() {
        return project_wind2_x;
    }

    public void setProject_wind2_x(String project_wind2_x) {
        this.project_wind2_x = project_wind2_x;
    }

    public String getProject_wind2_x0() {
        return project_wind2_x0;
    }

    public void setProject_wind2_x0(String project_wind2_x0) {
        this.project_wind2_x0 = project_wind2_x0;
    }

    public String getProject_wind2_x0_psse() {
        return project_wind2_x0_psse;
    }

    public void setProject_wind2_x0_psse(String project_wind2_x0_psse) {
        this.project_wind2_x0_psse = project_wind2_x0_psse;
    }

    public String getProject_wind2_x_psse() {
        return project_wind2_x_psse;
    }

    public void setProject_wind2_x_psse(String project_wind2_x_psse) {
        this.project_wind2_x_psse = project_wind2_x_psse;
    }

    public String getProject_wind3_r() {
        return project_wind3_r;
    }

    public void setProject_wind3_r(String project_wind3_r) {
        this.project_wind3_r = project_wind3_r;
    }

    public String getProject_wind3_r0() {
        return project_wind3_r0;
    }

    public void setProject_wind3_r0(String project_wind3_r0) {
        this.project_wind3_r0 = project_wind3_r0;
    }

    public String getProject_wind3_rateds() {
        return project_wind3_rateds;
    }

    public void setProject_wind3_rateds(String project_wind3_rateds) {
        this.project_wind3_rateds = project_wind3_rateds;
    }

    public String getProject_wind3_ratedvoltage() {
        return project_wind3_ratedvoltage;
    }

    public void setProject_wind3_ratedvoltage(String project_wind3_ratedvoltage) {
        this.project_wind3_ratedvoltage = project_wind3_ratedvoltage;
    }

    public String getProject_wind3_x() {
        return project_wind3_x;
    }

    public void setProject_wind3_x(String project_wind3_x) {
        this.project_wind3_x = project_wind3_x;
    }

    public String getProject_wind3_x0() {
        return project_wind3_x0;
    }

    public void setProject_wind3_x0(String project_wind3_x0) {
        this.project_wind3_x0 = project_wind3_x0;
    }

    public String getProject_wind3_x0_psse() {
        return project_wind3_x0_psse;
    }

    public void setProject_wind3_x0_psse(String project_wind3_x0_psse) {
        this.project_wind3_x0_psse = project_wind3_x0_psse;
    }

    public String getProject_wind3_x_psse() {
        return project_wind3_x_psse;
    }

    public void setProject_wind3_x_psse(String project_wind3_x_psse) {
        this.project_wind3_x_psse = project_wind3_x_psse;
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

    public String getStartratio30() {
        return startratio30;
    }

    public void setStartratio30(String startratio30) {
        this.startratio30 = startratio30;
    }

    public String getStartratio40() {
        return startratio40;
    }

    public void setStartratio40(String startratio40) {
        this.startratio40 = startratio40;
    }

    public String getStepvoltage() {
        return stepvoltage;
    }

    public void setStepvoltage(String stepvoltage) {
        this.stepvoltage = stepvoltage;
    }

    public String getSubstationuuid() {
        return substationuuid;
    }

    public void setSubstationuuid(String substationuuid) {
        this.substationuuid = substationuuid;
    }

    public String getTranindex() {
        return tranindex;
    }

    public void setTranindex(String tranindex) {
        this.tranindex = tranindex;
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

    public String getVoltagelevel() {
        return voltagelevel;
    }

    public void setVoltagelevel(String voltagelevel) {
        this.voltagelevel = voltagelevel;
    }

    public String getWind1_busnameuuid() {
        return wind1_busnameuuid;
    }

    public void setWind1_busnameuuid(String wind1_busnameuuid) {
        this.wind1_busnameuuid = wind1_busnameuuid;
    }

    public String getWind2_busnameuuid() {
        return wind2_busnameuuid;
    }

    public void setWind2_busnameuuid(String wind2_busnameuuid) {
        this.wind2_busnameuuid = wind2_busnameuuid;
    }

    public String getWind3_busnameuuid() {
        return wind3_busnameuuid;
    }

    public void setWind3_busnameuuid(String wind3_busnameuuid) {
        this.wind3_busnameuuid = wind3_busnameuuid;
    }

    public String getWindingconnection() {
        return windingconnection;
    }

    public void setWindingconnection(String windingconnection) {
        this.windingconnection = windingconnection;
    }

    public String getFtpattachmentnumbers() {
        return ftpattachmentnumbers;
    }

    public void setFtpattachmentnumbers(String ftpattachmentnumbers) {
        this.ftpattachmentnumbers = ftpattachmentnumbers;
    }
}
