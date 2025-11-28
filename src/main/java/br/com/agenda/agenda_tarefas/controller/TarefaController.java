package br.com.agenda.agenda_tarefas.controller;

import br.com.agenda.agenda_tarefas.model.Etapa;
import br.com.agenda.agenda_tarefas.model.Tarefa;
import br.com.agenda.agenda_tarefas.model.TarefaComplexa;
import br.com.agenda.agenda_tarefas.model.TarefaSimples;
import br.com.agenda.agenda_tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository repository;

    // 1. Listar
    @GetMapping
    public String listarTarefas(@RequestParam(required = false) String busca, Model model) {
        if (busca != null && !busca.isEmpty()) {
            model.addAttribute("listaTarefas", repository.findByDescricaoContainingIgnoreCase(busca));
        } else {
            model.addAttribute("listaTarefas", repository.findAll());
        }
        return "tarefas";
    }

    // 2. Salvar (Lógica unificada para Simples ou Complexa)
    @PostMapping("/salvar")
    public String salvarTarefa(@RequestParam String descricao,
                               @RequestParam String dataExecucao,
                               @RequestParam String tipo) {
        Tarefa tarefa;
        if ("COMPLEXA".equals(tipo)) {
            tarefa = new TarefaComplexa();
        } else {
            tarefa = new TarefaSimples();
        }

        tarefa.setDescricao(descricao);
        tarefa.setDataExecucao(java.time.LocalDate.parse(dataExecucao));

        repository.save(tarefa);
        return "redirect:/tarefas";
    }

    // 3. Ver Detalhes (Para gerenciar Etapas da Tarefa Complexa)
    @GetMapping("/{id}")
    public String detalhesTarefa(@PathVariable Long id, Model model) {
        Optional<Tarefa> tarefaOpt = repository.findById(id);

        if (tarefaOpt.isPresent()) {
            Tarefa tarefa = tarefaOpt.get();
            model.addAttribute("tarefa", tarefa);

            // Se for complexa, mostramos a tela de etapas
            if (tarefa instanceof TarefaComplexa) {
                return "detalhes-complexa";
            }
        }
        return "redirect:/tarefas";
    }

    // 4. Adicionar Etapa (Funcionalidade exclusiva da Tarefa Complexa)
    @PostMapping("/{id}/adicionar-etapa")
    public String adicionarEtapa(@PathVariable Long id, @RequestParam String descricaoEtapa) {
        Optional<Tarefa> tarefaOpt = repository.findById(id);

        if (tarefaOpt.isPresent() && tarefaOpt.get() instanceof TarefaComplexa) {
            TarefaComplexa tc = (TarefaComplexa) tarefaOpt.get();

            Etapa novaEtapa = new Etapa();
            novaEtapa.setDescricao(descricaoEtapa);

            tc.getEtapas().add(novaEtapa);
            repository.save(tc); // Salva a tarefa e a etapa (Cascade)
        }
        return "redirect:/tarefas/" + id;
    }

    // 5. Remover Etapa
    @GetMapping("/{idTarefa}/remover-etapa/{index}")
    public String removerEtapa(@PathVariable Long idTarefa, @PathVariable int index) {
        Optional<Tarefa> tarefaOpt = repository.findById(idTarefa);

        if (tarefaOpt.isPresent() && tarefaOpt.get() instanceof TarefaComplexa) {
            TarefaComplexa tc = (TarefaComplexa) tarefaOpt.get();
            if (index >= 0 && index < tc.getEtapas().size()) {
                tc.getEtapas().remove(index);
                repository.save(tc);
            }
        }
        return "redirect:/tarefas/" + idTarefa;
    }

    @PostMapping("/{idTarefa}/etapas/{idEtapa}/toggle")
    public String toggleEtapa(@PathVariable Long idTarefa, @PathVariable Long idEtapa) {
        Optional<Tarefa> tarefaOpt = repository.findById(idTarefa);

        if (tarefaOpt.isPresent() && tarefaOpt.get() instanceof TarefaComplexa) {
            TarefaComplexa tc = (TarefaComplexa) tarefaOpt.get();

            for (Etapa etapa : tc.getEtapas()) {
                if (etapa.getId().equals(idEtapa)) {
                    etapa.setConcluida(!etapa.isConcluida());
                    break;
                }
            }
            repository.save(tc); // Salva a tarefa completa com a etapa atualizada
        }

        return "redirect:/tarefas";
    }

    @GetMapping("/deletar")
    public String deletarTarefa(@RequestParam Long id) {
        repository.deleteById(id);
        return "redirect:/tarefas";
    }
}