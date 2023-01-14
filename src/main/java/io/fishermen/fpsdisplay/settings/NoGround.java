package io.fishermen.fpsdisplay.settings;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.keystrokes.render.Gui;

public class NoGround extends GuiSettings {
    public CommandSettings Hight;
    public NoGround(){
        super("NoGround","",c4.Player,0,-1);
        Hight = new CommandSettings("Jump Edge Hight", 0.01, 0.01, 0.1, 0.01);
        this.avav(Hight);
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent E){
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        mc.thePlayer.onGround=false;
        if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY-Hight.g3tV4l4u3(),mc.thePlayer.posZ)).getBlock() != Blocks.air && mc.gameSettings.keyBindJump.isKeyDown()){
            mc.thePlayer.setSprinting(false);
            mc.thePlayer.jump();
        }
    }
}
