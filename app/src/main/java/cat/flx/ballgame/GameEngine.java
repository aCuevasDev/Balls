package cat.flx.ballgame;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.View;

public class GameEngine extends View implements Runnable, SensorEventListener {
    private Context context;
    private Handler handler;
    private Game game;
    private SensorManager sensorManager;

    GameEngine(Context context) {
        super(context);
        this.context = context;
        handler = new Handler();
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    void setGame(Game game) { this.game = game; }

    int getScreenWidth() { return this.getMeasuredWidth(); }
    int getScreenHeight() { return this.getMeasuredHeight(); }

    void resume() {
        lastTime = System.currentTimeMillis();
        handler.postDelayed(this, 0);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    void pause() {
        handler.removeCallbacks(this);
        sensorManager.unregisterListener(this);
    }

    // GAME ENGINE RUNNABLE
    private final static int UPDATES_PER_SECOND = 30;  // desired physic's updates/second
    private final static int UPDATES_TO_REDRAW  = 1;   // how many physics per redraw update
    private final static int PERIOD = 1000 / UPDATES_PER_SECOND;
    private int count = 0;
    private long lastTime;

    @Override public void run() {
        // post new update
        handler.postDelayed(this, PERIOD);

        // do nothing unless everything is fully loaded
        if (game == null) return;

        // Time elapsed since last execution
        long currentTime = System.currentTimeMillis();
        long deltaTime = (currentTime - lastTime);
        lastTime = currentTime;

        // UPDATE PHYSICS EACH UPDATE
        game.physics(deltaTime);

        // REDRAW CANVAS EACH 1/UPDATES_TO_REDRAW TIMES THE PHYSICS UPDATE HAPPENS
        if (count == 0) this.invalidate();
        count++;
        if (count >= UPDATES_TO_REDRAW) count = 0;
    }

    @Override public void onDraw(Canvas canvas) {
        if (game == null) return;
        game.draw(canvas);
    }

    @Override public void onSensorChanged(SensorEvent sensorEvent) {
        if (game == null) return;
        game.onAccelerometerChanged(sensorEvent);
    }

    @Override public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
