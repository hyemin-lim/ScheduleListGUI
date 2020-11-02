import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;

import javax.swing.*;

public class Monthly extends JFrame{
	//ScheduleList
	ScheduleList list;
	//날짜와 관련된 data field
	private int year = 2020;
	private int month = 5;
	private int day = 31;
	private int firstday = 5;
	
	//GUI 구현 관련 data field
	private JPanel p = new JPanel();
	private JPanel p_up = new JPanel(new BorderLayout(5,10));
	private JPanel ynm = new JPanel(new BorderLayout());
	private JLabel y = new JLabel(""+year, JLabel.CENTER);
	private JLabel m = new JLabel(""+(month), JLabel.CENTER);
	private JPanel p_middle = new JPanel(new GridLayout(1, 7));
	private JPanel p_under = new JPanel(new GridLayout(6,7));
	
	private JButton[] dayButton = new JButton[day];
	private JButton lastmonth = new JButton("<");
	private JButton nextmonth = new JButton(">");
	
	Monthly(){//전달받은 인자가 없을 때의 생성자
		//외부 파일을 새로 만들어야 할까?
		//외부 파일이 없어도 ScheduleList를 만들 수도 있긴한데 ... 객체 생성일까 이것도?
	}
	
	Monthly(ScheduleList list){//생성자
		//전달받은 ScheduleList를 데이터 필드에 저장.
		this.list = list;
		
		//상단바 - 좌우 화살표와 년, 월 표시
		//p_up panel은 lastmonth(좌측화살표), nextmonth(우측화살표), ynm이라는 panel로 구성되어 있다.
		p_up.add(lastmonth, BorderLayout.WEST);
		lastmonth.addActionListener(new Listener());
		
		//ynm panel은 년도를 표시하는 JLabel y와 월을 표시하는 JLabel m을 포함.
		ynm.add(y, BorderLayout.NORTH);
		ynm.add(m, BorderLayout.SOUTH);
		p_up.add(ynm, BorderLayout.CENTER);
		
		p_up.add(nextmonth, BorderLayout.EAST);
		nextmonth.addActionListener(new Listener());
		
		//중간바 - 요일 표시
		p_middle.add(new JLabel("SUN", JLabel.CENTER));
		p_middle.add(new JLabel("MON", JLabel.CENTER));
		p_middle.add(new JLabel("TUE", JLabel.CENTER));
		p_middle.add(new JLabel("WED", JLabel.CENTER));
		p_middle.add(new JLabel("TUR", JLabel.CENTER));
		p_middle.add(new JLabel("FRI", JLabel.CENTER));
		p_middle.add(new JLabel("SAT", JLabel.CENTER));
		
		//하단 - 날짜 표시
		//firstday는 매월 1일의 요일을 나타낸다. 즉 firstday가 0이면 일요일~6이면 토요일인 식이다. 매월 1일 전에는 아무것도 없으므로 빈 JLabel을 grid에 추가한다.
		for(int i = 0; i < firstday; i++) {
			p_under.add(new JLabel(""));
		}
		//day는 이 달의 날짜 수를 나타낸다. 날짜수만큼 그 날짜에 해당하는 숫자 텍스트를 가진 버튼을 추가한다. 
		for(int i = 0; i < day; i++) {
			dayButton[i] = new JButton(""+(i+1));
			dayButton[i].addActionListener(new Listener());
			p_under.add(dayButton[i]);
		}
		//Grid의 나머지 부분을 빈 라벨로 채운다.
		for(int i = 0; i < ((6*7) - day - firstday); i++) {
			p_under.add(new JLabel(""));
		}
		
		//전체 패널인 p에 지금까지 만든 모든 패널을 더한다.
		p.setLayout(new BorderLayout());
		p.add(p_up, BorderLayout.NORTH);
		p.add(p_middle, BorderLayout.CENTER);
		p.add(p_under, BorderLayout.SOUTH);
	}
	
	public void MonthlyFrame() {//월별 일정을 프레임으로 나타낸다.
		this.setTitle("Monthly Schedule Calendar");
		this.add(p);
		this.pack();//크기조정은 컴포넌트에 맞춰 자동으로 조절된다.
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	class Listener implements ActionListener{//버튼을 누르면 발생하는 이벤트를 위한 클래스
		public void actionPerformed(ActionEvent act) {
			//날짜 버튼을 누르면 일별 일정 프레임이 나온다.
			for(int i = 0; i < day; i++) {
				if(act.getSource() == dayButton[i]) {
					Daily daySchedule = new Daily(year, (month), (i+1), list);
					daySchedule.setTitle("Daily Schedule- "+year+"-"+(month)+"-"+(i+1));
					daySchedule.DailyFrame();
				}
				
			}
			//좌측 화살표를 누르면 전월 달력이 나온다.
			if(act.getSource() == lastmonth) {
				LocalDate cal = LocalDate.of(year, month, 1);
				cal = cal.minusMonths(1);
				year = cal.getYear();
				month = cal.getMonthValue();
				day = cal.lengthOfMonth();
				switch (cal.getDayOfWeek()) {
				case SUNDAY:
					firstday = 0;
					break;
				case MONDAY:
					firstday = 1;
					break;
				case TUESDAY:
					firstday = 2;
					break;
				case WEDNESDAY:
					firstday = 3;
					break;
				case THURSDAY:
					firstday = 4;
					break;
				case FRIDAY:
					firstday = 5;
					break;
				case SATURDAY:
					firstday = 6;
					break;
				}
				
				y.setText(""+year);
				m.setText(""+(month));
				
				p_under.removeAll();
				
				for(int i = 0; i < firstday; i++) {
					p_under.add(new JLabel(""));
				}
				
				for(int i = 0; i < day; i++) {
					dayButton[i] = new JButton(""+(i+1));
					dayButton[i].addActionListener(new Listener());
					p_under.add(dayButton[i]);
				}
				
				for(int i = 0; i < ((6*7) - day - firstday); i++) {
					p_under.add(new JLabel(""));
				}
				
				p.revalidate();
				p.repaint();
				
			}
			//우측 화살표를 누르면 내월 달력이 나온다.
			else if(act.getSource() == nextmonth) {
				LocalDate cal = LocalDate.of(year, month, 1);
				cal = cal.plusMonths(1);
				year = cal.getYear();
				month = cal.getMonthValue();
				day = cal.lengthOfMonth();
				switch (cal.getDayOfWeek()) {
				case SUNDAY:
					firstday = 0;
					break;
				case MONDAY:
					firstday = 1;
					break;
				case TUESDAY:
					firstday = 2;
					break;
				case WEDNESDAY:
					firstday = 3;
					break;
				case THURSDAY:
					firstday = 4;
					break;
				case FRIDAY:
					firstday = 5;
					break;
				case SATURDAY:
					firstday = 6;
					break;
				}
				
				y.setText(""+year);
				m.setText(""+(month));
				
				p_under.removeAll();
				
				for(int i = 0; i < firstday; i++) {
					p_under.add(new JLabel(""));
				}
				
				for(int i = 0; i < day; i++) {
					dayButton[i] = new JButton(""+(i+1));
					dayButton[i].addActionListener(new Listener());
					p_under.add(dayButton[i]);
				}
				
				for(int i = 0; i < ((6*7) - day - firstday); i++) {
					p_under.add(new JLabel(""));
				}
				
				p.revalidate();
				p.repaint();
				
			}
			
		}
	}
}
