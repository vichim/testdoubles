package ro.vichim.testdoubles.model;

public class RoomOccupancyPolicy {

	public boolean isAllowed(Reservation reservation) {
		MeetingRoom meetingRoom = reservation.getRoom();
		if (reservation.getNoOfPeople() < 2 && meetingRoom.getRoomLayout() == RoomLayout.BIG) {
			return false;
		}
		return true;
	}
}
