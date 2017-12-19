package tropicraft.volleyball;

public enum CourtType {
	
	LARGE(9, 15, 6),
	MEDIUM(7, 13, 4),
	SMALL(5, 11, 3);
	
	public final int xLength;
	public final int zLength;
	public final int maxPerTeam;
	
	private CourtType(int xl, int zl, int mpt) {
		this.xLength = xl;
		this.zLength = zl;
		this.maxPerTeam = mpt;
	}
}
