package app.entities.gruppeE;

public class Timer {
    private long secondsDisplay;
    private long elapsedSeconds;
    private long elapsedMinutes;

    public void showTime(long startTime){
        long elapsedTime = System.currentTimeMillis() - startTime;
        secondsDisplay = elapsedSeconds % 60;
        elapsedMinutes = elapsedSeconds / 60;
        System.out.println("Du har brugt så mange minutter: "+elapsedMinutes+" og så mange sekunder: "+secondsDisplay);

    }

    public long getSeconds(){
        return elapsedMinutes+elapsedSeconds;
    }

}
