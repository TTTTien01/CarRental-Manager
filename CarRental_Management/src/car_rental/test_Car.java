package car_rental;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RootPaneContainer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.xdevapi.DbDoc;
import java.sql.*;
import java.util.*;
import conn.MySQLConnection;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;

//

public class test_Car extends JFrame {

	private JPanel contentPane;
	private JTextField RegNumTb;
	private JTextField BrandTb;
	private JTextField ModelTb;
	private JTextField PriceTb;
	private JTable CarsTable;
	
	Connection con = null;
	Statement St = null;
	ResultSet Rs = null;
	String sql="";
	
	private JScrollPane jsrPane1 = new JScrollPane();
	private String FlagAction="" ;
	private MySQLConnection mySQL = new MySQLConnection();
	
	String[] colsName = { "CARREG","BRAND", "MODEL","STATUS", "PRICE" };
	JComboBox StatusCb = new JComboBox(colsName);
	private DefaultTableModel tableModel = new DefaultTableModel(null,colsName);
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test_Car frame = new test_Car();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public test_Car() {
		setTitle("Manage Cars");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) { //Form load Event
				locktext();
	            loadData(); // Hiện bảng dữ liệu lên Jtable trong Frame
			}
		});
		 
		//thiết kế giao diện
			CarsTable = new JTable();
			CarsTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1238, 668);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 196, 176));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 239, 626);
		panel.setBackground(new Color(236, 75, 15));
		panel.setLayout(null);
		
		JLabel lblManageCars_1 = new JLabel("Customer");
		lblManageCars_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Customers().setVisible(true);
				dispose();
			}
		});
		lblManageCars_1.setBounds(65, 219, 150, 31);
		lblManageCars_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCars_1.setForeground(new Color(255, 255, 255));
		lblManageCars_1.setFont(new Font("Calibri", Font.BOLD, 18));
		panel.add(lblManageCars_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Customers().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setIcon(new ImageIcon(test_Car.class.getResource("/car_rental/images/Icons8-Windows-8-Users-Group.48.png")));
		lblNewLabel_1.setBounds(33, 205, 53, 46);
		panel.add(lblNewLabel_1);
		
		JLabel lblManageCars_1_1 = new JLabel("Rental Car");
		lblManageCars_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				new Rental().setVisible(true);
				dispose();
			}
		});
		lblManageCars_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCars_1_1.setForeground(Color.WHITE);
		lblManageCars_1_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblManageCars_1_1.setBounds(65, 277, 150, 31);
		panel.add(lblManageCars_1_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Rental().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1_1.setIcon(new ImageIcon(test_Car.class.getResource("/car_rental/images/Pictogrammers-Material-Car-clock.48.png")));
		lblNewLabel_1_1.setBounds(35, 257, 48, 46);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Return_car().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1_1_1.setIcon(new ImageIcon(test_Car.class.getResource("/car_rental/images/Pictogrammers-Material-Car-arrow-left.48.png")));
		lblNewLabel_1_1_1.setBounds(35, 319, 64, 51);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblManageCars_1_1_1 = new JLabel("Return Car");
		lblManageCars_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Return_car().setVisible(true);
				dispose();
			}
		});
		lblManageCars_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCars_1_1_1.setForeground(Color.WHITE);
		lblManageCars_1_1_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblManageCars_1_1_1.setBounds(65, 339, 150, 31);
		panel.add(lblManageCars_1_1_1);
		
		JLabel lblManageCars_1_1_1_1 = new JLabel("Logout");
		lblManageCars_1_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Login().setVisible(true);
				dispose();
			}
		});
		lblManageCars_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCars_1_1_1_1.setForeground(Color.WHITE);
		lblManageCars_1_1_1_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblManageCars_1_1_1_1.setBounds(22, 497, 150, 31);
		panel.add(lblManageCars_1_1_1_1);
		
		JLabel lblNewLabel = new JLabel("Registration number");
		lblNewLabel.setBounds(265, 81, 157, 32);
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		
		RegNumTb = new JTextField();
		RegNumTb.setBounds(259, 131, 141, 26);
		RegNumTb.setColumns(10);
		
		JLabel lblThngHiu = new JLabel("Brand");
		lblThngHiu.setBounds(464, 81, 135, 32);
		lblThngHiu.setForeground(new Color(128, 0, 0));
		lblThngHiu.setFont(new Font("Arial", Font.BOLD, 15));
		
		BrandTb = new JTextField();
		BrandTb.setBounds(458, 131, 141, 26);
		BrandTb.setColumns(10);
		
		JLabel lblMu = new JLabel("Model");
		lblMu.setBounds(648, 81, 135, 32);
		lblMu.setForeground(new Color(128, 0, 0));
		lblMu.setFont(new Font("Arial", Font.BOLD, 15));
		
		ModelTb = new JTextField();
		ModelTb.setBounds(642, 131, 141, 26);
		ModelTb.setColumns(10);
		
		JLabel lblTrngThi = new JLabel("Status");
		lblTrngThi.setBounds(825, 81, 135, 32);
		lblTrngThi.setForeground(new Color(128, 0, 0));
		lblTrngThi.setFont(new Font("Arial", Font.BOLD, 15));
		
		JLabel lblGi = new JLabel("Giá");
		lblGi.setBounds(1279, 81, 135, 32);
		lblGi.setForeground(new Color(128, 0, 0));
		lblGi.setFont(new Font("Calibri", Font.BOLD, 20));
		
		PriceTb = new JTextField();
		PriceTb.setBounds(1031, 131, 141, 26);
		PriceTb.setColumns(10);
		
		JComboBox StatusCb = new JComboBox();
		StatusCb.setBounds(825, 132, 148, 25);
		StatusCb.setForeground(new Color(128, 0, 0));
		StatusCb.setFont(new Font("Calibri", Font.PLAIN, 18));
		StatusCb.setModel(new DefaultComboBoxModel(new String[] {"Booked", "Available"}));
		
		
		JButton SaveBtn = new JButton("Save");
		SaveBtn.setBackground(new Color(255, 89, 89));
		SaveBtn.setBounds(499, 204, 100, 35);
		SaveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Đọc dữ liệu từ textbox vào biến
				String idCar = RegNumTb.getText();
				String Brand = BrandTb.getText();
				String Model = ModelTb.getText();
				String Status = StatusCb.getSelectedItem().toString();
				String Price = PriceTb.getText();
				
				//1.Open connection
				Connection conn = MySQLConnection.OpenConnention();
				String sql="";
				//2.Create Statement
				try {
					Statement stmt=conn.createStatement();
				if (FlagAction=="ADDNEW") {
					 sql ="INSERT INTO car_tb values('" +idCar+"','"+Brand +"','" + Model + "','"+Status +"','" + Price +  "')";
				//mySQL.insert(sql);
				}
				if (FlagAction=="EDIT"){
					 sql ="UPDATE car_tb Set Brand='"+Brand +"',Model='" + Model + "',Status='"+Status +"',Price='" + Price +  "' WHERE idCar='" +idCar+"'";
				//mySQL.update(sql);
				}
				
				    //JOptionPane.showMessageDialog(btnSave, sql);//hiển trị câu truy vấn để kiểm tra đúng sai
					//3. EXEC SQL
					int x = stmt.executeUpdate(sql);
					if (FlagAction=="ADDNEW") {
					if (x==0) {
						JOptionPane.showMessageDialog(SaveBtn, "Mẫu tin đã tồn tại");
					}else {
						JOptionPane.showMessageDialog(SaveBtn, "Đã thêm thành công!");
					}}
					
					if (FlagAction=="EDIT") {
						if (x==0) {
							JOptionPane.showMessageDialog(SaveBtn, "Mẫu tin không tồn tại");
						}else {
							JOptionPane.showMessageDialog(SaveBtn, "Đã cập nhật giá trị mới thành công!");
						}}
					
					//Hiển thị dữ liệu lên table
					loadData();
					locktext();
				//4. close conn
					
					conn.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		SaveBtn.setForeground(new Color(128, 0, 0));
		SaveBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JButton EditBtn = new JButton("Edit");
		EditBtn.setBackground(new Color(255, 89, 89));
		EditBtn.setBounds(629, 204, 115, 35);
		EditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idCar = RegNumTb.getText();
				FlagAction="EDIT";
				if (idCar.isEmpty()){
					JOptionPane.showMessageDialog(EditBtn, "Bạn chưa chọn mẫu tin để sửa!");
				}else {
				unlocktext();
				RegNumTb.setEditable(false);//lock textID
				}
			}
		});
		EditBtn.setForeground(new Color(128, 0, 0));
		EditBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JButton DeleteBtn = new JButton("Delete");
		DeleteBtn.setBackground(new Color(255, 89, 89));
		DeleteBtn.setBounds(778, 204, 106, 35);
		DeleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Đọc dữ liệu từ textbox vào biến
				String idCar = RegNumTb.getText();
				//1.Open connection
				Connection conn = MySQLConnection.OpenConnention();
				//2.Create Statement
				try {
					Statement stmt=conn.createStatement();
				
					String sql ="DELETE FROM car_tb WHERE idCar = '" + idCar + "'";
				//3. EXEC SQL
					int x = stmt.executeUpdate(sql);
					if (x==0) {
						JOptionPane.showMessageDialog(SaveBtn, "Mẫu tin không tồn tại");
					}else {
						JOptionPane.showMessageDialog(SaveBtn, "Đã Xóa thành công!");
					}
					//Hiển thị dữ liệu lên table
					loadData();
					locktext();
				//4. close conn
					
					conn.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		DeleteBtn.setForeground(new Color(128, 0, 0));
		DeleteBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JButton ResetBtn = new JButton("Reset");
		ResetBtn.setBackground(new Color(255, 89, 89));
		ResetBtn.setBounds(916, 204, 106, 35);
		ResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locktext();
				SetAllNulltext();
			}
		});
		ResetBtn.setForeground(new Color(128, 0, 0));
		ResetBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(265, 318, 934, 37);
		panel_1_1.setBackground(new Color(255, 165, 74));
		panel_1_1.setLayout(null);
		
		JLabel lblDanhSchXe = new JLabel("Cars List");
		lblDanhSchXe.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchXe.setForeground(new Color(128, 0, 0));
		lblDanhSchXe.setFont(new Font("Calibri", Font.BOLD, 25));
		lblDanhSchXe.setBounds(336, 0, 225, 37);
		panel_1_1.add(lblDanhSchXe);
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.setBackground(new Color(255, 89, 89));
		btnAddNew.setBounds(368, 204, 100, 35);
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlagAction="ADDNEW";
				unlocktext();
			}
		});
		btnAddNew.setForeground(new Color(128, 0, 0));
		btnAddNew.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblManageCars = new JLabel("Manage Cars");
		lblManageCars.setBounds(248, 5, 236, 54);
		lblManageCars.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCars.setForeground(new Color(128, 0, 0));
		lblManageCars.setFont(new Font("Calibri", Font.BOLD, 30));
		
		JLabel lblGi_1 = new JLabel("Price");
		lblGi_1.setBounds(1062, 81, 76, 32);
		lblGi_1.setForeground(new Color(128, 0, 0));
		lblGi_1.setFont(new Font("Arial", Font.BOLD, 15));
		//xu ly sk click len table
				CarsTable.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int selectedRow = CarsTable.getSelectedRow();//dong duoc chon
						displayDetail(selectedRow);
					}
				});
		contentPane.setLayout(null);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Login().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1_1_1_1.setIcon(new ImageIcon(test_Car.class.getResource("/car_rental/images/Pictogrammers-Material-Logout-variant.48.png")));
		lblNewLabel_1_1_1_1.setBounds(141, 486, 48, 41);
		panel.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setIcon(new ImageIcon(test_Car.class.getResource("/car_rental/images/preview_rev_1.png")));
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setBounds(0, 10, 328, 158);
		panel.add(lblNewLabel_1_2);
		contentPane.add(lblNewLabel);
		contentPane.add(lblThngHiu);
		contentPane.add(lblGi);
		contentPane.add(panel_1_1);
		contentPane.add(SaveBtn);
		contentPane.add(EditBtn);
		contentPane.add(ResetBtn);
		contentPane.add(lblMu);
		contentPane.add(RegNumTb);
		contentPane.add(BrandTb);
		contentPane.add(ModelTb);
		contentPane.add(StatusCb);
		contentPane.add(lblTrngThi);
		contentPane.add(PriceTb);
		contentPane.add(lblGi_1);
		contentPane.add(btnAddNew);
		contentPane.add(DeleteBtn);
		contentPane.add(lblManageCars);
		jsrPane1.setBounds(265, 365, 934, 253);
		contentPane.add(jsrPane1);
		
		
		
	}
	 
	//lock all text Fields
		private void locktext() {
			this.RegNumTb.setEditable(false);
			this.BrandTb.setEditable(false);
			this.ModelTb.setEditable(false);
			this.StatusCb.setEditable(false);
			this.PriceTb.setEditable(false);
		}
		
		//unlock all text Fields
		private void unlocktext() {
			this.RegNumTb.setEditable(true);
			this.BrandTb.setEditable(true);
			this.ModelTb.setEditable(true);
			this.StatusCb.setEditable(true);
			this.PriceTb.setEditable(true);
		}
		//hien du lieu khi click
		private void displayDetail(int SelectedIndex) {
			this.RegNumTb.setText((String)tableModel.getValueAt(SelectedIndex, 0));
			this.BrandTb.setText((String)tableModel.getValueAt(SelectedIndex, 1));
			this.ModelTb.setText((String)tableModel.getValueAt(SelectedIndex, 2));
			this.StatusCb.setSelectedItem((String)tableModel.getValueAt(SelectedIndex, 3));
			this.PriceTb.setText((String)tableModel.getValueAt(SelectedIndex, 4));
		}
		
		//Xóa rỗng text
			private void SetAllNulltext() {
				this.RegNumTb.setText("");
				this.BrandTb.setText("");
				this.ModelTb.setText("");
				this.StatusCb.setSelectedItem("");
				this.PriceTb.setText("");
			}
			
			 public static void removeAllRows(JTable table) {

			        DefaultTableModel model = (DefaultTableModel) table.getModel();
			       if (table.getRowCount()>0) {//kiểm tra bảng cũ nếu có dữ liệu
			    	   int j=table.getRowCount();//đếm số dòng
			            for (int row = 0; row <j; row++) {//xóa j dòng
			                model.removeRow(0);//xóa tại top =0, khi xóa sẽ đôn các dòng lên vị trí 0
			             }
			        }
			    }
			 
		// Tải dữ liệu lên JTable
	    public void loadData() {
	    	removeAllRows(CarsTable);//Xóa dữ liệu cũ, chuẩn bị nạp mới
	    	//mở nói kết đến CSDL
			mySQL.connect();  
	    	ResultSet result = mySQL.view("car_tb", null); //đọc dữ liệu bảng student
	        //String[] colsName = { "id", "Ten","age", "address","gpa" };
	        //tableModel.setColumnIdentifiers(colsName); // Đặt tiêu đề cho bảng theo các giá trị của mảng colsName
	        try {
	            while (result.next()) { // nếu còn đọc tiếp được một dòng dữ liệu
	                String rows[] = new String[5];
	                rows[0] = result.getString(1); // lấy dữ liệu tại cột số 1 - id
	                rows[1] = result.getString(2); // lấy dữ liệu tai cột số 2 - tên 
	                rows[2] = result.getString(3); // lấy dữ liệu tại cột số 3 - tuoi
	                rows[3] = result.getString(4); // lấy dữ liệu tai cột số 4 - đchi
	                rows[4] = result.getString(5); // lấy dữ liệu tai cột số 5- gpa
	                
	                 System.out.println(result.getString(2));//in dữ liệu để ktra
	                
	                 tableModel.addRow(rows); // đưa dòng dữ liệu vào tableModel để hiện thị lên jtable
	                // mỗi lần có sự thay đổi dữ liệu ở tableModel thì Jtable sẽ tự động update lại trên frame 
	                CarsTable.setModel(tableModel); // kết nối jtable với tableModel
	                tableModel.addTableModelListener(CarsTable);
	              //them JScroll vào để thấy tiêu đề
	                jsrPane1.setViewportView(CarsTable);
	                this.getContentPane().add(jsrPane1, BorderLayout.CENTER);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();}
	        finally {  
	    	//dong ket noi
	        mySQL.disconnect();  }
	    }
}
