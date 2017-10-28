package ivote;
import java.util.*;
import java.rmi.*;
import java.sql.*;

/**
 * @author Utilizador
 *
 */
public interface RMI_1 extends Remote{
	
	/*1 - Registar pessoas. Deve poder-se registar estudantes, docentes e funcionários. 
	Deverá guardar toda a informação pessoal que considere necessária, bem como uma password 
	(código de acesso) e o departamento/faculdade ao qual a pessoa pertence. A informação pessoal 
	deverá incluir também dados de contacto telefónico, morada, número e validade do cartão de cidadão. */
	
	// return int - retorna um numero sendo 1 - registado e 0 - não registado, podemos depois acrecentar outras opções
	// String tipo - estudante/docente/funcionario
	// String nome - nome da pessoa separado por espaço ex."XXXXXXXX XXXXXXXXXXXX XXXXXXX XXXXXXX"
	// String password - apenas números para evitar uso de símbolos que entrem em conflito com a comunicação tcp
	// String faculdade - nome da faculdade
	// String departamento - nome do departamento
	// int telefone - numero de telefone
	
	/**
	 * @param tipo
	 * @param numeroCc
	 * @param dataCc
	 * @param nome
	 * @param password
	 * @param telefone
	 * @param morada
	 * @param no_faculd
	 * @param no_depart
	 * @return
	 * @throws RemoteException
	 */
	public String registar(String tipo, int numeroCc, String dataCc, String nome, String password, int telefone, String morada, String no_faculd, String no_depart) throws RemoteException;
	/**
	 * @param NCC
	 * @return
	 * @throws RemoteException
	 */
	public boolean removerUtilizador(int NCC) throws RemoteException;
	/**
	 * @param id_faculd
	 * @return
	 * @throws RemoteException
	 */
	/**
	 * @param id_faculd
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> ListDepartamentos(int id_faculd) throws RemoteException;//retorna a lista de departamentos de uma faculdade
	/**
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> ListDepartamentos() throws RemoteException;
	/**
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> ListFaculdades() throws RemoteException;
	/**
	 * @param ncc
	 * @return
	 * @throws RemoteException
	 */
	public boolean testeNCC(int ncc) throws RemoteException;
	
	//Gerir departamentos
	/**
	 * @param sigla
	 * @param nomeDepart
	 * @param id_dep
	 * @param id_fac
	 * @return
	 * @throws RemoteException
	 */
	public boolean addDepartamento(String sigla, String nomeDepart, int id_dep, int id_fac)throws RemoteException;
	/**
	 * @param id_dep
	 * @return
	 * @throws RemoteException
	 */
	public boolean removeDepartamento(int id_dep)throws RemoteException;
	
	
	//Gerir faculdades
	/**
	 * @param sigla
	 * @param nomeFaculd
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean addFaculdade(String sigla, String nomeFaculd, int id)throws RemoteException;
	/**
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean removeFaculdade(int id)throws RemoteException;
	
	
	
	//Criar eleição; Tipo->núcleo de estudantes, conselho geral, direção de departamento, direção de faculdade
	//tipo de eleição -> NEstudante CGeral DDepartamento DFaculdade
	/**
	 * @param tipo
	 * @param inicio
	 * @param fim
	 * @param titulo
	 * @param descricao
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public String criaEleicao(String tipo, String inicio, String fim, String titulo, String descricao, int id) throws RemoteException; // retorna o número da eleição
	/**
	 * @param id_eleicao
	 * @return
	 * @throws RemoteException
	 */
	public boolean removeEleicao(int id_eleicao) throws RemoteException;
	/**
	 * @param inicio
	 * @param fim
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> listEleicao(String inicio, String fim) throws RemoteException;//pesquisa num intervalo de tempo, para pesquisar todas deixar a null
	/**
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> listEleicao() throws RemoteException;
	

	//Gerir listas de candidatos a uma eleição
	/**
	 * @param id
	 * @param id_eleicao
	 * @param membro1
	 * @param membro2
	 * @param membro3
	 * @param membro4
	 * @param membro5
	 * @return
	 * @throws RemoteException
	 */
	public String addListaCandidatos(int id, int id_eleicao, String membro1, String membro2, String membro3, String membro4, String membro5) throws RemoteException;
	/**
	 * @param id_LC
	 * @return
	 * @throws RemoteException
	 */
	public boolean removeListaCandidatos(int id_LC) throws RemoteException;
	/**
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> listListasCandidatos(int id_elei)throws RemoteException;
	
	
	//Gerir mesas de voto
	/**
	 * @param id
	 * @param id_depart
	 * @param id_faculd
	 * @param id_elei
	 * @param user
	 * @param pass
	 * @return
	 * @throws RemoteException
	 */
	public String addMesaVoto(int id, int id_depart, int id_faculd, int id_elei, String user, String pass) throws RemoteException;
	/**
	 * @param id
	 * @param id_faculd
	 * @param id_elei
	 * @param user
	 * @param pass
	 * @return
	 * @throws RemoteException
	 */
	public String addMesaVoto(int id, int id_faculd, int id_elei, String user, String pass) throws RemoteException;
	/**
	 * @param id_MV
	 * @return
	 * @throws RemoteException
	 */
	public boolean removeMesaVoto(int id_MV) throws RemoteException;
	/**
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> listMesaVoto(int id_elei) throws RemoteException;
	/**
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> listMesaVoto() throws RemoteException;
	
	
	//Alterar propriedades de uma eleição

	/**
	 * @param eleicao
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> detalheEleicao(int eleicao) throws RemoteException;
	
	/**
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public String getTipo(int id_elei) throws RemoteException;
	/**
	 * @param id_elei
	 * @param n_tipo
	 * @return
	 * @throws RemoteException
	 */
	public boolean setTipo(int id_elei, String n_tipo) throws RemoteException;
	
