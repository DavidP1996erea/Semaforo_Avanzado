import java.util.concurrent.Semaphore;

   public class Main implements Runnable {
        // Se crea el objeto tipo Semaphore, y se le pone un límite de 4
        Semaphore charcutería = new Semaphore(2);
       Semaphore carnicería = new Semaphore(4);
        public static void main(String[] args)  {


            // Se crean los 10 hilos
            Main sb = new Main();
            for(int i=0; i<10; i++) {
                new Thread(sb).start();

            }

        }

        /**
         * Si hay hueco en la carnicería los hilos se van metiendo ahí y en caso contario se meten en la charcutería,
         * lo mismo pasa al revés. Cuando entran en cada uno de ellos, dependiendo si es la carnicería o la
         * charcutería se pondrá una variable a true, para indicar que ya fue atendido ahí. Una vez que es atendido
         * en ambos sitios, se interrumpe el hilo.
         */
        @Override
        public void run() {

            boolean esAtendidoCarniceria=false;
            boolean esAtendidoCharcuteria=false;

                    if(carnicería.availablePermits()!=0) {
                        carniceria();

                        esAtendidoCarniceria=true;

                    }else {
                        charcuteria();
                        esAtendidoCharcuteria=true;
                    }

                    if(charcutería.availablePermits()!=0){
                        charcuteria();
                        esAtendidoCharcuteria=true;
                    }else {
                        carniceria();

                        esAtendidoCarniceria=true;
                    }

                    if(esAtendidoCarniceria && esAtendidoCharcuteria){
                        Thread.interrupted();
                    }


        }


       /**
        * Método para que ser atendido en la carniceríaa
        */
       public void carniceria(){
            try {
                carnicería.acquire();

            System.out.println(Thread.currentThread().getName() + "Está siendo atendido en la Carnicería");
            Thread.sleep((int) (Math.random() * 10000));
            System.out.println(Thread.currentThread().getName() + "Ha terminado de ser atendido en la Carnicería");
            carnicería.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

       /**
        * Igual que el anterior para ser atendido en la charcutería
        */

       public  void charcuteria(){
            try {
                charcutería.acquire();
                System.out.println(Thread.currentThread().getName() + "Está siendo atendido en la Charcutería");
                Thread.sleep((int) (Math.random() * 10000));
                System.out.println(Thread.currentThread().getName() + "Ha terminado de ser atendido en la Charcutería");
                charcutería.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }