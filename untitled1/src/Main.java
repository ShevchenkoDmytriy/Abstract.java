import java.util.ArrayList;
import java.util.List;

// Абстрактний клас Банковський Счет
abstract class BankAccount {
    protected int accountNumber;
    protected double balance;
    protected String owner;

    public BankAccount(int accountNumber, double balance, String owner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    // Абстрактні методи
    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public abstract void calculateBalance();
    public double getBalance() {
        return balance;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
}

// Клас Текущий Счет
class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(int accountNumber, double balance, String owner, double overdraftLimit) {
        super(accountNumber, balance, owner);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Депозит на " + amount + " грн. на рахунок " + accountNumber);
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= -overdraftLimit) {
            balance -= amount;
            System.out.println("Знято " + amount + " грн. з рахунку " + accountNumber);
        } else {
            System.out.println("Недостатньо коштів для зняття");
        }
    }

    @Override
    public void calculateBalance() {
        System.out.println("Баланс рахунку " + accountNumber + ": " + balance + " грн.");
    }
}

// Клас Сберегательный Счет
class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(int accountNumber, double balance, String owner, double interestRate) {
        super(accountNumber, balance, owner);
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Депозит на " + amount + " грн. на рахунок " + accountNumber);
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
            System.out.println("Знято " + amount + " грн. з рахунку " + accountNumber);
        } else {
            System.out.println("Недостатньо коштів для зняття");
        }
    }

    @Override
    public void calculateBalance() {
        System.out.println("Баланс рахунку " + accountNumber + ": " + balance + " грн.");
    }

    // Додатковий метод для нарахування відсотків
    public void accrueInterest() {
        balance += balance * interestRate;
        System.out.println("Нараховано відсотки на рахунок " + accountNumber);
    }
}

// Інтерфейс Інтернет-Банкинг
interface InternetBanking {
    void transferMoney(double amount, BankAccount toAccount);

    void viewTransactions();
}

// Інтерфейс SMS-Уведомления
interface SMSNotifications {
    void sendNotification(String message);
}

// Клас Кліент Банку
class BankClient implements InternetBanking, SMSNotifications {
    private String name;
    private List<BankAccount> accounts;

    public BankClient(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    @Override
    public void transferMoney(double amount, BankAccount toAccount) {
        if (this.accounts.get(0).getBalance() >= amount) {
            this.accounts.get(0).withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Переказано " + amount + " грн. з вашого рахунку на рахунок " + toAccount.getAccountNumber());
        } else {
            System.out.println("Недостатньо коштів для переказу");
        }
    }

    @Override
    public void viewTransactions() {
        // Реалізація перегляду транзакцій
        System.out.println("Перегляд транзакцій...");
        // Логіка перегляду транзакцій
    }

    @Override
    public void sendNotification(String message) {
        // Реалізація відправлення SMS-повідомлень
        System.out.println("Відправка SMS: " + message);
        // Логіка відправлення SMS-повідомлення
    }
}

public class Main {
    public static void main(String[] args) {
        // Приклад використання
        CurrentAccount currentAccount = new CurrentAccount(1, 1000, "John", 500);
        SavingsAccount savingsAccount = new SavingsAccount(2, 2000, "Alice", 0.05);

        BankClient client = new BankClient("Client1");
        client.addAccount(currentAccount);
        client.addAccount(savingsAccount);

        currentAccount.deposit(200);
        currentAccount.withdraw(150);
        currentAccount.calculateBalance();

        savingsAccount.deposit(500);
        savingsAccount.accrueInterest();
        savingsAccount.calculateBalance();

        // Приклад використання методів клієнта для переказу грошей, перегляду транзакцій і відправлення SMS-повідомлень
        BankAccount anotherAccount = new SavingsAccount(3, 3000, "Bob", 0.03);
        client.transferMoney(100, anotherAccount);
        client.viewTransactions();
        client.sendNotification("Успішний переказ 100 грн.");

        // Тут можна додати інші приклади використання методів класів
    }
}
