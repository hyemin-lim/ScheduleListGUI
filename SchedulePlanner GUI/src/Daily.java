import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Daily extends JFrame{
	//��¥ ���� �������ʵ�
	private int year;
	private int month;
	private int day;
	//�ʱ�����
	private ScheduleList list;
	//��ư
	private JButton cancel = new JButton("Cancel");
	private JButton add = new JButton("Add");
	private JButton save = new JButton("Save");
	//�г�
	private JPanel p = new JPanel(new BorderLayout());
	private JPanel p_up = new JPanel(new GridLayout(1, 4));
	private JPanel p_middle;
	private JPanel p_under = new JPanel(new GridLayout(1, 3));
	//�⺻ ������
	Daily(){
		
	}
	//������
	Daily(int y, int m, int d, ScheduleList list){
		this.list = list;
		//��ܹ�
		p_up.add(new JLabel("Title", JLabel.CENTER));
		p_up.add(new JLabel("Start Time", JLabel.CENTER));
		p_up.add(new JLabel("End Time", JLabel.CENTER));
		p_up.add(new JLabel("Memo", JLabel.CENTER));
		
		//�ʱ� �������� ���� - ������Ʈ 3�� ����
		year = y;
		month = m;
		day = d;
		
		//���� �߰��Ȱ� �ƹ��͵� ���� �� �ʱ�ȭ��
		p_middle = new JPanel();
		p_middle.setLayout(new BoxLayout(p_middle, BoxLayout.Y_AXIS));
		
		//���Ͽ� �ִ� ���� �߰�
		for(int i = 0; i < list.numSchedules(); i++) {
			Schedule def = list.getSchedule(i);
			if(def.getStartTime_year() == year  && def.getStartTime_month() == month && def.getStartTime_day() == day) {//������ �ִ� ���϶� �ʱ�ȭ��
				JPanel newSchedule = new JPanel(new GridLayout(1,4));
				
				JTextField title = new JTextField();
				title.setText(def.getName());
				title.setPreferredSize(new Dimension(100,100));
				newSchedule.add(title);
				
				JTextField s_time = new JTextField();
				s_time.setText(def.getStartTime());
				newSchedule.add(s_time);
				
				JTextField e_time = new JTextField();
				e_time.setText(def.getEndTime());
				newSchedule.add(e_time);
				
				JTextField memo = new JTextField();
				memo.setText(def.getMemo());
				newSchedule.add(memo);
				
				p_middle.add(newSchedule);
			}
			else {//������ ���� ���϶� �ʱ�ȭ��
				
			}
			
	    }
		
		//�ϴܹ� - ��ư ����
		cancel.addActionListener(new Listener());
		p_under.add(cancel);
		add.addActionListener(new Listener());
		p_under.add(add);
		save.addActionListener(new Listener());
		p_under.add(save);
		
		//��ü �г� p�� ���ݱ��� ���� �� ���� �г��� �߰�.
		p.add(p_up, BorderLayout.NORTH);
		p.add(p_middle, BorderLayout.CENTER);
		p.add(p_under, BorderLayout.SOUTH);
	}
	
	class Listener implements ActionListener{
		public void actionPerformed(ActionEvent act) {//��ư ��� �ܼ���¸� �����ϰ� �߰���.
			if(act.getSource() == cancel) {
				dispose();
			}
			else if(act.getSource() == add) {
				JPanel newSchedule = new JPanel(new GridLayout(1,4));
				
				JTextField title = new JTextField();
				JTextField s_time = new JTextField();
				JTextField e_time = new JTextField();
				JTextField memo = new JTextField();
				title.setPreferredSize(new Dimension(100,100));
				
				newSchedule.add(title);
				newSchedule.add(s_time);
				newSchedule.add(e_time);
				newSchedule.add(memo);
				
				p_middle.add(newSchedule);
				
				p.revalidate();
				p.repaint();
				pack();
			}
			else if(act.getSource() == save) {
				
				for(int i = 0; i < list.numSchedules(); i++) {//�� ���� �������� ����
					Schedule def = list.getSchedule(i);
					if(def.getStartTime_year() == year  && def.getStartTime_month() == month && def.getStartTime_day() == day) {//������ �ִ� ���϶� �ʱ�ȭ��
						list.removeSchedule(i);
						i--;
					}
			    }
				
				for(int j = 0; j < p_middle.getComponentCount(); j++) {
					JPanel temp = (JPanel)p_middle.getComponent(j);
					String temp_title = ((JTextField)temp.getComponent(0)).getText();
					String temp_start_time = ((JTextField)temp.getComponent(1)).getText();
					String temp_end_time = ((JTextField)temp.getComponent(2)).getText();
					String temp_memo = ((JTextField)temp.getComponent(3)).getText();
					Schedule sch = new Schedule(temp_title, temp_start_time, temp_end_time, temp_memo);
					list.addSchedule(sch);
				}
				list.removeError();
				list.writeScheduleList();
				dispose();
				
			}
			else;
		}
	}
	
	public void DailyFrame() {//�Ϻ� ������ ���������� ��Ÿ����.
		this.add(p);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
