package exercicio1;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CargoTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Cargo> cargo;

	public CargoTableModel(List<Cargo> cargo) {
		this.cargo = cargo;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return cargo.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Cargo n = cargo.get(linha);
		switch (coluna) {
		case 0:
			return n.getCar_codigo();
		case 1:
			return n.getCar_nome();
		case 2:
			return n.getCar_descricao();
		}
		return null;
	}

}
