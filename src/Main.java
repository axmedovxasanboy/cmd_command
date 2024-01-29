import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static StringBuilder currentPath = new StringBuilder("C:/");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome! ");

        String cmd = "";
        while (!cmd.equals("quit") && !cmd.equals("exit")) {
            System.out.print(currentPath.toString() + " : ");
            cmd = sc.nextLine();
            if (cmd.equals("dir")) allDirectories();
            if (cmd.startsWith("cd ")) cdCommand(cmd);
            if (cmd.startsWith("mkdir ")) createFolder(cmd);
            if (cmd.startsWith("delete ")) delete(cmd);
            if (cmd.startsWith("create file ")) createFile(cmd);
            if (cmd.startsWith("copy ")) copy(cmd.split(" ")[1], cmd.split(" ")[2]);
            if (cmd.startsWith("rename ")) renameFile(cmd, cmd.split(" ")[1], cmd.split(" ")[2]);
        }
        System.out.println("Good bye!");
    }

    private static void renameFile(String cmd, String oldFileName, String newFileName) {
        createFile(cmd);
        copy(currentPath + oldFileName, currentPath + newFileName);
        delete(cmd);
    }


    private static void delete(String cmd) {
        String name = cmd.split(" ")[1];
        File fileName = new File("" + currentPath);
        for (int i = 0; i < Objects.requireNonNull(fileName.list()).length; i++) {
            File f1 = Objects.requireNonNull(fileName.listFiles())[i];
            if (f1.getName().equals(name)) {
                if (f1.delete()) System.out.println("Success");
                else System.out.println("Error occurred");
            }
        }
    }

    private static void createFile(String cmd) {
        String fileName = cmd.split(" ")[2];
        if (fileSpellingChecker(fileName)) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(currentPath + fileName);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    Objects.requireNonNull(out).close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void createFolder(String cmd) {
        String subFolder = cmd.split(" ")[1];
        if (fileSpellingChecker(subFolder)) {
            File out = new File(currentPath + subFolder);
            boolean mkdir = out.mkdir();
            if (mkdir) System.out.println("Folder created");
            else System.out.println("Error occurred");
        }
    }

    private static void cdCommand(String cmd) {
        String subFolder = cmd.split(" ")[1];

        if (subFolder.equals("../")) {
            int last = currentPath.lastIndexOf("/");

            currentPath = new StringBuilder(currentPath.substring(0, last));
        } else {
            gotoFolder(subFolder);
        }
    }

    private static void allDirectories() {
        File file = new File(currentPath.toString());
        String[] files = file.list();

        for (String s : Objects.requireNonNull(files)) {
            System.out.println(s);
        }
    }

    private static boolean fileSpellingChecker(String fileName) {
        for (int i = 0; i < fileName.length(); i++) {
            if (fileName.charAt(i) == '\\' || fileName.charAt(i) == '/' || fileName.charAt(i) == ':'
                    || fileName.charAt(i) == '*' || fileName.charAt(i) == '?' || fileName.charAt(i) == '"'
                    || fileName.charAt(i) == '<' || fileName.charAt(i) == '>' || fileName.charAt(i) == '|') {
                System.out.println("A file name can't contain following characters");
                System.out.println("\\ / : * ? \" < > |");
                return false;
            }
        }
        return true;
    }

    private static void gotoFolder(String subFolder) {
        File file = new File(currentPath + "/" + subFolder);


        if (!file.isDirectory()) {
            System.out.println("Error");
        } else {
            currentPath.append("/").append(subFolder);
        }
    }

    public static void copy(String source, String destination) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(destination);
            int a;
            while ((a = in.read()) != -1) {
                out.write((char) a);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                Objects.requireNonNull(out).close();
                in.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
