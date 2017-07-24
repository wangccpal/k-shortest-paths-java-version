package paper.fit;

import java.util.List;

import edu.asu.emit.algorithm.graph.Graph;
import edu.asu.emit.algorithm.graph.Path;
import edu.asu.emit.algorithm.graph.VariableGraph;
import edu.asu.emit.algorithm.graph.shortestpaths.YenTopKShortestPathsAlg;
import paper.data.Edge;
import paper.data.Traffic;

public class AbstractRSCA {
	public static void main(String[] args) {
		Graph graph = new VariableGraph("data/JPN-12.txt");
		Traffic.unSerialTraffic("d:\\traffic1000.obj");
		SlotAssignAlgr sa = new FirstFitAlgrImpl();//设定时隙分配策略
		System.out.println("Testing batch processing of top-k shortest paths!");
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		int i=0;
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
//				int vnum = p.getVertexList().size();
//					for(int i = 0;i<vnum;i++){
//						Edge e = Edge.getEdge(p.getVertexList().get(i).getId(), p.getVertexList().get(i+1).getId());
//						if(!sa.slotAssign(e, tra.getSlotNum())) {
//							linkSuccess = false;
//							break;//break 的是link 的分配
//						}
//					}
//					// 如果到这里说明所有链路分配成功，剩下的path不需要再分配
//					if(linkSuccess) {
//						pathSuccess = true;
//						tra.setPath(p);
//						break;//break的是path的分配
//					}
			}
			tra.setSuccess(pathSuccess);
			System.out.println("业务: "+(i++)+" 路由是"+ tra.getPath());
		}
	}
	
	
	
}
