package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.categories.Category;


public class SavingsAccount extends Account {
    public static Category savingsCategory = new Category("SAVINGS");

    public SavingsAccount(long id, String name) {
        super(id, name);
    }

    @Override
    public double getInterestRate() {
        return BanksConstants.savingsInterestRate();
    }

    @Override
    public void addStatementLine(StatementLine statementLine) {
        statementLine.setCategory(savingsCategory);
        super.getStatementLines().add(statementLine);
    }
    @Override
    public double estimatedAverageBalance() {
        double saldo = 0.0;
        if(super.getStatementLines().size()!=0){
            saldo=(super.getStatementLines().get(super.getStatementLines().size()-1)).getAvailableBalance();
        }
        return saldo;
    }

}
