package com.way.common.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BaseEntity
 * @Description: 序列化基类
 * @author: xinpei.xu
 * @date: 2017/08/15 20:28
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
