package club.jackzhan.cloudstore.mapper;

import club.jackzhan.cloudstore.entities.Role;
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
    List<Role> getByMemberId(@Param("memberId") String memberId);

    List<Role> getAllRolesByStateAndType(@Param("state") Integer state, @Param("type") Integer type);
}
