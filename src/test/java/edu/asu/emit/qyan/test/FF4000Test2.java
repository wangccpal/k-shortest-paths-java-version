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

public class FF4000Test2 {
	public static final String file = "d:\\traffic4000.obj";
//	@BeforeTest
	public void generateTraTest(){
			String file = "d:\\traffic320.obj";
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
		Traffic.randomGenTraffic(2000,12,12);
		Traffic.serialTraffic("d:\\traffic1000.obj");
		Traffic.unSerialTraffic("d:\\traffic1000.obj");
		}
	@Test
	public void  ffplustest() {//pure ff
		Traffic.unSerialTraffic(file);

		Graph graph = new VariableGraph("data/JPN-12.txt");
		SlotAssignAlgr sa = new FirstFitPlusImpl();//设定时隙分配策略
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		
		int i=0;
		Traffic t = null;
		for(Traffic tra : Traffic.tlist){
		List<Path> shortest_paths_list = yenAlg.getShortestPaths(graph.getVertex(tra.getFrom()), graph.getVertex(tra.getTo()), 1);
//		System.out.println(":" + shortest_paths_list); 
		boolean pathSuccess = false;
			for(Path p : shortest_paths_list){
				if(sa.slotAssign(p, tra)){
					pathSuccess = true;
					tra.setPath(p);
					break;//break的是path的分配
				};
			}
			tra.setSuccess(pathSuccess);
//			System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
//			System.out.println(tra);
			t= tra;
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

		Graph graph = new VariableGraph("data/JPN-12.txt");
		SlotAssignAlgr sa = new FirstFitAlgrImpl();//设定时隙分配策略
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		
		int i=0;
		Traffic t = null;
		for(Traffic tra : Traffic.tlist){
		List<Path> shortest_paths_list = yenAlg.getShortestPaths(graph.getVertex(tra.getFrom()), graph.getVertex(tra.getTo()), 1);
//		System.out.println(":" + shortest_paths_list); 
		boolean pathSuccess = false;
			for(Path p : shortest_paths_list){
				if(sa.slotAssign(p, tra)){
					pathSuccess = true;
					tra.setPath(p);
					break;//break的是path的分配
				};
			}
			tra.setSuccess(pathSuccess);
//			System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
//			System.out.println(tra);
			t= tra;
		}
		for(Entry<String,Edge> e : Edge.emap.entrySet()) {
			System.out.println(e.getValue());
		}
		System.out.println(Traffic.blockingRate());
//		Assert.assertEquals(t.getEdgeList().get(0).getSlotList()[0][319].isUsed(), true);
	}
	
//	@Test
	public void  fforitest() {//33,44,55
		Traffic.unSerialTraffic(file);

		Graph graph = new VariableGraph("data/JPN-12.txt");
		SlotAssignAlgr sa = new FirstFitOriImpl();//设定时隙分配策略
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
					break;//break的是path的分配
				};
			}
			tra.setSuccess(pathSuccess);
//			System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
//			System.out.println(tra);
			t= tra;
		}
		for(Entry<String,Edge> e : Edge.emap.entrySet()) {
			System.out.println(e.getValue());
		}
		System.out.println(Traffic.blockingRate());
//		Assert.assertEquals(t.getEdgeList().get(0).getSlotList()[0][319].isUsed(), true);
	}
	
//	@Test
	public void  fltest() {//35,44,53 flf
		Traffic.unSerialTraffic(file);

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
					break;//break的是path的分配
				};
			}
			tra.setSuccess(pathSuccess);
			System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
			System.out.println(tra);
			t= tra;
		}
		for(Entry<String,Edge> e : Edge.emap.entrySet()) {
			System.out.println(e.getValue());
		}
		System.out.println(Traffic.blockingRate());
//		Assert.assertEquals(t.getEdgeList().get(0).getSlotList()[0][319].isUsed(), true);
	}
	
}
