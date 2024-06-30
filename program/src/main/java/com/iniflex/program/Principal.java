package com.iniflex.program;

import com.iniflex.program.model.Funcionario;
import com.iniflex.program.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
public class Principal implements CommandLineRunner {

	@Autowired
	private FuncionarioService service;

	public static void main(String[] args) {
		SpringApplication.run(Principal.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		List<Funcionario> lista = new ArrayList<>();
		String url = "http://localhost:8080/api/funcionario";
		URI uri = URI.create(url);
		HttpClient httpClient = HttpClient.newBuilder().build();

		lista.add(new Funcionario("Maria", LocalDate.of(2010, 10, 18), 2009.44,"Operador"));
		lista.add(new Funcionario("João",LocalDate.of(1990, 5, 12), 2284.38,"Operador"));
		lista.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), 9836.14,"Coordenador"));
		lista.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), 19119.88,"Diretor"));
		lista.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), 2234.68,"Recepcionista"));
		lista.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), 1582.72,"Operador"));
		lista.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), 4071.84,"Contador"));
		lista.add(new Funcionario("Laura", LocalDate.of(1994, 7,8), 3017.45,"Gerente"));
		lista.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), 1606.85,"Eletricista"));
		lista.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), 2799.93,"Gerente"));

		DecimalFormat df = new DecimalFormat("#,##0.00");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");


		for(Funcionario item: lista){
			this.service.create(item);
		}

		imprimirInformacaoFuncionarios(lista, df, dtf);

		aumentoSalario(lista, df, dtf);

		Map<String, List<Funcionario>> mapFuncionarios = agruparPorFuncao(lista);

		exibirMapeamento(mapFuncionarios, df, dtf);

		imprimirFuncionariosMesDez(lista, df, dtf);

		imprimirFuncionarioMaisVelho(lista, df, dtf);

		List<Funcionario> listaOrdenada = service.ordenacaoPorNome(lista);

		//Imprimindo a lista ordenada de funcionários
		System.out.println("\nImprimindo os dados dos funcionários por ordem alfabética");
		imprimirInformacaoFuncionarios(listaOrdenada, df, dtf);

		totalSalarios(lista, df);

		imprimirQuantidadeSalarioPorFuncionario(lista, df, dtf);

		Optional<Funcionario> optionalRemover = this.service.readByName("João");

        optionalRemover.ifPresent(funcionario -> this.service.delete(funcionario.getId()));

	}

	private void imprimirInformacaoFuncionarios(List<Funcionario> lista, DecimalFormat df, DateTimeFormatter dtf){
		System.out.println("\nExibindo as informações dos funcionários");
		for (Funcionario item: lista){
			System.out.println("Nome: " + item.getNome() +
					" Data de Nascimento: " + item.getDataNascimento().format(dtf) +
					" Função: " + item.getFuncao() +
					" Salário: " + df.format(item.getSalario()));
		}
	}

	private void aumentoSalario(List<Funcionario> lista, DecimalFormat df, DateTimeFormatter dtf){
		for(Funcionario item: lista){
			item.setSalario(item.getSalario() * 0.1 + item.getSalario());
			this.service.update(item.getId(), item);
		}

		imprimirInformacaoFuncionarios(lista, df, dtf);
	}

	private Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> lista){
		return service.agruparPorFuncao(lista);
	}

	private void exibirMapeamento(Map<String, List<Funcionario>> mapFuncionarios, DecimalFormat df, DateTimeFormatter dtf) {
		System.out.println("\nExibindo funcionarios agrupados por função: ");
		for (Map.Entry<String, List<Funcionario>> entry: mapFuncionarios.entrySet()){
			System.out.println("Cargo: " + entry.getKey());
			for(Funcionario item: entry.getValue()){
				System.out.println(" - " + item.getNome() + " (Salário: " + df.format(item.getSalario()) + ")");
			}
		}
	}

	private void imprimirFuncionariosMesDez(List<Funcionario> lista, DecimalFormat df, DateTimeFormatter dtf){
		System.out.println("\nImprimindo lista com funcionários do mês 10");
		for (Funcionario item: lista){
            if (item.getDataNascimento().getMonth().getValue() == 10) {
				System.out.println("Nome: " + item.getNome() +
						" Data de Nascimento: " + item.getDataNascimento().format(dtf) +
						" Função: " + item.getFuncao() +
						" Salário: " + df.format(item.getSalario()));
            }
        }
	}

	private void imprimirFuncionarioMaisVelho(List<Funcionario> lista, DecimalFormat df, DateTimeFormatter dtf){
		Funcionario maisVelho;
		maisVelho = lista.stream().min(Comparator.comparing(Funcionario::getDataNascimento)).get();

		System.out.println("\nImprimindo os dados do funcionário mais velho: ");
		System.out.println("Nome: " + maisVelho.getNome() +
				" Data de Nascimento: " + maisVelho.getDataNascimento().format(dtf) +
				" Função: " + maisVelho.getFuncao() +
				" Salário: " + df.format(maisVelho.getSalario()));
	}

	private void totalSalarios(List<Funcionario> lista, DecimalFormat df){
		double total = 0.0;

		for (Funcionario item: lista){
			total += item.getSalario();
		}

		System.out.println("\nSoma de todos os salários é de: " + df.format(total));
	}

	private void imprimirQuantidadeSalarioPorFuncionario(List<Funcionario> lista, DecimalFormat df, DateTimeFormatter dtf){
		System.out.println("\nImprimindo a quantidade de salário por cada funcionário");
		for (Funcionario item:  lista){
			System.out.println(item.getNome() + " ganha " + df.format(item.getSalario()/ 1212));
		}
	}
}
