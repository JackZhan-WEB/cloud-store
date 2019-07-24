package club.jackzhan.cloudstore.mapper;

import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.entities.Member;
import club.jackzhan.cloudstore.module.dto.member.MemberDTO;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 16:45
 *
 * @Author: JackZhan
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    Member getMember(@Param("loginName") String loginName);

    //TODO 分页有问题，问题：https://www.v2ex.com/t/566096#reply15
    List<MemberDTO> list(@Param("request") MemberQueryRequest request, @Param("offSet") Integer currentPage, @Param("pageSize") Integer pageSize);

    Integer countByQuery(@Param("request") MemberQueryRequest request);

    int insertMemberRole(@Param("memberId") String memberId, @Param("roles") List<RoleDTO> roles);

    int deleteUserRoles(@Param("memberId") String memberId);
}
