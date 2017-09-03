package com.mio.jrdv.autofakecall.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mio.jrdv.autofakecall.R;


public class Android44ReceiveAnim extends View implements Runnable {
    private Context f8217a;
    private Paint f8218b;
    private Bitmap[] f8219c;
    private boolean f8220d;
    private int f8221e;
    private int f8222f;
    private float f8223g;
    private float f8224h;
    private float f8225i;
    private float f8226j;
    private float f8227k;
    private boolean f8228l;
    private int f8229m;
    private Bitmap f8230n;
    private Bitmap f8231o;
    private Bitmap f8232p;
    private Bitmap f8233q;
    private Bitmap f8234r;
    private Bitmap f8235s;
    private Bitmap f8236t;
    private C2302a f8237u;

    public interface C2302a {
        void mo1884a(View view, int i);
    }

    public Android44ReceiveAnim(Context context) {
        super(context);
        this.f8219c = new Bitmap[10];
        this.f8220d = false;
        this.f8221e = 0;
        this.f8228l = false;
        this.f8229m = 0;
    }

    public Android44ReceiveAnim(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f8219c = new Bitmap[10];
        this.f8220d = false;
        this.f8221e = 0;
        this.f8228l = false;
        this.f8229m = 0;
        this.f8217a = context;
        this.f8218b = new Paint();
        int a = (int) (((float) C2277h.m12974a(context)) * 0.69f);
        switch (C2279c.m12992g(this.f8217a)) {
            case 16:
            case 22:
                a = (int) (((float) C2277h.m12974a(context)) * 0.69f);
                if (C2277h.m12974a(context) <= 480) {
                    a = (int) (((float) C2277h.m12974a(context)) * 0.65f);
                    break;
                }
                break;
            case 26:
                a = (int) (((float) (C2277h.m12974a(context) * 360)) / 480.0f);
                break;
            case 27:
                a = (int) (((float) (C2277h.m12974a(context) * 360)) / 480.0f);
                break;
        }
        this.f8224h = ((float) a) / 2.0f;
        switch (C2279c.m12992g(this.f8217a)) {
            case 16:
            case 22:
                this.f8225i = 47.0f * C2277h.m12976c(context);
                break;
            case 26:
                this.f8225i = C2277h.m12976c(context) * 30.0f;
                break;
            case 27:
                this.f8225i = C2277h.m12976c(context) * 30.0f;
                break;
        }
        this.f8222f = (int) (((float) C2277h.m12974a(context)) * 1.048f);
        this.f8223g = (float) this.f8222f;
        Resources resources = getResources();
        /*
        switch (C2279c.m12992g(this.f8217a)) {
            case 22:
                this.f8233q = BitmapFactory.decodeResource(resources, R.drawable.ic_lockscreen_text_normal_44);
                this.f8231o = BitmapFactory.decodeResource(resources, R.drawable.ic_lockscreen_answer_normal_41);
                this.f8232p = BitmapFactory.decodeResource(resources, R.drawable.ic_lockscreen_decline_normal_41);
                this.f8234r = BitmapFactory.decodeResource(resources, R.drawable.ic_lockscreen_answer_activated_41);
                this.f8235s = BitmapFactory.decodeResource(resources, R.drawable.ic_lockscreen_decline_activated_41);
                this.f8230n = BitmapFactory.decodeResource(resources, R.drawable.ic_in_call_touch_handle_normal40);
                break;
            case 26:
                this.f8233q = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_message);
                this.f8231o = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_call_on);
                this.f8232p = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_end_call_on);
                this.f8234r = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_call);
                this.f8235s = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_end_call);
                this.f8230n = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_handle);
                break;
            case 27:
                this.f8233q = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_message);
                this.f8231o = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_call_on);
                this.f8232p = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_end_call_on);
                this.f8234r = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_call);
                this.f8235s = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_end_call);
                this.f8230n = BitmapFactory.decodeResource(resources, R.drawable.android_60x_fab_ic_handle);
                break;
        }

        */
        this.f8236t = BitmapFactory.decodeResource(resources, R.drawable.ic_lockscreen_handle_pressed41);
        for (a = 0; a < this.f8219c.length; a++) {
            this.f8219c[a] = BitmapFactory.decodeResource(resources, R.drawable.android_42_00 + a);
        }
    }

