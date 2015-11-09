package Server;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedServer{
	private static int PortNo = 7734;
	public static activePeersLinkedList ap = new activePeersLinkedList();
	public static rfcLL rf =new rfcLL();

	public static void main(String[] args){
		try{
			ServerSocket listener = new ServerSocket(PortNo);
			Socket server;
			System.out.println("Server");
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			System.out.println("10.139.64.144");
			//InetAddress.getByAddress()
			while(true){
				if((server = listener.accept())!= null){
					System.out.println(server.getRemoteSocketAddress())	;	
					ServerThread st = new ServerThread(server);
					Thread t = new Thread(st);
					//System.out.println(server.getInetAddress());
					t.start();
					//System.out.println(t);
				}
			}
		}
		catch(IOException ioe){
			System.out.println("IOException on Socket Occured" + ioe);
		}
	}
}
