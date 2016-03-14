package examples.datenbank_1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DBExamplePanel extends JPanel {
	
	private BufferedImage signal_rot;
	private BufferedImage signal_gruen;
	private BufferedImage hupe;
	private BufferedImage hupe_rot;
	private BufferedImage silo_lang;
	private BufferedImage silo_mittel;
	private BufferedImage silo_klein;
 
	private int value_silo_lang = 500;
	private int value_silo_mittel = 200;
	private int value_silo_klein = 670;
	private int value_hupe1 = 1;
	private int value_hupe2 = 0;
	private int value_signal1 = 0;
	private int value_signal2 = 1;

	public DBExamplePanel()
	{
		try {
			silo_lang = ImageIO.read(getClass().getResource("silo_lang_100breit.png"));
			silo_mittel = ImageIO.read(getClass().getResource("silo_mittel_100breit.png"));
			silo_klein = ImageIO.read(getClass().getResource("silo_klein_100breit.png"));
			signal_rot = ImageIO.read(getClass().getResource("signal_rot.png"));
			signal_gruen = ImageIO.read(getClass().getResource("signal_gruen.png"));
			hupe = ImageIO.read(getClass().getResource("hupe.png"));
			hupe_rot = ImageIO.read(getClass().getResource("hupe_rot.png"));			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		init();
	}
	
	private void init()
	{
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		int breite, hoehe;
		
		// Hintergrund weiss zeichnen
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0,  getWidth(),  getHeight());
		
		// Silos, Signallampen und Hupen zeichnen
		g2d.drawImage(silo_lang, 650, 100, this);
		g2d.drawImage(silo_mittel, 500, 230, this);
		g2d.drawImage(silo_klein, 350, 295, this);
		g2d.drawImage((value_signal1==1) ? signal_gruen : signal_rot, 250, 100, this);
		g2d.drawImage((value_signal2==1) ? signal_gruen : signal_rot, 250, 170, this);
		g2d.drawImage((value_hupe1==1) ? hupe : hupe_rot, 250, 250, this);
		g2d.drawImage((value_hupe2==1) ? hupe : hupe_rot, 250, 320, this);
		
		// Fuellstaende der Silos zeichnen
		g2d.setColor(Color.RED);
		breite = 100-40;

		hoehe = (int)(silo_lang.getHeight()*((double)value_silo_lang / 1023d));
		g2d.fillRect(650+20, silo_lang.getHeight()+100-5-hoehe, breite, hoehe);
		
		hoehe = (int)(silo_mittel.getHeight()*((double)value_silo_mittel / 1023d));
		g2d.fillRect(500+20, silo_mittel.getHeight()+230-5-hoehe, breite, hoehe);

		hoehe = (int)(silo_klein.getHeight()*((double)value_silo_klein / 1023d));
		g2d.fillRect(350+20, silo_klein.getHeight()+295-5-hoehe, breite, hoehe);		
	}
}
