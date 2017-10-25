package ivote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.Scanner;

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
		consola.menu();
		

	}
	public void menu() {
		System.out.println("------------MAIN MENU-----------");
		
		System.out.println("1- Gerir utilizador");
		//adicionar, remover, consultar
		System.out.println("2- Gerir faculdades");
		//adicionar, remover, consultar
		System.out.println("3- Gerir departamentos");
		//adicionar, remover, consultar
		System.out.println("4- Gerir elei��es");
		//criar elei��o, adicionar listas, remover listas, consultar listas, consultar elei��es, remover elei��es
		System.out.println("5- Dados de elei��es (Real Time)");
		//escolher qual a elei��o que est� a correr e recebe elei��es e come�amos a receber as notifica��es
		System.out.println("6- Voto antecipado");
		//autenticar a pessoa
		System.out.println("0- Sair");
		Scanner sc = new Scanner(System.in);
		
		int option = leTeclado.pedeNumero("Op��o: ", 0, 6);
		
		do {
			switch(option) {
			
			case 1: System.out.println("------------Sub Menu do Utilizador------------");
					System.out.println("1- Adicionar utilizador");
					System.out.println("2- Remover utilizador");
					System.out.println("3- Consultar utilizador");
					option = leTeclado.pedeNumero("Op��o: ", 0, 3);
					
					switch(option) {
						case 1:
							if (criaUser()) {
								System.out.println("Utilzador adicionado!");
							}
							else {
								System.out.println("Erro na cria��o de utilizador!");
							}
							leTeclado.leLinha("Continuar...");
							break;
						case 2:
							if(!removeUser()) {
								System.out.println("Erro na remo��o de utilizador!");
								
							}
							
							break;
						case 3:
							if(!consultaUser()) {
								System.out.println("Erro na consulta de utilizador!");
							}

							option = sc.nextInt();
						break;
					}
			break;
				
			case 2: System.out.println("------------Sub Menu das Faculades------------");
					System.out.println("1- Adicionar faculdade");
					System.out.println("2- Remover faculdade");
					System.out.println("3- Consultar faculdade");
					System.out.println("Op��o: ");
					option = leTeclado.pedeNumero("Op��o: ", 0, 3);
					
					switch (option) {
						case 1:
						case 2:
						case 3:
						option = sc.nextInt();
						break;					
					}
			break;
			
			case 3: System.out.println("------------Sub Menu dos Departamentos------------");
					System.out.println("1- Adicionar departamento");
					System.out.println("2- Remover departamento");
					System.out.println("3- Consultar Departamento");
					System.out.println("Op��o: ");
					option = leTeclado.pedeNumero("Op��o: ", 0, 3);
					
					switch (option) {
						case 1:
						case 2:
						case 3:
						option = sc.nextInt();
						break;
					}
			break;
			
			case 4: System.out.println("------------Sub Menu das Elei��es------------");
					System.out.println("1- Criar elei��o");
					System.out.println("2- Adicionar listas");
					System.out.println("3- Remover listas");
					System.out.println("4- Consultar listas");
					System.out.println("5- Remover elei��es");
					System.out.println("6- Consultar elei��es");
					option = leTeclado.pedeNumero("Op��o: ", 0, 3);
					
					switch (option) {
					
						case 1:
						case 2:
						case 3:
						case 4: 
						case 5:
						case 6:
						option = sc.nextInt();
						break;
					}
			break;
			
			case 5: System.out.println("------------Dados das elei��es------------");
					System.out.println("..........................................");
					System.out.println("..........................................");
					System.out.println("..........................................");
					
					
					switch (option) {
						
						case 1:
						case 2:
						case 3:
						option = sc.nextInt();
						break;
					}
			break;
			
			case 6: System.out.println("------------Voto Antecipado------------");
					System.out.println(".......................................");
					System.out.println(".......................................");
					System.out.println(".......................................");
					option = sc.nextInt();
					
					switch (option) {
						
						case 1:
						case 2:
						case 3:
						option = sc.nextInt();
						break;
					}
			break;
			
			case 0: System.exit(0);
				break;
				
			default: break;
			
			}
			
		} while (option != 0);
	}
	
	public boolean criaUser() {
		int nccTemp = leTeclado.pedeNumero("N�mero de cart�o de cidad�o: ", 9999999, 100000000);
		try {
			if (comunicacao.testeNCC(nccTemp)) {
				//telefone
				int telefoneTemp = leTeclado.pedeNumero("Contactos: ", 99999999, 1000000000);
				//nome
				String nomeTemp = leTeclado.leLinha("Nome: ");
				//password
				String passTemp = leTeclado.leLinha("Password: ");
				//morada
				String moradaTemp = leTeclado.leLinha("Morada: ");
				//Data CC
				String dataccTemp = leTeclado.pedeData("Data cart�o cidad�o: ");
				//faculdade
				String facTemp = pedeFac();
				String depTemp = pedeDep(facTemp);
				String cargoTemp = "";
				
				//cargo
				System.out.println("1- Aluno");
				System.out.println("2- Docente");
				System.out.println("3- Funcion�rio");
				switch(leTeclado.pedeNumero("Op��o: ", 1, 3)) {
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
		return true;
		
	}
	
	public String pedeFac() {
		return "";
		
	}
	
	public String pedeDep(String faculdade) {
		return "";
		
	}
	
	public boolean consultaUser() {
		System.out.println("1- Procurar por n�meo cart�o de cidad�o");
		System.out.println("2- Procurar por nome");
		System.out.println("3- Procurar por contacto");
		System.out.println("0- Sair");
		switch (leTeclado.pedeNumero("Op��o: ", 0, 3)) {
			case 1: int numero = leTeclado.pedeNumero("Introduza o n�mero: ", 9999999, 100000000);
			try {
				if(comunicacao.testeNCC(numero)) {
					String nome = comunicacao.getNome(numero);
					System.out.println(nome);
				}
				else
					System.out.println("Utilizador n�o encontrado");
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}			
				break;
			case 2:
				break;
			case 3:
				break;
			case 0:
				break;
		}
		return true;
	}
	
}
