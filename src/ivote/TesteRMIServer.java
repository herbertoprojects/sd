package ivote;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

public class TesteRMIServer extends UnicastRemoteObject implements RMIRemoteInterface {

	public TesteRMIServer() throws RemoteException {
		super();
	}


	@Override
	public int registar(String tipo, String nome, String password, String faculdade, String departamento, int telefone,
			String morada, int numeroCc, Date dataCc) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public ArrayList<String> ListFaculdades() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<String> ListaDepartamentos(String nomeFaculdade) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean addDepartamento(String nome, String nomeFaculdade) throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean removeDepartamento(String nomeDep) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean addFaculdade(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean removeFaculdade(String nomeFaculdade) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int criaEleicao(String tipo, Date inicio, Date fim, String titulo, String descricao) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public ArrayList<String> listEleicao(Date inicio, Date fim) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addMesaVoto(String nome, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean removeMesaVoto(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList<String> listMesaVoto() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String detalheEleicao(String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return "teste ola";
	}


	@Override
	public String getTipo(String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setTipo(String tipo, String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Date getDataInicio(String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setDataInicio(Date data, String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Date getDataFim(String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setDataFim(Date data, String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getTitulo(String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setTitulo(String titulo, String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getDescricao(String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setDescricao(String descricao, String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList<String> localVotoEleitor(String nome, int numCC, int telefone) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String estadoMesas(String mesaVoto) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String eleitoresTReal() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String consultaResulPass(String eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<String> getNCC(String nome, int telefone) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setNCC(int nCC, int novoNCC) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getTipo(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setTipo(int nCC, String tipo) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getNome(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setNome(int nCC, String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getPassword(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setPassword(int nCC, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getFacudade(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setFacudade(int nCC, String faculdade) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getDepartamento(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setDepartamento(int nCC, String departamento) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int getTelefone(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean setTelefone(int nCC, int telefone) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getMorada(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setMorada(int nCC, String morada) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Date getDataCC(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setDataCC(int nCC, Date data) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean addMembroMesaVoto(String mesaVoto, int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean removeMembroMesaVoto(String mesaVoto, int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList<String> listaMembrosMesaVoto(String mesaVoto) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean votaAntecipadamente(int nCC, String passwordUser) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean ligarServidor(String nomeMesaVoto, String passwordMesaVoto) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Call: "+ nomeMesaVoto+ " "+ passwordMesaVoto);
		return true;
	}


	@Override
	public boolean desligarServidor(String nomeMesaVoto, String passwordMesaVoto) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean desbloquearUser(String nomeMesaVoto, String passwordMesaVoto, int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean desbloquearVoto(String nomeMesaVoto, String passwordMesaVoto, int nCC, String passwordUser)
			throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean votar(String nomeMesaVoto, String passwordMesaVoto, int nCC, String passwordUser,int voto)
			throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
		
		try {
			TesteRMIServer servidorTeste = new TesteRMIServer();
			LocateRegistry.createRegistry(7000).rebind("DepartamentoInformatica", servidorTeste);
			System.out.println("Servidor ready!!");
		}catch (RemoteException re) {
			System.out.println("Exception in HelloImpl.main: " + re);
		}

	}


	@Override
	public boolean addListaCandidatos(String Eleicao, String nomeLista, ArrayList<int[]> listaCandidatos)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean removeListaCandidatos(String Eleicao, String nomeLista) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList<String> listListasCandidatos(String Eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean addCandidato(String Eleicao, String lista) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean removeCandidato(String Eleicao, String lista) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList<String> listCandidato(String Eleicao, String lista) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
