package ivote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class RMI extends UnicastRemoteObject implements RMI_1 {
	
	protected RMI() throws RemoteException {
		super();
	}
	
	private static final long serialVersionUID = 1L;
	
	public static Connection conn = null;
	
	public static void main(String[] args) {
		int portaRMI;
		RMI_1 rmi = null;
		
		try {
			if(args.length != 2) {
				System.out.println("Argumentos em falta!");
				System.exit(0);
			}
			
			portaRMI = Integer.parseInt(args[1]);
			
			try {
				primario(portaRMI);
				rmi = (RMI_1) Naming.lookup("rmi://"+args[0]+":"+portaRMI+"/rmi");
				System.out.println("Primário ligado!");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (NotBoundException | RemoteException e){
				e.printStackTrace();
			}
			
		} catch(Exception e) {
			System.out.println("RMI: Erro na porta "+e);
			System.exit(1);
		}
		
	}
	
	private static void primario(int portaRMI) {
		System.out.println("RMI primário");
		try {
			LocateRegistry.createRegistry(portaRMI);
			RMI_1 rmi = new RMI();
			Naming.rebind("rmi://localhost:"+portaRMI+"/rmi", rmi);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "proj", "proj");
			System.out.println("Conectado há base de dados"+conn);
			conn.setAutoCommit(false);
		} catch(RemoteException | MalformedURLException | SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/*
	private static void secundario(RMI_1 rmi, int portaRMI) {s
			
		}
	}*/

	
	public String registar(String tipo, int numeroCc, String dataCc, String nome, String password, int telefone, String morada, String no_faculd, String no_depart){
		try {
			int id_faculd = nmtoidFaculd(no_faculd);
			int id_depart = nmtoidDepart(no_depart);
			Statement st = conn.createStatement();
			//System.out.println("'"+tipo+"', '"+numeroCc+"', to_date('"+dataCc+"','yyyy/mm/dd')), '"+nome+"', '"+password+"', '"+telefone+"', '"+morada+"', '"+id_faculd+"', '"+id_depart+"')");
			st.executeUpdate("Insert into pessoa values ('"+tipo+"', '"+numeroCc+"', to_date('"+dataCc+"','yyyy/mm/dd'), '"+nome+"', '"+password+"', '"+telefone+"', '"+morada+"', '"+id_faculd+"', '"+id_depart+"')");
			conn.commit();
			return "type : register , ok : true";
		} catch (SQLException | RemoteException e) {
			e.printStackTrace();
			return "type : register , ok : false";
		}
	}
	
	public ArrayList <String> ListFaculdades() throws RemoteException {
		ArrayList<String> faculdades = new ArrayList<String>();
		try {
			Statement st = conn.createStatement();
			String sql = "select * from faculdade";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    faculdades.add(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getInt(3));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return faculdades;
	}
	
	
	public ArrayList <String> ListDepartamentos(int id_faculd) throws RemoteException {
		ArrayList<String> departamentos = new ArrayList<String>();
		try {
			Statement st = conn.createStatement();
			String sql = "select * from departamento where id_faculd = ('"+id_faculd+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    departamentos.add(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getInt(3));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return departamentos;
	}
	
	public boolean addDepartamento(String sigla,String nomeDepart, int id, int id_faculd)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into departamento values ('"+sigla+"', '"+nomeDepart+"', '"+id+"', '"+id_faculd+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeDepartamento(int id_depart, int id_faculd)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from departamento where id = ('"+id_depart+"') and id_faculd = ('"+id_faculd+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean addFaculdade(String sigla, String nomeFaculd, int id)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into faculdade values ('"+sigla+"', '"+nomeFaculd+"', '"+id+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeFaculdade(int id_faculd)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from faculdade where id = ('"+id_faculd+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public String criaEleicao(String tipo, String inicio, String fim, String titulo, String descricao, int id) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into eleicao values ('"+tipo+"', to_date('"+inicio+"','yyyy/mm/dd hh:mi'), to_date('"+fim+"','yyyy/mm/dd hh:mi'), '"+titulo+"', '"+descricao+"', '"+id+"')");
			conn.commit();
			return "type : create_election , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			return "type : create_election , ok : false";
		}
	}
	
	public ArrayList <String> listEleicao(String inicioE, String fimE) throws RemoteException {
		ArrayList<String> eleicoes = new ArrayList<String>();
		try {
			Statement st = conn.createStatement();
			String sql = "select * from eleicao where dataInicio >= ('"+inicioE+"') and dataFim <= ('"+fimE+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    eleicoes.add(rs.getString(1)+" - "+rs.getDate(2)+" - "+rs.getDate(3)+" - "+rs.getString(4)+" - "+rs.getString(5)+" - "+rs.getInt(6));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eleicoes;
	}
	
	public ArrayList <String> listEleicao() throws RemoteException {
		ArrayList<String> eleicoes = new ArrayList<String>();
		try {
			Statement st = conn.createStatement();
			String sql = "select * from eleicao";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    eleicoes.add(rs.getString(1)+" - "+rs.getDate(2)+" - "+rs.getDate(3)+" - "+rs.getString(4)+" - "+rs.getString(5)+" - "+rs.getInt(6));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eleicoes;
	}
	
	
	public String addListaCandidatos(int id, int id_eleicao, String membro1, String membro2, String membro3, String membro4, String membro5) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into pessoasLista values ('"+id+"', '"+id_eleicao+"', '"+membro1+"', '"+membro2+"', '"+membro3+"', '"+membro4+"', '"+membro5+"')");
			conn.commit();
			return "type : add_peopleList , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			return "type : add_peopleList , ok : false";
		}
	}
	

	public boolean removeListaCandidatos(int id_LC) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from pessoasLista where id = ('"+id_LC+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//depois ter em atenção o tipo de eleição!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public ArrayList <String> listListasCandidatos(int id_elei) throws RemoteException {
		ArrayList<String> candidatos = new ArrayList<String>();
		try {
			Statement st = conn.createStatement();
			String sql = "select * from pessoaslista where id_eleicao = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    candidatos.add(rs.getInt(1)+" - "+rs.getInt(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - "+rs.getString(5)+" - "+rs.getString(6)+" - "+rs.getString(7));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return candidatos;
	}
	
	
	/*
	public boolean addCandidato(String Eleicao,String lista)throws RemoteException {
		
	}
	
	public boolean removeCandidato(String Eleicao, String lista)throws RemoteException {
		
	}
	
	public ArrayList <String> listCandidato(String Eleicao, String lista)throws RemoteException {
		
	}
	*/
	
	
	//Em falta String user, String pass!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public String addMesaVoto(int id, int id_depart, int id_faculd, int id_elei) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into mesavoto values ('"+id+"', '"+id_depart+"', '"+id_faculd+"', '"+id_elei+"')");
			conn.commit();
			return "type : add_voteTable , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			return "type : add_voteTable , ok : false";
		}
	}
	
	public String addMesaVoto(int id, int id_faculd, int id_elei) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into mesavoto values ('"+id+"', null, '"+id_faculd+"', '"+id_elei+"')");
			conn.commit();
			return "type : add_voteTable , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			return "type : add_voteTable , ok : false";
		}
	}
	
	public boolean removeMesaVoto(int id_MV) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from mesavoto where id = ('"+id_MV+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList <String> listMesaVoto(int id_elei) throws RemoteException {
		ArrayList<String> mesas = new ArrayList<String>();
		try {
			Statement st = conn.createStatement();
			String sql = "select * from mesavoto where id_eleicao = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    mesas.add(rs.getInt(1)+" - "+rs.getInt(2)+" - "+rs.getInt(3)+" - "+rs.getInt(4));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mesas;
	}
	
	public ArrayList <String> listMesaVoto() throws RemoteException {
		ArrayList<String> mesas = new ArrayList<String>();
		try {
			Statement st = conn.createStatement();
			String sql = "select * from mesavoto";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    mesas.add(rs.getInt(1)+" - "+rs.getInt(2)+" - "+rs.getInt(3)+" - "+rs.getInt(4));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mesas;
	}
	
	/*
	public String detalheEleicao(String eleicao) throws RemoteException {
		
	}
	*/
	
	public boolean getTipo(int id_elei) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select tipo from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setTipo(int id_elei, String n_tipo) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update eleicao set tipo = ('"+n_tipo+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean getDataInicio(int id_elei) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select datainicio from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getDate(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getDataFim(int id_elei) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select datafim from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getDate(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setDataInicio(String d_inicio, int id_elei) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update eleicao set datainicio = ('"+d_inicio+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setDataFim(String d_fim, int id_elei) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update eleicao set datafim = ('"+d_fim+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getTitulo(int id_elei) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select titulo from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setTitulo(String n_titulo,int id_elei) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update eleicao set titulo = ('"+n_titulo+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getDescricao(int id_elei) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select descricao from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setDescricao(String n_descricao, int id_elei) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update eleicao set descricao = ('"+n_descricao+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	public ArrayList <String> localVotoEleitor(String nome,int numCC,int telefone) throws RemoteException {
		
	}
	
	public String estadoMesas(String mesaVoto) throws RemoteException {
		
	}
	
	public String eleitoresTReal() throws RemoteException {
		
	}

	public String consultaResulPass(String eleicao) throws RemoteException {
		
	}
	*/
	
	public boolean getNCC (String nome_p,int telefone_p) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select numeroCc from pessoa where nome = ('"+nome_p+"') and telefone = ('"+telefone_p+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getInt(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setNCC(int nCC, int novoNCC) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set numeroCc = ('"+novoNCC+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getTipoP(int nCC)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select tipo from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setTipoP(int nCC, String n_tipo) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set tipo = ('"+n_tipo+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getNome(int nCC)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select nome from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setNome(int nCC, String n_nome) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set nome = ('"+n_nome+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getPassword(int nCC)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select password from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setPassword(int nCC, String n_password) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set password = ('"+n_password+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getFacudade(int nCC)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select faculdade from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setFacudade(int nCC, String n_faculdade) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set faculdade = ('"+n_faculdade+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getDepartamento(int nCC)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select departamento from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setDepartamento(int nCC, String n_departamento) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set departamento = ('"+n_departamento+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getTelefone(int nCC)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select telefone from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getInt(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setTelefone(int nCC, int n_telefone) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set telefone = ('"+n_telefone+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getMorada(int nCC)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select morada from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setMorada(int nCC, String n_morada) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set morada = ('"+n_morada+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getDataCC(int nCC)throws RemoteException {
		try {
			Statement st = conn.createStatement();
			String sql = "select dataCc from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    System.out.println((rs.getString(1)));
			}
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setDataCC(int nCC, String n_data) throws RemoteException {
		try {
			PreparedStatement st = conn.prepareStatement("Update pessoa set dataCc = to_date('"+n_data+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	public boolean addMembroMesaVoto(String mesaVoto,int nCC) throws RemoteException {
		
	}
	
	public boolean removeMembroMesaVoto(String mesaVoto,int nCC) throws RemoteException {
		
	}
	
	public ArrayList <String> listaMembrosMesaVoto(String mesaVoto) throws RemoteException {
		
	}

	public boolean votaAntecipadamente(int nCC, String passwordUser) throws RemoteException {
		
	}

	public boolean ligarServidor(String nomeMesaVoto,String passwordMesaVoto) throws RemoteException {
		
	}
	
	public boolean desligarServidor(String nomeMesaVoto,String passwordMesaVoto) throws RemoteException {
		
	}
	
	public boolean desbloquearUser(String nomeMesaVoto,String passwordMesaVoto,int nCC) throws RemoteException {
		
	}

	public boolean desbloquearVoto(String nomeMesaVoto,String passwordMesaVoto, int nCC, String passwordUser) throws RemoteException {
		
	}
	
	public boolean votar(String nomeMesaVoto,String passwordMesaVoto, int nCC, String passwordUser) throws RemoteException {
		
	}
	*/
	
	private int nmtoidDepart(String nome_b)throws RemoteException {
		int tempNum = 0;
		try {
			Statement st = conn.createStatement();
			String sql = "select id from departamento where nome = ('"+nome_b+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			tempNum = rs.getInt(1);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempNum;
	}
	
	private int nmtoidFaculd(String nome_b)throws RemoteException {
		int tempNum = 0;
		try {
			Statement st = conn.createStatement();
			String sql = "select id from faculdade where nome = ('"+nome_b+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			tempNum = rs.getInt(1);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempNum;
	}

	@Override
	public boolean testeNCC(int ncc) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
