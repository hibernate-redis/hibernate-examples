package org.hibernate.examples.mapping.inheritance.subclass;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.examples.model.AbstractHibernateEntity;
import org.hibernate.examples.utils.HashTool;
import org.hibernate.examples.utils.ToStringHelper;

import javax.persistence.*;

/**
 * org.hibernate.examples.mapping.inheritance.subclass.AbstractBilling
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 11. 30. 오후 2:11
 */
@Entity(name = "Subclass_Billing")
@org.hibernate.annotations.Cache(region = "example", usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BillingType", discriminatorType = DiscriminatorType.STRING)
@DynamicInsert
@DynamicUpdate
public abstract class AbstractBilling extends AbstractHibernateEntity<Long> {

    @Id
    @GeneratedValue
    @Column(name = "billingId")
    public Long id;

    @Column(name = "owner")
    private String owner;

    @Override
    public int hashCode() {
        return HashTool.compute(owner);
    }

    @Override
    public ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                    .add("owner", owner);
    }

    private static final long serialVersionUID = 8802737282954083012L;

    public Long getId() {
        return this.id;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
