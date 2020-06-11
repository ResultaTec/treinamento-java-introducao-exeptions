package br.com.resultatec.treinamento.exception.caixaeletronico;

import br.com.resultatec.treinamento.exception.conta.Conta;
import br.com.resultatec.treinamento.exception.conta.ContaCorrente;
import br.com.resultatec.treinamento.exception.conta.ContaPoupanca;
import br.com.resultatec.treinamento.exception.exception.SaldoInsuficienteException;
import br.com.resultatec.treinamento.exception.exception.ValorNegativoException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CaixaEletronicoTest {

    CaixaEletronico caixaEletronico = new CaixaEletronico();

    @Test
    public void depositarValorEmContaCorrenteTest() throws ValorNegativoException {

        Conta contaCorrente = new ContaCorrente();
        double valorDeposito = 100.00;

        caixaEletronico.despositar(contaCorrente, valorDeposito);

        Assert.isTrue(valorDeposito==contaCorrente.getSaldo(), "O valor depositado em conta corrente foi de R$"+valorDeposito+" o valor retornado foi de R$"+contaCorrente.getSaldo());
    }

    @Test
    public void sacarValorEmContaCorrenteTest() throws ValorNegativoException, SaldoInsuficienteException {
        Conta contaCorrente = new ContaCorrente();
        double valorDeposito = 100.00;
        double valorSaque = 80.00;

        caixaEletronico.despositar(contaCorrente, valorDeposito);
        caixaEletronico.sacar(contaCorrente, valorSaque);

        double saldoFinal = 20.00;

        Assert.isTrue(saldoFinal==contaCorrente.getSaldo(), "O valor do saque em conta corrente foi de R$"+valorSaque+" o valor em conta final ficou em de R$"+contaCorrente.getSaldo()+" era esperado R$"+saldoFinal);

    }

    @Test
    public void excecaoValorDepositoNegativoTest() {

        Conta contaCorrente = new ContaCorrente();
        double valorDeposito = -100.00;

        ValorNegativoException exception = assertThrows(ValorNegativoException.class, () -> {
            caixaEletronico.despositar(contaCorrente, valorDeposito);
        });

        String erro = exception.getMessage();
        String erroEsperado = "Não é possível depositar um valor negativo.";

        Assert.isTrue(erro.equals(erroEsperado), "O erro esperado era "+erroEsperado+" retornou "+erro);
    }

    @Test
    public void excecaoValorSaqueNegativoTest() {
        Conta contaCorrente = new ContaCorrente();
        double valorSaque = -100.00;

        ValorNegativoException exception = assertThrows(ValorNegativoException.class, () -> {
            caixaEletronico.sacar(contaCorrente, valorSaque);
        });

        String erro = exception.getMessage();
        String erroEsperado = "Não é possível sacar um valor negativo.";

        Assert.isTrue(erro.equals(erroEsperado), "O erro esperado era "+erroEsperado+" retornou "+erro);
    }

    @Test
    public void excecaoSaldoInsuficienteSaqueTest() throws ValorNegativoException {

        Conta contaCorrente = new ContaCorrente();
        double valorDeposito = 100.00;
        double valorSaque = 110.00;

        caixaEletronico.despositar(contaCorrente, valorDeposito);

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> {
            caixaEletronico.sacar(contaCorrente, valorSaque);
        });

        String erro = exception.getMessage();
        String erroEsperado = "Saldo Insuficiente para realizar o saque.";

        Assert.isTrue(erro.equals(erroEsperado), "O erro esperado era "+erroEsperado+" retornou "+erro);
    }

    @Test
    public void transferirSaldoEntreDuasContasTest() throws ValorNegativoException, SaldoInsuficienteException {
        Conta contaOrigem = new ContaCorrente();
        Conta contaDestino = new ContaPoupanca();

        double valorDepositoContaOrigem = 100.00;
        double valorDespositoContaDestino = 50.00;
        double valorTransferencia = 25.00;
        double saldoEsperado = 75.00;

        caixaEletronico.despositar(contaOrigem, valorDepositoContaOrigem);
        caixaEletronico.despositar(contaDestino, valorDespositoContaDestino);
        caixaEletronico.transferir(contaOrigem, contaDestino, valorTransferencia);

        Assert.isTrue(contaDestino.getSaldo() == saldoEsperado, "O valor esperado de saldo na conta de destino era de R$ "+saldoEsperado+" mas retornou R$"+contaDestino.getSaldo());
    }

    @Test
    public void excecaoSaldoInsuficienteTransferenciaTest() throws ValorNegativoException {
        Conta contaCorrente = new ContaCorrente();
        Conta contaPoupanca = new ContaPoupanca();

        double valorDeposito = 100.00;
        double valorTransferencia = 110.00;

        caixaEletronico.despositar(contaCorrente, valorDeposito);

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> {
            caixaEletronico.transferir(contaCorrente, contaPoupanca, valorTransferencia);
        });

        String erro = exception.getMessage();
        String erroEsperado = "Saldo Insuficiente para realizar a transferencia.";

        Assert.isTrue(erro.equals(erroEsperado), "O erro esperado era "+erroEsperado+" retornou "+erro);
    }
}
