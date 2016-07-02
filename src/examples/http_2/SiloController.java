package examples.http_2;

import javax.swing.JOptionPane;

public class SiloController extends Thread {
	
	private SiloServerView siloView;
	private boolean runMode = true;
	
	public SiloController(SiloServerView siloView)
	{
		this.siloView = siloView;
	}
	
	public void run()
	{
		while(runMode)
		{
			siloView.nextCircle();
			
			if(siloView.getValue_silo_links()/SiloServerView.MAX_VALUE >= 1)
			{
				JOptionPane.showMessageDialog(null, "Achtung: Linkes Silo voll gelaufen!");
				
				runMode = false;
			}
			
			if(siloView.getValue_silo_links()/SiloServerView.MAX_VALUE <= 0)
			{
				JOptionPane.showMessageDialog(null, "Achtung: Linkes Silo leer gelaufen!");
				
				runMode = false;
			}

			if(siloView.getValue_silo_rechts()/SiloServerView.MAX_VALUE >= 1)
			{
				JOptionPane.showMessageDialog(null, "Achtung: Rechtes Silo voll gelaufen!");
				
				runMode = false;
			}

			if(siloView.getValue_silo_rechts()/SiloServerView.MAX_VALUE <= 0)
			{
				JOptionPane.showMessageDialog(null, "Achtung: Rechtes Silo leer gelaufen!");
				
				runMode = false;
			}

			try { sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	public boolean isRunMode() {
		return runMode;
	}

	public void setRunMode(boolean runMode) {
		this.runMode = runMode;
	}
}
