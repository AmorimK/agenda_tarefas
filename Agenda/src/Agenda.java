import java.time.YearMonth; // Importa a classe para verificar dias do mês

public class Agenda {

  private int mes;
  private int ano;
  private Data[] datas; // O array de Datas
  private int diasNoMes;

  public Agenda(int mes, int ano) {
    this.mes = mes;
    this.ano = ano;

    // Calcula dinamicamente o número de dias no mês
    this.diasNoMes = YearMonth.of(ano, mes).lengthOfMonth();

    // Cria o array com o tamanho correto
    this.datas = new Data[this.diasNoMes];
  }

  public int getDiasNoMes() {
    return this.diasNoMes;
  }

  public int getMes() {
    return mes;
  }

  public void setMes(int mes) {
    this.mes = mes;
  }

  public int getAno() {
    return ano;
  }

  public void setAno(int ano) {
    this.ano = ano;
  }

  // Adiciona validação de limites
  public void setData(int dia, Data data) {
    if (dia > 0 && dia <= this.diasNoMes) {
      this.datas[dia - 1] = data;
    }
  }

  // Adiciona validação de limites
  public Data getData(int dia) {
    if (dia > 0 && dia <= this.diasNoMes) {
      return this.datas[dia - 1];
    }
    return null; // Retorna nulo se o dia for inválido
  }

  // Adiciona validação de limites
  public boolean checkData(int dia) {
    if (dia > 0 && dia <= this.diasNoMes) {
      return datas[dia - 1] != null;
    }
    return false;
  }

  /**
   * Retorna String formatada de todas as tarefas (SRP)
   */
  public String getTodasTarefasComoString() {
    StringBuilder sb = new StringBuilder();
    boolean encontrou = false;

    for (int i = 1; i <= this.diasNoMes; i++) {
      if (checkData(i)) {
        encontrou = true;
        Data d = getData(i);
        sb.append("-----------------\n");
        sb.append("Dia " + i + "\n");
        sb.append("-----------------\n");
        sb.append("Tarefas Simples:\n");
        sb.append(d.getTarefasSimplesComoString() + "\n");
        sb.append("Tarefas Complexas:\n");
        sb.append(d.getTarefasComplexasComoString() + "\n");
      }
    }

    if (!encontrou) {
      return "Nenhuma tarefa cadastrada na agenda.\n";
    }
    return sb.toString();
  }
}
