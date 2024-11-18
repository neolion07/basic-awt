import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainFrame extends Frame implements ActionListener{
	// COMPONENT DECLARATION
	Panel namePanel, idPanel, genderPanel, agePanel, occupationPanel, buttonPanel;
	Panel headerPanel, statusPanel;
	Button submitButton, listButton, clearButton, exitButton;
	Button closeListButton;
	Label nameLabel, idLabel, genderLabel, ageLabel, occupationLabel;
	Label headerLabel, statusLabel;
	Dialog listDialog;
	List listList; // what da heeeeeell
	Label countLabel;
	TextField nameField, idField, ageField, occupationField;
	Choice genderChoice;
	
	public MainFrame(){
		prepareGUI();
	}
	
	private void prepareGUI(){
		// COMPONENT INITIALIZATION
		headerPanel = new Panel(new GridLayout(1, 1));
		headerLabel = new Label("Please enter the employee's data.", Label.CENTER);
		
		buttonPanel = new Panel(new GridLayout(1, 4));
		submitButton = new Button("Save");
		listButton = new Button("List");
		clearButton = new Button("Clear");
		exitButton = new Button("Exit");
		
		namePanel = new Panel(new GridLayout(1, 2));
		nameLabel = new Label("Name: ");
		nameField = new TextField();
		
		idPanel = new Panel(new GridLayout(1, 2));
		idField = new TextField();
		idLabel = new Label("ID: ");
		
		genderPanel = new Panel(new GridLayout(1, 2));
		genderLabel = new Label("Gender: ");
		genderChoice = new Choice();
		
		agePanel = new Panel(new GridLayout(1, 2));
		ageField = new TextField();
		ageLabel = new Label("Age: ");
		
		occupationPanel = new Panel(new GridLayout(1, 2));
		occupationField = new TextField();
		occupationLabel = new Label("Occupation: ");
		
		statusPanel = new Panel(new GridLayout(1, 1));
		statusLabel = new Label("...", Label.CENTER);
		
		listDialog = new Dialog(this, "Employee List");
		listList = new List();
		countLabel = new Label("-");
		closeListButton = new Button("OK");
		// obj.setBounds(int xpos, int ypos, int width, int height);
		
		// COMPONENT SETTINGS
		genderChoice.add("Male");
		genderChoice.add("Female");
		listDialog.setSize(300, 200);
		listDialog.setLayout(new GridLayout(3, 1));		
		
		// ACTION LISTENERS
		submitButton.addActionListener(this);
		listButton.addActionListener(this);
		clearButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		closeListButton.addActionListener(this);
		
		// ADDING COMPONENTS TO CONTAINERS
		headerPanel.add(headerLabel);
		add(headerPanel);
		
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		add(namePanel);
		
		idPanel.add(idLabel);
		idPanel.add(idField);
		add(idPanel);
		
		genderPanel.add(genderLabel);
		genderPanel.add(genderChoice);
		add(genderPanel);
		
		agePanel.add(ageLabel);
		agePanel.add(ageField);
		add(agePanel);
		
		occupationPanel.add(occupationLabel);
		occupationPanel.add(occupationField);
		add(occupationPanel);
		
		buttonPanel.add(submitButton);
		buttonPanel.add(listButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);
		add(buttonPanel);
		
		statusPanel.add(statusLabel);
		add(statusPanel);
		
		listDialog.add(listList);
		listDialog.add(countLabel);
		listDialog.add(closeListButton);
		
		// FRAME SETTINGS
		setSize(400, 300);
		setTitle("Employee Registry");
		setVisible(true);
		setLayout(new GridLayout(8, 1));
	}
	public void actionPerformed(ActionEvent ev){
		if (ev.getSource() == submitButton){
			if (
				nameField.getText().equals("") ||
				idField.getText().equals("") ||
				ageField.getText().equals("") ||
				occupationField.getText().equals("")
			){
				statusLabel.setText("Some fields are missing.");
				return;
			}
			try {
				File out = new File("employees.txt");
				FileWriter fw = new FileWriter(out, true);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write(nameField.getText());
				bw.write(",");
				
				bw.write(idField.getText());
				bw.write(",");
				
				bw.write(genderChoice.getSelectedItem());
				bw.write(",");
				
				bw.write(ageField.getText());
				bw.write(",");
				
				bw.write(occupationField.getText());
				bw.newLine();
				
				bw.close();
				statusLabel.setText("Saved to 'employees.txt'");
			} catch (IOException ex){
				ex.printStackTrace();
			}
		}
		
		if (ev.getSource() == listButton){
			try {
				File in = new File("employees.txt");
				FileReader fr = new FileReader(in);
				BufferedReader br = new BufferedReader(fr);
				
				String line;
				while ((line = br.readLine()) != null){
					listList.add(line);
				}
				String countStr = "";
				countStr += listList.getItemCount();
				countStr += " employee(s) registered";
				br.close();
				countLabel.setText(countStr);
				listDialog.setVisible(true);
			} catch (IOException ex){
				statusLabel.setText("Error while reading 'employees.txt'");
				ex.printStackTrace();
			}
		}
		
		if (ev.getSource() == clearButton){
			nameField.setText("");
			idField.setText("");
			ageField.setText("");
			occupationField.setText("");
			statusLabel.setText("Cleared the form's contents.");
		}
		
		if (ev.getSource() == closeListButton){
			listDialog.setVisible(false);
			listList.removeAll();
		}
		
		if (ev.getSource() == exitButton) System.exit(0);
	}
}
