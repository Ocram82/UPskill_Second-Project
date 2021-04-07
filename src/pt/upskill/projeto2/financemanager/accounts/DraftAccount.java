package pt.upskill.projeto2.financemanager.accounts;

public class DraftAccount extends Account {

    public DraftAccount(long id, String name) {
        super(id, name);
    }

    @Override
    public double getInterestRate() {
        return super.getInterestRate();
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


