package banco.composite;


public class Lancamento extends Item {

    private final String descricao;
    private final double valor;

    public Lancamento(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }


    @Override
    public String getDescricao() {
        String tipo = valor >= 0 ? "Crédito" : "Débito";
        return String.format("Lançamento: %s - %s: R$ %.2f\n", descricao, tipo, Math.abs(valor));

    }

    @Override
    public double getValor() {
        return valor;
    }


}
