package cleverua.bb.example.task_demo;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import cleverua.bb.task.ReturningResultRunnableTaskAction;
import cleverua.bb.task.RunnableTask;
import cleverua.bb.task.RunnableTaskAction;

public class FailureTask extends RunnableTask {
	private static final String TASK_FAILURE_MESSAGE = "Task failed: ";
	private static final String TASK_CANCELED_MESSAGE = "Task canceled!";
	
	public void process(String popupText) {		
		setJobAction(new ReturningResultRunnableTaskAction() {
			public Object execute(Object context) throws Exception {
				Thread.sleep(5000);
				throw new Exception("Failure task needs to crash!");
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
		
		super.process(popupText);
	}
}
