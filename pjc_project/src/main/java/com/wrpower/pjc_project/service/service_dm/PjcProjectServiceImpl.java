package com.wrpower.pjc_project.service.service_dm;

import com.wrpower.pjc_project.entity.PjcProject;
import com.wrpower.pjc_project.service.service_dm.impl.PjcProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//public class PjcProjectServiceImpl{}
@Repository
public class PjcProjectServiceImpl implements PjcProjectService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int savePjcProject(PjcProject project) {

        //初始化属性参数
        String name = "张三";
        Integer age = 12;
        //执行写入
        int row = jdbcTemplate.update("INSERT INTO pm_new_pjc_project ( author,inservicedate,name,onlyversion,outservicedate,owneruuid," +
                "projectsource,projecttype,status,uuid,voltagelevel ) VALUES (?,?);", project.getAuthor(), project.getInservicedate(),
                project.getName(),project.getOnlyversion(),project.getOutservicedate(),project.getOwneruuid(),project.getProjectsource(),project.getProjecttype(),project.getStatus(),
                project.getUuid(),project.getVoltagelevel());
        //返回结果
        return row;
    }

    @Override
    public List<PjcProject> queryAllPjcProject() {
        //SQL
        String sql = "SELECT *  FROM  \"ZJU_WEBPJC\".\"PM_NEW_PJC_PROJECT\" ";
        //结果
        System.out.println("queryAllPjcProject:" + sql);

        //返回结果
        if( jdbcTemplate == null )
        {
            System.out.println("jdbcTemplate == null");
        }
        return jdbcTemplate.query(sql, new RowMapper<PjcProject>() {
            @Override
            public PjcProject mapRow(ResultSet rs, int rowNum) throws SQLException {
                PjcProject project = new PjcProject();
                project.setUuid(rs.getString("UUID"));
                project.setName(rs.getString("NAME"));
                return project;
            }
        });

    }


    @Override
    public int updatePjcProject(final PjcProject project) {
        //SQL
        String sql = "update tudent set name=?,address=? where id=?";
        //结果
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            //映射数据
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getVoltagelevel());
            }
        });
        //返回结果
        return row;
    }

    @Override
    public int deletePjcProject(final Integer id) {

        //SQL+结果
        int resRow = jdbcTemplate.update("delete from pm_new_pjc_project WHERE id=?", new PreparedStatementSetter() {
            //映射数据
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        });
        //返回结果
        return resRow;
    }
}
