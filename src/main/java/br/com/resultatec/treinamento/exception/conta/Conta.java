package br.com.resultatec.treinamento.exception.conta;

import br.com.resultatec.treinamento.exception.exception.SaldoInsuficienteException;
import br.com.resultatec.treinamento.exception.exception.ValorNegativoException;

public class Conta implements IConta {

    private double saldo;

    public Conta() {
        this.setSaldo(0.00);
    }

    @Override
    public void depositar(double valorDeposito) throws ValorNegativoException {
        if (valorDeposito < 0) throw new ValorNegativoException("Não é possível depositar um valor negativo.");
        saldo += valorDeposito;
    }

    @Override
    public void sacar(double valorSacar) throws ValorNegativoException, SaldoInsuficienteException{
        if (valorSacar < 0) throw new ValorNegativoException("Não é possível sacar um valor negativo.");
        if (saldo < valorSacar) throw new SaldoInsuficienteException("Saldo Insuficiente para realizar o saque.");

        saldo -= valorSacar;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
