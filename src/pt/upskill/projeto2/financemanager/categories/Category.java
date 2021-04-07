package pt.upskill.projeto2.financemanager.categories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class Category implements Serializable{
    private String name;
    private List<String> tags;
    private static final long serialVersionUID = -9107819223195202547L;

    public Category(String name) {
        this.name = name;
        this.tags = new ArrayList<>();
    }

    /**
     * Função que lê o ficheiro categories e gera uma lista de {@link Category} (método fábrica)
     * Deve ser utilizada a desserialização de objetos para ler o ficheiro binário categories.
     *
     * @param file - Ficheiro onde estão apontadas as categorias possíveis iniciais, numa lista serializada (por defeito: /account_info/categories)
     * @return uma lista de categorias, geradas ao ler o ficheiro
     */
    public static List<Category> readCategories(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Category> category = (List<Category>) in.readObject();
            in.close();
            return category;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro a ler o ficheiro");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Função que grava no ficheiro categories (por defeito: /account_info/categories) a lista de {@link Category} passada como segundo argumento
     * Deve ser utilizada a serialização dos objetos para gravar o ficheiro binário categories.
     * @param file
     * @param categories
     */
    public static void writeCategories(File file, List<Category> categories) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(categories);
            out.close();
        } catch (IOException e) {
            System.out.println("Erro a salver o ficheiro");
            e.printStackTrace();
        }
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean hasTag(String tag) {
        for (String categoryTag: tags){
            if (categoryTag.equals(tag)){
                return true;
            }
        }
        return false;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
