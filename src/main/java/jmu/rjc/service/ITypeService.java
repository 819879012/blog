package jmu.rjc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.vo.Type;

import java.util.List;

public interface ITypeService {

    int saveType(Type type);

    Type getTypeByID(Long id);

    Type getTypeByName(String name);

    IPage<Type> listType(long current,long size);

    int updateType(Long id,Type type);

    int deleteTypeByID(Long id);

    List<Type> getTypeByPage(Page<Type> page);

    List<Type> getAllType();

    List<Type> listTypeTop(Integer size);
}