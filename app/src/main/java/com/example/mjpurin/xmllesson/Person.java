package com.example.mjpurin.xmllesson;

/**
 * Created by mjpurin on 2015/09/01.
 */
public class Person {
    String name;
    String email;

    @Override
    public String toString() {
        return String.format("名前:%s\nemail:%s",name,email);
    }
}
