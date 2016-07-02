package examples.http_2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class SiloServerPanel extends JPanel implements ActionListener {
	
	private SiloServerView siloView = new SiloServerView();
	
	private HashMap<String, JTextField> textfelder = new HashMap<String, JTextField>();
	private HashMap<String, Component> buttons = new HashMap<String, Component>();
	
	private SiloController pws;
	
	private SiloServer server = null;
	
	public SiloServerPanel()
	{
		setLayout(new BorderLayout());
		
		init();
	}
	
	private void init()
	{
		Box vBox = Box.createVerticalBox();
		
		Dimension dimButton = new Dimension(120, 30);
		Dimension dimTField = new Dimension(210, 23);
		
		JLabel label;
		JTextField textfield;
		JPanel panel;
		JButton button;
		JToggleButton tButton;
		Box vBox2;
		
		button = new JButton("Server starten");
		button.addActionListener(this);
		button.setPreferredSize(dimButton);
		button.setMaximumSize(dimButton);
		button.setMinimumSize(dimButton);
		buttons.put("server", button);
		vBox.add(button);
		
		button = new JButton("Reset");
		button.addActionListener(this);
		button.setPreferredSize(dimButton);
		button.setMaximumSize(dimButton);
		button.setMinimumSize(dimButton);
		vBox.add(button);
		
		button = new JButton("Start");
		button.addActionListener(this);
		button.setPreferredSize(dimButton);
		button.setMaximumSize(dimButton);
		button.setMinimumSize(dimButton);
		buttons.put("start", button);
		vBox.add(button);
		vBox.add(Box.createVerticalStrut(8));
		
		tButton = new JToggleButton("Ventil 1");
		tButton.addActionListener(this);
		button.setPreferredSize(dimButton);
		tButton.setMaximumSize(dimButton);
		tButton.setMinimumSize(dimButton);
		buttons.put("Ventil 1", tButton);
		vBox.add(tButton);
		
		tButton = new JToggleButton("Ventil 2");
		tButton.addActionListener(this);
		button.setPreferredSize(dimButton);
		tButton.setMaximumSize(dimButton);
		tButton.setMinimumSize(dimButton);
		buttons.put("Ventil 2", tButton);
		vBox.add(tButton);
		
		tButton = new JToggleButton("Ventil 3");
		tButton.addActionListener(this);
		button.setPreferredSize(dimButton);
		tButton.setMaximumSize(dimButton);
		tButton.setMinimumSize(dimButton);
		buttons.put("Ventil 3", tButton);
		vBox.add(tButton);
		vBox.add(Box.createVerticalStrut(8));
		
		vBox2 = Box.createVerticalBox();
		panel = new JPanel();
//		panel.setLayout(new GridLayout(1,1)); //new BoxLayout(panel, BoxLayout.Y_AXIS));
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		label = new JLabel("<html>Ventil 1<br>Durchfl. %/sek.</html>");
		vBox2.add(label);
		textfield = new JTextField("15");
		textfield.setName("ventil1abl");
		textfield.addActionListener(this);
		textfield.setMaximumSize(dimTField);
		textfelder.put("ventil1abl", textfield);
		vBox2.add(textfield);
//		panel.add(vBox2, BorderLayout.WEST);
		vBox.add(vBox2);
		vBox.add(Box.createVerticalStrut(8));
		
		vBox2 = Box.createVerticalBox();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		label = new JLabel("<html>Ventil 2<br>Durchfl. %/sek.</html>");
		vBox2.add(label);
		textfield = new JTextField("5");
		textfield.setName("ventil2abl");
		textfield.addActionListener(this);
		textfield.setMaximumSize(dimTField);
		textfelder.put("ventil2abl", textfield);
		vBox2.add(textfield);
		vBox.add(vBox2);
		vBox.add(Box.createVerticalStrut(8));
		
		vBox2 = Box.createVerticalBox();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		label = new JLabel("<html>Ventil 3<br>Durchfl. %/sek.</html>");
		vBox2.add(label);
		textfield = new JTextField("3");
		textfield.setName("ventil3abl");
		textfield.addActionListener(this);
		textfield.setMaximumSize(dimTField);
		textfelder.put("ventil3abl", textfield);
		vBox2.add(textfield);
		vBox.add(vBox2);
		vBox.add(Box.createVerticalStrut(12));
		
		vBox2 = Box.createVerticalBox();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		label = new JLabel("<html>Füllstand S1<br>Startwert in %</html>");
		vBox2.add(label);
		textfield = new JTextField("75");
		textfield.setName("silo1start");
		textfield.addActionListener(this);
		textfield.setMaximumSize(dimTField);
		textfelder.put("silo1start", textfield);
		vBox2.add(textfield);
		vBox.add(vBox2);
		vBox.add(Box.createVerticalStrut(8));
		
		vBox2 = Box.createVerticalBox();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		label = new JLabel("<html>Füllstand S2<br>Startwert in %</html>");
		vBox2.add(label);
		textfield = new JTextField("50");
		textfield.setName("silo2start");
		textfield.addActionListener(this);
		textfield.setMaximumSize(dimTField);
		textfelder.put("silo2start", textfield);
		vBox2.add(textfield);
//		vBox2.add(new JLabel(" "));
		vBox.add(vBox2);
		vBox.add(Box.createVerticalStrut(8));

		add(vBox, BorderLayout.WEST);
		add(siloView, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String cmd = arg0.getActionCommand();
		System.out.println("cmd="+cmd);
		
		if(cmd.contains("Server starten"))
		{
			((JButton)buttons.get("server")).setText("Server stoppen");
			
			if(server == null)
			{
				server = new SiloServer(80, siloView);
				server.start();
			}
		}
		else
			if(cmd.contains("Server stoppen"))
			{
				if(server != null)
				{
					server.setRunMode(false);
					server = null;
					
					System.out.println("SERVER STOPPEN");
				}
				
				((JButton)buttons.get("server")).setText("Server starten");
			}
			else
				if(cmd.contains("Reset"))
				{
					killController();
					
					((JToggleButton)buttons.get("Ventil 1")).setSelected(false);
					((JToggleButton)buttons.get("Ventil 2")).setSelected(false);
					((JToggleButton)buttons.get("Ventil 3")).setSelected(false);
					siloView.setVentil1(false);
					siloView.setVentil2(false);
					siloView.setVentil3(false);
					
					siloView.setVentil1durchfl(convert2Double(textfelder.get("ventil1abl")));
					siloView.setVentil2durchfl(convert2Double(textfelder.get("ventil2abl")));
					siloView.setVentil3durchfl(convert2Double(textfelder.get("ventil3abl")));
					
					siloView.setValue_silo_links(convert2Double(textfelder.get("silo1start")));
					siloView.setValue_silo_rechts(convert2Double(textfelder.get("silo2start")));
					
					siloView.reset();
					
					siloView.repaint();
				}
				else
					if(cmd.contains("Start"))
					{
						killController();
						pws = new SiloController(siloView);
						pws.start();
						
						((JButton)buttons.get("start")).setText("Stopp");
					}
					else
						if(cmd.contains("Stopp"))
						{
							killController();
							
							((JButton)buttons.get("start")).setText("Start");
						}
						else
							if(cmd.contains("Ventil 1"))
							{
								siloView.setVentil1(((JToggleButton)arg0.getSource()).isSelected());
							}
							else
								if(cmd.contains("Ventil 2"))
								{
									siloView.setVentil2(((JToggleButton)arg0.getSource()).isSelected());
								}
								else
									if(cmd.contains("Ventil 3"))
									{
										siloView.setVentil3(((JToggleButton)arg0.getSource()).isSelected());
									}
									else
									{
										JTextField tf = (JTextField)arg0.getSource();
										cmd = tf.getName();
										double value = convert2Double(tf);
										System.out.println(cmd);
										
										if(cmd.contains("ventil1abl"))
										{
											siloView.setVentil1durchfl(value); // System.out.println(tf.getText());
										}
										else
											if(cmd.contains("ventil2abl"))
											{
												siloView.setVentil2durchfl(value); // System.out.println(tf.getText());
											}
											else
												if(cmd.contains("ventil3abl"))
												{
													siloView.setVentil3durchfl(value); // System.out.println(tf.getText());
												}
												else
													if(cmd.contains("silo1start"))
													{
														siloView.setValue_silo_links(value); // System.out.println(tf.getText());
													}
													else
														if(cmd.contains("silo2start"))
														{
															siloView.setValue_silo_rechts(value); // System.out.println(tf.getText());
														}
						}
				
		siloView.repaint();
		
//		System.out.println(cmd);
	}
	
	private double convert2Double(JTextField tf)
	{
		double retV = 0;
		
		retV = Double.parseDouble(tf.getText());
		
		if(retV < 0)
		{
			retV = 0;
			tf.setText("0");
		}
		
		if(retV > 100)
		{
			retV = 100;
			tf.setText("100");
		}
		
		return retV;
	}
	
	private void killController()
	{
		if(pws != null)
		{
			pws.setRunMode(false);
			pws = null;	
		}
	}
}
