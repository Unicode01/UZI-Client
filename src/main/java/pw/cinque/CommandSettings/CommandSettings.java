package pw.cinque.CommandSettings;

import java.math.RoundingMode;
import java.math.BigDecimal;

public class CommandSettings extends GuiSettings
{
    public String n;
    public double v;
    public double a;
    public double m;
    public double i;
    
    public CommandSettings(final String n, final double v, final double m, final double a, final double i) {
        super(n);
        this.n = n;
        this.v = v;
        this.a = a;
        this.m = m;
        this.i = i;
    }
    
    @Override
    public String g() {
        return this.n;
    }
    
    public double g3tV4l4u3() {
        return r(this.v, 2);
    }
    
    public double g3ti() {
        return this.m;
    }
    
    public double g3ta() {
        return this.a;
    }
    
    public void v(double n) {
        n = c(n, this.m, this.a);
        n = Math.round(n * (1.0 / this.i)) / (1.0 / this.i);
        this.v = n;
    }
    
    public static double c(double v, final double i, final double a) {
        v = Math.max(i, v);
        v = Math.min(a, v);
        return v;
    }
    
    public static double r(final double v, final int p) {
        if (p < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(p, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
