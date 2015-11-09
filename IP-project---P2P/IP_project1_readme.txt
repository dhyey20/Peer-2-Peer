The file we are submitting is an eclipse project and uses JDK8. Please open eclipse and import the project into eclipse. 

The project source file contains two folders namely client and server.
First run the multithreadedserver.java file in eclipse which opens the server listening at some specified port (7734).

Now run the client using the client_main file from the client folder which will ask to enter servers ip address. Please enter that. Then client will show you a menu. The client initially has one RFC that we have saved in its folder and have sent an add request to the server when the client first makes a connection to the server.

The server then responds with an OK message to the client.The client then can add an RFC, lookup some RFC from the server, get list of all RFCs from the server and can also request an RFC from some other peer in the network.

We have explicitly made two clients client_main and client_main1. 




If using command line todo the same:
Make sure that you have java and javac version as 8. Then compile all the classes in server and client folder inside the src folder. Then to run the server come to the src folder and type 
java -cp . Server.MultithreadedServer

Then run the first client using:
java -cp . Client.Client_Main

And then run the second client using:
java -cp . Client.Client_Main1

