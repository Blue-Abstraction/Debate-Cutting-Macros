public enum Mode {
	UNDERLINE(0),
	EMPHASIZE(1),
	HIGHLIGHT(2);
	
	private int cycle;
	
	Mode(int cycle) {
		this.cycle = cycle;
	}
	
	public static Mode getMode(int num) {
        for (Mode cycle : Mode.values()) {
            if (cycle.cycle == num) {
                return cycle;
            }
        }
        throw new IllegalArgumentException("No enum constant found.");
    }
}
