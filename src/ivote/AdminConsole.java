package ivote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
	public static RMI_1 comunicacao;
	public getScanner leTeclado;
	
	public AdminConsole() throws RemoteException{
		/***
		 * construtor da classe AdminConsole
		 * @throws retorna uma exceção quando não há ligação com o servidor
		 */
		
		super();
		leTeclado = new getScanner();
	}
	
	public static void main(String[] args){
		/**
		 * inicializa ligação com o RMI server e executa o menu
		 */
		AdminConsole consola = null;
		try {
			consola = new AdminConsole();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		try {
			comunicacao = (RMI_1) Naming.lookup("rmi://localhost:7000/rmi");
		} catch (RemoteException | MalformedURLException | NotBoundException  e) {
			e.printStackTrace();
		}
		
		consola.menuInicial();

	}
	
	public void menuInicial() {
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
	
	
	public void menuFac() {
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
			
			switch(leTeclado.pedeNumero("Opção: ", 0, 3)) {
			
				case 1:
					if (criaDep()) {
						System.out.println("Departamento adicionado!");
					}
					else {
						System.out.println("Erro na criação do departamento!");
					}
					leTeclado.leLinha("Continuar...");
					break;
					
				case 2:
					if(removeDep()) {
						System.out.println("Departamento removido!");		
					}
					else
						System.out.println("Erro na remoção de departamento");
						leTeclado.leLinha("Continuar...");
					break;
					
				case 3:
					if(!consultaDep()) {
						System.out.println("Erro na consulta de departamento!");			
					}
					leTeclado.leLinha("Continuar...");
					break;
				
				case 0:
					return;
				
					
			}
			leTeclado.leLinha("Continuar...");
		}
		
		
	}
	
	public void menuEleicoes() {
		while(true) {
			System.out.println("------------Sub Menu das Eleições------------");
			System.out.println("1- Criar eleição");
			System.out.println("2- Adicionar listas");
			System.out.println("3- Remover listas");
			System.out.println("4- Consultar listas");
			System.out.println("5- Remover eleições");
			System.out.println("6- Consultar eleições");
			
			switch(leTeclado.pedeNumero("Opção: ", 0, 6)) {
				case 1:
					String tipoTemp = tipoEleicao();
					String horaInicioTemp = leTeclado.pedeDataHora("Data de Inicio: ");
					String horaFim = leTeclado.pedeDataHora("Data de Fim: ");
					
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
				case 0:
					return;
			}
			leTeclado.leLinha("Continuar...");
		}
		
	}
	
	public boolean criaUser() {
		int nccTemp = leTeclado.pedeNumero("Número de cartão de cidadão: ", 9999999, 100000000);
		try {
			if (comunicacao.testeNCC(nccTemp)) {
				
				int telefoneTemp = leTeclado.pedeNumero("Contactos: ", 99999999, 1000000000);		//telefone
				
				String nomeTemp = leTeclado.leLinha("Nome: ");										//nome
				
				String passTemp = leTeclado.leLinha("Password: ");									//password
				
				String moradaTemp = leTeclado.leLinha("Morada: ");									//morada
				
				String dataccTemp = leTeclado.pedeData("Data cartão cidadão: ");					//Data CC
				
				
				String listaTemp = "";
				ArrayList <String> listaFaculdades1 = comunicacao.ListFaculdades();
				for (String textoTemp:listaFaculdades1) {
					if(listaTemp=="") {listaTemp = textoTemp;}
					else {listaTemp = listaTemp+";"+textoTemp;}
				}
				
				
				String facTemp = leTeclado.mudaListaString("Faculdade: ", listaTemp);				//faculdade
				
				listaTemp = "";
				listaFaculdades1 = comunicacao.ListDepartamentos(facTemp);
				for (String textoTemp:listaFaculdades1) {
					if(listaTemp=="") {listaTemp = textoTemp;}
					else {listaTemp = listaTemp+";"+textoTemp;}
				}
				
				String depTemp = leTeclado.mudaListaString("Departamento: ", listaTemp);
				
				String cargoTemp = leTeclado.mudaListaString("Cargo: ","aluno;docente;funcionario");
				
				//cargo
				System.out.println("1- Aluno");
				System.out.println("2- Docente");
				System.out.println("3- Funcionário");
				
				
				switch(leTeclado.pedeNumero("Opção: ", 1, 3)) {
				case 1: cargoTemp = "aluno";
					break;
				case 2: cargoTemp = "docente";
					break;
				case 3: cargoTemp = "funcionario";
					break;
				
				}
				comunicacao.registar(cargoTemp, nccTemp, dataccTemp, nomeTemp, passTemp, telefoneTemp, moradaTemp, facTemp, depTemp);
			}
			
		} 
		
		
		catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean removeUser() {
		
		int tempNumero = leTeclado.pedeNumero("Introduza o número de cartão de cidadão: ", 9999999, 100000000);
		try {
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
		
		int tempNumero = leTeclado.pedeNumero("Introduza o número de cartão de cidadão: ", 9999999, 100000000);
		try {
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
			System.out.println("8- Altera Data do Cartão de Cidadão");
			System.out.println("0- Sair");
			
			switch (leTeclado.pedeNumero("Opção: ", 0, 8)) {
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
	
	public boolean criaFac() {
		
		String nomeFaculd = leTeclado.leLinha("Nome da faculadade: ");
		String sigla = leTeclado.leLinha("Sigla da faculdade: ");
		int id = leTeclado.pedeNumero("Id da faculdade: ", 0, 999);
		System.out.println(id+ " "+sigla+" "+nomeFaculd);
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		
		if(leTeclado.pedeNumero("Opção: ", 0, 1)== 1) {
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
	
			}
			while (!comunicacao.removeFaculdade(idFac) || idFac == 0);
			return idFac == 0 ? false:true;
			
		} catch (RemoteException e) {
			e.printStackTrace();	
		}	
		return false;
	}
	
	
	public boolean consultaDep() {
		
		int idDep=0;
		try {
			for(String dep:comunicacao.ListDepartamentos(idDep)) {
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
			for(String temp_2:comunicacao.ListDepartamentos(idDep_2)) {
				System.out.println(temp_2);				
			}
			do {
				idDep_2 = leTeclado.pedeNumero("Introduza o id do departamento (0- Cancelar): ", 0, 999);
	
			}
			while (!comunicacao.removeFaculdade(idDep_2) || idDep_2 == 0);
			return idDep_2 == 0 ? false:true;
			
		} catch (RemoteException e) {
			e.printStackTrace();	
		}	
		return false;
			
	}
	
	public boolean criaDep() {
		
		String sigla = leTeclado.leLinha("Sigla do departamento: ");
		String nomeDepart = leTeclado.leLinha("Nome do departamento: ");
		int id = leTeclado.pedeNumero("Id do departamento: ", 0, 999);
		int idFac = leTeclado.pedeNumero("Id da faculdade", 0, 999);
		
		System.out.println(id+ " "+sigla+" "+nomeDepart+" "+idFac);
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		
		if(leTeclado.pedeNumero("Opção: ", 0, 1)== 1) {
			try {
				if(comunicacao.addDepartamento(sigla, nomeDepart, id, idFac )) {
					return true;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}		
		}
		return false;
		
		
	}

}