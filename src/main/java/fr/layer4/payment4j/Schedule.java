package fr.layer4.payment4j;

import java.util.Date;

public class Schedule {

	private Date startDate;

	private int totalOccurences;

	private ScheduleUnit each;

	private int interval;

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getTotalOccurences() {
		return totalOccurences;
	}

	public void setTotalOccurences(int totalOccurences) {
		this.totalOccurences = totalOccurences;
	}

	public ScheduleUnit getEach() {
		return each;
	}

	public void setEach(ScheduleUnit each) {
		this.each = each;
	}
}
