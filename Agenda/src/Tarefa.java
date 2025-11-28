public abstract class Tarefa {

  private String desc;

  public Tarefa(String desc) {
    this.desc = desc;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public abstract void exibirDetalhes();
}
