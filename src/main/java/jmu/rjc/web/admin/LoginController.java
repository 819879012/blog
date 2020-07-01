package jmu.rjc.web.admin;

import jmu.rjc.service.IUserService;
import jmu.rjc.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Resource(name = "userService")
    private IUserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(name = "userName") String userName,
                        @RequestParam(name = "password") String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes){
        User user = userService.checkUser(userName,password);
        if(user!=null){
            user.setPassword(null); //把密码设置为空,不传递到前端.保证安全性
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            redirectAttributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
