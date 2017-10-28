package ivote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminConsole extends UnicastRemoteObject{
	
	/**
	 * @author Júlio Girão
	 * @author Herberto Cardoso
	 * @author Luís Almeida
	 * @return
	 * @throws
	 */
	
	private static final long serialVersionUID = 1L;
	public RMI_1 comunicacao;
	public getScanner leTeclado;
	public getOptions parametros;
	
	public AdminConsole() throws RemoteException{
		/***
		 * construtor da classe AdminConsole
		 * @throws retorna uma exceção quando não há ligação com o servidor
		 */
		
		super();
		leTeclado = new getScanner();
		parametros = new getOptions();
	}
	
	public static void main(String[] args){
		/**
		 * inicializa ligação com o RMI server e executa o menu
		 */
		
		AdminConsole consola = null;
		try {
			consola = new AdminConsole();
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		boolean teste = true;
		while(teste) {
			try {
				
				
				consola.comunicacao = (RMI_1) Naming.lookup("rmi://"+consola.parametros.ipRmiServer+":"+consola.parametros.portRmiServer+"/rmi");
				consola.menuInicial();
				teste = false;
				
			} catch (RemoteException | MalformedURLException | NotBoundException  e) {
				System.out.println("Perda de ligação com o servidor...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		

	}
	
	public void menuInicial() throws RemoteException {
		/**
		 * menu inicial
		 * @return void saída do menu inicial
		 */
		while(true) {
			System.out.println();
			System.out.println("------------MAIN MENU-----------");
			System.out.println("1- Gerir utilizador");						//adicionar, remover, consultar
			System.out.println("2- Gerir faculdades");						//adicionar, remover, consultar
			System.out.println("3- Gerir departamentos");					//adicionar, remover, consultar
			System.out.println("4- Gerir eleições");						//criar eleição, adicionar listas, remover listas, consultar listas, consultar eleições, remover eleições
			System.out.println("5- Dados de eleições (Real Time)");			//escolher qual a eleição que está a correr e recebe eleições e começamos a receber as notificações
			System.out.println("6- Voto antecipado");						//autenticar a pessoa
			System.out.println("0- Sair");
			
			switch (leTeclado.pedeNumero("Opção: ", 0, 6)) {
				case 1:
					menuUtil();	
					break;
				case 2:
					menuFac();
					break;
				case 3:
					menuDep();
					break;
				case 4:
					menuEleicoes();
					break;
				case 5:
					 
					try {
						comunicacao.mensagemRealTime(selecionaEleicao(), new realTimeDados());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 6:
					break;
				case 0:
					return;
					
			}
			leTeclado.leLinha("Continuar...");
		}
	}
	
	public void menuUtil() throws RemoteException{
		while(true) {
			System.out.println();
			System.out.println("------------Sub Menu do Utilizador------------");
			System.out.println("1- Adicionar utilizador");
			System.out.println("2- Remover utilizador");
			System.out.println("3- Consultar utilizador");
			System.out.println("0- sair");
			
			switch(leTeclado.pedeNumero("Opção: ", 0, 3)) {
				case 1:
					if (criaUser()) {
						System.out.println("Utilzador adicionado!");
					}
					else {
						System.out.println("Erro na criação de utilizador!");
					}
					break;
				case 2:
					if(removeUser()) {
						System.out.println("Utilizador removido!");		
					}
					else
						System.out.println("Erro na remoção do utilizador");
					break;
				case 3:
					if(!consultaUser()) {
						System.out.println("Erro na consulta de utilizador!");			
					}
					break;
				case 0:
					return;
				
			}
			leTeclado.leLinha("Continuar...");
		}
		
	}
	
	
	public void menuFac()throws RemoteException{
		while(true) {
		 	System.out.println("------------Sub Menu das Faculdades------------");
			System.out.println("1- Adicionar faculdade");
			System.out.println("2- Remover faculdade");
			System.out.println("3- Consultar faculdade");
			System.out.println("0- Sair");
			switch(leTeclado.pedeNumero("Opção: ", 0, 3)) {
				
			case 1:
				if (criaFac()) {
					System.out.println("Faculdade adicionada!");
				}
				else {
					System.out.println("Erro na criação da faculdade!");
				}
				break;
			case 2:
				if(removeFac()) {
					System.out.println("Faculdade removida!");		
				}
				else
					System.out.println("Erro na remoção da faculdade");
				break;
			case 3:
				if(!consultaFac()) {
					System.out.println("Erro na consulta da faculdade!");			
				}
				break;
				
			case 0:
				return;
			
			}
			leTeclado.leLinha("Continuar...");
		}
	}
	
	
	private boolean consultaFac() throws RemoteException {
		

		for(String temp:comunicacao.ListFaculdades()) {
			System.out.println(temp);				
		}
		return true;

	}

	public void menuDep() throws RemoteException{
		while(true) {
			System.out.println("------------Sub Menu dos Departamentos------------");
			System.out.println("1- Adicionar departamento");
			System.out.println("2- Remover departamento");
			System.out.println("3- Consultar Departamento");
			System.out.println("0- Sair");
			
			switch(leTeclado.pedeNumero("Opção: ", 0, 3)) {
			
				case 1:
					if (criaDep()) {
						System.out.println("Departamento adicionado!");
					}
					else {
						System.out.println("Erro na criação do departamento!");
					}
					break;
					
				case 2:
					if(removeDep()) {
						System.out.println("Departamento removido!");		
					}
					else
						System.out.println("Erro na remoção de departamento");
					break;
					
				case 3:
					if(!consultaDep()) {
						System.out.println("Erro na consulta de departamento!");			
					}
					break;
				
				case 0:
					return;
				
					
			}
			leTeclado.leLinha("Continuar...");
		}
		
		
	}
	
	public void menuEleicoes() throws RemoteException {
		while(true) {
			System.out.println("------------Sub Menu das Eleições------------");
			
			System.out.println("1- Criar eleição");
			System.out.println("2- Remover eleições");
			System.out.println("3- Consultar eleições");
			System.out.println("4- Adicionar listas");
			System.out.println("5- Remover listas");
			System.out.println("6- Consultar listas");
			System.out.println("7- Adicionar mesa de voto");
			System.out.println("8- remover mesa de voto");
			System.out.println("9- Consultar mesas de voto");
			System.out.println("0- Sair");
			
			switch(leTeclado.pedeNumero("Opção: ", 0, 9)) {
				case 1:
					String tipoTemp = tipoEleicao();
					String horaInicioTemp = leTeclado.pedeDataHora("Data de Inicio: ");
					String horaFim = leTeclado.pedeDataHora("Data de Fim: ");
					String titulo = leTeclado.leLinha("Titulo: ");
					String descricao = leTeclado.leLinha("Descrição: ");
					int numero = leTeclado.pedeNumero("Id: ",0, 999);
					
					System.out.println("1- Confirmar");
					System.out.println("0- Cancelar");
					if(leTeclado.pedeNumero("Opcão: ", 0, 1)==1) {
							comunicacao.criaEleicao(tipoTemp, horaInicioTemp, horaFim, titulo, descricao,numero);
							comunicacao.addListaCandidatos((int)(Math.random()*1000-1), numero, "branco", "branco", "branco","branco", "branco");
							boolean teste = true;
							while (teste) {
								System.out.println("1- Adicionar Mesa de voto");
								System.out.println("2- Remover Mesa de voto");
								System.out.println("3- Consultar Mesa de voto");
								System.out.println("4- Adicionar Lista");
								System.out.println("5- Remover Lista");
								System.out.println("6- Consultar Lista");
								System.out.println("0- Sair");
								
								switch (leTeclado.pedeNumero("Opção: ", 0, 6)) {
									case 1:
										adicionarMesaVoto(numero);
										break;
									case 2:
										removeMesaVoto(numero);
										break;
									case 3:
										consultaMesaVoto(numero);
										break;
									case 4:
										adicionarLista(numero);
										break;
									case 5:
										removerLista(numero);
										break;
									case 6:
										consultarLista(numero);
										break;
									case 0:
										teste = false;
										break;
								}
							}
					}
					break;
				case 2:
						if(comunicacao.removeEleicao(selecionaEleicao())) {
							System.out.println("Eleição removida...");
							break;
						}
						System.out.println("Eleição não encontrada...");
					break;
				case 3:
						ArrayList<String> listaEleicoes = comunicacao.listEleicao();
						if(listaEleicoes.isEmpty()) {
							System.out.println("Sem eleições...");
							break;
						}
						for(String texto:listaEleicoes) {
							System.out.println(texto);
						}
					break;
				case 4:
					adicionarLista(selecionaEleicao());
					break;
				case 5:
					removerLista(selecionaEleicao());
					break;
				case 6:
					consultarLista(selecionaEleicao());
					break;
				case 7:
					adicionarMesaVoto(selecionaEleicao());
					break;
				case 8:
					removeMesaVoto(selecionaEleicao());
					break;
				case 9:
					consultaMesaVoto(selecionaEleicao());
					break;
				case 0:
					return;
			}
			leTeclado.leLinha("Continuar...");
		}
		
	}
	
	private int selecionaEleicao() throws RemoteException {
		System.out.println("Eleições: ");
		

			ArrayList<String> listaEleicoes = comunicacao.listEleicao();
			if(listaEleicoes.isEmpty()) {
				System.out.println("Sem eleições...");
				return 0;
			}
			int numTemp = 1;
			for(String texto:listaEleicoes) {
				System.out.println(numTemp+") "+texto);
				numTemp++;
			}
			System.out.println("0) cancelar");
			int numTemp1 = leTeclado.pedeNumero("Opção: ", 0, numTemp);
			if(numTemp1==0) {
				return 0;
			}
			
			return Integer.parseInt(listaEleicoes.get(numTemp1-1).split(" - ")[5]);
	}

	private void consultarLista(int numero) throws RemoteException {
		if(numero==0) {return;}
			System.out.println("Listas de candidatos:");
			ArrayList <String> listaCandidatos = comunicacao.listListasCandidatos(numero);
			if(listaCandidatos.isEmpty()) {
				System.out.println("Sem lista de candidatos...");
				return;
			}
			for(String texto:listaCandidatos) {
				System.out.println(texto);
				
			}
			leTeclado.leLinha("Continuar...");
	}

	private void removerLista(int numero) throws RemoteException {
		if(numero==0) {return;}
		
		System.out.println("Listas de candidatos:");
		ArrayList <String> listaCandidatos = comunicacao.listListasCandidatos(numero);
		if(listaCandidatos.isEmpty()) {
			System.out.println("Sem lista de candidatos...");
			return;
		}
		int numTemp = 1;
		for(String texto:listaCandidatos) {
			System.out.println(numTemp+"- "+texto);
			numTemp++;
		
		}
		System.out.println("0- cancelar");
		int numTemp1 = leTeclado.pedeNumero("Opção: ", 0, numTemp);
		if(numTemp1==0) {return;}
		if(comunicacao.removeListaCandidatos(Integer.parseInt(listaCandidatos.get(numTemp1-1).split(" - ")[0]))) {
			leTeclado.leLinha("Lista Removida...");
			return;
		}
		leTeclado.leLinha("Lista Não Removida...");
		
	}

	private void adicionarLista(int numero) throws RemoteException {
		if(numero==0) {return;}
		int numLista = leTeclado.pedeNumero("Numero da lista: ",0, 999);
		String m1 = leTeclado.leLinha("Membro1: ");
		String m2 = leTeclado.leLinha("Membro2: ");
		String m3 = leTeclado.leLinha("Membro3: ");
		String m4 = leTeclado.leLinha("Membro4: ");
		String m5 = leTeclado.leLinha("Membro5: ");
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		if(leTeclado.pedeNumero("Opção: ", 0, 1)==1) {
			System.out.println( comunicacao.addListaCandidatos(numLista, numero, m1, m2, m3, m4, m5));

		}
		leTeclado.leLinha("Continuar...");
	}

	private void consultaMesaVoto(int numero) throws RemoteException {
		if(numero==0) {return;}
		

		ArrayList <String> listaDeMesas = comunicacao.listMesaVoto(numero);
		for(String mesas:listaDeMesas) {
			System.out.println(mesas);
		}
		
		leTeclado.leLinha("Continuar...");
		
	}

	private void removeMesaVoto(int numero) throws RemoteException {
		// TODO Auto-generated method stub
		if(numero==0) {return;}
		
		ArrayList <String> listaDeMesas = comunicacao.listMesaVoto(numero);
		int numTemp = 1;
		for(String mesas:listaDeMesas) {
			System.out.println(numTemp+"- "+mesas);
			numTemp++;
		}
		System.out.println("0- Cancelar");
		int numTemp1 = leTeclado.pedeNumero("Opção: ", 0, numTemp);
		if(numTemp1==0) {return;}
		comunicacao.removeMesaVoto(Integer.parseInt(listaDeMesas.get(numTemp1-1).split(" - ")[0]));
		leTeclado.leLinha("Continuar...");
		
	}

	private void adicionarMesaVoto(int numero) throws RemoteException {
		// TODO Auto-generated method stub
		if(numero==0) {return;}
		
		int numMesaVoto = leTeclado.pedeNumero("Numero da mesa: ", 0, 999);
		int numFac = escolheFaculdade();
		int numDep = escolheDepartamento(numFac);
		String user = leTeclado.leLinha("Username: ");
		String pass = leTeclado.leLinha("Password: ");
		int user1;
		int user2;
		int user3;
		
		System.out.println("Adicionar Membro a mesa de voto: ");
		System.out.println("1- sim");
		System.out.println("2- não");
		if(leTeclado.pedeNumero("Opção: ", 0, 1)==1) {
			user1 = leTeclado.pedeNumero("Nº do Cartão de Cidadão: ", 9999999, 100000000);
			while(comunicacao.testeNCC(user1)==false) {
				System.out.println("Numero invalido...");
				user1 = leTeclado.pedeNumero("Nº do Cartão de Cidadão: ", 9999999, 100000000);
			}
			
			System.out.println("Adicionar Membro a mesa de voto: ");
			System.out.println("1- sim");
			System.out.println("2- não");
			if(leTeclado.pedeNumero("Opção: ", 0, 1)==1) {
				user2 = leTeclado.pedeNumero("Nº do Cartão de Cidadão: ", 9999999, 100000000);
				while(comunicacao.testeNCC(user2)==false) {
					System.out.println("Numero invalido...");
					user2 = leTeclado.pedeNumero("Nº do Cartão de Cidadão: ", 9999999, 100000000);
				}
				
				System.out.println("Adicionar Membro a mesa de voto: ");
				System.out.println("1- sim");
				System.out.println("2- não");
				if(leTeclado.pedeNumero("Opção: ", 0, 1)==1) {
					
					user3 = leTeclado.pedeNumero("Nº do Cartão de Cidadão: ", 9999999, 100000000);
					while(comunicacao.testeNCC(user3)==false) {
						System.out.println("Numero invalido...");
						user3 = leTeclado.pedeNumero("Nº do Cartão de Cidadão: ", 9999999, 100000000);
					}
					
				}
				else {
					
				}
			}
			else {
				
			}
		}
		
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		if(leTeclado.pedeNumero("Opção: ", 0, 1)==1) {
			leTeclado.leLinha(comunicacao.addMesaVoto(numMesaVoto, numDep, numFac, numero, user, pass));
		}
		
		
	}

	private int escolheDepartamento(int numFac) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList <String> dep = comunicacao.ListDepartamentos(numFac);
		int numTemp = 0;
		if (dep.isEmpty()) {return 0;}
		for(String tempTexto:dep) {
			System.out.println(numTemp+"- "+tempTexto);
			numTemp++;
		}
		return Integer.parseInt(dep.get(leTeclado.pedeNumero("Opção: ", 0, numTemp-1)).split(" - ")[2]);
	}

	private int escolheFaculdade() throws RemoteException {
		// TODO Auto-generated method stub
		
		ArrayList <String> fac = comunicacao.ListFaculdades();
		int numTemp = 0;
		if(fac.isEmpty()) {return 0;}
		for(String tempTexto:fac) {
			System.out.println(numTemp+"- "+tempTexto);
			numTemp++;
		}
		return Integer.parseInt(fac.get(leTeclado.pedeNumero("Opção: ", 0, numTemp-1)).split(" - ")[2]);
	}

	public boolean criaUser() throws RemoteException {
		int nccTemp = leTeclado.pedeNumero("Número de cartão de cidadão: ", 9999999, 100000000);
		if (comunicacao.testeNCC(nccTemp)) {
			
			int telefoneTemp = leTeclado.pedeNumero("Contactos: ", 99999999, 1000000000);		//telefone
			
			String nomeTemp = leTeclado.leLinha("Nome: ");										//nome
			
			String passTemp = leTeclado.leLinha("Password: ");									//password
			
			String moradaTemp = leTeclado.leLinha("Morada: ");									//morada
			
			String dataccTemp = leTeclado.pedeData("Data cartão cidadão: ");					//Data CC
			
			
			String listaTemp = "";
			ArrayList <String> listaFaculdades1 = comunicacao.ListFaculdades();
			for (String textoTemp:listaFaculdades1) {
				if(listaTemp=="") {listaTemp = textoTemp.split(" - ")[1];}
				else {listaTemp = listaTemp+";"+textoTemp.split(" - ")[1];}
			}
			
			
			String facTemp = leTeclado.mudaListaString("Faculdade: ", listaTemp);				//faculdade
			
			listaTemp = "";
			listaFaculdades1 = comunicacao.ListDepartamentos(facTemp);
			for (String textoTemp:listaFaculdades1) {
				if(listaTemp=="") {listaTemp = textoTemp.split(" - ")[1];}
				else {listaTemp = listaTemp+";"+textoTemp.split(" - ")[1];}
			}
			
			String depTemp = leTeclado.mudaListaString("Departamento: ", listaTemp);
			
			String cargoTemp = leTeclado.mudaListaString("Cargo: ","aluno;docente;funcionario");
			
			System.out.println(comunicacao.registar(cargoTemp, nccTemp, dataccTemp, nomeTemp, passTemp, telefoneTemp, moradaTemp, facTemp, depTemp));
			return true;
			}
		
		return false;
				
		
	}
	
	public boolean removeUser() throws RemoteException {
		
		int tempNumero = leTeclado.pedeNumero("Introduza o número de cartão de cidadão: ", 9999999, 100000000);

			if(comunicacao.testeNCC(tempNumero)) {
				String nome = comunicacao.getNome(tempNumero);
				System.out.println("Nome: "+nome);
				System.out.println("1- Confirmar");
				System.out.println("0- Cancelar");
				if(leTeclado.pedeNumero("Opção: ", 0, 1) == 1) {
					if(comunicacao.removerUtilizador(tempNumero)) {
						return true;
					}										
				}
			}

		
		return false;
				
		
	}
	
	public boolean consultaUser() throws RemoteException {
		
		int tempNumero = leTeclado.pedeNumero("Introduza o número de cartão de cidadão: ", 9999999, 100000000);

		if(comunicacao.testeNCC(tempNumero)) {
			String nome = comunicacao.getNome(tempNumero);
			System.out.println("Nome: "+nome);
			System.out.println("1- Alterar");
			System.out.println("0- voltar");
			if(leTeclado.pedeNumero("Opção: ", 0, 1)==1) {
				alteraUtilizador(tempNumero);
			}
			return true;
		}
		else {
			System.out.println("Utilizador não encontrado");
			return false;
		}

	}
	
	private void alteraUtilizador(int tempNumero) throws RemoteException {
		while(true) {
			System.out.println("1- Alterar Nome");
			System.out.println("2- Alterar Password");
			System.out.println("3- Alterar Cargo");
			System.out.println("4- Alterar Faculdade");
			System.out.println("5- Alterar Departamento");
			System.out.println("6- Altera Contacto");
			System.out.println("7- Altera Morada");
			System.out.println("8- Altera Data do Cartão de Cidadão");
			System.out.println("0- Sair");
			
			switch (leTeclado.pedeNumero("Opção: ", 0, 8)) {
			case 1:
					comunicacao.setNome(tempNumero, leTeclado.mudaString(comunicacao.getNome(tempNumero)));

				break;
			case 2:
					comunicacao.setPassword(tempNumero, leTeclado.mudaString(comunicacao.getPassword(tempNumero)));
				break;
			case 3:

					comunicacao.setTipoP(tempNumero, leTeclado.mudaListaString(comunicacao.getTipoP(tempNumero),"aluno;docente;funcionario"));
				break;
			case 4:

				 	String listaTemp = "";
					ArrayList <String> listaFaculdades1 = comunicacao.ListFaculdades();
					for (String textoTemp:listaFaculdades1) {
						if(listaTemp=="") {listaTemp = textoTemp;}
						else {listaTemp = listaTemp+";"+textoTemp;}
					}
					
					String faculdadeTemp = leTeclado.mudaListaString(comunicacao.getFacudade(tempNumero),listaTemp);
					comunicacao.setFacudade(tempNumero, faculdadeTemp);
					
					listaTemp = "";
					listaFaculdades1 = comunicacao.ListDepartamentos(faculdadeTemp);
					for (String textoTemp:listaFaculdades1) {
						if(listaTemp=="") {listaTemp = textoTemp;}
						else {listaTemp = listaTemp+";"+textoTemp;}
					}
					
					comunicacao.setDepartamento(tempNumero, leTeclado.mudaListaString(comunicacao.getDepartamento(tempNumero), listaTemp));

				break;
			case 5:

					String listaTemp1 = "";
					ArrayList <String> listaFaculdades = comunicacao.ListDepartamentos(comunicacao.getFacudade(tempNumero));
					for (String textoTemp:listaFaculdades) {
						if(listaTemp1=="") {listaTemp = textoTemp;}
						else {listaTemp = listaTemp1+";"+textoTemp;}
					}
					comunicacao.setDepartamento(tempNumero, leTeclado.mudaListaString(comunicacao.getDepartamento(tempNumero), listaTemp1));

				break;
			case 6:

					comunicacao.setTelefone(tempNumero, leTeclado.mudaInt(comunicacao.getTelefone(tempNumero),99999999,1000000000));

				break;
			case 7:

					comunicacao.setMorada(tempNumero, leTeclado.mudaString(comunicacao.getMorada(tempNumero)));
				break;
			case 8:

					comunicacao.setDataCC(tempNumero, leTeclado.pedeData(comunicacao.getDataCC(tempNumero)));

				break;
			case 0:
				return;
			}
		}
		
	}

	public String tipoEleicao() throws RemoteException{
		System.out.println("Escolha o tipo de eleição:");
		System.out.println("1- Núcleo de Estudantes");
		System.out.println("2- Conselho Geral");
		System.out.println("3- Direção de Departamento");
		System.out.println("4- Direção de Faculdade");
		switch(leTeclado.pedeNumero("Opção: ", 1, 4)) {
			case 1:
				return "NEstudante"; 
			case 2:
				return "CGeral";
			case 3:
				return "DDepartamento";
			case 4:
				return "DFaculdade";
		}
		return "";
	}
	
	public boolean criaFac() throws RemoteException {
		
		String nomeFaculd = leTeclado.leLinha("Nome da faculadade: ");
		String sigla = leTeclado.leLinha("Sigla da faculdade: ");
		int id = leTeclado.pedeNumero("Id da faculdade: ", 0, 999);
		System.out.println(id+ " "+sigla+" "+nomeFaculd);
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		
		if(leTeclado.pedeNumero("Opção: ", 0, 1)== 1) {
				if(comunicacao.addFaculdade(sigla, nomeFaculd, id)) {
					return true;
				}	
		}
		return false;
	}

	public boolean removeFac() throws RemoteException {
		
		int idFac=0;

			for(String temp:comunicacao.ListFaculdades()) {
				System.out.println(temp);				
			}
			do {
				idFac = leTeclado.pedeNumero("Introduza o id da faculdade (0- Cancelar): ", 0, 999);
				if(idFac==0) {return false;}
			}
			while (!comunicacao.removeFaculdade(idFac));
	
		return true;
	}
	
	
	public boolean consultaDep() throws RemoteException {

			for(String dep:comunicacao.ListDepartamentos()) {
				System.out.println(dep);				
			}
			return true;


	}
	
	public boolean removeDep() throws RemoteException {
		
		int idDep_2=0;

			for(String temp_2:comunicacao.ListDepartamentos()) {
				System.out.println(temp_2);				
			}
			do {
				idDep_2 = leTeclado.pedeNumero("Introduza o id do departamento (0- Cancelar): ", 0, 999);
				if(idDep_2==0) {return false;}
			}
			while (!comunicacao.removeDepartamento(idDep_2));
			

		return true;
			
	}
	
	public boolean criaDep() throws RemoteException {
		
		String sigla = leTeclado.leLinha("Sigla do departamento: ");
		String nomeDepart = leTeclado.leLinha("Nome do departamento: ");
		int id = leTeclado.pedeNumero("Id do departamento: ", 0, 999);
		
		String listaTemp = "";
		ArrayList<String> listaFaculdades1 = null;
		

			listaFaculdades1 = comunicacao.ListFaculdades();

		
		for (String textoTemp:listaFaculdades1) {
			if(listaTemp=="") {listaTemp = textoTemp;}
			else {listaTemp = listaTemp+";"+textoTemp;}
		}
		String facTemp = leTeclado.mudaListaString("Faculdade: ", listaTemp);
		
		System.out.println(id+ " "+sigla+" "+nomeDepart+" "+Integer.parseInt(facTemp.split(" - ")[2]));
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		
		if(leTeclado.pedeNumero("Opção: ", 0, 1)== 1) {

				if(comunicacao.addDepartamento(sigla, nomeDepart, id, Integer.parseInt(facTemp.split(" - ")[2]) )) {
					return true;
				}
		
		}
		return false;
		
		
	}

}