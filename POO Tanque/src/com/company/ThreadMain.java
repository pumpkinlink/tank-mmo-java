package com.company;

/**
 * Created by denis on 13/11/15.
 */
public class ThreadMain  implements Runnable {
Cliente c;

    public ThreadMain(Arena a) throws Exception {
        this.c = new Cliente(a);
    }




    @Override
    public void run() {
        try {
            c.iniciarEscutar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
