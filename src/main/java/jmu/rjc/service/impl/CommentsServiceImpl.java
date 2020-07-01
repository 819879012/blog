package jmu.rjc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jmu.rjc.dao.CommentDao;
import jmu.rjc.service.ICommentService;
import jmu.rjc.vo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

@Service
public class CommentsServiceImpl implements ICommentService {

    @Resource(name = "commentDao")
    private CommentDao commentDao;

    @Override
    public List<Comment> listParentCommentByBlogId(Long bid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>().eq("bid",bid).isNull(true,"parentCommentId");
        List<Comment> list = commentDao.selectList(wrapper);
        Comparator<Comment> comparator = new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o2.getCreateTime().toString().compareTo(o1.getCreateTime().toString());
            }
        };
        Collections.sort(list,comparator);
        return eachComment(list);
    }

    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<Comment>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments) {
            comment.setReplyComments(listReplyCommentByCid(comment.getCid()));
            List<Comment> reply = comment.getReplyComments();
            for (Comment reply1 : reply) {
                reply1.setReplyComments(listReplyCommentByCid(reply1.getCid()));
                reply1.setParentComment(bindParentComment(reply1.getParentCommentId()));
                recursively(reply1);
            }
            comment.setReplyComments(tempReplys);
            //开辟新的缓存集合
            tempReplys = new ArrayList<Comment>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<Comment>();

    /**
     * 递归迭代,返回某个comment所有子回复
     * @param comment
     */
    private void recursively(Comment comment){
        tempReplys.add(comment); //顶节点添加到临时存放集合
        if(comment.getReplyComments()!=null && comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                reply.setParentComment(bindParentComment(reply.getParentCommentId()));
                tempReplys.add(reply);
                if(reply.getReplyComments()!=null&&reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        return commentDao.insert(comment);
    }

    @Override
    public List<Comment> listReplyCommentByCid(Long cid) {
        return commentDao.selectList(new QueryWrapper<Comment>().eq("parentCommentId",cid));
    }

    public Comment bindParentComment(Long parentId){
        return commentDao.selectById(parentId);
    }
}
