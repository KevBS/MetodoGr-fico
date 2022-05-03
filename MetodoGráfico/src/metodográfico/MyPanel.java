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
public class MyPanel extends JPanel implements Runnable,ActionListener {
    
int count = 0;
    int info[] = new int[11];
    
    int simu_judge = 0;       //Variables para decidir si iniciar la simulación
                              //simu_judge=1 para iniciar la simulación
    
    int scale_judge = 0;     //Variable para determinar la escala en el eje del gráfico
                             //scale_judge=1 para iniciar la simulación
    
    //Círculo (Mostrar la solución en ese momento) Centro de coordenadas de
    int x = 100;
    int y = 600;
    
    //Almacena el número de pasos para encontrar la solución óptima 
    int step = 0;
    
    //X en la intersección de tres líneas rectas, Variable para almacenar la coordenada Y
    int x_intersection12 = -1;   //La coordenada X de la intersección de la primera recta y la segunda recta
    int x_intersection13 = -1;   //La coordenada X de la intersección de la primera recta y la tercera recta
    int x_intersection23 = -1;   //La coordenada X de la intersección de la segunda y tercera recta
    int y_intersection12 = -1;   //Coordenada Y de la intersección de la primera línea recta ingresada y la segunda línea recta ingresada
    int y_intersection13 = -1;   //La coordenada y de la intersección de la primera recta y la tercera recta
    int y_intersection23 = -1;   //La coordenada y de la intersección de la segunda recta y la tercera recta
    
    //Text field(Función de Maximización)
    JTextField o_x = new JTextField(10);
    JTextField o_y = new JTextField(10);
    JTextField o_n = new JTextField(10);
    
    //Text field(Restricción 1 y primera línea recta para ingresar)
    JTextField s_x1 = new JTextField(10);
    JTextField s_y1 = new JTextField(10);
    JTextField s_n1= new JTextField(10);
    
    //Text field(Restricción 2 y segunda línea recta para ingresar)
    JTextField s_x2 = new JTextField(10);
    JTextField s_y2 = new JTextField(10);
    JTextField s_n2 = new JTextField(10);
    
    //Text field(Restricción 3 y 3ra línea recta para ingresar)
    JTextField s_x3 = new JTextField(10);
    JTextField s_y3 = new JTextField(10);
    JTextField s_n3 = new JTextField(10);
    
    //label(Guía para solicitar entrada en la pantalla)
    JLabel label1 = new JLabel("x            +");
    JLabel label2 = new JLabel("y");
    JLabel label3 = new JLabel("");
    JLabel label4 = new JLabel("x            +");
    JLabel label5 = new JLabel("y           ≦");
    JLabel label6 = new JLabel("x            +");
    JLabel label7 = new JLabel("y           ≦");
    JLabel label8 = new JLabel("x            +");
    JLabel label9 = new JLabel("y           ≦");
    JLabel label10 = new JLabel("x≧0, y≧0");
    JLabel label11 = new JLabel("Función Objetivo(Maximización)");
    JLabel label12 = new JLabel("Restricciones");
    
