package exercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractPersistente {
	protected abstract void criaTabela() throws SQLException;
	protected abstract void inserirValores() throws SQLException;
	protected abstract void selecionarDados() throws SQLException;

	protected void criaTabela(String comando) throws SQLException {
		Connection tabCon = obterConexao();
		if (tabCon != null) {
			Statement stmt = tabCon.createStatement();
			stmt.executeUpdate(comando);
			stmt.close();
			tabCon.close();
		} else {
			System.out.println("Problemas na conex�o.");
		}
	}
	protected void inserirValores(String comando) throws SQLException {
	}
	
	protected void selecionarDados(String comando) throws SQLException{
	}

	public Connection obterConexao() {
		// MinhaConexao obj = new MinhaConexao();
		// return obj.obterConexao();
		String usuario = "root";
		String senha = "nico";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver nao disponivel.");
		}
		String urlMysql = "jdbc:mysql://localhost:3306/meubanco?createDatabaseIfNotExist=true";
		try {
			Connection conexao = DriverManager.getConnection(urlMysql, usuario,
					senha);
			return conexao;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("URL, usuario ou senha invalidos.");
		}
		return null;
	}

}
