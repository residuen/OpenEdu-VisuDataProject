

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Initialisiert wichtige Objekte
 * setzt das Look&Feel und baut die GUI auf
 * @author bettray
 */
public class GuiBuilder
{
	public GuiBuilder()
	{
		initLookAndFeel();	// Setzen des Look & Feels
		
//		HashMap<String, Object> inputComponents = new HashMap<String,Object>(); // Nimmt verschiedene Compontenten auf	
//		String userName = System.getProperty("user.home");
//		userName = userName.substring(userName.lastIndexOf("\\")+1);
//		System.out.println("userName="+userName);

		JLabel status = new JLabel("Status:");
		status.setOpaque(true);
		status.setForeground(Color.BLACK);
		status.setBackground(Color.WHITE);
		
		VisuFrame mainFrame = new VisuFrame("VisuDataProject - Visualisierung von Prozessdaten");
		mainFrame.getContentPane().setLayout(new BorderLayout());
		
//		JScrollPane leftScrollpane = new JScrollPane(new IconMenuPanel(inputComponents), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
//		MainPanel mainPanel = new MainPanel(inputComponents);
//		mainPanel.setLayout(new GridLayout(1,1));
//		Box hBox = Box.createHorizontalBox();
		
//		inputComponents.put("neworder", new NewOrderPanel(inputComponents, dataList));
//		inputComponents.put("vieworders", new ViewOrdersPanel(inputComponents, ViewOrdersPanel.REQUESTER));
//		inputComponents.put("viewexecutiver", new ViewOrdersPanel(inputComponents, ViewOrdersPanel.EXECUTIVER));
//		inputComponents.put("viewadministration", new AdministratorPanel(inputComponents));
//		inputComponents.put("status", status);
				
//		mainPanel.add(hBox);
		
		mainFrame.setVisible(true);
		mainFrame.getContentPane().validate();
	}

	private void initLookAndFeel() {
		
		// Setzen des Look & Feels auf die System-Optik
		String ui = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	
		try {
			UIManager.setLookAndFeel(ui);
		} catch (ClassNotFoundException e1) {
			alternativeLF();
		} catch (InstantiationException e1) {
			alternativeLF();
		} catch (IllegalAccessException e1) {
			alternativeLF();
		} catch (UnsupportedLookAndFeelException e1) {
			alternativeLF();
		}
	}
	
	private void alternativeLF()
	{
		String ui;
		
		if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{
			ui = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
		}
		else
		{	
			ui = UIManager.getSystemLookAndFeelClassName();
		}
		// Setzen des Look & Feels auf die System-Optik
		try {
			UIManager.setLookAndFeel(ui);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
