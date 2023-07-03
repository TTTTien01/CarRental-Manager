package car_rental;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import conn.MySQLConnection;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Customers extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtMaKH;
	private JTextField txtTenKH;
	private JTextField txtSDT;
	private JTable CarsTable;
	
	Connection con = null;
	Statement St = null;
	ResultSet Rs = null;
	String sql="";
	
	private JScrollPane jsrPane1 = new JScrollPane();
	private String FlagAction="" ;
	private MySQLConnection mySQL = new MySQLConnection();
	
	String[] colsName = { "CUSTOMERID","CUSTOMERNAME", "GENDER","ADDRESS", "PHONE" };
	JComboBox txtGT = new JComboBox(colsName);
	private DefaultTableModel tableModel = new DefaultTableModel(null,colsName);
	private JTextField txtDC;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customers frame = new Customers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Customers() {
		
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
		setBounds(100, 100, 1221, 656);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 196, 176));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("CustomerID\r\n\r\n");
		lblNewLabel.setBounds(275, 64, 135, 32);
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 15));
		
		txtMaKH = new JTextField();
		txtMaKH.setBounds(275, 105, 76, 26);
		txtMaKH.setColumns(10);
		
		JLabel lblThngHiu = new JLabel("Customer Name ");
		lblThngHiu.setBounds(407, 64, 135, 32);
		lblThngHiu.setForeground(new Color(128, 0, 0));
		lblThngHiu.setFont(new Font("Calibri", Font.BOLD, 15));
		
		txtTenKH = new JTextField();
		txtTenKH.setBounds(407, 106, 141, 26);
		txtTenKH.setColumns(10);
		
		JLabel lblMu = new JLabel("Gender");
		lblMu.setBounds(601, 64, 135, 32);
		lblMu.setForeground(new Color(128, 0, 0));
		lblMu.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblTrngThi = new JLabel("Address");
		lblTrngThi.setBounds(787, 64, 135, 32);
		lblTrngThi.setForeground(new Color(128, 0, 0));
		lblTrngThi.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblGi = new JLabel("Giá");
		lblGi.setBounds(1295, 81, 135, 32);
		lblGi.setForeground(new Color(128, 0, 0));
		lblGi.setFont(new Font("Calibri", Font.BOLD, 20));
		
		txtSDT = new JTextField();
		txtSDT.setBounds(978, 105, 141, 26);
		txtSDT.setColumns(10);
		
		JComboBox txtGT = new JComboBox();
		txtGT.setBounds(601, 106, 148, 25);
		txtGT.setForeground(new Color(128, 0, 0));
		txtGT.setFont(new Font("Calibri", Font.PLAIN, 18));
		txtGT.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		
		
		JButton SaveBtn = new JButton("Save");
		SaveBtn.setBackground(new Color(255, 89, 89));
		SaveBtn.setBounds(521, 194, 102, 32);
		SaveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Đọc dữ liệu từ textbox vào biến
				String id = txtMaKH.getText();
				String username = txtTenKH.getText();
				String gender = txtGT.getSelectedItem().toString();
				String diachi = txtDC.getText();
				String phone = txtSDT.getText();
				
				//1.Open connection
				Connection conn = MySQLConnection.OpenConnention();
				String sql="";
				//2.Create Statement
				try {
					Statement stmt=conn.createStatement();
				if (FlagAction=="ADDNEW") {
					 sql ="INSERT INTO customer_tb values('" +id+"','"+username +"','" + gender + "','"+diachi +"','" + phone +  "')";
				//mySQL.insert(sql);
				}
				if (FlagAction=="EDIT"){
					 sql ="UPDATE customer_tb Set username='"+username +"',gender='" + gender + "',diachi='"+diachi +"',phone='" + phone +  "' WHERE idCustomer='" +id+"'";
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
		EditBtn.setBounds(667, 194, 94, 32);
		EditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtMaKH.getText();
				FlagAction="EDIT";
				if (id.isEmpty()){
					JOptionPane.showMessageDialog(EditBtn, "Bạn chưa chọn mẫu tin để sửa!");
				}else {
				unlocktext();
				txtMaKH.setEditable(false);//lock textID
				}
			}
		});
		EditBtn.setForeground(new Color(128, 0, 0));
		EditBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JButton DeleteBtn = new JButton("Delete");
		DeleteBtn.setBackground(new Color(255, 89, 89));
		DeleteBtn.setBounds(796, 193, 88, 32);
		DeleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Đọc dữ liệu từ textbox vào biến
				String id = txtMaKH.getText();
				//1.Open connection
				Connection conn = MySQLConnection.OpenConnention();
				//2.Create Statement
				try {
					Statement stmt=conn.createStatement();
				
					String sql ="DELETE FROM customer_tb WHERE idCustomer = '" + id + "'";
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
		ResetBtn.setBounds(940, 194, 94, 32);
		ResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locktext();
				SetAllNulltext();
			}
		});
		ResetBtn.setForeground(new Color(128, 0, 0));
		ResetBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(260, 252, 924, 37);
		panel_1_1.setBackground(new Color(255, 165, 74));
		panel_1_1.setLayout(null);
		
		JLabel lblDanhSchXe = new JLabel("Customer List");
		lblDanhSchXe.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchXe.setForeground(new Color(128, 0, 0));
		lblDanhSchXe.setFont(new Font("Calibri", Font.BOLD, 25));
		lblDanhSchXe.setBounds(302, 0, 362, 37);
		panel_1_1.add(lblDanhSchXe);
		
		JButton btnAddNew = new JButton("Add New\r\n");
		btnAddNew.setBackground(new Color(255, 89, 89));
		btnAddNew.setBounds(377, 194, 102, 32);
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlagAction="ADDNEW";
				unlocktext();
			}
		});
		btnAddNew.setForeground(new Color(128, 0, 0));
		btnAddNew.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblManageCars = new JLabel("Manage Customer");
		lblManageCars.setBounds(260, 5, 236, 37);
		lblManageCars.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCars.setForeground(new Color(128, 0, 0));
		lblManageCars.setFont(new Font("Calibri", Font.BOLD, 25));
		
		JLabel lblGi_1 = new JLabel("PhoneNumber\r\n");
		lblGi_1.setBounds(968, 64, 111, 32);
		lblGi_1.setForeground(new Color(128, 0, 0));
		lblGi_1.setFont(new Font("Calibri", Font.BOLD, 15));
		
		txtDC = new JTextField();
		txtDC.setBounds(797, 105, 141, 26);
		txtDC.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 239, 626);
		panel.setLayout(null);
		panel.setBackground(new Color(236, 75, 15));
		
		JLabel lblManageCars_1 = new JLabel("Cars\r\n");
		lblManageCars_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new test_Car().setVisible(true);
				dispose();
			}
		});
		lblManageCars_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCars_1.setForeground(Color.WHITE);
		lblManageCars_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblManageCars_1.setBounds(65, 219, 150, 31);
		panel.add(lblManageCars_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new test_Car().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon(Customers.class.getResource("/car_rental/images/Pictogrammers-Material-Car-multiple.48.png")));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(33, 205, 53, 46);
		panel.add(lblNewLabel_1);
		
		JLabel lblManageCars_1_1 = new JLabel("Rental Car");
		lblManageCars_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
		lblNewLabel_1_1.setIcon(new ImageIcon(Customers.class.getResource("/car_rental/images/Pictogrammers-Material-Car-clock.48.png")));
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
		lblNewLabel_1_1_1.setIcon(new ImageIcon(Customers.class.getResource("/car_rental/images/Pictogrammers-Material-Car-arrow-left.48.png")));
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
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Login().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1_1_1_1.setIcon(new ImageIcon(Customers.class.getResource("/car_rental/images/Pictogrammers-Material-Logout-variant.48.png")));
		lblNewLabel_1_1_1_1.setBounds(141, 486, 48, 41);
		panel.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setIcon(new ImageIcon(Customers.class.getResource("/car_rental/images/preview_rev_1.png")));
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(0, 10, 328, 158);
		panel.add(lblNewLabel_1_2);
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
		contentPane.add(lblNewLabel);
		contentPane.add(lblThngHiu);
		contentPane.add(lblGi);
		contentPane.add(panel_1_1);
		contentPane.add(SaveBtn);
		contentPane.add(EditBtn);
		contentPane.add(ResetBtn);
		contentPane.add(txtMaKH);
		contentPane.add(txtTenKH);
		contentPane.add(lblMu);
		contentPane.add(lblTrngThi);
		contentPane.add(txtGT);
		contentPane.add(txtDC);
		contentPane.add(lblGi_1);
		contentPane.add(txtSDT);
		contentPane.add(btnAddNew);
		contentPane.add(DeleteBtn);
		contentPane.add(lblManageCars);
		jsrPane1.setBounds(260, 298, 924, 314);
		contentPane.add(jsrPane1);
		
		
		
	}
	 
	//lock all text Fields
		private void locktext() {
			this.txtMaKH.setEditable(false);
			this.txtTenKH.setEditable(false);
			this.txtGT.setEditable(false);
			this.txtDC.setEditable(false);
			this.txtSDT.setEditable(false);
		}
		
		//unlock all text Fields
		private void unlocktext() {
			this.txtMaKH.setEditable(true);
			this.txtTenKH.setEditable(true);
			this.txtGT.setEditable(true);
			this.txtDC.setEditable(true);
			this.txtSDT.setEditable(true);
		}
		//hien du lieu khi click
		private void displayDetail(int SelectedIndex) {
			this.txtMaKH.setText((String)tableModel.getValueAt(SelectedIndex, 0));
			this.txtTenKH.setText((String)tableModel.getValueAt(SelectedIndex, 1));
			this.txtGT.setSelectedItem((String)tableModel.getValueAt(SelectedIndex, 2));
			this.txtDC.setText((String)tableModel.getValueAt(SelectedIndex, 3));
			this.txtSDT.setText((String)tableModel.getValueAt(SelectedIndex, 4));
		}
		
		//Xóa rỗng text
			private void SetAllNulltext() {
				this.txtMaKH.setText("");
				this.txtTenKH.setText("");
				this.txtDC.setText("");
				this.txtGT.setSelectedItem("");
				this.txtSDT.setText("");
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
	    	ResultSet result = mySQL.view("customer_tb", null); //đọc dữ liệu bảng student
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
