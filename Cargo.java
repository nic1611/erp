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

public class Cargo extends AbstractPersistente {
	String comando = null;
	JTextField texto1, texto2, texto3 = null;
	private int car_codigo;
	private String car_nome, car_descricao;

	public Cargo(int car_codigo, String car_nome, String car_descricao) {
		super();
		this.car_codigo = car_codigo;
		this.car_nome = car_nome;
		this.car_descricao = car_descricao;
	}

	public Cargo() {
		super();
	}

	public int getCar_codigo() {
		return car_codigo;
	}

	public void setCar_codigo(int car_codigo) {
		this.car_codigo = car_codigo;
	}

	public String getCar_nome() {
		return car_nome;
	}

	public void setCar_nome(String car_nome) {
		this.car_nome = car_nome;
	}

	public String getCar_descricao() {
		return car_descricao;
	}

	public void setCar_descricao(String car_descricao) {
		this.car_descricao = car_descricao;
	}

	@Override
	public void criaTabela() throws SQLException {
		String comando = null;
		comando = "create table cargo (car_codigo int , "
				+ "car_nome VARCHAR(32) , " + "car_descricao VARCHAR(32) , "
				+ " primary key (car_codigo) ) ";
		super.criaTabela(comando);
	}



	public void inserirValores() throws SQLException {
		new Thread(new Runnable() {
			@SuppressWarnings("resource")
			@Override
			public void run() {
				try {
					JFrame tela = new JFrame("Inserir");
					JPanel painel = new JPanel();
					JToggleButton botao = new JToggleButton("Inserir");
					texto1 = new JTextField("Digite o codigo");
					texto2 = new JTextField("Digite o nome");
					texto3 = new JTextField("Digite a descricao");
					painel.add(botao);
					painel.add(texto1);
					painel.add(texto2);
					painel.add(texto3);
					painel.setBackground(Color.DARK_GRAY);
					tela.add(painel);
					tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					tela.setSize(400, 100);
					tela.setLocationRelativeTo(null);
					tela.setVisible(true);
					botao.addActionListener(e -> {
						setCar_codigo(Integer.parseInt(texto1.getText()));
						setCar_nome(String.valueOf(texto2.getText()));
						setCar_descricao(String.valueOf(texto3.getText()));
						comando = "insert into `cargo`(`car_codigo`,`car_nome`,`car_descricao`)"
								+ "values ("
								+ getCar_codigo()
								+ ", '"
								+ getCar_nome()
								+ "' , '"
								+ getCar_descricao()
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

	public void selecionarDados() throws SQLException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String comando = null;
					JTable table = new JTable();
					List<Cargo> lista = new ArrayList<Cargo>();
					Cargo cargo = new Cargo();
					JFrame janela = new JFrame("Tabela");
					comando = "select car_codigo, car_nome, car_descricao from cargo;";
					Connection tabCon = obterConexao();
					if (tabCon != null) {
						Statement stmt = tabCon.createStatement();
						ResultSet rs = stmt.executeQuery(comando);
						while (rs.next()) {
							cargo = new Cargo();
							cargo.setCar_codigo(rs.getInt("car_codigo"));
							cargo.setCar_nome(rs.getString("car_nome"));
							cargo.setCar_descricao(rs
									.getString("car_descricao"));
							lista.add(cargo);
						}
						CargoTableModel ntm = new CargoTableModel(lista);
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
