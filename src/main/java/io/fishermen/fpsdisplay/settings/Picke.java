// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.awt.*;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import pw.cinque.CommandSettings.CommandSettings;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import pw.cinque.timechanger.ClickListener;

public class Picke extends GuiSettings
{
    public static CommandSettings x;
    public static CommandSettings y;
    public Picke() {
        super(GuiSettings.a(new char[] { 'H', 'u', 'd' }), "", c4.render, 0, -1);
        this.avav(x = new CommandSettings("X",2,0,mc.displayWidth,1));
        this.avav(y = new CommandSettings("Y",2,0,mc.displayHeight,1));
    }
    @SubscribeEvent
    public void a(final RenderGameOverlayEvent.Post e) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        final Minecraft mc = Picke.mc;
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
            return;
        }
        if (e.type != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        GL11.glPushMatrix();
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("UZI Client Ver 1.2.8 By Unicode", (float)x.g3tV4l4u3(), (float) y.g3tV4l4u3(), a(6000, -270));
        int yCount = (int) ((float) y.g3tV4l4u3() + 8);
        for (final GuiSettings mod : io.fishermen.fpsdisplay.settings.CommandSettings.m) {
            if (mod != null && mod != this && mod.g3t()) {
                final Minecraft mc2 = Picke.mc;
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(mod.g3t4(), (float)x.g3tV4l4u3(), (float)yCount, a(6000, -270));
                yCount += 8;
            }
        }
        GL11.glPopMatrix();
    }
    
    public static int a(final int s, final int o) {
        float h = (System.currentTimeMillis() + (long)o) % (long)s;
        return Color.getHSBColor(h /= (float)s, 1.0f, 1.0f).getRGB();
    }
}
