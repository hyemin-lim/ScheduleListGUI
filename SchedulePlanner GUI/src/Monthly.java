import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;

import javax.swing.*;

public class Monthly extends JFrame{
	//ScheduleList
	ScheduleList list;
	//��¥�� ���õ� data field
	private int year = 2020;
	private int month = 5;
	private int day = 31;
	private int firstday = 5;
	
	//GUI ���� ���� data field
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
	
	Monthly(){//���޹��� ���ڰ� ���� ���� ������
		//�ܺ� ������ ���� ������ �ұ�?
		//�ܺ� ������ ��� ScheduleList�� ���� ���� �ֱ��ѵ� ... ��ü �����ϱ� �̰͵�?
	}
	
	Monthly(ScheduleList list){//������
		//���޹��� ScheduleList�� ������ �ʵ忡 ����.
		this.list = list;
		
		//��ܹ� - �¿� ȭ��ǥ�� ��, �� ǥ��
		//p_up panel�� lastmonth(����ȭ��ǥ), nextmonth(����ȭ��ǥ), ynm�̶�� panel�� �����Ǿ� �ִ�.
		p_up.add(lastmonth, BorderLayout.WEST);
		lastmonth.addActionListener(new Listener());
		
		//ynm panel�� �⵵�� ǥ���ϴ� JLabel y�� ���� ǥ���ϴ� JLabel m�� ����.
		ynm.add(y, BorderLayout.NORTH);
		ynm.add(m, BorderLayout.SOUTH);
		p_up.add(ynm, BorderLayout.CENTER);
		
		p_up.add(nextmonth, BorderLayout.EAST);
		nextmonth.addActionListener(new Listener());
		
		//�߰��� - ���� ǥ��
		p_middle.add(new JLabel("SUN", JLabel.CENTER));
		p_middle.add(new JLabel("MON", JLabel.CENTER));
		p_middle.add(new JLabel("TUE", JLabel.CENTER));
		p_middle.add(new JLabel("WED", JLabel.CENTER));
		p_middle.add(new JLabel("TUR", JLabel.CENTER));
		p_middle.add(new JLabel("FRI", JLabel.CENTER));
		p_middle.add(new JLabel("SAT", JLabel.CENTER));
		
		//�ϴ� - ��¥ ǥ��
		//firstday�� �ſ� 1���� ������ ��Ÿ����. �� firstday�� 0�̸� �Ͽ���~6�̸� ������� ���̴�. �ſ� 1�� ������ �ƹ��͵� �����Ƿ� �� JLabel�� grid�� �߰��Ѵ�.
		for(int i = 0; i < firstday; i++) {
			p_under.add(new JLabel(""));
		}
		//day�� �� ���� ��¥ ���� ��Ÿ����. ��¥����ŭ �� ��¥�� �ش��ϴ� ���� �ؽ�Ʈ�� ���� ��ư�� �߰��Ѵ�. 
		for(int i = 0; i < day; i++) {
			dayButton[i] = new JButton(""+(i+1));
			dayButton[i].addActionListener(new Listener());
			p_under.add(dayButton[i]);
		}
		//Grid�� ������ �κ��� �� �󺧷� ä���.
		for(int i = 0; i < ((6*7) - day - firstday); i++) {
			p_under.add(new JLabel(""));
		}
		
		//��ü �г��� p�� ���ݱ��� ���� ��� �г��� ���Ѵ�.
		p.setLayout(new BorderLayout());
		p.add(p_up, BorderLayout.NORTH);
		p.add(p_middle, BorderLayout.CENTER);
		p.add(p_under, BorderLayout.SOUTH);
	}
	
	public void MonthlyFrame() {//���� ������ ���������� ��Ÿ����.
		this.setTitle("Monthly Schedule Calendar");
		this.add(p);
		this.pack();//ũ�������� ������Ʈ�� ���� �ڵ����� �����ȴ�.
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	class Listener implements ActionListener{//��ư�� ������ �߻��ϴ� �̺�Ʈ�� ���� Ŭ����
		public void actionPerformed(ActionEvent act) {
			//��¥ ��ư�� ������ �Ϻ� ���� �������� ���´�.
			for(int i = 0; i < day; i++) {
				if(act.getSource() == dayButton[i]) {
					Daily daySchedule = new Daily(year, (month), (i+1), list);
					daySchedule.setTitle("Daily Schedule- "+year+"-"+(month)+"-"+(i+1));
					daySchedule.DailyFrame();
				}
				
			}
			//���� ȭ��ǥ�� ������ ���� �޷��� ���´�.
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
			//���� ȭ��ǥ�� ������ ���� �޷��� ���´�.
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
