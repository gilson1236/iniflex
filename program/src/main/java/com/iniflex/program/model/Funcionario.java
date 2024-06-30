package com.iniflex.program.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Funcionario extends Pessoa{

    @Column(nullable = false)
    private double salario;

    @Column(nullable = false)
    private String funcao;

    public Funcionario(){
        super();
    }

    public Funcionario(String nome, LocalDate dataNascimento, double salario, String funcao){
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    @Override
    public double getSalario() {
        return salario;
    }

    @Override
    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String getFuncao() {
        return funcao;
    }

    @Override
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}
