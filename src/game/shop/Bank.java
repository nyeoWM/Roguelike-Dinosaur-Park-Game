package game.shop;

public class Bank {
    private Account account_player;

    /**
     * constructor for the bank class.
     * @param initialBalance        the initial balance the bank has.
     */
    public Bank(int initialBalance) {

        account_player = new Account(initialBalance);
    }

    /**
     * return the current balance in account.
     * @return         current balance in account.
     */
    public double getBalance() {
        return account_player.getBalance();
    }

    /**
     * deduct money from account.
     * @param amount        amount deducted.
     * @throws Exception    to make sure that amount after deduction is  >= 0 (no negative values).
     */
    public void deduct(double amount) throws Exception {
        account_player.deduct(amount);
    }

    /**
     * deposit money into account.
     * @param amount        amount to deposit.
     */
    public void deposit(double amount) {
        account_player.deposit(amount);
    }



}

