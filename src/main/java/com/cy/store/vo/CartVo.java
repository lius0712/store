package com.cy.store.vo;

import java.io.Serializable;
/**购物车数据的vo类*/
public class CartVo implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private String image;
    private Long realPrice;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Long realPrice) {
        this.realPrice = realPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartVo)) return false;

        CartVo cartVo = (CartVo) o;

        if (getCid() != null ? !getCid().equals(cartVo.getCid()) : cartVo.getCid() != null) return false;
        if (getUid() != null ? !getUid().equals(cartVo.getUid()) : cartVo.getUid() != null) return false;
        if (getPid() != null ? !getPid().equals(cartVo.getPid()) : cartVo.getPid() != null) return false;
        if (getPrice() != null ? !getPrice().equals(cartVo.getPrice()) : cartVo.getPrice() != null) return false;
        if (getNum() != null ? !getNum().equals(cartVo.getNum()) : cartVo.getNum() != null) return false;
        if (getTitle() != null ? !getTitle().equals(cartVo.getTitle()) : cartVo.getTitle() != null) return false;
        if (getImage() != null ? !getImage().equals(cartVo.getImage()) : cartVo.getImage() != null) return false;
        return getRealPrice() != null ? getRealPrice().equals(cartVo.getRealPrice()) : cartVo.getRealPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getCid() != null ? getCid().hashCode() : 0;
        result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
        result = 31 * result + (getPid() != null ? getPid().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getNum() != null ? getNum().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + (getRealPrice() != null ? getRealPrice().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CartVo{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", price=" + price +
                ", num=" + num +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", realPrice=" + realPrice +
                '}';
    }
}
