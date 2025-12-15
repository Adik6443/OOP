public class Assignment1 {
    public static void main(String[] args) {
        Account acc1 = new Account(1, 600000);
        Account acc2 = new Account(2, 150000);

        Customer cust1 = new Customer("Adilkhan", "adik@example.com", acc1 );
        Customer cust2 = new Customer("Daniyar", "Dkaipsie@example.com", acc2);

        Bank bank1 = new Bank("KaspiBank", cust1);
        Bank bank2 = new Bank("HalykBank", cust2);

        System.out.println(cust1.GetName() + " has " + acc1.Getbalance() + " in account " + acc1.Getid());
        System.out.println(cust2.GetName() + " has " + acc2.Getbalance() + " in account " + acc2.Getid());

        if (acc1.Getbalance() > acc2.Getbalance()) {
            System.out.println(acc1.Getid() + " has more balance.");
        } else {
            System.out.println(acc2.Getid() + " has more balance.");
        }

    }
}

    class Account {
        private int id;
        private double balance;

        public Account(int id, double balance){
            this.id = id;
            this.balance = balance;
        }
        public int Getid(){
            return id;
        }
        public void Setid(int id){
            this.id = id;
        }

        public double Getbalance(){
            return balance;
        }
        public void Setbalance(double balance){
            this.balance = balance;
        }
    }

    class Customer {
        private String name;
        private String email;
        private Account account;

        public Customer(String name, String email, Account account){
            this.name = name;
            this.email = email;
            this.account = account;
        }

        public String GetName(){
            return name;
        }

        public void Setname(String name){
            this.name = name;
        }

        public String Getemail(){
            return email;
        }

        public void Setemail(String email){
            this.email = email;
        }

        public Account GetAccount(){
            return account;
        }

        public void SetAccount(Account account){
            this.account = account;
        }
    }

    class Bank {
        private String bankname;
        private Customer customer;

        public Bank(String bankname, Customer customer){
            this.bankname = bankname;
            this.customer = customer;
        }

        public String GetBankname(){
            return bankname;
        }

        public void SetBankname(String bankname) {
            this.bankname = bankname;
        }

        public Customer GetCustomer(){
            return customer;
        }

        public void SetCustomer(Customer customer){
            this.customer = customer;
        }
    }
