package jmu.rjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jmu.rjc.vo.Tag;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface TagDao extends BaseMapper<Tag> {

    @Select("select * from tag where name=#{name} limit 1")
    Tag findByName(@PathVariable String name);


}
