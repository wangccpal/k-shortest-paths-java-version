package paper.fit;

import java.util.List;

import edu.asu.emit.algorithm.graph.Path;
import paper.data.Edge;
import paper.data.Slot;
import paper.data.Traffic;

public class FirstFitPlusImpl implements SlotAssignAlgr {

	public boolean slotAssign(Path path, Traffic tra) {
		int slotNum = tra.getSlotNum();
		int vSum = path.getVertexList().size();
		int[] startSlot = new int[vSum - 1];
		if(findTimeSlot(0, slotNum, startSlot, path)){
			int start = startSlot[0];
			for (int i = 0; i < vSum - 1; i++) {
				Edge e = Edge.getEdge(path.getVertexList().get(i).getId(), path.getVertexList().get(i + 1).getId());
				Slot[][] slotList = e.getSlotList();
				int j = start % slotList[0].length; // 第几列
				int h = start / slotList[0].length;// 第几行
				int sum = 0;
				while(sum<slotNum){
					slotList[h][j+sum].setUsed(true);
					slotList[h][j+sum].setTraffic(tra.getId());
					sum++;
				}
			}
			path.setStart(start);
			path.setOver(start+slotNum-1);
			return true;
		}
		else return false;
	}

	public static boolean findTimeSlot(int start, int needSlotNum, int[] startSlot, Path path) {
		int vSum = path.getVertexList().size();
		int cc = start;// 记录start初始值，以便在每条链路都以初始值开始寻找
		for (int i = 0; i < vSum - 1; i++) {
			Edge e = Edge.getEdge(path.getVertexList().get(i).getId(), path.getVertexList().get(i + 1).getId());
			Slot[][] slotList = e.getSlotList();
			start = cc;
			int sum = 0;
			int j = start % slotList[0].length; // 第几列
			int h = start / slotList[0].length;// 第几行
			start = start%320; //在匹配的时候start不能超过320
			while (sum != needSlotNum ) {// i相当于循环指针
				// System.out.println(fiber.getTimeslotList());//CC
				if (j >= 320) {
					h++;
					j = 0;
					start = 0;
					sum=0;
					if(h==slotList.length) return false;
				}
				if (!slotList[h][j].isUsed()) {
					sum++;// 如果一直空闲则直到找到满足数量的时隙或者到达最后一个时隙
					j++;
				} else {// 如果遇到了不是空闲的则从这个时隙的下一个时隙开始找到需要数量的时隙或者到达最后一个时隙，更新start：开始的位置
					j = start + sum + 1;
					start = j;
					sum = 0;
				}
			} // 循环结束，找到了该链路的起始时隙或者找不到
			if (sum == needSlotNum)
				startSlot[i] = h * 320 + start;
			else {
				startSlot[i] = -1;
				return false;
			}
		}
		// 每条链路需要频隙序号一致判断
		int max = startSlot[0];
		int min = max;
		for (int i : startSlot) {
			max = max > i ? max : i;
			min = min < i ? min : i;
		}
		if (max != min) {// 表示链路开始时隙序号不全一样，从最大值开始重新找
			return findTimeSlot(max, needSlotNum, startSlot, path);
		} else
			return true;
	}
	
	public void slotRelease(Traffic traffic) {
		Path path = traffic.getPath();
		if(path==null) return ;
		List<Edge> edgeList = traffic.getEdgeList();
		int start = path.getStart();
		int over = traffic.getSlotNum();
		for(Edge e : edgeList) {
			int x = start/320;
			int y = start%320;
			for(int i=0;i<over;i++) {
			e.getSlotList()[x][y+i].setUsed(false);
			e.getSlotList()[x][y+i].setTraffic(0);;
			}
		}
	}
}
