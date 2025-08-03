import java.util.Scanner;

public class MMSTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);    // Scanner to read user input
        MMS mms = new MMS(100);                 // Initialize memory manager with 100 bytes
        String command;

        // Display user instructions
        System.out.println("MMS Console — enter commands like:");
        System.out.println("alloc pgmA 20 | free pgmA | printalloc | printfree | exit");

        // Command processing loop
        while (true) {
            System.out.print("> ");
            command = sc.nextLine();

            // Split input into components (e.g., ["alloc", "pgmA", "20"])
            String[] parts = command.trim().split("\\s+");

            // Handle 'alloc' command
            if (parts[0].equalsIgnoreCase("alloc") && parts.length == 3) {
                try {
                    int size = Integer.parseInt(parts[2]);
                    mms.alloc(parts[1], size);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid size: must be an integer.");
                }

            // Handle 'free' command
            } else if (parts[0].equalsIgnoreCase("free") && parts.length == 2) {
                mms.free(parts[1]);

            // Print allocated blocks
            } else if (parts[0].equalsIgnoreCase("printalloc")) {
                mms.printalloc();

            // Print free memory blocks
            } else if (parts[0].equalsIgnoreCase("printfree")) {
                mms.printfree();

            // Exit the program
            } else if (parts[0].equalsIgnoreCase("exit")) {
                break;

            // Handle unknown or malformed commands
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }

        sc.close(); // Close scanner to free resources
    }
}
