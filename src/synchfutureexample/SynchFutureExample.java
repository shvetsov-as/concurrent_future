/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package synchfutureexample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author User
 */
public class SynchFutureExample implements Callable<String> {

    static boolean done[] = new boolean[100];
    
    
    
    @Override
    public String call() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            System.out.println("son prervan");
        }
        return Thread.currentThread().getName();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(5);// sozdanie pula potokov na 5 shtyk, on mojet raspred zadachi po potokam

        //sozdaem spisok objektov Future
        TreeMap<Integer, Future> list = new TreeMap<>();

        SynchFutureExample sfe = new SynchFutureExample();

        for (int i = 0; i < 100; i++) {
            Future <String> future = executor.submit(sfe);
            list.put(i, future);
        }
        Set<Map.Entry<Integer, Future>> eset = list.entrySet();
        
        int counter = 0;
        while(counter < 100)
        for (Map.Entry<Integer, Future> f : eset) {
            if (done[f.getKey().intValue()]) continue;
            if (  f.getValue().isDone()) {//zakonchil li potok raboty

                System.out.println((new Date()).toString() + " N zadachi  " + f.getKey() + " Potok : " + f.getValue().get());
                counter ++;
                done[f.getKey().intValue()] = true;
            }
            
        }
        //System.out.println("Propushinie zadachi");
        //executor.shutdown();
    }
}
