/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author csalas
 */
public class VistaCronometro extends JPanel implements Runnable, ActionListener {
    
    public static int onoff = 0;
    JLabel tiempo;
    Thread hilo;
    boolean cronometroActivo, tiempoAcabado;
    int minutos, segundos;
    JButton btnSiguiente;
    public VistaCronometro(int totalSegundos, JButton btnSiguiente)
    {
        
        this.btnSiguiente = btnSiguiente;
        String cadTiempo = calculaTiempoInicial(totalSegundos);
        setLayout( new FlowLayout());

        //Etiqueta donde se colocara el tiempo 
        tiempo = new JLabel( cadTiempo );
        tiempo.setFont( new Font( Font.SERIF, Font.BOLD, 25 ) );
        tiempo.setHorizontalAlignment( JLabel.CENTER );
        tiempo.setForeground( Color.BLACK );
        tiempo.setOpaque( true );

        add( tiempo );
        

        setVisible( true );
    }
    
    private String calculaTiempoInicial(int segundos) {
        int m, s;
        String mCad, sCad;
        m = segundos / 60;
        s = segundos%60;
        
        minutos = m;
        this.segundos = s;
        
        if (m < 10)
            mCad = "0"+m;
        else
            mCad = Integer.toString(m);
        
        if (s < 10)
            sCad = "0"+s;
        else
            sCad = Integer.toString(s);
        
        return mCad+":"+sCad;
    }
    
    public boolean isAcabado() {
        return tiempoAcabado;
    }

    @Override
    public void run(){
        Integer minutos = this.minutos , segundos = this.segundos;
        //min es minutos, seg es segundos y mil es milesimas de segundo
        String min="", seg="", mil="";
        try
        {
            //Mientras cronometroActivo sea verdadero entonces seguira
            //aumentando el tiempo
            while( cronometroActivo )
            {
                Thread.sleep( 1000 );
                //El cronómetro gira segundo a segundo

                   // Si los segundos llegan a cero
                    if( segundos == 0 )
                    {
                        segundos = 59;
                        minutos--;
                    }
                    else
                        segundos--;
                

                if( minutos < 10 ) min = "0" + minutos;
                else min = minutos.toString();
                if( segundos < 10 ) seg = "0" + segundos;
                else seg = segundos.toString();
                
                if (minutos == 0 && segundos == 0) {
                    pararCronometro();
                    btnSiguiente.setText("Finalizar");
                    tiempo.setForeground(Color.RED);
                    tiempoAcabado = true;
                }

                //Colocamos en la etiqueta la informacion
                tiempo.setText( min + ":" + seg);
            }
        }catch(Exception e){}

    }

    @Override
    public void actionPerformed( ActionEvent evt ) {
        Object o = evt.getSource();
        if( o instanceof JButton )
        {
            JButton btn = (JButton)o;
            if( btn.getText().equals("Iniciar") ){
                if(onoff == 0){
                   onoff = 1;
                   iniciarCronometro();
                }
            }
            if( btn.getText().equals("Reiniciar") ) {
                if (onoff == 1){
                   onoff = 0;
                   pararCronometro();
                }
            }
        }
    }
    
    //Iniciar el cronometro poniendo cronometroActivo 
    //en verdadero para que entre en el while
    public void iniciarCronometro() {
        cronometroActivo = true;
        hilo = new Thread( this );
        hilo.start();
    }

    //Esto es para parar el cronometro
    public void pararCronometro(){
        cronometroActivo = false;
    }
    
}
