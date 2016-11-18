/**
 * 
 */
package trello;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.trello4j.TrelloImpl;
import org.trello4j.TrelloURL;
import org.trello4j.model.Action;
import org.trello4j.model.Action.Data;

/**
 * @author Leon Qu
 *
 */
public class TrelloDropdown {

  public static void main(String[] args) {
    
    String choice = ask("GET", "PUT", "DELETE", "POST");
    
}
  
  
public static void trell(String input){
  /*
   * Trello API Key ID for allowing application access to user account
   */
  TrelloImpl task = new TrelloImpl("f7af5802a8798f22ee397600dde10430");
  
  if(input.equals("GET")){
    task.getCard("https://api.trello.com/1/cards/{0}/actions");
    ask("CARD", "LIST", "BOARD");
  }
  if(input.equals("PUT")){
    task.getActionsByCard("https://api.trello.com/1/cards/{0}");
    ask("CARD", "LIST", "BOARD");
  }
  if(input.equals("POST")){
    //TODO
  }
  if(input.equals("DELETE")){
    //TODO
  }
}

/*
 * Creates the drop down menu for the user to pick what option to do for Trello Actions
 */
public static String ask(final String... values) {

    String result = null;

    if (EventQueue.isDispatchThread()) {

        JPanel panel = new JPanel();
        panel.add(new JLabel("Please select an action:"));
        
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (String value : values) {
            model.addElement(value);
        }
        
        JComboBox comboBox = MenuListener(panel, model);

        int iResult = JOptionPane.showConfirmDialog(null, panel, "Trello Options", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (iResult) {
            case JOptionPane.OK_OPTION:
                result = (String) comboBox.getSelectedItem();
                break;
        }

    } 
    else {

        Response response = new Response(values);
        try {
            SwingUtilities.invokeAndWait(response);
            result = response.getResponse();
        } catch (InvocationTargetException | InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    return result;

}


public static JComboBox MenuListener(JPanel panel, DefaultComboBoxModel model){
  JComboBox comboBox = new JComboBox(model);
  panel.add(comboBox);
  /*
   * ActionListener lib will take the choice of the user and do further actions from drop
   * down menu. i.e. choosing get will then allow user to get card, post to post card onto list
   */
  ActionListener action = new ActionListener(){
    
    public void actionPerformed(ActionEvent e){
      String s = (String) comboBox.getSelectedItem();
      
      switch(s){
        case "GET":
          trell("GET");
          break;
        case "PUT":
          trell("PUT");
          break;
        case "DELETE":
          trell("DELETE");
          break;
        case "POST":
          trell("POST");
          break;
        default: 
          
          break;
        }
      }
  };
  
  comboBox.addActionListener(action);
  return comboBox;
}

/*
 * Keeps track of response from user
 */
public static class Response implements Runnable {

    private String[] values;
    private String response;

    public Response(String... values) {
        this.values = values;
    }

    @Override
    public void run() {
        response = ask(values);
    }

    public String getResponse() {
        return response;
    }
}

}
