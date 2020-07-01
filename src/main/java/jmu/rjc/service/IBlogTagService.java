package jmu.rjc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.vo.Blog;
import java.util.List;

public interface IBlogTagService {

    int saveBlogTagRelation(Long bid,Long tag_id);

    int updateBlogTagRelation(Long bid,Long tag_id);

    int deleteBlogTagRelationByBid(Long bid);

    int deleteBlogTagRelationByTagId(Long tag_id);

    List<Long> findTagIdByBid(Long bid);

    List<Long> findBidByTagId(Long tag_id);

    List<Blog> getBlogListByTagId(Long tag_id);

    Page<Blog> getBlogPageByTagId(Long tag_id, long current);
}
