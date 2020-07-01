package jmu.rjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jmu.rjc.vo.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;

@Repository
public interface BlogDao extends BaseMapper<Blog> {

    @Select("select date_format(blog.updateTime,'%Y') as year from blog group by year desc;")
    List<String> findGroupYear();

    @Select("select * from blog where DATE_FORMAT(blog.updateTime,'%Y') = #{year}")
    List<Blog> findByYear(@Param("year") String year);

//    @Override
//    @Select("select * from blog where bid=#{bid}")
//    @Results(value = {
//            @Result(property = "uid",column = "uid"),
//            @Result(property = "user",column = "uid",one = @One(select = "jmu.rjc.dao.UserDao.selectById")),
//            @Result(property = "type",column = "type_id",one = @One(select = "jmu.rjc.dao.TypeDao.selectById"))
//    })
//    Blog selectById(Serializable id);

}
