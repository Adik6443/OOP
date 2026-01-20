import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class Assignment3 {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);


        try (Connection c = DBConnection.getConnection()) {
            System.out.println("PSQL Connected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection c = DBConnection.getConnection()) {
            String insertAcc = "INSERT INTO account(balance) VALUES (1000) RETURNING id";
            PreparedStatement psAcc = c.prepareStatement(insertAcc);
            ResultSet rsAcc = psAcc.executeQuery();
            rsAcc.next();
            int accId = rsAcc.getInt("id");


            String insertCust = "INSERT INTO customer(name, email, account_id) VALUES (?, ?, ?)";
            PreparedStatement psCust = c.prepareStatement(insertCust);
            psCust.setString(1, "Adilkhan");
            psCust.setString(2, "adik@mail.com");
            psCust.setInt(3, accId);
            psCust.executeUpdate();


            String read = "SELECT c.name, c.email, a.balance " + "FROM customer c JOIN account a ON c.account_id = a.id";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(read);

            while (rs.next()) {
                System.out.println(
                        rs.getString("name") + " | " + rs.getString("email") + " | " + rs.getDouble("balance")
                );
            }
        }

        Account a1 = new Account(1, 600000);
        Account a2 = new Account(2, 150000);
        Account a3 = new Account(3, 350000);

        Customer c1 = new Customer("Adik", "a@mail.com", a1);
        Customer c2 = new Customer("Danik", "d@mail.com", a2);
        Customer c3 = new Customer("Birzhik", "i@mail.com", a3);

        Bank bank = new Bank("Kaspi");
        bank.addCustomer(c1);
        bank.addCustomer(c2);
        bank.addCustomer(c3);

        System.out.println("Filter Balance >= 300,000");
        for (Customer c : bank.filterByMinBalance(300000)) {
            System.out.println(c);
        }

        System.out.println("Searching by Eamil");
        Customer found = bank.findByEmail("d@mail.com");
        System.out.println(found);

        System.out.println("Sorting Balance by Desc");
        bank.sortByBalanceDesc();
        System.out.println(bank.getCustomers());

        }


    }

    interface Transactional {
        void deposit(double amount);
        void withdraw(double amount);
    }
    class Account implements Transactional {
        private int id;
        private double balance;

        public Account(int id, double balance) {
            this.id = id;
            if (balance >= 0) {
                this.balance = balance;
            }
        }

        public String toString(){
            return "Account{id=" + id + " balance =" + balance + " }" ;
        }

        public int getId() {
            return id;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0){
                balance += amount;
            }
        }

        public void withdraw(double amount) {
            if (balance >= amount && amount > 0){
                balance -= amount;

            }
        }

    }

    abstract class Person {
            protected String name;
            protected  String email;

            protected Person(String name, String email){
                this.name = name;
                this.email = email;
            }

            String getName(){
                return name;
            }

            String getEmail(){
                return email;
            }

            public String getRole(){
                return "Person";
            }
}


    class Customer extends Person {
        private Account account;

        public String toString(){
            return "Customer{ " + name + ", email=" + email + ", account=" + account + " }" ;
        }

        public Customer(String name, String email, Account account){
            super(name, email);
            this.account = account;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (null == o || getClass() != o.getClass()) {
                return false;
            }

            Customer customer = (Customer) o;
            return email.equals(customer.email);
        }

        public int hashCode() {
            return email.hashCode();
        }

        public Account getAccount(){
            return account;
        }

        public String getRole(){
            return "Customer";
        }
    }

    class Bank {
        private String bankname;
        private List<Customer> customers = new ArrayList<>();

        public Bank(String bankname){
            this.bankname = bankname;
        }

        public void addCustomer(Customer customer){
            if (customer != null){
                customers.add(customer);
            }
        }

        public List<Customer> getCustomers(){
            return List.copyOf(customers);
        }

        public List<Customer> filterByMinBalance(double minBalance) {
            List<Customer> result = new ArrayList<>();
            for (Customer c : customers) {
                if (c.getAccount().getBalance() >= minBalance) {
                    result.add(c);
                }
            }
            return result;
        }

        public Customer findByEmail(String email) {
            for (Customer c : customers) {
                if (c.getEmail().equalsIgnoreCase(email)) {
                    return c;
                }
            }
            return null;
        }

        public void sortByBalanceDesc() {
            customers.sort(new Comparator<Customer>() {
                public int compare(Customer c1, Customer c2) {
                    return Double.compare(
                            c2.getAccount().getBalance(),
                            c1.getAccount().getBalance()
                    );
                }
            });
        }


    }
