package fr.quiibz.minimap.render.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class MinimapGui extends Gui {

	/**
	 * Render a filled circle with the given radius
	 * 
	 * @param radius
	 */
	protected void renderFilledCircle(int radius) {
		
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_POLYGON);
        
		for(int i = 360; i >= 0; i-=5) {
        	
			GL11.glTexCoord2f((MathHelper.cos((float) Math.toRadians(i)) + 1) * 0.5f, (MathHelper.sin((float) Math.toRadians(i)) + 1) * 0.5f);
			GL11.glVertex2f(MathHelper.cos((float) Math.toRadians(i)) * radius, MathHelper.sin((float) Math.toRadians(i)) * radius);
		}

		GL11.glEnd();	
		GL11.glPopMatrix();
    }
	
	/**
	 * Render a outlined circle with the given radius
	 * 
	 * @param radius
	 */
	protected void renderCirle(int radius) {
		
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		 
		for (int i = 0; i <= 360; i++) {
			
			double degInRad = Math.toRadians(i);
			GL11.glVertex2d(Math.cos(degInRad) * radius, Math.sin(degInRad) * radius);
		}
		
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}
