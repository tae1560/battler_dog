package contest.lab.gala.util;

import java.util.ArrayList;

import contest.lab.gala.interfaces.LifeCycleInterface;

public class LayerDestroyManager {
	private static LayerDestroyManager _instance = null;
	public static LayerDestroyManager getInstance() {
		if (_instance == null) {
			_instance = new LayerDestroyManager();
		}
		return _instance;
	}

	private ArrayList<LifeCycleInterface> destroy_queue = null;
	public LayerDestroyManager() {
		destroy_queue = new ArrayList<LifeCycleInterface>();
	}
	
	public void addLayer(LifeCycleInterface layer) {
		if (! destroy_queue.contains(layer)) {
			destroy_queue.add(layer);
		}
	}
	
	public void removeLayer(LifeCycleInterface layer) {
		destroy_queue.remove(layer);
	}
	
	public void deallocLayers() {
		for (LifeCycleInterface layer : destroy_queue) {
			layer.onDestroy();
		}
	}
}
