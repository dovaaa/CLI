import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
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
            ret += arg[i] + ' ';
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
            if (new File(arg).isAbsolute()) {
                File f = new File(arg);
                if (f.exists()) {
                    file = f;
                } else {
                    return false;
                }
            } else {
                File f = new File(file.getPath() + File.separator + arg);
                if (f.exists()) {
                    file = f;
                } else {
                    return false;
                }
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
        for (int i = 0; i < arg.length; ++i) {
            if (new File(arg[i]).isAbsolute()) {
                new File(arg[i]).mkdir();
            } else {
                new File(file.getPath() + File.separator + arg[i]).mkdir();
            }
        }
    }

    public String rmdir(String arg) {
        String ret = "";
        if (arg.equals("*")) {
            boolean atleastOneDeletion = false;
            File[] filesList = file.listFiles();
            for (int i = 0; i < filesList.length; ++i) {
                if (filesList[i].length() == 0) {
                    filesList[i].delete();
                    atleastOneDeletion = true;
                }
            }
            if (!atleastOneDeletion) {
                ret = "There isn't empty files.";
            } else {
                ret = "deletion completed";
            }
        } else {
            File f = new File(arg);
            if (!f.isAbsolute()) {
                f = new File(file.getPath() + File.separator + arg);
            }
            if (f.exists()) {
                if (f.length() == 0) {
                    f.delete();
                } else {
                    ret = arg + " is not empty.";
                }
            } else {
                ret = arg + " is not found.";
            }
        }
        return ret;
    }

    public boolean touch(String arg) throws IOException {
        File f;
        if (new File(arg).isAbsolute()) {
            f = new File(arg);
        } else {
            f = new File(file.getPath() + File.separator + arg);
        }
        if (!f.exists()) {
            f.mkdir();
        }
        boolean created = f.createNewFile();
        return created;
    }

    public void cp(String arg1, String arg2) throws IOException {
        File f1 = new File(arg1);
        File f2 = new File(arg2);
        Files.copy(f1.toPath(), f2.toPath());
    }

    void outputExecution(String output, int type) throws IOException {
        String[] args = parser.getArgs();

        if (type == 0) {
            System.out.println(output);
        } else if (type == 1) {
            FileWriter fileWriter = new FileWriter(args[args.length - 1]);
            String[] str = output.split(" ");
            String[] sub = Arrays.copyOfRange(str,0,str.length-2);
            output="";
            for(String in:sub){
                output+=in+" ";
            }
            fileWriter.write(output + '\n');
            fileWriter.close();
        } else {
            FileOutputStream fos = new FileOutputStream(args[args.length - 1], true);
            String[] str = output.split(" ");
            String[] sub = Arrays.copyOfRange(str,0,str.length-2);
            output="";
            for(String in:sub){
                output+=in+" ";
            }
            output+="\n";
            fos.write(output.getBytes());
            fos.close();
        }
    }

    //This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException {
        String command = parser.getCommandName();
        String[] args = parser.getArgs();

        switch (command) {
            case "echo":
                outputExecution(echo(args), argsCheck(args));
                break;
            case "pwd":
                outputExecution(pwd(),argsCheck(args));
                break;
            case "cd":
                if(args.length==0){
                    cd();
                }else{
                    cd(args[1]);
                }
                break;
            case "ls":
                if(args.length==0){
                    outputExecution(ls(),argsCheck(args));
                }else{
                    outputExecution(ls_r(),argsCheck(args));
                }
                break;
            case "mkdir":
                mkdir(args);
                break;
            case "rmdir":
                rmdir(args[1]);
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
        terminal.parser.parse("echo Hello World >> echo.txt");
        terminal.chooseCommandAction();
    }
}
