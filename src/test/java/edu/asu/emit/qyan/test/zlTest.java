package edu.asu.emit.qyan.test;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import edu.asu.emit.algorithm.graph.Graph;
import edu.asu.emit.algorithm.graph.Path;
import edu.asu.emit.algorithm.graph.VariableGraph;
import edu.asu.emit.algorithm.graph.shortestpaths.YenTopKShortestPathsAlg;
import paper.data.Edge;
import paper.data.Traffic;
import paper.fit.FirstFitAlgrImpl;
import paper.fit.FirstFitOriImpl;
import paper.fit.FirstFitPlusImpl;
import paper.fit.FirstLastFitAlgrImpl;
import paper.fit.SlotAssignAlgr;

public class zlTest {
	public static final String file = "d:\\traffic8000.obj";
	static int success = 0; 
//	@BeforeTest
	public void generateTraTest(){
			String file = "d:\\traffic500.obj";
//			int[] slotnum = { 3, 4, 5 }; // 业务的slot数量
			Random r = new Random();
			int num = 800;
			for (int i = 0; i < num ; i++) {
				int fromid = 12;
				int toid = 7;
				Traffic.tlist.add(new Traffic(3, fromid, toid,i));
			}
			Traffic.serialTraffic(file);
//			Traffic.unSerialTraffic(file);
//			统计每种业务的数量
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
//	@BeforeTest
		public void selectGraph() {
		Traffic.randomGenTraffic(3000,12,12);
		Traffic.serialTraffic("d:\\traffic1000.obj");
		Traffic.unSerialTraffic("d:\\traffic1000.obj");
		}
//	@Test
	public void  ffplustest() {//pure ff
		Traffic.unSerialTraffic(file);
		int rls = 5;//每几个业务就释放一个
		int rlsCount=0;//释放哪一个
		Graph graph = new VariableGraph("data/JPN-12.txt");
		SlotAssignAlgr sa = new FirstFitPlusImpl();//设定时隙分配策略
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		
		int i=0;
		Traffic t = null;
		for(Traffic tra : Traffic.tlist){
			List<Path> shortest_paths_list = yenAlg.getShortestPaths(graph.getVertex(tra.getFrom()), graph.getVertex(tra.getTo()), 3);
//			System.out.println(":" + shortest_paths_list); 
			boolean pathSuccess = false;
				for(Path p : shortest_paths_list){
					if(sa.slotAssign(p, tra)){
						pathSuccess = true;
						tra.setPath(p);
						success++;
						break;//break的是path的分配
					};
				}
				tra.setSuccess(pathSuccess);
				i++;
//				System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
//				System.out.println(tra);
				if( (i % rls)==0 ) sa.slotRelease(Traffic.tlist.get(rlsCount++) );
				t= tra;
				for( Edge e :tra.getEdgeList() )
				e.calUseRate() ;//再这里更新一下链路利用率来计算load
				double load = Edge.getTrafficLoad()/7.0;
				//0.6,.0.7,0.,0.9,1附近打印一次
				if( (load > 0.599 && load < 0.601) || (load > 0.699 && load < 0.701) || (load > 0.799 && load < 0.801) 
						|| (load > 0.899 && load < 0.901)  || (load > 0.809 && load < 0.811) || (load > 0.819 && load < 0.821) 
						|| (load > 0.829 && load < 0.831)|| (load > 0.839 && load < 0.841)|| (load > 0.849 && load < 0.851)
						|| (load > 0.859 && load < 0.861)|| (load > 0.869 && load < 0.871)|| (load > 0.879 && load < 0.881)
						|| (load > 0.889 && load < 0.891))
					System.out.println("current load is " + load +", BR:" + (1-(double)success/i) + " i:" + i);
//				System.out.println("current load is " + load +", BR:" + (1-(double)success/i));
			}
		for(Entry<String,Edge> e : Edge.emap.entrySet()) {
			System.out.println(e.getValue());
		}
		System.out.println(Traffic.blockingRate());
//		Assert.assertEquals(t.getEdgeList().get(0).getSlotList()[0][319].isUsed(), true);
	
	}
	
//	@Test
	public void  fftest() {//35,44,53 ff
		Traffic.unSerialTraffic(file);
		int rls = 5;//每几个业务就释放一个
		int rlsCount=0;//释放哪一个
		Graph graph = new VariableGraph("data/JPN-12.txt");
		SlotAssignAlgr sa = new FirstFitAlgrImpl();//设定时隙分配策略
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		
		int i=0;
		Traffic t = null;
		for(Traffic tra : Traffic.tlist){
			List<Path> shortest_paths_list = yenAlg.getShortestPaths(graph.getVertex(tra.getFrom()), graph.getVertex(tra.getTo()), 3);
//			System.out.println(":" + shortest_paths_list); 
			boolean pathSuccess = false;
				for(Path p : shortest_paths_list){
					if(sa.slotAssign(p, tra)){
						pathSuccess = true;
						tra.setPath(p);
						success++;
						break;//break的是path的分配
					};
				}
				tra.setSuccess(pathSuccess);
				i++;
//				System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
//				System.out.println(tra);
				if( (i % rls)==0 ) sa.slotRelease(Traffic.tlist.get(rlsCount++) );
				t= tra;
				for( Edge e :tra.getEdgeList() )
				e.calUseRate() ;//再这里更新一下链路利用率来计算load
				double load = Edge.getTrafficLoad()/7.0;
				//0.6,.0.7,0.,0.9,1附近打印一次
//				if( (load > 0.599 && load < 0.601) || (load > 0.699 && load < 0.701) || (load > 0.799 && load < 0.801) 
//						|| (load > 0.899 && load < 0.901)  || (load > 0.809 && load < 0.811) || (load > 0.819 && load < 0.821) 
//						|| (load > 0.829 && load < 0.831)|| (load > 0.839 && load < 0.841)|| (load > 0.849 && load < 0.851)
//						|| (load > 0.859 && load < 0.861)|| (load > 0.869 && load < 0.871)|| (load > 0.879 && load < 0.881)
//						|| (load > 0.889 && load < 0.891))
//					System.out.println("current load is " + load +", BR:" + (1-(double)success/i) + " i:" + i);
//				System.out.println("current load is " + load +", BR:" + (1-(double)success/i));
			}
		for(Entry<String,Edge> e : Edge.emap.entrySet()) {
			System.out.println(e.getValue());
		}
		System.out.println(Traffic.blockingRate());
//		Assert.assertEquals(t.getEdgeList().get(0).getSlotList()[0][319].isUsed(), true);
	}
	
	@Test
	public void  fforitest() {//33,44,55
		//35,44,53 ff
				Traffic.unSerialTraffic(file);
				int rls = 5;//每几个业务就释放一个
				int rlsCount=0;//释放哪一个
				Graph graph = new VariableGraph("data/JPN-12.txt");
				SlotAssignAlgr sa = new FirstFitOriImpl();//设定时隙分配策略
				YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
				
				int i=0;
				Traffic t = null;
				for(Traffic tra : Traffic.tlist){
					List<Path> shortest_paths_list = yenAlg.getShortestPaths(graph.getVertex(tra.getFrom()), graph.getVertex(tra.getTo()), 3);
//					System.out.println(":" + shortest_paths_list); 
					boolean pathSuccess = false;
						for(Path p : shortest_paths_list){
							if(sa.slotAssign(p, tra)){
								pathSuccess = true;
								tra.setPath(p);
								success++;
								break;//break的是path的分配
							};
						}
						tra.setSuccess(pathSuccess);
						i++;
//						System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
//						System.out.println(tra);
						if( (i % rls)==0 ) sa.slotRelease(Traffic.tlist.get(rlsCount++) );
						t= tra;
						for( Edge e :tra.getEdgeList() )
						e.calUseRate() ;//再这里更新一下链路利用率来计算load
						double load = Edge.getTrafficLoad()/7.0;
						//0.6,.0.7,0.,0.9,1附近打印一次
//						if( (load > 0.599 && load < 0.601) || (load > 0.699 && load < 0.701) || (load > 0.799 && load < 0.801) 
//								|| (load > 0.899 && load < 0.901)  || (load > 0.809 && load < 0.811) || (load > 0.819 && load < 0.821) 
//								|| (load > 0.829 && load < 0.831)|| (load > 0.839 && load < 0.841)|| (load > 0.849 && load < 0.851)
//								|| (load > 0.859 && load < 0.861)|| (load > 0.869 && load < 0.871)|| (load > 0.879 && load < 0.881)
//								|| (load > 0.889 && load < 0.891))
//							System.out.println("current load is " + load +", BR:" + (1-(double)success/i) + " i:" + i);
//						System.out.println("current load is " + load +", BR:" + (1-(double)success/i));
					}
				for(Entry<String,Edge> e : Edge.emap.entrySet()) {
					System.out.println(e.getValue());
				}
				System.out.println(Traffic.blockingRate());
//				Assert.assertEquals(t.getEdgeList().get(0).getSlotList()[0][319].isUsed(), true);
			
	}
	
//	@Test
	public void  fltest() {//35,44,53 flf
		Traffic.unSerialTraffic(file);
		int rls = 5;//每几个业务就释放一个
		int rlsCount=0;//释放哪一个
//		List<Traffic> completedList = new ArrayList<Traffic>();
		Graph graph = new VariableGraph("data/JPN-12.txt");
		SlotAssignAlgr sa = new FirstLastFitAlgrImpl();//设定时隙分配策略
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		
		int i=0;
		Traffic t = null;
		for(Traffic tra : Traffic.tlist){
		List<Path> shortest_paths_list = yenAlg.getShortestPaths(graph.getVertex(tra.getFrom()), graph.getVertex(tra.getTo()), 3);
//		System.out.println(":" + shortest_paths_list); 
		boolean pathSuccess = false;
			for(Path p : shortest_paths_list){
				if(sa.slotAssign(p, tra)){
					pathSuccess = true;
					tra.setPath(p);
					success++;
					break;//break的是path的分配
				};
			}
			tra.setSuccess(pathSuccess);
			i++;
//			System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
//			System.out.println(tra);
			if( (i % rls)==0 ) sa.slotRelease(Traffic.tlist.get(rlsCount++) );
			t= tra;
			for( Edge e :tra.getEdgeList() )
			e.calUseRate() ;//再这里更新一下链路利用率来计算load
			double load = Edge.getTrafficLoad()/7.0;
			//0.6,.0.7,0.,0.9,1附近打印一次
			if( (load > 0.599 && load < 0.601) || (load > 0.699 && load < 0.701) || (load > 0.799 && load < 0.801) 
					|| (load > 0.899 && load < 0.901)  || (load > 0.809 && load < 0.811) || (load > 0.819 && load < 0.821) 
					|| (load > 0.829 && load < 0.831)|| (load > 0.839 && load < 0.841)|| (load > 0.849 && load < 0.851)
					|| (load > 0.859 && load < 0.861)|| (load > 0.869 && load < 0.871)|| (load > 0.879 && load < 0.881)
					|| (load > 0.889 && load < 0.891))
				System.out.println("current load is " + load +", BR:" + (1-(double)success/i) + " i:" + i);
//			System.out.println("current load is " + load +", BR:" + (1-(double)success/i));
		}
		for(Entry<String,Edge> e : Edge.emap.entrySet()) {
			//toString 会计算e的利用率，或者调用caluse
			System.out.println(e.getValue());
		}
		System.out.println(Traffic.blockingRate());
//		Assert.assertEquals(t.getEdgeList().get(0).getSlotList()[0][319].isUsed(), true);
	}
//	@Test 
	public void add(){
		int a =5;
		int b =3;
		System.out.println("asfasfasf"+(double)a/b);
	}
}
