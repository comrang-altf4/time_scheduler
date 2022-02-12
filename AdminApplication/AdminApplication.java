import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.util.Pair;

public class AdminApplication {
    static Scanner myInput = new Scanner(System.in);

    public interface CommandLine {
        void execute();
    }

    public static class User {
        public String username;
        public String email;
        public String password = "";

        User() {
            username = "aaa";
            email = "bbb";
        }

        User(String x, String y, String z) {
            username = x;
            email = y;
            password = z;
        }
    }

    static List<Pair<String, String>> a = new ArrayList<>();
    static List<String> deleteList = new ArrayList<>();

    static void importDB() throws SQLException {
        a = Database.getUsers();
    }

    static void list() {

        int c = 0;
        for (Pair<String, String> x : a) {
            c++;
            System.out.println(String.format("%d %s %s", c, x.getKey(), x.getValue()));
        }
    }

    static void delete() {
        while (true) {
            System.out.println("Enter username (-1 to exit)");
            String username = myInput.nextLine();
            if (username.equals("-1"))
                return;
            Boolean flag = false;
            for (Pair<String, String> user : a)
                if (user.getKey().equals(username)) {
                    deleteList.add(username);
                    a.remove(user);
                    flag = true;
                    break;
                }
            if (!flag)
                System.out.println("Username not exist");
        }
    }

    static void edit() {
        while (true) {
            String username, email, password;
            System.out.println("Enter username: (-1 to exit)");
            username = myInput.nextLine();
            if (username.equals("-1"))
                return;
            boolean flag = false;
            for (int i = 0; i < a.size(); i++)
                if (a.get(i).getKey().equals(username)) {
                    System.out.println("Enter new email: ");
                    email = myInput.nextLine();
                    a.set(i, new Pair<>(a.get(i).getKey(), email));
                    flag = true;
                    System.out.println(String.format("%s email changed", username));
                    break;
                }
            if (!flag)
                System.out.println("User not exist");
        }
    }

    static CommandLine[] cmds = new CommandLine[] {
            new CommandLine() {
                public void execute() {
                    list();
                }
            },
            new CommandLine() {
                public void execute() {
                    delete();
                }
            },
            new CommandLine() {
                public void execute() {
                    edit();
                }
            }
    };
    static String[] commandString = { "list", "delete", "edit" };

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new Database();
        importDB();
        while (true) {
            System.out.print("@admin:-$ ");
            String command = myInput.nextLine();
            if (command.equals("exit"))
                break;
            for (int i = 0; i < commandString.length; i++) {
                if (commandString[i].equals(command)) {
                    cmds[i].execute();
                }
            }
        }
        for (Pair<String, String> user : a)
            Database.updateUser(user.getKey(), user.getValue());
        for (String deletedUser : deleteList)
            Database.deleteUser(deletedUser);
    }

}
