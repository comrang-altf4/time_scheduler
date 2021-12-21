import java.util.*;

class Event {
	private int event_Id;
	private String event_name;
	private String event_location;
	private int event_duration;
	private int event_priority;// red=0,yellow=1,green=2;

	Calendar event_date;

	Event() {
		this.event_Id = ID_management.getID();
		this.event_date = Calendar.getInstance();
	}

	void getInfo() {
		System.out.println(
				this.event_duration + "\n" + this.event_name + "\n" + this.event_date.getTime() + "\n"
						+ this.event_location + "\n"
						+ this.event_priority + "\n");
	}

	int getID() {
		return this.event_Id;
	}

	String getLocation() {
		return this.event_location;
	}

	String getName() {
		return this.event_name;
	}

	Calendar getDate() {
		return this.event_date;
	}

	int getDuration() {
		return this.event_duration;
	}

	int getPriority() {
		return this.event_priority;
	}

	void update() {
		String n = "from text box";
		String l = "from text box";
		int d = 1;// from timer box
		int day = 2;// from set date box
		int month = 2;// from set date box
		int year = 2;// from set date box
		int hour = 2;// from set date box
		int minute = 2;// from set date box
		int p = 3;// from priority box
		this.event_duration = d;
		this.event_name = n;
		this.event_location = l;
		this.event_priority = p;
		this.event_date.set(year, month, day, hour, minute);
	}

	public boolean equals(Event e) {
		return this.event_Id == e.event_Id;
	}
}