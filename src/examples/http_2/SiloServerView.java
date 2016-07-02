package examples.http_2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SiloServerView extends JPanel {
	
//	private final String IP_ARDUINO = "192.168.1.177";
	
	// Objekt, um HTTP-Daten aus dem Internet oder Intranet zu lesen 
//	private HTTPClientConnection clientConnection = new HTTPClientConnection();
	
	public static final double MAX_VALUE = 920;

	// Image-Objekte fuer grafik_Komponenten
//	private BufferedImage signal_rot;
//	private BufferedImage signal_gruen;
//	private BufferedImage hupe;
//	private BufferedImage hupe_rot;
	private BufferedImage silo_extralang_links;
	private BufferedImage silo_extralang_rechts;
	private BufferedImage ventil_blank;
	private BufferedImage ventil_rot;
	private BufferedImage ventil_gruen;
	
//	private Rectangle2D rohr;
//	private Rectangle2D rohr2;

	private int shiftLinks  = -200;
	private int shiftRechts = 0;
	
	private int timeCircle = 0;
	
	// Variablen zum Speichern der Messdaten 
	private double value_silo_links = MAX_VALUE * 0.75;
	private double value_silo_rechts = MAX_VALUE * 0.5;
	
//	private int value_hupe1 = 1;
//	private int value_hupe2 = 0;
//	private int value_signal1 = 0;
//	private int value_signal2 = 1;
	
	private double ventil1durchfl = 15;
	private double ventil2durchfl = 5;
	private double ventil3durchfl = 3;
	
	private boolean ventil1 = false;
	private boolean ventil2 = false;
	private boolean ventil3 = false;

	private int silo_links_x = 350;
	private int silo_rechts_x = 550;
	private int silo_links_y = 100;
	private int silo_rechts_y = 100;
//	private int innenabstand_silos;
	
	// Position der Rohre
	private int xRohr_links, yRohr_links = 375;
	private int xRohr_mitte, yRohr_mitte = 400;
	private int xRohr_rechts, yRohr_rechts = 420;
	private int hoeheRohr = 20;
	
	private BasicStroke bigStroke = new BasicStroke(3f);
	private BasicStroke thinStroke = new BasicStroke(1f);
	
	public SiloServerView()
	{
		// Laden der Bilder
		try {
			silo_extralang_links  = ImageIO.read(getClass().getResource("/examples/images/silo_extralang_100breit.png"));
			silo_extralang_rechts = ImageIO.read(getClass().getResource("/examples/images/silo_extralang_100breit.png"));
//			signal_rot 	 = ImageIO.read(getClass().getResource("/examples/images/signal_rot.png"));
//			signal_gruen = ImageIO.read(getClass().getResource("/examples/images/signal_gruen.png"));
//			hupe = ImageIO.read(getClass().getResource("/examples/images/hupe.png"));
//			hupe_rot = ImageIO.read(getClass().getResource("/examples/images/hupe_rot.png"));
			ventil_blank = ImageIO.read(getClass().getResource("/examples/images/ventil_blank.png"));
			ventil_rot 	 = ImageIO.read(getClass().getResource("/examples/images/ventil_rot.png"));
			ventil_gruen = ImageIO.read(getClass().getResource("/examples/images/ventil_gruen.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		init();
	}

	{
		// Worker-Thread erzeugen
	/*	new Thread()
		{
			public void run()
			{
				String anfrage = ""; 	// Speichert anfrageergebnis
				String daten[];			// zum speichern von HTTP-Daten in einem Array

				// Endlosschleife
				while(true)
				{
					
					// HTTP-Anfrage an URL oder IP-Adresse senden, Ergebnis in anfrage speichern
//					anfrage = clientConnection.sendRequest("http://172.16.1.1");
//					 System.out.println("anfrage="+anfrage);
					
					// Loeschen nicht benoetigter Zeichen 
					anfrage = anfrage.replaceAll("<!DOCTYPE HTML><html>", "").replaceAll("</html>", "");
//					 System.out.println("anfrage="+anfrage);
					
					// Daten nach Zeichenkette "<br />" teilen und in Array speichern
					daten = anfrage.split("<br />");

					// Schleife zum durchlaufen des Arrays
					for (String s : daten) {
//						 System.out.println(s);

						// Die Werte aller Analog- und Digitalpins auslesen und in Variablen speichern 
						if (s.contains("pin,2,"))
							value_signal1 = Integer.parseInt(s.split(",")[2]);
						else
						if (s.contains("pin,3,"))
							value_signal2 = Integer.parseInt(s.split(",")[2]);
						else
						if (s.contains("pin,4,"))
							value_hupe1 = Integer.parseInt(s.split(",")[2]);
						else
						if (s.contains("pin,5,"))
							value_hupe2 = Integer.parseInt(s.split(",")[2]);
						else
						if (s.contains("adc,0,")) {
							value_silo_lang = Integer.parseInt(s.split(",")[2]);

							if (value_silo_lang > 900)
								value_silo_lang = 900;
						}
						else
						if (s.contains("adc,1,")) {
							value_silo_mittel = Integer.parseInt(s.split(",")[2]);

							if (value_silo_mittel >= 750)
								value_silo_mittel = 750;
						}
						else
						if (s.contains("adc,2,")) {
							value_silo_klein = Integer
									.parseInt(s.split(",")[2]);

							if (value_silo_klein >= 600)
								value_silo_klein = 600;
						}
						
						continue;
					}

					// Anweisung zum Neuzeichnen des Fensters geben
					repaint();
					
					// Warte-Zyklus von 75ms einstellen
					try {
						Thread.sleep(75);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}	// Ende der Endlosschleife
			}
		}.start();	// Starten des Worker-Threads */
	}

	/**
	 * Zeichnen der Grafik-Componenten und Fuellstaende
	 */
	public void paintComponent(Graphics g)
	{
		// Zeichenschablone
		Graphics2D g2d = (Graphics2D) g;

		// Zwischenspeicher
		int breite, hoehe, deltaBreite;
		int breiteRohr, ventil_x, ventil_y;
		int innenabstand_silos = silo_rechts_x - (silo_links_x+silo_extralang_links.getWidth());
		
		// Hintergrund weiss zeichnen
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// Silo Zuleitungsrohr
		breiteRohr = silo_rechts_x-(silo_links_x+silo_extralang_links.getWidth());

		xRohr_links = silo_links_x - 75;
		xRohr_mitte = silo_links_x+silo_extralang_links.getWidth();
		xRohr_rechts = silo_rechts_x + silo_extralang_rechts.getWidth();
		
		// linkes Rohr zeichnen
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(shiftLinks + xRohr_links, shiftRechts + yRohr_links, 75, hoeheRohr);
		g2d.setColor(ventil1 ? Color.RED : Color.BLACK);
		g2d.setStroke(ventil1 ? bigStroke : thinStroke);
		g2d.drawRect(shiftLinks + xRohr_links, shiftRechts + yRohr_links, 75, hoeheRohr);
		g2d.setStroke(new BasicStroke(1f));
				
		// mittleres Rohr zeichnen
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(shiftLinks + xRohr_mitte, shiftRechts + yRohr_mitte, breiteRohr, hoeheRohr);
		g2d.setColor(ventil2 ? Color.RED : Color.BLACK);
		g2d.setStroke(ventil2 ? bigStroke : thinStroke);
		g2d.drawRect(shiftLinks + xRohr_mitte, shiftRechts + yRohr_mitte, breiteRohr, hoeheRohr);
		g2d.setStroke(new BasicStroke(1f));

		// rechtes Rohr zeichnen
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(shiftLinks + xRohr_rechts, shiftRechts + yRohr_rechts, 100, hoeheRohr);
		g2d.setColor(ventil3 ? Color.RED : Color.BLACK);
		g2d.setStroke(ventil3 ? bigStroke : thinStroke);
		g2d.drawRect(shiftLinks + xRohr_rechts, shiftRechts + yRohr_rechts, 100, hoeheRohr);
		g2d.setStroke(thinStroke);

		g2d.setColor(Color.BLACK);
		
		ventil_x = silo_links_x+silo_extralang_links.getWidth() + (int)(0.5*(innenabstand_silos - ventil_blank.getWidth()));
		ventil_y = yRohr_mitte + (int)(0.5*(hoeheRohr - ventil_blank.getHeight()));
		
		// Ventile und Ventiltext zeichnen
		g2d.drawImage(ventil1 ? ventil_gruen : ventil_rot, shiftLinks + xRohr_links + 5,  shiftRechts + yRohr_links + (hoeheRohr/2 - ventil_blank.getHeight()/2), this);
		g2d.drawString("Ventil 1: ", shiftLinks + xRohr_links + 5, shiftRechts + yRohr_links + (hoeheRohr/2 - ventil_blank.getHeight()/2)-5);
		g2d.drawString((int)ventil1durchfl+"%/sek.", shiftLinks + xRohr_links + 5, shiftRechts + yRohr_links + ventil_blank.getHeight());
		
		
		g2d.drawImage(ventil2 ? ventil_gruen : ventil_rot, shiftLinks + ventil_x, shiftRechts + ventil_y, this);
		g2d.drawString("Ventil 2: ", shiftLinks + ventil_x + 5, shiftRechts + ventil_y + (hoeheRohr/2 - ventil_blank.getHeight()/2) + 5);
		g2d.drawString((int)ventil2durchfl+"%/sek.", shiftLinks + ventil_x + 5, shiftRechts + ventil_y + hoeheRohr + ventil_blank.getHeight());

		g2d.drawImage(ventil3 ? ventil_gruen : ventil_rot, shiftLinks + xRohr_rechts + 25, shiftRechts + yRohr_rechts + (hoeheRohr/2 - ventil_blank.getHeight()/2), this);
		g2d.drawString("Ventil 3: ", shiftLinks + xRohr_rechts + 35, shiftRechts + yRohr_rechts - 25);
		g2d.drawString((int)ventil3durchfl+"%/sek.", shiftLinks + xRohr_rechts + 35, shiftRechts + yRohr_rechts + ventil_blank.getHeight());

		// Silos und Silo-Text zeichnen
		g2d.drawImage(silo_extralang_links, shiftLinks + silo_links_x, shiftRechts + silo_links_y, this);
		g2d.drawString("Silo 1 (S1): "+(Math.round(((double)value_silo_links/MAX_VALUE)*100d))+"%", shiftLinks + silo_links_x+5,shiftRechts +  silo_links_y - 5);

		g2d.drawImage(silo_extralang_rechts, shiftLinks + silo_rechts_x, shiftRechts + silo_rechts_y, this);
		g2d.drawString("Silo 2 (S2): "+(Math.round(((double)value_silo_rechts/MAX_VALUE)*100d))+"%", shiftRechts + shiftLinks + silo_rechts_x+5, shiftRechts + silo_rechts_y - 5);
		
		
		// Signallampen, gruene Lampen bei value==1, sonst rote Lampen
//		g2d.drawImage((value_signal1 == 1) ? signal_gruen : signal_rot, 250, 100, this);
//		g2d.drawImage((value_signal2 == 1) ? signal_gruen : signal_rot, 250, 170, this);
//		
//		// Hupen zeichnen, graue hupe bei value==1, sonst rote Hupe
//		g2d.drawImage((value_hupe1 == 1) ? hupe : hupe_rot, 250, 250, this);
//		g2d.drawImage((value_hupe2 == 1) ? hupe : hupe_rot, 250, 320, this);

		// Breite der Fuellstandsbalken
		breite = (int)(silo_extralang_links.getWidth()*0.66); // - 40;
		deltaBreite = silo_extralang_links.getWidth() - breite;

		// Fuellstaende der Silos zeichnen
		g2d.setColor(Color.RED);

		// Hoehe des langen Silos berechnen ...
		hoehe = (int) (silo_extralang_links.getHeight() * ((double) value_silo_links / 1023d));
		// Füllstand links zeichnen
		g2d.fillRect(shiftLinks + silo_links_x + deltaBreite/2, shiftRechts + silo_extralang_links.getHeight() + 100 - 5 - hoehe, breite,	hoehe);

		// Hoehe des langen Silos berechnen ...
		hoehe = (int) (silo_extralang_rechts.getHeight() * ((double) value_silo_rechts / 1023d));
		
		// Füllstand rechts zeichnen
		g2d.fillRect(shiftLinks + silo_rechts_x + deltaBreite/2, shiftRechts + silo_extralang_rechts.getHeight() + 100 - 5 - hoehe, breite,	hoehe);
//		// Rohr rechts zeichnen
//		g2d.fillRect(silo_rechts_x + 20, silo_extralang_rechts.getHeight() + 100 - 5 - hoehe, breite,	hoehe);

		g2d.setColor(Color.BLUE);
		g2d.drawString("Zeit: "+timeCircle+" Sekunden", 5, 15);
	}
	
	public void setVentil1(boolean ventil1) {
		this.ventil1 = ventil1;
	}

	public void setVentil2(boolean ventil2) {
		this.ventil2 = ventil2;
	}

	public void setVentil3(boolean ventil3) {
		this.ventil3 = ventil3;
	}

	public boolean isVentil1() {
		return ventil1;
	}

	public boolean isVentil2() {
		return ventil2;
	}

	public boolean isVentil3() {
		return ventil3;
	}

	public int getTimeCircle() {
		return timeCircle;
	}

	public void setValue_silo_links(double value_silo_links) {		
		this.value_silo_links = 0.01 * MAX_VALUE * value_silo_links;
	}

	public void setValue_silo_rechts(double value_silo_rechts) {
		this.value_silo_rechts = 0.01 * MAX_VALUE * value_silo_rechts;
	}

	public void setVentil1durchfl(double ventil1durchfl) {
		this.ventil1durchfl = ventil1durchfl;
	}

	public void setVentil2durchfl(double ventil2durchfl) {
		this.ventil2durchfl = ventil2durchfl;
	}

	public void setVentil3durchfl(double ventil3durchfl) {
		this.ventil3durchfl = ventil3durchfl;
	}

	public double getValue_silo_links() {
		return value_silo_links;
	}

	public double getValue_silo_rechts() {
		return value_silo_rechts;
	}

	public void nextCircle()
	{
		// Füllstand linkes Silo
		if(ventil1)
			value_silo_links += MAX_VALUE * (ventil1durchfl/100d);
		
		if(ventil2)
			value_silo_links -=  MAX_VALUE * (ventil2durchfl/100d);
		
		// Füllstand rechtes Silo
		if(ventil2)
			value_silo_rechts +=  MAX_VALUE * (ventil2durchfl/100d);
		
		if(ventil3)
			value_silo_rechts -=  MAX_VALUE * (ventil3durchfl/100d);
		
		timeCircle++;
		
		repaint();
	}
	
	public void reset()
	{
		timeCircle = 0;
		
		repaint();
	}
}
