package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

import javax.management.ListenerNotFoundException;

public class ClientThread implements Runnable{
	private Socket p2p;
	ServerSocket listener;
	int listeningport;

	public ClientThread(int listeningport){
		this.listeningport=listeningport;
	}

	public void run() {

		try {
			listener = new ServerSocket(listeningport);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while(true){
			try {
				p2p= listener.accept();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String req,version="P2P-CI/1.0";
			String outst="";
			java.util.Date date= new java.util.Date();

			try {
				DataInputStream in = new DataInputStream(p2p.getInputStream());
				DataOutputStream out = new DataOutputStream(p2p.getOutputStream());


				req = in.readUTF();
				String split[] = req.split(" |\n");
				if(split[0].equals("GET")){
					int RFCno = Integer.parseInt(split[2]);
					String hostname = split[5];
					String ops = req.substring(req.indexOf("OS:")+3);
					File f = new File(RFCno+".txt");
					if(!f.exists()){
						outst = version+" 404 Not Found";	
						out.writeUTF(outst);
						out.close();
						in.close();
						//fis.close();
					}
					outst = version+" 200 OK\nDate: "+new Timestamp(date.getTime());
					outst+= "\nOS: "+System.getProperty("os.name")+"\nLast-Modified: "+f.lastModified();
					outst+= "\nContent-Length: "+f.length()+"\nContent-Type: text/text";		
					out.writeUTF(outst);
					String file = RFCno+".txt";
					FileInputStream fis = new FileInputStream(file);
					byte[] buffer=new byte[1];
					while (fis.read(buffer) > 0) {
						out.write(buffer);
						//System.out.println("out:"+buffer);
					}

					fis.close();
					in.close();
					out.close();
					p2p.close();
				}
				// TODO else print error

			}

			catch(IOException e){
			}
		}
	}
}



