package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//SpringBootTest:表明标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//RunWith:启动这个单元测试类，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {
    //idea有检测功能，接口不能够直接创建Bean的（动态代理技术来解决）
    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试方法：可以独立运行，不用启动整个项目
     * 1、必须被@Test注解修饰
     * 2、返回值类型必须是void
     * 3、方法的参数列表不指定任何类型
     * 4、方法的访问修饰符必须是public
     */
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }


    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(8, "321", "管理员", new Date());
    }
    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(8));
    }
    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(9);
        user.setPhone("1234566");
        user.setEmail("test@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(9,
                "/upload/avatar.png",
                "管理员",
                new Date());
    }
}
