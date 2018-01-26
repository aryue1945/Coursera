import java.util.Arrays;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LinkedList<LineSegment> res; 
    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        for (int i = 0; i < n; i++)
            if (points[i] == null)
                throw new IllegalArgumentException();
        res = new LinkedList<LineSegment>();
        if (n < 4)
            return;
        Point[] p = Arrays.copyOf(points, n);
        Arrays.sort(p);
        for (int i = 0; i < n-3; i++) {
            for (int j = i+1; j < n-2; j++) {
                for (int k = j+1; k < n-1; k++) {
                    if (p[i].slopeTo(p[j]) == p[j].slopeTo(p[k]))
                        for (int m = k+1; m < n; m++) {
                            if (p[i].slopeTo(p[j]) == p[i].slopeTo(p[m]) && p[i].slopeTo(p[j]) == p[j].slopeTo(p[k])) {
                                res.add(new LineSegment(minPt(p[i], p[j], p[k], p[m]), 
                                        maxPt(p[i], p[j], p[k], p[m])));
                            //    System.out.printf("i:%dj:%dk:%dm:%d\n", i,j,k,m);
                            }
                                
                        }
                }
            }
        } 
    }
    private Point minPt(Point a, Point b, Point c, Point d) {
        Point min = a;
        if (a.compareTo(min) < 0)
            min = b;
        if (c.compareTo(min) < 0)
            min = c;
        if (d.compareTo(min) < 0)
            min = d;
        return min;
    }
    private Point maxPt(Point a, Point b, Point c, Point d) {
        Point max = a;
        if (a.compareTo(max) > 0)
            max = b;
        if (c.compareTo(max) > 0)
            max = c;
        if (d.compareTo(max) > 0)
            max = d;
        return max;
    }
    public int numberOfSegments() {        // the number of line segments
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }
