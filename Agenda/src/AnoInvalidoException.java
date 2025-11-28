public class AnoInvalidoException extends Exception {
	public AnoInvalidoException() {
        super("Ano inválido. Insira um valor maior que 0.\n");
    }
}
