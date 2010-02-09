package cleverua.bb.example;

import cleverua.bb.example.ui.AsyncTaskScreen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

public class AsyncTaskApplication extends UiApplication {
	private static final String NOT_IMPLEMENTED_MSG = "Not yet implemented";
	private static AsyncTaskApplication application;

	public static void main(String[] args) {
		application = new AsyncTaskApplication();
		application.pushScreen(new AsyncTaskScreen());
		application.enterEventDispatcher();
	}

	public static AsyncTaskApplication instance() {
		return application;
	}

	public static void exit() {
		System.exit(0);
	}

	public void showNotImplementedAlert() {
		invokeLater(new Runnable() {
			public void run() {
				Dialog.alert(NOT_IMPLEMENTED_MSG);
			}
		});
	}
}
