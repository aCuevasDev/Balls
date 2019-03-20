package cat.flx.ballgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Ball {
    private Game game;

    private Paint paint;

    private float x, y;
    private float vx, vy;
    private float ax, ay;
    private int size = 50;
    private int color = Color.WHITE;

    private int factor = 1;
    private int elapsedTime = 0;
    private boolean erasing = false;

    private float constAX, constAY;

    Ball(Game game) {
        this.game = game;
    }

    void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    void setAcceleration(float ax, float ay) {
        this.ax = -ax * factor;
        this.ay = ay * factor;
    }

    void physics(long deltaTime) {
        vx += ax * deltaTime / 1000 * 10;
        vy += ay * deltaTime / 1000 * 10;
        vx *= 0.95;
        vy *= 0.95;
        x += vx * deltaTime / 1000 * 10;
        y += vy * deltaTime / 1000 * 10;
        if (x < size) {
            vx = -vx;
            x += size - x;
        }
        if (y < size) {
            vy = -vy;
            y += size - y;
        }
        if (x > game.getScreenWidth() - size) {
            vx = -vx;
            x = 2 * (game.getScreenWidth() - size) - x;
        }
        if (y > game.getScreenHeight() - size) {
            vy = -vy;
            y = 2 * (game.getScreenHeight() - size) - y;
        }

        selfDestruct(deltaTime);
    }

    void draw(Canvas canvas) {
        if (paint == null) {
            paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setTextSize(20);
        }
        canvas.drawCircle(this.x, this.y, size, paint);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFactor(int factor) {
        if (factor != 0)
            this.factor = factor;
    }

    public void setErasing(boolean erasing) {
        this.erasing = erasing;
    }

    public void selfDestruct(long deltaTime) {
        if (erasing)
            elapsedTime += deltaTime;
        if (elapsedTime >= 30)
            game.getBalls().remove(this);
    }

    public float getConstAX() {
        return constAX;
    }

    public void setConstAX(float constAX) {
        this.constAX = constAX;
    }

    public float getConstAY() {
        return constAY;
    }

    public void setConstAY(float constAY) {
        this.constAY = constAY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }
}