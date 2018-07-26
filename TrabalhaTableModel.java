package exercicio1;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TrabalhaTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Trabalha> trabalha;

	public TrabalhaTableModel(List<Trabalha> trabalha) {
		this.trabalha = trabalha;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return trabalha.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Trabalha n = trabalha.get(linha);
		switch (coluna) {
		case 0:
			return n.getCodFuncionario();
		case 1:
			return n.getNrProjeto();
		case 2:
			return n.getHorasTrabalhadas();
		}
		return null;
	}

}
