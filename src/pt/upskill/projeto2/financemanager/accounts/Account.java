package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.filters.AfterDateSelector;
import pt.upskill.projeto2.financemanager.filters.BeforeDateSelector;
import pt.upskill.projeto2.financemanager.filters.Filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class Account {
    private long id;
    private String name;
    private List<StatementLine> statementLines;
    private final String additionalInfo = "";
    private String currency;
    private String accountType;
    private Date endDate;
    private Date startDate;

    public Account(long id, String name) {
        this.id = id;
        this.name = name;
        this.statementLines = new ArrayList<>();
    }

    public static Account newAccount(File file) {
        Account account = null;
        try {
            Scanner scanner = new Scanner(file);
            String[] line1 = scanner.nextLine().trim().split(" - ");
            String actualDate = line1[1].trim();
            String[] line2 = scanner.nextLine().trim().split(";");
            long id = Long.parseLong(line2[1].trim());
            String currency = line2[2].trim();
            String desc = line2[3].trim();
            String accountType = line2[4].trim();

            switch (accountType) {
                case "SavingsAccount":
                    account = new SavingsAccount(id, desc);
                    break;
                case "DraftAccount":
                    account = new DraftAccount(id, desc);
                    break;
                default:
                    System.out.println("Account type ?");
            }
            account.setCurrency(currency);
            account.setAccountType(accountType);
            String[] line3 = scanner.nextLine().trim().split(";");
            String[] line4 = scanner.nextLine().trim().split(";");
            String[] statementRawLine = scanner.nextLine().trim().split(";");

            while (scanner.hasNextLine()) {
                String statementNextLine=scanner.nextLine().trim();
                if(statementNextLine.isEmpty()){
                    break;
                }
                String[] statementRaw = statementNextLine.split(" ;");
                String[] data1 = statementRaw[0].trim().split("-");
                int dia = Integer.parseInt(data1[0].trim());
                int mes = Integer.parseInt(data1[1].trim());
                int ano = Integer.parseInt(data1[2].trim());
                Date data = new Date(dia, mes, ano);
                String[] data2 = statementRaw[1].trim().split("-");
                int diaV = Integer.parseInt(data2[0].trim());
                int mesV = Integer.parseInt(data2[1].trim());
                int anoV = Integer.parseInt(data2[2].trim());
                Date dataV = new Date(diaV, mesV, anoV);
                String descricao = statementRaw[2].trim();
                double gasto = Double.parseDouble(statementRaw[3].trim());
                double deposito = Double.parseDouble(statementRaw[4].trim());
                double saldo = Double.parseDouble(statementRaw[5].trim());
                double saldoD = Double.parseDouble(statementRaw[6].trim());

                StatementLine statementLine = new StatementLine(data, dataV, descricao, gasto, deposito, saldo, saldoD, null);
                account.statementLines.add(statementLine);
            }
            scanner.close();
        } catch (FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Erro ao ler o ficheiro");
        }

        return account;
    }


    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInterestRate() {
        return BanksConstants.normalInterestRate();
    }

    public ArrayList<StatementLine> getStatementLines() {
        return (ArrayList<StatementLine>) statementLines;
    }

    public void addStatementLine(StatementLine statementLine) {
        statementLines.add(statementLine);
        Collections.sort(statementLines);
    }

    public double currentBalance() {
        if (statementLines.size() > 0) {
            return statementLines.get(statementLines.size() - 1).getAvailableBalance();
        } else {
            return 0;
        }
    }

    public double totalDraftsForCategorySince(Category category, Date date) {
        double total = 0;
        for (StatementLine operacoes : statementLines) {
            if (operacoes.getDate().after(date) && operacoes.getCategory()!= null && operacoes.getCategory().equals(category)) {
                total += operacoes.getDraft();
            }
        }
        return total;
    }

    private StatementLine getLastStatementLine() {
        int size = this.statementLines.size();
        if (size == 0) {
            return null;
        }
        return this.statementLines.get(this.statementLines.size() - 1);
    }

    public double estimatedAverageBalance() {
        int currentYear = new Date().getYear();

        StatementLine lastStatementLine = getLastStatementLine();
        if (lastStatementLine == null) {
            return currentBalance();
        }

        Date lastYearsDate = new Date(31, 12, currentYear - 1);
        AfterDateSelector selector = new AfterDateSelector(lastYearsDate);
        BeforeDateSelector selector2 = new BeforeDateSelector(lastYearsDate);
        Filter<StatementLine, AfterDateSelector> filterAfter = new Filter<>(selector);
        Filter<StatementLine, BeforeDateSelector> filterBefore = new Filter<>(selector2);
        List<StatementLine> yearStatements = (List<StatementLine>) filterAfter.apply(statementLines);
        List<StatementLine> lastYearStatements = (List<StatementLine>) filterBefore.apply(statementLines);

        StatementLine lastStatement = lastYearStatements.get(lastYearStatements.size() - 1);
        double balancoTotal = 0.0;
        for (int i = 0; i < yearStatements.size(); i++) {
            StatementLine statement = yearStatements.get(i);
            int diffDays;
            if (i == 0) {
                diffDays = statement.getDate().diffInDays(lastYearsDate);

            } else {
                diffDays = statement.getDate().diffInDays(lastStatement.getDate());

            }
            balancoTotal += lastStatement.getAvailableBalance() * diffDays;
            lastStatement = statement;
        }
        int numTotalDias = lastYearsDate.diffInDays(new Date()) - 1;
        return balancoTotal / numTotalDias;
    }

    public String additionalInfo() {
        return additionalInfo;
    }

    public Date getStartDate() {
        if (statementLines.size() > 0) {
            return statementLines.get(0).getDate();
        } else {
            return null;
        }
    }

    public Date getEndDate() {
        if (statementLines.size() > 0) {
            return statementLines.get(statementLines.size() - 1).getDate();
        } else {
            return null;
        }
    }


    public void removeStatementLinesBefore(Date date) {
        BeforeDateSelector selector = new BeforeDateSelector(date);
        Filter<StatementLine, BeforeDateSelector> filter = new Filter<>(selector);
        statementLines = (List<StatementLine>) filter.apply(statementLines);
    }

    public double totalForMonth(int i, int i1) {
        double total = 0;
        for (StatementLine operacao : statementLines) {
            if (operacao.getDate().getMonth().ordinal() == i && operacao.getDate().getYear() == i1) {
                total += operacao.getDraft();
            }
        }
        return total;
    }

    public void autoCategorizeStatements(List<Category> categories) {
        for (StatementLine statementLine : statementLines) {
            for (Category category : categories) {
                if (category.hasTag(statementLine.getDescription())) {
                    statementLine.setCategory(category);
                }
            }
        }
    }
}
