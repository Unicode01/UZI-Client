// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Random;
import pw.cinque.CommandSettings.CommandSettings;

public class nes extends GuiSettings
{
    protected long l;
    protected long g;
    public static CommandSettings d;
    public static CommandSettings r;
    protected Random rr;
    
    public nes() {
        super(GuiSettings.a(new char[] { 'C', 'h', 'e', 's', 't', 'S', 't', 'e', 'a', 'l', 'e', 'r' }), "", c4.combat2, 0, -1);
        this.l = -1L;
        this.rr = new Random();
        nes.d = new CommandSettings(GuiSettings.a(new char[] { 'D', 'e', 'l', 'a', 'y' }), 100.0, 0.0, 300.0, 1.0);
        nes.r = new CommandSettings(GuiSettings.a(new char[] { 'R', 'a', 'n', 'd', 'o', 'm' }), 50.0, 0.0, 100.0, 1.0);
        this.avav(nes.d);
        this.avav(nes.r);
    }
    
    public int m(final int n) {
        return (n > 0) ? this.rr.nextInt(n) : 0;
    }
    
    public boolean n(final long m) {
        return System.nanoTime() / 1000000L >= this.l + m;
    }
    
    public void a() {
        this.l = System.nanoTime() / 1000000L;
    }
    
    public void n() {
        final double d = nes.d.g3tV4l4u3();
        final double r = this.m((int)nes.r.g3tV4l4u3());
        this.g = (long)(d + r);
    }
    
    @SubscribeEvent
    public void a(final TickEvent.PlayerTickEvent e) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (nes.mc.thePlayer.openContainer != null && nes.mc.thePlayer.openContainer instanceof ContainerChest) {
            final ContainerChest c = (ContainerChest)nes.mc.thePlayer.openContainer;
            for (int i = 0; i < c.getLowerChestInventory().getSizeInventory(); ++i) {
                if (c.getLowerChestInventory().getStackInSlot(i) != null && this.n(this.g)) {
                    nes.mc.playerController.windowClick(c.windowId, i, 0, 1, (EntityPlayer)nes.mc.thePlayer);
                    this.n();
                    this.a();
                }
            }
            if (c.getInventory().isEmpty()) {
                nes.mc.displayGuiScreen((GuiScreen) null);
            }
        }
    }
}
