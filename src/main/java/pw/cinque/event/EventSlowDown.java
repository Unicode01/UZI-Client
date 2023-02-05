package pw.cinque.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EventSlowDown extends Event {

    private float strafe;
    private float forward;

    public EventSlowDown(float f, float f1) {
        this.forward = f1;
        this.strafe = f;
    }

    public void setStrafe(float f) {
        this.strafe = f;
    }

    public void setForward(float f) {
        this.forward = f;
    }

    public float getStrafe() {
        return this.strafe;
    }

    public float getForward() {
        return this.forward;
    }
}
