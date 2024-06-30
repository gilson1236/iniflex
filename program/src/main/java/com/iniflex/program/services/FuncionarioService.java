package com.iniflex.program.services;

import com.iniflex.program.exceptions.RegistroNaoEncontradoException;
import com.iniflex.program.model.Funcionario;
import com.iniflex.program.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    /*
    public FuncionarioService(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }*/

    public void create(Funcionario funcionario){
        this.funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> list(){
        return this.funcionarioRepository.findAll();
    }

    public Funcionario readById(Long id){
        return this.funcionarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException(id)
        );
    }

    public Optional<Funcionario> readByName(String nome){
        return this.funcionarioRepository.buscarPorNome(nome);
    }

    public void update(Long id, Funcionario funcionario){
        this.funcionarioRepository.findById(id)
                .map(registroEncontrado -> {
                   registroEncontrado.setNome(funcionario.getNome());
                   registroEncontrado.setFuncao(funcionario.getFuncao());
                   registroEncontrado.setSalario(funcionario.getSalario());
                   registroEncontrado.setDataNascimento(funcionario.getDataNascimento());
                   return this.funcionarioRepository.save(registroEncontrado);
                }).orElseThrow(() -> new RegistroNaoEncontradoException(id));
    }

    public void delete(Long id){
        this.funcionarioRepository.delete(this.funcionarioRepository.findById(id)
                .orElseThrow());
    }

    public Map<String,List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios){
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public List<Funcionario> ordenacaoPorNome(List<Funcionario> lista){
        return lista.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }
}
