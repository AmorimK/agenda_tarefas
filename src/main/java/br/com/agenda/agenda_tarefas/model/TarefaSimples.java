package br.com.agenda.agenda_tarefas.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("SIMPLES")
public class TarefaSimples extends Tarefa {
    // Herda ID, descricao e dataExecucao automaticamente
}