# MCP Minimap

This is a basic Minimap build on MCP version 1.8.8. It uses the Minecraft map's system to render.

You can easily change the position and the scale of the Minimap, in the `render()` method of `MinimapRenderer.java`.

The Minimap also rotate based on the player's yaw, so you could see better the direction you're facing.

When you don't move, the Minimap will not update the `DynamicTexture`, to keep better FPS.
