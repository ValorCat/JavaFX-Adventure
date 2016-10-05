package world;

import java.util.ArrayList;
import java.util.List;

public class Adventure {
	
	private List<Area> regions;
	
	public Adventure() {
		regions = new ArrayList<Area>();
	}
	
	public Area getRegion(int regionID) {
		return regions.get(regionID);
	}
	
	public void addRegion(Area region) {
		regions.add(region);
	}

}