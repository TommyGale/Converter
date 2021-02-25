import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;//All of the imported packages used within this code
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class MainPanel extends JPanel { //The start of the program 

	public final static String[] inputlist = { "inches/cm", "Miles/Kilometres", "Pounds/Kilograms", "Gallons/Litres","Feet/Metres", "Celsius/Kelvin", "Acres/Hectare" };
	//List of all possible conversions
	public static final AbstractButton clear = null;
	public static JTextField textField;//The main textfield where all the conversions take place within
	public static JLabel label;// Both labels used within the mainpanel
	public static JLabel counter;
	public JComboBox<String> calc;//Converts the values when the options are changed
	public static JCheckBox ticked;//The checkbox that reverses the conversions
	public static int count = 1;//Count of conversions
	public CurrencyPanel currencyPanel;//allows methods to be called from the other panel 
	public static String[] parts;
	public static ArrayList<String[]> newValues = new ArrayList<String[]>();
	JMenuBar setupTaskBar() {//Where the taskbar is created

		JMenuBar menuBar = new JMenuBar();//Creates the initial base for the menu
		menuBar.setToolTipText("Indicates where all the submenus are added.");//Tooltips explaining what things do when highlighted within the program

		JMenu m1 = new JMenu("File");//Creating the menus within the menubar
		m1.setIcon(new ImageIcon("img/file.png"));//Displaying of the icons and their paths
		m1.setToolTipText("Opens the File menu.");

		JMenu m2 = new JMenu("Help");
		m2.setIcon(new ImageIcon("img/help.png"));
		m2.setToolTipText("Opens the Help menu.");

		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_E);//Declaring the Mnemonics
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));//Allowing it to react to the key and choosing the mask you prefer to use
		exit.setIcon(new ImageIcon("img/exit.png"));
		exit.setToolTipText("Allows you to exit the application.");

		JMenuItem about = new JMenuItem("About");
		about.setMnemonic(KeyEvent.VK_A);
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		about.setIcon(new ImageIcon("img/about.png"));
		about.setToolTipText("Gives you a brief description of the application.");

		menuBar.add(m1);//Adds the menus and submenus to the main menubar
		menuBar.add(m2);
		m1.add(exit);
		m2.add(about);

		exit.addActionListener(new ActionListener() {//Allows the program to listen to the exit option
			public void actionPerformed(ActionEvent e) {
				System.exit(0);//Terminates the program when clicked
			}
		});

		about.addActionListener(new ActionListener() {//Allows the program to listen to the about option
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"This purpose of this application is to allow you to convert between different values." + "\n"//Displays the message when pressed
								+ "Author name - Thomas Gale" + "\n" + "Copyright \u00a9 2018",
								"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		return menuBar;//Returns the menubar to its default state
	}

	MainPanel() {//Mainpanel is where everything you see on the screen is shown

		ActionListener listener = new ConversiontListener();//Listens to Action events

		calc = new JComboBox<String>(inputlist);//Converts the values when the options changed
		calc.addActionListener(listener); //Allows it to listen to actions
		calc.setToolTipText("List of all conversions.");

		JLabel inputLabel = new JLabel("Enter value:");//Label telling you what to do
		inputLabel.setToolTipText("The label telling you what to do.");

		final JButton convertButton = new JButton("Convert");
		convertButton.addActionListener(listener); //Converts the values when pressed
		convertButton.setToolTipText("Converts the value entered in the textfield.");

		JButton clear = new JButton("Clear");//Clears all the current data in the application
		clear.addActionListener(listener);
		clear.setToolTipText("Clears all current data.");

		ticked = new JCheckBox("Reverse conversion");//Tickbox allowing you to reverse conversion depending on if it is ticked or not
		ticked.addActionListener(listener);
		ticked.setToolTipText("Choose which way to convert.");

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
		add(label);
		add(clear);
		add(counter);
		add(ticked);
		setPreferredSize(new Dimension(800, 80));//Set the preferred size of the application
		setBackground(Color.LIGHT_GRAY);//Choosing the colour of the application

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
				label.setText("---");
				counter.setText("Conversion count:0");
				count = 1;
			}
		});
		CurrencyPanel.clearBothPanel(clear);//Calling the methods from the other panel
		CurrencyPanel.countBothPanel(convertButton);
	}




	public static void countBothPanel(JButton convertButton) {//allows these methods to be called to the other panel
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText().trim();
				if (text.isEmpty())
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
				label.setText("---");
				counter.setText("Conversion count:0");
				count = 1;
			}

		});

	}	


	public class ConversiontListener implements ActionListener {//Class where all the conversions happen

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

				case 0: // Inches/Cm
					factor = 2.54;
					break;

				case 1: // Miles/Kilometres
					factor = 1.60934;
					break;

				case 2: // Pounds/Kilograms
					factor = 0.453592;
					break;

				case 3: // Gallons/Litres
					factor = 4.54609;
					break;

				case 4: // Feet/Metres
					factor = 0.3048;
					break;

				case 5: // Celsius/Kelvin
					offset = 273.15;
					factor = 1;
					break;

				case 6: // Acres/Hectare
					factor = 0.404686;
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

				case 0: // Cm/Inches
					factor = 0.393701;
					break;

				case 1: // Kilometres/Miles
					factor = 0.621371;
					break;

				case 2: // Kilograms/Pounds
					factor = 2.20462;
					break;

				case 3: // Litres/Gallons
					factor = 0.219969;
					break;

				case 4: // Metres/Feet
					factor = 3.28084;
					break;

				case 5: // Kelvin/Celsius
					offset = -273.15;
					factor = 1;
					break;

				case 6: // Hectare/Acres
					factor = 2.47105;
					break;
				}

				double result = factor * value + offset;
				DecimalFormat df = new DecimalFormat("###.##");
				label.setText(df.format(result));

			}
		}

	}
}//End of the application
