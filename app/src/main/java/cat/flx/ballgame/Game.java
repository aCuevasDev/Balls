package cat.flx.ballgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.SensorEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game {

    private GameEngine gameEngine;

    private Ball ball;
    Random rand = new Random();

    List<Ball> balls = new ArrayList<>();


    Game(GameEngine gameEngine) {
        this.gameEngine = gameEngine;

        ball = ballBuilder(Color.WHITE,50);

    }

    int getScreenWidth() { return gameEngine.getScreenWidth(); }
    int getScreenHeight() { return gameEngine.getScreenHeight(); }


    void onAccelerometerChanged(SensorEvent sensorEvent) {
        float ax = sensorEvent.values[0];
        float ay = sensorEvent.values[1];
        // float az = sensorEvent.values[2];
        ball.setAcceleration(ax, ay);

        for (Ball ball2 : balls) {
            ball2.setAcceleration(ax,ay);
        }
    }

    void physics(long deltaTime) {
        ball.physics(deltaTime);

        for (Ball ball2 : balls) {
            ball2.physics(deltaTime);
        }
    }

    void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        ball.draw(canvas);

        for (Ball ball2 : balls) {
            ball2.draw(canvas);
        }
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    public Ball ballBuilder(Integer color, int size){
        Ball ball = new Ball(this);

        float randomH = (float) (0 + Math.random() * (getScreenHeight() - 0));
        float randomW = (float) (0 + Math.random() * (getScreenWidth() - 0));

        ball.setPosition(randomW,randomH);
        ball.setFactor((int) (-5 + Math.random() * (5 - (-5))));
        ball.setColor(color);
        ball.setSize(size);
        return ball;
    }


    public Ball ballBuilder(){

            int r = rand.nextInt();
            int g = rand.nextInt();
            int b = rand.nextInt();
        int size = rand.nextInt(75);


        return ballBuilder(Color.rgb(r,g,b),size);
    }

    public void generateBalls(int quantity){
        for (int i = 0; i < quantity; i++){
            Ball ball = ballBuilder();
            ball.setErasing(true);
            balls.add(ball);
        }
    }
}
