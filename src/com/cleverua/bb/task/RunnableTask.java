package cleverua.bb.task;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.PopupScreen;

public class RunnableTask implements Runnable {

	protected boolean canceled = false;
	private ReturningResultRunnableTaskAction jobAction;
	private RunnableTaskAction successAction;
	private RunnableTaskAction cancelAction;
	private RunnableTaskAction failureAction;
	private Thread processThread;
	private TaskProgressPopup taskProgressPopup;
	private Object result = null;

	public void run() {
		try {
			// 1. Invoke main job block
			result = jobAction.execute(null);

			// 2. If we are not cancelled - invoke successblock
			if (!canceled && successAction != null) {
				hideProgress();
				successAction.execute(null);
			}
		} catch (InterruptedException e) {
			/* That's okay to happen */
		} catch (Exception e) {
			// 3. Invoke failure block here
			try {
				if (!canceled) {
					hideProgress();
					failureAction.execute(e);
				}
			} catch (Exception ex) { /* n/a */
			}
		}
	}

	public void process(String popupText) {
		taskProgressPopup = new TaskProgressPopup(this, popupText);
		processThread = taskProgressPopup.getThread();
		
		processThread.start();
		
		final PopupScreen screen = taskProgressPopup.getScreen();
		setScreenVisible(screen);
	}

	public void cancel() {
		this.canceled = true;
		if (cancelAction != null) {
			try { cancelAction.execute(null); } catch (Exception ex) { /* not applicable here */}
		}
	}

	public void setJobAction(ReturningResultRunnableTaskAction block) {
		this.jobAction = block;
	}

	public void setSuccessAction(RunnableTaskAction block) {
		this.successAction = block;
	}

	public void setCancelAction(RunnableTaskAction block) {
		this.cancelAction = block;
	}

	public void setFailureAction(RunnableTaskAction block) {
		this.failureAction = block;
	}

	public boolean isCanceled() {
		return canceled;
	}
	
	// it's a good candidate to be extracted into some kind of utility class
	private void setScreenVisible(final Screen screen) {
        UiApplication.getApplication().invokeLater(new Runnable() {
            public void run() {
            	UiApplication.getUiApplication().pushScreen(screen);
            }
        });
    }
	
	public void hideProgress() {
        UiApplication.getUiApplication().invokeLater(new Runnable() {
            public void run() {
                
                boolean keepClosing = true;
                
                while(keepClosing) {
                    Screen activeScreen = UiApplication.getUiApplication().getActiveScreen();
                    if (activeScreen.getClass() == PopupScreen.class) {
                        UiApplication.getUiApplication().popScreen(activeScreen);
                    }
                    else if(activeScreen.getClass() == Dialog.class) {
                        ((Dialog)activeScreen).cancel();
                    }
                    else {
                        keepClosing = false;
                    }
                }
            }
        });
    }
	
	public void stopTask() {
        if (processThread.isAlive()) {
            processThread.interrupt();
        }
        processThread = null;
    }

	public Object getResult() {
		return result;
	}
	
	public void setProgressPopupTitle(final String title) {
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				taskProgressPopup.setDialogTitle(title);
			}
		});
	}

}
