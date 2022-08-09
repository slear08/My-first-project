import java.awt.EventQueue;
import java.sql.*;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Crud {

	private Image logo = new ImageIcon(Crud.class.getResource("res/logo.png")).getImage().getScaledInstance(85,85, Image.SCALE_SMOOTH);
	
	JFrame frame;
	private JTextField productTxt;
	private JTextField priceTxt;
	private JTextField quantityTxt;
	private JTextField searchBar;
	private JTable table;

	/**
	 * Launch the application.
	 */
	static Connection conn;
	static PreparedStatement pst;
	

	public static void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/match_a_tea","root","");
			System.out.print("Connection Established");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Crud window = new Crud();
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
	public Crud() {
		initialize();
		Connect();
		tableLoad();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	 public void tableLoad() {
		ResultSet rSet;
		int q;
		
		try {
			pst = conn.prepareStatement("SELECT * FROM product");
			rSet = pst.executeQuery();
			
			ResultSetMetaData rsm = rSet.getMetaData();
			q = rsm.getColumnCount();
			
			DefaultTableModel df = (DefaultTableModel)table.getModel();
			df.setRowCount(0);
			while(rSet.next()) {
				Vector v2 = new Vector();
				for(int i =1;i<=q;i++) {
					v2.add(rSet.getString("id"));
					v2.add(rSet.getString("pname"));
					v2.add(rSet.getString("price"));
					v2.add(rSet.getString("qty"));
				}
				df.addRow(v2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			pst = conn.prepareStatement("SELECT * FROM product");
//			rSet = pst.executeQuery();
//			table.setModel(DbUtils.resultSetToTableModel(rSet));
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(143, 188, 143));
		frame.setBounds(100, 100, 863, 456);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(50, 205, 50), 7, true));
		panel.setBackground(new Color(255, 215, 0));
		panel.setBounds(25, 181, 359, 141);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Product Name & Size:");
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 11, 153, 29);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Product Price:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(58, 51, 105, 29);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Quantity:");
		lblNewLabel_1_2_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel_1_2_1.setBounds(92, 91, 71, 29);
		panel.add(lblNewLabel_1_2_1);
		
		productTxt = new JTextField();
		productTxt.setBounds(173, 17, 164, 23);
		panel.add(productTxt);
		productTxt.setColumns(10);
		
		priceTxt = new JTextField();
		priceTxt.setColumns(10);
		priceTxt.setBounds(173, 57, 164, 23);
		panel.add(priceTxt);
		
		quantityTxt = new JTextField();
		quantityTxt.setColumns(10);
		quantityTxt.setBounds(173, 97, 164, 23);
		panel.add(quantityTxt);
		
		JLabel lblNewLabel = new JLabel("Match-A-Tea");
		lblNewLabel.setBounds(95, 33, 289, 57);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setBorder(null);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 31));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(50, 205, 50), 6, true));
		panel_1.setBackground(new Color(255, 215, 0));
		panel_1.setBounds(25, 115, 238, 48);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("SEARCH PRODUCT ID:");
		lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(10, 11, 119, 26);
		panel_1.add(lblNewLabel_2);
		
		searchBar = new JTextField();
		
		searchBar.setBounds(139, 13, 81, 23);
		panel_1.add(searchBar);
		searchBar.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(407, 33, 331, 361);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"ID", "PRODUCT", "PRICE", "QUANTITY"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		
		scrollPane.setViewportView(table);
		
		JButton saveBtn = new JButton("SAVE");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				 try {
						String sProduct,sPrice,sQuantity;
							
						sProduct = productTxt.getText();
						sPrice = priceTxt.getText();
						sQuantity = quantityTxt.getText();
						
						int price = Integer.parseInt(sPrice);
						int quantity = Integer.parseInt(sQuantity);
						
						pst = conn.prepareStatement("SELECT pname FROM product WHERE pname=?");
						pst.setString(1, sProduct);
						
						ResultSet rs = pst.executeQuery();
						if (rs.next()) {
							JOptionPane.showMessageDialog(null, "Product existed");
							System.out.print("Product existed");
						}else {
							pst = conn.prepareStatement("INSERT INTO product (pname,qty,price)VALUES(?,?,?)");
							pst.setString(1, sProduct);
							pst.setInt(2, quantity);
							pst.setInt(3, price);
							
							int x = pst.executeUpdate();
							
							if(x==1) {
								JOptionPane.showMessageDialog(null, "Product Successfully added");
								System.out.print("Successfully added");
								tableLoad();
							}else {
								JOptionPane.showMessageDialog(null, "Failed!!\nSomething Wrong!");
								System.out.print("Failed");
							}
						}
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
		});
		saveBtn.setBounds(35, 333, 89, 57);
		frame.getContentPane().add(saveBtn);
		
		JButton clearBtn = new JButton("CLEAR");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productTxt.setText("");
				quantityTxt.setText("");
				priceTxt.setText("");
				searchBar.setText("");
			}
		});
		clearBtn.setBounds(134, 333, 89, 57);
		frame.getContentPane().add(clearBtn);
		
		JButton exitBtn = new JButton("EXIT");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null,"Are you sure you want exit?", "Confirmation", JOptionPane.YES_NO_OPTION)==0) {
					System.exit(0);
				}
				
			}
		});
		exitBtn.setBounds(277, 333, 89, 57);
		frame.getContentPane().add(exitBtn);
		
		JButton updateBtn = new JButton("UPDATE");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String sProduct,sPrice,sQuantity,search;
					
						
					sProduct = productTxt.getText();
					sPrice = priceTxt.getText();
					sQuantity = quantityTxt.getText();
					search = searchBar.getText();
					
					int price = Integer.parseInt(sPrice);
					int quantity = Integer.parseInt(sQuantity);
					
					pst = conn.prepareStatement("UPDATE product SET pname=?,price=?,qty=? WHERE pname=?");
					pst.setString(1, sProduct);
					pst.setInt(2, price);
					pst.setInt(3, quantity);
					pst.setString(4, search);
					
					
					int x = pst.executeUpdate();
					
					if(x==1) {
						JOptionPane.showMessageDialog(null, "Product Successfully Update");
						System.out.print("Successfully added");
						tableLoad();
					}else {
						JOptionPane.showMessageDialog(null, "Failed!!\nSomething Wrong!");
						System.out.print("Failed");
					
					}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		updateBtn.setBounds(748, 33, 89, 57);
		frame.getContentPane().add(updateBtn);
		
		JButton deleteBtn = new JButton("DELETE");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the product?", "Confirmation", JOptionPane.YES_NO_OPTION)==0) {
					try {
						
						String search;
						
						search = searchBar.getText();
					
						pst = conn.prepareStatement("DELETE FROM product WHERE id=? or pname=?");
						pst.setString(1, search);
						pst.setString(2, search);
						
						int x = pst.executeUpdate();
						
						if(x==1) {
							JOptionPane.showMessageDialog(null, "Product Successfully Deleted");
							System.out.print("Successfully deleted");
							tableLoad();
							productTxt.setText("");
							quantityTxt.setText("");
							priceTxt.setText("");
						}else {
							JOptionPane.showMessageDialog(null, "Failed!!\nSomething Wrong!");
							System.out.print("Failed");
						
						}
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		deleteBtn.setBounds(748, 333, 89, 57);
		frame.getContentPane().add(deleteBtn);
		
		JButton searchBtn = new JButton("SEARCH");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String id = searchBar.getText();
					int ID = Integer.parseInt(id);
					
					pst = conn.prepareStatement("SELECT pname,price,qty FROM product WHERE id=?");
					pst.setInt(1, ID);
					ResultSet rSet = pst.executeQuery();
					
					if(rSet.next()==true) {
						String product = rSet.getString(1);
						String price = rSet.getString(2);
						String quantity = rSet.getString(3);
						
						searchBar.setText(product);
						productTxt.setText(product);
						priceTxt.setText(price);
						quantityTxt.setText(quantity);
					}else {
						JOptionPane.showMessageDialog(null, "Not Found!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		searchBtn.setBounds(277, 129, 107, 34);
		frame.getContentPane().add(searchBtn);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(logo));
		lblNewLabel_3.setBounds(39, 15, 85, 85);
		frame.getContentPane().add(lblNewLabel_3);
	}
}
