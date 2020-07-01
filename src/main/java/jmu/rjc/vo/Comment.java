package jmu.rjc.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cid",type = IdType.AUTO)
    private Long cid;

    @TableField(value = "bid")
    private Long bid;

    @TableField("parentCommentId")
    private Long parentCommentId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "nickName")
    private String nickName;

    @TableField(value = "email")
    private String email;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "adminComment")
    private boolean adminComment;

    @TableField(value = "createTime",fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>(); //一个父评论可以有多个子评论

    @TableField(exist = false)
    private Comment parentComment; //一个子评论只能对应一个父评论

    @TableField(exist = false)
    private Blog blog;

}
