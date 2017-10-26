package ivote;


public class terminalVoto {
	
	public RMI_1 comunicacao;	
	public boolean login = false;
	public boolean autentication = false;
	public boolean vote = false;
	public String nomeCliente;
	public String passCliente;
	public int nccCLiente;
	
	public threadClienteTCP threadAssociada = null;
	
	public String nome;
	public String password;
	
	
	public terminalVoto (RMI_1 comunicacao,String nome,String password){
		
		this.comunicacao = comunicacao;
		this.nome = nome;
		this.password = password;
		
	}

}
