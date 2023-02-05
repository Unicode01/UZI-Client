// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import java.util.Random;
import pw.cinque.CommandSettings.CommandSettings;

public class RR extends GuiSettings
{
    private long n;
    private long a;
    public static CommandSettings c;
    public Random random;
    
    public RR() {
        super(GuiSettings.a(new char[] { 'W', 'T', 'a', 'p' }), "", c4.combat1, 0, 0);
        this.n = 0L;
        this.a = 0L;
        this.random = new Random();
        this.avav(RR.c = new CommandSettings(GuiSettings.a(new char[] { 'C', 'h', 'a', 'n', 'c', 'e' }), 90.0, 1.0, 100.0, 1.0));
    }
    
    @SubscribeEvent
    public void a(final AttackEntityEvent a) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (!RR.mc.thePlayer.isSprinting()) {
            return;
        }
        if ((int)RR.c.g3tV4l4u3() >= this.random.nextInt(100) && this.a == 0L && this.n == 0L) {
            this.a = System.currentTimeMillis() + 40L + this.random.nextInt(325);
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.RenderTickEvent ee) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (!RR.mc.thePlayer.isSprinting() && this.a > 0L) {
            this.a = 0L;
            return;
        }
        if (System.currentTimeMillis() - this.a > 0L && this.a != 0L) {
            final int f = RR.mc.gameSettings.keyBindForward.getKeyCode();
            KeyBinding.setKeyBindState(f, false);
            KeyBinding.onTick(f);
            this.n = System.currentTimeMillis() + 90L + this.random.nextInt(50);
            this.a = 0L;
        }
        else if (System.currentTimeMillis() - this.n > 0L && this.n != 0L) {
            final int g = RR.mc.gameSettings.keyBindForward.getKeyCode();
            KeyBinding.setKeyBindState(g, true);
            KeyBinding.onTick(g);
            this.n = 0L;
        }
    }
}
