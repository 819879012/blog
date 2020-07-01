package jmu.rjc.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.BlogContants;
import jmu.rjc.constant.TagConstants;
import jmu.rjc.constant.TypeConstants;
import jmu.rjc.service.IBlogService;
import jmu.rjc.service.ITagService;
import jmu.rjc.service.ITypeService;
import jmu.rjc.service.IUserService;
import jmu.rjc.vo.Blog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {

    @Resource(name = "blogServiceImpl")
    private IBlogService blogService;

    @Resource(name = "typeServiceImpl")
    private ITypeService typeService;

    @Resource(name = "tagServiceImpl")
    private ITagService tagService;

    @Resource(name = "userService")
    private IUserService userService;

    @GetMapping("/")
    public String index(@RequestParam(name = "current",defaultValue = "0") long current, Model model) {
        Page<Blog> page = new Page<Blog>(current, BlogContants.SIZE);
        List<Blog> list = blogService.getBlogByPage(page,null);
        list.forEach(blog -> blog.setUser(userService.getUserById(blog.getUid())));
        page.setRecords(list);
        model.addAttribute("page",page);
        model.addAttribute("types",typeService.listTypeTop(TypeConstants.SIZE));
        model.addAttribute("tags",tagService.listTagTop(TagConstants.SIZE));
        model.addAttribute("recommendBlogs",blogService.getRecommendBlogTop(BlogContants.RECOMMEND_SIZE));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(value = "current",defaultValue = "0") long current,@RequestParam("query") String query,Model model){
        model.addAttribute("page",blogService.queryBlog(query,current));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvertById(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        List<Blog> list = blogService.getRecommendBlogTop(3);
        model.addAttribute("newblogs",list);
        return "_fragment :: newblogList";
    }

}
