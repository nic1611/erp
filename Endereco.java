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

public class Endereco extends AbstractPersistente {
	String comando = null;
	JTextField texto1, texto2, texto3, texto4, texto5, texto6 = null;
	private int cep, id, numero;
	private String bairro, cidade, rua;

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	@Override
	protected void criaTabela() throws SQLException {
		String comando = null;
		comando = "create table endereco (cep int , " + "id int , " + "numero int , " + "bairro varchar(32) , "
				+ "cidade varchar(32) , " + "rua varchar(32) , " + " primary key (id) ) ";
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
					texto1 = new JTextField("Digite o cep");
					texto2 = new JTextField("Digite o id");
					texto3 = new JTextField("Digite o numero");
					texto4 = new JTextField("Digite o bairro");
					texto5 = new JTextField("Digite a cidade");
					texto6 = new JTextField("Digite a rua");
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
						setCep(Integer.parseInt(texto1.getText()));
						setId(Integer.parseInt(texto2.getText()));
						setNumero(Integer.parseInt(texto3.getText()));
						setBairro(String.valueOf(texto4.getText()));
						setCidade(String.valueOf(texto5.getText()));
						setRua(String.valueOf(texto6.getText()));
						comando = "insert into `endereco`(`cep`,`id`,`numero`,`bairro`,`cidade`,`rua`)" + "values ("
								+ getCep() + " , " + getId() + " , " + getNumero() + " , '" + getBairro() + "' , '"
								+ getCidade() + "' , '" + getRua() + "');";
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
					List<Endereco> lista = new ArrayList<Endereco>();
					Endereco endereco = new Endereco();
					JFrame janela = new JFrame("Tabela");
					comando = "select cep, id, numero, bairro, cidade, rua from endereco;";
					Connection tabCon = obterConexao();
					if (tabCon != null) {
						Statement stmt = tabCon.createStatement();
						ResultSet rs = stmt.executeQuery(comando);
						while (rs.next()) {
							endereco = new Endereco();
							endereco.setCep(rs.getInt("cep"));
							endereco.setId(rs.getInt("id"));
							endereco.setNumero(rs.getInt("numero"));
							endereco.setBairro(rs.getString("bairro"));
							endereco.setCidade(rs.getString("cidade"));
							endereco.setRua(rs.getString("rua"));
							lista.add(endereco);
						}
						EnderecoTableModel ntm = new EnderecoTableModel(lista);
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
