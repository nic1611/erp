package exercicio1;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FuncionariosTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Funcionarios> funcionarios;



	public FuncionariosTableModel(List<Funcionarios> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public int getRowCount() {
		return funcionarios.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Funcionarios n = funcionarios.get(linha);
		switch (coluna) {
		case 0:
			return n.getCodFuncionario();
		case 1:
			return n.getNome();
		case 2:
			return n.getSexo();
		case 3:
			return n.getSalario();
		case 4:
			return n.getDtNascimento();
		case 5:
			return n.getNrDepto();
		case 6:
			return n.getCodSupervidor();
		case 7:
			return n.getCodCargo();
		}
		return null;
	}
}
