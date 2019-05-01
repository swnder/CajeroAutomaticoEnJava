

package appcajero;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

class DecimalRenderer extends DefaultTableCellRenderer {
    DecimalFormat formatter;
    DecimalRenderer(String pattern){
        this(new DecimalFormat(pattern));
    }
    DecimalRenderer(DecimalFormat formatter){
        this.formatter = formatter;
        setHorizontalAlignment (JLabel.RIGHT);
    }
}
public class Grilla {
    ConexionBD bd;
    
    public void configurarmodelo(JTable nombregrilla, String[] columnas, int [] ancho){
        ResultSet rs;
        DefaultTableModel dm = new DefaultTableModel(){
            public boolean isCellEditable(int row, int col){
            return false;
           }
        };
        dm.setDataVector(
                new Object[][]{},
                new String[]{});
        for (int cont=0; cont<columnas.length;cont++)
            dm.addColumn(columnas[cont]);
        nombregrilla.getTableHeader().setReorderingAllowed(false);
        nombregrilla.setModel(dm);
        for (int cont2=0; cont2<=columnas.length-1;cont2++){
            nombregrilla.getColumnModel().getColumn(cont2).setPreferredWidth(ancho[1]);}
    }
    public void alinear(JTable grilla, String columna){
        final DecimalFormat formato = new DecimalFormat("###,##0.00");
        grilla.getColumn(columna).setCellRenderer(new DecimalRenderer (formato));

    }
    public void actualizargrilla(ConexionBD bd,JTable nombregrilla, String tabla, String[] campos,String union){
        String sql="select ";
        DefaultTableModel modelo = (DefaultTableModel) nombregrilla.getModel();
        nombregrilla.selectAll();
        int[] filas = nombregrilla.getSelectedRows();
        for (int i= (filas.length - 1); i >=0; --i)
            modelo.removeRow(i);
        for (int cont=0;cont<campos.length;cont++){
            if (cont <1){
                sql=sql+campos[cont];
            }else{
                sql=sql+ ", " +campos[cont];
            }
        }
        try{
            //sql=sql+" from "+union;
            sql = sql+tabla+union;
            ResultSet rs =bd.dameLista(sql);
            String [] valores= new String[campos.length];
            
            int fila=0;
            
                        while(rs.next()){
                            for (int cont2=0;cont2<campos.length;cont2++){
                                valores[cont2]=rs.getString(cont2+1);

                            }
                            modelo.addRow(new Object[]{});

                            for (int col=0; col < valores.length;col++)
                                modelo.setValueAt(valores[col],fila,col);
                            fila =fila+1;
                            
                            
                        }

            nombregrilla.setModel(modelo);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error al intentar cargar la grilla"+ex.toString(), "Grilla",JOptionPane.INFORMATION_MESSAGE);
        }  
}



public void filtrarGrilla(JTable nombregrilla, String texto){
        DefaultTableModel modelo = (DefaultTableModel) nombregrilla.getModel();
    TableRowSorter gridFiltrado = new TableRowSorter(modelo);
    gridFiltrado.setRowFilter(RowFilter.regexFilter(texto));
    nombregrilla.setRowSorter(gridFiltrado);
}

public static ArrayList <String> llenarCombo(String consulta){
    ArrayList <String> lista = new ArrayList<String>();
    ResultSet resultado = null;
    Statement sentencia = null;
    try{
        resultado =sentencia.executeQuery(consulta);
    }catch( Exception e){}
    
    
    try{
        while (resultado.next()){
            lista.add(resultado.getString("calle"));
        
        }
    }catch( Exception e){}
    
    
    
    
    return lista;
}



}