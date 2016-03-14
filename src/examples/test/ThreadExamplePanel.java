package examples.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ThreadExamplePanel extends JPanel {
	
	// Image-Objekte fuer grafik_Komponenten
	private BufferedImage signal_rot;
	private BufferedImage signal_gruen;
	private BufferedImage hupe;
	private BufferedImage hupe_rot;
	private BufferedImage silo_lang;
	private BufferedImage silo_mittel;
	private BufferedImage silo_klein;
 
	// Variablen zum Speichern der Messdaten 
	private int value_silo_lang = 500;
	private int value_silo_mittel = 200;
	private double value_silo_klein = 670;
	private int value_hupe1 = 1;
	private int value_hupe2 = 0;
	private int value_signal1 = 0;
	private int value_signal2 = 1;

	public ThreadExamplePanel()
	{
		// Laden der Bilder
		try {
			silo_lang = ImageIO.read(getClass().getResource("/examples/images/silo_lang_100breit.png"));
			silo_mittel = ImageIO.read(getClass().getResource("/examples/images/silo_mittel_100breit.png"));
			silo_klein = ImageIO.read(getClass().getResource("/examples/images/silo_klein_100breit.png"));
			signal_rot = ImageIO.read(getClass().getResource("/examples/images/signal_rot.png"));
			signal_gruen = ImageIO.read(getClass().getResource("/examples/images/signal_gruen.png"));
			hupe = ImageIO.read(getClass().getResource("/examples/images/hupe.png"));
			hupe_rot = ImageIO.read(getClass().getResource("/examples/images/hupe_rot.png"));			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		init();
	}
	
	private void init()
	{
		// Worker-Thread erzeugen
		new Thread()
		{
			public void run()
			{
				// Endlosschleife
				while(true)
				{
					// per Zufall Zustaende ( 1 oder 0) von Hupen und Signalen erzeugen
					if(value_silo_lang%100 == 0)
					{
						value_hupe1 = (int)(Math.random()*2.0);
						value_hupe2 = (int)(Math.random()*2.0);
						value_signal1 = (int)(Math.random()*2.0);
						value_signal2 = (int)(Math.random()*2.0);
					}
					
					// Silouellstand bis Grenzwert hochzaehlen
					value_silo_lang += 6;
					if(value_silo_lang>=900) // Wenn Grenzwert erreicht ... 
						value_silo_lang = 0; // ... Wert zuruecksetzen
					
					// Silouellstand bis Grenzwert runterzaehlen
					value_silo_mittel-=6;
					if(value_silo_mittel<=0) 	 // Wenn Grenzwert erreicht ... 
						value_silo_mittel = 750; // ... Wert zuruecksetzen
					
					// Silouellstand bis Grenzwert vervielfachen um Faktor 1.25
					value_silo_klein*= 1.25;
					if(value_silo_klein>=600) 	// Wenn Grenzwert erreicht ... 
						value_silo_klein = 20; 	// ... Wert zuruecksetzen
					
					// Anweisung zum Neuzeichnen des Fensters geben
					repaint();
					
					// Warte-Zyklus von 75ms einstellen
					try { Thread.sleep(75); }
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}.start();	// Starten des Worker-Threads
	}
	
	/**
	 * Zeichnen der Grafik-Componenten und Fuellstaende
	 */
	public void paintComponent(Graphics g)
	{
		// Zeichenschablone
		Graphics2D g2d = (Graphics2D) g;

		// Zwischenspeicher
		int breite, hoehe;

		// Hintergrund weiss zeichnen
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// Silos zeichnen
		g2d.drawImage(silo_lang, 650, 100, this);
		g2d.drawImage(silo_mittel, 500, 230, this);
		g2d.drawImage(silo_klein, 350, 295, this);
		
		// Signallampen, gruene Lampen bei value==1, sonst rote Lampen
		g2d.drawImage((value_signal1 == 1) ? signal_gruen : signal_rot, 250, 100, this);
		g2d.drawImage((value_signal2 == 1) ? signal_gruen : signal_rot, 250, 170, this);
		
		// Hupen zeichnen, graue hupe bei value==1, sonst rote Hupe
		g2d.drawImage((value_hupe1 == 1) ? hupe : hupe_rot, 250, 250, this);
		g2d.drawImage((value_hupe2 == 1) ? hupe : hupe_rot, 250, 320, this);

		// Breite der Fuellstandsbalken
		breite = 100 - 40;

		// Fuellstaende der Silos zeichnen
		g2d.setColor(Color.RED);

		// Hoehe des langen Silos berechnen ...
		hoehe = (int) (silo_lang.getHeight() * ((double) value_silo_lang / 1023d));
		// Langes Silo zeichnen
		g2d.fillRect(650 + 20, silo_lang.getHeight() + 100 - 5 - hoehe, breite,	hoehe);

		// Hoehe des mittleren Silos berechnen ...
		hoehe = (int) (silo_mittel.getHeight() * ((double) value_silo_mittel / 1023d));
		// mittleres Silo zeichnen
		g2d.fillRect(500 + 20, silo_mittel.getHeight() + 230 - 5 - hoehe, breite, hoehe);

		// Hoehe des kleinen Silos berechnen ...
		hoehe = (int) (silo_klein.getHeight() * ((double) value_silo_klein / 1023d));
		// kleines Silo zeichnen
		g2d.fillRect(350 + 20, silo_klein.getHeight() + 295 - 5 - hoehe, breite, hoehe);
	}
}
