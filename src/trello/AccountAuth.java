/**
 * 
 */
package trello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.CheckList;
import com.julienvey.trello.domain.Organization;
import com.julienvey.trello.impl.TrelloImpl;

/**
 * @author Leon Qu
 *
 * Class to authenticate user account and allow application to access the account
 */
public class AccountAuth{
  
  public String getMyToken = "https://trello.com/1/authorize?expiration=never&name=SinglePurposeToken&key=f7af5802a8798f22ee397600dde10430";
  public String APIKey = "f7af5802a8798f22ee397600dde10430";
  public String username = "";
  public String AuthToken = null;
 
  public Trello trello = new TrelloImpl(APIKey, AuthToken);

  
  /**
   * Asks the user to enter the token given.
   * @return
   */
  public String AuthToken(){
   Scanner input = new Scanner(System.in);
   
   requestUserToken();
   System.out.println("Enter token code that printed.");
   
   AuthToken = input.nextLine();
   input.close();
   return AuthToken;
  }
  

  /**
   * Opens url connection for user to copy token to allow actions 
   * @param 
   * @return null
   */
  public void requestUserToken(){
    
    try {
      HttpsURLConnection url = (HttpsURLConnection) new URL(getMyToken).openConnection();
      
      BufferedReader br = new BufferedReader(new InputStreamReader(url.getInputStream()));
      
      String token;
      
      while((token = br.readLine()) != null){
        System.out.println("User token successfully saved.\n");
        System.out.println(token);
      }
      
      br.close();
      url.disconnect();
    }
    catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  
  /**
   * 
   * @param trl: the trello object
   * @return return result: success or not
   */
  public String verification(Trello trl){
    String result = "";
    
    if(trl != null){
      result = "Verification successful.";
    }
    else{
      result = "Verificaiton failed.";
    }
    
    return result;
  }
  //TrelloException UserExcep = new TrelloException(username + " has allowed access to Trello.");
  
  
  public void authenticationMsg(String APIKey){
    
    System.out.println(verification(trello));
  }
  
}

