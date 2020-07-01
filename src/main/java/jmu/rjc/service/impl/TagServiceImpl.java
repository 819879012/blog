package jmu.rjc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.dao.TagDao;
import jmu.rjc.exception.NotFoundException;
import jmu.rjc.service.IBlogTagService;
import jmu.rjc.service.ITagService;
import jmu.rjc.vo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class TagServiceImpl implements ITagService {

    @Resource(name = "tagDao")
    private TagDao tagDao;

    @Resource(name = "blogTagServiceImpl")
    private IBlogTagService blogTagService;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagDao.insert(tag);
    }

    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag tag1 = tagDao.selectById(id);
        if(tag1==null){
            throw new NotFoundException("没有该类型");
        }
        blogTagService.updateBlogTagRelation(null,id);
        BeanUtils.copyProperties(tag,tag1);
        return tagDao.updateById(tag1);
    }

    @Transactional
    @Override
    public int deleteTagById(Long id) {
        blogTagService.deleteBlogTagRelationByTagId(id);
        return tagDao.deleteById(id);
    }

    @Override
    public List<Tag> getTagByPage(Page<Tag> page) {
        return tagDao.selectPage(page,null).getRecords();
    }

    @Transactional
    @Override
    public Tag getTagById(Long id) {
        return tagDao.selectById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public IPage<Tag> listTag(long current, long size)
    {
        IPage<Tag> page = new Page<Tag>(current,size);
        return page;
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.selectList(null);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagDao.selectBatchIds(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Comparator<Tag> comparator = new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                return o2.getBlogList().size()-o1.getBlogList().size();
            }
        };
        List<Tag> list = getAllTag();
        list.forEach(tag -> {tag.setBlogList(blogTagService.getBlogListByTagId(tag.getId()));});
        if(list.size()>size){
            list = list.subList(0,size);
        }
        return list;
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new LinkedList<Long>();
        if(!StringUtils.isEmpty(ids)){
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }
}
