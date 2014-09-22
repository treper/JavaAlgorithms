package ataosky.example.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ataosky on 14-8-19.
 */
public class DBCPTest {
    private BasicDataSource dataSource = new BasicDataSource();

    @Test
    public void testDBCP() throws IOException, SQLException {
        Properties properties = new Properties();
        InputStream inputStream = DBCPTest.class.getClassLoader().getResourceAsStream("dbcp.properties");
        properties.load(inputStream);


        dataSource.setDriverClassName(properties.getProperty("driver"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        dataSource.setInitialSize(5);
        dataSource.setMaxWait(500);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(10);

        Connection connection = dataSource.getConnection();

        System.out.println(connection);




    }

}
