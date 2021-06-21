package com.szy.javapoemtest;

import android.util.Log;

/**
 * Created by devov on 2021/3/2.
 */

public class ThreadTest {
    private Object obj = new Object();

    String test3(){
      new Thread(Thread.currentThread().getThreadGroup(),()->{
          throw new RuntimeException();
      }).start();
      return "";
    }
    private String getStr(){
        Log.i("RRORK","getStr invoke");
        return "SDS";
    }

    void test(){
        Thread thread1 = new Thread(() -> {
            synchronized (obj) {
                Log.i("RRORE", "thread1 start");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("RRORE", "thread1 end");
            }
        });
        thread1.start();
    }

    void test2(){
        Thread thread2 = new Thread(() -> {
            synchronized (obj){
                Log.i("RRORE","thread2 start");
                obj.notify();

                Log.i("RRORE","thread2 end");
            }

        });
        thread2.start();
    }
}
