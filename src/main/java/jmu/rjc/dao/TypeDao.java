package jmu.rjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jmu.rjc.vo.Type;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends BaseMapper<Type> {

    @Select("select name from type where name=#{name} limit 1")
    Type findByName(@Param("name") String name);

}
