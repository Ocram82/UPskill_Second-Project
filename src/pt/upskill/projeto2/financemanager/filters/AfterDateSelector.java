package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

public class AfterDateSelector implements Selector<StatementLine>{
    private Date date;

    public AfterDateSelector(Date date) {
        this.date=date;
    }
    @Override
    public boolean isSelected(StatementLine item) {
        if (item.getDate().after(date)){
            return true;
        } else {
            return false;
        }
    }
}
