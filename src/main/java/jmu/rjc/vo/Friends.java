package jmu.rjc.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "friends")
public class Friends implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fid",type = IdType.AUTO)
    private Long fid;

    @NonNull
    @TableField(value = "url")
    private String friendUrl;
}
