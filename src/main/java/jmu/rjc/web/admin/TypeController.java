package jmu.rjc.web.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.TypeConstants;
import jmu.rjc.service.ITypeService;
import jmu.rjc.vo.Type;
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
public class TypeController {

    @Resource(name = "typeServiceImpl")
    private ITypeService typeServiceImpl;

    @GetMapping("/types")
    public String list(@RequestParam(name = "current",defaultValue = "0") long current, Model model){
        Page<Type> page = new Page<Type>(current, TypeConstants.SIZE);
        List<Type> list = typeServiceImpl.getTypeByPage(page);
        page.setRecords(list);
        model.addAttribute("page",page);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeServiceImpl.getTypeByID(id));
        return "admin/type-input";
    }

    @PostMapping("/addTypes")
    public String post(@Validated Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeServiceImpl.getTypeByName(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","不能重复添加分类");
        }
        if(result.hasErrors()){
            return "admin/type-input";
        }
        int flag = typeServiceImpl.saveType(type);
        if(flag == 0){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Validated Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeServiceImpl.getTypeByName(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","不能重复添加分类");
        }
        if(result.hasErrors()){
            return "admin/type-input";
        }
        int flag = typeServiceImpl.updateType(id,type);
        if(flag == 0){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeServiceImpl.deleteTypeByID(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
