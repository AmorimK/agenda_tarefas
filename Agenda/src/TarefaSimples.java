public class TarefaSimples extends Tarefa {

  public TarefaSimples(String desc) {
    super(desc);
  }

  @Override
  public void exibirDetalhes() {
    System.out.println("Tipo: Tarefa Simples");
    System.out.println("Descrição: " + this.getDesc() + "\n");
  }
}
