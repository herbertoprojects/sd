package ivote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AdminConsole extends UnicastRemoteObject{
	
	/**
	 * @author J�lio Gir�o
	 * @author Herberto Cardoso
	 * @author Lu�s Almeida
	 * @return
	 * @throws
	 */
	
	private static final long serialVersionUID = 1L;
	public static RMI_1 comunicacao;
	public getScanner leTeclado;
	
	public AdminConsole() throws RemoteException{
		/***
		 * construtor da classe AdminConsole
		 * @throws retorna uma exce��o quando n�o h� liga��o com o servidor
		 */
		
		super();
		leTeclado = new getScanner();
	}
	
	public static void main(String[] args){
		/**
		 * inicializa liga��o com o RMI server e executa o menu
		 */
		AdminConsole consola = null;
		try {
			consola = new AdminConsole();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		try {
			comunicacao = (RMI_1) Naming.lookup("rmi://127.0.0.1:7000/rmi");
		} catch (RemoteException | MalformedURLException | NotBoundException  e) {
			e.printStackTrace();
		}
		
		consola.menuInicial();

	}
	
	public void menuInicial() {
		/**
		 * menu inicial
		 * @return void sa�da do menu inicial
		 */
		while(true) {
			System.out.println();
			System.out.println("------------MAIN MENU-----------");
			System.out.println("1- Gerir utilizador");						//adicionar, remover, consultar
			System.out.println("2- Gerir faculdades");						//adicionar, remover, consultar
			System.out.println("3- Gerir departamentos");					//adicionar, remover, consultar
			System.out.println("4- Gerir elei��es");						//criar elei��o, adicionar listas, remover listas, consultar listas, consultar elei��es, remover elei��es
			System.out.println("5- Dados de elei��es (Real Time)");			//escolher qual a elei��o que est� a correr e recebe elei��es e come�amos a receber as notifica��es
			System.out.println("6- Voto antecipado");						//autenticar a pessoa
			System.out.println("0- Sair");
			
			switch (leTeclado.pedeNumero("Op��o: ", 0, 6)) {
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
					break;
				case 6:
					break;
				case 0:
					return;
					
			}
			leTeclado.leLinha("Continuar...");
		}
	}
	
	public void menuUtil() {
		while(true) {
			System.out.println();
			System.out.println("------------Sub Menu do Utilizador------------");
			System.out.println("1- Adicionar utilizador");
			System.out.println("2- Remover utilizador");
			System.out.println("3- Consultar utilizador");
			System.out.println("0- sair");
			
			switch(leTeclado.pedeNumero("Op��o: ", 0, 3)) {
				case 1:
					if (criaUser()) {
						System.out.println("Utilzador adicionado!");
					}
					else {
						System.out.println("Erro na cria��o de utilizador!");
					}
					break;
				case 2:
					if(removeUser()) {
						System.out.println("Utilizador removido!");		
					}
					else
						System.out.println("Erro na remo��o do utilizador");
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
	
	
	public void menuFac() {
		while(true) {
		 	System.out.println("------------Sub Menu das Faculdades------------");
			System.out.println("1- Adicionar faculdade");
			System.out.println("2- Remover faculdade");
			System.out.println("3- Consultar faculdade");
			System.out.println("0- Sair");
			switch(leTeclado.pedeNumero("Op��o: ", 0, 3)) {
				
			case 1:
				if (criaFac()) {
					System.out.println("Faculdade adicionada!");
				}
				else {
					System.out.println("Erro na cria��o da faculdade!");
				}
				break;
			case 2:
				if(removeFac()) {
					System.out.println("Faculdade removida!");		
				}
				else
					System.out.println("Erro na remo��o da faculdade");
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
	
	
	private boolean consultaFac() {
		
		try {
			for(String temp:comunicacao.ListFaculdades()) {
				System.out.println(temp);				
			}
			return true;
			
		} catch (RemoteException e) {
			e.printStackTrace();	
		}	
		return false;
	}

	public void menuDep() {
		while(true) {
			System.out.println("------------Sub Menu dos Departamentos------------");
			System.out.println("1- Adicionar departamento");
			System.out.println("2- Remover departamento");
			System.out.println("3- Consultar Departamento");
			System.out.println("0- Sair");
			
			switch(leTeclado.pedeNumero("Op��o: ", 0, 3)) {
			
				case 1:
					if (criaDep()) {
						System.out.println("Departamento adicionado!");
					}
					else {
						System.out.println("Erro na cria��o do departamento!");
					}
					break;
					
				case 2:
					if(removeDep()) {
						System.out.println("Departamento removido!");		
					}
					else
						System.out.println("Erro na remo��o de departamento");
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
	
	public void menuEleicoes() {
		while(true) {
			System.out.println("------------Sub Menu das Elei��es------------");
			
			System.out.println("1- Criar elei��o");
			System.out.println("2- Remover elei��es");
			System.out.println("3- Consultar elei��es");
			System.out.println("4- Adicionar listas");
			System.out.println("5- Remover listas");
			System.out.println("6- Consultar listas");
			System.out.println("7- Adicionar mesa de voto");
			System.out.println("8- remover mesa de voto");
			System.out.println("9- Consultar mesas de voto");
			System.out.println("0- Sair");
			
			switch(leTeclado.pedeNumero("Op��o: ", 0, 9)) {
				case 1:
					String tipoTemp = tipoEleicao();
					String horaInicioTemp = leTeclado.pedeDataHora("Data de Inicio: ");
					String horaFim = leTeclado.pedeDataHora("Data de Fim: ");
					String titulo = leTeclado.leLinha("Titulo: ");
					String descricao = leTeclado.leLinha("Descri��o: ");
					int numero = leTeclado.pedeNumero("Id: ",0, 999);
					
					System.out.println("1- Confirmar");
					System.out.println("0- Cancelar");
					if(leTeclado.pedeNumero("Opc�o: ", 0, 1)==1) {
						try {
							comunicacao.criaEleicao(tipoTemp, horaInicioTemp, horaFim, titulo, descricao,numero);
							while (true) {
								System.out.println("1- Adicionar Mesa de voto");
								System.out.println("2- Remover Mesa de voto");
								System.out.println("3- Consultar Mesa de voto");
								System.out.println("4- Adicionar Mesa de voto");
								System.out.println("5- Adicionar Mesa de voto");
								System.out.println("6- Adicionar Mesa de voto");
								System.out.println("0- Sair");
								
								switch (leTeclado.pedeNumero(, minNum, maxNum)) {
								case value:
									
									break;

								default:
									break;
								}
							}
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					break;
				case 0:
					return;
			}
			leTeclado.leLinha("Continuar...");
		}
		
	}
	
	public boolean criaUser() {
		int nccTemp = leTeclado.pedeNumero("N�mero de cart�o de cidad�o: ", 9999999, 100000000);
		try {
			if (comunicacao.testeNCC(nccTemp)) {
				
				int telefoneTemp = leTeclado.pedeNumero("Contactos: ", 99999999, 1000000000);		//telefone
				
				String nomeTemp = leTeclado.leLinha("Nome: ");										//nome
				
				String passTemp = leTeclado.leLinha("Password: ");									//password
				
				String moradaTemp = leTeclado.leLinha("Morada: ");									//morada
				
				String dataccTemp = leTeclado.pedeData("Data cart�o cidad�o: ");					//Data CC
				
				
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
		}catch(RemoteException e) {
			return false;
		}
		
		return false;
				
		
	}
	
	public boolean removeUser() {
		
		int tempNumero = leTeclado.pedeNumero("Introduza o n�mero de cart�o de cidad�o: ", 9999999, 100000000);
		try {
			if(comunicacao.testeNCC(tempNumero)) {
				String nome = comunicacao.getNome(tempNumero);
				System.out.println("Nome: "+nome);
				System.out.println("1- Confirmar");
				System.out.println("0- Cancelar");
				if(leTeclado.pedeNumero("Op��o: ", 0, 1) == 1) {
					if(comunicacao.removerUtilizador(tempNumero)) {
						return true;
					}										
				}
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();	
		}
		
		return false;
				
		
	}
	
	public String pedeFac() {
		
		
		
		return "";
		
	}
	
	public String pedeDep(String faculdade) {
		return "";
		
	}
	
	public boolean consultaUser() {
		
		int tempNumero = leTeclado.pedeNumero("Introduza o n�mero de cart�o de cidad�o: ", 9999999, 100000000);
		try {
			if(comunicacao.testeNCC(tempNumero)) {
				String nome = comunicacao.getNome(tempNumero);
				System.out.println("Nome: "+nome);
				System.out.println("1- Alterar");
				System.out.println("0- voltar");
				if(leTeclado.pedeNumero("Op��o: ", 0, 1)==1) {
					alteraUtilizador(tempNumero);
				}
				return true;
			}
			else {
				System.out.println("Utilizador n�o encontrado");
				return false;
			}
				
		} catch (RemoteException e) {
			e.printStackTrace();
			
		}
		return false;

	}
	
	private void alteraUtilizador(int tempNumero) {
		while(true) {
			System.out.println("1- Alterar Nome");
			System.out.println("2- Alterar Password");
			System.out.println("3- Alterar Cargo");
			System.out.println("4- Alterar Faculdade");
			System.out.println("5- Alterar Departamento");
			System.out.println("6- Altera Contacto");
			System.out.println("7- Altera Morada");
			System.out.println("8- Altera Data do Cart�o de Cidad�o");
			System.out.println("0- Sair");
			
			switch (leTeclado.pedeNumero("Op��o: ", 0, 8)) {
			case 1:
				try {
					comunicacao.setNome(tempNumero, leTeclado.mudaString(comunicacao.getNome(tempNumero)));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					comunicacao.setPassword(tempNumero, leTeclado.mudaString(comunicacao.getPassword(tempNumero)));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					comunicacao.setTipoP(tempNumero, leTeclado.mudaListaString(comunicacao.getTipoP(tempNumero),"aluno;docente;funcionario"));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			case 4:
				try {
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
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					String listaTemp = "";
					ArrayList <String> listaFaculdades = comunicacao.ListDepartamentos(comunicacao.getFacudade(tempNumero));
					for (String textoTemp:listaFaculdades) {
						if(listaTemp=="") {listaTemp = textoTemp;}
						else {listaTemp = listaTemp+";"+textoTemp;}
					}
					comunicacao.setDepartamento(tempNumero, leTeclado.mudaListaString(comunicacao.getDepartamento(tempNumero), listaTemp));
					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					comunicacao.setTelefone(tempNumero, leTeclado.mudaInt(comunicacao.getTelefone(tempNumero),99999999,1000000000));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case 7:
				try {
					comunicacao.setMorada(tempNumero, leTeclado.mudaString(comunicacao.getMorada(tempNumero)));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case 8:
				try {
					comunicacao.setDataCC(tempNumero, leTeclado.pedeData(comunicacao.getDataCC(tempNumero)));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case 0:
				return;
			}
		}
		
	}

	public String tipoEleicao() {
		System.out.println("Escolha o tipo de elei��o:");
		System.out.println("1- N�cleo de Estudantes");
		System.out.println("2- Conselho Geral");
		System.out.println("3- Dire��o de Departamento");
		System.out.println("4- Dire��o de Faculdade");
		switch(leTeclado.pedeNumero("Op��o: ", 1, 4)) {
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
	
	public boolean criaFac() {
		
		String nomeFaculd = leTeclado.leLinha("Nome da faculadade: ");
		String sigla = leTeclado.leLinha("Sigla da faculdade: ");
		int id = leTeclado.pedeNumero("Id da faculdade: ", 0, 999);
		System.out.println(id+ " "+sigla+" "+nomeFaculd);
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		
		if(leTeclado.pedeNumero("Op��o: ", 0, 1)== 1) {
			try {
				if(comunicacao.addFaculdade(sigla, nomeFaculd, id)) {
					return true;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}		
		}
		return false;
	}

	public boolean removeFac() {
		
		int idFac=0;
		try {
			for(String temp:comunicacao.ListFaculdades()) {
				System.out.println(temp);				
			}
			do {
				idFac = leTeclado.pedeNumero("Introduza o id da faculdade (0- Cancelar): ", 0, 999);
				if(idFac==0) {return false;}
			}
			while (!comunicacao.removeFaculdade(idFac));
			
		} catch (RemoteException e) {
			e.printStackTrace();	
		}	
		return true;
	}
	
	
	public boolean consultaDep() {
		try {
			for(String dep:comunicacao.ListDepartamentos()) {
				System.out.println(dep);				
			}
			return true;
			
		} catch (RemoteException e) {
			e.printStackTrace();	
		}	
		return false;		
	}
	
	public boolean removeDep() {
		
		int idDep_2=0;
		try {
			for(String temp_2:comunicacao.ListDepartamentos()) {
				System.out.println(temp_2);				
			}
			do {
				idDep_2 = leTeclado.pedeNumero("Introduza o id do departamento (0- Cancelar): ", 0, 999);
				if(idDep_2==0) {return false;}
			}
			while (!comunicacao.removeDepartamento(idDep_2));
			
		} catch (RemoteException e) {
			e.printStackTrace();	
		}	
		return true;
			
	}
	
	public boolean criaDep() {
		
		String sigla = leTeclado.leLinha("Sigla do departamento: ");
		String nomeDepart = leTeclado.leLinha("Nome do departamento: ");
		int id = leTeclado.pedeNumero("Id do departamento: ", 0, 999);
		
		String listaTemp = "";
		ArrayList<String> listaFaculdades1 = null;
		
		try {
			listaFaculdades1 = comunicacao.ListFaculdades();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (String textoTemp:listaFaculdades1) {
			if(listaTemp=="") {listaTemp = textoTemp;}
			else {listaTemp = listaTemp+";"+textoTemp;}
		}
		String facTemp = leTeclado.mudaListaString("Faculdade: ", listaTemp);
		
		System.out.println(id+ " "+sigla+" "+nomeDepart+" "+Integer.parseInt(facTemp.split(" - ")[2]));
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		
		if(leTeclado.pedeNumero("Op��o: ", 0, 1)== 1) {
			try {
				if(comunicacao.addDepartamento(sigla, nomeDepart, id, Integer.parseInt(facTemp.split(" - ")[2]) )) {
					return true;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}		
		}
		return false;
		
		
	}

}