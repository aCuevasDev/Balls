package cat.flx.ballgame;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    GameEngine gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameEngine = new GameEngine(this);
        ViewTreeObserver vto = gameEngine.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);
        setContentView(gameEngine);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override public void onGlobalLayout() {
        ViewTreeObserver vto = gameEngine.getViewTreeObserver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            vto.removeOnGlobalLayoutListener(this);
        }
        else {
            vto.removeGlobalOnLayoutListener(this);
        }
        int width  = gameEngine.getMeasuredWidth();
        int height = gameEngine.getMeasuredHeight();
        Log.i("flx", "WIDTH=" + width + " HEIGHT" + height);
        Game game = new Game(gameEngine);
        gameEngine.setGame(game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameEngine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameEngine.pause();
    }
}
