package edu.rosehulman.dots.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import edu.rosehulman.dots.R;

public class GameDrawer extends View {
	int width = 5;
	int height = 5;
	int boxHeight;
	Paint paint;
	private final int PADDING = 5;
	private final int DOT_SIZE = 3;
	private final int BORDER_STROKE = 3;
	
	private List<Point> points;

	public GameDrawer(Context context, int w, int h) {
		super(context);
		paint = new Paint();
		points = new ArrayList<Point>();
		width = w;
		height = h;
	}

	private void drawLines(Canvas canvas) {
	}

	private void drawBorder(Canvas canvas) {
		paint.setStrokeWidth(BORDER_STROKE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(getResources().getColor(R.color.black));
		canvas.drawRect(PADDING, PADDING, getWidth() - PADDING, getHeight()
				- PADDING, paint);
	}

	private void drawSquares(Canvas canvas) {

	}

	private void initDots() {
		int extraPaddingFromBorder = PADDING + BORDER_STROKE;
		int xDistanceBetweenDots = (getWidth() - extraPaddingFromBorder * 2)
				/ width + 1;
		int yDistanceBetweenDots = (getHeight() - extraPaddingFromBorder * 2)
				/ height + 1;

		int xPosition;
		int yPosition;
		int xPadding = xDistanceBetweenDots / 2 + extraPaddingFromBorder;
		int yPadding = yDistanceBetweenDots / 2 + extraPaddingFromBorder;
		for (int i = 0; i < width; i++) {
			xPosition = xPadding + xDistanceBetweenDots * i;
			for (int z = 0; z < height; z++) {
				yPosition = yPadding + yDistanceBetweenDots * z;
				points.add(new Point(xPosition, yPosition, i, z));
				Log.d("DOTS", "Adding point at " + xPosition + ", " + yPosition);
			}
		}
	}
	
	private void drawDots(Canvas canvas){
		for (Point p : points){
			Log.d("DOTS", "Drawing point at " + p.getX() + ", " + p.getY());
			canvas.drawCircle(p.getX(),p.getY(), DOT_SIZE, paint);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (points.size() == 0)
			initDots();
		drawLines(canvas);
		drawSquares(canvas);
		drawDots(canvas);
		drawBorder(canvas);
	}

	public int getViewWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getViewHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(int boxHeight) {
		this.boxHeight = boxHeight;
	}

}
