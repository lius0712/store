package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVo;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 更新购车某件商品的数量
     * @param cid 购物车数据id
     * @param num 更新的数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户id和商品id来查询购物车中的数据
     * @return
     */
    Cart findByUidAndPid(Integer uid, Integer pid);

    List<CartVo> findVoByUid(Integer uid);

    Cart findByCid(Integer cid);

    List<CartVo> findVoByCid(Integer[] cids);

}
