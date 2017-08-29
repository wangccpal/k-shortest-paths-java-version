package paper.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.asu.emit.algorithm.graph.Path;

public class Traffic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6817159874724577640L;
	public static List<Traffic> tlist = new LinkedList();

	/**
	 *  随机生成一些业务
	 * @param num 生成的业务数量
	 * @param from 起点的范围
	 * @param to	  重点的范围
	 */
	public static void randomGenTraffic(int num, int from, int to) {
		int[] slotnum = { 3, 4, 5 }; // 业务的slot数量
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			int fromid = r.nextInt(from)+1;
			int toid = r.nextInt(to)+1;
			while (fromid == toid)
				toid = r.nextInt(to)+1;
			tlist.add(new Traffic(slotnum[r.nextInt(3)], fromid, toid,i));
		}
	}
	
	

	public static void serialTraffic(String File) {
		try {
			// "d:\\traffic.obj"
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(File));
			oos.writeObject(tlist);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void unSerialTraffic(String File) {
		ObjectInputStream oin;
		try {
			oin = new ObjectInputStream(new FileInputStream(File));
			List<Traffic> tlist = (List<Traffic>) oin.readObject();
			oin.close();
			Traffic.tlist = tlist;
			System.out.println(tlist);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static double blockingRate() {
		int count = 0;
		for(Traffic t: Traffic.tlist) {
			if(!t.isSuccess()) count++;
		}
		return (double)count/tlist.size();
	}

	@Override
	public String toString() {
		return "Traffic [slotNum=" + slotNum + ", id=" + id + ", from=" + from + ", to=" + to + ", success="+success + "]:" + edgeList;
	}

	public Traffic(int slotNum, int from, int to,int id) {
		super();
		this.slotNum = slotNum;
		this.from = from;
		this.to = to;
		this.id = id;
	}

	double rate;
	int slotNum;
	int from;
	int to;
	int id;
	Path path;
	List<Edge> edgeList = new LinkedList();
	boolean success;

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getSlotNum() {
		return slotNum;
	}

	public void setSlotNum(int slotNum) {
		this.slotNum = slotNum;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
		for (int i = 0; i < path.getVertexList().size() - 1; i++) {
			edgeList.add( Edge.getEdge(path.getVertexList().get(i).getId(), path.getVertexList().get(i + 1).getId()) );
		}
	}

	public List<Edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public static int blockingNum() {
		int count = 0;
		for(Traffic t: Traffic.tlist) {
			if(t.isSuccess()) count++;
		}
		return count;
	}

	public static double blockingRate(int i) {
		int count = 0;
		for(Traffic t: Traffic.tlist) {
			if(t.isSuccess()) count++;
		}
		return (count+0.0)/i;
	}
}
