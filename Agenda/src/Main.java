import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    // Scanner é criado aqui e passado como parâmetro
    Scanner ent = new Scanner(System.in);

    try {
      System.out.println("Bem-vindo à Agenda de Tarefas!");

      int mes = lerInteiro(ent, "Insira o mês referente a Agenda (1-12):");
      validarMes(mes);

      int ano = lerInteiro(ent, "Insira o ano referente a Agenda (ex: 2024):");
      validarAno(ano);

      Agenda agenda = new Agenda(mes, ano);
      System.out.println(
        "Agenda para " +
          mes +
          "/" +
          ano +
          " criada com " +
          agenda.getDiasNoMes() +
          " dias.\n"
      );

      int opt;
      do {
        opt = menu(ent);

        switch (opt) {
          case 1:
            cadastrarTarefa(ent, agenda);
            break;
          case 2:
            alterarTarefa(ent, agenda);
            break;
          case 3:
            excluirTarefa(ent, agenda);
            break;
          case 4:
            listarTodasTarefas(agenda);
            break;
          case 5:
            buscar(ent, agenda);
            break;
          case 0:
            System.out.println("Encerrado.\n");
            break;
        }
      } while (opt != 0);
    } catch (
      OpcaoInvalidaException
      | MesInvalidoException
      | AnoInvalidoException
      | TipoTarefaInvalidoException
      | DiaInvalidoException e
    ) {
      // Captura todas as exceções de validação de forma limpa
      System.out.println("Erro de Validação: " + e.getMessage());
    } catch (Exception e) {
      // Captura outros erros inesperados
      System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
      e.printStackTrace(); // Ajuda a debugar
    } finally {
      ent.close();
    }
  }

  // --- Métodos de Menu ---

  private static void cadastrarTarefa(Scanner ent, Agenda agenda)
    throws DiaInvalidoException, TipoTarefaInvalidoException {
    int tipo = lerInteiro(
      ent,
      "Insira o tipo de tarefa:\n1 - Simples\n2 - Complexa"
    );
    validarTipoTarefa(tipo);

    int maxDias = agenda.getDiasNoMes();
    int dia = lerInteiro(ent, "Insira o dia (1-" + maxDias + "):");
    validarDia(dia, maxDias);

    // Cria o objeto Data se ele ainda não existir para esse dia
    if (!agenda.checkData(dia)) {
      agenda.setData(dia, new Data());
    }
    Data dataDia = agenda.getData(dia);

    if (tipo == 1) {
      TarefaSimples ts = criarTarefaSimplesPeloUsuario(ent);
      dataDia.incluirTarefaSimples(ts);
    } else {
      TarefaComplexa tc = criarTarefaComplexaPeloUsuario(ent);
      dataDia.incluirTarefaComplexa(tc);
    }
  }

  private static void alterarTarefa(Scanner ent, Agenda agenda)
    throws DiaInvalidoException, TipoTarefaInvalidoException {
    int tipo = lerInteiro(
      ent,
      "Selecione o tipo de tarefa a mudar:\n1 - Tarefa Simples\n2 - Tarefa Complexa"
    );
    validarTipoTarefa(tipo);

    int maxDias = agenda.getDiasNoMes();
    int diaT = lerInteiro(
      ent,
      "Insira o dia em que a tarefa se encontra (1-" + maxDias + "):"
    );
    validarDia(diaT, maxDias);

    Data tarefaDia = agenda.getData(diaT);
    if (tarefaDia == null) {
      System.out.println("Não há tarefas a serem alteradas neste dia.\n");
      return;
    }

    if (tipo == 1) {
      System.out.println("Tarefas Simples Listadas:\n");
      System.out.println(tarefaDia.getTarefasSimplesComoString());

      if (tarefaDia.getQtdS() == 0) return; // Sai se não houver

      int posT =
        lerInteiro(
          ent,
          "Selecione a tarefa simples a ser alterada (pelo número):"
        ) -
        1;

      // Validação de Índice
      if (posT < 0 || posT >= tarefaDia.getQtdS()) {
        System.out.println("Erro: Posição da tarefa é inválida.\n");
      } else {
        TarefaSimples novaTarefaS = criarTarefaSimplesPeloUsuario(ent);
        tarefaDia.altTarefaSimples(posT, novaTarefaS);
      }
    } else {
      System.out.println("Tarefas Complexas Listadas:\n");
      System.out.println(tarefaDia.getTarefasComplexasComoString());

      if (tarefaDia.getQtdC() == 0) return; // Sai se não houver

      int posT =
        lerInteiro(
          ent,
          "Selecione a tarefa complexa a ser alterada (pelo número):"
        ) -
        1;

      // Validação de Índice
      if (posT < 0 || posT >= tarefaDia.getQtdC()) {
        System.out.println("Erro: Posição da tarefa é inválida.\n");
      } else {
        TarefaComplexa novaTarefaC = criarTarefaComplexaPeloUsuario(ent);
        tarefaDia.altTarefaComplexa(posT, novaTarefaC);
      }
    }
  }

  private static void excluirTarefa(Scanner ent, Agenda agenda)
    throws DiaInvalidoException, TipoTarefaInvalidoException {
    int tipo = lerInteiro(
      ent,
      "Selecione o tipo de tarefa a ser deletada:\n1 - Tarefa Simples\n2 - Tarefa Complexa"
    );
    validarTipoTarefa(tipo);

    int maxDias = agenda.getDiasNoMes();
    int diaT = lerInteiro(
      ent,
      "Insira o dia em que a tarefa se encontra (1-" + maxDias + "):"
    );
    validarDia(diaT, maxDias);

    Data tarefaDia = agenda.getData(diaT);
    if (tarefaDia == null) {
      System.out.println("Não há tarefas a serem excluídas neste dia.\n");
      return;
    }

    if (tipo == 1) {
      System.out.println("Tarefas Simples Listadas:\n");
      System.out.println(tarefaDia.getTarefasSimplesComoString());

      if (tarefaDia.getQtdS() == 0) return;

      int posT =
        lerInteiro(
          ent,
          "Selecione a tarefa simples a ser deletada (pelo número):"
        ) -
        1;

      tarefaDia.delTarefaSimples(posT);
    } else {
      System.out.println("Tarefas Complexas Listadas:\n");
      System.out.println(tarefaDia.getTarefasComplexasComoString());

      if (tarefaDia.getQtdC() == 0) return;

      int posT =
        lerInteiro(
          ent,
          "Selecione a tarefa complexa a ser deletada (pelo número):"
        ) -
        1;

      tarefaDia.delTarefaComplexa(posT);
    }
  }

  private static void listarTodasTarefas(Agenda agenda) {
    System.out.println(agenda.getTodasTarefasComoString());
  }

  private static void buscar(Scanner ent, Agenda agenda)
    throws DiaInvalidoException {
    int opt = lerInteiro(
      ent,
      "Selecione o tipo de busca:\n1 - Por Dia\n2 - Por Palavra-chave"
    );

    if (opt == 1) {
      int maxDias = agenda.getDiasNoMes();
      int dia = lerInteiro(ent, "Escolha o dia desejado (1-" + maxDias + "):");
      validarDia(dia, maxDias);

      Data diaEscolhido = agenda.getData(dia);
      if (diaEscolhido == null) {
        System.out.println("Não há tarefas a serem exibidas para este dia.\n");
      } else {
        System.out.println("Tarefas Simples:\n");
        System.out.println(diaEscolhido.getTarefasSimplesComoString());
        System.out.println("Tarefas Complexas:\n");
        System.out.println(diaEscolhido.getTarefasComplexasComoString());
      }
    } else if (opt == 2) {
      String palavraChave = lerString(
        ent,
        "Insira a palavra-chave:"
      ).toLowerCase();
      StringBuilder resultados = new StringBuilder();
      boolean encontrou = false;

      for (int dia = 1; dia <= agenda.getDiasNoMes(); dia++) {
        if (agenda.checkData(dia)) {
          Data diaAtual = agenda.getData(dia);
          String tarefasEncontradas = diaAtual.buscarPorPalavraChave(
            palavraChave
          );

          if (!tarefasEncontradas.isEmpty()) {
            encontrou = true;
            resultados.append("--- Dia " + dia + " ---\n");
            resultados.append(tarefasEncontradas);
          }
        }
      }

      if (!encontrou) {
        System.out.println(
          "Nenhuma tarefa encontrada com a palavra-chave: " +
            palavraChave +
            "\n"
        );
      } else {
        System.out.println(resultados.toString());
      }
    } else {
      System.out.println("Opção de busca inválida.\n");
    }
  }

  // --- Métodos Auxiliares (Helpers) ---

  /**
   * Método auxiliar para criar Tarefa Simples (DRY)
   */
  private static TarefaSimples criarTarefaSimplesPeloUsuario(Scanner ent) {
    String desc = lerString(ent, "Insira a descrição da tarefa simples:");
    return new TarefaSimples(desc);
  }

  /**
   * Método auxiliar para criar Tarefa Complexa (DRY)
   */
  private static TarefaComplexa criarTarefaComplexaPeloUsuario(Scanner ent) {
    String desc = lerString(ent, "Insira a descrição da tarefa complexa:");
    TarefaComplexa tarefaC = new TarefaComplexa(desc);

    int opcao;
    do {
      opcao = lerInteiro(ent, "Deseja cadastrar nova Etapa?\n1 - Sim\n2 - Não");
      if (opcao == 1) {
        String etapadesc = lerString(ent, "Insira a descrição da etapa:");
        tarefaC.incluirEtapa(new Etapa(etapadesc));
      }
    } while (opcao != 2);

    return tarefaC;
  }

  /**
   * Método auxiliar robusto para ler Strings
   */
  private static String lerString(Scanner ent, String prompt) {
    System.out.println(prompt);
    return ent.nextLine();
  }

  /**
   * Método auxiliar robusto para ler Inteiros
   */
  private static int lerInteiro(Scanner ent, String prompt) {
    while (true) {
      try {
        System.out.println(prompt);
        String input = ent.nextLine();
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println(
          "Erro: Entrada inválida. Por favor, insira um número."
        );
      }
    }
  }

  // --- Métodos de Validação ---

  private static void validarMes(int mes) throws MesInvalidoException {
    if (mes < 1 || mes > 12) {
      throw new MesInvalidoException();
    }
  }

  private static void validarAno(int ano) throws AnoInvalidoException {
    if (ano <= 0) {
      // Simplificado
      throw new AnoInvalidoException();
    }
  }

  private static void validarTipoTarefa(int tipo)
    throws TipoTarefaInvalidoException {
    if (tipo < 1 || tipo > 2) {
      throw new TipoTarefaInvalidoException();
    }
  }

  // Agora recebe o máximo de dias do mês
  private static void validarDia(int dia, int maxDias)
    throws DiaInvalidoException {
    if (dia < 1 || dia > maxDias) {
      throw new DiaInvalidoException(maxDias);
    }
  }

  private static void validarOpcao(int opt) throws OpcaoInvalidaException {
    if (opt < 0 || opt > 5) {
      throw new OpcaoInvalidaException();
    }
  }

  public static int menu(Scanner ent) throws OpcaoInvalidaException {
    System.out.println("\n--- Menu Principal ---");
    System.out.println("1 - Cadastrar Tarefa");
    System.out.println("2 - Alterar Tarefa");
    System.out.println("3 - Excluir Tarefa");
    System.out.println("4 - Listar todas as tarefas");
    System.out.println("5 - Buscar");
    System.out.println("0 - Encerrar sistema");

    int opt = lerInteiro(ent, "\nEscolha uma opção:");
    validarOpcao(opt);

    return opt;
  }
}
