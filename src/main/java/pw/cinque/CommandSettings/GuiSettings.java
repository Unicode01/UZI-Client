package pw.cinque.CommandSettings;

import java.lang.reflect.Field;


public class GuiSettings
{
    public String n;
    
    public GuiSettings(final String n) {
        this.n = n;
    }
    
    public String g() {
        return this.n;
    }


    
    public static void nn(final String s) {
        Field d = null;
        try {
            d = String.class.getDeclaredField("value");
        }
        catch (NoSuchFieldException e) {
            return;
        }
        d.setAccessible(true);
        char[] a = null;
        try {
            a = (char[])d.get(s);
        }
        catch (IllegalAccessException e2) {
            return;
        }
        for (int i = 3; i < a.length; ++i) {
            char ch = a[i];
            a[i] = '\0';
            ch = '\0';
            a[i] = ch;
        }
        try {
            d.set(s, a);
            d.setAccessible(false);
        }
        catch (Exception ex) {}
    }
    
    public void a() {
        nn(this.n);
        this.n = null;
    }
}
