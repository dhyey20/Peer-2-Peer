package Server;
public class rfcLL {
    rfcLLNode head;
    
    //empty LL
    public rfcLL(){
        head=new rfcLLNode(-1,null,null);
    }
    
    //add info about rfc to the list
    public void addRFCInfo(int rfcNumber, String rfcTitle, String hostName){
        rfcLLNode newNode=new rfcLLNode(rfcNumber, rfcTitle, hostName);
        rfcLLNode temp=head;
        head = newNode;
        head.next=temp;
    }
    
    public void deleteRFCInfo(String hostname){
        rfcLLNode temp=head;
        rfcLLNode prev=head;
        
        while(temp.next!=null){
            if(temp.hostName.equals(hostname)){
                prev.next=temp.next;
                temp=temp.next;
            }
            else{
            	prev=temp;
            	temp=temp.next;
            }
            
        }

        if(head.hostName.equals(hostname)){
        	head=head.next;
        }
    }
    
    public String searchRFC(int rfc){
    	rfcLLNode temp=head;
    	while(temp.next!=null){
    		if(temp.rfcNumber==rfc){
    			return temp.hostName;
    		}
    		temp = temp.next;
    	}
    	return "-1";
    }
    
    public int searchRFC(int rfc, String hostname){
    	rfcLLNode temp=head;
    	while(temp.next!=null){
    		if(temp.rfcNumber==rfc && temp.hostName.equals(hostname)){
    			return 0;
    		}
    		temp = temp.next;
    	}
    	return -1;
    }
    
    
    public void print(){
        rfcLLNode temp=head;
        while(temp.next!=null){
            System.out.println(temp.hostName+"   "+temp.rfcNumber+"  "+temp.rfcTitle+" "+temp.next);
            temp=temp.next;
        }
    }       
}

class rfcLLNode{
    rfcLLNode next;
    int rfcNumber;
    String rfcTitle;
    String hostName;
    
    //constructor
    public rfcLLNode(int rfcNumber, String rfcTitle, String hostName){
        this.next=null;
        this.rfcNumber=rfcNumber;
        this.rfcTitle=rfcTitle;
        this.hostName=hostName;
    }
}
