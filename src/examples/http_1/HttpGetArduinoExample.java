package examples.http_1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HttpGetArduinoExample extends JPanel {
	
	private final String IP_ARDUINO = "192.168.1.177";
	
	private HTTPClientConnection clientConnection = new HTTPClientConnection();

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

	public HttpGetArduinoExample() {
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

	private void init() {
		new Thread() {
			public void run() {
				String anfrage = "", daten[];
				// String suche =
				// "wann diese Seite aufgerufen wurde, lautet:<P><UL><LI><B>";
				// int start;

				while (true) {
					anfrage = clientConnection.sendRequest("http://"+IP_ARDUINO);
					anfrage = anfrage.replaceAll("<!DOCTYPE HTML><html>", "").replaceAll("</html>", "");
//					 System.out.println(anfrage);

					daten = anfrage.split("<br />");

					for (String s : daten) {
//						 System.out.println(s);

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

					repaint();

					try {
						Thread.sleep(75);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

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

		// Fuellstaende der Silos zeichnen
		g2d.setColor(Color.RED);
		breite = 100 - 40;

		hoehe = (int) (silo_lang.getHeight() * ((double) value_silo_lang / 1023d));
		g2d.fillRect(650 + 20, silo_lang.getHeight() + 100 - 5 - hoehe, breite,	hoehe);

		hoehe = (int) (silo_mittel.getHeight() * ((double) value_silo_mittel / 1023d));
		g2d.fillRect(500 + 20, silo_mittel.getHeight() + 230 - 5 - hoehe, breite, hoehe);

		hoehe = (int) (silo_klein.getHeight() * ((double) value_silo_klein / 1023d));
		g2d.fillRect(350 + 20, silo_klein.getHeight() + 295 - 5 - hoehe, breite, hoehe);
	}
}
