package exercicio1;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProjetoTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Projeto> projeto;


	public ProjetoTableModel(List<Projeto> projeto) {
		this.projeto = projeto;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return projeto.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Projeto n = projeto.get(linha);
		switch (coluna) {
		case 0:
			return n.getNrProjeto();
		case 1:
			return n.getNome();
		case 2:
			return n.getNrDepto();
		case 3:
			return n.getDescricao();
		}
		return null;
	}

}
