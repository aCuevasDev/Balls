package cat.flx.ballgame;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallBuilder {
    private Ball ball;
    private Random random = new Random();

    public BallBuilder(Game game) {
        ball = new Ball(game);
    }

    public BallBuilder setRandomParams(View view) {
//        float randomHf = (float) (0 + Math.random() * (view.getMeasuredHeight() - 0));
//        float randomWf = (float) (0 + Math.random() * (view.getMeasuredWidth() - 0));

        //Position
        int randomH = random.nextInt(view.getMeasuredHeight());
        int randomW = random.nextInt(view.getMeasuredWidth());
        //Acceleration factor
        int factor = (int) (Math.random() * ((5 - (-5)) + 1)) - 5;
        //Size
        int size = random.nextInt(90);
        //Color
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        //Starting speed
        int vx = random.nextInt(250);
        int vy = random.nextInt(250);

        int sx = (int) (Math.random() * ((250 - (-250)) + 1)) - 250;
        int sy = (int) (Math.random() * ((250 - (-250)) + 1)) - 250;

//        this.setStartingPosition(randomW, randomH).setAccelerationFactor(factor).setSize(size).setColor(Color.rgb(r, g, b)).setStatingSpeed(vx,vy);
        this.setStartingPosition(randomW, randomH).setSize(size).setColor(Color.rgb(r, g, b)).setStatingSpeed(sx, sy);
        return this;
    }

    public Ball build() {
        return ball;
    }

    //TODO rn this method creates X balls EQUAL this means same position and speed so you end up just seeing one. solve it
    @Deprecated
    public List<Ball> build(int num) {
        ArrayList<Ball> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(ball);
        }
        return list;
    }

    public BallBuilder setStartingPosition(int x, int y) {
        ball.setPosition(x, y);
        return this;
    }

    public BallBuilder setAccelerationFactor(int factor) {
        ball.setFactor(factor);
        return this;
    }

    public BallBuilder setColor(int color) {
        ball.setColor(color);
        return this;
    }

    public BallBuilder setSize(int size) {
        ball.setSize(size);
        return this;
    }

    public BallBuilder setAccelerationConst(float aX, float aY) {
        ball.setConstAX(aX);
        ball.setConstAX(aY);
        return this;
    }

    public BallBuilder setStatingSpeed(int vx, int vy) {
        ball.setVx(vx);
        ball.setVy(vy);
        return this;
    }

//    public void generateBalls(int quantity) {
//        for (int i = 0; i < quantity; i++) {
//            Ball ball = ballBuilder();
//            ball.setErasing(true);
//            balls.add(ball);
//        }
//    }
}
