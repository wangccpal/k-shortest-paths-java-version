package paper.fit;

import edu.asu.emit.algorithm.graph.Path;
import paper.data.Edge;
import paper.data.Traffic;

public interface SlotAssignAlgr {
	public boolean slotAssign(Path path, Traffic traffic);
	
	public void slotRelease(Traffic traffic);
}
