package jmu.rjc.service;

import jmu.rjc.vo.Comment;
import java.util.List;

public interface ICommentService {

    List<Comment> listParentCommentByBlogId(Long bid);

    int saveComment(Comment comment);

    List<Comment> listReplyCommentByCid(Long cid);
}
