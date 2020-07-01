package jmu.rjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jmu.rjc.vo.Comment;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends BaseMapper<Comment> {

    @Select("select * from comment where bid=#{bid}")
    List<Comment> getCommentByBid(Long bid);

}
