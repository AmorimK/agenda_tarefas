package br.com.agenda.agenda_tarefas.repository;

import br.com.agenda.agenda_tarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByDataExecucaoBetween(LocalDate inicio, LocalDate fim);

    List<Tarefa> findByDescricaoContainingIgnoreCase(String termo);
}