    private void m13204a(Canvas canvas) {
        this.f8218b = new Paint();
        this.f8218b.setAntiAlias(true);
        this.f8218b.setStrokeWidth(C2277h.m12976c(this.f8217a) * 2.0f);
        this.f8218b.setStyle(Style.STROKE);
        switch (C2279c.m12992g(this.f8217a)) {
            case 16:
            case 22:
                this.f8218b.setColor(Color.parseColor("#181818"));
                break;
            case 26:
                this.f8218b.setColor(Color.parseColor("#5f5f5f"));
                break;
            case 27:
                this.f8218b.setColor(Color.parseColor("#5f5f5f"));
                break;
        }
        canvas.drawCircle((float) (C2277h.m12974a(this.f8217a) / 2), this.f8223g / 2.0f, this.f8224h, this.f8218b);
    }

    private void m13205b(Canvas canvas) {
        if (this.f8220d) {
            switch (C2279c.m12992g(this.f8217a)) {
                case 16:
                case 22:
                    if (this.f8236t != null && !this.f8236t.isRecycled()) {
                        canvas.drawBitmap(this.f8236t, this.f8226j - ((float) (this.f8236t.getWidth() / 2)), this.f8227k - ((float) (this.f8236t.getHeight() / 2)), null);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void m13206c(Canvas canvas) {
        this.f8218b = new Paint();
        this.f8218b.setAntiAlias(true);
        switch (C2279c.m12992g(this.f8217a)) {
            case 16:
            case 22:
                this.f8218b.setColor(Color.parseColor("#FF3C39"));
                break;
            case 26:
                this.f8218b.setColor(Color.parseColor("#FF1744"));
                break;
            case 27:
                this.f8218b.setColor(Color.parseColor("#FF1744"));
                break;
        }
        canvas.drawCircle(((float) (C2277h.m12974a(this.f8217a) / 2)) - this.f8224h, this.f8223g / 2.0f, this.f8225i, this.f8218b);
    }

    private void m13207d(Canvas canvas) {
        this.f8218b = new Paint();
        this.f8218b.setAntiAlias(true);
        switch (C2279c.m12992g(this.f8217a)) {
            case 16:
            case 22:
                this.f8218b.setColor(Color.parseColor("#9CCF00"));
                break;
            case 26:
                this.f8218b.setStyle(Style.STROKE);
                this.f8218b.setStrokeWidth(C2277h.m12976c(this.f8217a) * 2.0f);
                this.f8218b.setColor(Color.parseColor("#FFFFFF"));
                break;
            case 27:
                this.f8218b.setStyle(Style.STROKE);
                this.f8218b.setStrokeWidth(C2277h.m12976c(this.f8217a) * 2.0f);
                this.f8218b.setColor(Color.parseColor("#FFFFFF"));
                break;
        }
        canvas.drawCircle((float) (C2277h.m12974a(this.f8217a) / 2), (this.f8223g / 2.0f) - this.f8224h, this.f8225i, this.f8218b);
    }

    private void m13208e(Canvas canvas) {
        this.f8218b = new Paint();
        this.f8218b.setAntiAlias(true);
        switch (C2279c.m12992g(this.f8217a)) {
            case 16:
            case 22:
                this.f8218b.setColor(Color.parseColor("#9CCF00"));
                break;
            case 26:
                this.f8218b.setColor(Color.parseColor("#0288D1"));
                break;
            case 27:
                this.f8218b.setColor(Color.parseColor("#0288D1"));
                break;
        }
        canvas.drawCircle(((float) (C2277h.m12974a(this.f8217a) / 2)) + this.f8224h, this.f8223g / 2.0f, this.f8225i, this.f8218b);
    }

    private void m13209f(Canvas canvas) {
        if (this.f8220d) {
            if (!(this.f8233q == null || this.f8233q.isRecycled())) {
                canvas.drawBitmap(this.f8233q, (float) ((C2277h.m12974a(this.f8217a) / 2) - (this.f8233q.getWidth() / 2)), ((this.f8223g / 2.0f) - this.f8224h) - ((float) (this.f8233q.getHeight() / 2)), null);
            }
            Bitmap bitmap = this.f8229m == 1 ? this.f8234r : this.f8231o;
            if (!(bitmap == null || bitmap.isRecycled())) {
                canvas.drawBitmap(bitmap, (((float) (C2277h.m12974a(this.f8217a) / 2)) + this.f8224h) - ((float) (this.f8231o.getWidth() / 2)), (this.f8223g / 2.0f) - ((float) (this.f8231o.getHeight() / 2)), null);
            }
            bitmap = this.f8229m == 3 ? this.f8235s : this.f8232p;
            if (bitmap != null && !bitmap.isRecycled()) {
                canvas.drawBitmap(bitmap, (((float) (C2277h.m12974a(this.f8217a) / 2)) - this.f8224h) - ((float) (this.f8232p.getWidth() / 2)), (this.f8223g / 2.0f) - ((float) (this.f8232p.getHeight() / 2)), null);
            }
        }
    }

    private void m13210g(Canvas canvas) {
        if (this.f8220d && this.f8221e >= 0 && this.f8221e < this.f8219c.length) {
            try {
                if (this.f8219c[this.f8221e] != null && !this.f8219c[this.f8221e].isRecycled()) {
                    canvas.drawBitmap(this.f8219c[this.f8221e], (float) ((C2277h.m12974a(this.f8217a) / 2) - (this.f8219c[this.f8221e].getWidth() / 2)), (this.f8223g / 2.0f) - ((float) (this.f8219c[this.f8221e].getHeight() / 2)), null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void m13211h(Canvas canvas) {
        if (this.f8220d && this.f8230n != null && !this.f8230n.isRecycled()) {
            canvas.drawBitmap(this.f8230n, (float) ((C2277h.m12974a(this.f8217a) / 2) - (this.f8230n.getWidth() / 2)), (this.f8223g / 2.0f) - ((float) (this.f8230n.getHeight() / 2)), null);
        }
    }

    public void m13212a() {
        this.f8220d = true;
        new Thread(this).start();
    }

    public void m13213b() {
        this.f8220d = false;
    }

    public void m13214c() {
        if (!(this.f8233q == null || this.f8233q.isRecycled())) {
            this.f8233q.recycle();
        }
        this.f8233q = null;
        if (!(this.f8231o == null || this.f8231o.isRecycled())) {
            this.f8231o.recycle();
        }
        this.f8231o = null;
        if (!(this.f8232p == null || this.f8232p.isRecycled())) {
            this.f8232p.recycle();
        }
        this.f8232p = null;
        if (!(this.f8234r == null || this.f8234r.isRecycled())) {
            this.f8234r.recycle();
        }
        this.f8234r = null;
        if (!(this.f8235s == null || this.f8235s.isRecycled())) {
            this.f8235s.recycle();
        }
        this.f8235s = null;
        if (!(this.f8230n == null || this.f8230n.isRecycled())) {
            this.f8230n.recycle();
        }
        this.f8230n = null;
        if (!(this.f8236t == null || this.f8236t.isRecycled())) {
            this.f8236t.recycle();
        }
        this.f8236t = null;
        int i = 0;
        while (i < this.f8219c.length) {
            if (!(this.f8219c[i] == null || this.f8219c[i].isRecycled())) {
                this.f8219c[i].recycle();
            }
            this.f8219c[i] = null;
            i++;
        }
        this.f8219c = null;
        this.f8220d = false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f8228l) {
            m13204a(canvas);
            switch (this.f8229m) {
                case 0:
                    m13205b(canvas);
                    break;
                case 1:
                    m13208e(canvas);
                    break;
                case 2:
                    m13207d(canvas);
                    break;
                case 3:
                    m13206c(canvas);
                    break;
            }
            m13209f(canvas);
            return;
        }
        m13210g(canvas);
        m13211h(canvas);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(C2277h.m12974a(this.f8217a), this.f8222f);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.f8228l = true;
                this.f8226j = motionEvent.getX();
                this.f8227k = motionEvent.getY();
                break;
            case 1:
                this.f8228l = false;
                this.f8226j = (float) (C2277h.m12974a(this.f8217a) / 2);
                this.f8227k = this.f8223g / 2.0f;
                switch (this.f8229m) {
                    case 1:
                        m13212a();
                        break;
                    case 3:
                        m13213b();
                        break;
                }
                if (this.f8237u != null) {
                    this.f8237u.mo1884a(this, this.f8229m);
                }
                this.f8229m = 0;
                break;
            case 2:
                this.f8228l = true;
                this.f8226j = motionEvent.getX();
                this.f8227k = motionEvent.getY();
                this.f8229m = 0;
                if (this.f8226j <= (((float) (C2277h.m12974a(this.f8217a) / 2)) + this.f8224h) - this.f8225i) {
                    if (this.f8226j >= (((float) (C2277h.m12974a(this.f8217a) / 2)) - this.f8224h) + this.f8225i) {
                        if (this.f8227k < ((this.f8223g / 2.0f) - this.f8224h) + this.f8225i) {
                            this.f8229m = 2;
                            break;
                        }
                        this.f8229m = 0;
                        break;
                    }
                    this.f8229m = 3;
                    break;
                }
                this.f8229m = 1;
                break;
        }
        return true;
    }

    public void run() {
        while (this.f8220d) {
            try {
                this.f8221e++;
                if (this.f8221e >= this.f8219c.length) {
                    this.f8221e = 0;
                }
                Thread.sleep(150);
            } catch (Exception e) {
                e.printStackTrace();
            }
            postInvalidate();
        }
    }

    public void setOnTriggerListener(C2302a c2302a) {
        this.f8237u = c2302a;
    }
}
