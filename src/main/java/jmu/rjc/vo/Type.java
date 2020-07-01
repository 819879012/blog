package jmu.rjc.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "type")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "type_id",type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    @NonNull
    private String name;

    @TableField(exist = false)
    private List<Blog> blogList = new ArrayList<Blog>();
}
