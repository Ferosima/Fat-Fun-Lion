package com.example.fatfunlion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameView extends View {
    Paint paint;
    GameView gameView;
    Boolean first = true;
    Boolean timer_game_run = false;
    Boolean timer_sleep_lion_run = false;
    Boolean timer_end_run = false;
    Boolean is_start_run = true;
    Boolean is_game_run = false;
    Boolean is_sleep_run = false;
    Boolean is_menu_run = false;
    Boolean click = false;
    //  Boolean timer_game_text = true;
    int w, h;
    int time = 120000;
    int time_timer = time;
    int number_of_antelope = 0, need_antelope = 35;
    int number_of_draw_antelope;
    int number;
    public CountDownTimer timer_start, timer_game, timer_sleep_lion, timer_end;
    Coord antelopes[];
    Coord lions[];
    Coord antilope;
    Coord counters;
    Coord coord_timer;
    Bitmap bitmaps_antelopes[];
    Bitmap bitmap_lions[];
    Bitmap timer;
    TextView textView, textView_start;
    int day = 1;
    Random random;
    Listener mListener;

    public interface Listener { // create an interface
        void goToMain();
    }

    public GameView(Context context, Listener mListener) {
        super(context);
        // this.mListener=mListener;
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
        gameView = this;
        this.mListener = mListener;

        antelopes = new Coord[3];
        lions = new Coord[4];

        bitmaps_antelopes = new Bitmap[3];
        bitmap_lions = new Bitmap[4];

        random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (first) {
            w = canvas.getWidth();
            h = canvas.getHeight();

            //bitmaps initialization (lions,antelopes,timer_panel,button)
            bitmaps_antelopes[0] = BitmapFactory.decodeResource(getResources(), R.drawable.gazelle_0000_1_optimized);
            bitmaps_antelopes[0] = Bitmap.createScaledBitmap(bitmaps_antelopes[0], (int) (w / 5), (int) (h / 3), true);

            bitmaps_antelopes[1] = BitmapFactory.decodeResource(getResources(), R.drawable.gazelle_0001_2_optimized);
            bitmaps_antelopes[1] = Bitmap.createScaledBitmap(bitmaps_antelopes[1], (int) (w / 5), (int) (h / 3), true);

            bitmaps_antelopes[2] = BitmapFactory.decodeResource(getResources(), R.drawable.gazelle_0002_3_optimized);
            bitmaps_antelopes[2] = Bitmap.createScaledBitmap(bitmaps_antelopes[2], (int) (w / 5), (int) (h / 3), true);

            bitmap_lions[0] = BitmapFactory.decodeResource(getResources(), R.drawable._0000_lion_thin_optimized);
            bitmap_lions[0] = Bitmap.createScaledBitmap(bitmap_lions[0], (int) (w / 3), (int) (h / 3) * 2, false);

            bitmap_lions[1] = BitmapFactory.decodeResource(getResources(), R.drawable._0001_lion_fat_optimized);
            bitmap_lions[1] = Bitmap.createScaledBitmap(bitmap_lions[1], (int) (w / 3), (int) (h / 3) * 2, false);

            bitmap_lions[2] = BitmapFactory.decodeResource(getResources(), R.drawable._0002_lion_normal_optimized);
            bitmap_lions[2] = Bitmap.createScaledBitmap(bitmap_lions[2], (int) (w / 2), (int) (h / 3) * 2, false);

            bitmap_lions[3] = BitmapFactory.decodeResource(getResources(), R.drawable._0003_lion_bam_optimized);
            bitmap_lions[3] = Bitmap.createScaledBitmap(bitmap_lions[3], (int) (w / 2), (int) (h / 3) * 2, false);

            //   timer=BitmapFactory.decodeResource(getResources(), R.drawable.timer_optimized);
            //    timer = Bitmap.createScaledBitmap(timer, w / 6, h / 6, false);

            //coords_antelopes initialization
            antelopes[0] = new Coord(h / 3, 0, w / 5, 0);
            antelopes[1] = new Coord(h / 3, 0, w / 5, 0);
            antelopes[2] = new Coord(h / 3, 0, w / 5, 0);

            //coords_lion initialization
            lions[0] = new Coord(h, (w / 6) * 2 + (w / 12), w / 3 + (w / 12), (h / 3));
            lions[1] = new Coord(h, (w / 6) * 2, w / 3, (h / 3));
            lions[2] = new Coord(h, (w / 6), w / 6 + (w / 2), (h / 3));
            lions[3] = new Coord(h, (w / 6), w / 6 + (w / 2), (h / 3));

            //coords_button initialization
            counters = new Coord(h / 4, 0, w / 12, 0);
            coord_timer = new Coord(h / 6, w - w / 6, w, 0);

            antilope = new Coord(h / 6, 0, w / 6, 0);

            textView = (TextView) getRootView().findViewById(R.id.timer_textView);
            textView_start = (TextView) getRootView().findViewById(R.id.text_start);

            timer_start = new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("millis_start", millisUntilFinished + "");

                    Log.d("millis_start_int", time_timer + "");
                }

                public void onFinish() {
                    timer_game_run = true;
                    is_game_run = true;
                    invalidate();
                }
            }.start();
            first = false;
        }

        if (timer_game_run) {
            timer_game_run = false;
            textView_start.setVisibility(INVISIBLE);
            textView.setVisibility(VISIBLE);
            timer_game = new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    time_timer = (int) millisUntilFinished;
                    int left = random.nextInt(w);
                    Log.d("millis", millisUntilFinished + "");
                    number_of_draw_antelope = random.nextInt(3) + 1;
                    update();

                }

                public void onFinish() {
                    //check time==30 text win
//                    timer_end_run = true;
//                    is_game_run = false;
                    if(number_of_antelope<need_antelope)
                    {
                        Log.d("time_out","Done");
                        is_menu_run=true;
                        timer_end_run=true;
                        is_game_run=false;
                        textView.setVisibility(INVISIBLE);
                        textView_start.setVisibility(VISIBLE);
                    }
                    invalidate();
                }
            }.start();
        }

        if (timer_sleep_lion_run) {
            timer_sleep_lion_run = false;
            // textView.setVisibility(VISIBLE);
            timer_sleep_lion = new CountDownTimer(1000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    time -= 10000;
                    if (time==30000)
                    {
                        mListener.goToMain();
                        Log.d("done","done");
                        invalidate();
                    }
                    else {
                        time_timer = time;
                        number_of_antelope = 0;
                        //timer_game.start();
                        textView.setVisibility(INVISIBLE);
                        timer_start.start();
                        is_sleep_run = false;
                        textView_start.setVisibility(VISIBLE);
                        day++;
                        invalidate();
                    }
                }
            }.start();

        }

        if (timer_end_run) {
            timer_end_run = false;
            timer_end = new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("millis_end", millisUntilFinished + "");
                }

                public void onFinish() {
                    mListener.goToMain();
                    invalidate();
                    //вызов интерфейса или же отрисовка меню с повтором и выходом
                }
            }.start();
        }

        if (is_start_run) {
            if (day < 2)
                textView_start.setText("Feed the lion so that it falls asleep. Press the fish that appear on the screen..");
            else {
                if (time == 30000)
                    textView_start.setText("You win");
                else
                    textView_start.setText("Day:" + day);
            }
        }

        if (is_game_run) {

            //canvas.drawRect(counters.left,counters.top,counters.right,counters.bottom,paint);

//            canvas.drawRect(coord_timer.left,coord_timer.top,coord_timer.right,coord_timer.bottom,paint);
//            canvas.drawBitmap(timer,coord_timer.left,coord_timer.top,paint);
//            canvas.drawText(time_timer/1000/60+" : "+time_timer/1000%60, coord_timer.left+coord_timer.w/6, coord_timer.centerY+coord_timer.h/5,paint);
            if (time_timer / 1000 % 60 < 10)
                textView.setText(time_timer / 1000 / 60 + " : 0" + time_timer / 1000 % 60);
            else
                textView.setText(time_timer / 1000 / 60 + " : " + time_timer / 1000 % 60);

            canvas.drawText(Integer.toString(need_antelope), w / 86, h / 12, paint);
            canvas.drawText(Integer.toString(number_of_antelope), w / 86, (h / 12) * 2, paint);
            //draw lion
            if (number_of_antelope < need_antelope / 3 && number_of_antelope >= 0)
                canvas.drawBitmap(bitmap_lions[0], lions[0].left, lions[0].top, paint);
            if (number_of_antelope < need_antelope - (need_antelope / 3) && number_of_antelope >= need_antelope / 3)
                canvas.drawBitmap(bitmap_lions[1], lions[1].left, lions[1].top, paint);
            if (number_of_antelope < need_antelope && number_of_antelope >= need_antelope - (need_antelope / 3))
                canvas.drawBitmap(bitmap_lions[2], lions[2].left, lions[2].top, paint);

//draw antelope
            for (int i = 0; i < number_of_draw_antelope; i++) {
                if (antelopes[i].draw == true) {
                    //  canvas.drawRect(antelopes[i].left, antelopes[i].top, antelopes[i].right, antelopes[i].bottom, paint);
                    canvas.drawBitmap(bitmaps_antelopes[i], antelopes[i].left, antelopes[i].top, paint);
                   // Log.d("log" + i, antelopes[i].right + " " + antelopes[i].bottom + " " + w + " " + h);
                }
            }

        }

        if (is_sleep_run) {
            canvas.drawBitmap(bitmap_lions[3], lions[3].left, lions[3].top, paint);
        }

        if (is_menu_run) {
            //draw button retry and exit(need interface)
            //
            textView_start.setText("Time's up. Try again.");
        }
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // координаты Touch-события
        float evX = event.getX();
        float evY = event.getY();

        switch (event.getAction()) {
            // касание началось
            case MotionEvent.ACTION_DOWN:
                if (is_game_run) {
                    //check coord_antelopes
                    for (int i = 0; i < number_of_draw_antelope; i++) {
                        if (evX >= antelopes[i].left && evX <= antelopes[i].right &&
                                evY >= antelopes[i].top && evY <= antelopes[i].bottom) {
                            click = true;
                            number = i;
                            break;
                        }
                    }
                }
                if (is_menu_run) {//check coord_button
                }
            case MotionEvent.ACTION_UP:
                if (is_game_run) {
                    if (click) {
                        antelopes[number].draw = false;
                        number_of_antelope++;
                        click = false;
                        if (number_of_antelope == need_antelope) {
                            timer_game.cancel();
                            timer_game.onFinish();
                            timer_sleep_lion_run = true;
                            is_sleep_run = true;
                            is_game_run = false;

                        }
                        break;
                    }
                    invalidate();
                    //check coord_antelopes
                }
                if (is_menu_run) {//check coord_button
                }
        }

        return true;
    }

    public void update() {
        for (int i = 0; i < 3; i++)
            antelopes[i].draw = false;
        for (int i = 0; i < number_of_draw_antelope; i++) {
            int left;
            int top;
            do {
                left = random.nextInt(w);
                top = random.nextInt(h);
            } while (collision(left, top, antelopes[i].w, antelopes[i].h));
            antelopes[i].update(left, top);
            antelopes[i].draw = true;
        }
    }

    public Boolean collision(int left, int top, int w, int h) {
        if ((left + w) < this.w && (top + h) < this.h)
            // if (left + w < coord_timer.left && top > coord_timer.bottom)
            return false;
        return true;
    }
}

