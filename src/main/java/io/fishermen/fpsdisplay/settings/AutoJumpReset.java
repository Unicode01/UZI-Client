package io.fishermen.fpsdisplay.settings;


import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class AutoJumpReset extends GuiSettings {
    private int tick;
    public AutoJumpReset(){
        super("AutoJumpReset","",c4.Player,0,-1);
    }
    @SubscribeEvent
    public void onTick(LivingEvent.LivingUpdateEvent event) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (Da.mc.thePlayer.hurtTime == Da.mc.thePlayer.maxHurtTime && Da.mc.thePlayer.maxHurtTime > 0 && mc.thePlayer.onGround && tick == 0) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
            tick=1;
        }
        if(tick==1){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
            tick=0;
        }
    }
}
