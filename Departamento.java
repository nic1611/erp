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

public class Departamento extends AbstractPersistente{
	String comando = null;
	JTextField texto1, texto2, texto3, texto4 = null;
	private int codGerente, nrDepto;
	private String dtInicio, nome;
	
	public int getCodGerente() {
		return codGerente;
	}
	public void setCodGerente(int codGerente) {
		this.codGerente = codGerente;
	}
	public int getNrDepto() {
		return nrDepto;
	}
	public void setNrDepto(int nrDepto) {
		this.nrDepto = nrDepto;
	}
	public String getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(String dtInicio) {
		this.dtInicio = dtInicio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	protected void criaTabela() throws SQLException {
		String comando = null;
		comando = "create table departamento (codGerente int , "
				+ "nrDepto int , " + "dtInicio varchar(32) , "+"nome varchar(32) , "
				+ " primary key (nrDepto) ) ";
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
					texto1 = new JTextField("Digite o codigo do gerente");
					texto2 = new JTextField("Digite o numero do departamento");
					texto3 = new JTextField("Digite a data de inicio do gerente");
					texto4 = new JTextField("Digite o nome do departamento");
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
						setCodGerente(Integer.parseInt(texto1.getText()));
						setNrDepto(Integer.parseInt(texto2.getText()));
						setDtInicio(String.valueOf(texto3.getText()));
						setNome(String.valueOf(texto4.getText()));
						comando = "insert into `departamento`(`codGerente`,`nrDepto`,`dtInicio`,`nome`)"
								+ "values ("
								+ getCodGerente()
								+ ", '"
								+ getNrDepto()
								+ "' , '"
								+ getDtInicio()
								+ "' , '"
								+ getNome()
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
					List<Departamento> lista = new ArrayList<Departamento>();
					Departamento departamento = new Departamento();
					JFrame janela = new JFrame("Tabela");
					comando = "select codGerente, nrDepto, dtInicio, nome from departamento;";
					Connection tabCon = obterConexao();
					if (tabCon != null) {
						Statement stmt = tabCon.createStatement();
						ResultSet rs = stmt.executeQuery(comando);
						while (rs.next()) {
							departamento = new Departamento();
							departamento.setCodGerente(rs.getInt("codGerente"));
							departamento.setNrDepto(rs.getInt("nrDepto"));
							departamento.setDtInicio(rs
									.getString("dtInicio"));
							departamento.setNome(rs
									.getString("nome"));
							lista.add(departamento);
						}
						DepartamentoTableModel ntm = new DepartamentoTableModel(lista);
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
