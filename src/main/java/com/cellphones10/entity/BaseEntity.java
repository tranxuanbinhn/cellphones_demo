package com.cellphones10.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass //mapping with subclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @CreatedBy()
    private String createBy;

    @Column(updatable = false)
    @CreatedDate()
    private Date createDate;

    @Column(insertable = false)
    @LastModifiedBy
    private String moidfiedBy;

    @Column(insertable = false)
    @LastModifiedDate
    private Date moidfiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMoidfiedBy() {
        return moidfiedBy;
    }

    public void setMoidfiedBy(String moidfiedBy) {
        this.moidfiedBy = moidfiedBy;
    }

    public Date getMoidfiedDate() {
        return moidfiedDate;
    }

    public void setMoidfiedDate(Date moidfiedDate) {
        this.moidfiedDate = moidfiedDate;
    }
}
