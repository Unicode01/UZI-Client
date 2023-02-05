package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pw.cinque.CommandSettings.CommandSettings;

public class Bo extends GuiSettings {
    public static CommandSettings ea;
    public Bo() {
        super(GuiSettings.a(new char[] { 'B', 'H', 'o', 'p' }), "", c4.movement, 0, -1);
        Bo.ea = new CommandSettings(GuiSettings.a(new char[] { 'S', 'p', 'e', 'e', 'd' }), 2.0, 1.0, 15.0, 0.2);
        this.avav(Bo.ea);
    }

    @SubscribeEvent
    public void a(final PlayerTickEvent a) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
    if(GuiSettings.isMoving() && !mc.thePlayer.isInWater()){
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
        mc.thePlayer.noClip = true;
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
        mc.thePlayer.setSprinting(true);
        double spd = 0.0025D * (int) Bo.ea.g3tV4l4u3();
        double m = (float)(Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + spd);
        GuiSettings.bop(m);
        }
    }
}
