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

public class Dependente extends AbstractPersistente {
	String comando = null;
	JTextField texto1, texto2, texto3, texto4, texto5, texto6 = null;
	private int codFuncionario, codDependente;
	private String nome, parentesco, sexo, dtNascimento;

	public int getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(int codFuncionario) {
		this.codFuncionario = codFuncionario;
	}

	public int getCodDependente() {
		return codDependente;
	}

	public void setCodDependente(int codDependente) {
		this.codDependente = codDependente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	@Override
	protected void criaTabela() throws SQLException {
		String comando = null;
		comando = "create table dependente (codFuncionario int , " + "codDependente int , " + "nome varchar(32) , "
				+ "parentesco varchar(32) , " + "sexo varchar(32) , " + "dtNascimento varchar(32) , "
				+ " primary key (codDependente) ) ";
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
					texto1 = new JTextField("Digite o cod funcionario");
					texto2 = new JTextField("Digite o cod dependente");
					texto3 = new JTextField("Digite o nome");
					texto4 = new JTextField("Digite o parentesco");
					texto5 = new JTextField("Digite o sexo");
					texto6 = new JTextField("Digite a dt nascimento");
					painel.add(botao);
					painel.add(texto1);
					painel.add(texto2);
					painel.add(texto3);
					painel.add(texto4);
					painel.add(texto5);
					painel.add(texto6);
					painel.setBackground(Color.DARK_GRAY);
					tela.add(painel);
					tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					tela.setSize(600, 200);
					tela.setLocationRelativeTo(null);
					tela.setVisible(true);
					botao.addActionListener(e -> {
						setCodFuncionario(Integer.parseInt(texto1.getText()));
						setCodDependente(Integer.parseInt(texto2.getText()));
						setNome(String.valueOf(texto3.getText()));
						setParentesco(String.valueOf(texto4.getText()));
						setSexo(String.valueOf(texto5.getText()));
						setDtNascimento(String.valueOf(texto6.getText()));
						comando = "insert into `dependente`(`codFuncionario`,`codDependente`,`nome`,`parentesco`,`sexo`,`dtNascimento`)"
								+ "values (" + getCodFuncionario() + ", " + getCodDependente() + ", '" + getNome()
								+ "' , '" + getParentesco() + "' , '" + getSexo() + "' , '" + getDtNascimento() + "');";
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
					List<Dependente> lista = new ArrayList<Dependente>();
					Dependente dependente = new Dependente();
					JFrame janela = new JFrame("Tabela");
					comando = "select codFuncionario, codDependente, nome, parentesco, sexo, dtNascimento from dependente;";
					Connection tabCon = obterConexao();
					if (tabCon != null) {
						Statement stmt = tabCon.createStatement();
						ResultSet rs = stmt.executeQuery(comando);
						while (rs.next()) {
							dependente = new Dependente();
							dependente.setCodFuncionario(rs.getInt("codFuncionario"));
							dependente.setCodDependente(rs.getInt("codDependente"));
							dependente.setNome(rs.getString("nome"));
							dependente.setParentesco(rs.getString("parentesco"));
							dependente.setSexo(rs.getString("sexo"));
							dependente.setDtNascimento(rs.getString("dtNascimento"));
							lista.add(dependente);
						}
						DependenteTableModel ntm = new DependenteTableModel(lista);
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
