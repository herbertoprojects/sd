package ivote;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class TESTE_rmiServer extends UnicastRemoteObject implements RMI_1{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TESTE_rmiServer() throws RemoteException {
		// TODO Auto-generated constructor stub
		super();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
		
		try {
			TESTE_rmiServer servidorTeste = new TESTE_rmiServer();
			LocateRegistry.createRegistry(7000).rebind("rmi", servidorTeste);
			System.out.println("Servidor ready!!");
		}catch (RemoteException re) {
			System.out.println("Exception in HelloImpl.main: " + re);
		}

	}

	@Override
	public String registar(String tipo, int numeroCc, String dataCc, String nome, String password, int telefone,
			String morada, String no_faculd, String no_depart) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(dataCc);
		return "Registrado";
	}

	@Override
	public boolean removerUtilizador(int NCC) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> ListDepartamentos(int id_faculd) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> ListFaculdades() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testeNCC(int ncc) throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean addDepartamento(String sigla, String nomeDepart, int id_dep, int id_fac) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeDepartamento(int id_dep, int id_fac) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addFaculdade(String sigla, String nomeFaculd, int id) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFaculdade(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String criaEleicao(String tipo, String inicio, String fim, String titulo, String descricao, int id)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> listEleicao(String inicio, String fim) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> listEleicao() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addListaCandidatos(int id, int id_eleicao, String membro1, String membro2, String membro3,
			String membro4, String membro5) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeListaCandidatos(int id_LC) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> listListasCandidatos(int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addMesaVoto(int id, int id_depart, int id_faculd, int id_elei, String user, String pass)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addMesaVoto(int id, int id_faculd, int id_elei, String user, String pass) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeMesaVoto(int id_MV) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> listMesaVoto(int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> listMesaVoto() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTipo(int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean setTipo(int id_elei, String n_tipo) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDataInicio(int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getDataFim(int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean setDataInicio(String d_inicio, int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setDataFim(String d_fim, int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTitulo(int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean setTitulo(String n_titulo, int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescricao(int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean setDescricao(String n_descricao, int id_elei) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNCC(String nome_p, int telefone_p) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setNCC(int nCC, int novoNCC) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTipoP(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setTipoP(int nCC, String n_tipo) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNome(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setNome(int nCC, String n_nome) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPassword(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setPassword(int nCC, String n_password) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFacudade(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setFacudade(int nCC, String n_faculdade) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDepartamento(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setDepartamento(int nCC, String n_departamento) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTelefone(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setTelefone(int nCC, int n_telefone) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMorada(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setMorada(int nCC, String n_morada) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDataCC(int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setDataCC(int nCC, String n_data) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String addMembrosMesaVoto(int mesaVoto, int nCC1, int nCC2, int nCC3) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeMembroMesaVoto(int mesaVoto) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ligarServidor(String nomeMesaVoto, String passwordMesaVoto) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean desligarServidor(String nomeMesaVoto, String passwordMesaVoto) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean desbloquearUser(String nomeMesaVoto, String passwordMesaVoto, int nCC) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean desbloquearVoto(String nomeMesaVoto, String passwordMesaVoto, int nCC, String passwordUser)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bloquearVoto(String nomeMesaVoto, String passwordMesaVoto, int nCC, String passwordUser)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean votar(String nomeMesaVoto, String passwordMesaVoto, int nCC, String passwordUser, int voto)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> ListDepartamentos(String faculdadeTemp) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
