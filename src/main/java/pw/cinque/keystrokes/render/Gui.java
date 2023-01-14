// Decompiled by ImCzf233

package pw.cinque.keystrokes.render;

import net.minecraft.client.gui.GuiScreen;
import io.fishermen.fpsdisplay.FPSRenderer;
import io.fishermen.fpsdisplay.settings.GuiSettings;
import pw.cinque.timechanger.ClickListener;

import java.awt.*;

public class Gui extends GuiSettings
{
    public static ClickListener GuiRainbow;
    public Gui() {
        super(GuiSettings.a(new char[] { 'G', 'u', 'i' }), "", c4.render, 41, 0);
        GuiRainbow = new ClickListener("Rainbow",false);
        this.avav(GuiRainbow);
    }
    
    @Override
    public void en() {
        if (!Entity.a) {
            Gui.mc.displayGuiScreen((GuiScreen)FPSRenderer.c);
            this.t();
        }
    }

    public static int a(final int s, final int o) {
        float h = (System.currentTimeMillis() + (long)o) % (long)s;
        return Color.getHSBColor(h /= (float)s, 1.0f, 1.0f).getRGB();
    }
}
