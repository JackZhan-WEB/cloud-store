package club.jackzhan.cloudstore.mapper;

import java.util.List;
import java.util.Date;

import club.jackzhan.cloudstore.module.entities.Member;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.util.PageBean;
import org.apache.ibatis.annotations.Param;

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

    List<Member> list(@Param("request")MemberQueryRequest request, PageBean page);


}
