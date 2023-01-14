package io.fishermen.fpsdisplay;

import pw.cinque.keystrokes.Colors;
import io.fishermen.fpsdisplay.settings.CommandSettings;

public class FPSRenderer
{
    public static CommandSettings m;
    public static Colors c;
    
    public FPSRenderer() {
        FPSRenderer.m = new CommandSettings();
    }
    
    public CommandSettings m() {
        return FPSRenderer.m;
    }
}
