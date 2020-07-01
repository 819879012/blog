package jmu.rjc.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.BlogContants;
import jmu.rjc.service.IBlogService;
import jmu.rjc.service.ITypeService;
import jmu.rjc.vo.Blog;
import jmu.rjc.vo.BlogQuery;
import jmu.rjc.vo.Type;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TypeShowController {

    @Resource(name = "typeServiceImpl")
    private ITypeService typeService;

    @Resource(name = "blogServiceImpl")
    private IBlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,@RequestParam(value = "current",defaultValue = "0") long current, Model model){
        List<Type> list = typeService.listTypeTop(10000);
        if(id == -1){
            id = list.get(0).getId();
        }
        list.forEach(type -> type.setBlogList(blogService.getBlogListByTypeId(type.getId())));
        BlogQuery query = new BlogQuery().setTypeId(id);
        model.addAttribute("types",list);
        Page<Blog> blogPage = new Page<Blog>(current, BlogContants.RECOMMEND_SIZE);
        blogPage.setRecords(blogService.getBlogByPage(blogPage,query));
        model.addAttribute("page",blogPage);
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
