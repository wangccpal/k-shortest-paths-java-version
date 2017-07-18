package paper.fit;

import java.util.LinkedList;

import edu.asu.emit.algorithm.graph.Path;
import paper.data.Edge;
import paper.data.Slot;

public class FirstFitAlgrImpl implements SlotAssignAlgr {

	public boolean slotAssign(Path path, int slotNum) {

		int vSum = path.getVertexList().size();
		int[] startSlot = new int[vSum - 1];
		if(findTimeSlot(0, slotNum, startSlot, path)){
			int start = startSlot[0];
			for (int i = 0; i < vSum - 1; i++) {
				Edge e = Edge.getEdge(path.getVertexList().get(i).getId(), path.getVertexList().get(i + 1).getId());
				Slot[][] slotList = e.getSlotList();
				int sum = 0;
				int j = start % slotList[0].length; // �ڼ���
				int h = start / slotList[0].length;// �ڼ���
				while(j<slotNum){
					slotList[h][j++].setUsed(true);
				}
			}
			path.setStart(start);
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
			while (sum != needSlotNum && j < slotList[0].length && h < slotList.length) {// i�൱��ѭ��ָ��
				// System.out.println(fiber.getTimeslotList());//CC
				if (!slotList[h][j].isUsed()) {
					sum++;// ���һֱ������ֱ���ҵ�����������ʱ϶���ߵ������һ��ʱ϶
					j++;
				} else {// ��������˲��ǿ��е�������ʱ϶����һ��ʱ϶��ʼ�ҵ���Ҫ������ʱ϶���ߵ������һ��ʱ϶������start����ʼ��λ��
					j = start + sum + 1;
					start = j;
					sum = 0;
				}
				if (j >= 320) {
					h++;
					j = 0;
				}
			} // ѭ���������ҵ��˸���·����ʼʱ϶�����Ҳ���
			if (sum == needSlotNum)
				startSlot[i] = h * 320 + j;
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

}
