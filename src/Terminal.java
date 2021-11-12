import java.io.File;
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
        for (int i = 1; i < list.length; i++) {
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
        file = new File(System.getProperty("user.dir"));
    }

    public String echo(String arg) {
        return arg;
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
            ret+= stringsList[i] + '\n';
        }
        return ret;
    }

    public void mkdir(String[] arg) {
        for (int i = 0; i < arg.length; ++i) {
            if (new File(arg[i]).isAbsolute()) {
                new File(arg[i]).mkdir();
            } else {
                new File(file.getPath() + File.separator + arg[i]).mkdir();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
