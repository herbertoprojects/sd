package ivote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface dadosEleicoes extends Remote {
	public void imprimeTexto (String texto) throws RemoteException;
}
