package paper.fit;

import edu.asu.emit.algorithm.graph.Path;
import paper.data.Edge;
import paper.data.Slot;
import paper.data.Traffic;

public class FirstLastFitAlgrImpl implements SlotAssignAlgr {
	int n3;//时隙数量为3的业务数，用来确定First还是last fit
	int n4;
	int n5;

	//奇数业务firstfit，偶数lastfit
	public boolean slotAssign(Path path, Traffic traffic) {
		boolean rst = false;
		if(traffic.getSlotNum()==3) {
			n3++;
			if(n3%2==1) rst = slotAssignFirst(path, traffic);
			else rst = slotAssignLast(path, traffic);
		}
		if(traffic.getSlotNum()==4) {
			n4++;
			if(n4%2==1) rst = slotAssignFirst(path, traffic);
			else rst = slotAssignLast(path, traffic);
		}
		if(traffic.getSlotNum()==5) {
			n5++;
			if(n5%2==1) rst = slotAssignFirst(path, traffic);
			else rst = slotAssignLast(path, traffic);
		}
		return rst;
	}

	private boolean slotAssignLast(Path path, Traffic tra) {
		int slotNum = tra.getSlotNum();
		int vSum = path.getVertexList().size();
		int[] startSlot = new int[vSum - 1];
		int startLoc = 0;
		switch(tra.getSlotNum()) {
		case 3:startLoc = 1919 ;break;
		case 4: startLoc = 1279;break;
		case 5: startLoc = 1759;break;
		}
		if(findTimeSlot2(startLoc, slotNum, startSlot, path)){
			int start = startSlot[0];
			for (int i = 0; i < vSum - 1; i++) {
				Edge e = Edge.getEdge(path.getVertexList().get(i).getId(), path.getVertexList().get(i + 1).getId());
				Slot[][] slotList = e.getSlotList();
				int j = start % slotList[0].length; // 第几列
				int h = start / slotList[0].length;// 第几行
				int sum = 0;
				while(sum<slotNum){
					slotList[h][j-sum].setUsed(true);
					slotList[h][j-sum].setTraffic(tra.getId());
					sum++;
				}
			}
			path.setStart(start-slotNum+1);
			path.setOver(start);
			return true;
		}
		else return false;
	}

