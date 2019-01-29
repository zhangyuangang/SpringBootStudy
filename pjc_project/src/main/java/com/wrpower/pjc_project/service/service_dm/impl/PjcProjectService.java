package com.wrpower.pjc_project.service.service_dm.impl;
import com.wrpower.pjc_project.entity.PjcProject;

import java.util.List;

public interface PjcProjectService {
    //写入数据
    int savePjcProject(PjcProject project);

    //查询数据
    List<PjcProject> queryAllPjcProject();

    //更新数据
    int updatePjcProject(PjcProject project);

    //删除数据
    int deletePjcProject(Integer id);
}
