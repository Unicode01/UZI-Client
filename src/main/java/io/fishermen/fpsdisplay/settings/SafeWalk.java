package io.fishermen.fpsdisplay.settings;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import pw.cinque.CommandSettings.CommandSettings;

public class SafeWalk extends GuiSettings{
    //public static CommandSettings mode;
    public SafeWalk()
    {
        super(GuiSettings.a(new char[] {'S','a','f','e','W','a','l','k'}),"", c4.movement, 0, -1);
        //SafeWalk.mode = new CommandSettings(GuiSettings.a(new char[]{'M','o','d','e'}),1,1,2,1);
        //this.avav(mode);
        ///SafeWalk.edge = new CommandSettings(GuiSettings.a(new char[] { 'e', 'd', 'g', 'e' }), 0.25, 0.01, 0.5, 0.01);
        ///this.avav(edge);
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent a) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        /*if (mode.g3tV4l4u3() == 1) {
            if (mc.thePlayer.onGround
                    && !Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()
                    && !isCollidable(Minecraft.getMinecraft().theWorld
                    .getBlockState(new BlockPos(
                            Minecraft.getMinecraft().thePlayer.getPositionVector().add(new Vec3(0, -0.5, 0))))
                    .getBlock()))
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), true);

            else if (!Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode()))
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
        }*/
        if (mc.thePlayer.onGround
                && !Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()
                && !isCollidable(Minecraft.getMinecraft().theWorld
                .getBlockState(new BlockPos(
                        Minecraft.getMinecraft().thePlayer.getPositionVector().add(new Vec3(0, -0.5, 0))))
                .getBlock()))
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), true);

        else if (!Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode()))
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
    }
    public static boolean isCollidable(Block block) {
        return block != Blocks.air && block != Blocks.carrots && block != Blocks.deadbush
                && block != Blocks.double_plant && block != Blocks.flowing_lava && block != Blocks.flowing_water
                && block != Blocks.lava && block != Blocks.melon_stem && block != Blocks.nether_wart
                && block != Blocks.potatoes && block != Blocks.pumpkin_stem && block != Blocks.red_flower
                && block != Blocks.red_mushroom && block != Blocks.redstone_torch && block != Blocks.tallgrass
                && block != Blocks.torch && block != Blocks.unlit_redstone_torch && block != Blocks.yellow_flower
                && block != Blocks.vine && block != Blocks.water && block != Blocks.web && block != Blocks.wheat;
    }
    @Override
    public void dd() {
        if (!Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
        }
    }
}
