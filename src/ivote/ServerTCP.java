package ivote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerTCP extends UnicastRemoteObject{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public RMIRemoteInterface comunicacaoRMI;
	public ServerSocket server;
	public getOptions opcoes;
	
	public userInterface menuInterface;

	public ServerTCP() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws IOException{
		ServerTCP servidorTCP = new ServerTCP();
		
		//metodos automaticos
		new cabecalhoProg();
		servidorTCP.opcoes = new getOptions();
		
		
		//Activar politicas de ligacao6+66+
		servidorTCP.ativaPrivilegios();
		
		//Cria comunicacao com servidor RMI
		servidorTCP.ativaConnectionRMI();
		
		//comunicacao com a mesa de voto (nova thread)
		//servidorTCP.comunication = new aceptTerminalVoto(servidorTCP.server, servidorTCP.opcoes, servidorTCP.listaDeTerminais, servidorTCP.comunicacaoRMI,servidorTCP);
		//evocar menu e guardar
		servidorTCP.menuInterface = new userInterface(servidorTCP.comunicacaoRMI);
		
		// binding ao porto tcp
		servidorTCP.aceptTerminalVoto();
		

	}
	
	public void ativaPrivilegios() {
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
	}
	public void ativaConnectionRMI() {
		try {
			comunicacaoRMI = (RMIRemoteInterface) Naming.lookup("rmi://"+opcoes.ipRmiServer+":"+opcoes.portRmiServer+"/DepartamentoInformatica");
		}catch (Exception e) {
			System.out.println("Exception in main: " + e);
			//e.printStackTrace();
		}
	}
	
	public void aceptTerminalVoto() {
		try {
			server = new ServerSocket(opcoes.portServerTCP);
			while(true) {
				if(!mesasLivres()) {
					synchronized (menuInterface) {
						menuInterface.wait();
					}
				}
				
				if(mesasLivres()) {
					System.out.println("Espera de terminal...");
					Socket cliente = server.accept();
					new threadClienteTCP(this.menuInterface.listaDeTerminais, cliente);
				}else {
					break;
				}
				
			}
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean mesasLivres() {
		if(menuInterface.listaDeTerminais.size()>0) {
			for (terminalVoto temp:menuInterface.listaDeTerminais) {
				if(temp.threadAssociada ==null || !temp.threadAssociada.cliente.isConnected()) {
					return true;
				}
			}
			return false;
		}else {
			return false;
		}
	}
	
	
}
