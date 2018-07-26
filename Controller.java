package exercicio1;

import java.sql.SQLException;

public class Controller {
	public static void criarTabela(AbstractPersistente[] obj, int varInt) throws SQLException {
		obj[varInt].criaTabela();
	}

	public static void inserirValores(AbstractPersistente[] obj, int varInt) throws SQLException {
		obj[varInt].inserirValores();

	}

	public static void selecionarDados(AbstractPersistente[] obj, int varInt) throws SQLException {
		obj[varInt].selecionarDados();

	}

}
