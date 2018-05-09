package com.dyangalih.student;

import com.dyangalih.library.Tools;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class StudentJDBCTemplate implements StudentDAO {
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    public void create(String name, Integer age) {
        String sql = Tools.getInstance().loadSql(getClass(), "student_insert.sql");
        jdbcTemplateObject.update( sql, name, age);
        System.out.println("Created Record Name = " + name + " Age = " + age);
    }
    public List<Student> listStudents() {
        String sql = Tools.getInstance().loadSql(getClass(), "student_data.sql");
        return jdbcTemplateObject.query(sql, new StudentMapper());
    }

    @Override
    public void deleteAllData() {
        String sql = Tools.getInstance().loadSql(getClass(),"student_delete.sql");
        jdbcTemplateObject.update(sql);
    }

    @Override
    public void updateDataById(String name, int age, int id) {
        String sql = Tools.getInstance().loadSql(getClass(),"student_update.sql");
        jdbcTemplateObject.update(sql, name, age, id);
    }

    @Override
    public List<Student> getStudent(Integer id) {
        String sql = Tools.getInstance().loadSql(getClass(), "student_data_get_by_id.sql");
        return jdbcTemplateObject.query(sql,new Object[] {id}, new StudentMapper());
    }
}
