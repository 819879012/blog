package jmu.rjc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.vo.Friends;
import java.util.List;

public interface IFriendService {

    int saveFriend(Friends friends);

    int updateFriend(Long id,Friends friends);

    int deleteFriendsById(Long id);

    List<Friends> getFriendsByPage(Page<Friends> page);

    Friends getFriendsById(Long id);

    Friends getFriendsByUrl(String name);

    Page<Friends> listFriends(long current, long size);

}
