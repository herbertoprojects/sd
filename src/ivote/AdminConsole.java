package ivote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AdminConsole extends UnicastRemoteObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static RMI_1 comunicacao;
	public getScanner leTeclado;
	
	public AdminConsole() throws RemoteException{
		super();
		leTeclado = new getScanner();
	}
	
	public static void main(String[] args){
		AdminConsole consola = null;
		try {
			consola = new AdminConsole();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		/*try {
			//comunicacao = (RMIRemoteInterface) Naming.lookup("rmi://localhost:7000/DepartamentoInformatica");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			//e.printStackTrace();
		}*/
		consola.menuInicial();

	}
	
	public void menuInicial() {
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
				leTeclado.leLinha("Continuar...");
				break;
			case 2:
				if(removeUser()) {
					System.out.println("Utilizador removido!");		
				}
				else
					System.out.println("Erro na remoção do utilizador");
					leTeclado.leLinha("Continuar...");
				break;
			case 3:
				if(!consultaUser()) {
					System.out.println("Erro na consulta de utilizador!");			
				}
				leTeclado.leLinha("Continuar...");
				break;
			
		}
		
	}
	public void menuFac() {
		 System.out.println("------------Sub Menu das Faculades------------");
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
				leTeclado.leLinha("Continuar...");
				break;
			case 2:
				if(removeFac()) {
					System.out.println("Faculdade removida!");		
				}
				else
					System.out.println("Erro na remoção da faculdade");
					leTeclado.leLinha("Continuar...");
				break;
			case 3:
				if(!consultaFac()) {
					System.out.println("Erro na consulta da faculdade!");			
				}
				leTeclado.leLinha("Continuar...");
				break;
				
			case 0:
				return;
			
			}
	}
	public void menuDep() {
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
		
		
	}
	public void menuEleicoes() {
		System.out.println("------------Sub Menu das Eleições------------");
		System.out.println("1- Criar eleição");
		System.out.println("2- Adicionar listas");
		System.out.println("3- Remover listas");
		System.out.println("4- Consultar listas");
		System.out.println("5- Remover eleições");
		System.out.println("6- Consultar eleições");
		int option4 = leTeclado.pedeNumero("Opção: ", 0, 3);
		
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
			
				String facTemp = pedeFac();															//faculdade
				String depTemp = pedeDep(facTemp);
				String cargoTemp = "";
				
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
			}
			else
				System.out.println("Utilizador não encontrado");
			
		} catch (RemoteException e) {
			e.printStackTrace();
			
		}
		return true;

	}
	
	public boolean criaFac() {
		
		String nomeFaculd = leTeclado.leLinha("Nome da faculadade: ");
		String sigla = leTeclado.leLinha("Sigla da faculdade: ");
		int id = leTeclado.pedeNumero("Id da faculdade: ", 1000, 200000000);
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
		
		int idFac = leTeclado.pedeNumero("Introduza o id da faculdade: ", 1000, 20000000);
		try {
			if(comunicacao.removeFaculdade(idFac)) {
				System.out.println("1- Confirmar");
				System.out.println("0- Cancelar");
				if(leTeclado.pedeNumero("Opção: ", 0, 1) == 1) {
					if(comunicacao.removeFaculdade(idFac)) {
						return true;
					}										
				}
			}	
		} catch (RemoteException e) {
			e.printStackTrace();	
		}	
		return false;
	}
	
	
	
	
}
