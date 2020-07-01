package jmu.rjc.web.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.FriendConstants;
import jmu.rjc.service.impl.TagServiceImpl;
import jmu.rjc.vo.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Resource(name = "tagServiceImpl")
    private TagServiceImpl tagService;

    @GetMapping("/tags")
    public String list(@RequestParam(name = "current",defaultValue = "0") long current, Model model){
        Page<Tag> page = new Page<Tag>(current, FriendConstants.SIZE);
        List<Tag> list = tagService.getTagByPage(page);
        page.setRecords(list);
        model.addAttribute("page",page);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTagById(id));
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTagById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

    @PostMapping("/addTags")
    public String post(@Validated Tag tag, BindingResult result,RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","nameError","不能重复添加标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        int flag = tagService.saveTag(tag);
        if(flag == 0){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Validated Tag tag,BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","nameError","不能重复添加标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        int flag = tagService.updateTag(id,tag);
        if(flag == 0){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

}
