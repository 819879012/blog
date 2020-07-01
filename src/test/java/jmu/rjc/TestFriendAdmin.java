package jmu.rjc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jmu.rjc.service.impl.FriendServiceImpl;
import jmu.rjc.vo.Friends;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestFriendAdmin {

    @Autowired
    FriendServiceImpl friendService;

    @Test
    void testFriend(){
        Page<Friends> page = new Page<Friends>(1,5);
        List<Friends> list = friendService.getFriendsByPage(page);
        System.out.println(list);
    }

}
