package trello.utils;

import trello.domain.Argument;

public class ArgUtils {

    private ArgUtils(){
    }

    public static Argument arg(String argName, String argValue){
        return new Argument(argName, argValue);
    }
}
