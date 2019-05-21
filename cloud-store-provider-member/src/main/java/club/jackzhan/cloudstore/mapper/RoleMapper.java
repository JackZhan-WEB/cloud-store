package club.jackzhan.cloudstore.mapper;

import club.jackzhan.cloudstore.module.entities.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 16:52
 *
 * @Author: JackZhan
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getByMemberId(@Param("memberId") String memberId);

    List<Role> getAllRolesByStateAndType(@Param("state")Integer state, @Param("type")Integer type);

}
