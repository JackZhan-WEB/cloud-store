package club.jackzhan.cloudstore.mapper;

import club.jackzhan.cloudstore.entities.Role;
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
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getByMemberId(@Param("memberId") String memberId);

    List<Role> getAllRolesByStateAndType(@Param("state") Integer state, @Param("type") Integer type);

    Integer insertRolePerms(@Param("id") Integer id, @Param("perms") Integer[] perms);

    Integer deleteMemberRole(List<Integer> ids);

    Integer deleteRolePermission(List<Integer> ids);

    Integer updateRolePerms(@Param("id") Integer id, @Param("permChecks") Integer[] permChecks);
}
