package exercise.GPC.trains.domain;

public enum Stops { 
	MAX ("<"),
	EXACTLY ("=")
	;
	
	private final String op;
	private int val;
	
	private Stops(String to ) { this.op = to; }
	
	public String getOp() {
		return op;
	}
	
	public Stops setVal(int val) {
		this.val = val;
		return this;
	}

	public int getVal() {
		return this.val;
	}

};
