

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class ScheduleList {
	//data field
	private ArrayList<Schedule> list_of_schedule = new ArrayList<Schedule>();
	private String fileName;
	
	ScheduleList(){
		
	}
	
	//reads file and make a list
	ScheduleList(String fileName){
		try {
			this.fileName = fileName;
			File file = new File(fileName);
			Scanner scan = new Scanner(file);
			int i = 0;
			while(scan.hasNext()) {
				String temp = scan.nextLine();
				if(temp.equals("")) {//빈줄 스킵
				}
				else if(temp.substring(0,1).equals(";")) {//;뒤의 코멘트는 모두 주석처리되어 스킵됨.
				}
				else {
					Schedule s = new Schedule(temp);
					String error = s.errorcheck();
					if(error.equals("none")) {
						list_of_schedule.add(s);
					}
					else if(error.equals("noName")) {
						//System.out.println("No schedule name: invalid: skip input line: "+temp);
					}
					else if(error.equals("wrongTimeFormat")) {
						//System.out.println("Wrong time format: invalid: skip input line: "+temp);
					}
					else if(error.equals("timeParadox")) {
						//System.out.println("Start time is set after End time: invalid: skip input line: "+temp);
					}
					else;
					
				}
			}
			scan.close();
		}catch(FileNotFoundException e){
			System.out.println("Unknown File");
		}
	}

	
	//show the number of schedules in this list
	public int numSchedules() {
		return list_of_schedule.size();
	}
	//show the ith schedule in this list
	public Schedule getSchedule(int i) {
		return list_of_schedule.get(i);
	}
	
	public void addSchedule(Schedule s) {
		list_of_schedule.add(s);
	}
	
	public void removeSchedule(int i) {
		list_of_schedule.remove(i);
	}
	
	public void removeSchedule(Object o) {//overloading
		list_of_schedule.remove(o);
	}
	
	public void removeError() {
		for(int i = 0; i < numSchedules(); i++) {
			String error = getSchedule(i).errorcheck();
			if(error.equals("none")) {
				
			}
			else if(error.equals("noName")) {
				removeSchedule(i);
			}
			else if(error.equals("wrongTimeFormat")) {
				removeSchedule(i);
			}
			else if(error.equals("timeParadox")) {
				removeSchedule(i);
			}
			else;
		}
	}
	
	public void writeScheduleList() {
		try {
			File file = new File(fileName);
			PrintWriter p = new PrintWriter(file);
			for(int i = 0; i < numSchedules(); i++) {//list_of_schedule.length로 하지 말고... numSchedules()로 했어야 했다...
				//print나 write는 자동으로 개행이 안되고 println만 된다.
				p.println("");
				p.print(getSchedule(i).getName()+"//");
				p.print(getSchedule(i).getStartTime()+"//");
				p.print(getSchedule(i).getEndTime()+"//");
				p.println(getSchedule(i).getMemo());
				
			}
			//memory leak 방지
			p.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Unknown File");
		}
	}
	
}
