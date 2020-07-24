package fr.quiibz.minimap.utils;

public enum MinimapDirection {

	NORTH('N', 90),
	EAST('E', 0),
	SOUTH('S', 270),
	WEAST('W', 180),
	;
	
	private char letter;
	private int degValue;
	
	MinimapDirection(char letter, int degValue) {
		
		this.letter = letter;
		this.degValue = degValue;
	}
	
	/**
	 * Get the letter of the direction
	 * 
	 * @return the letter of the direction
	 */
	public String getLetter() {
		
		return String.valueOf(this.letter);
	}
	
	/**
	 * Get the deg value of the direction
	 * 
	 * @return the deg value of the direction
	 */
	public int getDegValue() {
		
		return this.degValue;
	}
}
