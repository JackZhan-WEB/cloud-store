package club.jackzhan.cloudstore.mapper;

import club.jackzhan.cloudstore.entities.Dept;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: JackZhan
 * Date: 2019-03-21
 * Time: 16:07
 */
//@Mapper
public interface DeptDao {
    @Insert("INSERT INTO dept(dname,db_source) VALUES(#{dname},DATABASE());")
    public boolean addDept(Dept dept);

    @Select("select deptno,dname,db_source from dept where deptno=#{id};")
    public Dept findById(Long id);

    @Select("select deptno,dname,db_source from dept;")
    public List<Dept> findAll();
}
