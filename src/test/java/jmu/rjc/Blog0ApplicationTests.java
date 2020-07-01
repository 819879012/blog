package jmu.rjc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.BlogContants;
import jmu.rjc.dao.BlogDao;
import jmu.rjc.dao.BlogTagDao;
import jmu.rjc.service.*;
import jmu.rjc.vo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Blog0ApplicationTests {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IBlogTagService blogTagService;

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private BlogTagDao blogTagDao;

    @Test
    void testBlog(){
//        Page<Blog> page = new Page<Blog>(0,3);
//        List<Blog> list = blogService.getBlogByPage(page,new BlogQuery("季节",new Long(2),false));
//        System.out.println(list.size());
//        for (Blog blog : list) {
//            System.out.println(blog);
//        }
        Blog blog = blogService.getBlogById(new Long(1));
        blog.init();
        System.out.println(blog);
    }

    @Test
    void contextLoads() {
        System.out.println(commentService.listParentCommentByBlogId(new Long(10)));
    }

    @Test
    void typePage(){
        System.out.println(typeService.listType(0,10).getRecords());
    }

    @Test
    void blogQuery(){
        Long tag_id = new Long(3);
        long current = 3;
        Page<Blog> page = blogTagService.getBlogPageByTagId(tag_id,0);
        List<Blog> list = blogTagDao.getBlogByPage(tag_id,current, BlogContants.RECOMMEND_SIZE);
        page.setTotal(blogTagDao.getBlogIdByTagId(tag_id).size());
        System.out.println("pages = "+page.getPages());
        System.out.println("size = "+page.getSize());
        System.out.println("total = "+page.getTotal());
    }

}
