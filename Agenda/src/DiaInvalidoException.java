public class DiaInvalidoException extends Exception {

  public DiaInvalidoException(int maxDias) {
    super("Dia inválido. Insira um valor entre 1 e " + maxDias + ".\n");
  }
}
