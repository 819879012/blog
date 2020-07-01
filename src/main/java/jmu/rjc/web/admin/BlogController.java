package jmu.rjc.web.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.BlogContants;
import jmu.rjc.service.IBlogService;
import jmu.rjc.service.IBlogTagService;
import jmu.rjc.service.ITagService;
import jmu.rjc.service.ITypeService;
import jmu.rjc.vo.Blog;
import jmu.rjc.vo.BlogQuery;
import jmu.rjc.vo.Tag;
import jmu.rjc.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blog-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Resource(name = "blogServiceImpl")
    private IBlogService blogService;

    @Resource(name = "typeServiceImpl")
    private ITypeService typeService;

    @Resource(name = "tagServiceImpl")
    private ITagService tagService;

    @Resource(name = "blogTagServiceImpl")
    private IBlogTagService blogTagService;

    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "0") long current, BlogQuery blog, Model model){
        Page<Blog> page = new Page<Blog>(current, BlogContants.SIZE);
        List<Blog> list = blogService.getBlogByPage(page,blog);
        page.setRecords(list);
        model.addAttribute("page",page);
        model.addAttribute("types",typeService.getAllType());
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(value = "page",defaultValue = "0") long current, BlogQuery blog, Model model){
        Page<Blog> page = new Page<Blog>(current, BlogContants.SIZE);
        List<Blog> list = blogService.getBlogByPage(page,blog);
        page.setRecords(list);
        model.addAttribute("page",page);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog", new Blog());
        setTypeAndTag(model);
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlogById(id);
        blog.init();
        model.addAttribute("blog", blog);
        setTypeAndTag(model);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getTypeByID(blog.getTypeId()));
        blog.setTagList(tagService.listTag(blog.getTagIds()));
        if(blog.getFlag()==null||"".equals(blog.getFlag())){blog.setFlag("原创");}
        int flag = 0;
        if(blog.getBid()==null){
            flag = blogService.saveBlog(blog);
        }else{
            flag = blogService.updateBlog(blog.getBid(),blog);
        }
        blogTagService.deleteBlogTagRelationByBid(blog.getBid());
        for (Tag tag : blog.getTagList()) {
            blogTagService.saveBlogTagRelation(blog.getBid(),tag.getId());
        }
        if(flag == 0){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogTagService.deleteBlogTagRelationByBid(id);
        blogService.deleteBlogById(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}