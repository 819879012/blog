package jmu.rjc.service;

import jmu.rjc.vo.User;

public interface IUserService {
    /**
     * 检查用户是否存在
     * @param userName
     * @param password
     * @return
     */
    User checkUser(String userName,String password);

    User getUserById(Long uid);

}
