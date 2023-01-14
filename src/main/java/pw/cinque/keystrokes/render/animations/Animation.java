package pw.cinque.keystrokes.render.animations;



public abstract class Animation {

    public Timer timer = new Timer();
    protected int duration;
    protected double endPoint;
    protected Enum<Direction> direction;

    public Animation(int ms, double endPoint) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = Direction.FORWARDS;
    }

    public Animation(int ms, double endPoint, Enum<Direction> direction) {
        this.duration = ms; //Time in milliseconds of how long you want the animation to take.
        this.endPoint = endPoint; //The desired distance for the animated object to go.
        this.direction = direction; //Direction in which the graph is going. If backwards, will start from endPoint and go to 0.
    }

    public double getTimerOutput() {
        return timer.getTime() / (double) duration;
    }

    public double getEndPoint() {
        return endPoint;
    }

    public void reset() {
        timer.reset();
    }

    public boolean isDone() {
        return timer.hasTimeElapsed(duration, false);
    }

    public void changeDirection() {
        if (direction == Direction.FORWARDS) {
            direction = Direction.BACKWARDS;
        } else {
            direction = Direction.FORWARDS;
        }
        timer.setTime(System.currentTimeMillis() - (duration - Math.min(duration, timer.getTime())));
    }

    public Enum<Direction> getDirection() {
        return direction;
    }

    public void setDirection(Enum<Direction> direction) {
        if (this.direction != direction) {
            timer.setTime(System.currentTimeMillis() - (duration - Math.min(duration, timer.getTime())));
            this.direction = direction;
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getOutput() {
        if (direction == Direction.FORWARDS) {
            if (isDone())
                return endPoint;
            return getEquation(timer.getTime()) * endPoint;
        } else {
            if (isDone())
                return 0;
            return (1 - getEquation(timer.getTime())) * endPoint;
        }
    }

    //This is where the animation equation should go, for example, a logistic function. Output should range from 0 - 1.
    //This will take the timer's time as an input, x.
    protected abstract double getEquation(double x);

}

