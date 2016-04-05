package org.hibernate.examples.mapping.associations.join;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.examples.model.AbstractHibernateEntity;
import org.hibernate.examples.utils.HashTool;
import org.hibernate.examples.utils.ToStringHelper;

import javax.persistence.*;
import java.util.Date;

/**
 * org.hibernate.examples.mapping.associations.join.JoinCustomer
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 11. 28. 오후 11:15
 */
@Entity
@org.hibernate.annotations.Cache(region = "example", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "JoinCustomer")
@SecondaryTable(name = "JoinCustomerAddress", pkJoinColumns = @PrimaryKeyJoinColumn(name = "CustomerId"))
public class JoinCustomer extends AbstractHibernateEntity<Long> {

    @Id
    @GeneratedValue
    @Column(name = "CustomerId")
    private Long id;

    private String name;
    private String email;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "street",
                                       column = @Column(name = "Street", table = "JoinCustomerAddress")),
                    @AttributeOverride(name = "zipcode",
                                       column = @Column(name = "ZipCode", table = "JoinCustomerAddress")),
                    @AttributeOverride(name = "city",
                                       column = @Column(name = "City", table = "JoinCustomerAddress")),
            }
    )
    private JoinAddress joinAddress = new JoinAddress();

    @Temporal(TemporalType.TIMESTAMP)
    // @Generated(GenerationTime.INSERT)
    @Column(name = "createdAt", insertable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    // @Generated(GenerationTime.ALWAYS)
    @Column(name = "updatedAt", insertable = false, updatable = false)
    private Date updatedAt;

    @PrePersist
    private void onPrePersist() {
        createdAt = new Date();
    }

    @PreUpdate
    private void onPreUpdate() {
        updatedAt = new Date();
    }

    @Override
    public int hashCode() {
        return HashTool.compute(name, email);
    }

    @Override
    public ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                    .add("name", name)
                    .add("email", email)
                    .add("createdAt", createdAt)
                    .add("updatedAt", updatedAt);
    }

    private static final long serialVersionUID = 5214616271922396271L;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public JoinAddress getJoinAddress() {
        return this.joinAddress;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJoinAddress(JoinAddress joinAddress) {
        this.joinAddress = joinAddress;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