	//last fit
	private boolean findTimeSlot2(int startLoc, int needSlotNum, int[] startSlot, Path path) {
		int vSum = path.getVertexList().size();
		int cc = startLoc;// 记录start初始值，以便在每条链路都以初始值开始寻找
		for (int i = 0; i < vSum - 1; i++) {
			Edge e = Edge.getEdge(path.getVertexList().get(i).getId(), path.getVertexList().get(i + 1).getId());
			Slot[][] slotList = e.getSlotList();
			startLoc = cc;
			int sum = 0;
			int j = startLoc % slotList[0].length; // 第几列
			int h = startLoc / slotList[0].length;// 第几行
			startLoc = startLoc%320; //在匹配的时候start不能超过320
			
			if (needSlotNum==3) {
				while (sum != needSlotNum) {// i相当于循环指针
					if (h==4 || h==5) {//h=4,5
						// System.out.println(fiber.getTimeslotList());//CC
						if (j < 160) { //换光纤时所有参数归0
							h--;
							j = 319;
							startLoc = 319;
							sum = 0;
						} 
						if(h==3) {
							h=1;
							j=159;
							startLoc=159;
						}
					} 
					else if(h==0 || h==1){//h=1,0
						if (j < 0) { //换光纤时所有参数归0
							h--;
							j = 159;
							startLoc = 159;
							sum = 0;
						} 
						if(h==-1) {
							h=6;
							j= 319 ;
							startLoc = 319;
						}
					} 
					else {//h=6
						if(j<0) return false;
					}
					if (!slotList[h][j].isUsed()) {
						sum++;// 如果一直空闲则直到找到满足数量的时隙或者到达最后一个时隙
						j--;
					} else {// 如果遇到了不是空闲的则从这个时隙的下一个时隙开始找到需要数量的时隙或者到达最后一个时隙，更新start：开始的位置
						j = startLoc - sum - 1;
						startLoc = j;
						sum = 0;
					}
				} // 循环结束，找到了该链路的起始时隙或者找不到
			}
			else if(needSlotNum==4) {
//				h=2;
				while (sum != needSlotNum ) {// i相当于循环指针
					// System.out.println(fiber.getTimeslotList());//CC
					if (j < 0) {
						h--;
						j = 319;
						startLoc = 319;
						sum=0;
						if(h==1) h=6;
						else if(h==5) return false;
					}
					if (!slotList[h][j].isUsed()) {
						sum++;// 如果一直空闲则直到找到满足数量的时隙或者到达最后一个时隙
						j--;
					} else {// 如果遇到了不是空闲的则从这个时隙的下一个时隙开始找到需要数量的时隙或者到达最后一个时隙，更新start：开始的位置
						j = startLoc - sum - 1;
						startLoc = j;
						sum = 0;
					}
				} // 循环结束，找到了该链路的起始时隙或者找不到
			}
			else if(needSlotNum==5) {
				while (sum != needSlotNum) {// i相当于循环指针
					if (h==4 || h==5) {//h=4,5
						// System.out.println(fiber.getTimeslotList());//CC
						if (j < 0) { //换光纤时所有参数归0
							h--;
							j = 159;
							startLoc = 159;
							sum = 0;
						} 
						if(h==3) {
							h=1;
							j=319;
							startLoc=319;
						}
					} 
					else if(h==0 || h==1){//h=4,5
						if (j < 160) { //换光纤时所有参数归0
							h--;
							j = 319;
							startLoc = 319;
							sum = 0;
						} 
						if(h==-1) {
							h=6;
							j= 319 ;
							startLoc = 319;
						}
					} 
					else {//h=6
						if(j<0) return false;
					}
					if (!slotList[h][j].isUsed()) {
						sum++;// 如果一直空闲则直到找到满足数量的时隙或者到达最后一个时隙
						j--;
					} else {// 如果遇到了不是空闲的则从这个时隙的下一个时隙开始找到需要数量的时隙或者到达最后一个时隙，更新start：开始的位置
						j = startLoc - sum - 1;
						startLoc = j;
						sum = 0;
					}
				} // 循环结束，找到了该链路的起始时隙或者找不到
			}
			if (sum == needSlotNum)
				startSlot[i] = h * 320 + startLoc;
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
		if (max != min) {// 表示链路开始时隙序号不全一样，从最小值开始重新找,当有一根光纤在第7核上找时所有都要去第七核
			if(max>=1920 && min < 1920) return findTimeSlot2(max, needSlotNum, startSlot, path);
			else return findTimeSlot2(min, needSlotNum, startSlot, path);
		} else
			return true;
	}

	public boolean slotAssignFirst(Path path, Traffic tra) {
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
			
			if (needSlotNum==3) {
				while (sum != needSlotNum) {// i相当于循环指针
					if (h<2) {//h=0,1
						// System.out.println(fiber.getTimeslotList());//CC
						if (j >= 160) { //换光纤时所有参数归0
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
						if (j >= 320) { //换光纤时所有参数归0
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
						sum++;// 如果一直空闲则直到找到满足数量的时隙或者到达最后一个时隙
						j++;
					} else {// 如果遇到了不是空闲的则从这个时隙的下一个时隙开始找到需要数量的时隙或者到达最后一个时隙，更新start：开始的位置
						j = start + sum + 1;
						start = j;
						sum = 0;
					}
				} // 循环结束，找到了该链路的起始时隙或者找不到
			}
			else if(needSlotNum==4) {
//				h=2;
				while (sum != needSlotNum ) {// i相当于循环指针
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
						sum++;// 如果一直空闲则直到找到满足数量的时隙或者到达最后一个时隙
						j++;
					} else {// 如果遇到了不是空闲的则从这个时隙的下一个时隙开始找到需要数量的时隙或者到达最后一个时隙，更新start：开始的位置
						j = start + sum + 1;
						start = j;
						sum = 0;
					}
				} // 循环结束，找到了该链路的起始时隙或者找不到
			}
			else if(needSlotNum==5) {
				while (sum != needSlotNum) {// i相当于循环指针
					if (h<2) {//h=0,1
						// System.out.println(fiber.getTimeslotList());//CC
						if (j >= 320) { //换光纤时所有参数归0
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
						if (j >= 160) { //换光纤时所有参数归0
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
						sum++;// 如果一直空闲则直到找到满足数量的时隙或者到达最后一个时隙
						j++;
					} else {// 如果遇到了不是空闲的则从这个时隙的下一个时隙开始找到需要数量的时隙或者到达最后一个时隙，更新start：开始的位置
						j = start + sum + 1;
						start = j;
						sum = 0;
					}
				} // 循环结束，找到了该链路的起始时隙或者找不到
			}
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
}
