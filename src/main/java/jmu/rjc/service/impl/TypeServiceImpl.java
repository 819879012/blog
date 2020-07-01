package jmu.rjc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.TypeConstants;
import jmu.rjc.dao.TypeDao;
import jmu.rjc.exception.NotFoundException;
import jmu.rjc.service.IBlogService;
import jmu.rjc.service.ITypeService;
import jmu.rjc.vo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TypeServiceImpl implements ITypeService {

    @Resource(name = "typeDao")
    private TypeDao typeDao;

    @Resource(name = "blogServiceImpl")
    private IBlogService blogService;

    @Transactional
    @Override
    public List<Type> getTypeByPage(Page<Type> page){
        return typeDao.selectPage(page,null).getRecords();
    }

    @Override
    public List<Type> getAllType() {
        return typeDao.selectList(null);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Comparator<Type> comparator = new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return o2.getBlogList().size()-o1.getBlogList().size();
            }
        };
        List<Type> list = getAllType();
        list.forEach(type -> {type.setBlogList(blogService.getBlogListByTypeId(type.getId()));});
        Collections.sort(list,comparator);
        if(list.size()>size){
            list = list.subList(0,size);
        }
        return list;
    }

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeDao.insert(type);
    }

    @Transactional
    @Override
    public Type getTypeByID(Long id) { return typeDao.selectById(id); }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByName(name);
    }

    @Override
    public IPage<Type> listType(long current,long size) {
        IPage<Type> listType = new Page<Type>(current,size);
        return listType;
    }

    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type t = typeDao.selectById(id);
        if(t == null){
            throw new NotFoundException("不存在该类型!");
        }
        BeanUtils.copyProperties(type,t);
        return typeDao.updateById(t);
    }

    @Transactional
    @Override
    public int deleteTypeByID(Long id) {
        blogService.deleteBlogByTypeId(id);
        return typeDao.deleteById(id);
    }
}
