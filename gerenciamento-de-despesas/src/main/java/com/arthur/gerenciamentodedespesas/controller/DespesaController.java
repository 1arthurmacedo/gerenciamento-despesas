package com.arthur.gerenciamentodedespesas.controller;

import com.arthur.gerenciamentodedespesas.model.Despesa;
import com.arthur.gerenciamentodedespesas.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService service;

    public DespesaController(DespesaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Despesa criar(@Valid @RequestBody Despesa despesa) {
        despesa.setId(null);
        return service.salvar(despesa);
    }

    @GetMapping
    public List<Despesa> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Despesa buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Despesa atualizar(@PathVariable String id, @Valid @RequestBody Despesa despesa) {
        return service.atualizar(id, despesa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable String id) {
        service.excluir(id);
    }
}
