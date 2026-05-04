import java.util.*;
class User {
    String name;
    String address;
    String phone;
    String password;
    Account account;

    public User(String name, String address, String phone, String password, double initialDeposit, int accNo) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.account = new Account(accNo, initialDeposit);
    }
}


class Account {
    int accountNumber;
    double balance;
    List<String> transactions = new ArrayList<>();

    public Account(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited: " + amount + " | Balance: " + balance);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("❌ Insufficient Balance!");
            return false;
        }
        balance -= amount;
        transactions.add("Withdrawn: " + amount + " | Balance: " + balance);
        return true;
    }
}



public class BankSystem {
    static Map<Integer, User> users = new HashMap<>();
    static int accCounter = 1001;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> System.exit(0);
            }
        }
    }

    static void register() {
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Set Password: ");
        String password = sc.nextLine();

        System.out.print("Initial Deposit: ");
        double deposit = sc.nextDouble();

        int accNo = accCounter++;
        User user = new User(name, address, phone, password, deposit, accNo);

        users.put(accNo, user);

        System.out.println("✅ Registered Successfully! Account No: " + accNo);
    }

    static void login() {
        System.out.print("Enter Account No: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User user = users.get(accNo);

        if (user != null && user.password.equals(password)) {
            System.out.println("✅ Login Successful!");
            userMenu(user);
        } else {
            System.out.println("❌ Invalid Credentials!");
        }
    }

    static void userMenu(User user) {
        while (true) {
            System.out.println("\n1. Deposit\n2. Withdraw\n3. Transfer\n4. Statement\n5. Logout");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> deposit(user);
                case 2 -> withdraw(user);
                case 3 -> transfer(user);
                case 4 -> showStatement(user);
                case 5 -> { return; }
            }
        }
    }

    static void deposit(User user) {
        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();
        user.account.deposit(amt);
        System.out.println("✅ Deposited Successfully!");
    }

    static void withdraw(User user) {
        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();
        user.account.withdraw(amt);
    }

    static void transfer(User sender) {
        System.out.print("Enter Receiver Account No: ");
        int accNo = sc.nextInt();

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        User receiver = users.get(accNo);

        if (receiver != null && sender.account.withdraw(amt)) {
            receiver.account.deposit(amt);
            System.out.println("✅ Transfer Successful!");
        } else {
            System.out.println("❌ Transfer Failed!");
        }
    }

    static void showStatement(User user) {
        System.out.println("\n--- Transaction History ---");
        for (String t : user.account.transactions) {
            System.out.println(t);
        }
    }
}