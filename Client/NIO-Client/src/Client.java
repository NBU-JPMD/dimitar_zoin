import java.io.*;
import java.net.*;

class Client
{
 public static void main(String argv[]) throws Exception
 {
  String sentence = "";
  String modifiedSentence;
  
  BufferedReader inFromUser;
  Socket clientSocket = new Socket("localhost", 6578);
  
  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  
  while(sentence != "exit")
  {
	  inFromUser = new BufferedReader( new InputStreamReader(System.in));
	  sentence = inFromUser.readLine();
	  outToServer.writeBytes(sentence + "\n");
	  modifiedSentence = inFromServer.readLine();
	  System.out.println("FROM SERVER: " + modifiedSentence); 
  }
  
  clientSocket.close();
 }
}