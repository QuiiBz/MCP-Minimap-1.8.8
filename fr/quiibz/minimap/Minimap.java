package fr.quiibz.minimap;

import fr.quiibz.minimap.render.MinimapRenderer;

public class Minimap {

	private static Minimap instance;
	private final MinimapRenderer renderer;
	
	public Minimap() {
		
		instance = this;
		
		this.renderer = new MinimapRenderer();
	}
	
	/**
	 * Fetch the MinimapRenderer instance
	 * 
	 * @return the MinimapRenderer instance
	 */
	public MinimapRenderer getRenderer() {
		
		return this.renderer;
	}
	
	/**
	 * Fetch the Minimap instance
	 * 
	 * @return the Minimap instance
	 */
	public static Minimap getInstance() {
		
		return instance;
	}
}