    //Botón 1 (Refleja el valor ingresado en el programa y dibuja una línea recta en el gráfico)
    JButton button1 = new JButton("Calcular y Graficar");
   
    
    public MyPanel() {
        // Registrar detector de eventos
        button1.addActionListener(this);
       
        
       //Configuración del diseño del panel
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1,1));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,5));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1,1));
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(1,5));
        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(1,5));
        JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayout(1,5));
        JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayout(1,1));
        
        //Unificar el color de cada panel a blanco
        panel1.setBackground(Color.WHITE);
        panel2.setBackground(Color.WHITE);
        panel3.setBackground(Color.WHITE);
        panel4.setBackground(Color.WHITE);
        panel5.setBackground(Color.WHITE);
        panel6.setBackground(Color.WHITE);
        panel7.setBackground(Color.WHITE);
        
      //Añadir cada componente a cada panel
        panel1.add(label11);
        
        panel2.add(o_x);
        panel2.add(label1);
        panel2.add(o_y);
        panel2.add(label2);
        panel2.add(label3);
        
        panel3.add(label12);
        
        panel4.add(s_x1);
        panel4.add(label4);
        panel4.add(s_y1);
        panel4.add(label5);
        panel4.add(s_n1);
        
        panel5.add(s_x2);
        panel5.add(label6);
        panel5.add(s_y2);
        panel5.add(label7);
        panel5.add(s_n2);
        
        panel6.add(s_x3);
        panel6.add(label8);
        panel6.add(s_y3);
        panel6.add(label9);
        panel6.add(s_n3);
        panel7.add(label10);
        
        
        //Cada panel, un panel f_Pegar en panel1
        JPanel f_panel1 = new JPanel();
        f_panel1.setLayout(new GridLayout(9,1));
        f_panel1.add(panel1);
        f_panel1.add(panel2);
        f_panel1.add(panel3);
        f_panel1.add(panel4);
        f_panel1.add(panel5);
        f_panel1.add(panel6);
        f_panel1.add(panel7);
        f_panel1.add(button1);
       
        
        f_panel1.setBackground(Color.WHITE);    //f_Instalar panel1 en blanco
        add(f_panel1);                          //f_Agregar panel1 a MiPanel
        
        setBackground(Color.WHITE);
        Thread refresh = new Thread((Runnable) this);
        refresh.start();
        
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);             //Rellene el componente con un color de fondo.
        int seppen[] = new int[6];
        int seppen_max = 0;        //Intercepción (x, y ambas) Valor máximo de
        int xy_max = 0;            //X en el gráfico, valor máximo de y
        int x_min = 0;             //intersección mínima x
        int y_min = 0;             //intercepción mínima y
        int n_x_min = 0;           //x Número de la expresión con el intercepto mínimo
        int n_y_min = 0;           //El número de la expresión con la intersección y mínima
        double d_masu = 0;         //Unidad gráfica por píxel
        int select3 = 0;           //En el paso 3, almacena el nu
        
        /*
          Encuentre los valores temporales de intersección x e intersección y asígnelos a cada variable. 
          (Asigne hasta un lugar decimal a una variable convirtiéndola en tipo int más de 10 veces)
        */
        int temporary_x1 = (int)(((double)info[4]/(double)info[2])*10);   //X-intersección de la primera línea recta ingresada
        int temporary_y1 = (int)(((double)info[4]/(double)info[3])*10);   //Y-intersección de la primera línea recta ingresada
        int temporary_x2 = (int)(((double)info[7]/(double)info[5])*10);   //La intersección x de la segunda línea recta ingresada
        int temporary_y2 = (int)(((double)info[7]/(double)info[6])*10);   //Intersección en Y de la segunda línea recta ingresada
        int temporary_x3 = (int)(((double)info[10]/(double)info[8])*10);  //La intersección x de la tercera línea recta ingresada
        int temporary_y3 = (int)(((double)info[10]/(double)info[9])*10);  //Intersección en Y de la tercera línea recta de entrada
        
        //La intersección obtenida arriba se secuencia seppen[]Store (almacena) en:
        seppen[0] = temporary_x1;
        seppen[1] = temporary_y1;
        seppen[2] = temporary_x2;
        seppen[3] = temporary_y2;
        seppen[4] = temporary_x3;
        seppen[5] = temporary_y3;
        
        //Encuentra el valor máximo de la intersección.
        seppen_max = seppen[0];
        for(int i=1; i<6; i++){
            if(seppen[i] > seppen_max){
                seppen_max = seppen[i];
            }
        }
        
        //Encuentre el valor máximo de la escala del gráfico a partir del valor máximo de la intersección.
        while(true){
            //Cuando el valor máximo de la escala del gráfico excede el valor máximo de la intersección
            if(xy_max >= seppen_max){
                break;                //Salir del bucle y xy en ese punto_Establecer max en el valor máximo de la escala
            }
            xy_max = xy_max + 150;    //xy_Aumenta max by 150
                                      //(Dado que la intersección se multiplica por 10 en la parte superior, el valor máximo de la escala en realidad aumenta por 15)
        }
        
        //Encuentra la unidad gráfica por píxel
        d_masu = ((double)300)/((double)xy_max)*10;     //300 es el número de píxeles en la dirección vertical del gráfico
                                                        //Divide 300 por el valor máximo de la escala y multiplica por 10 para obtener el valor en unidades gráficas.
        
        //Encuentra las coordenadas de cada intersección en el gráfico
        int decisive_x1 = (int)(((double)info[4]/(double)info[2])*d_masu);   //Intersección en X de la primera línea recta ingresada
        int decisive_y1 = (int)(((double)info[4]/(double)info[3])*d_masu);   //Intersección en Y de la primera línea recta ingresada
        int decisive_x2 = (int)(((double)info[7]/(double)info[5])*d_masu);   //La intersección x de la segunda línea recta ingresada
        int decisive_y2 = (int)(((double)info[7]/(double)info[6])*d_masu);   //Intersección en Y de la segunda línea recta ingresada
        int decisive_x3 = (int)(((double)info[10]/(double)info[8])*d_masu);  //La intersección x de la tercera línea recta ingresada
        int decisive_y3 = (int)(((double)info[10]/(double)info[9])*d_masu);  //Intersección en Y de la tercera línea recta de entrada
        
        //Precauciones al ingresar datos
        g.drawString("Asegúrese de ingresar un número entero positivo en todos los campos de entrada.",120,640);
        
        
        g.drawLine(100,275,100,600);    //eje y
        g.drawLine(100,600,425,600);    //eje x
        
        g.drawLine(95,300,105,300);     //eje y escala (primero desde arriba)
        g.drawLine(95,400,105,400);     //eje y escala (segundo desde arriba)
        g.drawLine(95,500,105,500);     //eje y escala (tercera desde arriba)
        
        g.drawLine(200,595,200,605);    //eje x escala (primero desde la izquierda)
        g.drawLine(300,595,300,605);    //eje x escala (segundo desde la izquierda)
        g.drawLine(400,595,400,605);    //eje x escala (Tercero desde la izquierda)
        
        //Eje y
        g.drawLine(100,275,95,280);
        g.drawLine(100,275,105,280);
        //Eje x
        g.drawLine(425,600,420,595);
        g.drawLine(425,600,420,605);
        
        //Dibujar un gráfico de las tres líneas rectas de entrada
        g.drawLine(decisive_x1+100,600,100,600-decisive_y1);
        g.drawLine(decisive_x2+100,600,100,600-decisive_y2);
        g.drawLine(decisive_x3+100,600,100,600-decisive_y3);
        
        //Calcular el valor de cada escala
        int n_xy1 = xy_max/30;
        int n_xy2 = (xy_max/30)*2;
        int n_xy3 = xy_max/10;
        
        //Convierta el valor numérico de cada escala en una cadena de caracteres (para contar la cantidad de dígitos en cada escala)
        String s_xy1 = String.valueOf(n_xy1);
        String s_xy2 = String.valueOf(n_xy2);
        String s_xy3 = String.valueOf(n_xy3);
        
        //Obtenga la cantidad de dígitos de cada escala y sustitúyala (para evitar que la posición del valor numérico en la escala cambie según la cantidad de dígitos)
        int xy1_length = String.valueOf(n_xy1).length();
        int xy2_length = String.valueOf(n_xy2).length();
        int xy3_length = String.valueOf(n_xy3).length();
        
        //Cuando se ingresa los datos en el botón1 (Se escribe el valor de cada escala en el gráfico)
        if(scale_judge == 1){
            g.drawString(s_xy1, 200-(xy1_length*3), 620);
            g.drawString(s_xy2, 300-(xy2_length*3), 620);
            g.drawString(s_xy3, 400-(xy3_length*3), 620);
            g.drawString(s_xy1, 90-(xy1_length*6), 505);
            g.drawString(s_xy2, 90-(xy2_length*6), 405);
            g.drawString(s_xy3, 90-(xy3_length*6), 305);
        }
        
        //Encuentre el valor mínimo de la intersección x y la intersección y. Además, el número de la línea recta con el valor mínimo (Qué número se ingresó)
        x_min = decisive_x1;
        n_x_min = 1;
        y_min = decisive_y1;
        n_y_min = 1;
        
        if(x_min > decisive_x2){
            x_min = decisive_x2;
            n_x_min = 2;
        }
        if(x_min > decisive_x3){
            x_min = decisive_x3;
            n_x_min = 3;
        }
        
        
        if(y_min > decisive_y2){
            y_min = decisive_y2;
            n_y_min = 2;
        }
        if(y_min > decisive_y3){
            y_min = decisive_y3;
            n_y_min = 3;
        }
        
        /*
          Cada línea recta (Línea recta debido a restricciones) 
          Valor límite superior de (Término constante) A un valor en unidades gráficas y sustitución
        */
        int upper1 = (int)(((double)info[4])*d_masu);
        int upper2 = (int)(((double)info[7])*d_masu);
        int upper3 = (int)(((double)info[10])*d_masu);
        
        
        //Encuentre la coordenada x de la intersección de cada línea recta
        x_intersection12 = (int)((((double)info[7]/(double)info[6])-((double)info[4]/(double)info[3]))/(((double)info[5]/(double)info[6])-((double)info[2]/(double)info[3]))*d_masu);
        x_intersection13 = (int)((((double)info[10]/(double)info[9])-((double)info[4]/(double)info[3]))/(((double)info[8]/(double)info[9])-((double)info[2]/(double)info[3]))*d_masu);
        x_intersection23 = (int)((((double)info[10]/(double)info[9])-((double)info[7]/(double)info[6]))/(((double)info[8]/(double)info[9])-((double)info[5]/(double)info[6]))*d_masu);
        
        //Encuentre la coordenada y de la intersección de cada línea recta
        y_intersection12 = (int)((((double)info[7]/(double)info[5])-((double)info[4]/(double)info[2]))/(((double)info[6]/(double)info[5])-((double)info[3]/(double)info[2]))*d_masu);
        y_intersection13 = (int)((((double)info[10]/(double)info[8])-((double)info[4]/(double)info[2]))/(((double)info[9]/(double)info[8])-((double)info[3]/(double)info[2]))*d_masu);
        y_intersection23 = (int)((((double)info[10]/(double)info[8])-((double)info[7]/(double)info[5]))/(((double)info[9]/(double)info[8])-((double)info[6]/(double)info[5]))*d_masu);
        
       /*
         Si no hay intersección, las coordenadas(-1,-1)Establecidas en.(-1,-1)Está fuera de la región factible
         Cuando las rectas 1 y 2 son paralelas
       */
        if(x_intersection12>2000000000 || y_intersection12>2000000000){
            x_intersection12 = -1;
            y_intersection12 = -1;
        }
        //Cuando las rectas 1 y 3 son paralelas
        if(x_intersection13>2000000000 || y_intersection13>2000000000){
            x_intersection13 = -1;
            y_intersection13 = -1;
        }
        //Cuando las rectas de 2 y 3 son paralelas
        if(x_intersection23>2000000000 || y_intersection23>2000000000){
            x_intersection23 = -1;
            y_intersection23 = -1;
        }
        
        //Cuando la intersección de 1 y 2 está fuera de la región factible
        if(info[8]*x_intersection12+info[9]*y_intersection12 > upper3){
            x_intersection12 = -1;
            y_intersection12 = -1;
        }
        //Cuando la intersección de 1 y 3 está fuera de la región factible
        if(info[5]*x_intersection13+info[6]*y_intersection13 > upper2){
            x_intersection13 = -1;
            y_intersection13 = -1;
        }
        //Cuando la intersección de 2 y 3 está fuera de la región factible
        if(info[2]*x_intersection23+info[3]*y_intersection23 > upper1){
            x_intersection23 = -1;
            y_intersection23 = -1;
        }
        
        
        int answer_x = info[0]*x_min;    //intercepción x(valor mínimo) 
        int answer_y = info[1]*y_min;    //intercepción y(valor mínimo) 
        
        //Solución en la intersección de cada recta(El valor inicial es-1)
        int answer12 = -1;
        int answer13 = -1;
        int answer23 = -1;
        
        
        //Si la solución en la intersección de cada línea recta está en la región factible, asigna esa solución a cada variable.
        if(x_intersection12>0 && y_intersection12>0){
            answer12 = info[0]*x_intersection12+info[1]*y_intersection12;
        }
        if(x_intersection13>0 && y_intersection13>0){
            answer13 = info[0]*x_intersection13+info[1]*y_intersection13;
        }
        if(x_intersection23>0 && y_intersection23>0){
            answer23 = info[0]*x_intersection23+info[1]*y_intersection23;
        }
        
       // La solución máxima de las soluciones obtenidas anteriormente (Solución óptima) Busque y responda_Sustituya por max
        int answer_max = answer_x;
        if(answer_max < answer_y){
            answer_max = answer_y;
        }
        if(answer_max < answer12){
            answer_max = answer12;
        }
        if(answer_max < answer13){
            answer_max = answer13;
        }
        if(answer_max < answer23){
            answer_max = answer23;
        }
        
      //Cuando se hace clic en el botón para iniciar la simulación
        if(simu_judge == 1){
            
            g.setColor(Color.red);          //Establecer el color del círculo a dibujar en rojo
            
            g.fillOval(x-4, y-4, 8, 8);     //Dibuja un circulo((x-4,y-4)Es el centro del circulo)
             //paso 1(Mover la intercepción desde el origen)
            if(step == 1){
                //Cuando el coeficiente del intercepto en y de la función objetivo es mayor
                if(info[0] <= info[1]){
                    y = y-1;              //Moverse a lo largo del eje y desde el origen
                     //Cuando el punto alcanza el valor mínimo de la intersección y
                    if(y == 600-y_min){
                        //Si ese punto da la solucion optima
                        if(answer_max == answer_y){
                            step = 4;          //Mover al paso 4
                        }
                        //Si ese punto no da la solucion optima
                        else{
                            step = 2;          //Mover al paso 2
                        }
                    }
                }
               //Cuando el coeficiente del intercepto en y de la función objetivo es menor
                else if(info[0] > info[1]){
                    x = x+1;              //Moverse a lo largo del eje x desde el origen
                    //Cuando el punto alcanza el valor mínimo de la intersección x
                    if(x == x_min+100){
                        //Si ese punto da la solucion optima
                        if(answer_max == answer_x){
                            step = 4;         //Mover al paso 4
                        }
                        //Si ese punto no da la solucion optima
                        else{
                            step = 2;         //Mover al paso 2
                        }
                    }
                }
            }
            //Paso 2(Paso de intercepción a intersección)
            else if(step == 2){
                //Cuando el coeficiente del intercepto en y de la función objetivo es mayor
                if(info[0] <= info[1]){
                    //Línea recta 1, cuando llegue a la intersección de 2 (limitado al rango de la región factible)
                    if(x_intersection12>0 && x>x_intersection12+100){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection12+100));
                        y = y-(y - (600-y_intersection12));
                       //Si ese punto da la solucion optima
                        if(answer12 > answer13 && answer12 > answer23){
                            step = 4;          //Mover al paso 4
                        }
                        //Si ese punto no da la solucion optima
                        else{
                            step = 3;       //Mover al paso 3
                            select3 = 1;    //Recordar la línea recta 1 como pista para encontrar las intersecciones restantes
                        }
                    }
                   //Línea recta 1, cuando llegue a la intersección de 3 (limitado al rango de la región factible)
                    else if(x_intersection13>0 && x>x_intersection13+100){
                       //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection13+100));
                        y = y-(y - (600-y_intersection13));
                       //Si ese punto da la solucion optima
                        if(answer13 > answer12 && answer13 > answer23){
                            step=4;          //Mover al paso 4
                        }
                        //Si ese punto no da la solucion optima
                        else{
                            step = 3;       //Mover al paso 3
                            select3 = 2;    //Recordar la línea recta 2 como pista para encontrar las intersecciones restantes
                        }
                    }
                    // Línea recta 2, cuando llega a la intersección de 3 (Limitado al rango de la región factible)
                    else if(x_intersection23>0 && x>x_intersection23+100){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection23+100));
                        y = y-(y - (600-y_intersection23));
                        //Si ese punto da la solucion optima
                        if(answer23 > answer12 && answer23 > answer13){
                            step=4;          //Mover al paso 4
                        }
                        //Si ese punto no da la solucion optima
                        else{
                            step = 3;       //Mover al paso 3
                            select3 = 3;    //Recordar la línea recta 3 como pista para encontrar las intersecciones restantes
                        }
                    }
                    //Cuando la solución óptima es la intersección x y se alcanza el punto
                    else if(answer_max == answer_x && y>600){
                       //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_min+100));
                        y = y-(y - 600);
                        step = 4;          //Mover al paso 4
                    }
                    
                    //Estas tres ramas dan el desplazamiento de coordenadas correspondiente a cada recta.(Para moverse en esa recta)
                    else if(n_y_min == 1){
                        x = x+info[3];
                        y = y+info[2];
                    }
                    else if(n_y_min == 2){
                        x = x+info[6];
                        y = y+info[5];
                    }
                    else if(n_y_min == 3){
                        x = x+info[9];
                        y = y+info[8];
                    }
                }
                //Cuando el coeficiente del intercepto en y de la función objetivo es menor
                else if(info[0] > info[1]){
                    //Línea recta 1, cuando llegue a la intersección de 2 (limitado al rango de la región factible)
                    if(y_intersection12>0 && y<600-y_intersection12){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection12+100)-x);
                        y = y + ((600-y_intersection12)-y);
                        //Si ese punto da la solucion optima
                        if(answer12 > answer13 && answer12 > answer23){
                            step=4;          //Mover al paso 4
                        }
                       //Si ese punto no da la solucion optima
                        else{
                            step=3;         //Mover al paso 3
                            select3 = 1;    //Recordar la línea recta 1 como pista para encontrar las intersecciones restantes
                        }
                    }
                    //Línea recta 1, cuando llegue a la intersección de 3 (limitado al rango de la región factible)
                    else if(y_intersection13>0 && y<600-y_intersection13){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection13+100)-x);
                        y = y + ((600-y_intersection13)-y);
                        //Si ese punto da la solucion optima
                        if(answer13 > answer12 && answer13 > answer23){
                            step=4;          //Mover al paso 4
                        }
                        //Si ese punto no da la solucion optima
                        else{
                            step=3;         //Moveer al paso 3
                            select3 = 2;    //Recordar la línea recta 2 como pista para encontrar las intersecciones restantes
                        }
                    }
                    // Línea recta 2, cuando llega a la intersección de 3 (Limitado al rango de la región factible)
                    else if(y_intersection23>0 && y<600-y_intersection23){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection23+100)-x);
                        y = y + ((600-y_intersection23)-y);
                       //Si ese punto da la solucion optima
                        if(answer23 > answer12 && answer23 > answer13){
                            step=4;          //Moveer al paso 4
                        }
                        //Si ese punto no da la solucion optima
                        else{
                            step=3;         //Mover al paso 3
                            select3 = 3;    //Recordar la línea recta 3 como pista para encontrar las intersecciones restantes
                        }
                    }
                    //Cuando la solución óptima es el intercepto en y y se alcanza el punto
                    else if(answer_max == answer_y && x<100){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + (100-x);
                        y = y + ((600-y_min)-y);
                        step = 4;          //Mover al paso 4
                    }
                    
                   //Estas tres ramas dan el desplazamiento de coordenadas correspondiente a cada recta.(Para moverse en esa recta)
                    else if(n_x_min == 1){
                        x = x-info[3];
                        y = y-info[2];
                    }
                    else if(n_x_min == 2){
                        x = x-info[6];
                        y = y-info[5];
                    }
                    else if(n_x_min == 3){
                        x = x-info[9];
                        y = y-info[8];
                    }
                }
            }
            //Paso 3(Moverse de intersección en intersección)
            else if(step == 3){
                //Cuando el coeficiente del intercepción en y de la función objetivo es mayor
                if(info[0] <= info[1]){
                    /*
                    //Línea recta 1 que es la intersección restante, cuando se llega a la intersección 
                      de 3 y ese punto es la solución de ejecución
                    */
                    if(select3 == 1 && x_intersection13>0 && x>x_intersection13+100){
                       //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection13+100));
                        y = y-(y - (600-y_intersection13));
                        step=4;          //Move on to step 4
                    }
                   //Línea recta 2 que es la intersección restante, cuando se llega a la intersección de 3 y ese punto es la solución de ejecución
                    else if(select3 == 1 && x_intersection23>0 && x>x_intersection23+100){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection23+100));
                        y = y-(y - (600-y_intersection23));
                        step=4;          //Mover al paso 4
                    }
                    //Línea recta 1 que es la intersección restante, cuando se llega a la intersección de 2 y ese punto es la solución de ejecución
                    else if(select3 == 2 && x_intersection12>0 && x>x_intersection12+100){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection12+100));
                        y = y-(y - (600-y_intersection12));
                        step=4;          //Mover al paso 4 
                    }
                    //Línea recta 2 que es la intersección restante, cuando se llega a la intersección de 3 y ese punto es la solución de ejecución
                    else if(select3 == 2 && x_intersection23>0 && x>x_intersection23+100){
                       //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection23+100));
                        y = y-(y - (600-y_intersection23));
                        step=4;          //Mover al paso 4
                    }
                   //Línea recta 1 que es la intersección restante, cuando se llega a la intersección de 2 y ese punto es la solución de ejecución
                    else if(select3 == 3 && x_intersection12>0 && x>x_intersection12+100){
                       //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection12+100));
                        y = y-(y - (600-y_intersection12));
                        step=4;          //Mover al paso 4
                    }
                    //Línea recta 1 que es la intersección restante, cuando se llega a la intersección de 3 y ese punto es la solución de ejecución
                    else if(select3 == 3 && x_intersection13>0 && x>x_intersection13+100){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x-(x - (x_intersection13+100));
                        y = y-(y - (600-y_intersection13));
                        step=4;          //Mover en el paso 4
                    }
                    
                    //Estas tres ramas dan el desplazamiento de coordenadas correspondiente a cada recta.(Para moverse en esa recta)
                    else if(n_y_min == 1){
                        if(n_x_min == 2){
                            x = x+info[9];
                            y = y+info[8];
                        }
                        else if(n_x_min == 3){
                            x = x+info[6];
                            y = y+info[5];
                        }
                    }
                    else if(n_y_min == 2){
                        if(n_x_min == 1){
                            x = x+info[9];
                            y = y+info[8];
                        }
                        else if(n_x_min == 3){
                            x = x+info[3];
                            y = y+info[2];
                        }
                    }
                    else if(n_y_min == 3){
                        if(n_x_min == 1){
                            x = x+info[6];
                            y = y+info[5];
                        }
                        else if(n_x_min == 2){
                            x = x+info[3];
                            y = y+info[2];
                        }
                    }
                }
                //Cuando el coeficiente del intercepto en y de la función objetivo es menor
                else if(info[0] > info[1]){
                    if(select3 == 1 && y_intersection13>0 && y<600-y_intersection13){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection13+100)-x);
                        y = y + ((600-y_intersection13)-y);
                        step=4;          //Mover al paso 4 
                    }
                    else if(select3 == 1 && y_intersection23>0 && y<600-y_intersection23){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection23+100)-x);
                        y = y + ((600-y_intersection23)-y);
                        step=4;          //Mover al paso 4 
                    }
                    else if(select3 == 2 && y_intersection12>0 && y<600-y_intersection12){
                       //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection12+100)-x);
                        y = y + ((600-y_intersection12)-y);
                        step=4;          //Mover al paso 4
                    }
                    else if(select3 == 2 && y_intersection23>0 && y<600-y_intersection23){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection23+100)-x);
                        y = y + ((600-y_intersection23)-y);
                        step=4;          //Mover al paso 4
                    }
                    else if(select3 == 3 && y_intersection12>0 && y<600-y_intersection12){
                        //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection12+100)-x);
                        y = y + ((600-y_intersection12)-y);
                        step=4;          //Mover al paso 4 
                    }
                    else if(select3 == 3 && y_intersection13>0 && y<600-y_intersection13){
                       //Cuando llegue, corrígelo para que el punto sea exactamente las coordenadas del centro del círculo.
                        x = x + ((x_intersection13+100)-x);
                        y = y + ((600-y_intersection13)-y);
                        step=4;          //Mover al paso 4 
                    }
                    
                    //Estas tres ramas dan el desplazamiento de coordenadas correspondiente a cada recta.(Para moverse en esa recta)
                    else if(n_x_min == 1){
                        if(n_y_min == 2){
                            x = x-info[9];
                            y = y-info[8];
                        }
                        else if(n_y_min == 3){
                            x = x-info[6];
                            y = y-info[5];
                        }
                    }
                    else if(n_x_min == 2){
                        if(n_y_min == 1){
                            x = x-info[9];
                            y = y-info[8];
                        }
                        else if(n_y_min == 3){
                            x = x-info[3];
                            y = y-info[2];
                        }
                    }
                    else if(n_x_min == 3){
                        if(n_x_min == 1){
                            x = x-info[6];
                            y = y-info[5];
                        }
                        else if(n_x_min == 2){
                            x = x-info[3];
                            y = y-info[2];
                        }
                    }
                }
            }
            //Paso 4(punto(Círculo)Deja de moverse y se encuentra la solución óptima. Muestra la solución óptima)
            else if(step == 4){
                //Establezca el color del valor de la solución óptima que se mostrará en negro.
                g.setColor(Color.black);
                
                //Establecer el tamaño de fuente para que sea más fácil de ver
                Font fo = new Font("Serif",Font.PLAIN,20);
                g.setFont(fo);
                
                //Convertir la solución óptima en unidades gráficas a unidades del problema
                double best_answer = ((double)answer_max/d_masu);
                //Convertir el valor de la solución óptima a una cadena
                String ba = String.valueOf(best_answer);
                
                //Mostrar la solución óptima
                g.drawString("La solución óptima más próxima es: ",450,325);
                g.drawString(String.format("%.2f", best_answer), 475, 350);
            }
            
        }
         
        
    }
    
    //Controla el intervalo de ejecución del hilo para mover el círculo
    public void run() {
        while(true) {
            repaint();
            try {
             
            }catch(Exception e) {
                System.out.println(e);
            }
        }
    }
    
    public void actionPerformed(ActionEvent ae){
        //Cuando se hace clic en el boton1
        if(ae.getSource() == button1){
            //Almacenar el valor ingresado desde el campo de texto en la información de la matriz
            info[0] = Integer.parseInt(o_x.getText());
            info[1] = Integer.parseInt(o_y.getText());
            info[2] = Integer.parseInt(s_x1.getText());
            info[3] = Integer.parseInt(s_y1.getText());
            info[4] = Integer.parseInt(s_n1.getText());
            info[5] = Integer.parseInt(s_x2.getText());
            info[6] = Integer.parseInt(s_y2.getText());
            info[7] = Integer.parseInt(s_n2.getText());
            info[8] = Integer.parseInt(s_x3.getText());
            info[9] = Integer.parseInt(s_y3.getText());
            info[10] = Integer.parseInt(s_n3.getText());	
            scale_judge = 1;     //scale_judge=Set en 1 y escriba 3 líneas rectas de restricciones en el gráfico
            
            
            x = 100;
            y = 600;
            
            step = 1;          //Establecer el paso de simulación en 1
            simu_judge = 1;    //simu_Set a 1 e iniciar la simulación
            
        }
      
    }


}