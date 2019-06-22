package club.jackzhan.cloudstore.mapper;

import club.jackzhan.cloudstore.entities.Permissions;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 16:52
 *
 * @Author: JackZhan
 */
public interface PermissionsMapper extends BaseMapper<Permissions> {

    List<Permissions> getByRoleIds(@Param("roleIds") String roleIds);

    List<Permissions> list();

    int insertList(@Param("list") List<Permissions> list);

    List<Integer> getCheckList(@Param("type") Integer type);

    List<String> getAllCodes();
}
