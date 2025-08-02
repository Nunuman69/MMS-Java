// MMS Simulation with Optional Improvements
import java.util.Scanner;

public class MMS {
    private class ListNode {
        String PgmName;
        int address;
        int size;
        ListNode next;
    }

    int MMSsize;
    ListNode allocList, freeList;

    public MMS(int size) {
        MMSsize = size;
        allocList = null;
        freeList = new ListNode();
        freeList.address = 0;
        freeList.size = size;
        freeList.next = null;
    }

    public void alloc(String name, int size) {
        ListNode ptr = freeList, candidate = null, newNode;

        // Find the smallest suitable free block
        while (ptr != null) {
            if (ptr.size >= size && ptr.size > 0) {
                if (candidate == null || ptr.size < candidate.size) {
                    candidate = ptr;
                }
            }
            ptr = ptr.next;
        }

        // If no suitable free block found, do garbage collection
        if (candidate == null) {
            System.out.println("[GC] Garbage collection triggered to defragment memory.");
            freeList = GarbageCollection(allocList);
            if (freeList.size >= size) {
                candidate = freeList;
            }
        }

        // If no free block found, abandon attempt
        if (candidate == null || candidate.size < size) {
            System.out.println("\n==========================================");
            System.out.println("error: alloc: insufficient space");
            System.out.println("       alloc(" + name + "," + size + ") abandoned");
            System.out.println("==========================================\n");
            return;
        }

        // Create new node and insert into allocList
        newNode = new ListNode();
        newNode.PgmName = name;
        newNode.address = candidate.address;
        newNode.size = size;
        newNode.next = null;

        candidate.address += size;
        candidate.size -= size;

        allocList = insert(newNode, allocList);
    }

    public void free(String name) {
        while (allocList != null && allocList.PgmName.equals(name)) {
            ListNode toDelete = allocList;
            allocList = allocList.next;
            toDelete.next = freeList;
            freeList = toDelete;
        }

        if (allocList == null) return;

        ListNode prev = allocList;
        ListNode current = allocList.next;

        while (current != null) {
            if (current.PgmName.equals(name)) {
                prev.next = current.next;
                current.next = freeList;
                freeList = current;
                current = prev.next;
            } else {
                prev = current;
                current = current.next;
            }
        }
    }

    public void printalloc() {
        System.out.println("List of allocated blocks\n");
        System.out.println("Process Name   Start Address   Size");

        int count = 0;
        ListNode ptr = allocList;
        while (ptr != null) {
            System.out.format("%-15s%-16d%-10d\n", ptr.PgmName, ptr.address, ptr.size);
            ptr = ptr.next;
            count++;
        }
        System.out.println("\n" + count + " block(s) in the allocated list\n");
        System.out.println("-----------------------------------\n");
    }

    public void printfree() {
        System.out.println("List of free blocks\n");
        System.out.println("Start address   Size");

        int count = 0;
        ListNode ptr = freeList;
        while (ptr != null) {
            if (ptr.size > 0) {
                System.out.format("%-16d%-10d\n", ptr.address, ptr.size);
                count++;
            }
            ptr = ptr.next;
        }
        System.out.println("\n" + count + " block(s) in the free list\n");
        System.out.println("-----------------------------------\n");
    }

    public int getTotalFreeMemory() {
        int total = 0;
        ListNode ptr = freeList;
        while (ptr != null) {
            total += ptr.size;
            ptr = ptr.next;
        }
        return total;
    }

    private ListNode GarbageCollection(ListNode allocList) {
        ListNode ptr = allocList;
        int currentAddress = 0;

        while (ptr != null) {
            ptr.address = currentAddress;
            currentAddress += ptr.size;
            ptr = ptr.next;
        }

        ListNode newFree = new ListNode();
        newFree.address = currentAddress;
        newFree.size = MMSsize - currentAddress;
        newFree.next = null;

        return newFree;
    }

    private ListNode insert(ListNode node, ListNode allocList) {
        if (allocList == null || node.address < allocList.address) {
            node.next = allocList;
            return node;
        }

        ListNode current = allocList;
        while (current.next != null && current.next.address < node.address) {
            current = current.next;
        }
        node.next = current.next;
        current.next = node;

        return allocList;
    }

    // main method removed for separation
    // Use MMSTest.java to run this class

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
