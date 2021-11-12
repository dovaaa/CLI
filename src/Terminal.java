<<<<<<< HEAD
=======

import java.util.ArrayList;

class Parser{

    String commandName;
    String[] args;

    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user


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

}

public class Terminal {
    Parser parser;

    //Implement each command in a method, for example:

   /* public String pwd(){

    }
    public void cd(String[] args){

    }


    public String pwd() {

    }
    public void cd(String[] args) {

    }




    //This method will choose the suitable command method to be called
    public void chooseCommandAction(){

    }


    public static void main(String[] args){}

*/
      Terminal () {
      }


    public static void main(String[] args){

    }

    public static void main(String[] args){}

}
>>>>>>> 1f0bd9e84dab00a4eb5aa997d497f31a340ec297
