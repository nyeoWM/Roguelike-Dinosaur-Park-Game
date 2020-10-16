package game.shop;

public class Account {

    private double balance;

    /**
     * constructor for the account class.
     * @param balance       balance inside the account.
     */
    public Account( double balance){
        this.balance = balance;

    }

    /**
     * return the balance inside the account.
     * @return          balance currently inside the account.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * setter for the account class.
     * @param balance     to set the balance inside the account.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * to deposit balance inside the account.
     * @param amount    the amount of money to deposit.
     */
    public void deposit(double amount){
        balance = balance + amount;
    }

    /**
     * to deduct money after a purchase from the account.
     * @param amount        the amount of money to deduct.
     */
    public void deduct(double amount) throws Exception {
        if (this.balance - amount >= 0) {
            balance = balance - amount;
        }
        else {
            throw new Exception("Insufficient balance");
        }

    }












}

