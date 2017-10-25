package ivote;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

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
	
	public String pedeData(String text) {
		Scanner sc = new Scanner(System.in);
		System.out.println(text);
		int anoTemp = pedeNumero("Ano: ", 1900,1900 + (new Date(System.currentTimeMillis()).getYear()));
		int mesTemp = pedeNumero("Mês: ", 1, 12);		
		String data = String.format("%2.0n %4.0n", mesTemp, anoTemp); 
		return data;
		
	}	
	
	
}
