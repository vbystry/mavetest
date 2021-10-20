/**
 * Klasa monitorujaca dzialanie watku, z polem
 * informujacym o wykonaniu na nim akcji oraz metodami
 * zatrzymywania i ponownego uruchamiania watkow.
 */
public class myMonitor {
    public boolean flag;

    /**
     * Konstruktor z przypisaniem pola, oznaczajacego brak
     * zaangazowania watku.
     */
    myMonitor(){
        this.flag = false;
    }

    /**
     * Metoda zsynchronizowana, zawieszajaca dzialanie watku.
     */
    public synchronized void goWaiting(){
        try{
            wait();

        }catch(Exception e){
            e.printStackTrace();
        }


    }

    /**
     * Metoda zsynchronizowana, przywracajaca dzialanie watku.
     */
    public synchronized void wakeup() {
        notify();
    }
}
