// Decompiled by ImCzf233

package pw.cinque.timechanger;

import org.lwjgl.input.Keyboard;
import io.fishermen.fpsdisplay.FPSDisplay;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import pw.cinque.keystrokes.KeystrokesRenderer;
import pw.cinque.keystrokes.render.Mode;

public class ClickCounterRenderer extends Mode
{
    private boolean h;
    private boolean bh;
    private KeystrokesRenderer p;
    private int o;
    private int x;
    private int y;
    
    public ClickCounterRenderer(final KeystrokesRenderer b, final int o) {
        this.p = b;
        this.x = b.p.gx() + b.p.gw();
        this.y = b.p.gy() + b.o;
        this.o = o;
    }
    
    @Override
    public void so(final int n) {
        this.o = n;
    }
    
    @Override
    public void r() {
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.bh ? FPSDisplay.a : (String.valueOf(String.valueOf(FPSDisplay.ab)) + Keyboard.getKeyName(this.p.m.g())), (float)((this.p.p.gx() + 4) * 2), (float)((this.p.p.gy() + this.o + 3) * 2), -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void uu(final int x, final int y) {
        this.h = this.i(x, y);
        this.y = this.p.p.gy() + this.o;
        this.x = this.p.p.gx();
    }
    
    @Override
    public void cl(final int x, final int y, final int b) {
        if (this.i(x, y) && b == 0 && this.p.po) {
            this.bh = !this.bh;
        }
    }
    
    @Override
    public void ky(final char t, final int k) {
        if (this.bh) {
            this.p.m.sb(k);
            this.bh = false;
        }
    }
    
    public boolean i(final int x, final int y) {
        return x > this.x && x < this.x + this.p.p.gw() && y > this.y - 1 && y < this.y + 12;
    }
    
    @Override
    public int gh() {
        return 16;
    }
}
