package com.arthur.gerenciamentodedespesas.repository;

import com.arthur.gerenciamentodedespesas.model.Despesa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends CrudRepository<Despesa, String> {
}
