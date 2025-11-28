package br.com.agenda.agenda_tarefas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private boolean concluida = false;
}