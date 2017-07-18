package paper.data;

public class Slot {
	public Slot(double rate) {
		super();
		this.rate = rate;
	}
	double rate;
	boolean used=false;
	Traffic traffic;
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
}
