package fr.quiibz.minimap.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import fr.quiibz.minimap.data.MinimapData;
import fr.quiibz.minimap.render.gui.MinimapGui;
import fr.quiibz.minimap.utils.MinimapDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.MathHelper;

public class MinimapRenderer extends MinimapGui {

	private final MinimapData data;
    private final Minecraft mc;
    
    public MinimapRenderer() {
    	
    	this.data = new MinimapData();
    	this.mc = Minecraft.getMinecraft();
    }

    /**
     * Called every 50ms (20 times a second), to update
     * the Minimap. 
     */
    public void runTick() {
    	
    	this.data.update(this.mc.theWorld, this.mc.thePlayer);
    }
    
    /**
     * Render the Minimap
     */
    public void render() {
    	
    	// Theses values can be changed to
    	// change the position and scale
    	// of the Minimap in the screen
    	int x = 5;
    	int y = 5;
    	int scale = 50;
    	
    	x += scale;
    	y += scale;
    	
    	DynamicTexture texture = this.getTexture();
        float yaw = this.getYaw();

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(yaw, 0F, 0F, 1F);
        
        this.mc.getTextureManager().bindTexture(mc.getTextureManager().getDynamicTextureLocation("minimap", texture));
        this.renderFilledCircle(scale);
        
        // Render the outlined circle
        GL11.glColor3f(0F, 0F, 0F);
        GL11.glLineWidth(4F);
        this.renderCirle(scale + 1);
        
        // Render the player's marker at the middle
        int markerX = x + scale / 50;
        int markerY = y + scale / 50;
        GL11.glTranslatef(-x, -y, 0);
        GL11.glTranslatef(markerX, markerY, 0);
        GL11.glLineWidth(2F);
        this.renderCirle(1);
        
        GL11.glPopMatrix();
        
        // This will place better the directions to the circle
        scale += 2;
        
        // Render the directions
        for(MinimapDirection direction : MinimapDirection.values()) {
        	
        	int degValue = direction.getDegValue();
        	
        	int currentX = (int) (Math.cos(Math.toRadians(degValue + yaw)) * scale) - 3;
        	int currentY = (int) (Math.sin(Math.toRadians(degValue + yaw)) * scale) - 3;
        
        	this.drawString(this.mc.fontRendererObj, direction.getLetter(), x + currentX, y + currentY, new Color(1F, 1F, 1F).getRGB());
        }
    }
    
    /**
     * Fetch the texture of the Minimap
     * 
     * @return the MinimapData dynamtic texture
     */
    private DynamicTexture getTexture() {
    	
    	return this.data.getTexture();
    }
    
    /**
     * Fetch the current yaw rotation
     * 
     * @return the yaw of the player
     */
    private float getYaw() {
    	
    	return this.mc.thePlayer.rotationYaw;
    }
}
