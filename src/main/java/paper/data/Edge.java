package paper.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Edge {
	private static final int slotNum = 320;
	private static final int FiberNum = 7;
	int vertexId1;
	int vertexId2;
	String id;
	double weight;
//	∆ﬂÃıπ‚œÀ
	public static Map<String,Edge> emap = new HashMap();
	Slot[][] slotList = new Slot[FiberNum][slotNum];
	
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
