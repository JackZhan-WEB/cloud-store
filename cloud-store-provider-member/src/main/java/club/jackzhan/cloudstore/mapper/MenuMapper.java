package club.jackzhan.cloudstore.mapper;
import feign.Param;
import java.util.List;

import club.jackzhan.cloudstore.entities.Menu;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-29 17:19
 *
 * @Author: JackZhan
 */
public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getByPermissionIds(@Param("permissionIds")String permissionIds);


}
