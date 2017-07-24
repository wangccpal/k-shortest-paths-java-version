package paper.data;

public class Slot {
	public int getTraffic() {
		return trafficId;
	}
	public void setTraffic(int  trafficId) {
		this.trafficId = trafficId;
	}
	@Override
	public String toString() {
		return "Slot [used=" + used + ", traffic=" + trafficId + "]";
	}
	public Slot(double rate) {
		super();
		this.rate = rate;
	}
	double rate;
	boolean used=false;
	int trafficId;
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
