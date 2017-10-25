package ivote;
import java.util.*;
import java.rmi.*;

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
	
	public String registar(String tipo, int numeroCc, String dataCc, String nome, String password, int telefone, String morada, String no_faculd, String no_depart) throws RemoteException;
	public boolean removerUtilizador(int NCC) throws RemoteException;
	public ArrayList <String> ListDepartamentos(int id_faculd) throws RemoteException;//retorna a lista de departamentos de uma faculdade
	
	public boolean testeNCC(int ncc) throws RemoteException;
	
	
	//Gerir departamentos
	public boolean addDepartamento(String sigla, String nomeDepart, int id_dep, int id_fac)throws RemoteException;
	public boolean removeDepartamento(int id_dep, int id_fac)throws RemoteException;
	
	
	//Gerir faculdades
	public boolean addFaculdade(String sigla, String nomeFaculd, int id)throws RemoteException;
	public boolean removeFaculdade(int id)throws RemoteException;
	
	
	
	//Criar eleição; Tipo->núcleo de estudantes, conselho geral, direção de departamento, direção de faculdade
	public String criaEleicao(String tipo, String inicio, String fim, String titulo, String descricao, int id) throws RemoteException; // retorna o número da eleição
	public ArrayList <String> listEleicao(String inicio, String fim) throws RemoteException;//pesquisa num intervalo de tempo, para pesquisar todas deixar a null
	public ArrayList <String> listEleicao() throws RemoteException;
	

	//Gerir listas de candidatos a uma eleição
	public String addListaCandidatos(int id, int id_eleicao, String membro1, String membro2, String membro3, String membro4, String membro5) throws RemoteException;
	public boolean removeListaCandidatos(int id_LC) throws RemoteException;
	public ArrayList <String> listListasCandidatos(int id_elei)throws RemoteException;
	
	/*
	public boolean addCandidato(String Eleicao,String lista)throws RemoteException;
	public boolean removeCandidato(String Eleicao, String lista)throws RemoteException;
	public ArrayList <String> listCandidato(String Eleicao, String lista)throws RemoteException;
	*/
	
	//Gerir mesas de voto
	public String addMesaVoto(int id, int id_depart, int id_faculd, int id_elei, String user, String pass) throws RemoteException;
	public String addMesaVoto(int id, int id_faculd, int id_elei, String user, String pass) throws RemoteException;
	public boolean removeMesaVoto(int id_MV) throws RemoteException;
	public ArrayList <String> listMesaVoto(int id_elei) throws RemoteException;
	public ArrayList <String> listMesaVoto() throws RemoteException;
	
	
	//Alterar propriedades de uma eleição
	/*
	public String detalheEleicao(String eleicao) throws RemoteException;
	*/
	
	public boolean getTipo(int id_elei) throws RemoteException;
	public boolean setTipo(int id_elei, String n_tipo) throws RemoteException;
	
	public boolean getDataInicio(int id_elei) throws RemoteException;
	public boolean getDataFim(int id_elei) throws RemoteException;
	public boolean setDataInicio(String d_inicio, int id_elei) throws RemoteException;
	public boolean setDataFim(String d_fim, int id_elei) throws RemoteException;
	
	public boolean getTitulo(int id_elei) throws RemoteException;
	public boolean setTitulo(String n_titulo,int id_elei) throws RemoteException;
	
	public boolean getDescricao(int id_elei) throws RemoteException;
	public boolean setDescricao(String n_descricao, int id_elei) throws RemoteException;
	
	/*
	//Saber em que local votou cada eleitor
	//A pesquisa é feita apenas por um valor, o resto fica a null
	// na pequisa por nome pode surgir mais que um eleitor por isso retorna um array list
	public ArrayList <String> localVotoEleitor(String nome,int numCC,int telefone) throws RemoteException;
	
	
	//Mostrar o estado das mesas de voto
	public String estadoMesas(String mesaVoto) throws RemoteException;
	
	//Mostrar eleitores em tempo real
	//terá se ser implementado pela consola noutra interface, ou ainda temos de decidir
	public String eleitoresTReal() throws RemoteException;
	
	//Término da eleição na data, hora e minuto marcados, não precisa de ser implementado na interface, isto é automaticamente feito pelo
	//servidor de RMI
	//public String terminoEleicao() throws RemoteException;
	
	
	//Consultar resultados detalhados de eleições passadas
	public String consultaResulPass(String eleicao) throws RemoteException;
	
	
	//Alterar dados pessoais
	//todas as alterações são feitas atravez do nº Cartao de Cidadao
	//tipo, nome, password, faculdade, departamento, telefone, morada, numeroCc, dataCc
	 */
	
	public int getNCC (String nome_p,int telefone_p) throws RemoteException;// nº Cartao de Cidadao
	public boolean setNCC(int nCC, int novoNCC) throws RemoteException;
	
	public String getTipoP(int nCC)throws RemoteException;
	public boolean setTipoP(int nCC, String n_tipo) throws RemoteException;
	
	public String getNome(int nCC)throws RemoteException;
	public boolean setNome(int nCC, String n_nome) throws RemoteException;
	
	public String getPassword(int nCC)throws RemoteException;
	public boolean setPassword(int nCC, String n_password) throws RemoteException;
	
	public String getFacudade(int nCC)throws RemoteException;
	public boolean setFacudade(int nCC, String n_faculdade) throws RemoteException;
	
	public String getDepartamento(int nCC)throws RemoteException;
	public boolean setDepartamento(int nCC, String n_departamento) throws RemoteException;
	
	public int getTelefone(int nCC)throws RemoteException;
	public boolean setTelefone(int nCC, int n_telefone) throws RemoteException;
	
	public String getMorada(int nCC)throws RemoteException;
	public boolean setMorada(int nCC, String n_morada) throws RemoteException;
	
	public String getDataCC(int nCC)throws RemoteException;
	public boolean setDataCC(int nCC, String n_data) throws RemoteException;
	
	
	
	//Gerir membros de cada mesa de voto
	public String addMembroMesaVoto(String mesaVoto,int nCC1, int nCC2, int nCC3) throws RemoteException;
	public String removeMembroMesaVoto(int mesaVoto) throws RemoteException;
	/*
	public ArrayList <String> listaMembrosMesaVoto(String mesaVoto) throws RemoteException;

	//voto antecipado
	public boolean votaAntecipadamente(int nCC, String passwordUser) throws RemoteException;
	
	//Metodos para servidor TCP
	//todas as comunicações terao de ser autenticadas por questao de seguranca
	//-Ligar-se
	//-Votar
	//-Desbloquear Voto
	//-Votar antecipadamente
	
	public boolean ligarServidor(String nomeMesaVoto,String passwordMesaVoto) throws RemoteException;
	public boolean desligarServidor(String nomeMesaVoto,String passwordMesaVoto) throws RemoteException;
	
	public boolean desbloquearUser(String nomeMesaVoto,String passwordMesaVoto,int nCC) throws RemoteException;
	//verificar se o user existe nesta eleicao (exemplo na eleicao de detartamento se pertence ao departamento)
	//verificar se o user ja votou
	//e bloquear o user para que não vote em mais nenhuma mesa
	
	public boolean desbloquearVoto(String nomeMesaVoto,String passwordMesaVoto, int nCC, String passwordUser) throws RemoteException;
	public boolean bloquearVoto(String nomeMesaVoto,String passwordMesaVoto, int nCC, String passwordUser) throws RemoteException;

	//verificar se o user está bloqueado a esta mesa de voto
	//verificar se a password corresponde
	
	public boolean votar(String nomeMesaVoto,String passwordMesaVoto, int nCC, String passwordUser) throws RemoteException;
	//verificar se o user esta bloqueado a esta mesa de voto
	//verificar se a password corresponde
	//inserir o voto
	 */
}
