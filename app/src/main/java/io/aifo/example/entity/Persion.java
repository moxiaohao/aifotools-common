package io.aifo.example.entity;


import io.aifo.api.javassist.Inject;
import io.aifo.api.apt.InstanceFactory;

public class Persion {

//    @Inject
    public School school;
    private String name ;

    public Persion(){
    }
    public Persion(String name){
        this.name = name ;
    }
    public void printSelf() {
        System.out.println("I got you here "+name+";!");
    }

}
