package ivote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerTCP extends UnicastRemoteObject{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public RMI_1 comunicacaoRMI;
	public ServerSocket server;
	public getOptions opcoes;
	public String nome;
	
	public userInterface menuInterface;

	public ServerTCP() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws IOException{
		ServerTCP servidorTCP = new ServerTCP();
		
		//metodos automaticos
		new cabecalhoProg();
		servidorTCP.opcoes = new getOptions();
		
		//Activar politicas de ligacao
		servidorTCP.ativaPrivilegios();
		
		//Cria comunicacao com servidor RMI

		try {
				servidorTCP.nome = new getScanner().leLinha("Nome da mesa de voto: ");
				servidorTCP.ativaConnectionRMI(servidorTCP.nome);
				//comunicacao com a mesa de voto (nova thread)
				//servidorTCP.comunication = new aceptTerminalVoto(servidorTCP.server, servidorTCP.opcoes, servidorTCP.listaDeTerminais, servidorTCP.comunicacaoRMI,servidorTCP);
				//evocar menu e guardar
				servidorTCP.menuInterface = new userInterface(servidorTCP.comunicacaoRMI,servidorTCP.opcoes,servidorTCP.nome);
				
				// binding ao porto tcp
				servidorTCP.aceptTerminalVoto();
		}catch (RemoteException e) {
			if(new reparaLigacao(servidorTCP.comunicacaoRMI,servidorTCP.opcoes, servidorTCP.nome).ligado) {
				servidorTCP.menuInterface = new userInterface(servidorTCP.comunicacaoRMI,servidorTCP.opcoes,servidorTCP.nome);
			}
			System.out.println("Time out exception...");
		}
	}
	
	public void ativaPrivilegios() {
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
	}
	public void ativaConnectionRMI(String nome) throws RemoteException {
			try {
				comunicacaoRMI = (RMI_1) Naming.lookup("rmi://"+opcoes.ipRmiServer+":"+opcoes.portRmiServer+"/"+nome+"");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
					cliente.setSoTimeout(120000);
					new threadClienteTCP(this.menuInterface.listaDeTerminais, cliente, this.menuInterface.numEleicao);
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
