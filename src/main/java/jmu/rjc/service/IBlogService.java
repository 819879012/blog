package jmu.rjc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.vo.Blog;
import jmu.rjc.vo.BlogQuery;
import java.util.List;
import java.util.Map;

public interface IBlogService {

    Blog getBlogById(Long id);

    int saveBlog(Blog blog);

    int updateBlog(Long id,Blog blog);

    void deleteBlogById(Long id);

    List<Blog> getBlogByPage(Page<Blog> page, BlogQuery blog);

    int deleteBlogByTypeId(Long typeId);

    List<Blog> getBlogListByTypeId(Long type_id);

    List<Blog> getBlogListByTagId(Long tag_id);

    List<Blog> getRecommendBlogTop(Integer size);

    Page<Blog> queryBlog(String query,long current);

    Blog getAndConvertById(Long id);

    int updateViews(Long id);

    Map<String,List<Blog>> archiveBlog();

    Long getTotalBlogNum();
}
