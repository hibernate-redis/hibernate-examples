package org.hibernate.examples.mapping.associations.manytoone;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.examples.model.AbstractHibernateEntity;
import org.hibernate.examples.utils.HashTool;
import org.hibernate.examples.utils.ToStringHelper;

import javax.persistence.*;

/**
 * org.hibernate.examples.mapping.associations.manytoone.JugMember
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 11. 29. 오전 10:22
 */
@Entity
@org.hibernate.annotations.Cache(region = "example", usage = CacheConcurrencyStrategy.READ_WRITE)
public class JugMember extends AbstractHibernateEntity<Long> {

    public JugMember() {}

    public JugMember(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberOf", nullable = false)
    private Jug memberOf;

    @Override
    public int hashCode() {
        return HashTool.compute(name);
    }

    @Override
    public ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                    .add("name", name);
    }

    private static final long serialVersionUID = -2648248653087789755L;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Jug getMemberOf() {
        return this.memberOf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemberOf(Jug memberOf) {
        this.memberOf = memberOf;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
