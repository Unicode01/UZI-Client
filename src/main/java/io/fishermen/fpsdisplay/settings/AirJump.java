package io.fishermen.fpsdisplay.settings;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AirJump extends GuiSettings{
    public AirJump(){
        super(GuiSettings.a(new char[]{'A','i','r','J','u','m','p'}),"",c4.movement,0,-1);
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent en){
        if(!this.getStat()){
            return;
        }
        if(mc.thePlayer.fallDistance >= 0.8F && GuiSettings.isPlayerInGame()){
            mc.thePlayer.fallDistance=0F;
            mc.thePlayer.onGround=true;
            mc.thePlayer.jump();
        }
    }
}
