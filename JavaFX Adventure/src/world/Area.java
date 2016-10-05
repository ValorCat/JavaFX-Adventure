package world;

import java.util.ArrayList;
import java.util.List;

import world.Encounter.Trigger;

public class Area {
	
	private String descriptor;
	private InteractTarget[] descriptorElements;
	private List<Entrance> entrances;
	private List<Feature> features;
	private List<Encounter> encounters;
	private LightLevel lighting;

	public Area() {
		this(null);
	}
	
	public Area(String descriptor, InteractTarget... descriptorElements) {
		this.descriptor = descriptor;
		this.descriptorElements = descriptorElements;
		this.entrances = new ArrayList<>();
		this.features = new ArrayList<>();
		this.encounters = new ArrayList<>();
		this.lighting = LightLevel.LIT;
	}

	public String getBaseDescriptor() {
		Object[] states = new String[descriptorElements.length];
		for (int i = 0; i < states.length; i++)
			states[i] = descriptorElements[i].getBaseState();
		return String.format(descriptor, states);
	}

	public void setBaseDescriptor(String descriptor, InteractTarget... descriptorElements) {
		this.descriptor = descriptor;
		this.descriptorElements = descriptorElements;
	}
	
	public String getEnterText(int enterID) {
		return getEnterText(entrances.get(enterID));
	}
	
	public String getEnterText(Entrance entrance) {
		return String.format("%s %s %s", entrance.getEnterText(), getBaseDescriptor(), entrance.getAddedText());
	}
	
	public Entrance getEntrance(int enterID) {
		return entrances.get(enterID);
	}
	
	public void addEntrances(Entrance... entranceList) {
		for (Entrance entrance : entranceList)
			entrances.add(entrance);
	}
	
	public void addFeatures(Feature... featureList) {
		for (Feature feature : featureList)
			features.add(feature);
	}
	
	public List<Feature> getFeatures() {
		return features;
	}
	
	public List<Encounter> getEncounters() {
		return encounters;
	}
	
	public void addEncounters(Encounter... encounterList) {
		for (Encounter encounter : encounterList)
			encounters.add(encounter);
	}
	
	public List<Encounter> triggerEncounters(Trigger trigger) {
		List<Encounter> triggered = new ArrayList<>();
		for (Encounter encounter : encounters) {
			if (encounter.hasTrigger(trigger))
				triggered.add(encounter);
		}
		return triggered;
	}
	
	public LightLevel getLightLevel() {
		return lighting;
	}
	
	public void setLightLevel(LightLevel newLevel) {
		lighting = newLevel;
	}
	
}
