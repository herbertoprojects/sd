package ivote;

import java.util.*;
import java.rmi.*;

public interface RMIRemoteInterface extends Remote{
	
	/*1 - Registar pessoas. Deve poder-se registar estudantes, docentes e funcionários. 
	Deverá guardar toda a informação pessoal que considere necessária, bem como uma password 
	(código de acesso) e o departamento/faculdade ao qual a pessoa pertence. A informação pessoal 
	deverá incluir também dados de contacto telefónico, morada, número e validade do cartão de cidadão. */
	
	// return int - retorna um numero sendo 1 - registado e 0 - não registado, podemos depois acrecentar outras opções
	// String tipo - estudante/docente/funcionario
	// String nome - nome da pessoa semarado por espaço ex."XXXXXXXX XXXXXXXXXXXX XXXXXXX XXXXXXX"
	// String password - apenas numeros para evitar uso de simbolos que entrem em conflito com a comunicação tcp
	// String faculdade - nome da faculdade
	// String departamento - nome do departamento
	// int telefone - numero de telefone
	
	public int registar(String tipo, String nome, String password, String faculdade, String departamento, int telefone, String morada, int numeroCc, Date dataCc) throws RemoteException;
	
	public ArrayList <String> ListFaculdades() throws RemoteException;//retorna a lista de faculdade
	public ArrayList <String> ListaDepartamentos(String nomeFaculdade) throws RemoteException;//retorna a lista de departamentos de uma faculdade
	
	//Gerir departamentos
	public boolean addDepartamento(String nome,String nomeFaculdade)throws RemoteException;
	public boolean removeDepartamento(String nomeDep)throws RemoteException;
	
	
	//Gerir faculdades
	public boolean addFaculdade(String nome)throws RemoteException;
	public boolean removeFaculdade(String nomeFaculdade)throws RemoteException;
	
	
	//Criar eleição; Tipo->núcleo de estudantes, conselho geral, direção de departamento, direção de faculdade
	public int criaEleicao(String tipo, Date inicio, Date fim, String titulo, String descricao) throws RemoteException; // retorna o numero da eleiçao
	public ArrayList <String> listEleicao(Date inicio, Date fim) throws RemoteException;//pesquisa num intervalo de tempo, para pesquisar todas deixar a null
	
	//Gerir listas de candidatos a uma eleição
	public boolean addListaCandidatos(String Eleicao,String nomeLista,ArrayList <int[]> listaCandidatos)throws RemoteException;
	public boolean removeListaCandidatos(String Eleicao, String nomeLista)throws RemoteException;
	public ArrayList <String> listListasCandidatos(String Eleicao)throws RemoteException;
	
	public boolean addCandidato(String Eleicao,String lista)throws RemoteException;
	public boolean removeCandidato(String Eleicao, String lista)throws RemoteException;
	public ArrayList <String> listCandidato(String Eleicao, String lista)throws RemoteException;
	
	
	//Gerir mesas de voto
	public boolean addMesaVoto(String nome, String password) throws RemoteException;
	public boolean removeMesaVoto(String nome) throws RemoteException;
	public ArrayList <String> listMesaVoto()throws RemoteException;
	
	
	//Alterar propriedades de uma eleição	
	public String detalheEleicao(String eleicao) throws RemoteException;
	
	public String getTipo(String eleicao) throws RemoteException;
	public boolean setTipo(String tipo,String eleicao) throws RemoteException;
	
	public Date getDataInicio(String eleicao) throws RemoteException;
	public boolean setDataInicio(Date data,String eleicao) throws RemoteException;
	
	public Date getDataFim(String eleicao) throws RemoteException;
	public boolean setDataFim(Date data,String eleicao) throws RemoteException;
	
	public String getTitulo(String eleicao) throws RemoteException;
	public boolean setTitulo(String titulo,String eleicao) throws RemoteException;
	
	public String getDescricao(String eleicao) throws RemoteException;
	public boolean setDescricao(String descricao,String eleicao) throws RemoteException;
	
	
	//Saber em que local votou cada eleitor
	//A pesesquisa é feita apenas por um valor, o resto fica a null
	// na pequisa por nome pode surgir mais que um eleitor por isso retorna um array list
	public ArrayList <String> localVotoEleitor(String nome,int numCC,int telefone) throws RemoteException;
	
	
	//Mostrar o estado das mesas de voto
	public String estadoMesas(String mesaVoto) throws RemoteException;
	
	//Mostrar eleitores em tempo real
	//tera se ser implementado pela comsola noutra interface, ou ainda temos de decidir
	public String eleitoresTReal() throws RemoteException;
	
	//Término da eleição na data, hora e minuto marcados, não precisa de ser implementado na interface, isto é automaticamente feito pelo
	//servidor de RMI
	//public String terminoEleicao() throws RemoteException;
	
	
	//Consultar resultados detalhados de eleições passadas
	public String consultaResulPass(String eleicao) throws RemoteException;
	
	
	//Alterar dados pessoais
	//todas as alterações são feitas atravez do n Cartao de Cidadao que pode ser pesquisado por nome ou telefone
	//tipo, nome, password, faculdade, departamento, telefone, morada, numeroCc, dataCc
	public ArrayList<String> getNCC (String nome,int telefone) throws RemoteException;// nº Cartao de Cidadao
	
	public boolean setNCC(int nCC, int novoNCC) throws RemoteException;
	
	public String getTipo(int nCC)throws RemoteException;
	public boolean setTipo(int nCC, String tipo) throws RemoteException;
	
	public String getNome(int nCC)throws RemoteException;
	public boolean setNome(int nCC, String nome) throws RemoteException;
	
	public String getPassword(int nCC)throws RemoteException;
	public boolean setPassword(int nCC, String password) throws RemoteException;
	
	public String getFacudade(int nCC)throws RemoteException;
	public boolean setFacudade(int nCC, String faculdade) throws RemoteException;
	
	public String getDepartamento(int nCC)throws RemoteException;
	public boolean setDepartamento(int nCC, String departamento) throws RemoteException;
	
	public int getTelefone(int nCC)throws RemoteException;
	public boolean setTelefone(int nCC, int telefone) throws RemoteException;
	
	public String getMorada(int nCC)throws RemoteException;
	public boolean setMorada(int nCC, String morada) throws RemoteException;
	
	public Date getDataCC(int nCC)throws RemoteException;
	public boolean setDataCC(int nCC, Date data ) throws RemoteException;
	
	
	
	//Gerir membros de cada mesa de voto
	public boolean addMembroMesaVoto(String mesaVoto,int nCC) throws RemoteException;
	public boolean removeMembroMesaVoto(String mesaVoto,int nCC) throws RemoteException;
	public ArrayList <String> listaMembrosMesaVoto(String mesaVoto) throws RemoteException;

	
	//voto antecipado
	public boolean votaAntecipadamente(int nCC, String passwordUser) throws RemoteException;
	
	
	//Metodos para servidor TCP
	//todas as comunicaçoes terao de ser autenticadas por questao de seguranca
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
	//verificar se o user esta bloqueado a esta mesa de voto
	//verificar se a password corresponde
	
	public boolean votar(String nomeMesaVoto,String passwordMesaVoto, int nCC, String passwordUser,int voto) throws RemoteException;
	//verificar se o user esta bloqueado a esta mesa de voto
	//verificar se a password corresponde
	//inserir o voto
}
