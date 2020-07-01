package jmu.rjc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jmu.rjc.dao.FriendDao;
import jmu.rjc.exception.NotFoundException;
import jmu.rjc.service.IFriendService;
import jmu.rjc.vo.Friends;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendServiceImpl implements IFriendService {

    @Resource(name = "friendDao")
    private FriendDao friendDao;

    @Transactional
    @Override
    public int saveFriend(Friends friends) {
        return friendDao.insert(friends);
    }

    @Transactional
    @Override
    public int updateFriend(Long id, Friends friends) {
        Friends friends1 = friendDao.selectById(id);
        if(friends1 == null){
            throw new NotFoundException("没有该类型");
        }
        BeanUtils.copyProperties(friends,friends1);
        return friendDao.updateById(friends1);
    }

    @Transactional
    @Override
    public int deleteFriendsById(Long id) {
        return friendDao.deleteById(id);
    }

    @Override
    public List<Friends> getFriendsByPage(Page<Friends> page) {
        return friendDao.selectPage(page,null).getRecords();
    }

    @Override
    public Friends getFriendsById(@RequestParam("id") Long id) {
        return friendDao.selectById(id);
    }

    @Override
    public Friends getFriendsByUrl(@RequestParam("url") String url) {
        return friendDao.findFriendsByUrl(url);
    }

    @Override
    public Page<Friends> listFriends(@RequestParam("current") long current, @RequestParam("size") long size) {
        Page<Friends> page = new Page<Friends>(current,size);
        page.setRecords(getFriendsByPage(page));
        return page;
    }
}
