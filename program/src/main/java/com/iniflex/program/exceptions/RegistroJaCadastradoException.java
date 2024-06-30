package com.iniflex.program.exceptions;

public class RegistroJaCadastradoException extends RuntimeException{

    public RegistroJaCadastradoException(Long id){
        super("Registro já cadastrado de id: " + id);
    }
}
