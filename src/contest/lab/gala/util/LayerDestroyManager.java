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
			layer.setTime(System.currentTimeMillis());
		}
	}
	
	public void removeLayer(LifeCycleInterface layer) {
		destroy_queue.remove(layer);
		layer.setTime(0);
	}
	
	public void deallocLayers() {
		CommonUtils.debug("deallocLayers");
		for (LifeCycleInterface layer : destroy_queue) {
			CommonUtils.debug(layer.toString());
			
			if (System.currentTimeMillis() - layer.getTime() > 15000) {
				CommonUtils.debug("destroy");
				layer.onDestroy();				
			} else {
				CommonUtils.debug("not destroy");
			}
		}
		System.gc();
	}
}
