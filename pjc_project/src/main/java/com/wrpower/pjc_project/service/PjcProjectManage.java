package com.wrpower.pjc_project.service;

import com.nari.cloud.fidbaccess.wrapper.FIDBAccess;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.fidbaccess.core.FIDBAccessImpl;
import com.nari.cloud.fidbaccess.model.*;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;

import com.wrpower.pjc_project.domain.PjcProject;
import org.springframework.stereotype.Service;

@Service
public class PjcProjectManage {

    static private FIDBAccess m_fidbAccess = new FIDBAccessImpl();
    {
        m_fidbAccess.setProxyIp("10.33.3.31");
    }


    private Map<String,String> projectMap;
    private Map<String,String> ownerMap = OwnerManage.getOwnerMap();

    public PjcProjectManage() {
        projectMap = getProjectMap();

    }

    //获取所有投运工程--projectMap
    private Map<String,String> getProjectMap(){
        Map<String,String> projectMap = new HashMap<>();
        try {
            List<FutureProjectInfo> list = m_fidbAccess.queryForFutureProjectList();
            for(int i =0;i<list.size();i++){
                String name = list.get(i).getProject_name();
                String id = list.get(i).getProject_id();
                projectMap.put(name,id);
            }
        } catch (NRDataAccessException e){
            System.out.println("queryForFutureProjectList发生错误:" + e.getMessage());
            projectMap.put("error","queryForFutureProjectList发生错误:" + e.getMessage());
        }
        return projectMap;
    }

    //获得所有工程属性列表
    public List<PjcProject> getFutureProjectList() {
        List<PjcProject> projectList = new ArrayList<>();
        try {
            List<FutureProjectInfo> list = m_fidbAccess.queryForFutureProjectList();
            for( int i =0; i < list.size() ;i++){
                PjcProject myProjectInfo = futureProToMyPro(list.get(i));
                projectList.add(myProjectInfo);
            }
        } catch (NRDataAccessException e){
            System.out.println("queryForFutureProjectList发生错误:" + e.getMessage());
        }
        return projectList;
    }

    //根据工程id，获得某个工程属性
    public PjcProject getFutureProject(String projectId) {
        PjcProject pjcProject = new PjcProject();
        try {
            FutureProjectInfo futureProjectInfo = m_fidbAccess.queryForFutureProject(projectId);
            pjcProject=futureProToMyPro(futureProjectInfo);
        } catch (NRDataAccessException e){
            System.out.println("queryForFutureProject发生错误:" + e.getMessage());
        }
        return pjcProject;
    }


    //批量新建投运工程，成功返回新建工程id列表
    public List<String> insertFutureProject (List<PjcProject> myProList){
        List<String> list =new ArrayList<>();
        List<FutureProjectInfo> futureProList = new ArrayList<>();
        for (int i=0; i < myProList.size(); i++ ){
            String projectName = myProList.get(i).getName();
            if( !projectMap.get(projectName).isEmpty())
                continue;
            FutureProjectInfo futureProjectInfo = myProToFuturePro(myProList.get(i));
            futureProList.add(futureProjectInfo);
        }
        try {
            List<FuncReturn> result = m_fidbAccess.InsertFutureProject(futureProList);
            for (int i =0; i < result.size(); i++ ){
                String projectId = result.get(i).getProjectId();
                list.add(projectId);
            }
        } catch (NRDataAccessException e){
            System.out.println("InsertFutureProject发生错误:" + e.getMessage());
        }
        return list;
    }


