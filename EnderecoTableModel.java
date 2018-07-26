package exercicio1;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EnderecoTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Endereco> endereco;

	public EnderecoTableModel(List<Endereco> endereco) {
		this.endereco = endereco;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return endereco.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Endereco n = endereco.get(linha);
		switch (coluna) {
		case 0:
			return n.getId();
		case 1:
			return n.getCidade();
		case 2:
			return n.getBairro();
		case 3:
			return n.getRua();
		case 4:
			return n.getNumero();
		case 5:
			return n.getCep();
		}
		return null;
	}


}
