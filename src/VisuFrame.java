import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;


public class VisuFrame extends JFrame {
	
	private JDesktopPane dtp = new JDesktopPane();
	
	public VisuFrame(String title)
	{
		super(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		setSize(800, 630);
		add(dtp);
		dtp.setVisible(true);
		setVisible(true);
	}
	
	public void addVisuComponent(Component component)
	{
		JInternalFrame iFrame = new JInternalFrame();
		iFrame.add(component);
		iFrame.setSize(700, 600);
		iFrame.show();
//		iFrame.setVisible(true);
		
//		dtp.removeAll();
		dtp.add(iFrame);
		dtp.repaint();
	}

}
