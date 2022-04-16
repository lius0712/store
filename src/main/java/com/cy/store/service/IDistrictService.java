package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 根据父代号来查询区域信息（省市区）
     * @param parent 父代码
     * @return 区域信息
     */
    List<District> getByParent(String parent);

    /**
     * 获取省市区的名称
     * @param code 编码
     * @return 返回名称
     */
    String getNameByCode(String code);

}
