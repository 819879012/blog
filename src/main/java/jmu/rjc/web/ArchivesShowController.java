package jmu.rjc.web;

import jmu.rjc.service.IBlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.annotation.Resource;

@Controller
public class ArchivesShowController {

    @Resource(name = "blogServiceImpl")
    private IBlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.getTotalBlogNum());
        return "archives";
    }
}
