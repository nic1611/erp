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

public class Funcionarios extends AbstractPersistente {
	String comando = null;
	JTextField texto1, texto2, texto3, texto4, texto5, texto6, texto7, texto8 = null;
	private int codFuncionario, codCargo, nrDepto, codSupervidor;
	private String sexo, nome, dtNascimento;
	private double salario;

	public int getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(int codFuncionario) {
		this.codFuncionario = codFuncionario;
	}

	public int getCodCargo() {
		return codCargo;
	}

	public void setCodCargo(int codCargo) {
		this.codCargo = codCargo;
	}

	public int getNrDepto() {
		return nrDepto;
	}

	public void setNrDepto(int nrDepto) {
		this.nrDepto = nrDepto;
	}

	public int getCodSupervidor() {
		return codSupervidor;
	}

	public void setCodSupervidor(int codSupervidor) {
		this.codSupervidor = codSupervidor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	@Override
	protected void criaTabela() throws SQLException {
		String comando = null;
		comando = "create table funcionarios (codFuncionario int , " + "codCargo int , " + "nrDepto int , "
				+ "codSupervidor int , " + "sexo varchar(32) , " + "nome varchar(32) , " + "dtNascimento varchar(32) , "
				+ "salario double , " + " primary key (codFuncionario) ) ";
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
					texto2 = new JTextField("Digite o cod Cargo");
					texto3 = new JTextField("Digite a nr Departamento");
					texto4 = new JTextField("Digite o cod Supervisor");
					texto5 = new JTextField("Digite o sexo");
					texto6 = new JTextField("Digite 0 nome");
					texto7 = new JTextField("Digite a dt Nascimento");
					texto8 = new JTextField("Digite o salario");
					painel.add(botao);
					painel.add(texto1);
					painel.add(texto2);
					painel.add(texto3);
					painel.add(texto4);
					painel.add(texto5);
					painel.add(texto6);
					painel.add(texto7);
					painel.add(texto8);
					painel.setBackground(Color.DARK_GRAY);
					tela.add(painel);
					tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					tela.setSize(600, 400);
					tela.setLocationRelativeTo(null);
					tela.setVisible(true);
					botao.addActionListener(e -> {
						setCodFuncionario(Integer.parseInt(texto1.getText()));
						setCodCargo(Integer.parseInt(texto2.getText()));
						setNrDepto(Integer.parseInt(texto3.getText()));
						setCodSupervidor(Integer.parseInt(texto4.getText()));
						setSexo(String.valueOf(texto5.getText()));
						setNome(String.valueOf(texto6.getText()));
						setDtNascimento(String.valueOf(texto7.getText()));
						setSalario(Double.parseDouble(texto8.getText()));
						comando = "insert into `funcionarios`(`codFuncionario`,`codCargo`,`nrDepto`,`codSupervidor`,`sexo`,`nome`,`dtNascimento`,`salario`)"
								+ "values (" + getCodFuncionario() + " , " + getCodCargo() + " , " + getNrDepto() + ", "
								+ getCodSupervidor() + " , '" + getSexo() + "' , '" + getNome() + "' , '"
								+ getDtNascimento() + "' , " + getSalario() + ");";
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
					List<Funcionarios> lista = new ArrayList<Funcionarios>();
					Funcionarios funcionarios = new Funcionarios();
					JFrame janela = new JFrame("Tabela");
					comando = "select codFuncionario, codCargo, nrDepto, codSupervidor, sexo, nome, dtNascimento, salario from funcionarios;";
					Connection tabCon = obterConexao();
					if (tabCon != null) {
						Statement stmt = tabCon.createStatement();
						ResultSet rs = stmt.executeQuery(comando);
						while (rs.next()) {
							funcionarios = new Funcionarios();
							funcionarios.setCodFuncionario(rs.getInt("codFuncionario"));
							funcionarios.setCodCargo(rs.getInt("codCargo"));
							funcionarios.setNrDepto(rs.getInt("nrDepto"));
							funcionarios.setCodSupervidor(rs.getInt("codSupervidor"));
							funcionarios.setSexo(rs.getString("sexo"));
							funcionarios.setNome(rs.getString("nome"));
							funcionarios.setDtNascimento(rs.getString("dtNascimento"));
							funcionarios.setSalario(rs.getDouble("salario"));
							lista.add(funcionarios);
						}
						FuncionariosTableModel ntm = new FuncionariosTableModel(lista);
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
