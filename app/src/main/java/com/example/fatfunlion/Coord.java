package com.example.fatfunlion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Coord {
    public int bottom;
    public int left;
    public int right;
    public int top;
    public int h;
    public int w;
    public int start_w;
    public int centerX;
    public int centerY;
    public Boolean draw =false;

    public Coord(float bottom, float left, float right, float top) {
        this.bottom = (int) bottom;
        this.left = (int) left;
        this.right = (int) right;
        this.top = (int) top;
        h = (int) ( bottom-top);
        w = (int) (right-left);
        start_w=w;
        centerX = (int) (w / 2 + left);
        centerY = (int) (h / 2 + top);
    }
    public Coord(Coord coord)
    {this.bottom = coord.bottom;
        this.left =coord.left;
        this.right = coord. right;
        this.top = coord. top;
        h = (int) ( bottom-top);
        w = (int) (right-left);
        start_w=w;
        centerX = (int) (w / 2 + left);
        centerY = (int) (h / 2 + top);

    }
    public void update(float left,float top)
    {
        this.left=(int)left;
        this.top=(int)top;

        right=this.left+w;
        bottom=this.top+h;

        centerX=right-w/2;
        centerY=bottom-this.top;
    }//hitbox


    public void CoordDraw(Canvas canvas, Paint paint)//hitbox
    {
       int r=w/10;
       Paint p=new Paint();
       p.setColor(Color.GRAY);
       canvas.drawRect(left,top,right,bottom,p);
       canvas.drawRect(left+r,top+r,right-r,bottom-r,paint);
    }

}
