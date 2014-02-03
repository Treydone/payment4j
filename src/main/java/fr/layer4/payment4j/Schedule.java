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

	public Schedule setInterval(int interval) {
		this.interval = interval;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Schedule setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public int getTotalOccurences() {
		return totalOccurences;
	}

	public Schedule setTotalOccurences(int totalOccurences) {
		this.totalOccurences = totalOccurences;
		return this;
	}

	public ScheduleUnit getEach() {
		return each;
	}

	public Schedule setEach(ScheduleUnit each) {
		this.each = each;
		return this;
	}
}
