package ivote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class realTimeDados extends UnicastRemoteObject implements dadosEleicoes {

	protected realTimeDados() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void imprimeTexto(String texto) throws RemoteException {
		System.out.println("Servidor: "+texto);
		
	}

}
