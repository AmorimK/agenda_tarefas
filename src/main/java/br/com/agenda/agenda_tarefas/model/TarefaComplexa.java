package br.com.agenda.agenda_tarefas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("COMPLEXA")
public class TarefaComplexa extends Tarefa {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "tarefa_id") // Cria uma coluna 'tarefa_id' lá na tabela Etapa
    private List<Etapa> etapas = new ArrayList<>();
}