package club.jackzhan.cloudstore.mapper;

import club.jackzhan.cloudstore.module.entities.Permissions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 16:52
 *
 * @Author: JackZhan
 */
public interface PermissionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permissions record);

    int insertSelective(Permissions record);

    Permissions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permissions record);

    int updateByPrimaryKey(Permissions record);

    List<Permissions> getByRoleId(@Param("roleId") Integer roleId);
}
