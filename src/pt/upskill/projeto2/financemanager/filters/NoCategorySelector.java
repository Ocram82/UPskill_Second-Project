package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;

public class NoCategorySelector implements Selector<StatementLine>{

    @Override
    public boolean isSelected(StatementLine statementLine) {
        if(statementLine.getCategory()==null){
            return true;
        } else {
            return false;
        }
    }
}
