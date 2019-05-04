package com.company;

import com.sun.org.apache.xpath.internal.SourceTree;

public class Main {

    public static void main(String[] args){

        int rounds=1;

        System.out.println(String.valueOf(rounds));

        Chopstick[] chopstick = new Chopstick[5];


        for(int i=0; i< chopstick.length; i++){
            chopstick[i] = new Chopstick("Chopistic: "+i);
        }
        Philosopher[] philosophers = new Philosopher[5];

        philosophers[0] = new Philosopher("Philosopher: 0 - ", chopstick[0], chopstick[1], rounds);
        philosophers[1] = new Philosopher("Philosopher: 1 - ", chopstick[1], chopstick[2], rounds);
        philosophers[2] = new Philosopher("Philosopher: 2 - ", chopstick[2], chopstick[3], rounds);
        philosophers[3] = new Philosopher("Philosopher: 3 - ", chopstick[3], chopstick[4], rounds);
        philosophers[4] = new Philosopher("Philosopher: 4 - ", chopstick[0], chopstick[4], rounds);

        for(int j=0;j<philosophers.length;j++){
            System.out.println("Thread "+ j + " has started");
            Thread thread= new Thread( philosophers[j]);
            thread.start();
        }
    }
}


class Philosopher extends Thread
{
    private Chopstick leftChopstick;
    private Chopstick rightChopstick;

    private String Philosopher_name;
    private int state;
    private int _rounds;

    public Philosopher ( String name, Chopstick left, Chopstick right, int rounds){
        this.state = 1;
        this.Philosopher_name = name;
        leftChopstick = left;
        rightChopstick = right;
        _rounds = rounds;
    }

    public void start_Eat()
    {
        if(! leftChopstick.used){
            if(!rightChopstick.used){
                leftChopstick.get_Chopstick();
                rightChopstick.get_Chopstick();

                Log.msg(Philosopher_name + " : Eat");

                Log.Delay(1000);

                rightChopstick.put_Chopstick();
                leftChopstick.put_Chopstick();
            }
        }
        think();
    }

    public void think(){
        this.state = 1;
        Log.msg(Philosopher_name + " : Think");
        Log.Delay(1000);

    }

    public void run(){
        for(int i=0; i<=_rounds; i++){
            start_Eat();
        }
    }
}

class Chopstick{

    public boolean used;
    public String Chopstick_name;

    public Chopstick(String c_Name){
        this.Chopstick_name = c_Name;
    }

    public synchronized void get_Chopstick() {
        Log.msg ("Used_Chopstick :: " + Chopstick_name );
        this.used = true;
    }
    public synchronized void put_Chopstick() {
        Log.msg ("Released_Chopstick :: " + Chopstick_name );
        this.used = false ;
    }
}

class Log{

    public static void msg(String msg){

        System.out.println(msg);
    }
    public static void Delay(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException ex){ }
    }
}

