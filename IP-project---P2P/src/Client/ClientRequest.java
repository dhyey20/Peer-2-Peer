package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientRequest {
	Socket s = null;
	DataInputStream in = null;
	DataOutputStream out = null;
	String version = "P2P-CI/1.0";
	int portno;
	String outst;

	public ClientRequest(){
	}

	public int requestFRC(int RFCno, String reply) throws UnknownHostException, IOException{
		String split[] = reply.split(" |\n");
		int peerport = Integer.parseInt(split[split.length-1]);
		String peername = split[split.length-2];
		Socket clientreq = new Socket(peername, peerport);
		in = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());

		//outst = "GET RFC "+RFCno+" "+version+"\nHost: "+InetAddress.getLocalHost().getHostName()+"\nOS: "+System.getProperty("os.name");
		//changed to ip
		outst = "GET RFC "+RFCno+" "+version+"\nHost: "+InetAddress.getLocalHost().getHostAddress()+"\nOS: "+System.getProperty("os.name");
		out.writeUTF(outst);
		String rep = in.readUTF();
		String split1[] = reply.split(" |\n");
		if(split1[3].equals("OK")){
			String file = RFCno+".txt";
			FileOutputStream fos = new FileOutputStream(file);
			byte buffer;
			while((buffer = in.readByte())> 0){
				System.out.println("RFC received..");
				fos.write(buffer);
			}
			fos.close();
			out.close();
			in.close();
			return 1;
		}
		else{
			return 0;
		}
		
	}
}
