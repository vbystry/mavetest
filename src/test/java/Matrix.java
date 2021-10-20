/**
 * <h1>Matrix</h1>
 *
 * @author Piotr Korycki
 * @version 1.3
 */

public class Matrix {
    /**
     * Metoda glowna, w niej tablica z parametrami calkowitoliczbowymi
     * oraz zmienna zmiennoprzecinkowa przechowujaca prawdopodobienstwo.
     * @param args argumenty programu
     */
    public static void main(String[] args){

        int[] integers = new int[args.length];
        for(int i = 0; i < args.length - 1; i++){
            integers[i] = Integer.parseInt(args[i]);
        }
        double p = Double.parseDouble(args[3]);
        Frame frame = new Frame(integers[0], integers[1], integers[2], p);


    }
}
