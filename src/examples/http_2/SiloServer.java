package examples.http_2;
public class SiloServer extends JavaWebserver {
	
	SiloServerView siloView;

	/**
	 * Konstruktor mit Port
	 * 
	 * @param listen_port
	 */
	public SiloServer(int listen_port, SiloServerView siloView) {
		super(listen_port);
		
		this.siloView = siloView;
		
		System.out.println("Versuche Server zu starten!");
	}

	/**
	 * Auswerten der Server-Anfrage vom Client
	 */
	@Override
	void anfrageErgebnis(String anfrageText) {
		// Konsolenantwort
		System.out.println("Ausgabe->" + anfrageText);
		
		String answer = "<html><body>";
		answer += "\nvalue_silo_links="+siloView.getValue_silo_links();
		answer += "\n<br/>value_silo_rechts="+siloView.getValue_silo_rechts();
		answer += "\n<br/>MAX_VALUE="+siloView.MAX_VALUE;
		answer += "\n<br/>ventil1="+siloView.isVentil1();
		answer += "\n<br/>ventil2="+siloView.isVentil2();
		answer += "\n<br/>ventil3="+siloView.isVentil3();
		answer += "\n<br/>timeCircle="+siloView.getTimeCircle();
		answer += "\n</html></body>";

		// Antwort an den Client schicken
		clientAntwort(answer); // HTML-Seite in einer Einzelkette
	}
	
	public void setRunMode(boolean b)
	{
//		System.out.println("SiloServer: RUNMODE GESETZT AUF: "+b);
		super.setRunMode(b);
	}

	/**
	 * Der Java-Webserver Teste mich mit dem Aufruf
	 * http://localhost/client?message=Der Java-Webserver
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//
//		SiloServer server = new SiloServer(80);
//
//		server.start();
//	}
}
