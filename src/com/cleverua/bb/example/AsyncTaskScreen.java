package cleverua.bb.example;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.MainScreen;

public class AsyncTaskScreen extends MainScreen {
	private static final String ASYNC_TASK_SCREEN_TITLE = "Async Task Demo";
	private static final String START_FAILURE_TASK_LABEL = "Start failure task";
	private static final String START_SUCCESS_TASK_LABEL = "Start success task";
	private static final String SUCCESSFULL_TASK_RUNNING_MESSAGE = "Successfull task is running...";
	private static final String FAILURE_TASK_RUNNING_MESSAGE = "Failure task is running...";	
	
	private ButtonField successTaskButton;
	private ButtonField failureTaskButton;

	public AsyncTaskScreen() {
		super();
		initUI();
	}

	private void initUI() {
		setTitle(ASYNC_TASK_SCREEN_TITLE);
		successTaskButton = new ButtonField(START_SUCCESS_TASK_LABEL, FIELD_HCENTER);
		successTaskButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field arg0, int arg1) {
				new SuccessfullTask().process(SUCCESSFULL_TASK_RUNNING_MESSAGE);
			}
		});
		failureTaskButton = new ButtonField(START_FAILURE_TASK_LABEL, FIELD_HCENTER);
		failureTaskButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field arg0, int arg1) {
				new FailureTask().process(FAILURE_TASK_RUNNING_MESSAGE);	
			}
		});
		add(successTaskButton);
		add(failureTaskButton);
	}
	
	protected boolean onSavePrompt() {
		return true;
	}
}
