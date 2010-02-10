package cleverua.bb.example;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import cleverua.bb.task.Action;
import cleverua.bb.task.RunnableTask;

public class SuccessfullTask extends RunnableTask {
	private static final String TASK_FAILURE_MESSAGE = "Task failed: ";
	private static final String TASK_CANCELED_MESSAGE = "Task canceled!";
	private static final String TASK_SUCCESSFULL_MESSAGE = "Task is successfully completed!";
	private static final int SLEEP_DELAY = 5000;

	public SuccessfullTask() {
		setJobAction(new Action() {
			public Object execute(Object context) throws Exception {
				Thread.sleep(SLEEP_DELAY);
				return null;
			}
		});
		
		setSuccessAction(new Action() {
			public Object execute(Object context) throws Exception {
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						Dialog.alert(TASK_SUCCESSFULL_MESSAGE);
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
	}
}
