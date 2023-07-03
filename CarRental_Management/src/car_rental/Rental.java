package car_rental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import conn.MySQLConnection;
import com.toedter.calendar.JDateChooser;

public class Rental extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField RegNumTb;
	private JTextField txtLePhi;
	private JTable CarsTable;
	private JTable CarsTable2;
	private JTextField BrandTb;
	private JTextField ModelTb;
	private JTextField PriceTb;
	
	
	Connection con = null;
	Statement St = null;
	ResultSet Rs = null;
	String sql="";
	
	SimpleDateFormat ft = new SimpleDateFormat("E yyyy-MM-dd");
	
	
	private JScrollPane jsrPane1_Car = new JScrollPane();
	
	private	JScrollPane scrollPane = new JScrollPane();
	
	private String FlagAction="" ;
	private MySQLConnection mySQL = new MySQLConnection();
	
	String[] colsNameCar = { "CARREG","BRAND", "MODEL","STATUS", "PRICE" };
	
	String[] colsNameRental = { "RENTID","CARREG", "CUSTOMERNAME","RENTDATE", "RETURNDATE", "RENTFEES" };
	
	JDateChooser dateThue = new JDateChooser();
	
	JDateChooser dateTra = new JDateChooser();
	
	JComboBox NameCb = new JComboBox();
	
	
	private DefaultTableModel tableModelCar = new DefaultTableModel(null,colsNameCar);
	
	private DefaultTableModel tableModelRental = new DefaultTableModel(null,colsNameRental);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rental frame = new Rental();
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
	public Rental() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) { //Form load Event
				locktext();
				loadDataRent();
	            loadData(); // Hiện bảng dữ liệu lên Jtable trong Frame
	            GetCustomers();
			}
		});
		 
		//thiết kế giao diện
			CarsTable = new JTable();
			CarsTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					
				}
			});

			CarsTable2 = new JTable();
			CarsTable2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1365, 659);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 196, 176));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Registration ");
		lblNewLabel.setBounds(372, 81, 135, 32);
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 15));
		
		txtID = new JTextField();
		txtID.setBounds(265, 131, 76, 26);
		txtID.setColumns(10);
		
		JLabel lblThngHiu = new JLabel("Customer Name");
		lblThngHiu.setBounds(517, 81, 135, 32);
		lblThngHiu.setForeground(new Color(128, 0, 0));
		lblThngHiu.setFont(new Font("Calibri", Font.BOLD, 15));
		
		RegNumTb = new JTextField();
		RegNumTb.setBounds(351, 131, 141, 26);
		RegNumTb.setColumns(10);
		
		JLabel lblMu = new JLabel("Rent ID");
		lblMu.setBounds(265, 81, 135, 32);
		lblMu.setForeground(new Color(128, 0, 0));
		lblMu.setFont(new Font("Calibri", Font.BOLD, 15));
		
		txtLePhi = new JTextField();
		txtLePhi.setBounds(1085, 131, 141, 26);
		txtLePhi.setColumns(10);
		
		JLabel lblTrngThi = new JLabel("Rent Date");
		lblTrngThi.setBounds(696, 81, 135, 32);
		lblTrngThi.setForeground(new Color(128, 0, 0));
		lblTrngThi.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblGi = new JLabel("Fees");
		lblGi.setBounds(1085, 81, 135, 32);
		lblGi.setForeground(new Color(128, 0, 0));
		lblGi.setFont(new Font("Calibri", Font.BOLD, 15));
		NameCb.setBounds(510, 132, 148, 25);
		
		NameCb.setForeground(new Color(128, 0, 0));
		NameCb.setFont(new Font("Calibri", Font.PLAIN, 18));
		
		
		JButton SaveBtn = new JButton("Save");
		SaveBtn.setBackground(new Color(255, 89, 89));
		SaveBtn.setBounds(636, 201, 97, 32);
		SaveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Đọc dữ liệu từ textbox vào biến
				Integer RentId  = Integer.valueOf(txtID.getText());
				String CarReg = RegNumTb.getText();
				String CusName = NameCb.getSelectedItem().toString();
				
				Date RentDate1 = dateThue.getDate(); // giả sử jdcNgayThue là một đối tượng JDateChooser
				String RentDate = new SimpleDateFormat("yyyy-MM-dd")
				                      .format(RentDate1);
								
				Date ReturnDate1 = dateTra.getDate(); // giả sử jdcNgayTra là một đối tượng JDateChooser
				String ReturnDate = new SimpleDateFormat("yyyy-MM-dd")
				                      .format(ReturnDate1);
				
				Integer RentFee  = Integer.valueOf(txtLePhi.getText());
				
				//1.Open connection
				Connection conn = MySQLConnection.OpenConnention();
				String sql="";
				
				//2.Create Statement
				try {
					
					Statement stmt=conn.createStatement();	
					if (FlagAction=="ADDNEW") 
					{
						 sql ="INSERT INTO rent_tb values('" +RentId+"','"+CarReg +"','" + CusName + "','"+RentDate +"','" + ReturnDate +  "','" + RentFee+ "' )";
					}
					int x = stmt.executeUpdate(sql);
					if (FlagAction=="ADDNEW") 
					{
						if (x==0) 
						{
							JOptionPane.showMessageDialog(SaveBtn, "Mẫu tin đã tồn tại");
						}else {
							JOptionPane.showMessageDialog(SaveBtn, "Đã thêm thành công!");
						}
					}
						//Hiển thị dữ liệu lên table
					
						loadDataRent();
						Delete();
						UpdateCar();
						//loadData();
						//locktext();
						conn.close();
				
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		SaveBtn.setForeground(new Color(128, 0, 0));
		SaveBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JButton ResetBtn = new JButton("Reset");
		ResetBtn.setBackground(new Color(255, 89, 89));
		ResetBtn.setBounds(931, 200, 97, 35);
		ResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locktext();
				SetAllNulltext();
			}
		});
		ResetBtn.setForeground(new Color(128, 0, 0));
		ResetBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(250, 266, 494, 37);
		panel_1_1.setBackground(new Color(255, 165, 74));
		panel_1_1.setLayout(null);
		
		JLabel lblDanhSchXe = new JLabel("Cars List\r\n");
		lblDanhSchXe.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchXe.setForeground(new Color(128, 0, 0));
		lblDanhSchXe.setFont(new Font("Calibri", Font.BOLD, 25));
		lblDanhSchXe.setBounds(116, 0, 221, 47);
		panel_1_1.add(lblDanhSchXe);
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.setBackground(new Color(255, 89, 89));
		btnAddNew.setBounds(484, 201, 97, 32);
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlagAction="ADDNEW";
				unlocktext();
			}
		});
		btnAddNew.setForeground(new Color(128, 0, 0));
		btnAddNew.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblManageCars = new JLabel("Cars Rental");
		lblManageCars.setBounds(250, 5, 257, 44);
		lblManageCars.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCars.setForeground(new Color(128, 0, 0));
		lblManageCars.setFont(new Font("Calibri", Font.BOLD, 35));
		
		JLabel lblGi_1 = new JLabel("Return Date");
		lblGi_1.setBounds(898, 81, 97, 32);
		lblGi_1.setForeground(new Color(128, 0, 0));
		lblGi_1.setFont(new Font("Calibri", Font.BOLD, 15));
		//xu ly sk click len table
				CarsTable.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int selectedRow = CarsTable.getSelectedRow();//dong duoc chon
						displayDetail_Car(selectedRow);
						//Delete();
					}
				});
				CarsTable2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int selectedRow = CarsTable2.getSelectedRow();//dong duoc chon
						displayDetail(selectedRow);					}
				});
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBounds(848, 263, 493, 37);
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setBackground(new Color(255, 197, 138));
		
		JLabel lblDanhSchXe_1 = new JLabel("Cars On Rental");
		lblDanhSchXe_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSchXe_1.setForeground(new Color(128, 0, 0));
		lblDanhSchXe_1.setFont(new Font("Calibri", Font.BOLD, 25));
		lblDanhSchXe_1.setBounds(135, 0, 202, 47);
		panel_1_1_1.add(lblDanhSchXe_1);
		
		JButton btnIn = new JButton("Print");
		btnIn.setBounds(753, 436, 88, 34);
		btnIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CarsTable2.print();;
				}catch(Exception ex)
				{
					
				}
				
			
			}
		});
		btnIn.setBackground(new Color(255, 89, 89));
		btnIn.setToolTipText("Đóng cửa sổ");
		btnIn.setForeground(new Color(128, 0, 0));
		btnIn.setFont(new Font("Calibri", Font.BOLD, 15));
		contentPane.setLayout(null);
		contentPane.add(lblManageCars);
		contentPane.add(lblThngHiu);
		contentPane.add(lblNewLabel);
		contentPane.add(lblMu);
		contentPane.add(lblTrngThi);
		contentPane.add(txtID);
		contentPane.add(RegNumTb);
		contentPane.add(NameCb);
		dateThue.setBounds(706, 131, 135, 26);
		contentPane.add(dateThue);
		contentPane.add(btnAddNew);
		contentPane.add(SaveBtn);
		contentPane.add(ResetBtn);
		contentPane.add(panel_1_1);
		jsrPane1_Car.setBounds(254, 313, 490, 276);
		contentPane.add(jsrPane1_Car);
		contentPane.add(btnIn);
		contentPane.add(lblGi_1);
		contentPane.add(lblGi);
		dateTra.setBounds(903, 131, 135, 26);
		contentPane.add(dateTra);
		contentPane.add(txtLePhi);
		contentPane.add(panel_1_1_1);
		scrollPane.setBounds(847, 318, 494, 276);
		contentPane.add(scrollPane);
		
		JButton UpdateBtn = new JButton("Update");
		UpdateBtn.setBackground(new Color(255, 89, 89));
		UpdateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(RegNumTb.getText().isEmpty() || txtLePhi.getText().isEmpty() || txtID.getText().isEmpty() )
				{
					JOptionPane.showConfirmDialog(UpdateBtn, "select The Rent To Be Update");
				}else {
					
					Date RentDat = dateThue.getDate();
					java.sql.Date MyRentDat = new java.sql.Date(RentDat.getTime());
					Date ReturnDat = dateTra.getDate();
					java.sql.Date MyReturnDat = new java.sql.Date(ReturnDat.getTime());

					
					try {
						Connection con = MySQLConnection.OpenConnention();
						int RId = Integer.valueOf(txtID.getText());
						String sql = "Update rent_tb set CarReg='"+RegNumTb.getText()+"',CusName= '" + NameCb.getSelectedItem().toString()+ "',RentDate	='"+ MyRentDat+"',ReturnDate='"+MyReturnDat+ "',RentFee="+txtLePhi.getText()+" where RentId="+RId; 
						Statement Add = con.createStatement();
						Add.executeUpdate(sql);
						JOptionPane.showConfirmDialog(UpdateBtn, "Update Successfully");
						loadDataRent();
						
						
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				
			}
		});
		UpdateBtn.setForeground(new Color(128, 0, 0));
		UpdateBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		UpdateBtn.setBounds(779, 200, 97, 35);
		contentPane.add(UpdateBtn);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(236, 75, 15));
		panel.setBounds(0, 0, 239, 626);
		contentPane.add(panel);
		
		JLabel lblManageCars_1 = new JLabel("Customer");
		lblManageCars_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Customers().setVisible(true);
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
				new Customers().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon(Rental.class.getResource("/car_rental/images/Icons8-Windows-8-Users-Group.48.png")));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(33, 205, 53, 46);
		panel.add(lblNewLabel_1);
		
		JLabel lblManageCars_1_1 = new JLabel("Cars");
		lblManageCars_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new test_Car().setVisible(true);
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
				new test_Car().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1_1.setIcon(new ImageIcon(Rental.class.getResource("/car_rental/images/Pictogrammers-Material-Car-multiple.48.png")));
		lblNewLabel_1_1.setBounds(35, 257, 64, 51);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Return_car().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1_1_1.setIcon(new ImageIcon(Rental.class.getResource("/car_rental/images/Pictogrammers-Material-Car-arrow-left.48.png")));
		lblNewLabel_1_1_1.setBounds(35, 319, 64, 51);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblManageCars_1_1_1 = new JLabel("Return Cars");
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
		lblNewLabel_1_1_1_1.setIcon(new ImageIcon(Rental.class.getResource("/car_rental/images/Pictogrammers-Material-Logout-variant.48.png")));
		lblNewLabel_1_1_1_1.setBounds(131, 484, 48, 38);
		panel.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setIcon(new ImageIcon(Rental.class.getResource("/car_rental/images/preview_rev_1.png")));
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(0, 10, 328, 158);
		panel.add(lblNewLabel_1_2);
		
		
		
	}
	
	 
	//lock all text Fields
		private void locktext() {
			this.txtID.setEditable(false);
			this.RegNumTb.setEditable(false);
			this.NameCb.setEditable(false);
			this.dateThue.setEnabled(false);
			this.dateTra.setEnabled(false);
			this.txtLePhi.setEditable(false);
		}
		
		//unlock all text Fields
		private void unlocktext() {
			this.txtID.setEditable(true);
			this.RegNumTb.setEditable(true);
			this.NameCb.setEditable(true);
			this.dateThue.setEnabled(true);
			this.dateTra.setEnabled(true);
			this.txtLePhi.setEditable(true);
		}
		//hien du lieu khi click
	private void displayDetail_Car(int SelectedIndex) {
		this.RegNumTb.setText((String)tableModelCar.getValueAt(SelectedIndex, 0));
		
	}
	
	//java.awt.event.ActionEvent evt
	private void Delete() {
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
				JOptionPane.showMessageDialog(this, "Mẫu tin không tồn tại");
			}else {
				JOptionPane.showMessageDialog(this, "Đã Xóa thành công!");
			}
			//Hiển thị dữ liệu lên table
			//UpdateCar();
			loadData();	
			//4. close conn
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private void UpdateCar() {
		
			try {
				Connection con = MySQLConnection.OpenConnention();
				int Reg = Integer.valueOf(RegNumTb.getText());
				String CarStatus ="Booked";
				String sql = "UPDATE car_tb SET Status='"+CarStatus+"' WHERE idCar = '"+Reg+"'"; 
				Statement Add = con.createStatement();
				Add.executeUpdate(sql);
				JOptionPane.showConfirmDialog(this, "Update Successfully");
				
				//loadData();
								
			}catch(Exception e){
				e.printStackTrace();
		}
	}
	

	
	//hien du lieu khi click
			private void displayDetail(int SelectedIndex) {
				this.txtID.setText((String)tableModelRental.getValueAt(SelectedIndex, 0));
				this.RegNumTb.setText((String)tableModelRental.getValueAt(SelectedIndex, 1));
				this.NameCb.setSelectedItem((String)tableModelRental.getValueAt(SelectedIndex, 2));	
				this.txtLePhi.setText((String)tableModelRental.getValueAt(SelectedIndex, 5));
			}
	
	private void DisplayCar() {
		String CarStatus = "Available";
		try {
			Connection conn = MySQLConnection.OpenConnention();
			St = conn.createStatement();
			String query = "SELECT * FROM car_tb where Status='"+CarStatus+"' ";
			Rs = St.executeQuery(query);
			CarsTable.setModel(tableModelCar);	
	    	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void GetCustomers() {
		mySQL.connect();  
    	ResultSet result = mySQL.view("customer_tb", null);
		try {
			
			while (result.next()) {
				 String rows[] = new String[5];
	                rows[1] = result.getString("username");
	                NameCb.addItem(result.getString("username"));
			}
			
	    	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
		//Xóa rỗng text
			private void SetAllNulltext() {
				this.txtID.setText("");
				this.RegNumTb.setText("");
				this.txtLePhi.setText("");
				this.NameCb.setSelectedItem("");
				this.dateThue.setDate(null);
				this.dateTra.setDate(null);
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
	    	removeAllRows(CarsTable);
	    	mySQL.connect();  
	    	ResultSet result = mySQL.view("car_tb", null); //đọc dữ liệu bảng student
	        
	        try {
	            while (result.next()) { // nếu còn đọc tiếp được một dòng dữ liệu
	                String rows[] = new String[5];
	                rows[0] = result.getString(1); // lấy dữ liệu tại cột số 1 - id
	                rows[1] = result.getString(2); // lấy dữ liệu tai cột số 2 - tên 
	                rows[2] = result.getString(3); // lấy dữ liệu tại cột số 3 - tuoi
	                rows[3] = result.getString(4); // lấy dữ liệu tai cột số 4 - đchi
	                rows[4] = result.getString(5); // lấy dữ liệu tai cột số 5- gpa
	                
	                 System.out.println(result.getString(2));//in dữ liệu để ktra
	                
	                 tableModelCar.addRow(rows); // đưa dòng dữ liệu vào tableModel để hiện thị lên jtable
	                // mỗi lần có sự thay đổi dữ liệu ở tableModel thì Jtable sẽ tự động update lại trên frame 
	                CarsTable.setModel(tableModelCar); // kết nối jtable với tableModel
	                tableModelCar.addTableModelListener(CarsTable);
	              //them JScroll vào để thấy tiêu đề
	                jsrPane1_Car.setViewportView(CarsTable);
	                this.getContentPane().add(jsrPane1_Car, BorderLayout.CENTER);
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();}
	        finally {  
	    	//dong ket noi
	        mySQL.disconnect();  }
	    }
	    
	    public void loadDataRent() {
	    	removeAllRows(CarsTable2);//Xóa dữ liệu cũ, chuẩn bị nạp mới
	    	mySQL.connect();  
	    	ResultSet result = mySQL.view("rent_tb", null); //đọc dữ liệu bảng student
	        
	        try {
	            while (result.next()) { // nếu còn đọc tiếp được một dòng dữ liệu
	                String rows[] = new String[6];
	                rows[0] = result.getString(1); // lấy dữ liệu tại cột số 1 - id
	                rows[1] = result.getString(2); // lấy dữ liệu tai cột số 2 - tên 
	                rows[2] = result.getString(3); // lấy dữ liệu tại cột số 3 - tuoi
	                rows[3] = result.getString(4); // lấy dữ liệu tai cột số 4 - đchi
	                rows[4] = result.getString(5); // lấy dữ liệu tai cột số 5- gpa
	                rows[5] = result.getString(6);
	                
	                 System.out.println(result.getString(2));//in dữ liệu để ktra
	                
	                 tableModelRental.addRow(rows);
	                // mỗi lần có sự thay đổi dữ liệu ở tableModel thì Jtable sẽ tự động update lại trên frame 
	                CarsTable2.setModel(tableModelRental);
	                tableModelRental.addTableModelListener(CarsTable2);
	              //them JScroll vào để thấy tiêu đề
	                
	                scrollPane.setViewportView(CarsTable2);
	                this.getContentPane().add(scrollPane,BorderLayout.CENTER);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();}
	        finally {  
	    	//dong ket noi
	        mySQL.disconnect();  }
	    }
}
