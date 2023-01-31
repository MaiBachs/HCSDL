import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DatabaseInterface {
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JLabel tableLabel;
	private JComboBox<String> tableList;
	private JButton selectButton;

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/BENECO";
			String user = "root";
			String password = "1";
			try {
				return DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
		// names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);
	}
	
	public DatabaseInterface() {
		mainFrame = new JFrame("Database Interface");
		mainFrame.setSize(400, 200);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());

		tableLabel = new JLabel("Select a table: ");
		tableList = new JComboBox<String>();
		tableList.addItem("Benefit");
		tableList.addItem("Employee");
		tableList.addItem("Job");
		tableList.addItem("Plan");

		selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTable = (String) tableList.getSelectedItem();
				new TableFunctions(selectedTable);
			}
		});

		mainPanel.add(tableLabel);
		mainPanel.add(tableList);
		mainPanel.add(selectButton);

		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
	}

	class TableFunctions {
		private JFrame functionFrame;
		private JPanel functionPanel;
		private JButton addButton, editButton, deleteButton, viewButton, statisticsButton;

		public TableFunctions(String selectedTable) {
	
	        functionFrame = new JFrame("Table Functions - " + selectedTable);
	        functionFrame.setSize(400, 200);
	
	        functionPanel = new JPanel();
	        functionPanel.setLayout(new FlowLayout());
	        //create jlable
	        JLabel empCodeLabel = new JLabel("Employee Code:");
	        JLabel planCodeLabel = new JLabel("Plan Code:");
	        JLabel empLNameLabel = new JLabel("Last Name:");
	        JLabel jobCodeLabel = new JLabel("Job Code:");
	        JLabel jobDescriptionLabel = new JLabel("Job Description:");
	        JLabel planDescriptionLabel = new JLabel("Plan Description:");
	        
	        // Create JTextFields for empCode, planCode, empLName, jobCode, jobDescription, and planDescription
	        JTextField empCodeField = new JTextField();
	        JTextField planCodeField = new JTextField();
	        JTextField empLNameField = new JTextField();
	        JTextField jobCodeField = new JTextField();
	        JTextField jobDescriptionField = new JTextField();
	        JTextField planDescriptionField = new JTextField();

	        //button submit
	        JButton submitButton = new JButton("Submit");

	        // Create the corresponding textfields and jcombobox(tableSelection) for the code
	        String tableName = tableList.getSelectedItem().toString(); // get the selected table name
	        
	        addButton = new JButton("Add");
	        addButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // code for adding data to the selected table
	                if (tableName.equals("Benefit")) {
	                	JFrame addDataFrame = new JFrame("Add Data Benefit");
	                	addDataFrame.setSize(400, 200);
	                	addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                	JPanel addDataPanel = new JPanel();
	                	addDataPanel.setLayout(new BoxLayout(addDataPanel, BoxLayout.Y_AXIS));

	                	addDataPanel.add(empCodeLabel);
	                	addDataPanel.add(empCodeField);
	                	addDataPanel.add(planCodeLabel);
	                	addDataPanel.add(planCodeField);
	                	addDataPanel.add(submitButton);

	                	addDataFrame.add(addDataPanel);
	                	addDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement addData = con.prepareStatement("INSERT INTO BENEFIT (EMP_CODE, PLAN_CODE) VALUES (?, ?)");
			                        
			                        String planCode = planCodeField.getText();
			                        String empCode = empCodeField.getText();
			                        
			                        addData.setInt(1, Integer.parseInt(empCode));
			                        addData.setInt(2, Integer.parseInt(planCode));
			                        addData.executeUpdate();
			                        JOptionPane.showMessageDialog(null, "Data added successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Employee")) {
	                	JFrame addDataFrame = new JFrame("Add Data Employee");
	                	addDataFrame.setSize(400, 200);
	                	addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                	JPanel addDataPanel = new JPanel();
	                	addDataPanel.setLayout(new BoxLayout(addDataPanel, BoxLayout.Y_AXIS));

	                	addDataPanel.add(empCodeLabel);
	                	addDataPanel.add(empCodeField);
	                	addDataPanel.add(empLNameLabel);
	                	addDataPanel.add(empLNameField);
	                	addDataPanel.add(jobCodeLabel);
	                	addDataPanel.add(jobCodeField);
	                	addDataPanel.add(submitButton);

	                	addDataFrame.add(addDataPanel);
	                	addDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement addData = con.prepareStatement("INSERT INTO EMPLOYEE (EMP_CODE, EMP_LNAME, JOB_CODE) VALUES (?, ?, ?)");
			                        
			                        String empCode = empCodeField.getText();
			                        String empLName = empLNameField.getText();
			                        String jobCode = jobCodeField.getText();
			                        
			                        addData.setInt(1, Integer.parseInt(empCode));
			                        addData.setString(2, empLName);
			                        addData.setInt(3, Integer.parseInt(jobCode));
			                        addData.executeUpdate();
			                        JOptionPane.showMessageDialog(null, "Data added successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Job")) {
	                	JFrame addDataFrame = new JFrame("Add Data Job");
	                	addDataFrame.setSize(400, 200);
	                	addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                	JPanel addDataPanel = new JPanel();
	                	addDataPanel.setLayout(new BoxLayout(addDataPanel, BoxLayout.Y_AXIS));

	                	addDataPanel.add(jobCodeLabel);
	                	addDataPanel.add(jobCodeField);
	                	addDataPanel.add(jobDescriptionLabel);
	                	addDataPanel.add(jobDescriptionField);
	                	addDataPanel.add(submitButton);

	                	addDataFrame.add(addDataPanel);
	                	addDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement addData = con.prepareStatement("INSERT INTO JOB (JOB_CODE, JOB_DESCRIPTION) VALUES (?, ?)");
			                        
			                        String jobCode = jobCodeField.getText();
			                        String jobDescription = jobDescriptionField.getText();
			                        
			                        addData.setInt(1, Integer.parseInt(jobCode));
			                        addData.setString(2, jobDescription);
			                        addData.executeUpdate();
			                        
			                        JOptionPane.showMessageDialog(null, "Data added successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Plan")) {
	                	JFrame addDataFrame = new JFrame("Add Data Plan");
	                	addDataFrame.setSize(400, 200);
	                	addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                	JPanel addDataPanel = new JPanel();
	                	addDataPanel.setLayout(new BoxLayout(addDataPanel, BoxLayout.Y_AXIS));

	                	addDataPanel.add(planCodeLabel);
	                	addDataPanel.add(planCodeField);
	                	addDataPanel.add(planDescriptionLabel);
	                	addDataPanel.add(planDescriptionField);
	                	addDataPanel.add(submitButton);

	                	addDataFrame.add(addDataPanel);
	                	addDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
				                    Connection con = getConnection();
				                    PreparedStatement addData = con.prepareStatement("INSERT INTO PLAN (PLAN_CODE, PLAN_DESCRIPTION) VALUES (?, ?)");
				                    
				                    String planCode = planCodeField.getText();
				                    String planDescription = planDescriptionField.getText();
				                    
				                    addData.setInt(1, Integer.parseInt(planCode));
				                    addData.setString(2, planDescription);
				                    addData.executeUpdate();
				                    
				                    JOptionPane.showMessageDialog(null, "Data added successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                }
	            }
	        });
	
	        editButton = new JButton("Edit");
	        editButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // code for editing data in the selected table
	            	System.out.println("bach");
	                if (tableName.equals("Benefit")) {
	                	JFrame addDataFrame = new JFrame("Edit Data Benefit");
	                	addDataFrame.setSize(400, 200);
	                	addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                	JPanel addDataPanel = new JPanel();
	                	addDataPanel.setLayout(new BoxLayout(addDataPanel, BoxLayout.Y_AXIS));
	                	
	                	JLabel WhereEmpCodeLabel = new JLabel("where Employee Code:");
	                	JTextField WhereEmpCodeField = new JTextField();

	                	addDataPanel.add(empCodeLabel);
	                	addDataPanel.add(empCodeField);
	                	addDataPanel.add(planCodeLabel);
	                	addDataPanel.add(planCodeField);
	                	addDataPanel.add(WhereEmpCodeLabel);
	                	addDataPanel.add(WhereEmpCodeField);
	                	addDataPanel.add(submitButton);

	                	addDataFrame.add(addDataPanel);
	                	addDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement editData = con.prepareStatement("UPDATE BENEFIT SET EMP_CODE = ?, PLAN_CODE = ? WHERE EMP_CODE = ?");
			                        
			                        String planCode = planCodeField.getText();
			                        String empCode = empCodeField.getText();
			                        String WhereEmpCode = WhereEmpCodeField.getText();
			                        
			                        editData.setInt(1, Integer.parseInt(empCode));
			                        editData.setInt(2, Integer.parseInt(planCode));
			                        editData.setInt(3, Integer.parseInt(WhereEmpCode));
			                        editData.executeUpdate();
			                        
			                        JOptionPane.showMessageDialog(null, "Data updated successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Employee")) {
	                	JFrame addDataFrame = new JFrame("Add Data Employee");
	                	addDataFrame.setSize(400, 200);
	                	addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                	JPanel addDataPanel = new JPanel();
	                	addDataPanel.setLayout(new BoxLayout(addDataPanel, BoxLayout.Y_AXIS));
	                	
	                	JLabel WhereEmpCodeLabel = new JLabel("where Employee Code:");
	                	JTextField WhereEmpCodeField = new JTextField();

	                	addDataPanel.add(empCodeLabel);
	                	addDataPanel.add(empCodeField);
	                	addDataPanel.add(empLNameLabel);
	                	addDataPanel.add(empLNameField);
	                	addDataPanel.add(jobCodeLabel);
	                	addDataPanel.add(jobCodeField);
	                	addDataPanel.add(WhereEmpCodeLabel);
	                	addDataPanel.add(WhereEmpCodeField);
	                	addDataPanel.add(submitButton);

	                	addDataFrame.add(addDataPanel);
	                	addDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement editData = con.prepareStatement("UPDATE EMPLOYEE SET EMP_CODE = ?, EMP_LNAME = ?, JOB_CODE = ? WHERE EMP_CODE = ?");
			                        
			                        String empCode = empCodeField.getText();
			                        String empLName = empLNameField.getText();
			                        String jobCode = jobCodeField.getText();
			                        String WhereEmpCode = WhereEmpCodeField.getText();
			                        
			                        editData.setInt(1, Integer.parseInt(empCode));
			                        editData.setString(2, empLName);
			                        editData.setInt(3, Integer.parseInt(jobCode));
			                        editData.setInt(4, Integer.parseInt(WhereEmpCode));
			                        editData.executeUpdate();
			                        
			                        JOptionPane.showMessageDialog(null, "Data updated successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Job")) {
	                	JFrame addDataFrame = new JFrame("Add Data Job");
	                	addDataFrame.setSize(400, 200);
	                	addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                	JPanel addDataPanel = new JPanel();
	                	addDataPanel.setLayout(new BoxLayout(addDataPanel, BoxLayout.Y_AXIS));

	                	JLabel WhereJobCodeLabel = new JLabel("Job Code:");
	                	JTextField WhereJobCodeField = new JTextField();
	                	
	                	addDataPanel.add(jobCodeLabel);
	                	addDataPanel.add(jobCodeField);
	                	addDataPanel.add(jobDescriptionLabel);
	                	addDataPanel.add(jobDescriptionField);
	                	addDataPanel.add(jobCodeLabel);
	                	addDataPanel.add(jobCodeField);
	                	addDataPanel.add(WhereJobCodeLabel);
	                	addDataPanel.add(WhereJobCodeField);
	                	addDataPanel.add(submitButton);
	                	
	                	addDataFrame.add(addDataPanel);
	                	addDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement editData = con.prepareStatement("UPDATE JOB SET JOB_CODE = ?, JOB_DESCRIPTION = ? WHERE JOB_CODE = ?");
			                        
			                        String jobCode = jobCodeField.getText();
			                        String jobDescription = jobDescriptionField.getText();
			                        String WhereJobCode = WhereJobCodeField.getText();
			                        
			                        editData.setInt(1, Integer.parseInt(jobCode));
			                        editData.setString(2, jobDescription);
			                        editData.setInt(1, Integer.parseInt(WhereJobCode));
			                        
			                        JOptionPane.showMessageDialog(null, "Data updated successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Plan")) {
	                	JFrame addDataFrame = new JFrame("Add Data Plan");
	                	addDataFrame.setSize(400, 200);
	                	addDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                	JPanel addDataPanel = new JPanel();
	                	addDataPanel.setLayout(new BoxLayout(addDataPanel, BoxLayout.Y_AXIS));

	                	JLabel WherePlanCodeLabel = new JLabel("Plan Code:");
	                	JTextField WherePlanCodeField = new JTextField();
	                	
	                	addDataPanel.add(planCodeLabel);
	                	addDataPanel.add(planCodeField);
	                	addDataPanel.add(planDescriptionLabel);
	                	addDataPanel.add(planDescriptionField);
	                	addDataPanel.add(WherePlanCodeLabel);
	                	addDataPanel.add(WherePlanCodeField);
	                	addDataPanel.add(submitButton);
	                	
	                	addDataFrame.add(addDataPanel);
	                	addDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement editData = con.prepareStatement("UPDATE PLAN SET PLAN_CODE = ?, PLAN_DESCRIPTION = ? WHERE PLAN_CODE = ?");
			                        
			                        String planCode = planCodeField.getText();
				                    String planDescription = planDescriptionField.getText();
				                    String WherePlanCode = WherePlanCodeField.getText();
			                        
			                        editData.setInt(1, Integer.parseInt(planCode));
			                        editData.setString(2, planDescription);
			                        editData.setInt(3, Integer.parseInt(WherePlanCode));
			                        editData.executeUpdate();
			                        
			                        JOptionPane.showMessageDialog(null, "Data updated successfully!");
			                        } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                        }
							}
						});
	                 }
	            }
	        });
	
	        deleteButton = new JButton("Delete");
	        deleteButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // code for deleting data from the selected table
	            	if (tableName.equals("Benefit")) {
	            		JFrame deleteDataFrame = new JFrame("Delete Data Benefit");
	                	deleteDataFrame.setSize(400, 200);
	                	deleteDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                	
	                	JPanel deleteDataPanel = new JPanel();
	                	deleteDataPanel.setLayout(new BoxLayout(deleteDataPanel, BoxLayout.Y_AXIS));
	                	
	                	JLabel empCodeLabel = new JLabel("Delete Benefit Where EMP_CODE:");
	                	JTextField empCodeField = new JTextField();
	                	
	                	deleteDataPanel.add(empCodeLabel);
	                	deleteDataPanel.add(empCodeField);
	                	deleteDataPanel.add(submitButton);
	                	
	                	deleteDataFrame.add(deleteDataPanel);
	                	deleteDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement deleteData = con.prepareStatement("DELETE FROM BENEFIT WHERE EMP_CODE = ?");
			                        
			                        String empCode = empCodeField.getText();
			                        
			                        deleteData.setInt(1, Integer.parseInt(empCode));
			                        deleteData.executeUpdate();
			                        JOptionPane.showMessageDialog(null, "Data deleted successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Employee")) {
	                	JFrame deleteDataFrame = new JFrame("Delete Data Employee");
	                	deleteDataFrame.setSize(400, 200);
	                	deleteDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                	
	                	JPanel deleteDataPanel = new JPanel();
	                	deleteDataPanel.setLayout(new BoxLayout(deleteDataPanel, BoxLayout.Y_AXIS));
	                	
	                	JLabel empCodeLabel = new JLabel("Employee Code:");
	                	JTextField empCodeField = new JTextField();
	                	
	                	deleteDataPanel.add(empCodeLabel);
	                	deleteDataPanel.add(empCodeField);
	                	deleteDataPanel.add(submitButton);
	                	
	                	deleteDataFrame.add(deleteDataPanel);
	                	deleteDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement deleteData = con.prepareStatement("DELETE FROM EMPLOYEE WHERE EMP_CODE = ?");
			                        
			                        String empCode = empCodeField.getText();
			                        
			                        deleteData.setInt(1, Integer.parseInt(empCode));
			                        deleteData.executeUpdate();
			                        JOptionPane.showMessageDialog(null, "Data deleted successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Job")) {
	                	JFrame deleteDataFrame = new JFrame("Delete Data Employee");
	                	deleteDataFrame.setSize(400, 200);
	                	deleteDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                	
	                	JPanel deleteDataPanel = new JPanel();
	                	deleteDataPanel.setLayout(new BoxLayout(deleteDataPanel, BoxLayout.Y_AXIS));
	                	
	                	JLabel jobCodeLabel = new JLabel("Job Code:");
	                	JTextField jobCodeField = new JTextField();
	                	
	                	deleteDataPanel.add(jobCodeLabel);
	                	deleteDataPanel.add(jobCodeField);
	                	deleteDataPanel.add(submitButton);
	                	
	                	deleteDataFrame.add(deleteDataPanel);
	                	deleteDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement deleteData = con.prepareStatement("DELETE FROM JOB WHERE JOB_CODE = ?");
			                        
			                        String jobCode = jobCodeField.getText();
			                        
			                        deleteData.setInt(1, Integer.parseInt(jobCode));
			                        deleteData.executeUpdate();
			                        JOptionPane.showMessageDialog(null, "Data deleted successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                } else if (tableName.equals("Plan")) {
	                	JFrame deleteDataFrame = new JFrame("Delete Data Employee");
	                	deleteDataFrame.setSize(400, 200);
	                	deleteDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                	
	                	JPanel deleteDataPanel = new JPanel();
	                	deleteDataPanel.setLayout(new BoxLayout(deleteDataPanel, BoxLayout.Y_AXIS));
	                	
	                	JLabel planCodeLabel = new JLabel("Plan Code:");
	                	JTextField planCodeField = new JTextField();
	                	
	                	deleteDataPanel.add(planCodeLabel);
	                	deleteDataPanel.add(planCodeField);
	                	deleteDataPanel.add(submitButton);
	                	
	                	deleteDataFrame.add(deleteDataPanel);
	                	deleteDataFrame.setVisible(true);
	                	submitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
			                        Connection con = getConnection();
			                        PreparedStatement deleteData = con.prepareStatement("DELETE FROM PLAN WHERE PLAN_CODE = ?");
			                        
			                        String planCode = planCodeField.getText();
			                        
			                        deleteData.setInt(1, Integer.parseInt(planCode));
			                        deleteData.executeUpdate();
			                        JOptionPane.showMessageDialog(null, "Data deleted successfully!");
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, "Error: " + ex);
			                    }
							}
						});
	                }
	            }
	        });
	
	        viewButton = new JButton("View");
	        viewButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // code for viewing data in the selected table
	            	if (tableName.equals("Benefit")) {
	            		JFrame viewDataFrame = new JFrame("View Data Benefit");
	            		viewDataFrame.setSize(500, 500);
	            		viewDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            		JPanel viewDataPanel = new JPanel();
	                    viewDataPanel.setLayout(new BoxLayout(viewDataPanel, BoxLayout.Y_AXIS));

	                    JTable benefitData = new JTable();
	                    DefaultTableModel benefitModel = new DefaultTableModel();
	                    benefitModel.setColumnIdentifiers(new Object[] { "Employee Code", "Plan Code" });
	                    benefitData.setModel(benefitModel);

	                    JScrollPane benefitScrollPane = new JScrollPane(benefitData);
	                    viewDataPanel.add(benefitScrollPane);

	                    viewDataFrame.add(viewDataPanel);
	                    viewDataFrame.setVisible(true);

	                    try {
	                        Connection con = getConnection();
	                        PreparedStatement retrieveData = con.prepareStatement("SELECT * FROM BENEFIT");
	                        ResultSet result = retrieveData.executeQuery();

	                        while (result.next()) {
	                            int empCode = result.getInt("EMP_CODE");
	                            int planCode = result.getInt("PLAN_CODE");

	                            benefitModel.addRow(new Object[] { empCode, planCode });
	                        }
	                    } catch (SQLException ex) {
	                        JOptionPane.showMessageDialog(null, "Error: " + ex);
	                    }
	                }
	            	else if (tableName.equals("Employee")) {
	            		try {
	            			Connection con = getConnection();
	            			PreparedStatement viewData = con.prepareStatement("SELECT * FROM EMPLOYEE");
	            			ResultSet rs = viewData.executeQuery();
	                        JTable table = new JTable(buildTableModel(rs));
	                        
	                        JFrame viewDataFrame = new JFrame("View Data Employee");
	                        viewDataFrame.setSize(800, 600);
	                        viewDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                        
	                        JScrollPane sp = new JScrollPane(table);
	                        viewDataFrame.add(sp);
	                        
	                        viewDataFrame.setVisible(true);
	                    } catch (SQLException ex) {
	                        JOptionPane.showMessageDialog(null, "Error: " + ex);
	                    }
	            	}
	            	else if (tableName.equals("Job")) {
	            		try {
	            		Connection con = getConnection();
	            		PreparedStatement viewData = con.prepareStatement("SELECT * FROM JOB");
	            		ResultSet result = viewData.executeQuery();
	                    JTable table = new JTable(buildTableModel(result));

	                    JOptionPane.showMessageDialog(null, new JScrollPane(table));
	                } catch (SQLException ex) {
	                    JOptionPane.showMessageDialog(null, "Error: " + ex);
	                }
	            	}
	            	else if (tableName.equals("Plan")) {
	            		JFrame viewDataFrame = new JFrame("View Data Plan");
	            		viewDataFrame.setSize(500, 300);
	            		viewDataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            		try {
	                        Connection con = getConnection();
	                        PreparedStatement viewData = con.prepareStatement("SELECT * FROM PLAN");
	                        ResultSet result = viewData.executeQuery();

	                        JTable table = new JTable(buildTableModel(result));
	                        JScrollPane scrollPane = new JScrollPane(table);
	                        table.setFillsViewportHeight(true);

	                        viewDataFrame.add(scrollPane);
	                        viewDataFrame.setVisible(true);
	                    } catch (SQLException ex) {
	                        JOptionPane.showMessageDialog(null, "Error: " + ex);
	                    }
	                }
	            }
	        });
	
	        statisticsButton = new JButton("Statistics");
	        statisticsButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // code for displaying statistics for the selected table
	            }
	        });
	
	        functionPanel.add(addButton);
	        functionPanel.add(editButton);
	        functionPanel.add(deleteButton);
	        functionPanel.add(viewButton);
	        functionPanel.add(statisticsButton);
	        
	        functionFrame.add(functionPanel);
	        functionFrame.setVisible(true);
	    }
	}

	public static void main(String[] args) {
		new DatabaseInterface();
	}
}
