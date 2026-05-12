package banco.composite;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    @Test
    void deveRetornarExtratoCompletoComSubExtratos() {
        Extrato janeiro = new Extrato("Janeiro/2025");
        janeiro.addItem(new Lancamento("Salário", 5000.00));
        janeiro.addItem(new Lancamento("Aluguel", -1500.00));
        janeiro.addItem(new Lancamento("Supermercado", -300.00));

        Extrato fevereiro = new Extrato("Fevereiro/2025");
        fevereiro.addItem(new Lancamento("Salário", 5000.00));
        fevereiro.addItem(new Lancamento("Conta de Luz", -150.00));

        Extrato extratoAnual = new Extrato("Extrato Anual 2025");
        extratoAnual.addItem(janeiro);
        extratoAnual.addItem(fevereiro);

        ContaBancaria conta = new ContaBancaria("João Silva", "0001-2");
        conta.setExtrato(extratoAnual);

        String esperado =
                "Conta: 0001-2 - Titular: João Silva\n" +
                        "Extrato: Extrato Anual 2025\n" +
                        "Extrato: Janeiro/2025\n" +
                        "Lançamento: Salário - Crédito: R$ 5000,00\n" +
                        "Lançamento: Aluguel - Débito: R$ 1500,00\n" +
                        "Lançamento: Supermercado - Débito: R$ 300,00\n" +
                        "Extrato: Fevereiro/2025\n" +
                        "Lançamento: Salário - Crédito: R$ 5000,00\n" +
                        "Lançamento: Conta de Luz - Débito: R$ 150,00\n";

        assertEquals(esperado, conta.getExtrato());
    }

    @Test
    void deveCalcularSaldoCorretamenteSomandoTodosLancamentos() {
        Extrato extratoMes = new Extrato("Março/2025");
        extratoMes.addItem(new Lancamento("Salário", 3000.00));
        extratoMes.addItem(new Lancamento("Freelance", 800.00));
        extratoMes.addItem(new Lancamento("Aluguel", -900.00));
        extratoMes.addItem(new Lancamento("Mercado", -200.00));

        ContaBancaria conta = new ContaBancaria("Maria Souza", "0002-1");
        conta.setExtrato(extratoMes);

        assertEquals(2700.00, conta.getSaldo(), 0.001);
    }

    @Test
    void deveLancarExcecaoAoObterExtratoDe_ContaSemExtrato() {
        try {
            ContaBancaria conta = new ContaBancaria("Pedro Lima", "0003-5");
            conta.getExtrato();
            fail("Deveria ter lançado NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("Conta sem extrato definido", e.getMessage());
        }
    }

    @Test
    void deveLancarExcecaoAoCalcularSaldoDe_ContaSemExtrato() {
        try {
            ContaBancaria conta = new ContaBancaria("Ana Oliveira", "0004-9");
            conta.getSaldo();
            fail("Deveria ter lançado NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("Conta sem extrato definido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExtratoVazioQuandoNaoHaLancamentos() {
        Extrato extratoVazio = new Extrato("Abril/2025");
        ContaBancaria conta = new ContaBancaria("Carlos Nunes", "0005-3");
        conta.setExtrato(extratoVazio);

        assertEquals(0.0, conta.getSaldo(), 0.001);
        String extrato = conta.getExtrato();
        assertTrue(extrato.contains("Carlos Nunes"));
        assertTrue(extrato.contains("Abril/2025"));
    }

    @Test
    void deveCalcularValorCorretoDeUmLancamentoCreditoPositivo() {
        Lancamento credito = new Lancamento("PIX recebido", 500.00);
        assertEquals(500.00, credito.getValor(), 0.001);
        assertTrue(credito.getDescricao().contains("Crédito"));
    }

    @Test
    void deveCalcularValorCorretoDeUmLancamentoDebitoNegativo() {
        Lancamento debito = new Lancamento("TED enviado", -250.00);
        assertEquals(-250.00, debito.getValor(), 0.001);
        assertTrue(debito.getDescricao().contains("Débito"));
    }
}
