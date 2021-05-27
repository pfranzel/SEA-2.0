package de.telekom.sea2.event;

public class Events implements EventRegistration {

	public EventListener listener;

	public void subscribe(EventListener eventListener) {
		System.out.println("Info: I registrated to the EventListener - " + eventListener);
		if (listener != null)
		this.listener = eventListener;
	}

	public void sendAddEvent() {
		Event addEvent = new Event();
		addEvent.description = "An object has been added to the DB";
		if (listener != null)
			this.listener.receive(addEvent);
	}

	public void sendGetEvent() {
		Event sendGetEvent = new Event();
		sendGetEvent.description = "An object has been read from the DB";
		if (listener != null)
			this.listener.receive(sendGetEvent);
	}

	public void sendUpdateEvent(long id) {
		Event sendUpdateEvent = new Event();
		sendUpdateEvent.description = "An updated has been done on ID: " + id;
		if (listener != null)
			this.listener.receive(sendUpdateEvent);
	}

	public void sendFullEvent(long count) {
		Event fullEvent = new Event();
		fullEvent.description = "List full - only (" + count + ") allowed";
		if (listener != null)
			this.listener.receive(fullEvent);
	}

	public void sendRemoveEvent() {
		Event removeEvent = new Event();
		removeEvent.description = "An object has been deleted from the list";
		if (listener != null)
			this.listener.receive(removeEvent);
	}

	public void sendClearEvent() {
		Event clearEvent = new Event();
		clearEvent.description = "Database has been cleared/truncated";
		if (listener != null)
			this.listener.receive(clearEvent);
	}

	public void sendErrEvent(Exception err) {
		Event sendErrEvent = new Event();
		sendErrEvent.description = "User input Error - " + err;
		if (listener != null)
		this.listener.receive(sendErrEvent);
	}

	public void sendUsrErrEvent(String usrErr) {
		Event usrErrEvent = new Event();
		usrErrEvent.description = "User input Error - " + usrErr;
		if (listener != null)
		this.listener.receive(usrErrEvent);
	}

	public void sendDBConnEvent() {
		Event sendDBConnEvent = new Event();
		sendDBConnEvent.description = "DB-Connection successfull created";
		if (listener != null)
		this.listener.receive(sendDBConnEvent);
	}

	public void sendDBConnDestroyEvent() {
		Event sendDBConnDestroyEvent = new Event();
		sendDBConnDestroyEvent.description = "DB-Connection destroyed";
		if (listener != null)
		this.listener.receive(sendDBConnDestroyEvent);
	}

	public void sendDBConnErrEvent() {
		Event sendDBConnErrEvent = new Event();
		sendDBConnErrEvent.description = "DB-Connection not created";
		if (listener != null)
		this.listener.receive(sendDBConnErrEvent);
	}
}