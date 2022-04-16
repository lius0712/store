package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**用户模块业务层的实现类*/
@Service //Service注解：将当前类的对象交给Spring来管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        /**通过user参数来获取传递过来的username*/
        String username = user.getUsername();

        /**调用findByUsername()判断用户是否被注册过*/
        User result = userMapper.findByUsername(username);

        /**判断结果集是否为null, 不为null则抛出用户名被占用的异常*/
        if(result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        /**密码加密处理的实现：md5
         * 串 + password + 串 ----- md5算法进行加密，连续加载三次
         * 盐值 + password + 盐值 ----- 盐值就是有一个随机的字符串
         * */
        String oldPassword = user.getPassword();
        /**获取盐值*/
        String salt = UUID.randomUUID().toString().toUpperCase();

        /**补全数据：盐值的记录*/
        user.setSalt(salt);

        /**将密码和盐值作为一个整体进行加密处理*/
        String md5Password = getMD5Password(oldPassword, salt);
        /**补全数据：md5密码*/
        user.setPassword(md5Password);

        /**补全数据， is_delete设置为0， 4个日志信息*/
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);


        /**执行注册业务功能的实现*/
        Integer rows = userMapper.insert(user);
        if(rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }

    }

    @Override
    public User login(String username, String password) {
        //根据用户名来查询用户的数据是否存在
        User result = userMapper.findByUsername(username);
        if(result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测用户的密码是否匹配
        /**
         * 获取数据库中加密之后的密码
         * */
        String oldPassword = result.getPassword();

        String salt = result.getSalt();

        String newMd5Password = getMD5Password(password, salt);

        if(!oldPassword.equals(newMd5Password)) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        //判断is_delete字段的值是否为1标记被删除
        if(result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        //将当前的用户数据返回,为了数据展示使用
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        

        //原始密码和数据库密码进行比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());

        if(!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }

        //将新密码设置到数据库中，将新密码进行加密并更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());

        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if(rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户名未找到");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        //user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if(rows != 1) {
            throw new UpdateException("更新数据时产生异常");
        }

    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if(rows != 1) {
            throw new UpdateException("更新用户头像产生未知的异常");
        }

    }

    private String getMD5Password(String password, String salt) {
        //md5加密算法的调用
        for(int i = 0; i < 3; i++) {
           password = DigestUtils.md5DigestAsHex((salt+password).getBytes()).toUpperCase();
        }
        return password;
    }

}
