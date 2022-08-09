import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.ScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Cashier{
	private Image logo = new ImageIcon(Crud.class.getResource("res/logo.png")).getImage().getScaledInstance(166,166, Image.SCALE_SMOOTH);

	JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cashier window = new Cashier();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	static Connection conn;
	static PreparedStatement pst;
	static PreparedStatement pst1;
	private JLabel lblNewLabel;
	private JLabel lblPrice;
	private JLabel lblQuantity;
	private JLabel productDisplay;
	private JLabel priceDisplay;
	private JTextField quantity;
	private JButton add2CArt;
	private JTable cart;
	private JLabel totalDisplay;
	private JTextField inputPay;
	private JLabel lblNewLabel_1;
	
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

	/**
	 * Create the application.
	 */
	public Cashier() {
		initialize();
		Connect();
		tableLoad();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	



	/**
	 * Initialize the contents of the frame.
	 */
	public void tableLoad() {
		ResultSet rSet;
		int q;
		
		try {
			pst = conn.prepareStatement("SELECT *, CASE WHEN qty = 0 THEN 'OUT OF STOCK' ELSE 'HAVE STOCKS' END AS status FROM product");
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
					v2.add(rSet.getString("status"));
					
				}
				df.addRow(v2);
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getTotal(){
		int sum = 0;
		for(int i=0; i<cart.getRowCount();i++) {
			int quantity = Integer.parseInt(cart.getValueAt(i,1).toString());
			int price = Integer.parseInt(cart.getValueAt(i,2).toString());
			
			int product = quantity*price;
			sum += product;
		}
		totalDisplay.setText(Integer.toString(sum));
	}
//	public void sales() {// call it to the PAY BUTTON
//		String subtotal = totalDisplay.getText();
//		String pay = inputPay.getText();
//		String change = changeTxt.getText();
		
//		int lastInsertId = 0;
		
//		try {
//			pst = conn.prepareStatement("INSERT INTO sales(sab_total,pay,change) VALUES(?,?,?)");
//			pst.setString(1, subtotal);
//			pst.setString(2, pay);
//			pst.setString(3, change);
//			pst.executeUpdate();
//			
//			ResultSet genKey =  pst.getGeneratedKeys();
//			
//			if(genKey.next()) {
//				lastInsertId = genKey.getInt(1);
//			}
//			
//			int rows = cart.getRowCount();
//			
//			pst1 = conn.prepareStatement("INSERT INTO sales_product(product,quantity) VALUES(?,?,?)");
//			String product = "";
//			int quantity =0;
//			
//			for(int i=0;i<cart.getRowCount();i++) {
//				
//				product = (String)cart.getValueAt(i,0);
//				quantity = (Integer)cart.getValueAt(i,1);
//				
//				pst1.setString(1, product);
//				pst1.setInt(2, quantity);
//				
//				pst1.executeQuery();
//			}
//			JOptionPane.showMessageDialog(null, "Record added");
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//	}
	
	
	private void initialize() {
		frame =  new JFrame();
		frame.getContentPane().setBackground(new Color(143, 188, 143));
		frame.setResizable(false);
		frame.setBounds(100, 100, 897, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 45, 586, 166);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				
				TableModel model = table.getModel();
				
				String product = model.getValueAt(index, 1).toString().toUpperCase();
				String price = model.getValueAt(index, 2).toString().toUpperCase();
				
				productDisplay.setText(product);
				priceDisplay.setText(price);
				quantity.setText("1");
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"ID", "PRODUCT", "PRICE", "STOCKS", "STATUS"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		lblNewLabel = new JLabel("PRODUCT:");
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel.setBounds(37, 229, 98, 26);
		frame.getContentPane().add(lblNewLabel);
		
		lblPrice = new JLabel("PRICE:");
		lblPrice.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblPrice.setBounds(65, 266, 56, 26);
		frame.getContentPane().add(lblPrice);
		
		lblQuantity = new JLabel("QUANTITY:");
		lblQuantity.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblQuantity.setBounds(37, 309, 98, 26);
		frame.getContentPane().add(lblQuantity);
		
		productDisplay = new JLabel("--");
		productDisplay.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		productDisplay.setBounds(115, 231, 98, 26);
		frame.getContentPane().add(productDisplay);
		
		priceDisplay = new JLabel("--");
		priceDisplay.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		priceDisplay.setBounds(115, 266, 98, 26);
		frame.getContentPane().add(priceDisplay);
		
		quantity = new JTextField();
		quantity.setText("0");
		quantity.setBounds(127, 314, 32, 21);
		frame.getContentPane().add(quantity);
		quantity.setColumns(10);
		
		add2CArt = new JButton("ADD TO CART");
		add2CArt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String product = productDisplay.getText(),price=priceDisplay.getText();
				int Quantity=Integer.parseInt(quantity.getText());
				
				if(product=="--"||price=="--") {
					JOptionPane.showMessageDialog(null, "INVALID CHOOSE YOUR ORDER");
				}else if(Quantity==0) {
					JOptionPane.showMessageDialog(null, "INVALID QUANTITY");
				}else if(Quantity==0&&product=="--"||price=="--") {
					JOptionPane.showMessageDialog(null, "INVALID CHOOSE YOUR ORDER");
				}else {
					
					
					try {
						pst = conn.prepareStatement("SELECT qty FROM product WHERE pname =?");
						pst.setString(1,product);
						ResultSet rSetQuantity = pst.executeQuery();
						
						
						int countQuantity = 0;
						
						while(rSetQuantity.next()){
				            countQuantity = rSetQuantity.getInt(1);
				        }
						int value = Quantity;
						
						countQuantity -=value;
						
						if(countQuantity<0) {
							JOptionPane.showMessageDialog(null, "ITEM IS OUT OF STOCK");
							System.out.print("out of stock");
						}else {
							DefaultTableModel model = (DefaultTableModel)cart.getModel();
							model.addRow(new Object[] {productDisplay.getText(),quantity.getText(),priceDisplay.getText()});
							getTotal();
							
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
					
					
				}
				
				
			}
		});
		add2CArt.setBounds(47, 346, 136, 44);
		frame.getContentPane().add(add2CArt);
		
		JLabel lblNewLabel_2 = new JLabel("CART:");
		lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(308, 226, 56, 32);
		frame.getContentPane().add(lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(363, 222, 254, 166);
		frame.getContentPane().add(scrollPane_1);
		
		cart = new JTable();
		cart.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"PRODUCT", "QUANTITY", "PRICE"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(cart);
		
		totalDisplay = new JLabel("--");
		totalDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		totalDisplay.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		totalDisplay.setBounds(712, 221, 92, 26);
		frame.getContentPane().add(totalDisplay);
		
		JButton btnNewButton =   new JButton("PAY");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(totalDisplay.getText()=="--") {
					JOptionPane.showMessageDialog(null, "NO ORDER");
				}else {
					
					TableModel model = cart.getModel();
					for(int index =0;index<cart.getRowCount();index++) {
						String product = model.getValueAt(index, 0).toString();
						String quantity = model.getValueAt(index, 1).toString();
						
						try {
							String selectP1 = "SELECT qty FROM product WHERE pname =?";
							pst = conn.prepareStatement(selectP1);
							pst.setString(1,product);
							ResultSet rSetQuantity = pst.executeQuery();
							
							
							int countQuantity = 0;
							
							while(rSetQuantity.next()){
					            countQuantity = rSetQuantity.getInt(1);
					        }
							
							int value = Integer.parseInt(quantity);
							
							if(countQuantity<=0) {
								System.out.print("out of stock");
							}else {
								
								countQuantity  -= value;
								
								if(countQuantity<0) {
									System.out.print("out of stock");
								}else {
									
									int total = Integer.parseInt(totalDisplay.getText());
									int pay = Integer.parseInt(inputPay.getText());
									
									if(pay<total) {
										JOptionPane.showMessageDialog(null, "Invalid");
									}else {
										if(JOptionPane.showConfirmDialog(null,"Are you sure you want to pay this?", "Confirmation", JOptionPane.YES_NO_OPTION)==0) {
											int c = pay-total;
											String selectP2 = "UPDATE product SET qty=? WHERE pname=?";
											pst = conn.prepareStatement(selectP2);
											pst.setInt(1, countQuantity);
											pst.setString(2, product);
											
											pst.executeUpdate();
										
											String change = Integer.toString(c);
											
											JOptionPane.showConfirmDialog(null,"Your change is " +change, "THANK YOU", JOptionPane.CLOSED_OPTION);
											DefaultTableModel data = (DefaultTableModel)cart.getModel();
											data.setRowCount(0);
										
											totalDisplay.setText("--");
											inputPay.setText("");
										}
										
										
									}
									
								}
								
							}
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					
					
					
				}
				tableLoad();
			}
		});
		btnNewButton.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		btnNewButton.setBounds(664, 284, 187, 63);
		frame.getContentPane().add(btnNewButton);
		
		JButton remove2Cart = new JButton("REMOVE TO CART");
		remove2Cart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)cart.getModel();
				int index = cart.getSelectedRow();
				int prevTotal = Integer.parseInt(totalDisplay.getText());
				
				TableModel model1 = cart.getModel();

				int price = Integer.parseInt(model1.getValueAt(index, 2).toString());
				prevTotal -= price;
				
				String uptotal = Integer.toString(prevTotal);
				
				totalDisplay.setText(uptotal);
				
				int row = cart.getSelectedRow();
				model.removeRow(row);
				
				
				
				
			}
		});
		remove2Cart.setBounds(423, 397, 142, 23);
		frame.getContentPane().add(remove2Cart);
		
		JLabel lblNewLabel_3 = new JLabel("Amount to Pay");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(664, 185, 187, 32);
		frame.getContentPane().add(lblNewLabel_3);
		
		inputPay = new JTextField();
		inputPay.setHorizontalAlignment(SwingConstants.CENTER);
		inputPay.setBounds(664, 252, 187, 21);
		frame.getContentPane().add(inputPay);
		inputPay.setColumns(10);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(logo));
		lblNewLabel_1.setBounds(674, 24, 166, 166);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
