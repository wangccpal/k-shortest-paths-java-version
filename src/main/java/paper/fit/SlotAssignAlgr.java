package paper.fit;

import edu.asu.emit.algorithm.graph.Path;
import paper.data.Edge;

public interface SlotAssignAlgr {
	public boolean slotAssign(Path path,int slotNum);
}
