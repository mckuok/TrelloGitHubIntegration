/**
 * 
 */
package trello;

import org.trello4j.Trello;
import org.trello4j.TrelloException;
import org.trello4j.TrelloImpl;
import org.trello4j.TrelloURL;
import org.trello4j.TrelloUtil;
import org.trello4j.model.Board;
import org.trello4j.model.Organization;
import org.trello4j.model.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.trello4j.Trello;

/**
 * @author Leon Qu
 *
 * Class to authenticate user account and allow application to access the account
 */
public class AccountAuth{
  
  private String myToken = "https://trello.com/1/authorize?expiration=never&amp;scope=read,write,account&amp;response_type=token&amp;name=Server%20Token&amp;key=f7af5802a8798f22ee397600dde10430";
  private static String APIKey = "f7af5802a8798f22ee397600dde10430";
  private String username = "";
  private String OAuthKey = "9d47ec182231e6764fb8d10e97053e18973cd0418ab63fa23a11c977cb717ed7";
 
  public Trello trello = new TrelloImpl(APIKey, myToken);

  
  /*
   * Constructor for Trello authentication class
   */
  public AccountAuth(String username, String APIKey, String myToken){
    this.username = username;
    AccountAuth.APIKey = APIKey;
    this.myToken = myToken;
    
  }
  

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
  
  
  public void authentication(String APIKey){
    
    
    System.out.println(verification(trello));
  }
  
  /*
   * Gets the boards in account from trello url and prints in JSON format
   */
  public void getTrelloBoard() throws JsonProcessingException{
    Board boards = null;
    
    boards.setUrl("https://api.trello.com/1/boards/{0}");
    
    if(checkValidID("4f92b99b5c92e5cd28006ee8") == true){
      boards = trello.getBoard("4f92b99b5c92e5cd28006ee8");
    }
    
    ObjectMapper mapper = new ObjectMapper();

    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

    mapper.writeValueAsString(boards);
  }
  
  
  public boolean checkValidID(String s){
    if(TrelloUtil.isObjectIdValid(s)){
      return true;
    }
    else{
      return false;
    }
  }
}

