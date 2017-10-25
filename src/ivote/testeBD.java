package ivote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.*;

public class testeBD {
	
	public static Connection conn = null;
	
	public static void main(String[] args) {
		
		try {
			String tipo, nome, password, morada, dataCc, siglaF, siglaD, nomeFac, nomeDep, inicio, fim, titulo, descricao, inicioE, fimE, membro1, membro2, membro3, membro4, membro5 = "";
			int telefone, numeroCc, id_faculd, id_depart, id_elei, id_LC, id_MV = 0;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "proj", "proj");
			System.out.println("Conectado há base de dados "+conn);
			
			tipo = "docente";
			nome = "Júlio";
			password = "pasw";
			id_faculd = 1;
			id_depart = 4;
			morada = "mora";
			telefone = 98764;
			numeroCc = 432;
			dataCc = "1995/12/12";
			siglaF = "FMUC";
			siglaD = "DEEM";
			nomeDep = "engenharia mec";
			nomeFac = "medicina";
			
			inicio = "1995/12/12 12:12";
			fim = "1995/12/14 12:12";
			titulo = "eleicao1";
			inicioE = "1995/12/10";
			fimE = "1995/12/13";
			descricao = "teste";
			id_elei = 1;
			
			id_LC = 2;
			membro1 = "Presidente2";
			membro2 = "Vice-presidente2";
			membro3 = "Vareador12";
			membro4 = "Vareador22";
			membro5 = "Vareador32";
			
			id_MV = 8;

			//System.out.println(registar(tipo, numeroCc, dataCc, nome, password, telefone, morada, id_faculd, id_depart));
			//System.out.println(addFaculdade(siglaF, nomeFac, id_faculd));
			//System.out.println(removeFaculdade(id_faculd));
			//System.out.println(addDepartamento(siglaD, nomeDep, id_depart, id_faculd));
			//System.out.println(removeDepartamento(id_depart, id_faculd));
			//System.out.println(ListFaculdades());
			//System.out.println(ListDepartamentos(id_faculd));
			//System.out.println(criaEleicao(tipo, inicio, fim, titulo, descricao, id_elei));
			//System.out.println(listEleicao(inicioE, fimE));
			//System.out.println(listEleicao());
			//System.out.println(addListaCandidatos(id_LC, id_elei, membro1, membro2, membro3, membro4, membro5));
			//System.out.println(removeListaCandidatos(id_LC));
			//System.out.println(listListasCandidatos(id_elei));
			//System.out.println(addMesaVoto(id_MV, id_depart, id_faculd, id_elei));
			//System.out.println(addMesaVoto(id_MV, id_faculd, id_elei));
			//System.out.println(removeMesaVoto(id_MV));
			//System.out.println(listMesaVoto(id_elei));
			//getTipo(id_elei);
			//System.out.println(setTipo(id_elei, "docente"));
			//getTipo(id_elei);
			//getDataInicio(id_elei);
			//System.out.println(setDataInicio("1996/12/12", id_elei));
			//getDataFim(id_elei);
			//System.out.println(setDataFim("1996/12/14", id_elei));
			//getTitulo(id_elei);
			//System.out.println(setTitulo("n_titulo",id_elei));
			getDescricao(id_elei);
			//System.out.println(setDescricao("teste11",id_elei));
		} catch(Exception e) {
			e.printStackTrace();
		}	 
	}
	
	public static String registar(String tipo, int numeroCc, String dataCc, String nome, String password, int telefone, String morada, int id_faculd, int id_depart){
		try {
			Statement st = conn.createStatement();
			//System.out.println("'"+tipo+"', '"+numeroCc+"', to_date('"+dataCc+"','yyyy/mm/dd')), '"+nome+"', '"+password+"', '"+telefone+"', '"+morada+"', '"+id_faculd+"', '"+id_depart+"')");
			st.executeUpdate("Insert into pessoa values ('"+tipo+"', '"+numeroCc+"', to_date('"+dataCc+"','yyyy/mm/dd'), '"+nome+"', '"+password+"', '"+telefone+"', '"+morada+"', '"+id_faculd+"', '"+id_depart+"')");
			conn.commit();
			return "type : register , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			return "type : register , ok : false";
		}
	}
	
	public static boolean addFaculdade(String sigla, String nomeFaculd, int id)throws RemoteException {
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
	
	public static boolean removeFaculdade(int id_faculd)throws RemoteException {
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
	
	public static boolean addDepartamento(String sigla,String nomeDepart, int id, int id_faculd)throws RemoteException {
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
	
	public static boolean removeDepartamento(int id_depart, int id_faculd)throws RemoteException {
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
	
	public static ArrayList <String> ListFaculdades() throws RemoteException {
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
	
	
	public static ArrayList <String> ListDepartamentos(int id_faculd) throws RemoteException {
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
	
	public static String criaEleicao(String tipo, String inicio, String fim, String titulo, String descricao, int id) throws RemoteException {
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
	
	public static ArrayList <String> listEleicao(String inicioE, String fimE) throws RemoteException {
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
	
	public static ArrayList <String> listEleicao() throws RemoteException {
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
	
	public static String addListaCandidatos(int id, int id_eleicao, String membro1, String membro2, String membro3, String membro4, String membro5) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into pessoaslista values ('"+id+"', '"+id_eleicao+"', '"+membro1+"', '"+membro2+"', '"+membro3+"', '"+membro4+"', '"+membro5+"')");
			conn.commit();
			return "type : add_peopleList , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			return "type : add_peopleList , ok : false";
		}
	}
	
	public static boolean removeListaCandidatos(int id_LC) throws RemoteException {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from pessoaslista where id = ('"+id_LC+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList <String> listListasCandidatos(int id_elei)throws RemoteException {
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
	
	public static String addMesaVoto(int id, int id_depart, int id_faculd, int id_elei) throws RemoteException {
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
	
	public static String addMesaVoto(int id, int id_faculd, int id_elei) throws RemoteException {
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
	
	public static boolean removeMesaVoto(int id_MV) throws RemoteException {
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
	
	public static ArrayList <String> listMesaVoto(int id_elei) throws RemoteException {
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
	
	public static ArrayList <String> listMesaVoto() throws RemoteException {
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
	
	public static boolean getTipo(int id_elei) throws RemoteException {
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
	
	public static boolean setTipo(int id_elei, String n_tipo) throws RemoteException {
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
	
	public static boolean getDataInicio(int id_elei) throws RemoteException {
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
	
	public static boolean getDataFim(int id_elei) throws RemoteException {
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
	
	public static boolean setDataInicio(String d_inicio, int id_elei) throws RemoteException {
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
	
	public static boolean setDataFim(String d_fim, int id_elei) throws RemoteException {
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
	
	public static boolean getTitulo(int id_elei) throws RemoteException {
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
	
	public static boolean setTitulo(String n_titulo,int id_elei) throws RemoteException {
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
	
	public static boolean getDescricao(int id_elei) throws RemoteException {
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
	
	public static boolean setDescricao(String n_descricao, int id_elei) throws RemoteException {
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
	
	public static boolean getNCC (String nome_p,int telefone_p) throws RemoteException {
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
	
	public static boolean setNCC(int nCC, int novoNCC) throws RemoteException {
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
	
	public static boolean getTipoP(int nCC)throws RemoteException {
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
	
	public static boolean setTipoP(int nCC, String n_tipo) throws RemoteException {
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
	
	public static boolean getNome(int nCC)throws RemoteException {
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
	
	public static boolean setNome(int nCC, String n_nome) throws RemoteException {
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
	
	public static boolean getPassword(int nCC)throws RemoteException {
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
	
	public static boolean setPassword(int nCC, String n_password) throws RemoteException {
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
	
	public static boolean getFacudade(int nCC)throws RemoteException {
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
	
	public static boolean setFacudade(int nCC, String n_faculdade) throws RemoteException {
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
	
	public static boolean getDepartamento(int nCC)throws RemoteException {
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
	
	public static boolean setDepartamento(int nCC, String n_departamento) throws RemoteException {
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
	
	public static boolean getTelefone(int nCC)throws RemoteException {
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
	
	public static boolean setTelefone(int nCC, int n_telefone) throws RemoteException {
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
	
	public static boolean getMorada(int nCC)throws RemoteException {
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
	
	public static boolean setMorada(int nCC, String n_morada) throws RemoteException {
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
	
	public static boolean getDataCC(int nCC)throws RemoteException {
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
	
	public static boolean setDataCC(int nCC, String n_data) throws RemoteException {
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
}
