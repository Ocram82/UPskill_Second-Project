package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

public class FileAccountFormat implements Format {

    @Override
    public String format(Object objectToFormat) {
        String nl = System.getProperty("line.separator");
        String info = "Account Info - " + new Date().toString() + nl
                + "Account  ;" + ((Account) objectToFormat).getId() + " ; "
                + ((Account) objectToFormat).getCurrency() + "  ;" + ((Account) objectToFormat).getName() + " ;"
                + ((Account) objectToFormat).getAccountType() + " ;" + nl
                + "Start Date ;" + ((Account) objectToFormat).getStartDate().toString() + nl
                + "End Date ;" + ((Account) objectToFormat).getEndDate().toString() + nl
                + "Date ;Value Date ;Description ;Draft ;Credit ;Accounting balance ;Available balance" + nl;
        for (StatementLine statementLine: ((Account) objectToFormat).getStatementLines()){
                info+= statementLine.getDate().toString()+" ;"
                        +statementLine.getValueDate().toString()+" ;"
                        +statementLine.getDescription()+" ;"
                        +statementLine.getDraft()+" ;"
                        +statementLine.getCredit()+" ;"
                        +statementLine.getAccountingBalance()+" ;"
                        +statementLine.getAvailableBalance()+nl;
        }
        return info;
    }
}
