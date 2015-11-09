package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;


public class Client_Main1 {
	static int listeningport=20001;
	static String version = "P2P-CI/1.0";

	public static void main(String args[]) throws InterruptedException, IOException{
		//Client c = new Client("Uday-Laptop",7734,listeningport);
		System.out.println("Please enter server's IP address:");
		Scanner keyin = new Scanner(System.in);
		String serverip = keyin.nextLine();
		//Client c = new Client("10.139.56.185",7734,listeningport);
		//changed to ip
		Client c = new Client(serverip,7734,listeningport);
		//Add RFC
		c.addRFC(1508, "1508");
		// Make a thread for the listener
		ClientThread ct = new ClientThread(listeningport);
		Thread th = new Thread(ct);
		th.start();

		Scanner stdin = new Scanner(System.in);
		while(true){
			System.out.println("Enter your Choice");
			System.out.println("1. Add RFC");
			System.out.println("2. Lookup RFC");
			System.out.println("3. List ALL");
			System.out.println("4. Get RFC");
			System.out.println("0. Exit");
			int input = stdin.nextInt();

			switch(input){
			// Add RFC
			case 1:
				System.out.println("Enter RFCNO & Title:\n");
				c.addRFC(stdin.nextInt(), stdin.nextLine());
				break;
			case 2:
				System.out.println("Enter RFCNO & Title:\n");
				c.lookupRFC(stdin.nextInt(), stdin.nextLine());
				break;
			case 3:
				c.listallRFC();
				break;
			case 4:
				System.out.println("Enter RFCNO & Title:\n");
				int RFCno = stdin.nextInt();
				String title = stdin.nextLine();
				String reply = c.lookupRFC(RFCno,title);
				String split[] = reply.split(" |\n");
				int peerport = Integer.parseInt(split[split.length-1]);
				String peername = split[split.length-2];
				//System.out.println(title + reply + peerport + peername);
				Socket clientreq = new Socket(peername, peerport);
				DataInputStream in = new DataInputStream(clientreq.getInputStream());
				DataOutputStream out = new DataOutputStream(clientreq.getOutputStream());

				//String outst = "GET RFC "+RFCno+" "+version+"\nHost: "+InetAddress.getLocalHost().getHostName()+"\nOS: "+System.getProperty("os.name");
				//changed for ip address
				String outst = "GET RFC "+RFCno+" "+version+"\nHost: "+InetAddress.getLocalHost().getHostAddress()+"\nOS: "+System.getProperty("os.name");
				out.writeUTF(outst);
				//System.out.println("*****"+outst);
				String rep = in.readUTF();
				System.out.println("Reply from Peer: \n"+rep);
				String split1[] = reply.split(" |\n");
				if(split1[2].equals("OK")){
					String file = RFCno+".txt";
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buffer = new byte[1];
					//System.out.println(in.read());
					while((in.read(buffer)) != -1){
						//System.out.println("buffer: "+buffer);
						fos.write(buffer);
					}
					System.out.println("RFC received..");

					fos.close();
					out.close();
					in.close();
					c.addRFC(RFCno,title);
				}
				break;
			case 0:
				// Kill the Listener thread.
				c.close();
				System.exit(0);
				break;

			default:
				continue;
			}
		}

	}
}
