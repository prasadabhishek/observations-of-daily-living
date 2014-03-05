import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.sql.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class AssignClassToPatient extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AssignClassToPatient frame = new AssignClassToPatient();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AssignClassToPatient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 183);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
		final String user = "aprasad3"; // For example, "jsmith"
		final String passwd = "200015261"; // Your 9 digit student ID number
		int count = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = null;
		try {
			 conn = DriverManager.getConnection(jdbcURL,user, passwd);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql = "SELECT NAME FROM USERS where usertype = 0";                         
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.last();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			count = rs.getRow();
			System.out.println(count);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs.first();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet rs1 = null;
		try {
			rs1 = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
			//rs2 = stmt.executeQuery(sql);
		
		
		//final String sql1;
		String names [] = new String[count];
		
		int i = 0;
		
		try {
			while (rs1.next())
			{
				names[i] = rs1.getString(1);
				
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//int j;
		final JComboBox comboBox = new JComboBox(names);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String a = (String)comboBox.getSelectedItem();
				String sql1 = "SELECT DISTINCT CNAME FROM PATIENTCLASS MINUS (SELECT CNAME FROM PATIENTCLASS WHERE PTID IN (SELECT ID FROM USERS WHERE NAME = '"+ a +"'))";
				Connection conn1 = null;
				try {
					conn1 = DriverManager.getConnection(jdbcURL,user, passwd);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Statement stmt1 = null;
				try {
					stmt1 = conn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ResultSet rs2 = null;
				String patclass [] = new String[4];
				//String patclass [] = new String[4];
				try {
					rs2 = stmt1.executeQuery(sql1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int j = 0;
				try {
					while(rs2.next())
					{
						patclass[j] = rs2.getString(1);
						j++;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("I am here");
				comboBox_1 = new JComboBox(patclass);
				comboBox_1.setBounds(260, 45, 151, 20);
				contentPane.add(comboBox_1);
				
			}
		});
		comboBox.setBounds(101, 45, 131, 20);
		contentPane.add(comboBox);
		
		
		
		JButton btnAssignClass = new JButton("Assign Class");
		btnAssignClass.setFocusPainted(false);
		btnAssignClass.setBackground(Color.WHITE);
		btnAssignClass.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnAssignClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String x = (String)comboBox.getSelectedItem();
				String y = (String)comboBox_1.getSelectedItem();
				Connection conn2 = null;
				try {
					conn2 = DriverManager.getConnection(jdbcURL, user,passwd);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Statement stmt = null;
				try {
					stmt = conn2.createStatement();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String z = null;
				ResultSet rs = null;
				try {
					rs = stmt.executeQuery("SELECT * FROM USERS ") ;
					while(rs.next())
					{
						if(rs.getString("NAME").equals(x))
						{
							z = rs.getString("ID");
						}
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					rs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int classid = 0;
				int demo = 0;
				ResultSet rs1=null;
				try {
					 rs1 = stmt.executeQuery("SELECT * FROM PATIENTCLASS");
					 while(rs1.next())
					 {
						 demo = rs1.getInt(1);
							if (demo > classid)
							{
								classid = demo;
							}
					 }	 
					 
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					PreparedStatement pst = conn2.prepareStatement("INSERT INTO PATIENTCLASS VALUES (?,?,?)");
					pst.setInt(1,(classid+1));
					pst.setString(2,y);
					pst.setString(3,z);
					pst.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
				JOptionPane.showMessageDialog(null, "Class assigned successfully");
				new PhysicianHome().setVisible(true);
				
				//System.out.println(x + " " + y);
			}
		});
		btnAssignClass.setBounds(131, 102, 114, 23);
		contentPane.add(btnAssignClass);
		
		JLabel lblPatient = new JLabel("Patient");
		lblPatient.setForeground(Color.WHITE);
		lblPatient.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblPatient.setBounds(168, 22, 46, 14);
		contentPane.add(lblPatient);
		
	}
}
