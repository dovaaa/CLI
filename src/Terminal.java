import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;

class Parser {
    int type;
    String commandName;
    String[] args;
    String fileName;
    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user


    public boolean parse(String input) {
        if (input.equals("")) return false;

        args = input.split(" ");
        commandName = args[0];
//        if (args.length > 1)
        args = Arrays.copyOfRange(args, 1, args.length);

        if (args.length <= 1) type = 0;
        else if (args[args.length - 2].equals(">")) type = 1;
        else if (args[args.length - 2].equals(">>")) type = 2;
        else type = 0;

        if (type != 0) argsCleaner();
        return true;
    }

    private void argsCleaner() {
        fileName = args[args.length - 1];
        this.args = Arrays.copyOfRange(args, 0, args.length - 2);
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public int getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
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
        for (int i = 0; i < arg.length; ++i) {
            ret += arg[i] + ' ';
        }
        return ret;
    }

    public String pwd() {
        String ret = file.getPath() + " ";
        String[] arg = parser.getArgs();
        for (int i = 0; i < arg.length; i++) {
            ret += arg[i] + " ";
        }
        return ret;
    }

    public void cd() {
        file = new File(System.getProperty("user.dir"));
    }

    public boolean cd(String arg) {
        if (arg.equals("..")) {
            file = new File(file.getParent());
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
                    if(f.delete()){
                        return ret;
                    }else System.out.println("Not Deleted");
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

    public void cp(String arg1, String arg2) throws IOException { //TODO fix parameters number ie. dont crash in outputExecution()
        File a, b;
        if (new File(arg1).isAbsolute()) {
            a = new File(arg1);
        } else {
            a = new File(file.getPath() + File.separator + arg1);
        }
        if (new File(arg2).isAbsolute()) {
            b = new File(arg2);
        } else {
            b = new File(file.getPath() + File.separator + arg2);
        }

        BufferedReader f = new BufferedReader(new FileReader(a));
        FileOutputStream fos = new FileOutputStream(b, true);
        String Temp = "", str = "";
        while ((Temp = f.readLine()) != null) {
            str += Temp + '\n';
        }
        fos.write(str.getBytes());
        fos.close();


    }

    public void cp_r(File source, File destination) throws IOException {
        if (source.isDirectory()) {

            if (!destination.exists()) {
                destination.mkdir();
            }

            String[] files = source.list();
            if (files == null) {
                return;
            }

            for (String file : files) {
                File sourceFile = new File(source, file);
                File destinationFile = new File(destination, file);

                cp_r(sourceFile, destinationFile);
            }

        } else {

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {

                inputStream = new FileInputStream(source);
                outputStream = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

            } catch (IOException e) {

                System.err.println("IO errors : " + e.getMessage());

            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
    }


    public void rm(String arg) {
        File f;
        if (new File(arg).isAbsolute()) {
            f = new File(arg);
        } else {
            f = new File(file.getPath() + File.separator + arg);
        }
        if (f.isFile()) {
            if (f.delete()) {
                return;
            } else {
                System.out.println("File Not empty");
                return;
            }
        } else {
            System.out.println("Argument Not a File");
            return;
        }
    }

    public String cat(String[] args) throws IOException {
        File f;
        File g;
        if (args.length == 1) {

            if (new File(args[0]).isAbsolute()) {
                f = new File(args[0]);
            } else {
                f = new File(file.getPath() + File.separator + args[0]);
            }
            try {
                BufferedReader z = new BufferedReader(new FileReader(f));

                String Temp = "", str = "";
                while ((Temp = z.readLine()) != null) {
                    str += Temp + '\n';
                }
                return str;
            } catch (FileNotFoundException e) {
                System.out.println("No Such File!");
            }
        } else if (args.length == 2) {

            if (new File(args[0]).isAbsolute()) {
                f = new File(args[0]);
            } else {
                f = new File(file.getPath() + File.separator + args[0]);
            }
            if (new File(args[0]).isAbsolute()) {
                g = new File(args[1]);
            } else {
                g = new File(file.getPath() + File.separator + args[1]);
            }
            BufferedReader z = new BufferedReader(new FileReader(f));
            BufferedReader c = new BufferedReader(new FileReader(g));
            String Temp = "", str = "";
            while ((Temp = z.readLine()) != null) {
                str += Temp + '\n';
            }

            while ((Temp = c.readLine()) != null) {
                str += Temp + '\n';
            }
            return str;
        }
        return "";
    }

    void exit() {
        System.exit(0);
    }


    void outputExecution(String output, int type) throws IOException {
        String[] args = parser.getArgs();

        if (type == 0) {
            System.out.println(output);
        } else if (type == 1) {
            FileWriter fileWriter = new FileWriter(file.getPath() + File.separator + parser.getFileName());
            fileWriter.write(output + '\n');
            fileWriter.close();
        } else {
            FileOutputStream fos = new FileOutputStream(file.getPath() + File.separator + parser.getFileName(), true);
            output += "\n";
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
                outputExecution(echo(args), parser.getType());
                break;
            case "pwd":
                outputExecution(pwd(), parser.getType());
                break;
            case "cd":
                if (args.length == 0) {
                    cd();
                } else {
                    cd(args[0]);
                }
                break;
            case "ls":
                if (args.length == 0) {
                    outputExecution(ls(), parser.getType());
                } else {
                    if (args[0].equals("-r"))
                        outputExecution(ls_r(), parser.getType());
                    else {
                        outputExecution(ls(), parser.getType());
                    }
                }
                break;
            case "mkdir":
                mkdir(args);
                break;
            case "rmdir":
                rmdir(args[0]);
                break;
            case "touch":
                touch(args[0]);
                break;
            case "cp":
                if (!args[0].equals("-r")) {
                    try {
                        cp(args[0], args[1]);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid Parameters Needed: File1 File2");
                    }
                } else {
                    File source = new File(args[1]);
                    File destination = new File(args[2]);
                    try {
                        cp_r(source, destination);
                    } catch (IndexOutOfBoundsException E) {
                        System.out.println("Invalid Parameters Needed: File1 File2");
                    }
                }
                break;
            case "rm":
                rm(args[0]);
                break;
            case "cat":
                outputExecution(cat(args), parser.getType());
                break;
            case "exit":
                exit();
            default:
                System.out.println("No valid Command");

        }
    }

//    public int argsCheck(String[] args) {
//        if (args.length == 1) return 0;
//        if (args[args.length - 2].equals(">")) return 1;
//        else if (args[args.length - 2].equals(">>")) return 2;
//        else return 0;
//    }


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String inp;
        Terminal terminal = new Terminal();

        while (true) {
            System.out.print(">");
            inp = sc.nextLine();
            terminal.parser.parse(inp);
            terminal.chooseCommandAction();
        }
    }
}
