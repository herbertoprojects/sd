package ivote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class reparaLigacao extends Thread {
	
	RMI_1 comunicacao;
	getOptions opcoes;
	String nome;
	boolean ligado;
	
	public reparaLigacao(RMI_1 comunicacao, getOptions opcoes, String nome) {
		// TODO Auto-generated constructor stub
		super();
		this.comunicacao = comunicacao;
		this.opcoes = opcoes;
		this.nome = nome;
		this.ligado = false;
		this.start();
	}
	public void run() {
		int num= 0;
		boolean teste = true;
		while(teste) {
			try {
				comunicacao = (RMI_1) Naming.lookup("rmi://"+opcoes.ipRmiServer+":"+opcoes.portRmiServer+"/"+nome+"");
				teste = false;
				
			} catch (MalformedURLException e) {

			} catch (RemoteException e) {
				try {
					comunicacao = (RMI_1) Naming.lookup("rmi://"+opcoes.ipRmiServer_2+":"+opcoes.portRmiServer_2+"/"+nome+"");
				} catch (MalformedURLException | RemoteException | NotBoundException e1) {
					System.out.println("Não estableceu ligação...");
					num++;
					if(num==30) {
						return;
					}
					System.out.println("Sem ligação...");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				teste = false;
				
			} catch (NotBoundException e) {

			}
		}
		
		ligado = true;
		return;
	}
}
