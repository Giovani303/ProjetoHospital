package model;

public record Paciente(String nome, int idade, NivelEmergencia nivel, boolean possuiPlano) {

    public Paciente {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do paciente não pode ser nulo ou vazio.");
        }
        if (idade < 0) {
            throw new IllegalArgumentException("A idade do paciente não pode ser negativa.");
        }
        if (nivel == null) {
            throw new IllegalArgumentException("O nível de emergência é obrigatório.");
        }
    }
}
