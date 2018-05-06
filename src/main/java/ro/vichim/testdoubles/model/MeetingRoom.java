package ro.vichim.testdoubles.model;

public class MeetingRoom {

	private String name;
	private RoomLayout roomLayout;

	public MeetingRoom(String name, RoomLayout roomLayout) {
		this.name = name;
		this.roomLayout = roomLayout;
	}
	
	public String getName() {
		return name;
	}

	public RoomLayout getRoomLayout() {
		return roomLayout;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MeetingRoom other = (MeetingRoom) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
