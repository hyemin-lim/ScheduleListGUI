

public class ScheduleGUI{
	public static void main(String[] args) {
		ScheduleList list = new ScheduleList("C:\\Users\\������\\eclipse-workspace\\Schedule_management\\src\\schedule-file.data");
		Monthly frame = new Monthly(list);
		frame.MonthlyFrame();
	}

}
