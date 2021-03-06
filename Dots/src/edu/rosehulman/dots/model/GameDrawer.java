package edu.rosehulman.dots.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import edu.rosehulman.dots.R;

public class GameDrawer extends View {
	int width = 5;
	int height = 5;
	int boxHeight;
	Paint paint;
	int[] playerColors = {0,1};
	private final int PADDING = 5;
	private final int DOT_SIZE = 3;
	private final int BORDER_STROKE = 3;
	
	final public int[] COLORS = {R.color.red, R.color.blue, R.color.green, R.color.Orange};
	

	private List<Point> points;

	private List<Line> lines;
	private List<Square> squares;

	private int xDistanceBetweenDots;
	private int yDistanceBetweenDots;

	public int getXSpace() {
		return xDistanceBetweenDots;
	}

	public int getYSpace() {
		return yDistanceBetweenDots;
	}
	
	private int size;

	public GameDrawer(Context context, int w, int h) {
		super(context);
		paint = new Paint();
		points = new ArrayList<Point>();
		width = w;
		height = h;
		lines = new ArrayList<Line>();
		squares = new ArrayList<Square>();
	}

	public void addLine(Line l) {
		lines.add(l);
	}

	public void addSquare(Square s) {
		squares.add(s);
	}

	private void drawLines(Canvas canvas) {

		paint.setStrokeWidth(BORDER_STROKE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(getResources().getColor(R.color.black));

		for (Line l : lines) {
			canvas.drawLine(l.getA().ordX, l.getA().ordY, l.getB().ordX,
					l.getB().ordY, paint);
		}
	}

	public List<Point> getPoints() {
		return points;
	}

	private void drawBorder(Canvas canvas) {
		paint.setStrokeWidth(BORDER_STROKE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(getResources().getColor(R.color.black));
		canvas.drawRect(PADDING, PADDING, size - PADDING, size
				- PADDING, paint);
	}

	private void drawSquares(Canvas canvas) {
		paint.setStrokeWidth(BORDER_STROKE);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		for (Square s : squares) {
			paint.setColor(getResources().getColor(COLORS[playerColors[s.player]]));
			//paint.setColor(COLORS[playerColors[s.player]]);

			canvas.drawRect(s.left, s.top, s.left + xDistanceBetweenDots, s.top
					+ yDistanceBetweenDots, paint);

		}

	}

	private void initDots() {
		int extraPaddingFromBorder = PADDING + BORDER_STROKE;
		size = (getWidth() < getHeight() ? getWidth() : getHeight());
		xDistanceBetweenDots = (size - extraPaddingFromBorder * 2)
				/ width + 1;
		yDistanceBetweenDots = (size - extraPaddingFromBorder * 2)
				/ height + 1;

		int xPosition;
		int yPosition;
		int xPadding = xDistanceBetweenDots / 2 + extraPaddingFromBorder;
		int yPadding = yDistanceBetweenDots / 2 + extraPaddingFromBorder;
		for (int i = 0; i < width; i++) {
			xPosition = xPadding + xDistanceBetweenDots * i;
			for (int z = 0; z < height; z++) {
				yPosition = yPadding + yDistanceBetweenDots * z;
				points.add(new Point(xPosition, yPosition));
				//Log.d("DOTS", "Adding point at " + xPosition + ", " + yPosition);
			}
		}
	}

	private void drawDots(Canvas canvas) {
		paint.setColor(getResources().getColor(R.color.black));
		paint.setStyle(Style.FILL);
		for (Point p : points) {
			canvas.drawCircle(p.ordX, p.ordY, DOT_SIZE, paint);

		}
	}
	public void removeSquares(List<Square> slist){
		for (Square s : slist){
			this.squares.remove(s);
		}
	}
	
	public void removeLine(Line l){
		this.lines.remove(l);
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (points.size() == 0){
			initDots();
			this.setLayoutParams(new LinearLayout.LayoutParams(getSize(), getSize()));
		}
		Log.d("dots", "Width = " + getWidth() + " Height = " + getHeight());
		drawSquares(canvas);
		drawLines(canvas);
		drawDots(canvas);
		drawBorder(canvas);
	}

	public int getViewWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getSize(){
		return (getWidth() < getHeight() ? getWidth() : getHeight());
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

	public void setPlayerColor(int player, int color){
		this.playerColors[player] = color;
	}

}
