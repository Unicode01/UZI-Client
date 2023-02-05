package io.fishermen.fpsdisplay.settings;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class PlayerESP extends GuiSettings {
    public PlayerESP(){
        super("PlayerESP","",c4.render,0,-1);
    }
    @SubscribeEvent
    public void a(final RenderLivingEvent.Specials.Pre d){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
            if (d.entity.equals((Object) Minecraft.getMinecraft().thePlayer) || !(d.entity instanceof EntityPlayer)) {
                return;
            }
                GuiSettings.HUD.drawBoxAroundEntity(d.entity,4,0D,0D, Color.WHITE.getRGB(),false);
                GuiSettings.HUD.drawBoxAroundEntity(d.entity,1,0D,0D, Color.WHITE.getRGB(),false);
    }
}
