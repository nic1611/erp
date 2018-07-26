package exercicio1;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DependenteTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Dependente> dependente;


	public DependenteTableModel(List<Dependente> dependente) {
		this.dependente = dependente;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return dependente.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Dependente n = dependente.get(linha);
		switch (coluna) {
		case 0:
			return n.getCodDependente();
		case 1:
			return n.getNome();
		case 2:
			return n.getParentesco();
		case 3:
			return n.getSexo();
		case 4:
			return n.getDtNascimento();
		case 5:
			return n.getCodFuncionario();
		}
		return null;
	}
}
