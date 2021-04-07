package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;

public class SimpleStatementFormat implements StatementLineFormat {
    @Override
    public String fields() {
        return "Date"+" \t"+ "Description"+" \t"+ "Draft"+" \t"+ "Credit"+" \t"+ "Available balance"+" ";
    }

    @Override
    public String format(StatementLine objectToFormat) {
        return objectToFormat.getDate()+" \t"+ objectToFormat.getDescription()+" \t"+ objectToFormat.getDraft()+" \t"+ objectToFormat.getCredit()+" \t"+ objectToFormat.getAvailableBalance();
    }
}
