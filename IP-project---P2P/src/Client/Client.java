package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	Socket s = null;
	DataInputStream in = null;
	DataOutputStream out = null;
	String version = "P2P-CI/1.0";
	int portno;
	String reply;
	String outst;
	
	//socket 
	public Client(String servername, int serverport, int clientport) throws UnknownHostException, IOException{
		//this.s = new Socket(servername, serverport);
		this.s = new Socket(InetAddress.getByName(servername),serverport);
		this.in = new DataInputStream(s.getInputStream());
		this.out = new DataOutputStream(s.getOutputStream());
		this.portno = clientport;
	}
	
	public void addRFC(int rfcno, String title) throws IOException{
		//outst = "ADD RFC "+rfcno+" "+version+"\nHost: "+InetAddress.getLocalHost().getHostName();
		//changed to ip
		outst = "ADD RFC "+rfcno+" "+version+"\nHost: "+InetAddress.getLocalHost().getHostAddress();
		outst = outst+"\nPort: "+portno+"\nTitle: "+title;
		out.writeUTF(outst);
		reply = in.readUTF();
		System.out.println("\nReply from server:\n"+reply+"\n");
	}
	
	public String lookupRFC(int rfcno, String title) throws IOException{
		//outst = "LOOKUP RFC "+rfcno+" "+version+"\nHost: "+InetAddress.getLocalHost().getHostName();
		//changed to ip
		outst = "LOOKUP RFC "+rfcno+" "+version+"\nHost: "+InetAddress.getLocalHost().getHostAddress();
		outst = outst+"\nPort: "+portno+"\nTitle: "+title;
		out.writeUTF(outst);
		reply = in.readUTF();
		System.out.println("\nReply from server:\n"+reply+"\n");
		return reply;
	}
	
	public void listallRFC() throws IOException{
		//outst = "LIST ALL RFC "+version+"\nHost: "+InetAddress.getLocalHost().getHostName();
		//changed to ip
		outst = "LIST ALL RFC "+version+"\nHost: "+InetAddress.getLocalHost().getHostAddress();
		outst = outst+"\nPort: "+portno;
		out.writeUTF(outst);
		reply = in.readUTF();
		System.out.println("\nReply from server:\n"+reply+"\n");
	}
	
	public void close() throws IOException{
		s.close();
	}
}
