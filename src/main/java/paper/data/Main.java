package paper.data;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String file = "d:\\traffic8000.obj";
		Traffic.randomGenTraffic(8000,12,12);
		Traffic.serialTraffic(file);
		Traffic.unSerialTraffic(file);
//		统计每种业务的数量
		int n3 = 0;
		int n4=0;  
		int n5 =0;
		for(Traffic tra : Traffic.tlist) {
			switch (tra.getSlotNum()) {
			case 3:
				n3++;
				break;
			case 4:
				n4++;
				break;
			case 5:
				n5++;
				break;
			default:
				break;
			}
		}
		System.out.println(n3 +", "+n4+", "+n5);
	}

}
