package fr.quiibz.minimap.data;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import fr.quiibz.minimap.render.MinimapRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

public class MinimapData {

	private final DynamicTexture texture;
	private final byte[] colors = new byte[16384];
   
	// Used to check if we need to update the
	// data and the texture of the Minimap
	private int lastX;
	private int lastZ;
	
    public MinimapData() {
    	
    	this.texture = new DynamicTexture(128, 128);

        for(int i = 0; i < this.texture.getTextureData().length; ++i)
        	this.texture.getTextureData()[i] = 0;
    }
    
    /**
     * Called every tick from {@link MinimapRenderer#runTick()}
     * 
     * @param world
     * @param player
     */
    public void update(World world, Entity player) {
    	
    	this.updateData(world, player);
    	this.updateTexture();
    }
    
    /**
     * Check if the Minimap needs to be updated,
     * based on the current location of the player
     * and the saved last location
     * 
     * @return if we should update the Minimap
     */
    private boolean shouldUpdate(Entity player) {
    	
    	int x = player.getPosition().getX();
    	int z = player.getPosition().getZ();
    	
    	// If the location has changed, we needs to update
    	// the data and texture of the Minimap
    	if(this.lastX != x || this.lastZ != z) {
    		
    		this.lastX = x;
    		this.lastZ = z;
    		
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Update the data of the Minimap
     * NOTE: Taken from {@link ItemMap#updateMapData}
     * 
     * @param world
     * @param player
     */
    private void updateData(World world, Entity player) {
    	
    	// Not needed since we don't use MapData
    	//if (worldIn.provider.getDimensionId() == data.dimension && viewer instanceof EntityPlayer)
        //{
            int i = 1; // Can be changed to change the scale
            int j = player.getPosition().getX();
            int k = player.getPosition().getZ();
            int l = MathHelper.floor_double(player.posX - (double)j) / i + 64;
            int i1 = MathHelper.floor_double(player.posZ - (double)k) / i + 64;
            int j1 = 128 / i;

            if (world.provider.getHasNoSky())
            {
                j1 /= 2;
            }

            // Not needed since we don't use MapData
            //MapData.MapInfo mapdata$mapinfo = data.getMapInfo((EntityPlayer)viewer);
            //++mapdata$mapinfo.field_82569_d;
            //boolean flag = false;

            for (int k1 = l - j1 + 1; k1 < l + j1; ++k1)
            {
            	// Not needed since we don't use MapData
                //if ((k1 & 15) == (mapdata$mapinfo.field_82569_d & 15) || flag)
                //{
                	//flag = false;
                    double d0 = 0.0D;

                    for (int l1 = i1 - j1 - 1; l1 < i1 + j1; ++l1)
                    {
                        if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128)
                        {
                            int i2 = k1 - l;
                            int j2 = l1 - i1;
                            boolean flag1 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2);
                            int k2 = (j / i + k1 - 64) * i;
                            int l2 = (k / i + l1 - 64) * i;
                            Multiset<MapColor> multiset = HashMultiset.<MapColor>create();
                            Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(k2, 0, l2));

                            if (!chunk.isEmpty())
                            {
                                int i3 = k2 & 15;
                                int j3 = l2 & 15;
                                int k3 = 0;
                                double d1 = 0.0D;

                                if (world.provider.getHasNoSky())
                                {
                                    int l3 = k2 + l2 * 231871;
                                    l3 = l3 * l3 * 31287121 + l3 * 11;

                                    if ((l3 >> 20 & 1) == 0)
                                    {
                                        multiset.add(Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)), 10);
                                    }
                                    else
                                    {
                                        multiset.add(Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE)), 100);
                                    }

                                    d1 = 100.0D;
                                }
                                else
                                {
                                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                                    for (int i4 = 0; i4 < i; ++i4)
                                    {
                                        for (int j4 = 0; j4 < i; ++j4)
                                        {
                                            int k4 = chunk.getHeightValue(i4 + i3, j4 + j3) + 1;
                                            IBlockState iblockstate = Blocks.air.getDefaultState();

                                            if (k4 > 1)
                                            {
                                                label541:
                                                {
                                                    while (true)
                                                    {
                                                        --k4;
                                                        iblockstate = chunk.getBlockState(blockpos$mutableblockpos.func_181079_c(i4 + i3, k4, j4 + j3));

                                                        if (iblockstate.getBlock().getMapColor(iblockstate) != MapColor.airColor || k4 <= 0)
                                                        {
                                                            break;
                                                        }
                                                    }

                                                    if (k4 > 0 && iblockstate.getBlock().getMaterial().isLiquid())
                                                    {
                                                        int l4 = k4 - 1;

                                                        while (true)
                                                        {
                                                            Block block = chunk.getBlock(i4 + i3, l4--, j4 + j3);
                                                            ++k3;

                                                            if (l4 <= 0 || !block.getMaterial().isLiquid())
                                                            {
                                                                break label541;
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            d1 += (double)k4 / (double)(i * i);
                                            multiset.add(iblockstate.getBlock().getMapColor(iblockstate));
                                        }
                                    }
                                }

                                k3 = k3 / (i * i);
                                double d2 = (d1 - d0) * 4.0D / (double)(i + 4) + ((double)(k1 + l1 & 1) - 0.5D) * 0.4D;
                                int i5 = 1;

                                if (d2 > 0.6D)
                                {
                                    i5 = 2;
                                }

                                if (d2 < -0.6D)
                                {
                                    i5 = 0;
                                }

                                MapColor mapcolor = (MapColor)Iterables.getFirst(Multisets.<MapColor>copyHighestCountFirst(multiset), MapColor.airColor);

                                if (mapcolor == MapColor.waterColor)
                                {
                                    d2 = (double)k3 * 0.1D + (double)(k1 + l1 & 1) * 0.2D;
                                    i5 = 1;

                                    if (d2 < 0.5D)
                                    {
                                        i5 = 2;
                                    }

                                    if (d2 > 0.9D)
                                    {
                                        i5 = 0;
                                    }
                                }

                                d0 = d1;

                                if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 1) != 0))
                                {
                                    byte b0 = this.colors[k1 + l1 * 128];
                                    byte b1 = (byte)(mapcolor.colorIndex * 4 + i5);

                                    if (b0 != b1)
                                    {
                                        this.colors[k1 + l1 * 128] = b1;
                                        
                                        // Not needed since we don't use MapData
                                        //data.updateMapData(k1, l1);
                                        //flag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            //}
        //}
    }
    
    /**
     * Update the texture of the Minimap
     * NOTE: Taken from {@link MapItemRenderer#updateMapTexture(MapData)}
     */
    private void updateTexture() {
    	
    	for (int i = 0; i < 16384; ++i)
        {
            int j = this.colors[i] & 255;

            if (j / 4 == 0)
            {
                this.texture.getTextureData()[i] = (i + i / 128 & 1) * 8 + 16 << 24;
            }
            else
            {
                this.texture.getTextureData()[i] = MapColor.mapColorArray[j / 4].func_151643_b(j & 3);
            }
        }

        this.texture.updateDynamicTexture();
    }
    
    /**
     * Fetch the current dynamic texture
     * 
     * @return the dynamic texture of the Minimap
     */
    public DynamicTexture getTexture() {
    	
		return this.texture;
	}
}
