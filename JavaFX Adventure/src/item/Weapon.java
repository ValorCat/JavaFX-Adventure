package item;

import java.util.ArrayList;
import java.util.Arrays;

import chars.Skill;
import util.IntRange;

public class Weapon extends Item implements Equippable {

	public static enum WeaponFlag { HEAVY, LIGHT, SLOW, THROWABLE, TWO_HANDED }
	// str check to throw / dual weapon bonus / max one attack / no prec check to throw / str check to use one-handed
	
	public static enum WeaponClass { AXE, BOW, CLUB, CROSSBOW, DAGGER, HAND_CROSSBOW, HEAVY, LIGHT, MELEE, POLEARM, RANGED, SLING, SWORD, WHIP }
	
	public static final int DEFAULT_MAX_DAMAGE = 5;
	public static final int DEFAULT_MAX_RANGE = 5;
	public static final int DEFAULT_MIN_THROW_RANGE = 5;
	public static final int DEFAULT_MAX_THROW_RANGE = 30;
	public static final int DEFAULT_STR_PREREQ = 1;
	public static final float DEFAULT_CRIT_CHANCE = 0.05f;
	public static final Skill[] DEFAULT_ATTACK_SKILLS = {Skill.STRENGTH};
	public static final EquipSlot[] VALID_SLOTS = {EquipSlot.MAIN_HAND, EquipSlot.OFF_HAND};
	
	protected IntRange damageOneHand;
	protected IntRange damageTwoHands;
	protected IntRange damageThrown;
	protected IntRange optimalRange;
	protected IntRange optimalThrownRange;
	protected WeaponClass[] classes;
	protected Skill[] attackSkills;
	protected WeaponFlag[] flags;
	protected int strPrereq;
	protected float critChance;
	private boolean equipped;
	
	public Weapon(String name, int baseValue, float weight) {
		super(name, baseValue, weight, ItemType.GEAR);
		this.damageOneHand = new IntRange(1, DEFAULT_MAX_DAMAGE);
		this.damageTwoHands = damageOneHand;
		this.damageThrown = damageOneHand;
		this.optimalRange = new IntRange(DEFAULT_MAX_RANGE);
		this.optimalThrownRange = new IntRange(DEFAULT_MIN_THROW_RANGE, DEFAULT_MAX_THROW_RANGE);
		this.classes = new WeaponClass[] {};
		this.attackSkills = DEFAULT_ATTACK_SKILLS;
		this.flags = new WeaponFlag[] {};
		this.strPrereq = DEFAULT_STR_PREREQ;
		this.critChance = DEFAULT_CRIT_CHANCE;
		this.equipped = false;
	}

	public IntRange getDamageOneHand() {
		return damageOneHand;
	}

	public IntRange getDamageTwoHands() {
		return damageTwoHands;
	}

	public IntRange getDamageThrown() {
		return damageThrown;
	}

	public IntRange getOptimalRange() {
		return optimalRange;
	}

	public IntRange getOptimalThrownRange() {
		return optimalThrownRange;
	}
	
	public WeaponClass[] getClasses() {
		return classes;
	}

	public Skill[] getAttackSkills() {
		return attackSkills;
	}

	public WeaponFlag[] getFlags() {
		return flags;
	}

	public int getStrPrereq() {
		return strPrereq;
	}
	
	public float getCritChance() {
		return critChance;
	}
	
	public boolean isEquipped() {
		return equipped;
	}
	
	public EquipSlot[] getValidSlots() {
		return VALID_SLOTS;
	}
	
	public Weapon setOneHandDamage(int min, int max) {
		this.damageOneHand = new IntRange(min, max);
		return this;
	}
	
	public Weapon setTwoHandDamage(int min, int max) {
		this.damageTwoHands = new IntRange(min, max);
		return this;
	}
	
	public Weapon setThrownStats(int minDam, int maxDam, int lowRan, int hiRan) {
		this.damageThrown = new IntRange(minDam, maxDam);
		this.optimalThrownRange = new IntRange(lowRan, hiRan);
		return this;
	}
	
	public Weapon setDamage(int min, int max) {
		this.damageOneHand = new IntRange(min, max);
		this.damageTwoHands = damageOneHand;
		this.damageThrown = damageOneHand;
		return this;
	}
	
	public Weapon setRange(int low, int high) {
		this.optimalRange = new IntRange(low, high);
		return this;
	}
	
	public Weapon setThrownRange(int low, int high) {
		this.optimalThrownRange = new IntRange(low, high);
		return this;
	}
	
	public Weapon setClasses(WeaponClass...classes) {
		this.classes = classes;
		return this;
	}
	
	public Weapon setSkills(boolean bladecraft, boolean precision, boolean strength) {
		ArrayList<Skill> skills = new ArrayList<>();
		if (bladecraft)
			skills.add(Skill.BLADECRAFT);
		if (precision)
			skills.add(Skill.PRECISION);
		if (strength)
			skills.add(Skill.STRENGTH);
		this.attackSkills = skills.toArray(new Skill[0]);
		return this;
	}
	
	public Weapon setFlags(WeaponFlag... flags) {
		this.flags = flags;
		return this;
	}
	
	public Weapon setStrPrereq(int prereq) {
		this.strPrereq = prereq;
		return this;
	}
	
	public Weapon setCritChance(float chance) {
		this.critChance = chance;
		return this;
	}

	public void onEquip() {
		equipped = true;
	}
	
	public void onUnequip() {
		equipped = false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(attackSkills);
		result = prime * result + Arrays.hashCode(classes);
		result = prime * result + Float.floatToIntBits(critChance);
		result = prime * result + ((damageOneHand == null) ? 0 : damageOneHand.hashCode());
		result = prime * result + ((damageThrown == null) ? 0 : damageThrown.hashCode());
		result = prime * result + ((damageTwoHands == null) ? 0 : damageTwoHands.hashCode());
		result = prime * result + Arrays.hashCode(flags);
		result = prime * result + ((optimalRange == null) ? 0 : optimalRange.hashCode());
		result = prime * result + ((optimalThrownRange == null) ? 0 : optimalThrownRange.hashCode());
		result = prime * result + strPrereq;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weapon other = (Weapon) obj;
		if (!Arrays.equals(attackSkills, other.attackSkills))
			return false;
		if (!Arrays.equals(classes, other.classes))
			return false;
		if (Float.floatToIntBits(critChance) != Float.floatToIntBits(other.critChance))
			return false;
		if (damageOneHand == null) {
			if (other.damageOneHand != null)
				return false;
		} else if (!damageOneHand.equals(other.damageOneHand))
			return false;
		if (damageThrown == null) {
			if (other.damageThrown != null)
				return false;
		} else if (!damageThrown.equals(other.damageThrown))
			return false;
		if (damageTwoHands == null) {
			if (other.damageTwoHands != null)
				return false;
		} else if (!damageTwoHands.equals(other.damageTwoHands))
			return false;
		if (!Arrays.equals(flags, other.flags))
			return false;
		if (optimalRange == null) {
			if (other.optimalRange != null)
				return false;
		} else if (!optimalRange.equals(other.optimalRange))
			return false;
		if (optimalThrownRange == null) {
			if (other.optimalThrownRange != null)
				return false;
		} else if (!optimalThrownRange.equals(other.optimalThrownRange))
			return false;
		if (strPrereq != other.strPrereq)
			return false;
		return true;
	}
	
}
