package ivote;

public class ligacaoQuebradaRMI extends Thread {
	public ligacaoQuebradaRMI() {
		// TODO Auto-generated constructor stub
		super();
		this.start();
	}
	public void run() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
