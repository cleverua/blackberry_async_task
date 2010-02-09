package cleverua.bb.example.task_demo;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import cleverua.bb.task.ReturningResultRunnableTaskAction;
import cleverua.bb.task.RunnableTask;
import cleverua.bb.task.RunnableTaskAction;

public class SuccessfullTask extends RunnableTask {
	private static final String TASK_CANCELED_MESSAGE = "Task was canceled!";
	private static final String TASK_FAILURE_MESSAGE = "Task was failure: ";
	private static final String TASK_SUCCESSFULL_MESSAGE = "Task is successfully completed!";
	private static final int SLEEP_DELAY = 5000;

	public void process(String popupText) {
		setJobAction(new ReturningResultRunnableTaskAction() {
			public Object execute(Object context) throws Exception {
				Thread.sleep(SLEEP_DELAY);
				return null;
			}
		});
		
		setSuccessAction(new RunnableTaskAction() {
			public void execute(Object context) throws Exception {
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						Dialog.alert(TASK_SUCCESSFULL_MESSAGE);
					}
				});
			}
		});
		
		setCancelAction(new RunnableTaskAction() {
			public void execute(Object context) throws Exception {
				hideProgress();
				stopTask();
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						Dialog.alert(TASK_CANCELED_MESSAGE);
					}
				});
			}
		});
		
		setFailureAction(new RunnableTaskAction() {
			public void execute(Object context) throws Exception {
				final String message = TASK_FAILURE_MESSAGE + (Exception)context;
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						Dialog.alert(message);
					}
				});
			}
		});
		
		super.process(popupText);
	}
}
