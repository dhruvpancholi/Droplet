/**
 * @author Dhruv Pancholi
 */

package com.example.droplet;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * 
 * @author Dhruv Pancholi
 * 
 */

public class GameThread extends Thread {

	SurfaceHolder mySurfaceHolder;
	Context myContext;
	Handler myHandler;
	Bitmap background;
	/**
	 * @param screenW
	 *            Width of the screen after surface resize
	 * @param screenH
	 *            Height of the screen after surface resize
	 */
	protected static int screenW, screenH;
	/**
	 * @param running
	 *            States the state of the game, whether running or paused or
	 *            over
	 */
	protected boolean running;
	/**
	 * @param gameOver
	 *            Tracks whether the game is over
	 */
	protected boolean gameOver;

	protected Paint textPaint;

	// Parameter of the engine
	int pipeWidth, pipeHeight;
	// Zig-Zig parameters
	int zigWidth, zigHeight, waveLength;

	float timeStep = 1.0f / 60.0f;
	int velocityIterations = 6;
	int positionIterations = 2;
	// Conversion
	float p2A = 10.0f;
	int i = 0;

	/**
	 * @param CHANNELWIDTH
	 */
	public static float CHANNELWIDTH = (float) (screenW / 2.4);

	public GameThread(SurfaceHolder holder, Context context, Handler handler) {
		mySurfaceHolder = holder;
		myContext = context;
		myHandler = handler;
	}

	public SurfaceHolder getSurfaceHolder() {
		return mySurfaceHolder;
	}

	Bubble bubble;
	Pipe pipe;
	Calculator calc;
	LinkedList<Bubble> bubbleList = new LinkedList<Bubble>();
	LinkedList<Object> segmentList = new LinkedList<Object>();
	Elbow elbow;
	Paint linePaint;
	Paint pointPaint;
	private LinkedList<Node> nodeList;
	Vec[] nodes = new Vec[4];

	@Override
	public void run() {

		createBackground();
		createBubbles();
		createPaint();
		createNodes();

		
		

		while (running) {
			Canvas c = null;
			try {
				c = mySurfaceHolder.lockCanvas(null);
				synchronized (mySurfaceHolder) {
					if (!gameOver) {
						// Step the physics engine or do the necessary
						// calculation
					}
					try {
						Thread.sleep(17);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					draw(c);
				}
			} finally {
				if (c != null) {
					mySurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
		super.run();
	}

	private void createBubbles() {
		bubble = new Bubble(pipeWidth, myContext, 0);
	}

	private void createPaint() {
		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setColor(Color.BLACK);
		linePaint.setStyle(Style.STROKE);
		linePaint.setStrokeWidth(1);
		linePaint.setPathEffect(new DashPathEffect(new float[] {4,4}, 0));
		pointPaint = new Paint();
		pointPaint.setStrokeWidth(screenW/100);
		pointPaint.setAntiAlias(true);
	}

	private void createNodes() {
		float yTracker=0;
		nodes[0]=new Vec(screenW/4,yTracker);
		yTracker+=screenH/16;
		nodes[1]=new Vec(screenW/4,yTracker);
		float MERGE_ANGLE_DEG = 160;
		yTracker+=1+1/Math.tan(angleToRad(MERGE_ANGLE_DEG/2));
		nodes[2]=new Vec(screenW/2,yTracker);
		yTracker+=screenH/16;
		nodes[3]=new Vec(screenW/2,yTracker);
		yTracker+=screenH/32;
		float wavelength = screenH/4;
		nodes[4]=new Vec(screenW/2,yTracker);
		
	}

	private double angleToRad(float mERGE_ANGLE_DEG) {
		return mERGE_ANGLE_DEG*Math.PI/180;
	}

	/**
	 * To draw on the canvas c, part of the game thread
	 * @param c Canvas passed to the method
	 */
	private void draw(Canvas c) {
		try {
			c.drawBitmap(tableTop, 0, 0, null);
			c.drawLine(screenW/2, 0, screenW/2, screenH, linePaint);
			for (int i = 0; i < nodes.length-1; i++) {
				c.drawPoint(nodes[i].x, nodes[i].y, pointPaint);
				c.drawLine(nodes[i].x, nodes[i].y,nodes[i+1].x, nodes[i+1].y, linePaint);
			}
			c.drawCircle(screenW/2, screenH/2, 50, pointPaint);
		} catch (Exception e) {

		}

	}

	Bitmap tableTop;

	/**
	 * Initiate the background image called once when surfaceview is created
	 */
	private void createBackground() {
		tableTop = Bitmap.createBitmap(screenW, screenH,
				Bitmap.Config.ARGB_8888);
		Canvas bCanvas = new Canvas(tableTop);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL_AND_STROKE);
		bCanvas.drawRect(0, 0, screenW, screenH, paint);

	}

	public void setSurfaceSize(int width, int height) {
		synchronized (mySurfaceHolder) {
			screenW = width;
			screenH = height;
			textPaint = new Paint();
			textPaint.setAntiAlias(true);
			textPaint.setColor(Color.BLACK);
			textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			///textPaint.setTextAlign(Paint.Align.LEFT);

		}
	}

	public void setRunning(boolean b) {
		running = b;
	}

	private Bitmap resizeBitmap(Bitmap bitmap, int dstWidth, int dstHeight) {
		bitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
		return bitmap;
	}

	private Bitmap rotateBitmap(Bitmap bitmap, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return bitmap;
	}

	private Bitmap createBitmap(int width, int height) {
		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = Bitmap.createBitmap(width, height, conf);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		// paint.setARGB(1, 255, 255, 255);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRect(0, 0, width, height, paint);
		return bitmap;
	}

	public boolean doTouchEvent(MotionEvent event) {
		synchronized (mySurfaceHolder) {
			int eventAction = event.getAction();
			int X = (int) event.getX();
			int Y = (int) event.getY();
			switch (eventAction) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
		}
		return true;
	}


}
