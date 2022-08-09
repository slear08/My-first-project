import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class login {

	JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPanel panel;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(143, 188, 143));
		frame.setBounds(100, 100, 450, 343);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(90, 116, 261, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Segoe UI Black", Font.PLAIN, 17));
		lblPassword.setBounds(147, 153, 137, 34);
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(90, 195, 261, 27);
		frame.getContentPane().add(passwordField);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 165, 0));
		panel.setBorder(new LineBorder(new Color(34, 139, 34), 6, true));
		panel.setBounds(46, 71, 334, 179);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("USERNAME");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel_1 = new JLabel("MATCH-A-TEA");
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(75, 11, 276, 47);
		frame.getContentPane().add(lblNewLabel_1);
		
		btnNewButton = new JButton("LOG IN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textField.getText();
				String passwordString = passwordField.getText();
				
				if(user.contains("admin")&&passwordString.contains("admin123")) {
					textField.setText("");
					passwordField.setText("");
					
					Crud x = new Crud();
					x.frame.setVisible(true);
				}else JOptionPane.showMessageDialog(null, "Invalid username or password");
			}
		});
		btnNewButton.setBounds(147, 261, 131, 32);
		frame.getContentPane().add(btnNewButton);
	}
}
