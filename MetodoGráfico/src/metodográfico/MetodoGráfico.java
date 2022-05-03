/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodográfico;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Font;


/**
 *
 * @author kevb6
 */
public class MetodoGráfico {

      public static void main(String[] args) {
        JFrame frame = new JFrame();     //Frame generation
        
        frame.setTitle("Método Simplex: Gráfico");       //Window title
        frame.setSize(675, 680);                                      //Window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Processing when the × button is pressed
        
        MyPanel panel = new MyPanel(); //Generate MyPanel
        frame.getContentPane().add(panel); //Paste My Panel on the frame
        
        frame.setVisible(true);             //Make the window visible
    }
    
}
