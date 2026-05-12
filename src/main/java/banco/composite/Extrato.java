package banco.composite;

import java.util.ArrayList;
import java.util.List;


public class Extrato extends Item {

    private final String nome;
    private final List<Item> itens = new ArrayList<>();

    public Extrato(String nome) {
        this.nome = nome;
    }

    public void addItem(Item item) {
        itens.add(item);
    }

    @Override
    public String getDescricao() {
        StringBuilder sb = new StringBuilder();
        sb.append("Extrato: ").append(nome).append("\n");
        for (Item item : itens) {
            sb.append(item.getDescricao()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public double getValor() {
        return itens.stream().mapToDouble(Item::getValor).sum();
    } 


}
