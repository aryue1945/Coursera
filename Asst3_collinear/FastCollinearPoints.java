import java.util.Arrays;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LinkedList<LineSegment> res; 
    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        for (int i = 0; i < n; i++)
            if (points[i] == null)
                throw new IllegalArgumentException();
        res = new LinkedList<LineSegment>();
        if (n < 4)
            return;
        Point[] p = Arrays.copyOf(points, n);
        
        for (int i = 0; i < points.length; i++) {
            Point ref = points[i];
            Arrays.sort(p);
            Arrays.sort(p, ref.slopeOrder());

            int min = 0;
            while (min < n && ref.slopeTo(p[min]) == Double.NEGATIVE_INFINITY) min++;
            if (min != 1) throw new IllegalArgumentException();
            int max = min;
            while (min < n) {
                while (max < n && ref.slopeTo(p[max]) == ref.slopeTo(p[min])) max++;
                if (max - min >= 3) {
                    Point pMin = p[min].compareTo(ref) < 0 ? p[min] : ref;
                    Point pMax = p[max - 1].compareTo(ref) > 0 ? p[max - 1] : ref;
                    if (ref == pMin)
                        res.add(new LineSegment(pMin, pMax));
                }
                min = max;
            }
        }
    } 
    public int numberOfSegments() {       // the number of line segments
        return res.size();
    }
    public LineSegment[] segments() {                // the line segments
        return res.toArray(new LineSegment[res.size()]);
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }
