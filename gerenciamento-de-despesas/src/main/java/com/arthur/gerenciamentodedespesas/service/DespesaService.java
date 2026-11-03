package com.arthur.gerenciamentodedespesas.service;

import com.arthur.gerenciamentodedespesas.model.Despesa;
import com.arthur.gerenciamentodedespesas.repository.DespesaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class DespesaService {

    private final DespesaRepository repository;

    public DespesaService(DespesaRepository repository) {
        this.repository = repository;
    }

    public Despesa salvar(@Valid Despesa despesa) {
        if (despesa.getId() == null || despesa.getId().isBlank()) {
            despesa.setId(UUID.randomUUID().toString());
        }
        return repository.save(despesa);
    }

    public List<Despesa> listarTodas() {
        List<Despesa> despesas = new ArrayList<>();
        repository.findAll().forEach(despesas::add);
        return despesas;
    }

    public Despesa buscarPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Despesa não encontrada: " + id));
    }

    public Despesa atualizar(String id, @Valid Despesa dados) {
        Despesa despesaExistente = buscarPorId(id);
        despesaExistente.setDescricao(dados.getDescricao());
        despesaExistente.setValor(dados.getValor());
        despesaExistente.setData(dados.getData());
        return repository.save(despesaExistente);
    }

    public void excluir(String id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Despesa não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
