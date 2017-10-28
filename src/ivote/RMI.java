package ivote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.rmi.Naming;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;

public class RMI extends UnicastRemoteObject implements RMI_1 {
	
	getOptions parametrosEntrada;
	boolean debug_teste = true;
	
	protected RMI() throws RemoteException {
		super();
		parametrosEntrada = new getOptions();
	}
	
	private static final long serialVersionUID = 1L;
	
	public static Connection conn = null;
	
	public void debug(String texto) {
		if(debug_teste) {System.out.println(texto);}
	}
	
	public static void main(String[] args) {
		RMI server = null;
		
		try {
			server = new RMI();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int falhas = 0;
		System.out.println("Servidor secundario...");
		while(falhas < 10) {
			falhas = server.ServidorSecundario(falhas);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		server.primario(server.parametrosEntrada.portRmiServer);
		
	}
	
	private void primario(int portaRMI) {
		System.out.println();
		System.out.println("RMI primário");
		try {
			Thread hearthBitSend = (new Thread() {public void run() {enviaHearthBit();}});
			hearthBitSend.start();
			
			LocateRegistry.createRegistry(portaRMI);
			Naming.rebind("rmi://localhost:"+portaRMI+"/rmi", this);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "proj", "proj");
			System.out.println("Conectado há base de dados"+conn);
		} catch(RemoteException | MalformedURLException | SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	public int ServidorSecundario(int numTemp) {
		DatagramSocket socketCliente = null;
		
		try {
			socketCliente = new DatagramSocket(parametrosEntrada.hearthBitPort);
			socketCliente.setSoTimeout(250);
			byte [] reciveData = new byte[1024];
			DatagramPacket recivePacket = new DatagramPacket(reciveData, reciveData.length);
			socketCliente.receive(recivePacket);
			String mensagem = new String(recivePacket.getData());
			socketCliente.close();
			return 0;
			
		} catch (SocketException e1) {
			return numTemp;
		}catch (SocketTimeoutException e) {
			socketCliente.close();
			System.out.print(numTemp+"♥ ");
			return numTemp+1;
		} catch (IOException e) {
			socketCliente.close();
			return numTemp;
		}
	}
	
	public void enviaHearthBit() {
		byte[] sendData = new byte[1024];
		sendData = "Vivo".getBytes();
		DatagramSocket socketEnvia;
		InetAddress IPAddress;
		DatagramPacket packet;
		
		while(true) {
			try {
				IPAddress = InetAddress.getByName(parametrosEntrada.ipRmiServer_2);
				socketEnvia = new DatagramSocket();
				packet = new DatagramPacket(sendData, sendData.length, IPAddress,parametrosEntrada.hearthBitPort);
				socketEnvia.send(packet);
				Thread.sleep(250);
				socketEnvia.close();
				
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}

	
	public String registar(String tipo, int numeroCc, String dataCc, String nome, String password, int telefone, String morada, String no_faculd, String no_depart) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			int id_faculd;
			int id_depart; 
			Statement st = conn.createStatement();
			if(no_faculd==null) {
				if(no_depart == null) {
					st.executeUpdate("Insert into pessoa values ('"+tipo+"', '"+numeroCc+"', to_date('"+dataCc+"','yyyy/mm/dd'), '"+nome+"', '"+password+"', '"+telefone+"', '"+morada+"', null , null)");
				}else {
					id_depart= nmtoidDepart(no_depart);
					st.executeUpdate("Insert into pessoa values ('"+tipo+"', '"+numeroCc+"', to_date('"+dataCc+"','yyyy/mm/dd'), '"+nome+"', '"+password+"', '"+telefone+"', '"+morada+"', null, '"+id_depart+"')");
				}
				
			}else {
				id_faculd=nmtoidFaculd(no_faculd);
				if(no_depart == null) {
					st.executeUpdate("Insert into pessoa values ('"+tipo+"', '"+numeroCc+"', to_date('"+dataCc+"','yyyy/mm/dd'), '"+nome+"', '"+password+"', '"+telefone+"', '"+morada+"', '"+id_faculd+"', null)");
				}
				else {
					id_depart= nmtoidDepart(no_depart);
					st.executeUpdate("Insert into pessoa values ('"+tipo+"', '"+numeroCc+"', to_date('"+dataCc+"','yyyy/mm/dd'), '"+nome+"', '"+password+"', '"+telefone+"', '"+morada+"', '"+id_faculd+"', '"+id_depart+"')");
				}
			}
			
			
			conn.commit();
			return "type : register , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "type : register , ok : false";
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean removerUtilizador(int NCC) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from pessoa where numeroCc = ('"+NCC+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList <String> ListFaculdades() throws RemoteException {
		
		ArrayList<String> faculdades = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select * from faculdade";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    faculdades.add(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return faculdades;
	}
	
	public ArrayList <String> ListDepartamentos(int id_faculd) throws RemoteException {
		ArrayList<String> departamentos = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select * from departamento where id_faculd = ('"+id_faculd+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    departamentos.add(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return departamentos;
	}
	
	public ArrayList <String> ListDepartamentos() throws RemoteException {
		ArrayList<String> departamentos = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select * from departamento";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    departamentos.add(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return departamentos;
	}
	
	public boolean addDepartamento(String sigla,String nomeDepart, int id, int id_faculd)throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into departamento values ('"+sigla+"', '"+nomeDepart+"', '"+id+"', '"+id_faculd+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean removeDepartamento(int id_depart)throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from departamento where id = ('"+id_depart+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean addFaculdade(String sigla, String nomeFaculd, int id)throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into faculdade values ('"+sigla+"', '"+nomeFaculd+"', '"+id+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean removeFaculdade(int id_faculd)throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from faculdade where id = ('"+id_faculd+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String criaEleicao(String tipo, String inicio, String fim, String titulo, String descricao, int id) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into eleicao values ('"+tipo+"', to_date('"+inicio+"','yyyy/mm/dd hh:mi'), to_date('"+fim+"','yyyy/mm/dd hh:mi'), '"+titulo+"', '"+descricao+"', '"+id+"')");
			conn.commit();
			return "type : create_election , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "type : create_election , ok : false";
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean removeEleicao(int id_eleicao) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from eleicao where id = ("+id_eleicao+")");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList <String> listEleicao(String inicioE, String fimE) throws RemoteException {
		ArrayList<String> eleicoes = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select * from eleicao where dataInicio >= ('"+inicioE+"') and dataFim <= ('"+fimE+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    eleicoes.add(rs.getString(1)+" - "+rs.getDate(2)+" - "+rs.getDate(3)+" - "+rs.getString(4)+" - "+rs.getString(5)+" - "+rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return eleicoes;
	}
	
	public ArrayList <String> listEleicao() throws RemoteException {
		ArrayList<String> eleicoes = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select * from eleicao";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    eleicoes.add(rs.getString(1)+" - "+rs.getDate(2)+" - "+rs.getDate(3)+" - "+rs.getString(4)+" - "+rs.getString(5)+" - "+rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return eleicoes;
	}
	
	public String addListaCandidatos(int id, int id_eleicao, String membro1, String membro2, String membro3, String membro4, String membro5) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into pessoasLista values ('"+id+"', '"+id_eleicao+"', '"+membro1+"', '"+membro2+"', '"+membro3+"', '"+membro4+"', '"+membro5+"')");
			conn.commit();
			return "type : add_peopleList , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "type : add_peopleList , ok : false";
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean removeListaCandidatos(int id_LC) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from pessoasLista where id = ('"+id_LC+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//depois ter em atenção o tipo de eleição!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public ArrayList <String> listListasCandidatos(int id_elei) throws RemoteException {
		ArrayList<String> candidatos = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select * from pessoaslista where id_eleicao = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    candidatos.add(rs.getInt(1)+" - "+rs.getInt(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - "+rs.getString(5)+" - "+rs.getString(6)+" - "+rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return candidatos;
	}
	
	public String addMesaVoto(int id, int id_depart, int id_faculd, int id_elei, String user, String pass) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into mesavoto(id, id_depart, id_faculd, id_eleicao, usern, pass) values ('"+id+"', '"+id_depart+"', '"+id_faculd+"', '"+id_elei+"', '"+user+"', '"+pass+"')");
			conn.commit();
			return "type : add_voteTable , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "type : add_voteTable , ok : false";
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String addMesaVoto(int id, int id_faculd, int id_elei, String user, String pass) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Insert into mesavoto() values ('"+id+"', null, '"+id_faculd+"', '"+id_elei+"', '"+user+"', '"+pass+"')");
			conn.commit();
			return "type : add_voteTable , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "type : add_voteTable , ok : false";
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean removeMesaVoto(int id_MV) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.executeUpdate("Delete from mesavoto where id = ('"+id_MV+"')");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList <String> listMesaVoto(int id_elei) throws RemoteException {
		ArrayList<String> mesas = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select * from mesavoto where id_eleicao = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    mesas.add(rs.getInt(1)+" - "+rs.getInt(2)+" - "+rs.getInt(3)+" - "+rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mesas;
	}
	
	public ArrayList <String> listMesaVoto() throws RemoteException {
		ArrayList<String> mesas = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select * from mesavoto";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    mesas.add(rs.getInt(1)+" - "+rs.getInt(2)+" - "+rs.getInt(3)+" - "+rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mesas;
	}
	
	/*
	public String detalheEleicao(String eleicao) throws RemoteException {
		
	}
	
	*/
	
	public String getTipo(int id_elei) throws RemoteException {
		String tipo = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select tipo from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			tipo = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tipo;
	}
	
	public boolean setTipo(int id_elei, String n_tipo) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update eleicao set tipo = ('"+n_tipo+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getDataInicio(int id_elei) throws RemoteException {
		String dataI = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select datainicio from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			dataI = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataI;
	}
	
	public String getDataFim(int id_elei) throws RemoteException {
		String dataF = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select datafim from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			dataF = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataF;
	}
	
	public boolean setDataInicio(String d_inicio, int id_elei) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update eleicao set datainicio = ('"+d_inicio+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean setDataFim(String d_fim, int id_elei) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update eleicao set datafim = ('"+d_fim+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getTitulo(int id_elei) throws RemoteException {
		String title = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select titulo from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			title = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return title;
	}
	
	public boolean setTitulo(String n_titulo,int id_elei) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update eleicao set titulo = ('"+n_titulo+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getDescricao(int id_elei) throws RemoteException {
		String mora = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select descricao from eleicao where id = ('"+id_elei+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			mora = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mora;
	}
	
	public boolean setDescricao(String n_descricao, int id_elei) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update eleicao set descricao = ('"+n_descricao+"') where id = ('"+id_elei+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	public ArrayList <String> localVotoEleitor(String nome, int numCC, int telefone) throws RemoteException {
		
	}
	
	public String estadoMesas(String mesaVoto) throws RemoteException {
		
	}
	
	public String eleitoresTReal() throws RemoteException {
		
	}

	public String terminoEleicao() throws RemoteException {
	
	}

	public String consultaResulPass(String eleicao) throws RemoteException {
		
	}
	*/
	
	public int getNCC (String nome_p,int telefone_p) throws RemoteException {
		int numCC = 0;
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select numeroCc from pessoa where nome = ('"+nome_p+"') and telefone = ('"+telefone_p+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			numCC = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return numCC;
	}
	
	public boolean setNCC(int nCC, int novoNCC) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set numeroCc = ('"+novoNCC+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getTipoP(int nCC)throws RemoteException {
		String tipo = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select tipo from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			tipo = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tipo;
	}
	
	public boolean setTipoP(int nCC, String n_tipo) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set tipo = ('"+n_tipo+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getNome(int nCC)throws RemoteException {
		String n_nome = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select nome from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			n_nome = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n_nome;
	}
	
	public boolean setNome(int nCC, String n_nome) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set nome = ('"+n_nome+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getPassword(int nCC)throws RemoteException {
		String n_pass = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select password from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			n_pass = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n_pass;
	}
	
	public boolean setPassword(int nCC, String n_password) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set password = ('"+n_password+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getFacudade(int nCC)throws RemoteException {
		String n_facul = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select faculdade from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			n_facul = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n_facul;
	}
	
	public boolean setFacudade(int nCC, String n_faculdade) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set faculdade = ('"+n_faculdade+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getDepartamento(int nCC)throws RemoteException {
		String n_depar = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select departamento from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			n_depar = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n_depar;
	}
	
	public boolean setDepartamento(int nCC, String n_departamento) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set departamento = ('"+n_departamento+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int getTelefone(int nCC)throws RemoteException {
		int n_tel = 0;
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select telefone from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			n_tel = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n_tel;
	}
	
	public boolean setTelefone(int nCC, int n_telefone) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set telefone = ('"+n_telefone+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getMorada(int nCC)throws RemoteException {
		String n_mora = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select morada from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			n_mora = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n_mora;
	}
	
	public boolean setMorada(int nCC, String n_morada) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set morada = ('"+n_morada+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getDataCC(int nCC)throws RemoteException {
		String n_dat = "";
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select dataCc from pessoa where numeroCc = ('"+nCC+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			n_dat = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n_dat;
	}
	
	public boolean setDataCC(int nCC, String n_data) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("Update pessoa set dataCc = to_date('"+n_data+"') where numeroCc = ('"+nCC+"')");
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String addMembrosMesaVoto(int mesaVoto, int nCC1, int nCC2, int nCC3) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("UPDATE mesavoto SET id_pessoa1 = '"+nCC1+"', id_pessoa2 = '"+nCC2+"', id_pessoa3 = '"+nCC3+"' WHERE id = '"+mesaVoto+"'");
			st.executeUpdate();
			conn.commit();
			return "type : add_MembersVoteTable , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "type : add_MembersVoteTable , ok : false";
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String removeMembroMesaVoto(int mesaVoto) throws RemoteException {
		try {
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement("UPDATE mesavoto SET id_pessoa1 = null, id_pessoa2 = null, id_pessoa3 = null WHERE id = "+mesaVoto+"");
			st.executeUpdate();
			conn.commit();
			return "type : remove_MembersVoteTable , ok : true";
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "type : remove_MembersVoteTable , ok : false";
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public ArrayList <String> listaMembrosMesaVoto(int id_mesaVoto) throws RemoteException {
		ArrayList<String> listaMesas = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select id_pessoa1, id_pessoa2, id_pessoa3 from mesavoto where id = '"+id_mesaVoto+"'";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    listaMesas.add(rs.getInt(1)+" - "+rs.getInt(2)+" - "+rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listaMesas;
	}
	

	/*
	public boolean votaAntecipadamente(int nCC, String passwordUser) throws RemoteException {
		
	}
	*/
	
	private int nmtoidDepart(String nome_b)throws RemoteException {
		int tempNum = 0;
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select id from departamento where nomedep = ('"+nome_b+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			tempNum = rs.getInt(1);;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tempNum;
	}
	
	private int nmtoidFaculd(String nome_b)throws RemoteException {
		int tempNum = 0;
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select id from faculdade where nomefac = ('"+nome_b+"')";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			tempNum = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tempNum;
	}

	public boolean testeNCC(int ncc) throws RemoteException {
		int contador = 0;
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select count(*) from pessoa WHERE  numeroCc = '"+ncc+"'";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			contador = rs.getInt(1);
			if(contador!=0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String ligarServidor(String nomeMesaVoto, String passwordMesaVoto) throws RemoteException {
		int contador = 0;
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select count(*) from mesavoto where userN = '"+nomeMesaVoto+"' and pass = '"+passwordMesaVoto+"';";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			contador = rs.getInt(1);
			if(contador==1) {
				conn.setAutoCommit(false);
				st = conn.createStatement();
				sql = "select id from mesavoto where userN = '"+nomeMesaVoto+"' and pass = '"+passwordMesaVoto+"';";
				rs = st.executeQuery(sql);
				rs.next();
				return rs.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean desbloquearUser(String nomeMesaVoto, String passwordMesaVoto, int nCC, int elect) throws RemoteException {
		if (ligarServidor(nomeMesaVoto, passwordMesaVoto) != null) {
			int contador = 0;
			try {
				conn.setAutoCommit(false);
				Statement st = conn.createStatement();
				String sql = "select count(*) from voto WHERE id_pessoa = '"+nCC+"' and id_eleicao = '"+elect+"' and blocked = 0";
				ResultSet rs = st.executeQuery(sql);
				rs.next();
				contador = rs.getInt(1);
				if(contador==0) {
					comandoSql("insert into voto values (numLista.nextVal, "+elect+", "+nCC+", null, null, 1)");
					return false;
				} else {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return false;
			} finally {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	public boolean desbloquearVoto(String nomeMesaVoto, String passwordMesaVoto, int nCC, int id_elect)
			throws RemoteException {
		if (ligarServidor(nomeMesaVoto, passwordMesaVoto) != null) {
			comandoSql("update voto set blocked = 0 where id_pessoa = "+nCC+" and id_eleicao = "+id_elect+"");
			return true;
		} else {
			return false;
		}
		// TODO Auto-generated method stub
	}

	public boolean bloquearVoto(String nomeMesaVoto, String passwordMesaVoto, int nCC, int id_elect)
			throws RemoteException {
		if (ligarServidor(nomeMesaVoto, passwordMesaVoto) != null) {
			comandoSql("update voto set blocked = 1 where id_pessoa = "+nCC+" and id_eleicao = "+id_elect+"");
			return true;
		}else {
			return false;
		}
		// TODO Auto-generated method stub
	}

	public boolean votar(String nomeMesaVoto, String passwordMesaVoto, int nCC, String passwordUser, int voto)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<String> ListDepartamentos(String faculdadeTemp) throws RemoteException {
		int id_fac = nmtoidFaculd(faculdadeTemp);
		ArrayList<String> listDepart = new ArrayList<String>();
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			String sql = "select nome from departamento where id_faculd = '"+id_fac+"'";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
			    listDepart.add(rs.getInt(3)+" - "+rs.getInt(2)+" - "+rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listDepart;
	}
	
	public ResultSet comandoSql(String comand) {
		Statement st;
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			String sql = comand;
			ResultSet rs = st.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
