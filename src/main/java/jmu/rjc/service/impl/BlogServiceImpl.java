package jmu.rjc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.BlogContants;
import jmu.rjc.dao.*;
import jmu.rjc.exception.NotFoundException;
import jmu.rjc.service.IBlogService;
import jmu.rjc.service.IBlogTagService;
import jmu.rjc.service.ITypeService;
import jmu.rjc.service.IUserService;
import jmu.rjc.util.MarkdownUtils;
import jmu.rjc.vo.Blog;
import jmu.rjc.vo.BlogQuery;
import jmu.rjc.vo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

@Service
public class BlogServiceImpl implements IBlogService {

    @Resource(name = "blogDao")
    private BlogDao blogDao;

    @Resource(name = "typeServiceImpl")
    private ITypeService typeService;

    @Resource(name = "blogTagServiceImpl")
    private IBlogTagService blogTagService;

    @Resource(name = "tagDao")
    private TagDao tagDao;

    @Resource(name = "commentDao")
    private CommentDao commentDao;

    @Resource(name = "userService")
    private IUserService userService;

    @Override
    public Blog getBlogById(Long id) {
        return blogDao.selectById(id);
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        if(blog.getBid()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogDao.insert(blog);
    }

    @Transactional
    @Override
    public int updateBlog(Long id, Blog blog) {
        Blog b = blogDao.selectById(id);
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        b.init();
        BeanUtils.copyProperties(blog,b);
        return blogDao.updateById(b);
    }

    @Transactional
    @Override
    public void deleteBlogById(Long id) {
        blogTagService.deleteBlogTagRelationByBid(id);
        blogDao.deleteById(id);
    }

    @Override
    public List<Blog> getBlogListByTypeId(Long type_id){
        QueryWrapper<Blog> wrapper = new QueryWrapper<Blog>().eq("type_id",type_id);
        return blogDao.selectList(wrapper);
    }

    @Override
    public List<Blog> getBlogListByTagId(Long tag_id) {
        return null;
    }

    @Override
    public List<Blog> getRecommendBlogTop(Integer size) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<Blog>().eq("recommend",true);
        List<Blog> list = blogDao.selectList(wrapper);
        Comparator<Blog> comparator = new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                return o2.getCreateTime().toString().compareTo(o1.getCreateTime().toString());
            }
        };
        Collections.sort(list,comparator);
        if(list.size()>size){
            list = list.subList(0,size);
        }
        return list;
    }

    @Override
    public Page<Blog> queryBlog(String query,long current) {
        Page<Blog> page = new Page<Blog>(current, BlogContants.SIZE);
        QueryWrapper<Blog> wrapper = new QueryWrapper<Blog>().like("title",query).or().like("content",query);
        List<Blog> list = blogDao.selectList(wrapper);
        list.forEach(blog -> bindProperties(blog));
        page.setRecords(list);
        return page;
    }

    @Transactional
    @Override
    public Blog getAndConvertById(Long id) {
        Blog blog = blogDao.selectById(id);
        if(blog == null){
            throw new NotFoundException("博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        bindProperties(b);
        updateViews(b.getBid());
        return b;
    }

    @Override
    public int updateViews(Long id) {
        Blog b = blogDao.selectById(id);
        b.setViews(b.getViews()+1);
        return blogDao.updateById(b);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<String, List<Blog>>();
        for (String year : years) {
            List<Blog> list = blogDao.findByYear(year);
            list.forEach(blog -> bindProperties(blog));
            map.put(year,list);
        }
        return map;
    }

    @Override
    public Long getTotalBlogNum() {
        return new Long(blogDao.selectCount(null));
    }

    @Override
    public List<Blog> getBlogByPage(Page<Blog> page, BlogQuery blog) {
        QueryWrapper<Blog> wrapper = null;
        if(blog!=null){
            wrapper = new QueryWrapper<Blog>();
            if(blog.getTitle()!=null && !"".equals(blog.getTitle())){
                wrapper.like("title",blog.getTitle());
            }
            if(blog.getTypeId()!=null){
                wrapper.eq("type_id",blog.getTypeId());
            }
            if(blog.isRecommend()){
                wrapper.eq("recommend",blog.isRecommend());
            }
        }
        List<Blog> list = blogDao.selectPage(page,wrapper).getRecords();
        list.forEach(blog1 -> bindProperties(blog1));
        return list;
    }

    @Override
    public int deleteBlogByTypeId(Long typeId) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<Blog>().eq("type_id",typeId);
        return blogDao.delete(wrapper);
    }

    public void bindProperties(Blog blog){
        bindUser(blog);
        bindType(blog);
        bindTagList(blog);
        bindCommentList(blog);
    }

    public void bindType(Blog blog){
        blog.setType(typeService.getTypeByID(blog.getTypeId()));
    }

    public void bindTagList(Blog blog){
        List<Long> tagIdList = blogTagService.findTagIdByBid(blog.getBid());
        List<Tag> tagList = new LinkedList<Tag>();
        tagIdList.forEach(id -> tagList.add(tagDao.selectById(id)));
        blog.setTagList(tagList);
    }

    public void bindCommentList(Blog blog){
        blog.setCommentList(commentDao.getCommentByBid(blog.getBid()));
    }

    public void bindUser(Blog blog){
        if(blog.getUid()!=null){
            blog.setUser(userService.getUserById(blog.getUid()));
        }
    }

}
