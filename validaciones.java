
package Central;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class validaciones {
    
    public void textKeyPress(KeyEvent event){
        char car = event.getKeyChar();// obtenemos el valor de esa tecla
        //Ingresar solo texto <>
        
        if((car < 'a' || car > 'z') && (car < 'A' || car > 'Z') 
                && ( car != (char) KeyEvent.VK_BACK_SPACE)  && ( car != (char) KeyEvent.VK_SPACE)){
            event.consume();
        }
          
        //if(!(car >=KeyEvent.VK_0 &&  car <=KeyEvent.VK_9)){
        //    event.consume();
        //}
    }
    
    
    public void numberKeyPress(KeyEvent event){
        char car = event.getKeyChar();// obtenemos el valor de esa tecla
        //Ingresar solo texto <>
        if((car < '0' || car > '9') && ( car != (char) KeyEvent.VK_BACK_SPACE) ){
            event.consume();
        }
    }
    
    public void telefonoKeyPress(KeyEvent event){
        char car = event.getKeyChar();// obtenemos el valor de esa tecla
        //Ingresar solo texto <>
        if((car < '0' || car > '9') && ( car != (char) KeyEvent.VK_MINUS) ){
            event.consume();
        }
    }
    
    public void numberDecimalKeyPress( KeyEvent event, JTextField textField ){
        char car = event.getKeyChar();// obtenemos el valor de esa tecla
        //Ingresar solo texto <>
        if((car < '0' || car > '9') && (textField.getText().contains(".")) && ( car != (char) KeyEvent.VK_BACK_SPACE) ){
            event.consume();
        } else if((car < '0' || car > '9') && ( car != '.') && ( car != (char) KeyEvent.VK_BACK_SPACE) ){
            event.consume();//no permite introducir 2 veces el punto decimal
        }
    }
    
    
    public void limiteCarater(KeyEvent event, JTextField textField, int cantidad){
        if (textField.getText().length()>=cantidad){
            event.consume();
            JOptionPane.showMessageDialog(null, "Valor Máximo");
        
        }
    }
}
