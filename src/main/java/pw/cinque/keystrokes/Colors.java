// Decompiled by ImCzf233

package pw.cinque.keystrokes;

import java.awt.*;
import java.util.Iterator;
import pw.cinque.keystrokes.render.Mode;
import io.fishermen.fpsdisplay.settings.GuiSettings;
import pw.cinque.timechanger.CPSMod;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class Colors extends GuiScreen
{
    public static ArrayList<CPSMod> f;
    public static int c;
    
    public Colors() {
        Colors.f = new ArrayList<CPSMod>();
        int y = 5;
        GuiSettings.c4[] values;
        for (int length = (values = GuiSettings.c4.values()).length, i = 0; i < length; ++i) {
            final GuiSettings.c4 c = values[i];
            final CPSMod f = new CPSMod(c);
            f.y(y);
            Colors.f.add(f);
            y += 20;
        }
    }
    
    public void initGui() {
    }
    
    public void drawScreen(final int x, final int y, final float p) {
        this.drawDefaultBackground();
        for (final CPSMod f : Colors.f) {
            f.rf(this.fontRendererObj);
            f.up(x, y);
            for (final Mode c : f.gc()) {
                c.uu(x, y);
            }
        }
    }
    
    protected void mouseClicked(final int x, final int y, final int m) {
        for (final CPSMod f : Colors.f) {
            if (f.v(x, y) && !f.i(x, y) && !f.d(x, y) && m == 0) {
                f.d(true);
                f.xx = x - f.gx();
                f.yy = y - f.gy();
            }
            if (f.d(x, y) && m == 0) {
                f.oo(!f.fv());
            }
            if (f.i(x, y) && m == 0) {
                f.cv(!f.p());
            }
            if (f.fv() && !f.gc().isEmpty()) {
                for (final Mode c : f.gc()) {
                    c.cl(x, y, m);
                }
            }
        }
    }
    
    protected void keyTyped(final char t, final int k) {
        for (final CPSMod f : Colors.f) {
            if (f.fv() && k != 1 && !f.gc().isEmpty()) {
                for (final Mode c : f.gc()) {
                    c.ky(t, k);
                }
            }
        }
        if (k == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    protected void mouseReleased(final int x, final int y, final int s) {
        if (s == 0) {
            for (final CPSMod f : Colors.f) {
                f.d(false);
            }
            for (final CPSMod f : Colors.f) {
                if (f.fv() && !f.gc().isEmpty()) {
                    for (final Mode c : f.gc()) {
                        c.mr(x, y, s);
                    }
                }
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        Colors.c = -10110371; //-10110371
    }
}
