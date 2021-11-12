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
    public String pwd() {

    }
    public void cd(String[] args) {

    }



    //This method will choose the suitable command method to be called
    public void chooseCommandAction(){
        String command=parser.getCommandName();
        String[] args=parser.getArgs();

        switch (command){
            case "echo":
            //    echo(args[0]);
                break;
            case "pwd":
                pwd();
                break;
            case "ls":
                break;
            case "mkdir":
                break;
            case "rmdir":
                break;
            case "touch":
                break;
            case "cp":
                break;
            case "rm":
                break;
            case "cat":
                break;
            default:
                System.out.println("No valid Command");

        }

    }
    public void argsCheck(String[] args) {
    }
    public static void main(String[] args){}
}
