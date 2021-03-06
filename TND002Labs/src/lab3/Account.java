package lab3;

public class Account {
	protected int accountNumber;
	protected String accountType;
	protected double balance;
	protected Account otherAccount;
	public static final double FEE = 10.0;
	public static final double INTEREST = 0.02;
	
	public Account(int accountNumber) {
		this.accountNumber = accountNumber;
		this.accountType = "Current";
	}
	public Account(int accountNumber, Current currentAccount){
		this.accountNumber = accountNumber;
		this.otherAccount = currentAccount;
		this.accountType = "Savings";
		
		currentAccount.otherAccount = this;
	}
	public String getAccountType() {
		return this.accountType;
	}
	public int getNumber() {
		return this.accountNumber;
	}
	public double getBalance() {
		return this.balance;
	}
	public void annualChange() {
		if(accountType.equals("Current")){
			this.balance = this.balance - FEE;
		}
		if(accountType.equals("Savings")) {
			this.balance = this.balance + this.balance * INTEREST;
		}
	}
}
