package tropicraft.darts;

import net.minecraft.potion.Potion;

public class Curare {
	public static final Curare[] curareList = new Curare[128];
	public static final Curare paralysis = new Curare(0, Potion.blindness);
	public static final Curare poison = new Curare(1, Potion.poison);
	public static final Curare moveSlowdown = new Curare(2, Potion.moveSlowdown);
	public static final Curare harm = new Curare(3, Potion.harm);
	public static final Curare confusion = new Curare(4, Potion.confusion);
	public static final Curare hunger = new Curare(5, Potion.hunger);
	public static final Curare weakness = new Curare(6, Potion.weakness);
	
	private static final String[] names = new String[]{"Paralysis", "Poison", "Slowdown", "Harm", "Confusion", "Hunger", "Weakness"};
	
	public int curareId;
	
	public Potion potion;
	
	public Curare(int id, Potion potion) {
		curareId = id;
		this.potion = potion;
	}
	
	public Potion getPotion() {
		return potion;
	}
	
	@Override
	public String toString() {
		return names[curareId];
	}
}
