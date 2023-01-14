package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.net.Proxy;

public class Derp extends GuiSettings{
    public float RH;
    public Derp(){
        super(GuiSettings.a(new char[]{'D','e','r','p'}),"",c4.Player,0,-1);
        RH=0;
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent en){
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        mc.thePlayer.setRotationYawHead(RH + 8F);
        RH += 8F;
        if(RH>=360F){
            RH=0F;
        }
    }
}
