package exercise.GPC.trains.domain;

public enum Dist { 
	MAX ("<"),
	EXACTLY ("=")
	;
	
	private final String op;
	private int val;
	
	private Dist(String to ) { this.op = to; }
	
	public String getOp() {
		return op;
	}
	
	public Dist setVal(int val) {
		this.val = val;
		return this;
	}

	public int getVal() {
		return this.val;
	}

};
