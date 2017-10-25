package ivote;

import java.util.Scanner;

public class getScanner {
	
	public getScanner(){
		
	}
	
	public int pedeNumero(String texto,int minNum,int maxNum) {
			Scanner sc = new Scanner(System.in);
			String s;
			int num = 0;
			boolean teste = true;
			
			do {
				System.out.print(texto);
				s = sc.nextLine();
				try {
					num = Integer.parseInt(s);
					teste = false;
					if(num<minNum) {teste = true;System.out.println("O numero tera de ser maior que "+minNum);}
					if(num>maxNum) {teste = true;System.out.println("O numero tera de ser menor que "+maxNum);}
				}catch(NumberFormatException e) {
					teste = true;
					System.out.println("Formato invalido. Por favor coloque um numero.");
				}
				
			}while(teste);
			return num;
	}
		
	
	public String leLinha(String texto) {
		Scanner sc = new Scanner(System.in);
		System.out.print(texto);
		String textoR = sc.nextLine();
		return textoR;
	}
	
	
}
