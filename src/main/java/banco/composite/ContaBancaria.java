package banco.composite;


public class ContaBancaria {

    private final String titular;
    private final String numero;
    private Extrato extrato;

    public ContaBancaria(String titular, String numero) {
        this.titular = titular;
        this.numero = numero;
    }

    public void setExtrato(Extrato extrato) {
        this.extrato = extrato;
    }

    public String getExtrato() {
        if (extrato == null) {
            throw new NullPointerException("Conta sem extrato definido");
        }
        return "Conta: " + numero + " - Titular: " + titular + "\n" + extrato.getDescricao();
    }

    public double getSaldo() {
        if (extrato == null) {
            throw new NullPointerException("Conta sem extrato definido");
        }
        return extrato.getValor();
    }

    public String getTitular() {
        return titular;
    }

    public String getNumero() {
        return numero;
    }
}