    //批量更新投运工程属性，成功返回工程id列表
    // 工程一旦创建只能修改以下属性：
    // 1. 工程名称 project_name
    // 2. 未来时段 forecast_start_time,forecast_end_time
    // 3. 描述信息 desc
    // 4. 用户信息 createUserInfo
    // 5. 状态标志 status_flag
    public List<String> updateFutureProject (List<PjcProject> myProList){
        List<String> list =new ArrayList<>();
        List<FutureProjectUpdateInfo> updateProList = new ArrayList<>();
        for (int i=0; i < myProList.size(); i++ ){
            FutureProjectUpdateInfo futureProjectUpdateInfo = new FutureProjectUpdateInfo();
            futureProjectUpdateInfo.setProject_id(myProList.get(i).getUuid());
//            futureProjectUpdateInfo.setProject_name(myProList.get(i).getName());
            futureProjectUpdateInfo.setForecast_start_time(shortToDate(myProList.get(i).getInserviceDate()));
            futureProjectUpdateInfo.setForecast_end_time(shortToDate(myProList.get(i).getOutserviceDate()));
//            futureProjectUpdateInfo.setStatus_flag(Integer.parseInt(myProList.get(i).getStatus()));
            updateProList.add(futureProjectUpdateInfo);
        }
        try {
            List<FuncReturn> result = m_fidbAccess.UpdateFutureProject(updateProList);
            for (int i=0; i < result.size(); i++ ){
                String projectId=result.get(i).getProjectId();
                list.add(projectId);
            }
        } catch (NRDataAccessException e){
            System.out.println("UpdateFutureProject发生错误:" + e.getMessage());
        }
        return list;
    }

    //批量删除投运工程，成功返回retCode列表
    public List<Boolean> deleteFutureProject (List<String> projectIdList){
        List<Boolean> list =new ArrayList();
        try {
            List<FuncReturn> result = m_fidbAccess.DeleteFutureProject(projectIdList, MgDefineHeader.MG_PROJECT_DELETE);
            for (int i=0; i < result.size(); i++ ){
                boolean retCode=result.get(i).isRetCode();
                list.add(retCode);
            }
        } catch (NRDataAccessException e){
            System.out.println("UpdateFutureProject发生错误:" + e.getMessage());
        }
        return list;
    }

    //从FutureProjectInfo映射到MyProjectInfo
    private PjcProject futureProToMyPro(FutureProjectInfo futureProjectInfo){
        PjcProject myProjectInfo=new PjcProject();
        myProjectInfo.setAuthor(futureProjectInfo.getCreateUserInfo().getUserName());
        myProjectInfo.setInserviceDate(dateToShort(futureProjectInfo.getForecast_start_time()));
        myProjectInfo.setName(futureProjectInfo.getProject_name());
        myProjectInfo.setOutserviceDate(dateToShort(futureProjectInfo.getForecast_end_time()));
        myProjectInfo.setOwner(futureProjectInfo.getArea_code());//如：宁波地调
        myProjectInfo.setOwnerUuid(ownerMap.get(futureProjectInfo.getArea_code()));
        myProjectInfo.setProjectType(futureProjectInfo.getProject_type());
        myProjectInfo.setStatus(Integer.toString(futureProjectInfo.getStatus_flag()));
        myProjectInfo.setUuid(futureProjectInfo.getProject_id());//如：PROJECT_F_1
        myProjectInfo.setIs_reform(futureProjectInfo.getIs_reform());// 0为新投厂站  1为对现有站进行改造
        return myProjectInfo;
    }

    //从MyProjectInfo映射到FutureProjectInfo
    private FutureProjectInfo myProToFuturePro(PjcProject myProjectInfo){
        FutureProjectInfo futureProjectInfo = new FutureProjectInfo();
        CreateUserInfo createUserInfo = new CreateUserInfo();
        createUserInfo.setUserName("cyl_test");
        futureProjectInfo.setCreateUserInfo(createUserInfo);
        futureProjectInfo.setForecast_start_time(shortToDate(myProjectInfo.getInserviceDate()));
        futureProjectInfo.setProject_name(myProjectInfo.getName());
        futureProjectInfo.setForecast_end_time(shortToDate(myProjectInfo.getOutserviceDate()));
        futureProjectInfo.setArea_code(myProjectInfo.getOwnerUuid());
        futureProjectInfo.setProject_type(MgDefineHeader.MG_PROJECT_FUTURE);
        futureProjectInfo.setIs_reform(MgDefineHeader.MG_PROJECT_FUTURE_NEW);//???
//        futureProjectInfo.setStatus_flag(Integer.parseInt(myProjectInfo.getStatus()));
        return futureProjectInfo;
    }

    //"2018-12-04" => Date
    private Date shortToDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try{
            date = sdf.parse(time);
        }catch (ParseException e){
            System.out.println("日期转换发生错误:" + e.getMessage());
        }
        return date;
    }

    //Date => "2018-12-04"
    private String dateToShort(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        return time;
    }
}