	/**
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public String getDataInicio(int id_elei) throws RemoteException;
	/**
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public String getDataFim(int id_elei) throws RemoteException;
	/**
	 * @param d_inicio
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public boolean setDataInicio(String d_inicio, int id_elei) throws RemoteException;
	/**
	 * @param d_fim
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public boolean setDataFim(String d_fim, int id_elei) throws RemoteException;
	
	/**
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public String getTitulo(int id_elei) throws RemoteException;
	/**
	 * @param n_titulo
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public boolean setTitulo(String n_titulo,int id_elei) throws RemoteException;
	
	/**
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public String getDescricao(int id_elei) throws RemoteException;
	/**
	 * @param n_descricao
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public boolean setDescricao(String n_descricao, int id_elei) throws RemoteException;
	
	/*
	//Saber em que local votou cada eleitor
	//A pesquisa é feita apenas por um valor, o resto fica a null
	// na pequisa por nome pode surgir mais que um eleitor por isso retorna um array list
	public ArrayList <String> localVotoEleitor(String nome,int numCC,int telefone) throws RemoteException;
	
	//Mostrar o estado das mesas de voto
	public String estadoMesas(String mesaVoto) throws RemoteException;*/
	
	/**
	 * @param eleicao
	 * @param imprimir
	 * @throws RemoteException
	 */
	public void mensagemRealTime(int eleicao,dadosEleicoes imprimir) throws RemoteException;
	
	/*//Mostrar eleitores em tempo real
	//terá se ser implementado pela consola noutra interface, ou ainda temos de decidir
	public String eleitoresTReal() throws RemoteException;
	
	//Término da eleição na data, hora e minuto marcados, não precisa de ser implementado na interface, isto é automaticamente feito pelo
	//servidor de RMI
	//s
	
	//Consultar resultados detalhados de eleições passadas
	public String consultaResulPass(String eleicao) throws RemoteException;
	
	
	//Alterar dados pessoais
	//todas as alterações são feitas atravez do nº Cartao de Cidadao
	//tipo, nome, password, faculdade, departamento, telefone, morada, numeroCc, dataCc
	 */
	
	/**
	 * @param nome_p
	 * @param telefone_p
	 * @return
	 * @throws RemoteException
	 */
	public int getNCC (String nome_p,int telefone_p) throws RemoteException;// nº Cartao de Cidadao
	/**
	 * @param nCC
	 * @param novoNCC
	 * @return
	 * @throws RemoteException
	 */
	public boolean setNCC(int nCC, int novoNCC) throws RemoteException;
	
	/**
	 * @param nCC
	 * @return
	 * @throws RemoteException
	 */
	public String getTipoP(int nCC)throws RemoteException;
	/**
	 * @param nCC
	 * @param n_tipo
	 * @return
	 * @throws RemoteException
	 */
	public boolean setTipoP(int nCC, String n_tipo) throws RemoteException;
	
	/**
	 * @param nCC
	 * @return
	 * @throws RemoteException
	 */
	public String getNome(int nCC)throws RemoteException;
	/**
	 * @param nCC
	 * @param n_nome
	 * @return
	 * @throws RemoteException
	 */
	public boolean setNome(int nCC, String n_nome) throws RemoteException;
	
	/**
	 * @param nCC
	 * @return
	 * @throws RemoteException
	 */
	public String getPassword(int nCC)throws RemoteException;
	/**
	 * @param nCC
	 * @param n_password
	 * @return
	 * @throws RemoteException
	 */
	public boolean setPassword(int nCC, String n_password) throws RemoteException;
	
	/**
	 * @param nCC
	 * @return
	 * @throws RemoteException
	 */
	public String getFacudade(int nCC)throws RemoteException;
	/**
	 * @param nCC
	 * @param n_faculdade
	 * @return
	 * @throws RemoteException
	 */
	public boolean setFacudade(int nCC, String n_faculdade) throws RemoteException;
	
