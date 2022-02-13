package backend;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allocate and deallocate user event ID
 * @author comrang-altf4
 */
public class IdentityManagement {
	private static int base_id = 1000000;// use for local ID, local ID is from 1000000
	private static List<Integer> available_id = new ArrayList<Integer>();

	/**
	 * generates a local user event ID
	 * @return	ID
	 */
	public static int getID() {
		if (available_id.size() != 0) {
			int temp = available_id.get(available_id.size() - 1);
			available_id.remove(available_id.size() - 1);
			return temp;
		}
		base_id += 1;
		return base_id;
	}

	/**
	 * deletes an user event with specific ID
	 * @param ID ID of wanted user event
	 */
	public static void deleteID(int ID) {
		if (ID < 1000000)
			available_id.add(ID);
		Sess1on.eventList.removeIf(x->x.getID()==ID);
		System.out.println("ID released");
	}

	/**
	 * push any updates to the database
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws MessagingException
	 */
	public static void updateToDB() throws SQLException, ClassNotFoundException, MessagingException {
		int curMaxID=Database.getLastInsertID();
		for (int i=0;i<Sess1on.eventList.size();i++) {
			if (Sess1on.eventList.get(i).getID() >= 1000000)
			{
				curMaxID+=1;
				Sess1on.eventList.get(i).setId(curMaxID);
				Email.sendAddParticipantNotification(Sess1on.getEventList().get(i));
				Database.addEvent(Sess1on.eventList.get(i));
			}
			else {
				boolean flag = true;
				List<Event> olds = Database.getEvents();
				for (Event old:olds) {
					if(old.getID()!=Sess1on.eventList.get(i).getID())
						continue;
					if(old.compareEvent(Sess1on.eventList.get(i))) {
						flag = false;
					}
				}
				if (flag) {
					Email.sendUpdateNotification(Sess1on.eventList.get(i));
					Email.sendAddParticipantNotification(Sess1on.eventList.get(i));
					Database.updateEvent(Sess1on.eventList.get(i));
				}
			}
			System.out.println(Sess1on.eventList.get(i).getID());
		}
		System.out.println(available_id);
		Email.sendDeleteNotification(available_id);
		Database.deleteEvents(available_id);
	}
}


