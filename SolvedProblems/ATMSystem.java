import java.util.*;

public class ATMSystem {
    private final int[] denominations = {2000, 500, 100, 50, 10};
    private Map<Integer, Integer> inventory;

    public ATMSystem(Map<Integer, Integer> initialInventory) {
        this.inventory = new HashMap<>(initialInventory);
    }

    public Map<Integer, Integer> withdraw(int amount){
        Map<Integer, Integer> result = new LinkedHashMap<>();
        int remaining = amount;

        for(int denom : denominations){
            if(remaining <= 0) break;

            int availableNotes = inventory.getOrDefault(denom, 0);
            int requiredNotes = remaining / denom;

            if(requiredNotes > 0 && availableNotes > 0){
                int usedNotes = Math.min(requiredNotes, availableNotes);
                result.put(denom, usedNotes);
                remaining = remaining - (usedNotes * denom);
            }
        }

        //update inventory
        if(remaining == 0) {
            for (Map.Entry<Integer, Integer> ent : result.entrySet()) {
                int denom = ent.getKey();
                int used = ent.getValue();
                inventory.put(denom, inventory.get(denom) - used);
            }
            return result;
        } else {
            int pamt =  findPossiblemaount(amount);
            System.out.println("Cannot dispense " + amount + ". Closest possible: " + pamt);
            return null;
        }
    }

    private int findPossiblemaount(int amount) {
        int maxPossible = 0;
        boolean[] dp = new boolean[amount + 1];
        dp[0] = true;

        for (int denom : denominations) {
            int count = inventory.getOrDefault(denom, 0);
            for (int i = 0; i < count; i++) {
                for (int j = amount; j >= denom; j--) {
                    if (dp[j - denom]) {
                        dp[j] = true;
                        maxPossible = Math.max(maxPossible, j);
                    }
                }
            }
        }
        return maxPossible;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] denominations = {2000, 500, 100, 50, 20, 10};
        Map<Integer, Integer> inv = new HashMap<>();

        System.out.println("Enter ATM Inventory (number of notes for each denomination):");
        for (int denom : denominations) {
            System.out.print(denom + ": ");
            inv.put(denom, sc.nextInt());
        }

        ATMSystem atm = new ATMSystem(inv);

        System.out.print("Enter amount:");
        int amount = sc.nextInt();

        Map<Integer, Integer> result = atm.withdraw(amount);
    
        if (result != null) {
            System.out.println("Dispensed Notes: " + result.toString());
        }

        System.out.println("Available : " + atm.inventory);
        sc.close();
    }
}
