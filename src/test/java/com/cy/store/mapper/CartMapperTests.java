package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//SpringBootTest:表明标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//RunWith:启动这个单元测试类，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setPid(10000001);
        cart.setNum(2);
        cart.setUid(9);
        cart.setPrice(1000L);
        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid() {
        cartMapper.updateNumByCid(1, 4,"管理员", new Date());
    }
    @Test
    public void findByUidAndPid() {
        Cart cart = cartMapper.findByUidAndPid(9, 10000001);
        System.err.println(cart);
    }

    @Test
    public void findVoByUid() {
        System.out.println(cartMapper.findVoByUid(9));
    }

    @Test
    public void findByCid() {
        System.out.println(cartMapper.findByCid(1));
    }
    @Test
    public void findByVoCid() {
        Integer[] cids = {1,2,3,4,6,7,8};
        System.err.println(cartMapper.findVoByCid(cids));
    }
}
