import java.util.*;

class Calendars {
	public List<Event> calendar_Events;

	Calendars() {
		this.calendar_Events = new ArrayList<Event>();
		// load event from log or db
	}

	void addEvent(Event event) {
		this.calendar_Events.add(event);
	}

	void addEvent() {
		Event event = new Event();
		event.update();
		this.calendar_Events.add(event);
	}

	void deleteEvent(Event event) {
		ID_management.deleteID(event.getID());
		this.calendar_Events.remove(event);
	}

	void updateToDB() {
		ID_management.updateToDB(this.calendar_Events);
	}
}