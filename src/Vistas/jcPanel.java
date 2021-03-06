package Vistas;
import Vistas.jpComponente;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
/**
 * 
 */
public class jcPanel extends JPanel implements ActionListener {
    
    public String respSeleccionada = null;
    jpComponente jpc = null;
    ButtonGroup buttongroup;

     public jcPanel()
    {
        this.setSize(380, 200);
        this.setVisible(true);
        this.setLayout( new BoxLayout(this,BoxLayout.Y_AXIS) );
    }

    public void Mi_Componente(ButtonGroup buttongroup,String respuesta)
    {        
        //instancia nueva a componente
        jpc = new jpComponente();
        buttongroup.add(jpc.radioRespuesta);
        jpc.radioRespuesta.setText(respuesta);
        jpc.radioRespuesta.addActionListener(this);
        this.add(jpc,BorderLayout.EAST);//se añade al jpanel
        this.validate();
        this.buttongroup = buttongroup;
        
    }
    
    public void vaciar() {
        this.removeAll();
        respSeleccionada = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       for (Enumeration<AbstractButton> buttons = buttongroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                respSeleccionada = button.getText();
            }
        }
       
    }

   

}
