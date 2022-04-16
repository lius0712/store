package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//RunWith:启动这个单元测试类，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setPhone("123456");
        address.setName("女朋友");
        addressService.addNewAddress(10,"管理员", address);
    }

    @Test
    public void setDefault() {
        addressService.setDefault(5, 9, "管理员");
    }

    @Test
    public void delete() {
        addressService.delete(8,9, "管理员");
    }
}
