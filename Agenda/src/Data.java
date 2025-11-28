import java.util.ArrayList;

public class Data {

  private ArrayList<TarefaSimples> tarefaS = new ArrayList<>();
  private ArrayList<TarefaComplexa> tarefaC = new ArrayList<>();

  public Data() {}

  public int getQtdS() {
    return this.tarefaS.size();
  }

  public int getQtdC() {
    return this.tarefaC.size();
  }

  public TarefaSimples getTarefaSimples(int index) {
    if (index >= 0 && index < tarefaS.size()) {
      return tarefaS.get(index);
    }
    return null;
  }

  public TarefaComplexa getTarefaComplexa(int index) {
    if (index >= 0 && index < tarefaC.size()) {
      return tarefaC.get(index);
    }
    return null;
  }

  /**
   * Retorna String formatada de tarefas simples (SRP)
   */
  public String getTarefasSimplesComoString() {
    if (tarefaS.isEmpty()) {
      return "Nenhuma tarefa simples cadastrada.\n";
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < tarefaS.size(); i++) {
      sb.append("Tarefa " + (i + 1) + " - " + tarefaS.get(i).getDesc() + "\n");
    }
    return sb.toString();
  }

  /**
   * Retorna String formatada de tarefas complexas (SRP)
   */
  public String getTarefasComplexasComoString() {
    if (tarefaC.isEmpty()) {
      return "Nenhuma tarefa complexa cadastrada.\n";
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < tarefaC.size(); i++) {
      TarefaComplexa t = tarefaC.get(i);
      sb.append("Tarefa " + (i + 1) + " - " + t.getDesc() + "\n");
      sb.append(t.getEtapasComoString()); // Reutiliza o método da tarefa
    }
    return sb.toString();
  }

  public void incluirTarefaSimples(TarefaSimples tarefa) {
    tarefaS.add(tarefa);
    System.out.println("Tarefa simples cadastrada com sucesso.\n");
  }

  public void incluirTarefaComplexa(TarefaComplexa tarefa) {
    tarefaC.add(tarefa);
    System.out.println("Tarefa complexa cadastrada com sucesso.\n");
  }

  public void delTarefaSimples(int i) {
    if (i >= 0 && i < tarefaS.size()) {
      tarefaS.remove(i);
      System.out.println("Tarefa simples deletada com sucesso.\n");
    } else {
      System.out.println("Erro: Posição da tarefa inválida.\n");
    }
  }

  public void delTarefaComplexa(int i) {
    if (i >= 0 && i < tarefaC.size()) {
      tarefaC.remove(i);
      System.out.println("Tarefa complexa deletada com sucesso.\n");
    } else {
      System.out.println("Erro: Posição da tarefa inválida.\n");
    }
  }

  public void altTarefaComplexa(int posT, TarefaComplexa tarefa) {
    if (posT >= 0 && posT < tarefaC.size()) {
      tarefaC.set(posT, tarefa);
      System.out.println("Alterada com sucesso.\n");
    } else {
      System.out.println("Erro: Posição da tarefa inválida.\n");
    }
  }

  public void altTarefaSimples(int posT, TarefaSimples tarefa) {
    if (posT >= 0 && posT < tarefaS.size()) {
      tarefaS.set(posT, tarefa);
      System.out.println("Alterada com sucesso.\n");
    } else {
      System.out.println("Erro: Posição da tarefa inválida.\n");
    }
  }

  public String buscarPorPalavraChave(String palavraChave) {
    StringBuilder sb = new StringBuilder();

    for (TarefaSimples ts : tarefaS) {
      if (ts.getDesc().toLowerCase().contains(palavraChave)) {
        sb.append("Tipo: Tarefa Simples\nDescrição: " + ts.getDesc() + "\n\n");
      }
    }

    for (TarefaComplexa tc : tarefaC) {
      if (tc.getDesc().toLowerCase().contains(palavraChave)) {
        sb.append("Tipo: Tarefa Complexa\nDescrição: " + tc.getDesc() + "\n");
        sb.append(tc.getEtapasComoString() + "\n");
      }
    }

    return sb.toString();
  }
}
