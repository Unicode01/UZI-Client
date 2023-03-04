package pw.cinque.timechanger;

import io.fishermen.fpsdisplay.settings.Bo;
import pw.cinque.CommandSettings.GuiSettings;

public class ClickListener extends GuiSettings
{
    public String n;
    public boolean v;
    
    public ClickListener(final String n, final boolean v) {
        super(n);
        this.n = n;
        this.v = v;
    }
    
    @Override
    public String g() {
        return this.n;
    }
    
    public boolean i() {
        return this.v;
    }
    
    public void t() {
        this.v = !this.v;
    }
    @Override
    public Object getValue(){
        return this.v;
    }
    @Override
    public void setValue(Object Value){
        this.v = (Boolean)Value;
    }
}
