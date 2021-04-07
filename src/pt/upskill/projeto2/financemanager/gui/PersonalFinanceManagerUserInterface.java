package pt.upskill.projeto2.financemanager.gui;

import pt.upskill.projeto2.financemanager.PersonalFinanceManager;
import pt.upskill.projeto2.utils.Menu;

import javax.swing.text.html.Option;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class PersonalFinanceManagerUserInterface {

    public PersonalFinanceManagerUserInterface(
            PersonalFinanceManager personalFinanceManager) {
        this.personalFinanceManager = personalFinanceManager;
    }

    private static final String OPT_GLOBAL_POSITION = "Posição Global";
    private static final String OPT_ACCOUNT_STATEMENT = "Movimentos Conta";
    private static final String OPT_LIST_CATEGORIES = "Listar categorias";
    private static final String OPT_ANALISE = "Análise";
    private static final String OPT_EXIT = "Sair";

    private static final String OPT_MONTHLY_SUMMARY = "Evoluçãoo global por mês";
    private static final String OPT_PREDICTION_PER_CATEGORY = "Previsão gastos totais do mês por categoria";
    private static final String OPT_ANUAL_INTEREST = "Previsão juros anuais";

    private static final String[] OPTIONS_ANALYSIS = {OPT_MONTHLY_SUMMARY, OPT_PREDICTION_PER_CATEGORY, OPT_ANUAL_INTEREST};
    private static final String[] OPTIONS = {OPT_GLOBAL_POSITION,
            OPT_ACCOUNT_STATEMENT, OPT_LIST_CATEGORIES, OPT_ANALISE, OPT_EXIT};

    private PersonalFinanceManager personalFinanceManager;

    public void execute() {
        //para questionar sim ou nao ao utilizador:
        boolean teste = Menu.yesOrNoInput("Deseja executar o Programa?");
        if (teste==true) {
            System.out.println("Bem-vindo");
        } else {
            System.out.println("Adeus");
            System.exit(0);
        }
        while (true){
            String selecao=Menu.requestSelection("Escolha uma opcao",OPTIONS);
            if(selecao.equals(OPT_EXIT)){
                    teste=Menu.yesOrNoInput("Deseja sair do Programa?");
                    if (teste==true){
                        System.out.println("Adeus");
                        System.exit(0);
                    }
            }
            if(selecao.equals(OPT_GLOBAL_POSITION)){
                //todo
            }
            if(selecao.equals(OPT_ANALISE)){
                //todo
            }
            if(selecao.equals(OPT_LIST_CATEGORIES)){
                //todo
            }
            if(selecao.equals(OPT_ACCOUNT_STATEMENT)){
                //todo
            }
        }
    }
}
