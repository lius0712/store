package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**收货地址持久层的接口*/
public interface AddressMapper {
    /**
     * 插入用户的收货地址信息
     * @param address 地址
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址数量
     * @param uid 用户id
     * @return 当前用户的地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户id查询用户的收货地址数据
     * @param uid 用户id
     * @return 收货地址数据
     */
    List<Address> findByUid(Integer uid);


    /**
     * 根据aid查询收货地址数据
     * @param aid 收货地址id
     * @return 收货地址，没有返回null
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户uid来修改用户的收货地址设置为非默认
     * @param uid 用户id
     * @return
     */
    Integer updateNonDefault(Integer uid);


    Integer updateDefaultByAid(@Param("aid") Integer aid,
                               @Param("modifiedUser") String modifiedUser,
                               @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据收货id删除收货地址数据
     * @param aid 收货地址id
     * @return
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户uid查询当前用户最后一次被修改的收货地址数据
     * @param uid
     * @return
     */
    Address findLastModified(Integer uid);
}
