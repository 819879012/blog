package jmu.rjc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.vo.Tag;
import java.util.List;

public interface ITagService {

    int saveTag(Tag tag);

    int updateTag(Long id,Tag tag);

    int deleteTagById(Long id);

    List<Tag> getTagByPage(Page<Tag> page);

    Tag getTagById(Long id);

    Tag getTagByName(String name);

    IPage<Tag> listTag(long current,long size);

    List<Tag> getAllTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);
}
