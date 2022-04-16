package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    //在添加用户的收货地址的业务层依赖于IDistrictService的业务层接口
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //调用收货地址统计的方法
        Integer count = addressMapper.countByUid(uid);
        if(count >= maxCount) {
            throw new AddressCoutLimitException("用户收货地址超出上限");
        }

        //省、市、区相关数据
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //uid、isDefault
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);

        //日志补全
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        Integer rows = addressMapper.insert(address);
        if(rows != 1) {
            throw new InsertException("插入用户收货地址产生未知的异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for(Address d : list) {
            //d.setAid(null);
            //d.setUid(null);
            d.setProvinceCode(null);
            d.setCityCode(null);
            d.setAreaCode(null);
            d.setZip(null);
            d.setCreatedTime(null);
            d.setModifiedTime(null);
            d.setCreatedUser(null);
            d.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if(result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        //检测当前获取到的收货地址数据的归属
        if(!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        //先将所有的收货地址设为默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if(rows < 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
        //将用户选中某条地址设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if(rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }

    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if(result == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        if(!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.deleteByAid(aid);
        if(rows != 1) {
            throw new DeleteException("删除数据产生未知的异常");
        }
        Integer count = addressMapper.countByUid(uid);
        if(count == 0 || result.getIsDefault() == 0) {
            return;
        }

        Address address = addressMapper.findLastModified(uid);
        //将这条数据的is_default设置为1
        rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
        if(rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if(address == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        if(!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setModifiedUser(null);
        address.setCreatedTime(null);
        address.setModifiedTime(null);
        return address;
    }
}
