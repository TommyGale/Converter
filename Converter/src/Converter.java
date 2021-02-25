import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;//Import the JFrame package
import javax.swing.JPanel;

public class Converter {//The application itself

	public static void main(String[] args) {//The main method of the program

		JFrame frame = new JFrame("Converter");//Title of the application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Closes the program when shutdown

		JPanel masterPanel = new JPanel();//Creating a new panel
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));//Showing the layout of the program

		MainPanel panel = new MainPanel();//Creating a new panel
		frame.setJMenuBar(panel.setupTaskBar());//Adds the taskbar to the panel

		CurrencyPanel CurrencyPanel = new CurrencyPanel();//Creating a new panel
		masterPanel.add(panel);//adding both the panels to the masterpanel
		masterPanel.add(CurrencyPanel);

		frame.getContentPane().add(masterPanel);//shows the content within the masterPanel

		panel.currencyPanel = CurrencyPanel;//allows the linking of the panels to be processed into the other java classes
		CurrencyPanel.mainPanel = panel;

		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		CurrencyPanel.setBorder(BorderFactory.createLineBorder(Color.black));


		frame.pack();//The whole frame
		frame.setVisible(true);//Allow you to see 
	}
}//End of main method

