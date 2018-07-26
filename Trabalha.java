package exercicio1;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class Trabalha extends AbstractPersistente {
	String comando = null;
	JTextField texto1, texto2, texto3 = null;
	private int codFuncionario, horasTrabalhadas, nrProjeto;

	public int getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(int codFuncionario) {
		this.codFuncionario = codFuncionario;
	}

	public int getHorasTrabalhadas() {
		return horasTrabalhadas;
	}

	public void setHorasTrabalhadas(int horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}

	public int getNrProjeto() {
		return nrProjeto;
	}

	public void setNrProjeto(int nrProjeto) {
		this.nrProjeto = nrProjeto;
	}

	@Override
	protected void criaTabela() throws SQLException {
		String comando = null;
		comando = "create table trabalha (codFuncionario int , " + "horasTrabalhadas int , " + "nrProjeto int , "
				+ " primary key (codFuncionario) ) ";
		super.criaTabela(comando);
	}

	@Override
	protected void inserirValores() throws SQLException {
		new Thread(new Runnable() {
			@SuppressWarnings("resource")
			@Override
			public void run() {
				try {
					JFrame tela = new JFrame("Inserir");
					JPanel painel = new JPanel();
					JToggleButton botao = new JToggleButton("Inserir");
					texto1 = new JTextField("Digite o codigo");
					texto2 = new JTextField("Digite as Hoas Trabalhadas");
					texto3 = new JTextField("Digite o numero do Projeto");
					painel.add(botao);
					painel.add(texto1);
					painel.add(texto2);
					painel.add(texto3);
					painel.setBackground(Color.DARK_GRAY);
					tela.add(painel);
					tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					tela.setSize(600, 200);
					tela.setLocationRelativeTo(null);
					tela.setVisible(true);
					botao.addActionListener(e -> {
						setCodFuncionario(Integer.parseInt(texto1.getText()));
						setHorasTrabalhadas(Integer.valueOf(texto2.getText()));
						setNrProjeto(Integer.valueOf(texto3.getText()));
						comando = "insert into `trabalha`(`codFuncionario`,`horasTrabalhadas`,`nrProjeto`)" + "values ("
								+ getCodFuncionario() + ", '" + getHorasTrabalhadas() + "' , '" + getNrProjeto()
								+ "');";
						Connection tabCon = obterConexao();
						if (tabCon != null) {
							Statement stmt;
							try {
								stmt = tabCon.createStatement();
								stmt.executeUpdate(comando);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						} else {
							System.out.println("Problemas na conexao.");
						}
					});
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	protected void selecionarDados() throws SQLException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String comando = null;
					JTable table = new JTable();
					List<Trabalha> lista = new ArrayList<Trabalha>();
					Trabalha trabalha = new Trabalha();
					JFrame janela = new JFrame("Tabela");
					comando = "select codFuncionario, horasTrabalhadas, nrProjeto from trabalha;";
					Connection tabCon = obterConexao();
					if (tabCon != null) {
						Statement stmt = tabCon.createStatement();
						ResultSet rs = stmt.executeQuery(comando);
						while (rs.next()) {
							trabalha = new Trabalha();
							trabalha.setCodFuncionario(rs.getInt("codFuncionario"));
							trabalha.setHorasTrabalhadas(rs.getInt("horasTrabalhadas"));
							trabalha.setNrProjeto(rs.getInt(rs.getInt("nrProjeto")));
							lista.add(trabalha);
						}
						TrabalhaTableModel ntm = new TrabalhaTableModel(lista);
						table.setModel(ntm);

						JScrollPane scroll = new JScrollPane(table);
						scroll.add(table);
						janela.add(table);
						janela.setSize(500, 150);
						janela.setVisible(true);

						stmt.close();
						tabCon.close();
						rs.close();
					} else {
						System.out.println("Problemas na conexao.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
