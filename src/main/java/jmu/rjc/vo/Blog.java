package jmu.rjc.vo;

import com.baomidou.mybatisplus.annotation.*;
import jmu.rjc.service.IBlogService;
import jmu.rjc.service.impl.BlogServiceImpl;
import jmu.rjc.util.SpringUtil;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "bid",type = IdType.AUTO)
    private Long bid;

    @TableField(value = "type_id")
    private Long typeId;

    @TableField(value = "uid")
    private Long uid;

    @TableField(value = "title")
    private String title; //标题

    @TableField(value = "content",jdbcType = JdbcType.BLOB)
    private String content; //内容

    @TableField(value = "description")
    private String description; //描述

    @TableField(value = "firstPicture")
    private String firstPicture; //首图

    @TableField(value = "flag")
    private String flag; //标记

    @TableField(value = "views")
    private Integer views; //浏览次数

    @TableField(value = "appreciation")
    private boolean appreciation; //赞赏开启

    @TableField(value = "shareStatement")
    private boolean shareStatement; //版权开启

    @TableField(value = "commentabled")
    private boolean commentabled; //评论开启

    @TableField(value = "published")
    private boolean published; //发布

    @TableField(value = "recommend")
    private boolean recommend; //推荐

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime; //创建时间

    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private Date updateTime; //更新时间

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Type type;

    @TableField(exist = false)
    private List<Tag> tagList = new ArrayList<Tag>();

    @TableField(exist = false)
    private String tagIds;

    @TableField(exist = false)
    private List<Comment> commentList = new ArrayList<Comment>();

    public void init(){
        SpringUtil.getBean(BlogServiceImpl.class).bindProperties(this);
        this.tagIds = tagsToIds(getTagList());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if(flag){
                    ids.append(",");
                }else{
                    flag=true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }
}
