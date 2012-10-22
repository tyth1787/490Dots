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

	private List<Line> lines;
	private List<Square> squares;

	private int xDistanceBetweenDots;
	private int yDistanceBetweenDots;

	public GameDrawer(Context context, int w, int h) {
		super(context);
		paint = new Paint();
		points = new ArrayList<Point>();
		width = w;
		height = h;
		lines = new ArrayList<Line>();
		squares = new ArrayList<Square>();
		squares.add(new Square(40, 40, 0));

	}

	public void addLine(Line l) {
		lines.add(l);
	}

	public void addSquare(Square s) {
		squares.add(s);
	}

	private void drawLines(Canvas canvas) {
		if (lines.size() == 0) {
			Point a = new Point(40, 40, 0, 0);
			Point b = new Point(40, 40 + yDistanceBetweenDots, 0, 1);
			Point c = new Point(40 + xDistanceBetweenDots,
					40 + yDistanceBetweenDots, 0, 1);
			Point d = new Point(40 + xDistanceBetweenDots, 40, 0, 0);
			lines.add(new Line(a, b));
			lines.add(new Line(b, c));
			lines.add(new Line(c, d));
			lines.add(new Line(d, a));
		}
		paint.setStrokeWidth(BORDER_STROKE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(getResources().getColor(R.color.black));

		for (Line l : lines) {
			int x1 = l.getA().x;
			int y1 = l.getA().y;
			int x2 = l.getB().x;
			int y2 = l.getB().y;
			Log.d("DOTS", "Drawing line at " + x1 + ", " + y1 + " - " + x2
					+ ", " + y2);
			canvas.drawLine(l.getA().x, l.getA().y, l.getB().x, l.getB().y,
					paint);
		}
	}

	private void drawBorder(Canvas canvas) {
		paint.setStrokeWidth(BORDER_STROKE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(getResources().getColor(R.color.black));
		canvas.drawRect(PADDING, PADDING, getWidth() - PADDING, getHeight()
				- PADDING, paint);
	}

	private void drawSquares(Canvas canvas) {
		paint.setStrokeWidth(BORDER_STROKE);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		for (Square s : squares) {
			if (s.player == 0)
				paint.setColor(getResources().getColor(R.color.red));
			else
				paint.setColor(getResources().getColor(R.color.blue));

			canvas.drawRect(s.left, s.top, s.left + xDistanceBetweenDots, s.top
					+ yDistanceBetweenDots, paint);

		}

	}

	private void initDots() {
		int extraPaddingFromBorder = PADDING + BORDER_STROKE;
		xDistanceBetweenDots = (getWidth() - extraPaddingFromBorder * 2)
				/ width + 1;
		yDistanceBetweenDots = (getHeight() - extraPaddingFromBorder * 2)
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

	private void drawDots(Canvas canvas) {
		paint.setColor(getResources().getColor(R.color.black));
		for (Point p : points) {
			Log.d("DOTS", "Drawing point at " + p.getX() + ", " + p.getY());
			canvas.drawCircle(p.getX(), p.getY(), DOT_SIZE, paint);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (points.size() == 0)
			initDots();
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
