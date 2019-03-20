package cat.flx.ballgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class Game {

    private static final int SHAKE_THRESHOLD = 700;
    private GameEngine gameEngine;

    private final int ballsPerTouch = 7;

    private Ball mainBall;
    private float lastAx;
    private float lastAy;
    private float lastAz;

    private long lastUpdate;

    private List<Ball> balls = new ArrayList<>();


    Game(GameEngine gameEngine) {
        this.gameEngine = gameEngine;

        mainBall = new BallBuilder(this)
                .setStartingPosition(getScreenWidth() / 2, getScreenHeight() / 2)
                .setColor(Color.WHITE)
                .setAccelerationFactor(1)
                .setSize(50)
                .build();

    }

    int getScreenWidth() {
        return gameEngine.getScreenWidth();
    }

    int getScreenHeight() {
        return gameEngine.getScreenHeight();
    }


    void onAccelerometerChanged(SensorEvent sensorEvent) {
        float ax = sensorEvent.values[0];
        float ay = sensorEvent.values[1];
        float az = sensorEvent.values[2];

        long curTime = System.currentTimeMillis();

        mainBall.setAcceleration(ax, ay);
//        for (Ball ball2 : balls) {
//            ball2.setAcceleration(ax,ay);
//        }

        if ((curTime - lastUpdate) > 800) {
            long deltaTime = (curTime - lastUpdate);
            lastUpdate = curTime;

//            Log.d("speed", "ax: " + ax + " ay: " + ay + " az: " + az);
            float scalarSpeed = Math.abs(ax + ay + az - lastAx - lastAy - lastAz) / deltaTime * 10000;
//            Log.d("speed", "speed: " + scalarSpeed);

            if (scalarSpeed > SHAKE_THRESHOLD) {
//                Log.d("speed", "shake detected w/ speed: " + scalarSpeed);
                Toast.makeText(gameEngine.getContext(), "shake detected w/ speed: " + scalarSpeed, Toast.LENGTH_SHORT).show();

                float vx = ax*deltaTime /100;
                float vy = ay*deltaTime /100;

                for (Ball ball : balls)
                {
                    ball.setVx(vx);
                    ball.setVy(vy);
                }
            }
            lastAx = ax;
            lastAy = ay;
            lastAz = az;
        }

    }

    void physics(final long deltaTime) {
        mainBall.physics(deltaTime);

        for (Iterator<Ball> iterator = balls.iterator(); iterator.hasNext(); ) {
            iterator.next().physics(deltaTime);
        }

    }

    void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        mainBall.draw(canvas);

        for (Iterator<Ball> iterator = balls.iterator(); iterator.hasNext(); ) {
            iterator.next().draw(canvas);
        }
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    public Ball getMainBall() {
        return mainBall;
    }

    public void mainBallTouched(int x, int y) {
        //doing an auxiliary list so I don't need to care about erasing data in the balls list
        Log.d("flx", "mainBallTouched: ");
        List<Ball> list = new ArrayList<>();
        for (int i = 0; i < ballsPerTouch; i++) {
            Ball ball = new BallBuilder(this).setRandomParams(gameEngine).setStartingPosition(x, y).build();
            list.add(ball);
        }
        balls = list;
    }

    public void genBalls(int x,int y, int quant){
        List<Ball> list = new ArrayList<>();
        for (int i = 0; i < quant; i++) {
            Ball ball = new BallBuilder(this).setRandomParams(gameEngine).setStartingPosition(x, y).build();
            list.add(ball);
        }
        balls = list;
    }
}
