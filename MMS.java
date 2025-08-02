//MMS Simulation
public class MMS{
        private class ListNode {
        String PgmName; 
        int address;    
        int size;   
        ListNode next;  
    }

    int MMSsize; // Total memory size
    ListNode allocList, freeList;

    // Constructor to initialize memory
    public MMS(int size) {
        MMSsize = size;
        allocList = null;
        freeList = new ListNode();
        freeList.address = 0;
        freeList.size = size;
        freeList.next = null;
    }


    // alloc method to allocate memory block to a process
    public void alloc(String name, int size) {
        ListNode ptr = freeList, candidate = null, newNode;

        // Find the smallest suitable free block(Linear Search and smallest fit strategy)
        while (ptr != null) {
            if (ptr.size >= size) {
                if (candidate == null || ptr.size < candidate.size) {
                    candidate = ptr;
                }
            }
            ptr = ptr.next;
        }

        // If no suitable free block found, do garbage collection
        if (candidate == null) {
            freeList = GarbageCollection(allocList);
            if (freeList.size >= size) {
                candidate = freeList;
            }
        }

        // If no free block found, abandon attempt to allocate space
        if (candidate == null || candidate.size < size) {
            System.out.println("\n==========================================");
            System.out.println("error: alloc: insufficient space");
            System.out.println("       alloc(" + name + "," + size + ") abandoned");
            System.out.println("==========================================\n");
            return;
        }

        // Create a new node(to be inserted into allocList)
        newNode = new ListNode();
        newNode.PgmName = name;
        newNode.address = candidate.address;
        newNode.size = size;
        newNode.next = null;

        // Update found free block (candidate)
        candidate.address += size;
        candidate.size -= size;

        // Insert newNode into allocList, maintaining order by start address
        allocList = insert(newNode, allocList);
    }

    // free method to free memory blocks belonging to a process
    public void free(String name) {
        // Remove matching nodes at the front of allocList
        while (allocList != null && allocList.PgmName.equals(name)) {
            ListNode toDelete = allocList;
            allocList = allocList.next;
            toDelete.next = freeList;
            freeList = toDelete;
        }

        // If allocList is empty, return
        if (allocList == null) return;

        // Remove matching nodes in the middle or end
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

    // Print allocated memory blocks
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

    // Print free memory blocks
    public void printfree() {
        System.out.println("List of free blocks\n");
        System.out.println("Start address   Size");

        int count = 0;
        ListNode ptr = freeList;
        while (ptr != null) {
            System.out.format("%-16d%-10d\n", ptr.address, ptr.size);
            ptr = ptr.next;
            count++;
        }
        System.out.println("\n" + count + " block(s) in the free list\n");
        System.out.println("-----------------------------------\n");
    }



    // Consolidate all allocated blocks and return single free block
    private ListNode GarbageCollection(ListNode allocList) {
        ListNode ptr = allocList;
        int currentAddress = 0;

        // Update the starting addresses for all nodes in alloclist
        while (ptr != null) {
            ptr.address = currentAddress;
            currentAddress += ptr.size;
            ptr = ptr.next;
        }

        // Create freeList with one node for consolidated free space
        ListNode newFree = new ListNode();
        newFree.address = currentAddress;
        newFree.size = MMSsize - currentAddress;
        newFree.next = null;

        return newFree;
    }
    // Insert a node into the allocList in order of address
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

public static void main(String[] args) {
    MMS myMem = new MMS(100);

    myMem.printalloc();
    myMem.printfree();

    myMem.alloc("pgmA",50);
    myMem.printalloc();
    myMem.printfree();

    myMem.alloc("pgmB",20);
    myMem.printalloc();
    myMem.printfree();

    myMem.alloc("pgmA",10);
    myMem.printalloc();
    myMem.printfree();

    myMem.free("pgmB");
    myMem.printalloc();
    myMem.printfree();

    myMem.alloc("pgmD",25);
    myMem.printalloc();
    myMem.printfree();
}
}