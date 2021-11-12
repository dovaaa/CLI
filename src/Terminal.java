<<<<<<< HEAD
class Parser {
=======
import java.util.ArrayList;

class Parser{
>>>>>>> 1481391b4b522823a52a85669020656f1b11a092
    String commandName;
    String[] args;

    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user
<<<<<<< HEAD
   /* public boolean parse(String input){}

    public String getCommandName(){

    }

    public String[] getArgs(){

    }
*/
=======
    public boolean parse(String input){
        if(input.equals(""))return false;
        String[] list = input.split(" ");
        commandName=list[0];
        for (int i = 1; i < list.length; i++) {
            args[i]=list[i];
        }
        return true;
    }

    public String getCommandName(){
        return commandName;
    }

    public String[] getArgs(){return args;}
>>>>>>> 1481391b4b522823a52a85669020656f1b11a092
}

public class Terminal {
    Parser parser;

    //Implement each command in a method, for example:
<<<<<<< HEAD
   /* public String pwd(){

    }
    public void cd(String[] args){

    }

=======
    public String pwd() {

    }
    public void cd(String[] args) {

    }
>>>>>>> 1481391b4b522823a52a85669020656f1b11a092



    //This method will choose the suitable command method to be called
    public void chooseCommandAction(){

    }
<<<<<<< HEAD
*/
      Terminal () {
      }


    public static void main(String[] args){

    }
=======

    public static void main(String[] args){}
>>>>>>> 1481391b4b522823a52a85669020656f1b11a092
}
