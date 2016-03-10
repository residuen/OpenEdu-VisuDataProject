import java.awt.Component;

import javax.swing.JFrame;


public class VisuFrame extends JFrame {
	
	public VisuFrame(String title)
	{
		super(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 480);
		setVisible(true);
	}
	
	public void addVisuComponent(Component component)
	{
		getContentPane().removeAll();
		getContentPane().add(component);
		
		repaint();
	}

}
