package examples.daoexam.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceTest {
    public static void main(String[] args){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationContext.class);   // Java Config 를 읽어들인다.
        DataSource ds = ac.getBean(DataSource.class);
        Connection conn = null;
        try{
            conn = ds.getConnection();  // DataSource 로 부터 Connection 을 빌려와서 이를 이용하여 JDBC 프로그래밍을 한다.
            if(conn != null)
                System.out.println("접속 성공");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(conn == null){
                try{
                    conn.close();
                }catch (SQLException ex){
                }
            }
        } // finally
    }
} // end
