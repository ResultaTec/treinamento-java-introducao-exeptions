package br.com.resultatec.treinamento.exception.caixaeletronico;

import br.com.resultatec.treinamento.exception.conta.Conta;
import br.com.resultatec.treinamento.exception.exception.SaldoInsuficienteException;
import br.com.resultatec.treinamento.exception.exception.ValorNegativoException;

public class CaixaEletronico {

    public void despositar(Conta conta, double valorDeposito) throws ValorNegativoException {
        conta.depositar(valorDeposito);
    }

    public void sacar(Conta conta, double valorSaque) throws ValorNegativoException, SaldoInsuficienteException {
        conta.sacar(valorSaque);
    }

    public void transferir(Conta contaOrigem, Conta contaDestino, double valorTransferencia) throws ValorNegativoException, SaldoInsuficienteException {
        try {
            sacar(contaOrigem, valorTransferencia);
        } catch (SaldoInsuficienteException ex) {
            throw new SaldoInsuficienteException("Saldo Insuficiente para realizar a transferencia.");
        }

        despositar(contaDestino, valorTransferencia);
    }
}
