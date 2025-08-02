import java.util.Scanner;

public class MMSTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MMS mms = new MMS(100);
        String command;

        System.out.println("MMS Console — enter commands like:");
        System.out.println("alloc pgmA 20 | free pgmA | printalloc | printfree | exit");

        while (true) {
            System.out.print("> ");
            command = sc.nextLine();
            String[] parts = command.trim().split("\s+");

            if (parts[0].equalsIgnoreCase("alloc") && parts.length == 3) {
                mms.alloc(parts[1], Integer.parseInt(parts[2]));
            } else if (parts[0].equalsIgnoreCase("free") && parts.length == 2) {
                mms.free(parts[1]);
            } else if (parts[0].equalsIgnoreCase("printalloc")) {
                mms.printalloc();
            } else if (parts[0].equalsIgnoreCase("printfree")) {
                mms.printfree();
            } else if (parts[0].equalsIgnoreCase("exit")) {
                break;
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }

        sc.close();
    }
}
