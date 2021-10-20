import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * klasa panelu, w nim deklaracja tablicy klasy myRec,
 * przechowujacej obiekty dziedziczace po klasie Rectangle2D.Double
 * zaleta tablicy jako struktury jest natychmiastowy dostep
 * do kazdego z jej pol
 */
public class Panel extends JPanel {
    /**
     * Tablica obiektow klasy myRec.
     */
    public myRec[][] board;
    /**
     * Wspolrzedna x pierwszego kwadratu.
     */
    double x = 0;
    /**
     * Wspolrzedna y pierwszego kwadratu.
     */
    double y = 0;
    int n;
    int m;
    int k;
    double p;
    int d;

    /**
     * konstruktor panelu, w nim wypelnienie tablicy
     * obiektami klasy myRec
     * @param n liczba wierszy
     * @param m liczba kolumn
     * @param k dlugosc cyklu wprowadzona jako argument programu
     * @param p prawdopodobienstwo wprowadzone jako argument programu
     * @param d wielkosc kwadratu
     */
    public Panel(int n, int m, int k, double p, int d){
        this.n = n;
        this.m = m;
        this.k = k;
        this.p = p;
        this.d = d;
        setSize(n*d,m*d);
        addMouseListener(new HitTestAdapter());

        this.board = new myRec[n][m];
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(x == n*d) {x = 0;}
                this.board[j][i] = new myRec(x, y, d, d, k, p, j, i, this, new myMonitor());
                x += d;
            }
            y += d;
        }

    }

    /**
     * Metoda rysujaca kazde z pol w tablicy.
     *
     * @param g obiekt graficzny na ktorym wywolywana jest metoda
     */
    private void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++){
                g2d.setPaint(new Color(board[j][i].r, board[j][i].g, board[j][i].b));
                g2d.fill(board[j][i]);

            }
    }

    /**
     * Metoda paintComponent.
     * @param g obiekt graficzny na ktorym wywolywana jest metoda
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * Wewnetrzna klasa dziedziczaca po MouseAdapter,
     * reagujaca na przycisniecie kwadratu.
     */
    class HitTestAdapter extends MouseAdapter {
        /**
         * Metoda zsynchronizowana dla watkow, reaguje na nacisniecie przycisku
         * myszy, a nastepnie sprawdza dla kazdego obiektu z tabeli, czy zostal
         * wcisniety, o czym swiadczy pole typu bool flag, jesli dany punkt zawiera
         * sie w kwadracie, wywouluje metode wakeup.
         * @param ev zdarzenie, dla ktorego wywolywana jest metoda
         */
        @Override
        public synchronized void mousePressed(MouseEvent ev){

            for(int i = 0; i < m; i++)
                for(int j = 0; j < n; j++){
                    if(board[j][i].contains(ev.getPoint())) {
                        if(board[j][i].monitor.flag) {
                            try {
                                board[j][i].monitor.flag = false;
                                board[j][i].monitor.wakeup();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            try{
                                board[j][i].monitor.flag = true;
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }

                    }
                }

        }
    }
}