	/**
	 * @param nCC
	 * @return
	 * @throws RemoteException
	 */
	public String getDepartamento(int nCC)throws RemoteException;
	/**
	 * @param nCC
	 * @param n_departamento
	 * @return
	 * @throws RemoteException
	 */
	public boolean setDepartamento(int nCC, String n_departamento) throws RemoteException;
	
	/**
	 * @param nCC
	 * @return
	 * @throws RemoteException
	 */
	public int getTelefone(int nCC)throws RemoteException;
	/**
	 * @param nCC
	 * @param n_telefone
	 * @return
	 * @throws RemoteException
	 */
	public boolean setTelefone(int nCC, int n_telefone) throws RemoteException;
	
	/**
	 * @param nCC
	 * @return
	 * @throws RemoteException
	 */
	public String getMorada(int nCC)throws RemoteException;
	/**
	 * @param nCC
	 * @param n_morada
	 * @return
	 * @throws RemoteException
	 */
	public boolean setMorada(int nCC, String n_morada) throws RemoteException;
	
	/**
	 * @param nCC
	 * @return
	 * @throws RemoteException
	 */
	public String getDataCC(int nCC)throws RemoteException;
	/**
	 * @param nCC
	 * @param n_data
	 * @return
	 * @throws RemoteException
	 */
	public boolean setDataCC(int nCC, String n_data) throws RemoteException;
	
	
	
	//Gerir membros de cada mesa de voto
	/**
	 * @param mesaVoto
	 * @param nCC1
	 * @param nCC2
	 * @param nCC3
	 * @return
	 * @throws RemoteException
	 */
	public String addMembrosMesaVoto(int mesaVoto,int nCC1, int nCC2, int nCC3) throws RemoteException;
	/**
	 * @param mesaVoto
	 * @return
	 * @throws RemoteException
	 */
	public String removeMembroMesaVoto(int mesaVoto) throws RemoteException;
	/**
	 * @param id_mesaVoto
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList <String> listaMembrosMesaVoto(int id_mesaVoto) throws RemoteException;
	

	//voto antecipado
	/**
	 * @param nCC
	 * @param id_elei
	 * @return
	 * @throws RemoteException
	 */
	public boolean votaAntecipadamente(int nCC, int id_elei) throws RemoteException;
	
	/*
	//Metodos para servidor TCP
	//todas as comunicações terao de ser autenticadas por questao de seguranca
	//-Ligar-se
	//-Votar
	//-Desbloquear Voto
	//-Votar antecipadamente
	*/
	/**
	 * @param nomeMesaVoto
	 * @param passwordMesaVoto
	 * @return
	 * @throws RemoteException
	 */
	public String ligarServidor(String nomeMesaVoto,String passwordMesaVoto) throws RemoteException;
	
	/**
	 * @param nomeMesaVoto
	 * @param passwordMesaVoto
	 * @param nCC
	 * @param elect
	 * @return
	 * @throws RemoteException
	 */
	public boolean desbloquearUser(String nomeMesaVoto, String passwordMesaVoto, int nCC, int elect) throws RemoteException;
	//verificar se o user existe nesta eleicao (exemplo na eleicao de detartamento se pertence ao departamento)
	//verificar se o user ja votou
	//e bloquear o user para que não vote em mais nenhuma mesa
	
	/**
	 * @param nomeMesaVoto
	 * @param passwordMesaVoto
	 * @param nCC
	 * @param id_elect
	 * @return
	 * @throws RemoteException
	 */
	public boolean desbloquearVoto(String nomeMesaVoto, String passwordMesaVoto, int nCC, int id_elect) throws RemoteException;
	/**
	 * @param nomeMesaVoto
	 * @param passwordMesaVoto
	 * @param nCC
	 * @param id_elect
	 * @return
	 * @throws RemoteException
	 */
	public boolean bloquearVoto(String nomeMesaVoto,String passwordMesaVoto, int nCC, int id_elect) throws RemoteException;

	//verificar se o user está bloqueado a esta mesa de voto
	//verificar se a password corresponde
	
	/**
	 * @param nomeMesaVoto
	 * @param passwordMesaVoto
	 * @param nCC
	 * @param passwordUser
	 * @param id_elei
	 * @param id_lista_voto
	 * @return
	 * @throws RemoteException
	 */
	public boolean votar(String nomeMesaVoto, String passwordMesaVoto, int nCC, String passwordUser, int id_elei, int id_lista_voto) throws RemoteException;
	//verificar se o user esta bloqueado a esta mesa de voto
	//verificar se a password corresponde
	//inserir o voto
	
	//lista de departamento dando a faculdade como string 
	//fazer
	/**
	 * @param faculdadeTemp
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> ListDepartamentos(String faculdadeTemp) throws RemoteException;	 
}