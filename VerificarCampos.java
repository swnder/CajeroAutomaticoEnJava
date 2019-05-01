/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Central;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SOFT_LIGHTPeer;
import com.toedter.calendar.JDateChooser;
import java.awt.Button;
import javafx.scene.control.RadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

/**
 *
 * @author swnder
 */
public class VerificarCampos  {
    private final JFormattedTextField caja1;
    private final JFormattedTextField caja2;
    private final JFormattedTextField caja3;
    private  JComboBox desplegable ;
    private JComboBox desplegable1;
    private JDateChooser fecha1;
    private JDateChooser fecha2;
    private JFormattedTextField caja4;
    private ButtonGroup seleccion;
    private ButtonGroup selecion2;

    public String getSeleccion() {
           String seleccion1="";
        if (seleccion.getSelection() == null) {
            return seleccion1 = "vacio";
        }else{
          seleccion1 = String.valueOf(seleccion.getSelection().getActionCommand());
        }
        return seleccion1;
    }

    public String getSelecion2() {
        
        String valor ="";
        
        if(selecion2.getSelection()==null){
            return valor="vacio";
        }else{
            valor=String.valueOf(selecion2.getSelection().getActionCommand());
        }
        return valor;
    }
    
    public VerificarCampos(JFormattedTextField caja1, JFormattedTextField caja2, JFormattedTextField caja3, 
            JComboBox desplegable, JComboBox desplegable1, JDateChooser fecha1, JDateChooser fecha2, 
            JFormattedTextField caja4, ButtonGroup seleccion, ButtonGroup selecion2) {
        this.caja1 = caja1;
        this.caja2 = caja2;
        this.caja3 = caja3;
        this.desplegable = desplegable;
        this.desplegable1 = desplegable1;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        this.caja4 = caja4;
        this.seleccion = seleccion;
        this.selecion2 = selecion2;
    }

    public VerificarCampos(JFormattedTextField caja1, JFormattedTextField caja2, JFormattedTextField caja3) {
        this.caja1 = caja1;
        this.caja2 = caja2;
        this.caja3 = caja3;
    }
    
   
    
   
    
    public VerificarCampos (JFormattedTextField nombre, JFormattedTextField nombre2, JFormattedTextField nombre3, JComboBox combo){
        caja1 = nombre;
        caja2 = nombre2;
        caja3 = nombre3;
        desplegable = combo;
    }

     public VerificarCampos(JFormattedTextField nombre, JFormattedTextField nombre2, JComboBox combo){
            caja1 = nombre;
            caja2 = nombre2;
            caja3 = null ;
            desplegable = combo;
        
    }
    
    
    
    
    public VerificarCampos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
    
    
     public boolean vacios(){
        boolean resultado = false;
         if ("".equals(caja1.getText())
                 || "".equals(caja2.getText())
                 || "".equals(caja3.getText())
                 || desplegable.getSelectedIndex() == 0) {

             resultado = true;
         } 
        
        return resultado;
    }
    
    public boolean vaciosTarjetas() {
        boolean resultado = false;
        if ("".equals(caja1.getText())
                || "".equals(caja2.getText())
                || "".equals(caja3.getText())
                || desplegable.getSelectedIndex() == 0
                || desplegable1.getSelectedIndex() == 0
                || fecha2.getDate() == null
                || "".equals(caja4.getText())
                || seleccion.getSelection().getActionCommand() == null
                || selecion2.getSelection().getActionCommand() == null) {

            resultado = true;
        }

        return resultado;

    }

     
     
    public boolean vaciosBancos(){
        boolean resultado = false;
         if ("".equals(caja1.getText())
                 || desplegable.getSelectedIndex() == 0
                 || "".equals(caja2.getText())) {

             resultado = true;

         }
        return resultado;
    }  
     

    public boolean vacioSinDespleable() {
        boolean resultado = false;
        if ("".equals(caja1.getText())
                || "".equals(caja2.getText())
                || "".equals(caja3.getText())) {

            resultado = true;
        }
        return resultado;
    }
    public void Vaciar_Campos(){
        caja1.setText("");
        caja2.setText("");
        caja3.setText("");
        desplegable.setSelectedIndex(0);
          
        
    }
    public void Vaciar_camposBank(){
        caja1.setText("");
        caja2.setText("");
        desplegable.setSelectedIndex(0);
    
    }
    public void Vaciar_camposTarjeta(){
        this.caja1.setText("");
        this.caja2.setText("") ;
        this.caja3.setText("");
        this.caja4.setText("");
        this.desplegable.setSelectedIndex(0);
        this.desplegable1.setSelectedIndex(0);
        this.fecha1=fecha1;
        this.fecha2.setDate(null);
       
        
    
    }
    


}





