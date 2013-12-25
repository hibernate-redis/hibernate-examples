package org.hibernate.examples.mapping.queries;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.examples.model.AbstractHibernateEntity;
import org.hibernate.examples.utils.HashTool;
import org.hibernate.examples.utils.ToStringHelper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * org.hibernate.examples.mapping.queries.Helicopter
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 12. 3. 오후 8:06
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Helicopter extends AbstractHibernateEntity<Long> {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    private String name;

    @Override
    public int hashCode() {
        return HashTool.compute(name);
    }

    @Override
    public ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                    .add("name", name);
    }

    private static final long serialVersionUID = -4578683190280228574L;
}
