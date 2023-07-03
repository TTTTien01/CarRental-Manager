package conn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
//cách 2 - dùng lưu giá trị chèn vào
import java.util.Vector;

public class MySQLConnection {
	private static String DB_URL ="jdbc:mysql://localhost:3306/cardb"; //driver
	private static String USER_NAME="root";
	private static String PASSWORD="";
	
	private Connection connection;//biến cnn nội bộ, lớp cần khai báo khi sd, lưu đt nối kết nội bộ

//Create Connection 
    public static Connection getConnection(String dbURL, String userName, String password) {
    	Connection conn =null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		conn=DriverManager.getConnection(dbURL, userName, password);
    		System.out.println("Kết nối thành công");
    	} catch (Exception ex) {
    		System.out.println("Kết nối không thành công");
    		ex.printStackTrace(); //in đường dẫn lỗi
    	}
    	return conn;
    }
    
//Open Connection
    public static Connection OpenConnention() {
    	Connection conn =null;
    	try {
    		//
    		conn=getConnection(DB_URL, USER_NAME, PASSWORD);
    		
    	} catch (Exception ex) {
    	
    		ex.printStackTrace(); //in đường dẫn lỗi
    	}
    	return conn;
    }
    
    //==========Không sử dụng biến static ==============================================
    //chuyển biến kết nối ra dùng chung cho đối tường
    public  boolean connect( ){
        try {
            connection =getConnection(DB_URL, USER_NAME, PASSWORD);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //đóng kết nối
    public boolean disconnect( ){
        try {
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
     *  Xem dữ liệu trong bảng theo danh sách cột. 
     *  Nếu danh sách cột là null thì xem toàn bộc cột trong bảng
     */
     
    public ResultSet view(String table, String [] cols){
        ResultSet resultSet = null;
        try {
            Statement statement = (Statement) connection.createStatement();
            //tạo câu truy vấn
            String sql = "SELECT ";
            if(cols == null || cols.length == 0){
                sql += "* FROM";
            }else{
                for(int i = 0 ; i < cols.length; i++){
                    sql += "`" + cols[i] + "`, ";
                }
                sql += ";";
                sql = sql.replace("`, ;", "` FROM");
            }
            sql += " " + table;
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            return null;
        }
        return resultSet;
    }
    
     //Hiển thị bằng lệnh sql
    public ResultSet view(String sql){
        ResultSet resultSet = null;
        try {
            Statement statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            return null;
        }
        return resultSet;
    }
    /*
     *  Chèn dữ liệu vào bảng table.
     *  Values là giá trị tương ứng với cột cần cập nhật.
     */
     
    public boolean insert(String table, Vector values){
        try {
            if(table == null || table.equals("") || values == null || values.size() == 0) 
                return false;
             
            Statement statement =  (Statement) connection.createStatement();
             
            String sql = "insert into " + table + " values(";
            for(int i = 0; i < values.size(); i++){
                sql += "'" + values.elementAt(i).toString() + "',";
            }
            sql += ")";
            sql = sql.replace("',)", "')");
             
            if(statement.executeUpdate(sql) < 2){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
     //chèn dang sql
    public boolean insert(String sql){
        try { 
            Statement statement =  (Statement) connection.createStatement();
            if(statement.executeUpdate(sql) < 2){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    /*
     *  Cập nhật dữ liệu trong bảng table.
     *  cols là danh sách cột cần cập nhật, value là giá trị tương ứng với cột cần cập nhật.
     *  ColsWhere là danh sách cột ràng buộc, 
     *  valueWhere là giá trị ràng buộc tương ứng của từng cột
     */
    public boolean update(String table, String[] cols, Vector value, String[] colsWhere, Vector valueWhere){
        try {
            if(table == null || cols == null || colsWhere == null || colsWhere.length != cols.length) 
                return false;
             
            Statement statement = (Statement) connection.createStatement();
            String sql = "update " + table + " set ";
            for(int i = 0 ; i < cols.length; i++){
                sql += "`" + cols[i] + "` = '" + value.elementAt(i) + "', ";
            }
            sql += ";";
            sql = sql.replace("', ;", "' WHERE ");
             
            for(int i = 0 ; i < colsWhere.length; i++){
                sql += "`" + colsWhere[i] + "` = '" + valueWhere.elementAt(i) + "' and ";
            }
            sql += ";";
            sql = sql.replace("' and ;", "'");
 
            statement.executeUpdate(sql);
 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
     //Truyền dạng câu truy vấn
    public boolean update(String sql){
        try {
            
            Statement statement = (Statement) connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /*
     *  Xoá hàng dữ liệu trong bảng table, colsWhere là danh sách cột ràng buộc, 
     *  valueWhere là giá trị ràng buộc tương ứng của từng cột
     *  Nếu colsWhere = null hoặc colswhere.length = 0 thì hàm sẽ xóa toàn bộ bảng
     */
    public boolean delete(String table, String[] colsWhere, Vector valueWhere){
        try {
            if(table == null || table.length() == 0
                    || (colsWhere != null && valueWhere != null && valueWhere.size() != colsWhere.length) )
                return false;
             
            Statement statement = (Statement) connection.createStatement();
            String sql = "DELETE FROM " + table + "";
             
            if( colsWhere != null && colsWhere.length > 0){
                sql += " WHERE ";
                for(int i = 0 ; i < colsWhere.length; i++){
                    sql += "`" + colsWhere[i] + "` = '" + valueWhere.elementAt(i) + "' and ";
                }
                sql += ";";
                sql = sql.replace("' and ;", "'");
            }
 
            statement.executeUpdate(sql);
 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return false;
    }
    
    //truyền dạng sql
    public boolean delete(String sql){
        try {         
            Statement statement = (Statement) connection.createStatement();
            statement.executeUpdate(sql);
 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return false;
    }





}
