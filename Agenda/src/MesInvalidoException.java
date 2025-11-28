public class MesInvalidoException extends Exception {
	public MesInvalidoException() {
        super("Mês inválido. Insira um valor entre 1 e 12.\n");
    }
}
