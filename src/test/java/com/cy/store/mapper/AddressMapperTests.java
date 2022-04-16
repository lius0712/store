package com.cy.store.mapper;

import com.cy.store.entity.Address;
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
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(9);
        address.setPhone("1315412515");
        address.setName("liming");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        System.out.println(addressMapper.countByUid(9));
    }

    @Test
    public void findByUid() {
        List<Address> list = addressMapper.findByUid(9);
        System.out.println(list);
    }

    @Test
    public void findByAid() {
        System.err.println(addressMapper.findByAid(6));
    }
    @Test
    public void updateNonDefault() {
        addressMapper.updateNonDefault(9);
    }
    @Test
    public void updateDefaultByAid() {
        addressMapper.updateDefaultByAid(4, "明明", new Date());
    }
    @Test
    public void deleteByAid() {
        addressMapper.deleteByAid(2);
    }
    @Test
    public void findLastModified() {
        System.out.println(addressMapper.findLastModified(9));
    }

}
