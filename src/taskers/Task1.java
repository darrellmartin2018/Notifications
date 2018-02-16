/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import javafx.application.Platform;
import notificationexamples.TaskState;
import notificationexamples.NotificationsUIController;

/**
 *
 * @author Professor Wergeles
=======
import notifcationexamples.TaskState;

/**
 *
 * @author dalemusser
 * 
 * This example uses an object passed in with a notify()
 * method that gets called when a notification is to occur.
 * To accomplish this the Notifiable interface is needed.
 * 
>>>>>>> finished
 */
public class Task1 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    TaskState state = TaskState.STOPPED;
    private Notifiable notificationTarget;
    
    public Task1(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        doNotify("Task1 start.");
        state = TaskState.RUNNING;
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task1: " + i);
            }
            
            if (exit) {
                return;
            }
        }
        doNotify("Task1 done.");
        state = TaskState.STOPPED;
    }
    
    public void end() {
        exit = true;
    }
    
    public void setNotificationTarget(Notifiable notificationTarget) {
        this.notificationTarget = notificationTarget;
    }
    
    private void doNotify(String message) {
        // this provides the notification through a method on a passed in object (notificationTarget)
        if(notificationTarget != null){
            Platform.runLater(() -> {
                notificationTarget.notify(message);
            });
        }
    }

}
