package Server;
class activePeersLinkedList{
    activePeersLinkedListNode head;
    
    
    //create an empty LL
    public activePeersLinkedList(){
        head=new activePeersLinkedListNode(null,0);
    }
    
    //when a peer comes on the server adds its hostname and portnumber to the front of LL 
    public synchronized void addPeer(String hostName, int portNumber){
        activePeersLinkedListNode a=new activePeersLinkedListNode(hostName,portNumber);
        activePeersLinkedListNode temp=head;
        head=a;
        a.next=temp;
    }
    
    //when a peer leaves all its records are deleted from the LL
    public synchronized void deletePeer(String hostName){
        activePeersLinkedListNode temp=head;
        activePeersLinkedListNode prev=head;
        
        while(temp.next!=null){
            if(temp.hostName.equals(hostName)){
            	prev.next=temp.next;
                temp=temp.next;
            }
            else{
	            prev=temp;
	            temp=temp.next;
            }
        }
        if(head.hostName.equals(hostName)){
        	head=head.next;
        }
    }
    
    //when a peer asks for rfc find the other peers portno given its hostname
    public synchronized int findPeer(String hostname){
    	activePeersLinkedListNode temp=head;
    	while(temp.next!=null){
    		if(temp.hostName.equals(hostname)){
    			return temp.portNumber;
    		}
    		temp = temp.next;
    	}
    	return -1;
    }
    
    public void print(){
        activePeersLinkedListNode temp=head;
        while(temp.next!=null){
            //System.out.println(temp.hostName+"   "+temp.portNumber+"  "+temp.next);
            temp=temp.next;
        }
    }          
}

    class activePeersLinkedListNode{
        activePeersLinkedListNode next;
        String hostName;
        int portNumber;
    
        //constructor
        public  activePeersLinkedListNode(String hostName,int portNumber){
            this.next=null;
            this.hostName=hostName;
            this.portNumber=portNumber;
        }
}
