package jmu.rjc.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
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
@TableName(value = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.AUTO)
    private Long id;

    @TableField(value = "nickName") //昵称
    private String nickName;

    @TableField(value = "userName") //用户名
    private String userName;

    @TableField(value = "password") //密码
    private String password;

    @TableField(value = "email") //邮箱
    private String email;

    @TableField(value = "avatar") //头像
    private String avatar;

    @TableField(value = "type") //类型
    private Integer type;

    @TableField(value = "createTime") //创建时间
    private Date createTime;

    @TableField(value = "updateTime") //更新时间
    private Date updateTime;

    @TableField(select = false)
    private List<Blog> blogList = new ArrayList<>();

    @TableField(select = false)
    private List<Friends> friendsList = new ArrayList<>();
}
