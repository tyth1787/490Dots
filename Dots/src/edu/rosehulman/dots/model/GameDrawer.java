package edu.rosehulman.dots.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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

	public GameDrawer(Context context) {
		super(context);
		paint = new Paint();
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

	private void drawDots(Canvas canvas) {
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
				canvas.drawCircle(xPosition, yPosition, DOT_SIZE, paint);
			}
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
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
