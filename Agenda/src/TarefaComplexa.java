import java.util.ArrayList;

public class TarefaComplexa extends Tarefa {

  // Usa ArrayList em vez de array fixo
  private ArrayList<Etapa> etapas = new ArrayList<>();

  public TarefaComplexa(String desc) {
    super(desc);
  }

  /**
   * Retorna uma String formatada de todas as etapas.
   */
  public String getEtapasComoString() {
    if (etapas.isEmpty()) {
      return "  (Nenhuma etapa cadastrada)\n";
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < etapas.size(); i++) {
      sb.append("  - Etapa " + (i + 1) + ": " + etapas.get(i).getDesc() + "\n");
    }
    return sb.toString();
  }

  @Override
  public void exibirDetalhes() {
    System.out.println("Tipo: Tarefa Complexa");
    System.out.println("Descrição: " + this.getDesc());
    System.out.print(getEtapasComoString()); // Reutiliza o método
    System.out.println();
  }

  public void incluirEtapa(Etapa etapa) {
    this.etapas.add(etapa);
    System.out.println("Etapa cadastrada com sucesso.\n");
  }

  public void excluirEtapa(int i) {
    if (i >= 0 && i < etapas.size()) {
      etapas.remove(i);
      System.out.println("Etapa removida com sucesso.\n");
    } else {
      System.out.println("Erro: Posição da etapa inválida.\n");
    }
  }

  public int getQtdEtapas() {
    return etapas.size();
  }
}
