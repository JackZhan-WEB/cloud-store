package club.jackzhan.cloudstore.mapper;

import club.jackzhan.cloudstore.module.entities.Member;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 16:45
 *
 * @Author: JackZhan
 */
public interface MemberMapper {
    int deleteByPrimaryKey(String id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    Member getMember(@Param("loginName") String loginName);

    //TODO 分页有问题，问题：https://www.v2ex.com/t/566096#reply15
    List<Member> list(@Param("request") MemberQueryRequest request, @Param("offSet")Integer currentPage, @Param("pageSize")Integer pageSize);

    Integer countByQuery(@Param("request") MemberQueryRequest request);
}
