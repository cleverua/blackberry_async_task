package com.cleverua.bb.example;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

import com.cleverua.bb.task.Action;
import com.cleverua.bb.task.RunnableTask;

public class FailureTask extends RunnableTask {
    private static final String TASK_FAILURE_MESSAGE = "Task failed: ";
    private static final String TASK_CANCELED_MESSAGE = "Task canceled!";


    public FailureTask() {
        setJobAction(new Action() {
            public Object execute(Object context) throws Exception {
                Thread.sleep(5000);
                throw new Exception("Failure task needs to crash!");
            }
        });

        setFailureAction(new Action() {
            public Object execute(Object context) throws Exception {
                final String message = TASK_FAILURE_MESSAGE + (Exception)context;
                UiApplication.getUiApplication().invokeLater(new Runnable() {
                    public void run() {
                        Dialog.alert(message);
                    }
                });
                return null;
            }
        });

        setCancelAction(new Action() {
            public Object execute(Object context) throws Exception {
                hideProgress();
                stopTask();
                UiApplication.getUiApplication().invokeLater(new Runnable() {
                    public void run() {
                        Dialog.alert(TASK_CANCELED_MESSAGE);
                    }
                });
                return null;
            }
        });
    }
}
