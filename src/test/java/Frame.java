import javax.swing.*;

/**
 * Klasa dziedziczaca ramke, w niej dynamiczne dopasowanie do
 * zawartego w niej panelu.
 */
public class Frame extends JFrame {

    /**
     * Konstruktor ramki przyjmujacay parametry wielkosci planszy,
     * dlugosci cyklu, prawdopodobienstwa zmiany koloru oraz wielkosc
     * kwadratow, z ktoych zbudowana zostanie plansza.
     * @param n liczba wierszy
     * @param m liczba kolumn
     * @param k dlugosc cyklu wprowadzona jako argument programu
     * @param p prawdopodobienstwo wprowadzone jako argument programu
     */
    public Frame(int n, int m, int k, double p){
        super("Matrix");
        int d = 10;
        setSize(n*d + 13,m*d + 39);
        setLocation(700,400);
        setVisible(true);
        setDefaultCloseOperation(3);
        add(new Panel(n, m, k, p, d));

    }
}
