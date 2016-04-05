package org.hibernate.examples.hibernate.config;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.examples.model.AbstractHibernateEntity;
import org.hibernate.examples.utils.HashTool;
import org.hibernate.examples.utils.ToStringHelper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * org.hibernate.examples.hibernate.config.Account
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 11. 28. 오전 9:44
 */
@Entity
@org.hibernate.annotations.Cache(region = "example", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account extends AbstractHibernateEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private double cashBalance;

    @Column(name = "AccountName", nullable = false, length = 32)
    private String name;

    @Override
    public int hashCode() {
        return HashTool.compute(name);
    }

    @Override
    public ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("name", name);
    }

    private static final long serialVersionUID = 8633146002860863953L;

    public Long getId() {
        return this.id;
    }

    public double getCashBalance() {
        return this.cashBalance;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public void setName(String name) {
        this.name = name;
    }
}
