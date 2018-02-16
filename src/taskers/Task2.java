/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.util.ArrayList;
import javafx.application.Platform;
import notificationexamples.TaskState;

/**
 *
 * @author Professor Wergeles
 * 
 * @notes This example uses a Notification functional interface.
=======
import java.util.ArrayList;
import javafx.application.Platform;
import notifcationexamples.TaskState;

/**
 *
 * @author dalemusser
 * 
 * This example uses a Notification functional interface.
>>>>>>> finished
 * This allows the use of anonymous inner classes or
 * lambda expressions to define the method that gets called
 * when a notification is to be made.
 */

public class Task2 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    
    private Notification notification;
    TaskState state = TaskState.STOPPED;
    
    public Task2(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        doNotify("Started Task2!");
        state = TaskState.RUNNING;
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task2: " + i);
            }
            
            if (exit) {
                return;
            }
        }
        doNotify("Task2 done.");
        state = TaskState.STOPPED;
    }
    
    public void end() {
        exit = true;
    }
    
    // this method allows a notification handler to be registered to receive notifications
    public void setOnNotification(Notification notification) {
        this.notification = notification;
    }
    
    private void doNotify(String message) {
        // this provides the notification through the registered notification handler
        if(notification != null){
            Platform.runLater(() -> {
                notification.handle(message);
            });
        }
    }
}