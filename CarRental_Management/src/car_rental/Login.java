package car_rental;

import java.awt.EventQueue;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernametb;
	private JPasswordField passwordField;
    private JButton btnNewButton;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 316);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 196, 176));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 136, 286);
		panel.setBackground(new Color(236, 75, 15));
		contentPane.add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/car_rental/images/Black n White Luxury  Rent Car Logo_preview_rev_1.png")));
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("Login Car Rental Software");
		lblNewLabel.setBounds(169, 24, 303, 20);
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("VISCII Sample Font", Font.BOLD, 23));
		contentPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setBounds(146, 95, 106, 20);
		lblUsername.setForeground(new Color(128, 0, 0));
		lblUsername.setFont(new Font("VISCII Sample Font", Font.BOLD, 15));
		contentPane.add(lblUsername);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(262, 137, 193, 29);
		passwordField.setForeground(new Color(128, 0, 0));
		passwordField.setFont(new Font("VISCII Sample Font", Font.BOLD, 12));
		contentPane.add(passwordField);
		
		usernametb = new JTextField();
		usernametb.setBounds(262, 84, 193, 29);
		usernametb.setFont(new Font("VISCII Sample Font", Font.PLAIN, 12));
		contentPane.add(usernametb);
		usernametb.setColumns(10);
	
		
		JButton loginbtn = new JButton("Login");
		loginbtn.setBackground(new Color(255, 197, 138));
		loginbtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
               String userName = usernametb.getText();
                //
				//String password = passwordField.getText();
				String password=String.valueOf(passwordField.getPassword());
                try {
                	 //Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/csld_banhang","root","");
                    Connection connection = conn.MySQLConnection.OpenConnention();
                    PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("Select username, password from user where username=? and password=?");

                    st.setString(1, userName);
                    st.setString(2, password);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                    	new test_Car().setVisible(true);
                        dispose();
                        //test_Car ah = new test_Car(userName);
                        //ah.setTitle("Quản lý");
                        //ah.setVisible(true);
                        JOptionPane.showMessageDialog(btnNewButton, "Bạn đã đăng nhập thành công");
                    } else {
                        JOptionPane.showMessageDialog(btnNewButton, "Tên người dùng & Mật khẩu sai");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
			
           /*public void actionPerformed(ActionEvent e) {
                if(usernametb.getText().isEmpty() || String.valueOf(passwordField.getPassword()).isEmpty()) {
                	JOptionPane.showConfirmDialog(loginbtn, "Enter The Username and Password");
                	usernametb.setText("");
                	passwordField.setText("");
                }else if(usernametb.getText().equals("Admin") || passwordField.getPassword().equals("Password")){
                	
                	new test_Car().setVisible(true);
                }
                else {
                	JOptionPane.showConfirmDialog(loginbtn, "Wrong Username and Password");
                	usernametb.setText("");
                	passwordField.setText("");
                }
            }*/
        });
		loginbtn.setFont(new Font("VISCII Sample Font", Font.BOLD, 15));
		loginbtn.setForeground(new Color(128, 0, 0));
		loginbtn.setBounds(284, 182, 143, 29);
		contentPane.add(loginbtn);
		
		JLabel restpass = new JLabel("Change password");
		restpass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//new ChangePassword().setVisible(true);
				
			}
		});
		restpass.setForeground(new Color(128, 0, 0));
		restpass.setFont(new Font("VISCII Sample Font", Font.BOLD, 15));
		restpass.setBounds(284, 221, 137, 20);
		contentPane.add(restpass);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(128, 0, 0));
		lblPassword.setFont(new Font("VISCII Sample Font", Font.BOLD, 15));
		lblPassword.setBounds(146, 141, 106, 20);
		contentPane.add(lblPassword);
	}
}
