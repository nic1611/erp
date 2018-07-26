package exercicio1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Principal {
	static int varInt;
	static Integer var;
	JToggleButton criarTabela = null;
	JToggleButton inserirValores = null;
	JToggleButton selecionarDados = null;
	JToggleButton seta = null;
	JComboBox<String> op = null;

	
		public static void main(String[] args) {		
				new Principal().criarInterface();
		}
		private void criarInterface(){
			criarTabela = new JToggleButton("Criar");
			inserirValores = new JToggleButton("Inserir");
			selecionarDados = new JToggleButton("Selecionar");
			seta = new JToggleButton(">Set Tabela");
			op = new JComboBox<String>();
			
			op.addItem("Cargo");
			op.addItem("Trabalha");
			op.addItem("Endereco");
			op.addItem("Funcionario");
			op.addItem("Projeto");
			op.addItem("Departamento");
			op.addItem("Dependente");
			
			JPanel painel = new JPanel();
			JPanel painel1 = new JPanel();
			painel1.setBackground(Color.gray);
			painel.setBackground(Color.darkGray);
			painel1.add(op);
			painel1.add(seta);
			painel.add(criarTabela);
			painel.add(inserirValores);
			painel.add(selecionarDados);
			
			JFrame tela = new JFrame("Meu Banco");
			tela.add(BorderLayout.NORTH, painel1);
			tela.add(BorderLayout.SOUTH, painel);
			tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			tela.setSize(400, 100);
			tela.setVisible(true);
			tela.setLocationRelativeTo(null);
			
			
			criarTabela.addActionListener(e -> processarCriaTabela());
			inserirValores.addActionListener(e -> processarInserirValores());
			selecionarDados.addActionListener(e -> processarSelecionarDados());
			seta.addActionListener(e -> seta());
		}
		
		private void processarCriaTabela (){
			AbstractPersistente []obj = new AbstractPersistente[7];
			try {
				obj[0] = new Cargo();
				obj[1] = new Trabalha();
				obj[2] = new Endereco();
				obj[3] = new Funcionarios();
				obj[4] = new Projeto();
				obj[5] = new Departamento();
				obj[6] = new Dependente();
				Controller.criarTabela(obj, varInt);
			} catch (SQLException e) {
				if (e.getErrorCode() == 1050) {
					JOptionPane.showConfirmDialog(null, "Tabela ja existe");
				}
			}
		}
		
		private void processarInserirValores(){
			AbstractPersistente []obj = new AbstractPersistente[7];
			try {
				obj[0] = new Cargo();
				obj[1] = new Trabalha();
				obj[2] = new Endereco();
				obj[3] = new Funcionarios();
				obj[4] = new Projeto();
				obj[5] = new Departamento();
				obj[6] = new Dependente();
				Controller.inserirValores(obj, varInt);
			} catch (SQLException e) {
				System.out.println("Deu ruim");
				if (e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
					JOptionPane.showConfirmDialog(null, "Valores duplicados");
				}
			}
		}
		
		private void processarSelecionarDados(){
			AbstractPersistente []obj = new AbstractPersistente[7];
			try {
				obj[0] = new Cargo();
				obj[1] = new Trabalha();
				obj[2] = new Endereco();
				obj[3] = new Funcionarios();
				obj[4] = new Projeto();
				obj[5] = new Departamento();
				obj[6] = new Dependente();
				Controller.selecionarDados(obj, varInt);
			} catch (SQLException e) {
				System.out.println("Deu ruim");
				if (e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {

				}
				e.printStackTrace();
			}
		}
		
		private void seta(){
			var = op.getSelectedIndex();
			varInt = var;
			System.out.println(varInt);
		}


}
