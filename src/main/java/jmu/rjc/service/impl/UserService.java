package jmu.rjc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jmu.rjc.dao.UserDao;
import jmu.rjc.service.IUserService;
import jmu.rjc.util.MD5Utils;
import jmu.rjc.vo.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Override
    public User checkUser(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("userName",userName).eq("password", MD5Utils.code(password));
        List<User> list = userDao.selectList(queryWrapper);
        return list==null||list.size()==0?null:list.get(0);
    }

    @Override
    public User getUserById(Long uid) {
        return userDao.selectById(uid);
    }


}
