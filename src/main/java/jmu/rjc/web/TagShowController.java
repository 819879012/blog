package jmu.rjc.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.service.IBlogTagService;
import jmu.rjc.service.ITagService;
import jmu.rjc.vo.Blog;
import jmu.rjc.vo.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import java.util.List;

@Controller
public class TagShowController {

    @Resource(name = "tagServiceImpl")
    private ITagService tagService;

    @Resource(name = "blogTagServiceImpl")
    private IBlogTagService blogTagService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id, @RequestParam(value = "current",defaultValue = "0") long current, Model model){
        List<Tag> tagList = tagService.listTagTop(10000);
        if(id==-1){
            id = tagList.get(0).getId();
        }
        tagList.forEach(tag -> tag.setBlogList(blogTagService.getBlogListByTagId(tag.getId())));
        Page<Blog> page = blogTagService.getBlogPageByTagId(id,current);
        model.addAttribute("page",page);
        model.addAttribute("tags",tagList);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
