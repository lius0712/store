package com.cy.store.service;

import com.cy.store.entity.User;

/**用户模块业务层接口*/
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @param username 用户名
     * @param password 用户密码
     * @return 返回User类
     */
    User login(String username, String password);

    /**
     * 修改用户密码
     * @param uid 用户id
     * @param username 用户名称
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid,
                       String username,
                       String oldPassword,
                       String newPassword);

    /**
     * 根据用户的id查询用户的数据
     * @param uid 用户id
     * @return 用户数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户数据
     * @param uid 用户id
     * @param username 用户名称
     * @param user 用户对象的数据
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 更新用户的头像
     * @param uid 用户id
     * @param avatar 用户头像路径
     * @param name 用户名称
     */
    void changeAvatar(Integer uid, String avatar, String username);

}
