package car_rental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.toedter.calendar.JDateChooser;

import conn.MySQLConnection;

import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Return_car extends JFrame {

	private JPanel contentPane;
	private JTextField textField_3;
	private JTextField ReturnId;
	private JTextField RegNumTb;
	private JTextField DelayTb;
	private JTextField FineTb;
	
	
	private JTable CarsTable; //bang Rent
	private JScrollPane scrollPane_1 = new JScrollPane(); //bang rent
	String[] colsNameRental_1 = { "RentID","Registration", "Customer","RentDate", "ReturnDate", "RentFees" }; //bang Rent
	private DefaultTableModel tableModelCar_1 = new DefaultTableModel(null,colsNameRental_1); //bang Rent
	
	
	private JTable CarsTable2; //bang return
	private	JScrollPane Returntalbe_car = new JScrollPane(); //bang return
	String[] colsNameRental_2 = { "RetID","Registration", "Customer","ReturnDate", "Delay", "Fine" }; //bang return
	private DefaultTableModel tableModelCar_2 = new DefaultTableModel(null,colsNameRental_2); //bang return
	
	
	Connection con = null;
	Statement St = null;
	ResultSet Rs = null;
	String sql="";
	SimpleDateFormat ft = new SimpleDateFormat("E yyyy-MM-dd");
	
	private String FlagAction="" ;
	private MySQLConnection mySQL = new MySQLConnection();
	JDateChooser dateTra = new JDateChooser();

	//JDateChooser dateTra = new JDateChooser();
	JComboBox NameCb = new JComboBox();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Return_car frame = new Return_car();
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
	public Return_car() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) { //Form load Event
	            loadData(); // Hiện bảng dữ liệu lên Jtable trong Frame
	            loadDataReturn();
	            GetCustomers();
	            

			}
		});
		
		//thiết kế giao diện
		
		//bang rent
		CarsTable = new JTable();
		CarsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		//bang retuurn
		CarsTable2 = new JTable();
		CarsTable2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1236, 658);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 196, 176));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		ReturnId = new JTextField();
		ReturnId.setBounds(261, 117, 76, 26);
		ReturnId.setColumns(10);
		
		JLabel lblMu = new JLabel("RentId");
		lblMu.setBounds(261, 75, 135, 32);
		lblMu.setForeground(new Color(128, 0, 0));
		lblMu.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel Regis = new JLabel("Registration");
		Regis.setBounds(373, 75, 135, 32);
		Regis.setForeground(new Color(128, 0, 0));
		Regis.setFont(new Font("Calibri", Font.BOLD, 15));
		
		RegNumTb = new JTextField();
		RegNumTb.setBounds(350, 117, 141, 26);
		RegNumTb.setColumns(10);
		
		JLabel ReturnDate = new JLabel("ReturnDate");
		ReturnDate.setBounds(261, 232, 76, 32);
		ReturnDate.setForeground(new Color(128, 0, 0));
		ReturnDate.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblDelay = new JLabel("Delay");
		lblDelay.setForeground(new Color(128, 0, 0));
		lblDelay.setBounds(261, 309, 86, 23);
		lblDelay.setBackground(new Color(255, 89, 89));
		lblDelay.setFont(new Font("Calibri", Font.BOLD, 15));
		
		DelayTb = new JTextField();
		DelayTb.setBounds(311, 306, 73, 26);
		DelayTb.setColumns(10);
		
		JButton ReturnBtn = new JButton("Return");
		ReturnBtn.setBackground(new Color(255, 89, 89));
		ReturnBtn.setBounds(261, 445, 92, 32);
		ReturnBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Connection conn = MySQLConnection.OpenConnention();
				if(RegNumTb.getText().isEmpty() || DelayTb.getText().isEmpty() || FineTb.getText().isEmpty()) {
					JOptionPane.showConfirmDialog(ReturnBtn, "Missing Information");
				}else {
					try {
						Date dateTra1 = dateTra.getDate();
						MyReturnDate = new java.sql.Date(dateTra1.getTime());
						Connection con = MySQLConnection.OpenConnention();
						PreparedStatement add = con.prepareStatement("insert into return_tb values(?,?,?,?,?,?)");
						add.setInt(1, Integer.valueOf(ReturnId.getText()));
						add.setString(2, RegNumTb.getText());
						add.setString(3, NameCb.getSelectedItem().toString());
						add.setDate(4, MyReturnDate);
						add.setInt(5, Integer.valueOf(DelayTb.getText()));
						add.setInt(6, Fine);
						int row = add.executeUpdate();
						JOptionPane.showConfirmDialog(ReturnBtn, "Car Return SuccessFully");
						
						loadDataReturn();
						//int selectedRow = CarsTable2.getSelectedRow();//dong duoc chon
						Delete();
						UpdateCar();
						RemoveFromRent();
						//displayDetail(selectedRow);
						conn.close();
					}catch(Exception ex) {
						
					}
				}
			}
		});
		ReturnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnBTN();
				
				
			}
		});
		ReturnBtn.setForeground(new Color(128, 0, 0));
		ReturnBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JButton btnBack = new JButton("Print");
		btnBack.setBackground(new Color(255, 89, 89));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CarsTable2.print();;
				}catch(Exception ex)
				{
					
				}
			}
		});
		btnBack.setBounds(295, 511, 135, 32);
		btnBack.setForeground(new Color(128, 0, 0));
		btnBack.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(596, 47, 598, 32);
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(new Color(255, 165, 74));
		
		JLabel lblCarsOnTrnt = new JLabel("Cars On Rent");
		lblCarsOnTrnt.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarsOnTrnt.setForeground(new Color(128, 0, 0));
		lblCarsOnTrnt.setFont(new Font("Calibri", Font.BOLD, 25));
		lblCarsOnTrnt.setBounds(156, 0, 269, 32);
		panel_1_1.add(lblCarsOnTrnt);
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBounds(596, 330, 598, 37);
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setBackground(new Color(255, 165, 74));
		
		JLabel lblReturnedCars = new JLabel("Returned Cars");
		lblReturnedCars.setHorizontalAlignment(SwingConstants.CENTER);
		lblReturnedCars.setForeground(new Color(128, 0, 0));
		lblReturnedCars.setFont(new Font("Calibri", Font.BOLD, 25));
		lblReturnedCars.setBounds(157, 0, 273, 47);
		panel_1_1_1.add(lblReturnedCars);
		
		//xu ly sk click len table
		CarsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = CarsTable.getSelectedRow();//dong duoc chon
				displayDetail(selectedRow);
			}
		});
		Returntalbe_car.setBounds(596, 379, 598, 220);
		Returntalbe_car.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		FineTb = new JTextField();
		FineTb.setBounds(311, 367, 141, 26);
		FineTb.setColumns(10);
		
		JLabel lblFine = new JLabel("Fine");
		lblFine.setBounds(261, 365, 135, 32);
		lblFine.setForeground(new Color(128, 0, 0));
		lblFine.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblGenerate = new JLabel("Generate");
		lblGenerate.setBounds(414, 304, 74, 32);
		lblGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = CarsTable.getSelectedRow();//dong duoc chon
				GenerateDelay(selectedRow);
				
			}
		});
		lblGenerate.setForeground(new Color(128, 0, 0));
		lblGenerate.setFont(new Font("Calibri", Font.BOLD, 15));
		
		JLabel lblThngHiu = new JLabel("Tên khách hàng");
		lblThngHiu.setBounds(267, 165, 135, 32);
		lblThngHiu.setForeground(new Color(128, 0, 0));
		lblThngHiu.setFont(new Font("Calibri", Font.BOLD, 15));
		NameCb.setBounds(385, 168, 148, 25);
		
		NameCb.setForeground(new Color(128, 0, 0));
		NameCb.setFont(new Font("Calibri", Font.PLAIN, 18));
		
		JButton ResetBtn = new JButton("Reset\r\n");
		ResetBtn.setBackground(new Color(255, 89, 89));
		ResetBtn.setBounds(373, 444, 92, 33);
		ResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SetAllNulltext();
			}
		});
		ResetBtn.setForeground(new Color(128, 0, 0));
		ResetBtn.setFont(new Font("Calibri", Font.BOLD, 15));
		contentPane.setLayout(null);
		contentPane.add(lblMu);
		contentPane.add(ReturnId);
		contentPane.add(Regis);
		contentPane.add(RegNumTb);
		contentPane.add(lblThngHiu);
		contentPane.add(NameCb);
		contentPane.add(ReturnDate);
		dateTra.setBounds(356, 232, 135, 32);
		contentPane.add(dateTra);
		contentPane.add(lblDelay);
		contentPane.add(DelayTb);
		contentPane.add(lblGenerate);
		contentPane.add(lblFine);
		contentPane.add(FineTb);
		contentPane.add(panel_1_1);
		scrollPane_1.setBounds(596, 86, 598, 220);
		contentPane.add(scrollPane_1);
		contentPane.add(panel_1_1_1);
		contentPane.add(Returntalbe_car);
		contentPane.add(ReturnBtn);
		contentPane.add(ResetBtn);
		contentPane.add(btnBack);
		
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
		lblNewLabel_1.setIcon(new ImageIcon(Return_car.class.getResource("/car_rental/images/Icons8-Windows-8-Users-Group.48.png")));
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
			}
		});
		lblNewLabel_1_1.setIcon(new ImageIcon(Return_car.class.getResource("/car_rental/images/Pictogrammers-Material-Car-multiple.48.png")));
		lblNewLabel_1_1.setBounds(35, 257, 64, 51);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Rental().setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1_1_1.setIcon(new ImageIcon(Return_car.class.getResource("/car_rental/images/Pictogrammers-Material-Car-clock.48.png")));
		lblNewLabel_1_1_1.setBounds(35, 319, 64, 51);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblManageCars_1_1_1 = new JLabel("Rental");
		lblManageCars_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Rental().setVisible(true);
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
		lblNewLabel_1_1_1_1.setIcon(new ImageIcon(Return_car.class.getResource("/car_rental/images/Pictogrammers-Material-Logout-variant.48.png")));
		lblNewLabel_1_1_1_1.setBounds(141, 486, 48, 41);
		panel.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setIcon(new ImageIcon(Return_car.class.getResource("/car_rental/images/preview_rev_1.png")));
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(0, 10, 328, 158);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblReturnCars = new JLabel("Cars Return");
		lblReturnCars.setHorizontalAlignment(SwingConstants.CENTER);
		lblReturnCars.setForeground(new Color(128, 0, 0));
		lblReturnCars.setFont(new Font("Calibri", Font.BOLD, 35));
		lblReturnCars.setBounds(237, 0, 236, 54);
		contentPane.add(lblReturnCars);
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
	int cost = 0;
	int Fine ;
	private void GenerateDelay(int SelectedIndex) {
		if(cost == 0) {
			JOptionPane.showConfirmDialog(this, "Select The Car To Return");
		}else {
		Fine = cost * Integer.valueOf(DelayTb.getText());
			FineTb.setText("Rs"+Fine);
		}
		
	}
	java.util.Date dataTra1;
	java.sql.Date MyReturnDate;
	
	private void ReturnBTN() {
		if(RegNumTb.getText().isEmpty() || DelayTb.getText().isEmpty() || FineTb.getText().isEmpty()) {
			JOptionPane.showConfirmDialog(this, "Missing Information");
		}else {
			try {
				Date dateTra1 = dateTra.getDate();
				MyReturnDate = new java.sql.Date(dateTra1.getTime());
				Connection con = MySQLConnection.OpenConnention();
				PreparedStatement add = con.prepareStatement("insert into return_tb values(?,?,?,?,?,?)");
				add.setInt(1, Integer.valueOf(ReturnId.getText()));
				add.setString(2, RegNumTb.getText());
				add.setString(3, NameCb.getSelectedItem().toString());
				add.setDate(4, MyReturnDate);
				add.setInt(5, Integer.valueOf(DelayTb.getText()));
				add.setInt(6, Fine);
				int row = add.executeUpdate();
				JOptionPane.showConfirmDialog(this, "Car Return SuccessFully");
				
				loadDataReturn();
				Delete();
				UpdateCar();
				RemoveFromRent();
				//loadData();
				con.close();
			}catch(Exception e) {
				
			}
		}
		
	}
	private void SetAllNulltext() {
		this.ReturnId.setText("");
		this.RegNumTb.setText("");
		this.NameCb.setSelectedItem("");
		this.dateTra.setDate(null);
		this.DelayTb.setText("");
		this.FineTb.setText("");
	
}
	
	private void Delete() {
		//Đọc dữ liệu từ textbox vào biến
		String RentId  = RegNumTb.getText();
		//1.Open connection
		Connection conn = MySQLConnection.OpenConnention();
		//2.Create Statement
		try {
			Statement stmt=conn.createStatement();
		
			String sql ="DELETE FROM rent_tb WHERE RentId  = '" + RentId  + "'";
		//3. EXEC SQL
			int x = stmt.executeUpdate(sql);
			if (x==0) {
				JOptionPane.showMessageDialog(this, "Mẫu tin không tồn tại");
			}else {
				JOptionPane.showMessageDialog(this, "Đã Xóa thành công!");
			}
			//Hiển thị dữ liệu lên table
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
	private void RemoveFromRent() {
		try {
			Connection con = MySQLConnection.OpenConnention();
			//int RId = Integer.valueOf(RegNumTb.getText());
			String CarStatus ="Booked";
			String sql = "UPDATE rent_tb WHERE RentId  = " + Rid; 
			Statement Add = con.createStatement();
			Add.executeUpdate(sql);
			JOptionPane.showConfirmDialog(this, "Car Removed From Rented Cars");
			
			loadData();
							
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
	
	int Rid;
	//hien du lieu khi click
	private void displayDetail(int SelectedIndex) {
		this.RegNumTb.setText((String)tableModelCar_1.getValueAt(SelectedIndex, 1));
		this.NameCb.setSelectedItem((String)tableModelCar_1.getValueAt(SelectedIndex, 2));
		Rid = Integer.valueOf((String) tableModelCar_1.getValueAt(SelectedIndex, 0));
		cost = Integer.valueOf((String) tableModelCar_1.getValueAt(SelectedIndex, 5));
	}
	
	
	public void loadData() { // tuong duong ham display
		removeAllRows(CarsTable);//Xóa dữ liệu cũ, chuẩn bị nạp mới
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
                
                 tableModelCar_1.addRow(rows);
                // mỗi lần có sự thay đổi dữ liệu ở tableModel thì Jtable sẽ tự động update lại trên frame 
                CarsTable.setModel(tableModelCar_1);
                tableModelCar_1.addTableModelListener(CarsTable);
              //them JScroll vào để thấy tiêu đề
                
                scrollPane_1.setViewportView(CarsTable);
                this.getContentPane().add(scrollPane_1,BorderLayout.CENTER);
            }
        } catch (SQLException e) {
            e.printStackTrace();}
        finally {  
    	//dong ket noi
        mySQL.disconnect();  }
	}
	
	public void loadDataReturn() {
		removeAllRows(CarsTable2);//Xóa dữ liệu cũ, chuẩn bị nạp mới
    	mySQL.connect();  
    	ResultSet result = mySQL.view("return_tb", null); //đọc dữ liệu bảng student
        
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
                
                 tableModelCar_2.addRow(rows);
                // mỗi lần có sự thay đổi dữ liệu ở tableModel thì Jtable sẽ tự động update lại trên frame 
                 CarsTable2.setModel(tableModelCar_2);
                tableModelCar_2.addTableModelListener(CarsTable2);
              //them JScroll vào để thấy tiêu đề
                
                Returntalbe_car.setViewportView(CarsTable2);
                this.getContentPane().add(Returntalbe_car,BorderLayout.CENTER);
            }
        } catch (SQLException e) {
            e.printStackTrace();}
        finally {  
    	//dong ket noi
        mySQL.disconnect();  }
	}
}
