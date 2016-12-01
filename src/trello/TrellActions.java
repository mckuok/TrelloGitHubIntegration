/**
 * 
 */
package trello;

import java.util.List;
import org.eclipse.jdt.internal.compiler.parser.diagnose.LexStream.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Action.Data.CheckList;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;

/**
 * Class to have functions made from Trello API
 * @author Leon Qu
 *
 */
public class TrellActions {

  AccountAuth token = new AccountAuth();
  Trello trello = token.trello;
  /**
   * Gets the boards in account from trello url and prints in JSON format
   * @return null
   **/
  public void showTrelloBoard() throws JsonProcessingException{
    Board boards = null;
    
    boards.setUrl("https://api.trello.com/1/boards/{0}");
    
    if(checkValidID("4f92b99b5c92e5cd28006ee8") == true){
      boards = trello.getBoard("4f92b99b5c92e5cd28006ee8");
    }
    
    ObjectMapper mapper = new ObjectMapper();

    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

    mapper.writeValueAsString(boards);
  }
  
  
  /**
   * Gets the card from the user account
   * @return the card
   */
  public Card getCard(){
    Board board = trello.getCardBoard("");
    Card userCard = board.fetchCard("");
    
    return userCard;
  }
  
 
  /**
   * Checks validity of input
   * @param s
   * @return true/false
   */
  public boolean checkValidID(String s)
  {
    if(s == null){
      return false;
    }
    else{
      return true;
    }
  }
  
}
