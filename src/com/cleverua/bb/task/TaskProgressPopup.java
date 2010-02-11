package com.cleverua.bb.task;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.DialogFieldManager;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;

public class TaskProgressPopup {

    private PopupScreen screen;
    private RunnableTask task;
    private Thread thread;
    RichTextField dialogTitle;

    public TaskProgressPopup(final RunnableTask task, String text) {
        this.task = task;
        this.thread = new Thread(task);

        DialogFieldManager manager = new DialogFieldManager();
        HorizontalFieldManager hfm = new HorizontalFieldManager();

        this.screen = new PopupScreen(manager);

        ButtonField cancelButton = new ButtonField("Cancel");

        dialogTitle = new RichTextField(text, LabelField.NON_FOCUSABLE | LabelField.HCENTER);

        manager.setMessage(dialogTitle);
        hfm.add(cancelButton);
        manager.addCustomField(hfm);

        cancelButton.setChangeListener(new FieldChangeListener() {
            public void fieldChanged(Field arg0, int arg1) {
                task.cancel();
            }
        });
    }

    public PopupScreen getScreen() {
        return screen;
    }

    public RunnableTask getTask() {
        return task;
    }

    public Thread getThread() {
        return thread;
    }

    public void setDialogTitle(String newTitle) {
        dialogTitle.setText(newTitle);
        screen.invalidate();
    }

}
