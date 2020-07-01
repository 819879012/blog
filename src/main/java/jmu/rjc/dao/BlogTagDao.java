package jmu.rjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jmu.rjc.vo.Blog;
import jmu.rjc.vo.BlogTag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BlogTagDao extends BaseMapper<BlogTag> {

    @Select("select tag_id from blog_tag where blog_tag.bid=#{bid}")
    List<Long> findTagIdByBid(@Param("bid") Long bid);

    @Select("select bid from blog_tag where tag_id=#{tagId}")
    List<Long> getBlogIdByTagId(@Param("tagId") Long tagId);

    @Select("select blog.* from blog,blog_tag,tag where blog.bid=blog_tag.bid and blog_tag.tag_id=tag.tag_id and tag.tag_id=#{id} limit #{current},#{size};")
    List<Blog> getBlogByPage(@Param("id") Long tag_id,@Param("current") long current,@Param("size") long size);

}
