package org.hibernate.examples.mapping;

import org.hibernate.SessionFactory;
import org.hibernate.examples.AbstractHibernateTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.fest.assertions.Assertions.assertThat;

/**
 * org.hibernate.examples.mapping.HibernateMappingTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 11. 28. 오후 3:42
 */
public class HibernateMappingTest extends AbstractHibernateTest {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void mappingTest() throws Exception {
        assertThat(sessionFactory).isNotNull();
    }

}
