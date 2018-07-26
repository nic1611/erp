package exercicio1;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DepartamentoTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Departamento> departamento;


	public DepartamentoTableModel(List<Departamento> departamento) {
		this.departamento = departamento;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return departamento.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Departamento n = departamento.get(linha);
		switch (coluna) {
		case 0:
			return n.getNrDepto();
		case 1:
			return n.getNome();
		case 2:
			return n.getCodGerente();
		case 3:
			return n.getDtInicio();
		}
		return null;
	}
}
