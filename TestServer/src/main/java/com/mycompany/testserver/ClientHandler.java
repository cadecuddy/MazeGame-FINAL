/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 *
 * @author PrinceNiko
 */
public class ClientHandler implements Callable<String> {
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final Socket socket;
    private final String name;
    private String vote;

    public ClientHandler(ObjectInputStream ois, ObjectOutputStream oos, Socket socket, String name) {
        this.ois = ois;
        this.oos = oos;
        this.socket = socket;
        this.name = name;
        
    }

    @Override
    public String toString() {
        return "ClientHandler{" + "name=" + name + '}';
    }

    @Override
    public String call() throws Exception {
        vote = (String) ois.readObject();
        return vote + " " + name;
    }
    
    
}
