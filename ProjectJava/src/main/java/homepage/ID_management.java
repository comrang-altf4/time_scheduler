package homepage;

import java.util.*;

public class ID_management {
	private static int base_id = 1000000;// use for local ID, local ID is from 1000000

	private static List<Integer> available_id = new ArrayList<Integer>();

	public static int getID() {
		if (available_id.size() != 0) {
			int temp = available_id.get(available_id.size() - 1);
			available_id.remove(available_id.size() - 1);
			return temp;
		}
		base_id += 1;
		return base_id;
	}

	public static void deleteID(int ID) {
		if (ID < 1000000)
			available_id.add(ID);
		System.out.println("ID released");
	}

	public static void updateToDB(List<Event> calendar_Events) {
		calendar_Events.forEach((temp) -> {
			if (temp.getID() >= 1000000)
				;// assign ID from DB to event
			// push to DB
		});
		available_id.forEach((temp) -> {
			// remove temp.getID() from usedID in DB, add to available ID
		});
	}
}
