package com.arthur.gerenciamentodedespesas.view;

import com.arthur.gerenciamentodedespesas.model.Despesa;
import com.arthur.gerenciamentodedespesas.service.DespesaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleMenu implements CommandLineRunner {

    private final DespesaService service;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsoleMenu(DespesaService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println("\n=== GERENCIAMENTO DE DESPESAS ===");
            System.out.println("1 - Cadastrar despesa");
            System.out.println("2 - Listar despesas");
            System.out.println("3 - Buscar despesa por ID");
            System.out.println("4 - Atualizar despesa");
            System.out.println("5 - Excluir despesa");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            try {
                switch (opcao) {
                    case "1" -> cadastrar();
                    case "2" -> listar();
                    case "3" -> buscar();
                    case "4" -> atualizar();
                    case "5" -> excluir();
                    case "0" -> {
                        System.out.println("Encerrando o menu.");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | DateTimeParseException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void cadastrar() {
        Despesa despesa = lerDados();
        despesa.setId(null);
        Despesa salva = service.salvar(despesa);
        System.out.println("Despesa cadastrada. ID: " + salva.getId());
    }

    private void listar() {
        List<Despesa> despesas = service.listarTodas();
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa cadastrada.");
            return;
        }
        despesas.forEach(this::mostrarDespesa);
    }

    private void buscar() {
        System.out.print("Digite o ID da despesa: ");
        mostrarDespesa(service.buscarPorId(scanner.nextLine().trim()));
    }

    private void atualizar() {
        System.out.print("Digite o ID da despesa que deseja atualizar: ");
        String id = scanner.nextLine().trim();
        Despesa dados = lerDados();
        mostrarDespesa(service.atualizar(id, dados));
        System.out.println("Despesa atualizada.");
    }

    private void excluir() {
        System.out.print("Digite o ID da despesa que deseja excluir: ");
        service.excluir(scanner.nextLine().trim());
        System.out.println("Despesa excluída.");
    }

    private Despesa lerDados() {
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine().trim();
        if (descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição não pode ficar vazia.");
        }

        System.out.print("Valor (ex.: 125.50): ");
        BigDecimal valor = new BigDecimal(scanner.nextLine().trim().replace(",", "."));
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }

        System.out.print("Data (dd/MM/yyyy): ");
        LocalDate data = LocalDate.parse(scanner.nextLine().trim(), formatoData);

        return new Despesa(null, descricao, valor, data);
    }

    private void mostrarDespesa(Despesa despesa) {
        System.out.println("------------------------------------");
        System.out.println("ID: " + despesa.getId());
        System.out.println("Descrição: " + despesa.getDescricao());
        System.out.println("Valor: R$ " + despesa.getValor());
        System.out.println("Data: " + despesa.getData().format(formatoData));
    }
}
