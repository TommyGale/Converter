import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;//All of the imported packages used within this code
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class CurrencyPanel extends JPanel {
	public final static String[] inputlist = {"Euro (EUR)", "US Dollars (USD)", "Australian Dollars (AUD)","Canadian Dollars (CAD)", "Icelandic  Króna (ISK)", "United Arab Emirates Dirham (AED)","South African Rand (ZAR)","Thai Baht (THB)" };
	//List of all possible conversions
	public static final AbstractButton clear = null;
	public static JTextField textField;//The main textfield where all the conversions take place within
	public static JLabel label;// Both labels used within the mainpanel
	public static JLabel counter;
	public static JLabel currency;
	public JComboBox<String> calc;//Converts the values when the options are changed
	public JCheckBox ticked;//The checkbox that reverses the conversions
	public static int count = 1;//Count of conversions
	public MainPanel mainPanel;//allows methods to be called from the other panel
	public static String[] parts;
	public static ArrayList<String[]> newValues = new ArrayList<String[]>();


	CurrencyPanel(){


		ActionListener listener = new ConversiontListener();//Listens to Action events

		calc = new JComboBox<String>(inputlist);//Converts the values when the options changed
		calc.addActionListener(listener); //Allows it to listen to actions
		calc.setToolTipText("List of all conversions.");

		JLabel inputLabel = new JLabel("Enter value:");//Label telling you what to do
		inputLabel.setToolTipText("The label telling you what to do.");

		final JButton convertButton = new JButton("Convert");
		convertButton.addActionListener(listener); //Converts the values when pressed
		convertButton.setToolTipText("Converts the value entered in the textfield.");

		final JButton clear = new JButton("Clear");//Clears all the current data in the application
		clear.addActionListener(listener);
		clear.setToolTipText("Clears all current data.");

		final JButton load = new JButton("Load");//Loads all options for text files
		load.addActionListener(listener);
		load.setToolTipText("Used to load different text files");

		currency =  new JLabel("");

		label = new JLabel("---");//main label to display the converted values
		label.setToolTipText("Displays the converted values");

		textField = new JTextField(5);//textfield to allow input for converted values
		textField.addActionListener(listener);
		textField.setToolTipText("Enter data here to be converted.");

		counter = new JLabel("Conversion count:0 ");//counter for the conversion
		counter.setToolTipText("Keeps a track of the current count.");

		add(calc);//Adding all of the buttons and labels to the main panel
		add(inputLabel);
		add(textField);
		add(convertButton);
		add(currency);
		add(label);
		add(load);
		setPreferredSize(new Dimension(800, 80));//Set the preferred size of the application
		setBackground(Color.gray);//Choosing the colour of the application



		textField.setDocument(new PlainDocument() {
			public void insertString(int offset, String text, javax.swing.text.AttributeSet numCheck)//Does not allow letters to pass through
					throws BadLocationException {
				if (this.getLength() + text.length() > 8)//Limit the length of number that can be entered into the textfield
					return;
				try {
					Integer.parseInt(text);//Converts the string to Integer only
					super.insertString(offset, text, numCheck);//Reference listing the variables
				} catch (Exception e) {//Catch if the value entered is not a number 
					JOptionPane.showMessageDialog(null, "You can only enter number values", "Error message!",//Error message displayed
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText().trim();//Makes the code easier to read and more specific

				if (text.isEmpty())//If the textfield is empty then the message below is displayed
					JOptionPane.showMessageDialog(null, "The conversion textfield cannot be empty", "Error message!",
							JOptionPane.ERROR_MESSAGE);
				else {
					convertButton.doClick(count);//If information is entered then the button is pressed using the enter key
				}

			}

		});

		convertButton.addActionListener(new ActionListener() {//Same thing as above but for mouse input
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText().trim();

				if (text.isEmpty())
					JOptionPane.showMessageDialog(null, "The conversion textfield cannot be empty", "Error message!",
							JOptionPane.ERROR_MESSAGE);
				else {
					counter.setText("Conversion count: " + count);//Calculation for the count
					count++;
				}
			}

		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");//Empty all of the fields and returns the count to 0
				currency.setText("");
				label.setText("---");
				counter.setText("Conversion count:0");
				count = 1;
			}
		});

		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newValues.clear();
				CurrencyPanel.fileLoader(load);

			}
		});



		MainPanel.clearBothPanel(clear);//Calling the methods from the other panel
		MainPanel.countBothPanel(convertButton);

	}
	public static void countBothPanel(JButton convertButton) {//allows these methods to be called to the other panel
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.getText().trim();
				counter.setText("Conversion count: " + count);
				count++;
			}


		});

	}

	public static void clearBothPanel(JButton clear) {//allows these methods to be called to the other panel
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				currency.setText("");
				label.setText("---");
				counter.setText("Conversion count:0");
				count = 1;
			}

		});

	}	
	{       

		File file = new File("file\\currency.txt");//Default file loaded

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			String line = in.readLine();
			while ( line != null ) {
				parts = line.split(",");//Seperating by comma
				newValues.add(parts);//Adds the values to the arraylist
				line = in.readLine();
			}

			in.close();
			JOptionPane.showMessageDialog(null,
					"Default file loaded",
					"Currency File", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Invalid file path", "Error message!",//Error message displayed
					JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void fileLoader(JButton load) {{   //File chooser for the load button
		JFileChooser loadFile = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		loadFile.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("Desktop")));
		int newFactor = loadFile.showOpenDialog(null);

		if (newFactor == JFileChooser.APPROVE_OPTION) {
			File file = loadFile.getSelectedFile();

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				String line = in.readLine();
				while ( line != null ) {
					parts = line.split(",");//Seperating by comma
					newValues.add(parts);//Adds the values to the arraylist
					line = in.readLine();

				}

				in.close();//Display a message when a new file is loaded
				JOptionPane.showMessageDialog(null,
						"New file loaded",
						"Currency File", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) { 
				JOptionPane.showMessageDialog(null, "Invalid file path", "Error message!",//Error message displayed
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}}



	private class ConversiontListener implements ActionListener {//Class where all the conversions happen

		public void actionPerformed(ActionEvent event) {

			String text = textField.getText().trim();

			if (MainPanel.ticked.isSelected() == false) {//What the program will do if the checkbox is not ticked

				double value = Double.parseDouble(text);//Values entered into the textfield
				//The factor applied during the conversion

				double factor = 0;

				//The offset applied during the conversion.
				double offset = 0;

				//Setup the correct factor/offset values depending on required conversion
				switch (calc.getSelectedIndex()) {//Switches between each of the cases

				case 0: // Euro
					parts = (String[]) newValues.get(0);
					factor = Double.parseDouble(parts[1]);
					currency.setText(parts[2]);
					break;

				case 1: // US Dollars
					parts = (String[]) newValues.get(1);
					factor = Double.parseDouble(parts[1]);
					currency.setText(parts[2]);
					break;

				case 2: // Australian Dollars
					parts = (String[]) newValues.get(2);
					factor = Double.parseDouble(parts[1]);
					currency.setText(parts[2]);
					break;

				case 3: // Canadian Dollars
					parts = (String[]) newValues.get(3);
					factor = Double.parseDouble(parts[1]);
					currency.setText(parts[2]);
					break;

				case 4: // Icelandic Krona
					parts = (String[]) newValues.get(4);
					factor = Double.parseDouble(parts[1]);
					currency.setText(parts[2]);
					break;

				case 5: // United Arab Emirates Dirham
					parts = (String[]) newValues.get(5);
					factor = Double.parseDouble(parts[1]);
					currency.setText(parts[2]);
					break;

				case 6: // South African Rand 
					parts = (String[]) newValues.get(6);
					factor = Double.parseDouble(parts[1]);
					currency.setText(parts[2]);
					break;

				case 7: // Thai Baht 
					parts = (String[]) newValues.get(7);
					factor = Double.parseDouble(parts[1]);
					currency.setText(parts[2]);
					break;
				}

				double result = factor * value + offset;//How the calculation is performed
				DecimalFormat df = new DecimalFormat("###.##");//Setting the result to two decimal places
				label.setText(df.format(result));

			} else {//Performs the exact same thing but backwards due to ticked being true
				String text1 = textField.getText().trim();

				double value = Double.parseDouble(text1);

				//The factor applied during the conversion
				double factor = 0;

				//The offset applied during the conversion.
				double offset = 0;

				//Setup the correct factor/offset values depending on required conversion
				switch (calc.getSelectedIndex()) {

				case 0: // British Pound
					currency.setText("£");
					factor = 1;
					break;

				case 1: // British Pound
					currency.setText("£");
					factor = 1;
					break;

				case 2: // British Pound
					currency.setText("£");
					factor = 1;
					break;

				case 3: // British Pound
					currency.setText("£");
					factor = 1;
					break;

				case 4: // British Pound
					currency.setText("£");
					factor = 1;
					break;

				case 5: // British Pound
					currency.setText("£");
					factor = 1;
					break;

				case 6: // British Pound
					currency.setText("£");
					factor = 1;
					break;

				case 7: // British Pound
					currency.setText("£");
					factor = 1;
					break;
				}

				double result = factor * value + offset;
				DecimalFormat df = new DecimalFormat("###.##");
				label.setText(df.format(result));

			}
		}

	}

}//End of the application
