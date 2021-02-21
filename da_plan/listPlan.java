package finalDAPLAN;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

//linked list 
public class listPlan {

	private LinkedList<Task> plan;
	private LinkedList<Task> finalPlan;
	private LinkedList<Task> sublistSorted;

	public listPlan() {
		plan = new LinkedList<Task>();
		finalPlan = new LinkedList<Task>();
		sublistSorted = new LinkedList<Task>();

	}

	// add tasks
	public boolean addTask(Task t) {
		t.setDueInHowMany(daysDue(t));

		return plan.add(t);

	}

	public void removeElement(Task t) {
		plan.remove(t);

	}

	// total time of all tasks put into program
	public double totalTime() {
		double ret = 0;
		for (Task l : plan) {
			ret += l.getTaskLength();
		}

		return ret;
	}

	// will print all tasks added
	public String printList() {

		return plan.toString();
	}

	// sort according to when it is due
	public void sort() {

		Collections.sort(plan, new TaskC());

	}

	public static int daysDue(Task t) {
		// current date
		String timeStamp = new SimpleDateFormat("MMddyy").format(Calendar.getInstance().getTime());

		String cSmonth = timeStamp.substring(0, 2);
		String cSday = timeStamp.substring(2, 4);
		String cSyear = timeStamp.substring(4);
		// current day in terms of integers
		int cImonth = Integer.parseInt(cSmonth);
		int cIday = Integer.parseInt(cSday);
		int cIyear = Integer.parseInt(cSyear);

		String dDate = t.getDueDate();
		String dSMonth = dDate.substring(0, 2);
		String dSDay = dDate.substring(2, 4);
		String dSYear = dDate.substring(4);
		// due day in terms of integers
		int dIMonth = Integer.parseInt(dSMonth);
		int dIDay = Integer.parseInt(dSDay);
		int dIYear = Integer.parseInt(dSYear);

		DaysBetween cDay = new DaysBetween(cIday, cImonth, cIyear);
		DaysBetween dueDay = new DaysBetween(dIDay, dIMonth, dIYear);
		DaysBetween r = new DaysBetween();
		return r.getDifference(cDay, dueDay);
	}

	public LinkedList<Task> listDueToday() {
		// add tasks due today to sublistSorted
		// remove from plan list so that everything in plan is what is not due
		// today
		// prioritize the sublistSorted list in the final plan
		// System.out.println("list due today");
		// for (Task t : plan) {
		// if (t.getDueInHowMany() == 1) {
		// sublistSorted.add(t);
		// plan.remove(t);
		// }
		// }

		ListIterator<Task> list_Iter = plan.listIterator(0);
		while (list_Iter.hasNext()) {
			Task newT = list_Iter.next();
			if (newT.getDueInHowMany() <= 1) {
				sublistSorted.add(newT);
				list_Iter.remove();
			}
		}
		Collections.sort(sublistSorted, new TaskB());
		Collections.sort(plan, new TaskB());
		// returns everything due today in sorted order easiest to hardest
		return sublistSorted;
	}

	public LinkedList<Task> buildFinalList() {
		int sCounter = 0;
		int pCounter = 0;
		System.out.println(sublistSorted.size());
		while (sublistSorted.size() != 0) {
			if (sCounter % 2 == 0) {
				System.out.println("sublist if");
				finalPlan.add(sublistSorted.removeLast());
			} else {
				System.out.println("sublist else");
				finalPlan.add(sublistSorted.removeFirst());
			}
			sCounter++;
		}

		while (plan.size() != 0) {
			if (pCounter % 2 == 0) {
				System.out.println("plan if");
				finalPlan.add(plan.removeLast());
			} else {
				System.out.println("plan else");
				finalPlan.add(plan.removeFirst());
			}
			pCounter++;
		}

		return finalPlan;
	}

}
