package ro.vichim.testdoubles.model;

public class Reservation {

	private String applicant;
	private MeetingRoom room;
	private int noOfPeople;

	public Reservation(String applicant, MeetingRoom room, int noOfPeople) {
		this.applicant = applicant;
		this.room = room;
		this.noOfPeople = noOfPeople;
	}

	public String getApplicant() {
		return applicant;
	}

	public MeetingRoom getRoom() {
		return room;
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}

}
