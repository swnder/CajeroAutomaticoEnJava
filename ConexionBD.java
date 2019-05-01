package appcajero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;

public class ConexionBD {
    String baseD;
    
    /* La conexion con la base de datos */
    private Connection conexion = null;
    
    public ConexionBD(String a) {
        estaConectado();
        this.baseD=a;
    }

    ConexionBD() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public boolean estaConectado(){
        if (conexion != null)
            return true;
        
        try {
            //DriverManager.registerDriver(new org.postgresql.Driver());
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1/"+baseD,"root","123");
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean borrarRegistro(String tabla, String condicion){
        int resultado;
        try {
            // Se crea un Statement, para realizar la sentencia
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            JOptionPane optionPane=new JOptionPane();
            Object[] opciones={"Si","No"};
            //Dialogo modal SI_NO
            int ret=optionPane.showOptionDialog(null,"Esta seguro de ELIMINAR el REGISTGRO? ","Pregunta",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opciones,opciones[0]);
            //Si la opcion escogida es Si
            if(ret==JOptionPane.YES_OPTION)
                resultado = s.executeUpdate("delete from "+tabla+" where "+condicion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el registro debido a que la misma está siendo utilizada en otra tabla", "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    public boolean borrarRegistroSinPreguntar(String tabla, String condicion){
        int resultado;
        try {
            // Se crea un Statement, para realizar la sentencia
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultado = s.executeUpdate("delete from "+tabla+" where "+condicion);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
 
    public ResultSet dameLista(String sql){
        ResultSet rs = null;
        try{
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(sql);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Ocurrio Un error en Consulta: "+e , "Atencion",
            JOptionPane.INFORMATION_MESSAGE);
            System.out.println(sql);
        }
        return rs;
    }
     
    public boolean insertarRegistro(String tabla, String campos, String valores){
        int resultado;
        try {
            // Se crea un Statement, para realizar la sentencia
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // Se realiza el insert a la BD
            resultado = s.executeUpdate("insert into "+tabla+" ("+campos+") values ("+valores+")");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error\n"+e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    public boolean insertarRegistro(String tabla, String valores){
        int resultado;
        try {
            // Se crea un Statement, para realizar la sentencia
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // Se realiza el insert a la BD
            resultado = s.executeUpdate("insert into "+tabla+ " values("+valores+")");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error\n"+e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    public void cargarCombo(String sql, JComboBox combo){
        ResultSet rs = null;
        int contar=0;
        try{
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(sql);
            while (rs.next()){
                String columnas[] = new String[1];
                columnas[0] = rs.getString(1);
                //columnas[1] = rs.getString(2);
                combo.addItem(columnas);
                contar++;
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error: "+ e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if (contar>0){
            combo.setRenderer(new DefaultListCellRenderer() {
                public java.awt.Component getListCellRendererComponent(
                        JList l,Object o,int i,boolean s, boolean f) {
                    try{
                        return new JLabel(((String[])o)[1]);
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Ocurrio Un error" , "Atencion",
                                JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                }
            });
        }
    }
    public void cargarCombo2(String sql, JComboBox combo, int col){
        ResultSet rs = null;
        int contar=0;
        try{
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(sql);
            while (rs.next()){
                String columnas[] = new String[col];
                for (int a=0;a<col;a++){
                    columnas[a]=rs.getString(a+1);
                }
                combo.addItem(columnas);
                contar++;
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error: "+ e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if (contar>0){
            combo.setRenderer(new DefaultListCellRenderer() {
                public java.awt.Component getListCellRendererComponent(
                        JList l,Object o,int i,boolean s, boolean f) {
                    try{
                        return new JLabel(((String[])o)[1]);
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Ocurrio Un error" , "Atencion",
                                JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                }
            });
        }
    }
    public void VaciarCombo(JComboBox combo){
        combo.removeAllItems();
        ArrayList<datosCombo> camposCombo;
        camposCombo = new ArrayList();
    }

    public void llenarCombo(JComboBox combo, String campos, String tabla){
         ResultSet rsC = null;
         try{
             //Crear la sentencia para la consulta
             Statement sentencia=conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
             //Se ejecuta la consulta
             rsC = sentencia.executeQuery("select " + campos + " from " + tabla);
           //Se inicializa el arraylist
             ArrayList<datosCombo> camposCombo;
             camposCombo = new ArrayList();
             //Recorrer los registros
             while (rsC.next()) {
                 //Agregar al arraylist datos del rsC
                 camposCombo.add(new datosCombo(rsC.getInt(1), rsC.getString(2)));
             }
             for (datosCombo nombre: camposCombo){
                 //Agregar items al combo
                 combo.addItem(nombre);
             }
         }catch(Exception e) {
             //Si ocurrio un error mostrar mensaje
             JOptionPane.showMessageDialog(null, "Error al llenar combo\n" + e.getMessage()  , "Llenar Combo - "  + combo.getName(), JOptionPane.ERROR_MESSAGE);
             return;
         }
     }
    public boolean actualizarRegistro(String tabla, String campos, String criterio){
        int resultado;
        try {
            // Se crea un Statement, para realizar la sentencia
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // Se realiza el update.
            resultado = s.executeUpdate("update "+tabla+" set "+campos+" where " +criterio);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error\n "+e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    public ResultSet dameLista(String campos, String tabla, String condicion) {
        ResultSet rs = null;
        try {
            // Se crea un Statement, para realizar la sentencia
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            // Se realiza la consulta. Los resultados se guardan en el ResultSet rs
            rs = s.executeQuery("select "+campos+" from "+tabla+" "+condicion);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error " , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }

    public void iniciarTransaccion(boolean estado){
        try{
            conexion.setAutoCommit(!estado);
        }catch (Exception e){
        }
    }
    public void commitTransaccion(boolean estado){
        try{
            if (estado){
                conexion.commit();
            } else
                conexion.rollback();
        }catch (Exception e){
        }
    }
    /** Cierra la conexion con la base de datos */
    public void cierraConexion() {
        try {
            conexion.close();
        } catch (Exception e) {
        }
    }
    
    public void llenarCombo2(JComboBox combo, String PrimerItem){
         ResultSet rsC = null;
         try{
             //Crear la sentencia para la consulta
             Statement sentencia=conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
             //Se ejecuta la consulta
             //rsC = sentencia.executeQuery("select " + campos + " from " + tabla);
             rsC = sentencia.executeQuery("select c.id_cajero, d.calle from cajeros c "
                + "inner join direccion d on direcion=id_direccion");
           //Se inicializa el arraylist
             ArrayList<datosCombo> camposCombo;
             camposCombo = new ArrayList();
             //Recorrer los registros
             while (rsC.next()) {
                 //Agregar al arraylist datos del rsC
                 camposCombo.add(new datosCombo(rsC.getInt(1), rsC.getString(2)));
                
             }
             //JOptionPane.showMessageDialog(null, camposCombo.get(2));
             //Agrega el primer item en el combo con index 0
              combo.addItem(PrimerItem);
             for (datosCombo nombre: camposCombo){
                 //Agregar items al combo
                 combo.addItem(nombre);
             }
             
         }catch(Exception e) {
             //Si ocurrio un error mostrar mensaje
             JOptionPane.showMessageDialog(null, "Error al llenar combo\n" + e.getMessage()  , "Llenar Combo - "  + combo.getName(), JOptionPane.ERROR_MESSAGE);
             return;
         }
     }
    
    public String GenerarCodgio(String campo, String tabla){
        String codigo=null;
        ResultSet cod = null;
        try {
            Statement sql = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            cod = sql.executeQuery("select max("+campo+") from "+tabla+";");
            cod.first();
            codigo = String.valueOf(cod.getInt(1)+1);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de Conexión con base de datos\n vuelve a intentarlo");
        }
        return codigo;
    }
}
