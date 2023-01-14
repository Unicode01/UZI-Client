package pw.cinque.keystrokes;

public final class KeystrokesCommands
{
    public final double mx;
    public final double my;
    public final double mz;
    public final double nx;
    public final double ny;
    public final double nz;
    
    public KeystrokesCommands(final double x, final double y, final double z, final double x1, final double y1, final double z1) {
        this.mx = x;
        this.my = y;
        this.mz = z;
        this.nx = x1;
        this.ny = y1;
        this.nz = z1;
    }
}
