package tropicraft.entities.items;

public enum EnumMask {
	Squarezord(16,32),
	Hornmonkey(16,16),
	Oblongitron(32,16),
	Headinator(16,32),
	SquareHorn(16,16),
	ScrewAttack(32,32),
	TheBrain(16,16),
	BatBoy(16,16),
	Mask8(16,16),
	Mask9(16,16),
	Mask10(16,16),
	Mask11(16,16),
	Mask12(16,32);
	
	
	
	 
	    public final int sizeX;
	    public final int sizeY;
	    
	    
	    private EnumMask(int par4, int par5)
	    {
	        //this.title = par3Str;
	        this.sizeX = par4;
	        this.sizeY = par5;
	       
	    }

}
