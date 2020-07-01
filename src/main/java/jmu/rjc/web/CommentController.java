package jmu.rjc.web;

import jmu.rjc.service.ICommentService;
import jmu.rjc.vo.Comment;
import jmu.rjc.vo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Resource(name = "commentsServiceImpl")
    private ICommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{bid}")
    public String comments(@PathVariable Long bid, Model model){
        model.addAttribute("comments",commentService.listParentCommentByBlogId(bid));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            comment.setAvatar(avatar);
        }
        comment.setCreateTime(new Date());
        commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBid();
    }
}
