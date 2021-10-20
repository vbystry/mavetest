
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Klasa pojedynczego kwadratu, implementujaca interfejs
 * obslugi watkow
 */
public class myRec extends Rectangle2D.Double implements Runnable {

    public int r, g, b;
    double probability;
    double speed;
    double proba;
    int infimum;
    int coordn, coordm;
    Panel panel;
    public myMonitor monitor;
    Random generator = new Random();
    Thread t;

    /**
     * Konstrutktor przyjmujacy parametry podane wczesniej w konstrultorze Panel,
     * a takze podstawowe parametry konstruktora Rectangle2D.Double oraz parametry:
     * coordm, coordn (pozycja danego obiektu w tablicy), panel (panel, do ktorego przypisano obiekt)
     * oraz obiekt klasy monitor, potrzebny do obslugi watkow.
     * @param x polozenie kwadratu w osi x
     * @param y polozenie kwadratu w osi y
     * @param width szerokosc kwadratu
     * @param height wysokosc kwadratu
     * @param k dlugosc cyklu uspienia watku
     * @param p prawdopodobienstwo zmiany koloru watku
     * @param coordn pozycja w kolumnie tablicy
     * @param coordm pozycja w wierszu tablicy
     * @param panel panel, do ktorego przypisany jest obiekt
     * @param monitor monitor, ktory dla danego watku wywoluje odpowiednie metody
     */
    public myRec(double x, double y, double width, double height, int k, double p, int coordn, int coordm, Panel panel, myMonitor monitor){
        super(x,y,width,height);
        /**
         * Utworzenie watku, jako pola w obiekcie myRec.
         */
        t = new Thread(this);
        /**
         * Uruchomienie watku.
         */
        t.start();
        this.infimum = k;
        this.probability = p;
        this.coordm = coordm;
        this.coordn = coordn;
        this.panel = panel;
        this.monitor = monitor;
    }

    /**
     *
     * @param a wartosc 1
     * @param b wartosc 2
     * @param c wartosc 3
     * @param d wartosc 4
     * @return Srednia arytmetyczna 4 zmiennych typu int, w programie uzyta jako
     * srednia jednego z parametrow koloru 4 kwadratow otaczajacych dany obiekt.
     */
    public int averagre4(int a, int b, int c, int d){
        int av = (a + b + c + d)/4;
        return av;
    }

    /**
     * Metoda zsynchronizowana run, w niej rozstrzygniecie prawdobodobienstwa
     * zmiany koloru, losowanie chwilowej dlugosci cyklu dzialania watku
     * pola r, g, b oznaczaja wartosci calkowitoliczbowe barw podstawowych
     * obiektu klasy Color.
     * W pierwszej instrukcji warunkowej wywolana zostaje metoda gowainting
     * z klasy myMonitor, zawieszajaca dzialanie watku.
     * Po drugiej instrukcji warunkowej metoda wylicza srednie arytmetyczne
     * parametrow kolorow sasiednich obiektow, dzieki dzieleniu modulo plansza ma strukture torusa.
     * Obsluga wyjatkow sluzy naprawie dzialania kwadratow, dla ktorych nie wszystkie sasiadujace
     * obiekty zostaly utworzone (awaria pierwszego wiersza tablicy dla m = 0), z uwagi na zerowanie wartosci r, g, b
     * w obsludze wyjatkow, symulacja poczatkowo przybiera czarny kolor
     */
    @Override
    public synchronized void run() {
        while(true){
            speed = generator.nextDouble();
            speed += 0.5;
            speed *= infimum;
            proba = generator.nextDouble();
            if(monitor.flag){
                try{
                    monitor.goWaiting();
                }catch(Exception exc){
                    exc.printStackTrace();
                }
            }
            if(proba <= probability) {
                r = generator.nextInt(225);
                g = generator.nextInt(255);
                b = generator.nextInt(255);

            }
            else{
                int a,x,c,d;
                try {
                    a = panel.board[(coordn + 1) % panel.n][(coordm) % panel.m].r;
                    x = panel.board[(coordn) % panel.n][(coordm + 1) % panel.m].r;
                    c = panel.board[(coordn + panel.n - 1) % panel.n][(coordm) % panel.m].r;
                    d = panel.board[(coordn) % panel.n][(coordm + panel.m - 1) % panel.m].r;
                }catch(NullPointerException e){
                    // System.out.println(coordm + " " + coordn);
                    a = 0;
                    x = 0;
                    c = 0;
                    d = 0;
                }
                r = averagre4(a,x,c,d);

                try {
                    a = panel.board[(coordn + 1) % panel.n][(coordm) % panel.m].g;
                    x = panel.board[(coordn) % panel.n][(coordm + 1) % panel.m].g;
                    c = panel.board[(coordn + panel.n - 1) % panel.n][(coordm) % panel.m].g;
                    d = panel.board[(coordn) % panel.n][(coordm + panel.m - 1) % panel.m].g;
                }catch(NullPointerException e){
                    // System.out.println(coordm + " " + coordn);
                    a = 0;
                    x = 0;
                    c = 0;
                    d = 0;
                }
                g = averagre4(a,x,c,d);

                try {
                    a = panel.board[(coordn + 1) % panel.n][(coordm) % panel.m].b;
                    x = panel.board[(coordn) % panel.n][(coordm + 1) % panel.m].b;
                    c = panel.board[(coordn + panel.n - 1) % panel.n][(coordm) % panel.m].b;
                    d = panel.board[(coordn) % panel.n][(coordm + panel.m - 1) % panel.m].b;
                }catch(NullPointerException e){
                    // System.out.println(coordm + " " + coordn);
                    a = 0;
                    x = 0;
                    c = 0;
                    d = 0;
                }
                b = averagre4(a,x,c,d);

            }
            panel.repaint();
            try {

                Thread.sleep((long)speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
