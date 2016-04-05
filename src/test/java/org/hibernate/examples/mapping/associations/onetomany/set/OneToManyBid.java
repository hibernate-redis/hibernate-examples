package org.hibernate.examples.mapping.associations.onetomany.set;

import org.hibernate.annotations.*;
import org.hibernate.examples.model.AbstractHibernateEntity;
import org.hibernate.examples.utils.HashTool;
import org.hibernate.examples.utils.ToStringHelper;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * org.hibernate.examples.mapping.associations.onetomany.set.OneToManyBid
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 11. 29. 오후 2:04
 */
@Entity
@org.hibernate.annotations.Cache(region = "example", usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class OneToManyBid extends AbstractHibernateEntity<Long> {

    protected OneToManyBid() {}

    public OneToManyBid(OneToManyBiddingItem item, BigDecimal amount) {
        this.item = item;
        this.item.getBids().add(this);
        this.amount = amount;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @LazyToOne(LazyToOneOption.PROXY)
    private OneToManyBiddingItem item;

    @Column(nullable = false)
    private BigDecimal amount;

    @Transient
    private Timestamp timestamp;

    @Override
    public int hashCode() {
        return HashTool.compute(amount);
    }

    @Override
    public ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                    .add("amount", amount);
    }

    private static final long serialVersionUID = 6305076394221526269L;

    public Long getId() {
        return this.id;
    }

    public OneToManyBiddingItem getItem() {
        return this.item;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setItem(OneToManyBiddingItem item) {
        this.item = item;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
