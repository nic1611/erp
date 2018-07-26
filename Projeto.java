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

public class Projeto extends AbstractPersistente {
	String comando = null;
	JTextField texto1, texto2, texto3, texto4 = null;
	private String descricao, nome;
	private int nrDepto, nrProjeto;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNrDepto() {
		return nrDepto;
	}

	public void setNrDepto(int nrDepto) {
		this.nrDepto = nrDepto;
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
		comando = "create table projeto (nrDepto int , " + "nrProjeto int , " + "descricao varchar(32) , "
				+ "nome varchar(32) , " + " primary key (nrProjeto) ) ";
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
					texto1 = new JTextField("Digite o numero do projeto");
					texto2 = new JTextField("Digite o numero do departamento");
					texto3 = new JTextField("Digite a descricao");
					texto4 = new JTextField("Digite o nome");
					painel.add(botao);
					painel.add(texto1);
					painel.add(texto2);
					painel.add(texto3);
					painel.add(texto4);
					painel.setBackground(Color.DARK_GRAY);
					tela.add(painel);
					tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					tela.setSize(600, 200);
					tela.setLocationRelativeTo(null);
					tela.setVisible(true);
					botao.addActionListener(e -> {
						setNrProjeto(Integer.parseInt(texto1.getText()));
						setNrDepto(Integer.parseInt(texto2.getText()));
						setDescricao(String.valueOf(texto3.getText()));
						setNome(String.valueOf(texto4.getText()));
						comando = "insert into `projeto`(`nrProjeto`,`nrDepto`,`descricao`,`nome`)" + "values ("
								+ getNrProjeto() + ", " + getNrDepto() + ", '" + getDescricao() + "' , '" + getNome()
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
					List<Projeto> lista = new ArrayList<Projeto>();
					Projeto projeto = new Projeto();
					JFrame janela = new JFrame("Tabela");
					comando = "select nrProjeto, nrDepto, descricao, nome from projeto;";
					Connection tabCon = obterConexao();
					if (tabCon != null) {
						Statement stmt = tabCon.createStatement();
						ResultSet rs = stmt.executeQuery(comando);
						while (rs.next()) {
							projeto = new Projeto();
							projeto.setNrProjeto(rs.getInt("nrProjeto"));
							projeto.setNrDepto(rs.getInt("nrDepto"));
							projeto.setDescricao(rs.getString("descricao"));
							projeto.setNome(rs.getString("nome"));
							lista.add(projeto);
						}
						ProjetoTableModel ntm = new ProjetoTableModel(lista);
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
