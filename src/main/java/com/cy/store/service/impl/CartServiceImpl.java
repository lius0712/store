package com.cy.store.service.impl;


import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    /**购物车的业务层依赖于购物车的持久层和商品的持久层*/
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        //查询当前要添加的这个购物是否在表中存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if(result == null) { //表示这个商品从未加入到购物车
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());

            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());

            Integer rows = cartMapper.insert(cart);
            if(rows != 1) {
                throw new InsertException("插入数据时产生未知的异常");
            }

        } else { //表示当前商品已经存在，则更新这条数据的num值
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username,new Date());
            if(rows != 1) {
                throw new UpdateException("更新数据时产生异常");
            }
        }
    }

    @Override
    public List<CartVo> getVoByUid(Integer uid) {
        return cartMapper.findVoByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result =  cartMapper.findByCid(cid);
        if(result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if(!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if(rows != 1) {
            throw new UpdateException("更新数据失败");
        }
        return num;
    }

    @Override
    public List<CartVo> getVoByCid(Integer uid, Integer[] cids) {
       List<CartVo> list = cartMapper.findVoByCid(cids);
       Iterator<CartVo> it = list.iterator();
       while (it.hasNext()) {
           CartVo cartVo = it.next();
           if(!cartVo.getUid().equals(uid)) { //表示当前的数据不属于当前的用户
                list.remove(cartVo);
           }
       }
       return list;
    }
}
