package car_rental;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Splash extends JFrame {

	private JPanel MSplash;
	JProgressBar progressBar = new JProgressBar();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Splash MSplash = new Splash();
		MSplash.setVisible(true);
		try {
			for(int i = 0; i<=100; i++) {
				Thread.sleep(40);
				MSplash.progressBar.setValue(i);
			}
		}catch(Exception e) {
			
		}
		new Login().setVisible(true);
		
	}

	/**
	 * Create the frame.
	 */
	public Splash() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		MSplash = new JPanel();
		MSplash.setBackground(new Color(251, 196, 176));
		MSplash.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(MSplash);
		MSplash.setLayout(null);
		progressBar.setForeground(new Color(255, 170, 170));
		progressBar.setBackground(new Color(128, 0, 0));
		
		
		progressBar.setBounds(0, 257, 436, 6);
		MSplash.add(progressBar);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Splash.class.getResource("/car_rental/images/Rent Car Logo (2)_preview_rev_1.png")));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblNewLabel_1.setBounds(90, 37, 286, 169);
		MSplash.add(lblNewLabel_1);
	}
}
