package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import model.NivelEmergencia;
import model.Paciente;

public class Hospital {

    private final List<Paciente> pacientes = new ArrayList<>();


    public void admitir(Paciente p) {
        pacientes.add(p);
    }


    public List<Paciente> getPacientes() {
        return Collections.unmodifiableList(pacientes);
    }

    public List<Paciente> listarEmergenciais() {
        return pacientes.stream()
                .filter(p -> p.nivel() == NivelEmergencia.URGENTE || p.nivel() == NivelEmergencia.CRITICO)
                .sorted(Comparator.comparingInt(Paciente::idade).reversed())
                .toList();
    }

    public OptionalDouble calcularMediaIdadeCriticos() {
        return pacientes.stream()
                .filter(p -> p.nivel() == NivelEmergencia.CRITICO)
                .mapToInt(Paciente::idade)
                .average();
    }


    public Optional<Paciente> buscarPacienteMaisIdoso() {
        return pacientes.stream()
                .max(Comparator.comparingInt(Paciente::idade));
    }

    public long contarSegurados() {
        return pacientes.stream()
                .filter(Paciente::possuiPlano)
                .count();
    }
}
