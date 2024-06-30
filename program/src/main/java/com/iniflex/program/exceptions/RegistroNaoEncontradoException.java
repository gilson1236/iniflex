package com.iniflex.program.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException{

    public RegistroNaoEncontradoException(Long id){
        super("Registro não foi encontrado com id: " + id);
    }
}
