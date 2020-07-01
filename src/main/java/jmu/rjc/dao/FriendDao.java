package jmu.rjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jmu.rjc.vo.Friends;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDao extends BaseMapper<Friends> {

    @Select("select * from friends where url=#{url} limit 1")
    Friends findFriendsByUrl(@Param("url") String url);
}
