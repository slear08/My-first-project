import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class navigation {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					navigation window = new navigation();
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
	public navigation() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(143, 188, 143));
		frame.getContentPane().setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		frame.setBounds(100, 100, 378, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JButton btnCashier = new JButton("CASHIER");
		btnCashier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cashier x = new Cashier();
				x.frame.setVisible(true);
				
			}
		});
		btnCashier.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		btnCashier.setBackground(new Color(255, 165, 0));
		btnCashier.setBounds(56, 207, 268, 71);
		frame.getContentPane().add(btnCashier);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(34, 139, 34), 4, true));
		panel.setBackground(new Color(255, 165, 0));
		panel.setBounds(56, 46, 268, 57);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MATCH-A-TEA");
		lblNewLabel.setBounds(0, 0, 268, 57);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		
		JButton btnAdmin = new JButton("ADMIN");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login x = new login();
				x.frame.setVisible(true);
			}
		});
		btnAdmin.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		btnAdmin.setBackground(new Color(255, 165, 0));
		btnAdmin.setBounds(56, 114, 268, 71);
		frame.getContentPane().add(btnAdmin);
	}
}
