package legDay;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OperationLegDay {

	private JFrame frame;
	private JTextField userNameText;
	private JTable userInfoTable;
	private JTable userInfoTable2;
	private JTextField createNewUserName;
	private JPasswordField createNewPassword;
	private JPasswordField confirmNewPassword;
	private JTextField firstNameText;
	private JTextField lastNameText;
	private JTextField employeeText;
	private JTextField phoneNumberText;
	private JTextField emailText;
	private JTextField tierText;
	private JPasswordField passwordField;
	private JLabel loginLabel;
	private JPanel userInfoPanel;
	private ArrayList<String> dbinfo=new ArrayList<String>();
	private ArrayList<String> dbinfo2=new ArrayList<String>();
	private ArrayList<String> updateOptions = new ArrayList<String>();
	private User user;
	private ImageIcon icon;
	private JPanel createNewUserPanel;
	private JPanel createNewUserPanel2;
	private JPanel employeePanel;
	private JTable tablePopulateData;
	private JButton btnLogOut;
	private JTextField searchText;
	private JLabel lblSearchEmail;
	private JTextField editFirstName;
	private JTextField editLastName;
	private JTextField editEmail;
	private JTextField editPhoneNumber;
	private JPanel editInfoPanel;
	private JButton createNew;
	private JButton deleteBtn;
	private JButton btnCancel;
	private JButton btnCancel_1;
	private JLabel lblPasswordsDoNot;
	private JPanel editOptionsPanel;
	private JCheckBox chckbxPool;
	private JButton btnSubmit_1;
	private JCheckBox chckbxTennis;
	private JCheckBox chckbxFootballField;
	private JCheckBox chckbxZoomba;
	private JCheckBox chckbxYoga;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperationLegDay window = new OperationLegDay();
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
	public OperationLegDay() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 472);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setLocationRelativeTo(null);
		icon = new ImageIcon("src/img/Icon.png");
		frame.setIconImage(icon.getImage());
		frame.setTitle("Gym");
		frame.setResizable(false);

		JPanel loginPanel = new JPanel();
		frame.getContentPane().add(loginPanel, "name_178198344352392");
		loginPanel.setLayout(null);

		userNameText = new JTextField();
		userNameText.setBounds(73, 107, 116, 22);
		loginPanel.add(userNameText);
		userNameText.setColumns(10);

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(73, 72, 102, 16);
		loginPanel.add(lblUserName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 206, 116, 22);
		loginPanel.add(lblPassword);
		//***********************************************Login button function***********************************************************
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				/*
				 * this gathers the information typed into the text fields on the window
				 */
				String user2,password2;
				boolean verify;
				boolean isEmp = false;
				user2 = userNameText.getText();
				password2 = passwordField.getText();
				//reset text fields
				userNameText.setText("");
				passwordField.setText("");
				//passes the data into the LoginVerify
				user = new User(user2,password2);
				try {
					verify = user.loginVerify();
					if(verify)			
					{	
						//alters login status label		
						loginLabel.setText("Login: Success");
						//check if user is an employee
						isEmp = user.isEmployee();
						System.out.println(isEmp);

						if(isEmp==true)
						{

							//switch to alternate window
							employeePanel.setVisible(true);
							loginPanel.setVisible(false);
							//gets info from database to populate table

							//Removes duplicate problem.
							DefaultTableModel model = (DefaultTableModel) tablePopulateData.getModel();
							model.setRowCount(0);


							user.getAllInfo(tablePopulateData);

							tablePopulateData.setPreferredScrollableViewportSize(new Dimension(600,300));
							tablePopulateData.setFillsViewportHeight(true);
							JScrollPane js=new JScrollPane(tablePopulateData);
							js.setVisible(true);
							employeePanel.add(js);
						}
						else if(isEmp==false){

							//alternates viewable windows
							userInfoPanel.setVisible(true);
							loginPanel.setVisible(false);

							creatingUserInfoTable();
						}
					}
					else
					{
						//if login fails, it will set loginLabel to "Login: Failed"
						loginLabel.setText("Login: Failed");
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(386, 264, 97, 25);
		loginPanel.add(btnLogin);

		passwordField = new JPasswordField();
		passwordField.setBounds(73, 253, 123, 22);
		loginPanel.add(passwordField);

		loginLabel = new JLabel("Login:");
		loginLabel.setBounds(353, 72, 130, 85);
		loginPanel.add(loginLabel);

		userInfoPanel = new JPanel();
		frame.getContentPane().add(userInfoPanel, "name_178198350432762");
		userInfoPanel.setLayout(null);
		userInfoPanel.setVisible(false);

		JLabel lblUserInfo = new JLabel("User Info");
		lblUserInfo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUserInfo.setBounds(77, 89, 107, 35);
		userInfoPanel.add(lblUserInfo);
		//*******************log out button function************************************
		JButton logoutBtn = new JButton("Log out");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				userInfoPanel.setVisible(false);
				loginPanel.setVisible(true);
				userInfoPanel.remove(userInfoTable); //this fixes the bug where information is being displayed from the first user. the table needs to be removed each time and recreated with a new user.
				loginLabel.setText("Logout: Success"); // CHANGESS
			}
		});
		logoutBtn.setBounds(444, 295, 145, 50);
		userInfoPanel.add(logoutBtn);
		//*****************************************************************************EDIT CLIENT INFO***************************************************************************************
		JButton btnEditInformation = new JButton("Edit Information");
		btnEditInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				editInfoPanel.setVisible(true);
				userInfoPanel.setVisible(false);
				userInfoPanel.remove(userInfoTable);

				try {
					dbinfo = user.getInfo();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				editFirstName.setText(dbinfo.get(0));
				editLastName.setText(dbinfo.get(1));
				editEmail.setText(dbinfo.get(2));
				editPhoneNumber.setText(dbinfo.get(4));

			}
		});
		btnEditInformation.setBounds(231, 295, 156, 50);
		userInfoPanel.add(btnEditInformation);

		////////*******************************************************EDIT OPTIONS EDIT OPTIONS EDIT OPTIONS!!!!!!!!************************************************
		JButton btnEditOptions = new JButton("Edit Options");
		btnEditOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				editOptionsPanel.setVisible(true);
				userInfoPanel.setVisible(false);

				userInfoPanel.remove(userInfoTable);
				userInfoPanel.remove(userInfoTable2);  
			}
		});
		btnEditOptions.setBounds(22, 295, 150, 50);
		userInfoPanel.add(btnEditOptions);

		editInfoPanel = new JPanel();
		frame.getContentPane().add(editInfoPanel, "name_1703263228900954");
		editInfoPanel.setLayout(null);

		editFirstName = new JTextField();
		editFirstName.setBounds(35, 66, 116, 22);
		editInfoPanel.add(editFirstName);
		editFirstName.setColumns(10);

		editLastName = new JTextField();
		editLastName.setBounds(35, 142, 116, 22);
		editInfoPanel.add(editLastName);
		editLastName.setColumns(10);

		editEmail = new JTextField();
		editEmail.setBounds(35, 222, 116, 22);
		editInfoPanel.add(editEmail);
		editEmail.setColumns(10);

		editPhoneNumber = new JTextField();
		editPhoneNumber.setBounds(354, 66, 116, 22);
		editInfoPanel.add(editPhoneNumber);
		editPhoneNumber.setColumns(10);

		JLabel lblFirstName_1 = new JLabel("First Name");
		lblFirstName_1.setBounds(35, 37, 98, 16);
		editInfoPanel.add(lblFirstName_1);

		JLabel lblLastName_1 = new JLabel("Last Name");
		lblLastName_1.setBounds(35, 118, 98, 16);
		editInfoPanel.add(lblLastName_1);

		JLabel lblEmail_1 = new JLabel("E-Mail");
		lblEmail_1.setBounds(35, 192, 98, 16);
		editInfoPanel.add(lblEmail_1);

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(354, 37, 116, 16);
		editInfoPanel.add(lblPhoneNumber);
		//*******************************************************************************SUBMIT EDIT INFO****************************************
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f,l,em,p;
				f = editFirstName.getText();
				l = editLastName.getText();
				em = editEmail.getText();
				p = editPhoneNumber.getText();
				//reset text fields
				editFirstName.setText("");
				editLastName.setText("");
				editEmail.setText("");
				editPhoneNumber.setText("");

				try {
					user.update(f,l,em,p);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Some method in user that will take this information and run an update query

				editInfoPanel.setVisible(false);
				userInfoPanel.setVisible(true);
				creatingUserInfoTable();

			}
		});
		btnSubmit.setBounds(417, 317, 116, 57);
		editInfoPanel.add(btnSubmit);

		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Deleting User...",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				// 0=yes, 1=no
				System.out.println(input);
				if(input==0) 
				{
					String em,p;
					em = editEmail.getText();
					p = editPhoneNumber.getText();
					//reset text fields
					editFirstName.setText("");
					editLastName.setText("");
					editEmail.setText("");
					editPhoneNumber.setText("");

					try {
						user.removeUser(em,p);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//Some method in user that will take this information and run a delete query

					editInfoPanel.setVisible(false);
					loginPanel.setVisible(true);
				}
			}
		});
		deleteBtn.setBounds(88, 317, 116, 57);
		editInfoPanel.add(deleteBtn);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userInfoPanel.setVisible(true);
				editInfoPanel.setVisible(false);
				creatingUserInfoTable();
				userInfoPanel.add(userInfoTable);
			}
		});
		btnCancel.setBounds(253, 317, 116, 57);
		editInfoPanel.add(btnCancel);
		//*********************************************************************************End button function*************************************

		createNewUserPanel = new JPanel();
		frame.getContentPane().add(createNewUserPanel, "name_178198354646768");
		createNewUserPanel.setLayout(null);
		createNewUserPanel.setVisible(false);

		JLabel lblUserName_1 = new JLabel("User Name");
		lblUserName_1.setBounds(60, 63, 103, 32);
		createNewUserPanel.add(lblUserName_1);

		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(60, 185, 86, 16);
		createNewUserPanel.add(lblPassword_1);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(60, 274, 134, 32);
		createNewUserPanel.add(lblConfirmPassword);

		createNewUserName = new JTextField();
		createNewUserName.setBounds(60, 103, 116, 22);
		createNewUserPanel.add(createNewUserName);
		createNewUserName.setColumns(10);

		createNewPassword = new JPasswordField();
		createNewPassword.setBounds(60, 214, 116, 22);
		createNewUserPanel.add(createNewPassword);
		createNewPassword.setColumns(10);

		confirmNewPassword = new JPasswordField();
		confirmNewPassword.setBounds(60, 319, 116, 22);
		createNewUserPanel.add(confirmNewPassword);
		confirmNewPassword.setColumns(10);

		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{

				boolean flag = false;
				String newUserName,newPassword,cNewPassword;
				/*
				 * this gathers the information typed into the text fields on the window
				 */
				newUserName = createNewUserName.getText();
				newPassword = createNewPassword.getText();
				cNewPassword = confirmNewPassword.getText();

				//if statement to make sure confirm new password = createNewpassword
				if(cNewPassword.equals(newPassword))
				{
					System.out.println("Trying to create user");
					user = new User(newUserName, newPassword);

					try {
						flag = user.createNewUser();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if(flag)
					{
						//reset text fields
						createNewUserName.setText("");			//
						createNewPassword.setText("");			// CHANGESS
						confirmNewPassword.setText("");			//
						createNewUserPanel2.setVisible(true);
						createNewUserPanel.setVisible(false);
					}

				}
				else
				{
					lblPasswordsDoNot.setVisible(true);			// CHANGESS
					System.out.println("passwords do not match");   
				}



			}
		});
		submitBtn.setBounds(395, 318, 97, 25);
		createNewUserPanel.add(submitBtn);

		btnCancel_1 = new JButton("Cancel");
		btnCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewUserName.setText("");
				createNewPassword.setText("");
				confirmNewPassword.setText("");
				lblPasswordsDoNot.setVisible(false);
				createNewUserPanel.setVisible(false);
				employeePanel.setVisible(true);
				employeePanel.add(tablePopulateData);
				DefaultTableModel model = (DefaultTableModel) tablePopulateData.getModel();
				model.setRowCount(0);
				tablePopulateData.setPreferredScrollableViewportSize(new Dimension(600,300));
				tablePopulateData.setFillsViewportHeight(true);
				JScrollPane js=new JScrollPane(tablePopulateData);
				js.setVisible(true);
				employeePanel.add(js);

				tablePopulateData.setModel(new DefaultTableModel(
						new Object[][] {
							{null, null, null, null, null, null},
						},
						new String[] {
								"First Name", "Last Name", "E-Mail", "Tier", "Phone Number","Employee"
						}));
			}
		});
		btnCancel_1.setBounds(243, 318, 97, 25);
		createNewUserPanel.add(btnCancel_1);

		lblPasswordsDoNot = new JLabel("Passwords Do Not Match");
		lblPasswordsDoNot.setBounds(297, 185, 176, 16);
		lblPasswordsDoNot.setVisible(false);
		createNewUserPanel.add(lblPasswordsDoNot);

		createNewUserPanel2 = new JPanel();
		frame.getContentPane().add(createNewUserPanel2, "name_178198359276470");
		createNewUserPanel2.setLayout(null);
		createNewUserPanel2.setVisible(false);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(66, 80, 103, 27);
		createNewUserPanel2.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(66, 155, 86, 27);
		createNewUserPanel2.add(lblLastName);

		JLabel lblEmployee = new JLabel("Employee");
		lblEmployee.setBounds(66, 238, 56, 16);
		createNewUserPanel2.add(lblEmployee);

		JLabel lblPhone = new JLabel("Phone Number");
		lblPhone.setBounds(298, 82, 103, 22);
		createNewUserPanel2.add(lblPhone);

		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setBounds(298, 160, 56, 16);
		createNewUserPanel2.add(lblEmail);

		JLabel lblTier = new JLabel("Tier");
		lblTier.setBounds(298, 238, 56, 16);
		createNewUserPanel2.add(lblTier);

		firstNameText = new JTextField();
		firstNameText.setBounds(66, 109, 116, 22);
		createNewUserPanel2.add(firstNameText);
		firstNameText.setColumns(10);

		lastNameText = new JTextField();
		lastNameText.setBounds(66, 195, 116, 22);
		createNewUserPanel2.add(lastNameText);
		lastNameText.setColumns(10);

		employeeText = new JTextField();
		employeeText.setBounds(66, 274, 116, 22);
		createNewUserPanel2.add(employeeText);
		employeeText.setColumns(10);

		phoneNumberText = new JTextField();
		phoneNumberText.setBounds(298, 109, 116, 22);
		createNewUserPanel2.add(phoneNumberText);
		phoneNumberText.setColumns(10);

		emailText = new JTextField();
		emailText.setBounds(298, 189, 116, 22);
		createNewUserPanel2.add(emailText);
		emailText.setColumns(10);

		tierText = new JTextField();
		tierText.setBounds(298, 274, 116, 22);
		createNewUserPanel2.add(tierText);
		tierText.setColumns(10);

		JButton submitBtn2 = new JButton("Submit");
		submitBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String fName,lName,phoneNumber,email,tier,employee;
				/*
				 * this gathers the information typed into the text fields on the window
				 */
				fName = firstNameText.getText();
				lName = lastNameText.getText();
				employee = employeeText.getText();
				phoneNumber =  phoneNumberText.getText();
				email =  emailText.getText();
				tier = tierText.getText();


				//inserts user info into the database
				try {
					user.createUserInfo(fName,lName,email,tier,phoneNumber,employee);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//reset text fields
				firstNameText.setText("");
				lastNameText.setText("");
				employeeText.setText("");
				phoneNumberText.setText("");
				emailText.setText("");
				tierText.setText("");

				loginPanel.setVisible(true);
				createNewUserPanel2.setVisible(false);

				tablePopulateData.setModel(new DefaultTableModel(
						new Object[][] {
							{null, null, null, null, null, null},
						},
						new String[] {
								"First Name", "Last Name", "E-Mail", "Tier", "Phone Number","Employee"
						}));
			}
		});
		submitBtn2.setBounds(435, 341, 97, 25);
		createNewUserPanel2.add(submitBtn2);

		employeePanel = new JPanel();
		frame.getContentPane().add(employeePanel, "name_1648590061736022");
		employeePanel.setVisible(false);
		employeePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));


		//Searching Area - Label
		lblSearchEmail = new JLabel("Search By E-Mail");
		employeePanel.add(lblSearchEmail);

		//Search TextField, clicking on it deletes the text.
		searchText = new JTextField();
		searchText.setText("Enter e-mail");
		searchText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				searchText.setText("");
			}
		});

		//Every key type send query to database to find matched cases.
		searchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					DefaultTableModel model = (DefaultTableModel) tablePopulateData.getModel();
					model.setRowCount(0);
					user.getSearchedInfo(tablePopulateData, searchText.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		employeePanel.add(searchText);
		searchText.setColumns(10);
		tablePopulateData = new JTable();
		employeePanel.add(tablePopulateData);

		tablePopulateData.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null},
				},
				new String[] {
						"First Name", "Last Name", "E-Mail", "Tier", "Phone Number","Employee"
				}));



		btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				employeePanel.setVisible(false);
				employeePanel.remove(tablePopulateData);
				loginPanel.setVisible(true);
				loginLabel.setText("Logout: Success");

				tablePopulateData.setModel(new DefaultTableModel(
						new Object[][] {
							{null, null, null, null, null, null},
						},
						new String[] {
								"First Name", "Last Name", "E-Mail", "Tier", "Phone Number","Employee"
						}));
			}
		});

		createNew = new JButton("Create New User");
		createNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				employeePanel.setVisible(false);
				createNewUserPanel.setVisible(true);
			}
		});
		employeePanel.add(createNew);
		employeePanel.add(btnLogOut);

		editOptionsPanel = new JPanel();
		frame.getContentPane().add(editOptionsPanel, "name_1211094242985247");
		editOptionsPanel.setLayout(null);

		chckbxPool = new JCheckBox("Pool");
		chckbxPool.setBounds(63, 145, 113, 25);
		chckbxPool.setSelected(false);
		editOptionsPanel.add(chckbxPool);

		chckbxTennis = new JCheckBox("Tennis");
		chckbxTennis.setBounds(182, 145, 76, 25);
		editOptionsPanel.add(chckbxTennis);        


		chckbxFootballField = new JCheckBox("Football Field");
		chckbxFootballField.setBounds(332, 145, 113, 25);
		editOptionsPanel.add(chckbxFootballField);     

		chckbxZoomba = new JCheckBox("Zoomba");
		chckbxZoomba.setBounds(63, 214, 113, 25);
		editOptionsPanel.add(chckbxZoomba);

		chckbxYoga = new JCheckBox("Yoga");
		chckbxYoga.setBounds(182, 214, 113, 25);
		editOptionsPanel.add(chckbxYoga);
		editOptionsPanel.setVisible(false);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btnSubmit_1 = new JButton("Submit");
		btnSubmit_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				int count = 0;
				for(int i =0;i<=4;i++)
				{
					updateOptions.add("0");
				}
				int userTier = Integer.parseInt(user.getTier());



				if(chckbxPool.isSelected())
				{

					updateOptions.set(0, "1");

					count++;
					chckbxPool.setSelected(false);


				}
				if(chckbxTennis.isSelected())
				{

					updateOptions.set(1, "1");
					count++;
					chckbxTennis.setSelected(false);
				}

				if(chckbxFootballField.isSelected())
				{

					updateOptions.set(2, "1");
					count++;
					chckbxFootballField.setSelected(false);
				}
				if(chckbxZoomba.isSelected())
				{
					updateOptions.set(3, "1");
					count++;
					chckbxZoomba.setSelected(false);

				}
				if(chckbxYoga.isSelected())
				{
					updateOptions.set(4, "1");
					count++;
					chckbxYoga.setSelected(false);
				}

				if(count==userTier)
				{
					try {
						user.updateTierOptions(updateOptions);
						for(int i =0;i<=4;i++)
						{
							updateOptions.set(i, "0");
						}
						editOptionsPanel.setVisible(false);
						userInfoPanel.setVisible(true);
						creatingUserInfoTable();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if(count>userTier || count<userTier)
				{
					if(count==0)
					{
						JOptionPane.showMessageDialog(null, "Please make "+userTier+" selection");
					}
					else {
						JOptionPane.showMessageDialog(null, "You must select " + userTier + " different options. Please try again");
						for(int i =0;i<=4;i++)
						{
							updateOptions.set(i, "0");
						}
					}


				}
			}
		});
		btnSubmit_1.setBounds(410, 345, 97, 25);
		editOptionsPanel.add(btnSubmit_1);
		editOptionsPanel.setVisible(false);
	}

	public void getSearchedTable()
	{

		try {
			// grabs the information from user.getInfo() and equals it to the array dbinfo
			dbinfo = user.getInfo();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		//creates table for user information
		userInfoTable = new JTable();
		//sets parameters for table and out puts the information from dbinfo onto the table .
		userInfoTable.setModel(new DefaultTableModel(
				new Object[][] {
					{ "First Name", "Last Name", "E-Mail", "Tier", "Phone Number"},
					{dbinfo.get(0), dbinfo.get(1), dbinfo.get(2), dbinfo.get(3), dbinfo.get(4)},
				},
				new String[] {
						"First Name", "Last Name", "E-Mail", "Tier", "Phone Number"
				}
				));
		userInfoTable.getColumnModel().getColumn(0).setPreferredWidth(157);
		userInfoTable.getColumnModel().getColumn(1).setPreferredWidth(157);
		userInfoTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		userInfoTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		userInfoTable.getColumnModel().getColumn(4).setPreferredWidth(200);

		userInfoTable.setBounds(77, 137, 515, 35);
		//actually adds the the table to the userInfoPanel
		userInfoPanel.add(userInfoTable);
	}

	public void creatingUserInfoTable()
	{

		try {
			// grabs the information from user.getInfo() and equals it to the array dbinfo
			dbinfo = user.getInfo();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		//creates table for user information
		userInfoTable = new JTable();
		//sets parameters for table and out puts the information from dbinfo onto the table .
		userInfoTable.setModel(new DefaultTableModel(
				new Object[][] {
					{ "First Name", "Last Name", "E-Mail", "Tier", "Phone Number"},
					{dbinfo.get(0), dbinfo.get(1), dbinfo.get(2), dbinfo.get(3), dbinfo.get(4)},
				},
				new String[] {
						"First Name", "Last Name", "E-Mail", "Tier", "Phone Number"
				}
				));
		userInfoTable.getColumnModel().getColumn(0).setPreferredWidth(157);
		userInfoTable.getColumnModel().getColumn(1).setPreferredWidth(157);
		userInfoTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		userInfoTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		userInfoTable.getColumnModel().getColumn(4).setPreferredWidth(200);

		userInfoTable.setBounds(77, 137, 515, 35);
		//actually adds the the table to the userInfoPanel
		userInfoPanel.add(userInfoTable);

		try {
			dbinfo2 = user.getInfo2();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userInfoTable2 = new JTable();
		userInfoTable2.setModel(new DefaultTableModel(
				new Object[][] {
					{"Pool", "Tennis Court", "Football Field", "Zoomba Class", "Yoga Class"},
					{dbinfo2.get(0), dbinfo2.get(1), dbinfo2.get(2), dbinfo2.get(3), dbinfo2.get(4)},
				},
				new String[] {
						"Pool", "Tennis Court", "Football Field", "Zoomba Class", "Yoga Class"
				}
				));
		userInfoTable2.setBounds(26, 230, 563, 35);
		userInfoPanel.add(userInfoTable2);
	}
}


