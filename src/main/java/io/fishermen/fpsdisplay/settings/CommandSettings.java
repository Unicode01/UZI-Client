package io.fishermen.fpsdisplay.settings;

import pw.cinque.keystrokes.render.Entity;
import io.fishermen.fpsdisplay.FPSDisplay;
import pw.cinque.keystrokes.render.Gui;
import java.util.ArrayList;
import java.util.List;

public class CommandSettings
{
    static List<GuiSettings> m;
    
    public CommandSettings() {
        CommandSettings.m = new ArrayList<GuiSettings>();
    }
    
    public void r() {
        this.r(new NewKillaura());
        this.r(new Gui());
        this.r(new FPSDisplay());
        this.r(new Entity());
        this.r(new Wool());
        this.r(new Boker());
        this.r(new Jok());
        this.r(new Dada());
        this.r(new Covek());
        this.r(new Picke());
        this.r(new Zlo());
        this.r(new RR());
        this.r(new OO());
        this.r(new Da());
        this.r(new nes());
        this.r(new das());
        this.r(new fl());
        this.r(new ne());
        this.r(new Bo());
        this.r(new Fp());
        this.r(new Tm());
        this.r(new Spd2());
        this.r(new LongJump());
        this.r(new Step());
        this.r(new LowHopSpeed());
        this.r(new SafeWalk());
        this.r(new Scaffold());
        this.r(new NoFall());
        this.r(new AntiBot());
        this.r(new Xray());
        this.r(new AirJump());
        this.r(new Scaffold2());
        this.r(new Derp());
        this.r(new ChestESP());
        this.r(new BedAura());
        this.r(new DelayRemover());
        this.r(new FreeCam());
        this.r(new Regen());
        this.r(new NoGround());
        this.r(new Disabler());
        this.r(new NoSlow());
        this.r(new InvCleaner());
        this.r(new BetterSword());
        this.r(new PlayerESP());
        this.r(new AutoJumpReset());
        this.r(new Blink());
        this.r(new AutoMLG());
        this.r(new AutoFish());
        this.r(new FastBreak());
        this.r(new FastUse());
        this.r(new Criticals());
        this.r(new Strafe());
        this.r(new Hitbox());
        this.r(new phase());
        this.r(new PacketSniffer());
        this.r(new NoHurtCam());
        this.r(new PingSpoof());
        this.r(new Aim2());
        this.r(new AutoGapple());
        this.r(new SaveConfigs());
        this.r(new LoadConfigs());
        this.r(new Teleport());
    }
    
    private void r(final GuiSettings mm) {
        CommandSettings.m.add(mm);
    }
    
    public List<GuiSettings> g() {
        return CommandSettings.m;
    }
    
    public List<GuiSettings> i(final GuiSettings.c4 c) {
        final List<GuiSettings> mm = new ArrayList<GuiSettings>();
        for (final GuiSettings m : this.g()) {
            if (m.cc().equals(c)) {
                mm.add(m);
            }
        }
        return mm;
    }
}
