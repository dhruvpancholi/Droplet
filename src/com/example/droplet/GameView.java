package com.example.droplet;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * @author Dhruv Pancholi
 * 
 *         The surface view object which will be used to display graphics
 * 
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	Context myContext;

	/**
	 * @param thread
	 *            Caching of game thread
	 */
	protected GameThread thread;
	/**
	 * @param mySurfaceHolder
	 *            Caching of surface holder
	 */
	SurfaceHolder mySurfaceHolder;

	/**
	 * @param textPaint
	 *            Basic paint object which will be used through out the game to
	 *            display the text
	 */
	protected static Paint textPaint;
	/**
	 * @param FLOW_RATE
	 *            The flow rate of the circuit, which will be calculated
	 *            according to the screen size in the calculator object
	 * @param RESISTANCE_PER_UNIT_LENGTH
	 *            Name says it all
	 * @param VISCOSITY
	 * @param DENSITY
	 */
	protected static final double FLOW_RATE = 5.0;
	protected static final double RESISTANCE_PER_UNIT_LENGTH = 1.0;
	protected static final double VISCOSITY = 0.0001;
	protected static final double DENSITY = 1000.0;

	/**
	 * Constructor of the surface view
	 * 
	 * @param context
	 *            Context object
	 */

	public GameView(Context context) {
		super(context);
		myContext = context;
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		thread = new GameThread(holder, context, new Handler() {
			@Override
			public void handleMessage(Message m) {

			}
		});
		mySurfaceHolder = thread.getSurfaceHolder();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		if (thread.getState() == Thread.State.NEW) {
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.setRunning(false);
	}

	public GameThread getThread() {
		return thread;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return thread.doTouchEvent(event);
	}

}
