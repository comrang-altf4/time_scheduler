package backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IdentityManagement {
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
		Sess1on.eventList.removeIf(x->x.getID()==ID);
		System.out.println("ID released");
	}

	public static void updateToDB() throws SQLException,ClassNotFoundException {
		int curMaxID=Database.getLastInsertID();
		for (int i=0;i<Sess1on.eventList.size();i++) {
			if (Sess1on.eventList.get(i).getID() >= 1000000)
			{
				curMaxID+=1;
				Sess1on.eventList.get(i).setId(curMaxID);
				System.out.println("Aaaa");
				Database.addEvent(Sess1on.eventList.get(i));
			}
			else Database.updateEvent(Sess1on.eventList.get(i));
			System.out.println(Sess1on.eventList.get(i).getID());
		};
		System.out.println(available_id);
		Database.deleteEvents(available_id);
	}
}
