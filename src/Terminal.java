import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Parser {

    String commandName;
    String[] args;

    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user


    public boolean parse(String input) {
        if (input.equals("")) return false;
        String[] list = input.split(" ");
        commandName = list[0];
        args = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            args[i] = list[i];
        }
        return true;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }

}

public class Terminal {
    Parser parser;

    File file;

    public Terminal() {
        parser = new Parser();
        file = new File(System.getProperty("user.dir"));
    }

    public String echo(String[] arg) {
        String ret = "";
        for (int i = 1; i < arg.length; ++i) {
            ret+= arg[i] + ' ';
        }
        return ret;
    }

    public String pwd() {
        return file.getPath();
    }

    public void cd() {
        file = new File(System.getProperty("user.dir"));
    }

    public boolean cd(String arg) {
        if (arg.equals("..")) {
            file = new File(file.getPath() + File.separator + ".." + File.separator);
        } else {
            if (new File(arg).exists()) {
                file = new File(arg);
            } else if (new File(file.getPath() + File.separator + arg).exists()) {
                file = new File(file.getPath() + File.separator + arg);
            } else {
                return false;
            }
        }
        return true;
    }

    public String ls() {
        String[] stringsList = file.list();
        String ret = "";
        for (int i = 0; i < stringsList.length; ++i) {
            ret += stringsList[i] + '\n';
        }
        return ret;
    }

    public String ls_r() {
        String[] stringsList = file.list();
        String ret = "";
        for (int i = stringsList.length - 1; i >= 0; --i) {
            ret += stringsList[i] + '\n';
        }
        return ret;
    }

    public void mkdir(String[] arg) {

    }

    //This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException {
        String command = parser.getCommandName();
        String[] args = parser.getArgs();

        switch (command) {
            case "echo":

                if (argsCheck(args) == 1) {
                    FileWriter W = new FileWriter(args[args.length - 1]);
                    W.write(echo(args));
                    W.flush();
                    W.close();
                    break;
                } else if (argsCheck(args) == 2) {

                }
                System.out.println(echo(args));
                break;
            case "pwd":
                if (argsCheck(args) == 1) {
                    FileWriter W = new FileWriter(args[args.length - 1]);
                    W.write(pwd());
                    W.flush();
                    W.close();
                    break;
                } else if (argsCheck(args) == 2) {

                }
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

    public int argsCheck(String[] args) {
        if (args[args.length - 2].equals(">")) return 1;
        else if (args[args.length - 2].equals(">>")) return 2;
        else return 0;
    }


    public static void main(String[] args) throws IOException {
        Terminal terminal = new Terminal();
        terminal.parser.parse("echo Hello World");
        terminal.chooseCommandAction();
    }
}
