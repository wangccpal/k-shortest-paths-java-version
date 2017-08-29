package paper.fit;

import java.util.LinkedList;
import java.util.List;

import edu.asu.emit.algorithm.graph.Path;
import paper.data.Edge;
import paper.data.Slot;
import paper.data.Traffic;

public class FirstFitAlgrImpl implements SlotAssignAlgr {

	public boolean slotAssign(Path path, Traffic tra) {
		int slotNum = tra.getSlotNum();
		int vSum = path.getVertexList().size();
		int[] startSlot = new int[vSum - 1];
		int startLoc = 0;
		switch(tra.getSlotNum()) {
//		case 3:startLoc=0;break;
		case 4: startLoc = 640;break;
		case 5: startLoc = 160;break;
		}
		if(findTimeSlot(startLoc, slotNum, startSlot, path)){
			int start = startSlot[0];
			for (int i = 0; i < vSum - 1; i++) {
				Edge e = Edge.getEdge(path.getVertexList().get(i).getId(), path.getVertexList().get(i + 1).getId());
				Slot[][] slotList = e.getSlotList();
				int j = start % slotList[0].length; // �ڼ���
				int h = start / slotList[0].length;// �ڼ���
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
		int cc = start;// ��¼start��ʼֵ���Ա���ÿ����·���Գ�ʼֵ��ʼѰ��
		for (int i = 0; i < vSum - 1; i++) {
			Edge e = Edge.getEdge(path.getVertexList().get(i).getId(), path.getVertexList().get(i + 1).getId());
			Slot[][] slotList = e.getSlotList();
			start = cc;
			int sum = 0;
			int j = start % slotList[0].length; // �ڼ���
			int h = start / slotList[0].length;// �ڼ���
			start = start%320; //��ƥ���ʱ��start���ܳ���320
			
			if (needSlotNum==3) {
				while (sum != needSlotNum) {// i�൱��ѭ��ָ��
					if (h<2) {//h=0,1
						// System.out.println(fiber.getTimeslotList());//CC
						if (j >= 160) { //������ʱ���в�����0
							h++;
							j = 0;
							start = 0;
							sum = 0;
						} 
						if(h==2) {
							h=4;
							j=160;
							start=160;
						}
					} 
					else if(h<6){//h=4,5
						if (j >= 320) { //������ʱ���в�����0
							h++;
							j = 160;
							start = 160;
							sum = 0;
						} 
						if(h==6) {
							j= 0 ;
							start = 0;
						}
					} 
					else {//h=6
						if(j>=320) return false;
					}
					if (!slotList[h][j].isUsed()) {
						sum++;// ���һֱ������ֱ���ҵ�����������ʱ϶���ߵ������һ��ʱ϶
						j++;
					} else {// ��������˲��ǿ��е�������ʱ϶����һ��ʱ϶��ʼ�ҵ���Ҫ������ʱ϶���ߵ������һ��ʱ϶������start����ʼ��λ��
						j = start + sum + 1;
						start = j;
						sum = 0;
					}
				} // ѭ���������ҵ��˸���·����ʼʱ϶�����Ҳ���
			}
			else if(needSlotNum==4) {
//				h=2;
				while (sum != needSlotNum ) {// i�൱��ѭ��ָ��
					// System.out.println(fiber.getTimeslotList());//CC
					if (j >= 320) {
						h++;
						j = 0;
						start = 0;
						sum=0;
						if(h==4) h=6;
						else if(h==7) return false;
					}
					if (!slotList[h][j].isUsed()) {
						sum++;// ���һֱ������ֱ���ҵ�����������ʱ϶���ߵ������һ��ʱ϶
						j++;
					} else {// ��������˲��ǿ��е�������ʱ϶����һ��ʱ϶��ʼ�ҵ���Ҫ������ʱ϶���ߵ������һ��ʱ϶������start����ʼ��λ��
						j = start + sum + 1;
						start = j;
						sum = 0;
					}
				} // ѭ���������ҵ��˸���·����ʼʱ϶�����Ҳ���
			}
			else if(needSlotNum==5) {
				while (sum != needSlotNum) {// i�൱��ѭ��ָ��
					if (h<2) {//h=0,1
						// System.out.println(fiber.getTimeslotList());//CC
						if (j >= 320) { //������ʱ���в�����0
							h++;
							j = 160;
							start = 160;
							sum = 0;
						} 
						if(h==2) {
							h=4;
							j=0;
							start=0;
						}
					} 
					else if(h<6){//h=4,5
						if (j >= 160) { //������ʱ���в�����0
							h++;
							j = 0;
							start = 0;
							sum = 0;
						} 
						if(h==6) {
							j= 0 ;
							start = 0;
						}
					} 
					else {//h=6
						if(j>=320) return false;
					}
					if (!slotList[h][j].isUsed()) {
						sum++;// ���һֱ������ֱ���ҵ�����������ʱ϶���ߵ������һ��ʱ϶
						j++;
					} else {// ��������˲��ǿ��е�������ʱ϶����һ��ʱ϶��ʼ�ҵ���Ҫ������ʱ϶���ߵ������һ��ʱ϶������start����ʼ��λ��
						j = start + sum + 1;
						start = j;
						sum = 0;
					}
				} // ѭ���������ҵ��˸���·����ʼʱ϶�����Ҳ���
			}
			if (sum == needSlotNum)
				startSlot[i] = h * 320 + start;
			else {
				startSlot[i] = -1;
				return false;
			}
		}
		// ÿ����·��ҪƵ϶���һ���ж�
		int max = startSlot[0];
		int min = max;
		for (int i : startSlot) {
			max = max > i ? max : i;
			min = min < i ? min : i;
		}
		if (max != min) {// ��ʾ��·��ʼʱ϶��Ų�ȫһ���������ֵ��ʼ������
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
