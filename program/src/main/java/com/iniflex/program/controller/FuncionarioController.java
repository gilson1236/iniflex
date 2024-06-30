package com.iniflex.program.controller;

import com.iniflex.program.model.Funcionario;
import com.iniflex.program.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

    @Autowired
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public void inserir(Funcionario funcionario){
        this.funcionarioService.create(funcionario);
    }

    @GetMapping
    public List<Funcionario> listar(){
        return funcionarioService.list();
    }

    @GetMapping("/{id}")
    public Funcionario buscar(Long id){
        return funcionarioService.readById(id);
    }

    @PutMapping("/{id}")
    public void atualizar(Long id, Funcionario funcionario){
        funcionarioService.update(id, funcionario);
    }

    @DeleteMapping("/{id}")
    public void remover(Long id){
        funcionarioService.delete(id);
    }

}
