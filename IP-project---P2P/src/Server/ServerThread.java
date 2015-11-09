package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class ServerThread implements Runnable{

	private Socket s;
	ServerThread(Socket s){
		this.s = s;
	}
	public void run() {
		String hostname="", req="",version="P2P-CI/1.0";
	
		int rfcno=0,port;
		// TODO Auto-generated method stub
		try {
			String ip = s.getRemoteSocketAddress().toString();
			String ipfinal = ip.substring(1, ip.indexOf(":"));
			//System.out.println(ipfinal);	
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			int count=0;
			//System.out.println(s.getInetAddress());
			while(true){
				req = in.readUTF();
				String split[] = req.split(" |\n");
				/*for(int i=0;i<split.length;i++){
					System.out.println(i+" "+split[i]);
				}*/
				if(!split[0].equals("LIST")){
					rfcno = Integer.parseInt(split[2]);
				}
				hostname = ipfinal;
				port = Integer.parseInt(split[7]);
				String title; 
				String outst="";

				//version not supported error
				if(!split[3].equals(version)){
					outst = version+" 505 P2P-CI version not supported.";
					out.writeUTF(outst);
					//added for testing
					//System.out.println(outst);
				}
				//System.out.println("Before ADD");
				//if it is an ADD request
				if(split[0].equals("ADD")){	
					//System.out.println("entering ADD");
					title = req.substring(req.indexOf("Title:")+6);
					//add hostname and port to activepeer(ap) if it doesnot exist
					if(count==0){
						MultithreadedServer.ap.addPeer(hostname, port);
						count++;
					}
					//System.out.println("After 1st if");

					//add rfc to the rfcll(rf)
					if(MultithreadedServer.rf.searchRFC(rfcno, hostname)==-1){
						MultithreadedServer.rf.addRFCInfo(rfcno, title, hostname);
					}
					outst = version+" 200 OK\nRFC "+rfcno+" "+title+" "+hostname+" "+port;
					out.writeUTF(outst);
					//added for testing
					//System.out.println("exiting ADD");
				}//end of "add"


				//if it is LOOKUP request
				else if(split[0].equals("LOOKUP")){
					//System.out.println("In LOOKUP");
					title = req.substring(req.indexOf("Title:")+6);
					//System.out.println(title);
					String rfchostname=MultithreadedServer.rf.searchRFC(rfcno);
					//System.out.println("Lookup:  "+rfchostname);
					if(rfchostname.equals("-1")){
						//not found 
						//rfc is not with any of the peers
						outst = version+" 404 Not Found";
						out.writeUTF(outst);
						//System.out.println(outst);
					}
					else{
						int rfcport = MultithreadedServer.ap.findPeer(rfchostname);
						if(rfcport==-1){
							//error
							System.out.println("Hostname found for rfc but portno not found");
						}
						else{
							outst=version+" 200 OK\nRFC "+rfcno+" "+title+" "+rfchostname+" "+rfcport;
							out.writeUTF(outst);
							//System.out.println(outst);
						}
					}
				}//Lookup ends

				else if(split[0].equals("LIST")){
					//System.out.println("In LIST");
					outst = version+" 200 OK\n";
					rfcLLNode rfnode = MultithreadedServer.rf.head;
					rfcLLNode temp  ;
					while(rfnode.next!=null){
						outst = outst+"RFC "+rfnode.rfcNumber+" "+rfnode.rfcTitle+" "+rfnode.hostName+" "+MultithreadedServer.ap.findPeer(rfnode.hostName)+"\n";
						rfnode=rfnode.next;
					}
					out.writeUTF(outst);
					//System.out.println(outst);
				}//end List

				else{
					outst = version+" 400 Bad Request";
					out.writeUTF(outst);
					//System.out.println(outst);
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
			int count=0;
			//System.out.println("inside catch "+count++);
			//MultithreadedServer.ap.print();
			//MultithreadedServer.rf.print();
			MultithreadedServer.ap.deletePeer(hostname);
			MultithreadedServer.rf.deleteRFCInfo(hostname);
			//System.out.println("inside catch after deleting");
			//MultithreadedServer.ap.print();
			//MultithreadedServer.rf.print();
			
		}

	}

}
