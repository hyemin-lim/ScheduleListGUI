
import java.time.*;
import java.time.format.*;

public class Schedule {
	private String name;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	private String memo;
	DateTimeFormatter schedule_time = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
	
	//constructor
	Schedule(String infoString){
		String[] info = infoString.split("//");
		for(int i = 0; i <info.length;i++) {
			info[i] = info[i].trim();
		}
		
		try {
			if(info.length == 4) {//.length는 배열의 길이, .length()는 문자열의 길이(문자열은 배열이 아니므로)
				name = info[0];
				start_time = LocalDateTime.parse(info[1], schedule_time);
				end_time = LocalDateTime.parse(info[2], schedule_time);
				memo = info[3];
			}
			else if(info.length == 3) {
				name = info[0];
				start_time = LocalDateTime.parse(info[1], schedule_time);
				end_time = LocalDateTime.parse(info[2], schedule_time);
				memo="";
			}
			else;
		}
		catch(DateTimeParseException e){
			start_time = LocalDateTime.MAX;
			end_time = LocalDateTime.MIN;
		}
		
	}
	
	Schedule(String name, String sTime, String eTime, String memo){
		try {
			this.name = name;
			this.start_time = LocalDateTime.parse(sTime, schedule_time);
			this.end_time = LocalDateTime.parse(eTime, schedule_time);
			this.memo = memo;
		}
		catch(DateTimeParseException e){
			start_time = LocalDateTime.MAX;
			end_time = LocalDateTime.MIN;
		}
	}
	
	Schedule(String name, String sTime, String eTime){
		try {
			this.name = name;
			this.start_time = LocalDateTime.parse(sTime, schedule_time);
			this.end_time = LocalDateTime.parse(eTime, schedule_time);
			this.memo = "";
		}
		catch(DateTimeParseException e){
			start_time = LocalDateTime.MAX;
			end_time = LocalDateTime.MIN;
		}
	}
	
	//print an entire schedule
	public void print() {
		System.out.println("Schedule Name: "+name);
		System.out.println("Start: "+start_time.toString().replace("T"," "));
		System.out.println("End: "+end_time.toString().replace("T"," "));
		if(memo != null) {
			System.out.println("Memo: "+memo);
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getStartTime() {
		return start_time.format(schedule_time);
	}
	
	public int getStartTime_year() {
		return start_time.getYear();
	}
	
	public int getStartTime_month() {
		return start_time.getMonthValue();
	}
	
	public int getStartTime_day() {
		return start_time.getDayOfMonth();
	}
	
	public String getEndTime() {
		return end_time.format(schedule_time);
	}
	
	public int getEndTime_year() {
		return end_time.getYear();
	}
	
	public int getEndTime_month() {
		return end_time.getMonthValue();
	}
	
	public int getEndTime_day() {
		return end_time.getDayOfMonth();
	}
	
	public String getMemo() {
		return memo;
	}
	
	//valid check
	public String errorcheck() {
		String error = "none";
		//1. Missing Element
		if(name.equals("")) {
			error = "noName";
		}
		else if(start_time.equals(LocalDateTime.MAX)&&end_time.equals(LocalDateTime.MIN)) {
			error = "wrongTimeFormat";
		}
		else if(start_time.compareTo(end_time) > 0) {
			error = "timeParadox";
		}
		else;
		return error;
	}
	
	
}
