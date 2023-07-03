package car_rental;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class ChangePassword extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblEnterNewPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ChangePassword(String name) {
    	setTitle("\u0110\u1ED5i m\u1EADt kh\u1EA9u");
    	setBackground(new Color(255, 255, 255));
        setBounds(450, 360, 433, 167);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(227, 255, 255));
        contentPane.setForeground(new Color(236, 236, 236));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textField.setBounds(150, 40, 189, 23);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnSearch = new JButton("Enter");
        btnSearch.setForeground(new Color(255, 255, 255));
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String pstr = textField.getText();
                try {
                    System.out.println("update password username " + name);
                    System.out.println("update password");

                    //Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/csld_banhang","root", "");
                    Connection con = conn.MySQLConnection.OpenConnention();
                    PreparedStatement st = (PreparedStatement) con
                        .prepareStatement("Update user set password=? where username=?");

                    st.setString(1, pstr);
                    st.setString(2, name);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(btnSearch, "Mật khẩu đã được thay đổi thành công");

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSearch.setBackground(new Color(128, 0, 0));
        btnSearch.setBounds(150, 93, 105, 23);
        contentPane.add(btnSearch);   

        lblEnterNewPassword = new JLabel("Nh\u1EADp Password m\u1EDBi:");
        lblEnterNewPassword.setForeground(new Color(128, 0, 0));
        lblEnterNewPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblEnterNewPassword.setBounds(10, 24, 183, 48);
        contentPane.add(lblEnterNewPassword);
    }
}