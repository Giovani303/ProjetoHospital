package app;

import java.util.List;
import java.util.Scanner;

import model.NivelEmergencia;
import model.Paciente;
import service.Hospital;

public class GestorHospitalar {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Hospital hospital = new Hospital();

    public static void main(String[] args) {
        var executando = true;

        while (executando) {
            exibirMenu();
            var opcao = scanner.nextLine();


            executando = switch (opcao) {
                case "1" -> { execAdmissao(); yield true; }
                case "2" -> { execTriagemGeral(); yield true; }
                case "3" -> { execEstatisticas(); yield true; }
                case "4" -> { execBuscaAvancada(); yield true; }
                case "5" -> { System.out.println("Encerrando o sistema..."); yield false; }
                default -> { System.out.println("Opção inválida! Tente novamente."); yield true; }
            };
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE GESTÃO HOSPITALAR E TRIAGEM ===");
        System.out.println("1. Admitir Paciente");
        System.out.println("2. Relatório de Triagem (Urgentes / Críticos)");
        System.out.println("3. Painel de Estatísticas");
        System.out.println("4. Buscar Caso de Risco");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }


    private static void execAdmissao() {
        try {
            System.out.print("Nome do Paciente: ");
            var nome = scanner.nextLine();

            System.out.print("Idade: ");
            var idade = Integer.parseInt(scanner.nextLine());

            System.out.print("Nível de Emergência (LEVE, MODERADO, URGENTE, CRITICO): ");
            var nivel = NivelEmergencia.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Possui Plano de Saúde? (S/N): ");
            var possuiPlano = scanner.nextLine().trim().equalsIgnoreCase("S");

            var novoPaciente = new Paciente(nome, idade, nivel, possuiPlano);
            hospital.admitir(novoPaciente);

            System.out.println("Paciente admitido com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao admitir paciente: " + e.getMessage());
        }
    }


    private static void execTriagemGeral() {
        List<Paciente> emergenciais = hospital.listarEmergenciais();

        if (emergenciais.isEmpty()) {
            System.out.println("Nenhum paciente em estado urgente ou crítico no momento.");
        } else {
            System.out.println("--- Pacientes Urgentes / Críticos (ordenados por idade) ---");
            emergenciais.forEach(p ->
                    System.out.printf("%-20s | Idade: %3d | Nível: %-9s | Plano: %s%n",
                            p.nome(), p.idade(), p.nivel(), p.possuiPlano() ? "Sim" : "Não"));
        }
    }


    private static void execEstatisticas() {
        var mediaIdadeCriticos = hospital.calcularMediaIdadeCriticos();
        var totalSegurados = hospital.contarSegurados();

        System.out.println("--- Painel de Estatísticas ---");

        mediaIdadeCriticos.ifPresentOrElse(
                media -> System.out.printf("Média de idade dos casos CRÍTICOS: %.2f anos%n", media),
                () -> System.out.println("Não há pacientes em estado CRÍTICO para calcular a média.")
        );

        System.out.printf("Total de pacientes segurados (com plano): %d%n", totalSegurados);
    }


    private static void execBuscaAvancada() {
        var pacienteMaisIdoso = hospital.buscarPacienteMaisIdoso();

        pacienteMaisIdoso.ifPresentOrElse(
                p -> System.out.printf("Paciente mais idoso: %s - %d anos - Nível: %s%n",
                        p.nome(), p.idade(), p.nivel()),
                () -> System.out.println("O hospital está vazio. Não há pacientes para comparar.")
        );
    }
}
