package jmu.rjc.web.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.constant.FriendConstants;
import jmu.rjc.service.IFriendService;
import jmu.rjc.vo.Friends;
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
public class FriendController {

    @Resource(name = "friendServiceImpl")
    private IFriendService friendService;

    @GetMapping("/friends")
    public String list(@RequestParam(name = "current",defaultValue = "0") long current, Model model){
        Page<Friends> page = new Page<Friends>(current, FriendConstants.SIZE);
        List<Friends> list = friendService.getFriendsByPage(page);
        page.setRecords(list);
        model.addAttribute("page",page);
        System.out.println(list);
        return "admin/friends";
    }

    @GetMapping("/friends/input")
    public String input(Model model){
        model.addAttribute("friends",new Friends());
        return "admin/friends-input";
    }

    @GetMapping("/friends/{fid}/input")
    public String editFriends(@PathVariable Long fid, Model model){
        model.addAttribute("friends",friendService.getFriendsById(fid));
        return "admin/friends-input";
    }

    @GetMapping("/friends/{fid}/delete")
    public String deleteTag(@PathVariable Long fid, RedirectAttributes attributes){
        friendService.deleteFriendsById(fid);
        attributes.addFlashAttribute("message","删除友链成功");
        return "redirect:/admin/friends";
    }

    @PostMapping("/addFriends")
    public String post(@Validated Friends friends, BindingResult result, RedirectAttributes attributes){
        Friends friends1 = friendService.getFriendsByUrl(friends.getFriendUrl());
        if(friends1!=null){
            result.rejectValue("friend_url","nameError","不能重复添加友链");
        }
        if(result.hasErrors()){
            return "admin/friends-input";
        }
        int flag = friendService.saveFriend(friends);
        if(flag == 0){
            attributes.addFlashAttribute("message","新增友链失败");
        }else{
            attributes.addFlashAttribute("message","新增友链成功");
        }
        return "redirect:/admin/friends";
    }

    @PostMapping("/friends/{fid}")
    public String editPost(@Validated Friends friends,BindingResult result,@PathVariable Long fid,RedirectAttributes attributes){
        Friends friends1 = friendService.getFriendsByUrl(friends.getFriendUrl());
        if(friends1!=null){
            result.rejectValue("friend_url","nameError","不能重复添加友链");
        }
        if(result.hasErrors()){
            return "admin/friends-input";
        }
        int flag = friendService.updateFriend(fid,friends);
        if(flag == 0){
            attributes.addFlashAttribute("message","更新友链失败");
        }else{
            attributes.addFlashAttribute("message","更新友链成功");
        }
        return "redirect:/admin/friends";
    }
}
