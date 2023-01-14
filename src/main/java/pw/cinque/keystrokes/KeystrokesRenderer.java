//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Documents\Minecraft-Deobfuscator3000-master\1.8.9 mappings"!

// Decompiled by ImCzf233

package pw.cinque.keystrokes;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Iterator;
import pw.cinque.timechanger.ClickCounterRenderer;
import pw.cinque.keystrokes.render.Key;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.CommandSettings.CommandSettings;
import java.util.ArrayList;
import pw.cinque.timechanger.CPSMod;
import io.fishermen.fpsdisplay.settings.GuiSettings;
import pw.cinque.keystrokes.render.Mode;

public class KeystrokesRenderer extends Mode
{
    public GuiSettings m;
    public CPSMod p;
    public int o;
    private boolean ih;
    private ArrayList<Mode> sn;
    public boolean po;
    private int h;
    
    public KeystrokesRenderer(final GuiSettings m, final CPSMod p, final int o) {
        this.m = m;
        this.p = p;
        this.o = o;
        this.sn = new ArrayList<Mode>();
        this.po = false;
        this.h = 12;
        int y = o + 12;
        if (!m.v().isEmpty()) {
            for (final pw.cinque.CommandSettings.GuiSettings v : m.v()) {
                if (v instanceof CommandSettings) {
                    final CommandSettings n = (CommandSettings)v;
                    final KeystrokesGui s = new KeystrokesGui(n, this, y);
                    this.sn.add(s);
                    y += 12;
                }
                if (v instanceof ClickListener) {
                    final ClickListener b = (ClickListener)v;
                    final Key c = new Key(b, this, y);
                    this.sn.add(c);
                    y += 12;
                }
            }
        }
        this.sn.add(new ClickCounterRenderer(this, y));
    }
    
    @Override
    public void so(final int n) {
        this.o = n;
        int y = this.o + 16;
        for (final Mode comp : this.sn) {
            comp.so(y);
            if (comp instanceof KeystrokesGui) {
                y += 16;
            }
            if (comp instanceof Key) {
                y += 12;
            }
            if (comp instanceof ClickCounterRenderer) {
                y += 12;
            }
        }
    }
    
    public static void e() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void f() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void g(final int h) {
        final float a = (h >> 24 & 0xFF) / 255.0f;
        final float r = (h >> 16 & 0xFF) / 255.0f;
        final float g = (h >> 8 & 0xFF) / 255.0f;
        final float b = (h & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }
    
    public static void v(final float x, final float y, final float x1, final float y1, final int t, final int b) {
        e();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        g(t);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        g(b);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        f();
    }
    
    @Override
    public void r() {
        if(pw.cinque.keystrokes.render.Gui.GuiRainbow.i()) {
            KeystrokesRenderer.v(this.p.gx(), this.p.gy() + this.o, this.p.gx() + this.p.gw(), this.p.gy() + 15 + this.o, this.m.g3t() ? new pw.cinque.keystrokes.render.Gui().a(6000, -270) : -12829381, this.m.g3t() ? new pw.cinque.keystrokes.render.Gui().a(6000, -270) : -12302777);
        } else {
            KeystrokesRenderer.v(this.p.gx(), this.p.gy() + this.o, this.p.gx() + this.p.gw(), this.p.gy() + 15 + this.o, this.m.g3t() ? new Color(5,130,255).getRGB() : -12829381, this.m.g3t() ? new Color(5,130,255).getRGB() : -12302777);
        }
        GL11.glPushMatrix();
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.m.g3t4(), this.p.gx() + this.p.gw() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.m.g3t4()) / 2, this.p.gy() + this.o + 4, this.m.g3t() ? -3355444 : -5592406);
        GL11.glPopMatrix();
        if (this.po && !this.sn.isEmpty()) {
            for (Mode c : this.sn) {
                c.r();
            }
        }
    }
    
    @Override
    public int gh() {
        if (this.po) {
            int h = 16;
            for (final Mode c : this.sn) {
                if (c instanceof KeystrokesGui) {
                    h += 16;
                }
                if (c instanceof Key) {
                    h += 12;
                }
                if (c instanceof ClickCounterRenderer) {
                    h += 12;
                }
            }
            return h;
        }
        return 16;
    }
    
    @Override
    public void uu(final int x, final int y) {
        this.ih = this.ii(x, y);
        if (!this.sn.isEmpty()) {
            for (final Mode c : this.sn) {
                c.uu(x, y);
            }
        }
    }
    
    @Override
    public void cl(final int x, final int y, final int b) {
        if (this.ii(x, y) && b == 0) {
            this.m.t();
        }
        if (this.ii(x, y) && b == 1) {
            this.po = !this.po;
            this.p.r();
        }
        for (final Mode c : this.sn) {
            c.cl(x, y, b);
        }
    }
    
    @Override
    public void mr(final int x, final int y, final int m) {
        for (final Mode c : this.sn) {
            c.mr(x, y, m);
        }
    }
    
    @Override
    public void ky(final char t, final int k) {
        for (final Mode c : this.sn) {
            c.ky(t, k);
        }
    }
    
    public boolean ii(final int x, final int y) {
        return x > this.p.gx() && x < this.p.gx() + this.p.gw() && y > this.p.gy() + this.o && y < this.p.gy() + 16 + this.o;
    }
}
