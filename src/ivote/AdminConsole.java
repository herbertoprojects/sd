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
			System.out.println("1- Gerir utilizador");//adicionar, remover, consultar
			System.out.println("2- Gerir faculdades");//adicionar, remover, consultar
			System.out.println("3- Gerir departamentos");//adicionar, remover, consultar
			System.out.println("4- Gerir elei��es");//criar elei��o, adicionar listas, remover listas, consultar listas, consultar elei��es, remover elei��es
			System.out.println("5- Dados de elei��es (Real Time)");//escolher qual a elei��o que est� a correr e recebe elei��es e come�amos a receber as notifica��es
			System.out.println("6- Voto antecipado");//autenticar a pessoa
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
				leTeclado.leLinha("Continuar...");
				break;
			case 2:
				if(removeUser()) {
					System.out.println("Utilizador removido!");		
				}
				else
					System.out.println("Erro na remo��o do utilizador");
					leTeclado.leLinha("Continuar...");
				break;
			case 3:
				System.out.println("jsddvhsv1");
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
			System.out.println("Op��o: ");
			int option2 = leTeclado.pedeNumero("Op��o: ", 0, 3);
	}
	public void menuDep() {
		System.out.println("------------Sub Menu dos Departamentos------------");
		System.out.println("1- Adicionar departamento");
		System.out.println("2- Remover departamento");
		System.out.println("3- Consultar Departamento");
		System.out.println("Op��o: ");
		int option3 = leTeclado.pedeNumero("Op��o: ", 0, 3);
		
	}
	public void menuEleicoes() {
		System.out.println("------------Sub Menu das Elei��es------------");
		System.out.println("1- Criar elei��o");
		System.out.println("2- Adicionar listas");
		System.out.println("3- Remover listas");
		System.out.println("4- Consultar listas");
		System.out.println("5- Remover elei��es");
		System.out.println("6- Consultar elei��es");
		int option4 = leTeclado.pedeNumero("Op��o: ", 0, 3);
		
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
			}
			else
				System.out.println("Utilizador n�o encontrado");
			
		} catch (RemoteException e) {
			e.printStackTrace();
			
		}
		return true;

	}
	
}
