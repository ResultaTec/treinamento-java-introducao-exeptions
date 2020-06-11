package br.com.resultatec.treinamento.exception.conta;

import br.com.resultatec.treinamento.exception.exception.SaldoInsuficienteException;
import br.com.resultatec.treinamento.exception.exception.ValorNegativoException;

public interface IConta {
    void depositar(double valorDeposito) throws ValorNegativoException;
    void sacar(double valorSacar) throws ValorNegativoException, SaldoInsuficienteException;
}
