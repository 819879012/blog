package jmu.rjc.web;

import jmu.rjc.constant.FriendConstants;
import jmu.rjc.service.IFriendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;

@Controller
public class FriendsShowController {

    @Resource(name = "friendServiceImpl")
    private IFriendService friendService;

    @GetMapping("/friends")
    public String friends(@RequestParam(value = "page",defaultValue = "0") long current, Model model){
        model.addAttribute("page",friendService.listFriends(current, FriendConstants.SIZE));
        return "friends";
    }

    @PostMapping("/friendsList")
    public String friendsList(@RequestParam(value = "page",defaultValue = "0") long current, Model model){
        model.addAttribute("page",friendService.listFriends(current, FriendConstants.SIZE));
        return "friends :: friendsList";
    }

}
