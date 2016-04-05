package org.hibernate.examples.utils;

import com.jolbox.bonecp.BoneCPDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * {@link javax.sql.DataSource} 를 생성, 제공하는 Helper Object 입니다.
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 11. 28. 오전 11:04
 */
public class DataSources {

    private DataSources() {}

    public static final String HSQL_DRIVER_CLASS_NAME = "org.hsql.jdbcDriver";
    public static final String H2_DRIVER_CLASS_NAME = "org.h2.Driver";

    /**
     * {@link javax.sql.DataSource} 를 빌드합니다. 기본적으로 Tomcat DataSource 를 사용합니다.
     *
     * @param driverClass DriverClass 명
     * @param url         Database 주소
     * @return [[javax.sql.DataSource]] 인스턴스
     */
    public static DataSource getDataSource(String driverClass, String url) {
        return getDataSource(driverClass, url, "", "");
    }

    /**
     * {@link javax.sql.DataSource} 를 빌드합니다. 기본적으로 Tomcat DataSource 를 사용합니다.
     *
     * @param driverClass DriverClass 명
     * @param url         Database 주소
     * @param username    사용자 명
     * @param passwd      사용자 패스워드
     * @return [[javax.sql.DataSource]] 인스턴스
     */
    public static DataSource getDataSource(String driverClass, String url, String username, String passwd) {
        return getHikariDataSource(driverClass, url, username, passwd, null);
        // return getBoneCPDataSource(driverClass, url, username, passwd);
        // return getTomcatDataSource(driverClass, url, username, passwd);
    }

    /**
     * HikariCP DataSource를 생성합니다.
     *
     * @param driverClass DriverClass 명
     * @param url         Database 주소
     * @param username    사용자 명
     * @param passwd      사용자 패스워드
     * @return [[javax.sql.DataSource]] 인스턴스
     */
    public static DataSource getHikariDataSource(String driverClass,
                                                 String url,
                                                 String username,
                                                 String passwd,
                                                 Properties props) {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(driverClass);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(passwd);

        // MySQL 인 경우 성능을 위해 아래 설정을 사용합니다.
        if (DataConst.DRIVER_CLASS_MYSQL.equals(driverClass)) {
            config.addDataSourceProperty("cachePrepStmts", true);
            config.addDataSourceProperty("prepStmtCacheSize", 250);
            config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
            /**
             * Due to a number of issues with the use of server-side prepared statements, Connector/J 5.0.5
             * has disabled their use by default. The disabling of server-side prepared statements does not
             * affect the operation of the connector in any way.
             */
            config.addDataSourceProperty("useServerPrepStmts", false);
            /**
             * Disable rewriteBatchedStatements in case the batch contains a bad query (all queries after the bad query will not be executed).
             */
            config.addDataSourceProperty("rewriteBatchedStatements", false);
        }

        if (props != null) {
            for (Map.Entry<Object, Object> entry : props.entrySet())
                config.addDataSourceProperty(entry.getKey().toString(), entry.getValue().toString());
        }

        config.setMinimumIdle(2);
        config.setMaximumPoolSize(4 * Runtime.getRuntime().availableProcessors());

        config.setInitializationFailFast(true);
        config.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(config);
    }

    /**
     * BoneCP DataSource 를 빌드합니다.
     *
     * @param driverClass DriverClass 명
     * @param url         Database 주소
     * @param username    사용자 명
     * @param passwd      사용자 패스워드
     * @return [[javax.sql.DataSource]] 인스턴스
     */
    public static DataSource getBoneCPDataSource(String driverClass, String url, String username, String passwd) {

        BoneCPDataSource ds = new BoneCPDataSource();
        ds.setDriverClass(driverClass);
        ds.setJdbcUrl(url);
        ds.setUser(username);
        ds.setPassword(passwd);

        int processCount = Runtime.getRuntime().availableProcessors();

        ds.setMaxConnectionsPerPartition(100);
        ds.setMinConnectionsPerPartition(processCount);
        ds.setPartitionCount(4);

        ds.setIdleMaxAgeInSeconds(120);
        ds.setIdleConnectionTestPeriodInSeconds(60);
        ds.setMaxConnectionAgeInSeconds(300);

        ds.setDisableJMX(true);

        return ds;
    }

    /**
     * 테스트에 사용하기 위해 메모리를 사용하는 HSql DB 에 대한 DataSource 를 반환합니다.
     */
    public static DataSource getEmbeddedHSqlDataSource() {
        return getDataSource(HSQL_DRIVER_CLASS_NAME, "jdbc:hsqldb:mem:test;MVCC=TRUE;", "sa", "");
    }


    /**
     * 테스트에 사용하기 위해 메모리를 사용하는 H2 DB 에 대한 DataSource 를 반환합니다.
     */
    public static DataSource getEmbeddedH2DataSource() {
        return getDataSource(H2_DRIVER_CLASS_NAME, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MVCC=TRUE;", "sa", "");
    }

}
