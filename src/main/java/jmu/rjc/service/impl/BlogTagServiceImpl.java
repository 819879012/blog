package jmu.rjc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.BlogContants;
import jmu.rjc.dao.BlogDao;
import jmu.rjc.dao.BlogTagDao;
import jmu.rjc.service.IBlogService;
import jmu.rjc.service.IBlogTagService;
import jmu.rjc.vo.Blog;
import jmu.rjc.vo.BlogTag;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogTagServiceImpl implements IBlogTagService {
    
    @Resource(name = "blogTagDao")
    private BlogTagDao blogTagDao;

    @Resource(name = "blogDao")
    private BlogDao blogDao;

    @Resource(name = "blogServiceImpl")
    private BlogServiceImpl blogService;

    @Override
    public int saveBlogTagRelation(Long bid, Long tag_id) {
        return blogTagDao.insert(new BlogTag(bid,tag_id));
    }

    @Override
    public int updateBlogTagRelation(Long bid, Long tag_id) {
        QueryWrapper<BlogTag> wrapper = new QueryWrapper<BlogTag>();
        if(bid!=null){
            wrapper.eq("bid",bid);
        }
        if(tag_id!=null){
            wrapper.eq("tag_id",tag_id);
        }
        return blogTagDao.update(new BlogTag(bid,tag_id),wrapper);
    }

    @Override
    public int deleteBlogTagRelationByBid(Long bid) {
        QueryWrapper<BlogTag> wrapper = new QueryWrapper<BlogTag>();
        if(bid!=null){
            wrapper.eq("bid",bid);
        }
        return blogTagDao.delete(wrapper);
    }

    @Override
    public int deleteBlogTagRelationByTagId(Long tag_id) {
        QueryWrapper<BlogTag> wrapper = new QueryWrapper<BlogTag>();
        if(tag_id!=null){
            wrapper.eq("tag_id",tag_id);
        }
        return blogTagDao.delete(wrapper);
    }

    @Override
    public List<Long> findTagIdByBid(Long bid) {
        return blogTagDao.findTagIdByBid(bid);
    }

    @Override
    public List<Long> findBidByTagId(Long tag_id) {
        return blogTagDao.getBlogIdByTagId(tag_id);
    }

    @Override
    public List<Blog> getBlogListByTagId(Long tag_id) {
        List<Long> list = findBidByTagId(tag_id);
        if(list.size()==0){
            return new ArrayList<Blog>();
        }
        return blogDao.selectBatchIds(list);
    }

    @Override
    public Page<Blog> getBlogPageByTagId(Long tag_id, long current) {
        Page<Blog> page = new Page<Blog>(current, BlogContants.RECOMMEND_SIZE);
        List<Blog> list = blogTagDao.getBlogByPage(tag_id,current,BlogContants.RECOMMEND_SIZE);
        list.forEach(blog -> blogService.bindProperties(blog));
        page.setTotal(blogTagDao.getBlogIdByTagId(tag_id).size());
        page.setRecords(list);
        return page;
    }

}
