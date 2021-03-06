package paper.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Edge {
	private static final int slotNum = 320;
	private static final int FiberNum = 7;
	public static double allRate;
	int vertexId1;
	int vertexId2;
	String id;
	double weight;
//	七条光纤
	public static Map<String,Edge> emap = new HashMap();
	Slot[][] slotList = new Slot[FiberNum][slotNum];
	double [] useRate = new double [FiberNum];
	private double sum;
	
	public static double getTrafficLoad() {
		double rst = 0;
		for(Entry<String,Edge> e : Edge.emap.entrySet()) {
			rst = Math.max(rst , e.getValue().sum);
		}
		return rst;
	}
	public String calUseRate() {
		sum = 0;
		for(int i=0;i<FiberNum;i++) {
			int used = 0;
			for(int j =0;j<slotNum;j++) {
				if(slotList[i][j].isUsed()) used++;
			}
			useRate[i] = (double)used/slotNum;
			sum+=useRate[i];
		}
		return Arrays.toString(useRate) + sum;
	}
	
	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	//计算使用率
	public static String calAllUseRate() {
		allRate = 0;
		for(Entry<String,Edge> e : Edge.emap.entrySet()) {
			allRate +=e.getValue().getSum();
		}
		return  "" + allRate;
	}
	
	public Slot[][] getSlotList() {
		return slotList;
	}

	public void setSlotList(Slot[][] slotList) {
		this.slotList = slotList;
	}

	public static Edge getEdge(int id1,int id2){
		Edge e = emap.get(id1+"|"+id2) ;
		return null==e ? emap.get(id2+"|"+id1) : e;
	}
	
	public int getVertexId1() {
		return vertexId1;
	}

	public void setVertexId1(int vertexId1) {
		this.vertexId1 = vertexId1;
	}

	public int getVertexId2() {
		return vertexId2;
	}

	public Edge(int vertexId1, int vertexId2, double weight) {
		super();
		this.vertexId1 = vertexId1;
		this.vertexId2 = vertexId2;
		this.weight = weight;
		this.id = vertexId1+"|"+vertexId2;
		emap.put(id,this);
		for(int x=0,y=0;x<FiberNum&&y<slotNum;){
			slotList[x][y++] = new Slot(12.5);
			if(y==slotNum) {
				x++;
				y=0;
			}
		}
	}

	@Override
	public String toString() {
		return "Edge [+id=" + id + ", weight=" + weight + "]" + calUseRate() + " all:" +calAllUseRate();
	}

	public void setVertexId2(int vertexId2) {
		this.vertexId2 = vertexId2;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
