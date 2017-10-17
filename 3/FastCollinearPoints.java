import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;
    private final LineSegment[] segments;

    public FastCollinearPoints (Point[] p) {   // finds all line segments containing 4 points
        if (p == null || Arrays.asList(p).contains(null)) throw new IllegalArgumentException("Points must not be null");
        if (!this.checkPoints(p)) throw new IllegalArgumentException("Points must not contain equal points");
        this.points = p.clone();
        this.segments = this.brutForce();
    }
    
    private class LineSegmentTemp {
        public final Point p;   // one endpoint of this line segment
        public final Point q;   // the other endpoint of this line segment
        
        /**
         * Initializes a new line segment.
         *
         * @param  p one endpoint
         * @param  q the other endpoint
         * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
         *         is <tt>null</tt>
         */
        public LineSegmentTemp(Point p, Point q) {
            this.p = p;
            this.q = q;
        }
        
        public boolean equal(LineSegmentTemp segment) {
            return this.p.compareTo(segment.p) == 0 && this.q.compareTo(segment.q) == 0;
        }
    }
    
    private class PointSlope implements Comparable<PointSlope> {
        private final Point point;
        private final double slope;

        public PointSlope(Point p, double s) {
            this.point = p;
            this.slope = s;
        }
        
        public int compareTo(PointSlope point) {
            if(this == null && point != null) return -1;
            if(this != null && point == null) return 1;
            if(this == null && point == null) return 0;
            double weight = this.slope - point.slope;
            if (weight < 0) return -1;
            else if (weight > 0) return 1;
            return 0;
        }
        
        public String toString() {
            /* DO NOT MODIFY */
            return this.point.toString() + " - " + this.slope;
        }
    }
    
    private boolean containsLine(LineSegment[] segments, LineSegment line) {
        for (int i = 0; i < segments.length; i++) {
            LineSegment segment = segments[i];
            if (segment != null && segment == line) {
                return true;
            }
        }
        return false;
    }
    
    private LineSegment[] brutForce() {
        // - Think of p as the origin.
        // - For each other point q, determine the slope it makes with p.
        // - Sort the points according to the slopes they makes with p.
        // - Check if any 3 (or more) adjacent points in the sorted order have equal
        // slopes with respect to p. If so, these points, together with p, are collinear.
        int size = this.points.length;
        LineSegmentTemp[] segments = new LineSegmentTemp[size];
        int current = 0;
        for (int i = 0; i < size; i++) {
            Point p = this.points[i];
            PointSlope[] pointSlopes = new PointSlope[size - 1];
//            PointSlope[] pointSlopes = new PointSlope[size - i - 1];
            int slopeIndex = 0;
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    Point q = this.points[j];
                    double slope = p.slopeTo(q);
                    PointSlope pointSlope = new PointSlope(q, slope);
                    pointSlopes[slopeIndex++] = pointSlope;
                }
            }
//            for (int j = i; j < size; j++) {
//                if (i != j) {
//                    Point q = this.points[j];
//                    double slope = p.slopeTo(q);
//                    PointSlope pointSlope = new PointSlope(q, slope);
//                    pointSlopes[slopeIndex++] = pointSlope;
//                }
//            }
            Arrays.sort(pointSlopes);
            int lineSize = 0;
            if(pointSlopes.length > 2) {
                for (int k = 0; k < pointSlopes.length; k++) {
                    lineSize++;
//                    if (k == 0 ||
//                        k < pointSlopes.length - 1 && pointSlopes[k].slope == pointSlopes[k + 1].slope ||
//                        (k == pointSlopes.length - 1 && pointSlopes[k].slope == pointSlopes[k - 1].slope)
//                    ) {
//                        lineSize++;
//                    }
                    if (
                        (k < pointSlopes.length - 1 && pointSlopes[k].slope != pointSlopes[k + 1].slope) ||
                        (k == pointSlopes.length - 1)
                    ) {
                        if (lineSize >= 3) {
                            // TODO: Add LineSegment if no previosly added
                            Point[] points = new Point[lineSize + 1];
                            points[0] = p;
                            for(int l = 1; l < lineSize + 1; l++) {
                                points[l] = pointSlopes[k - (lineSize - 1) + (l - 1)].point;
                            }
                            Arrays.sort(points);
                            LineSegmentTemp line = new LineSegmentTemp(points[0], points[lineSize]);
                            boolean isAdded = false;
                            for(int m = 0; m < current; m++) {
                                if(segments[m].equal(line)) {
                                    isAdded = true;
                                }
                            }
                            if(!isAdded) {
                                segments[current++] = line;
                            }
                        }
                        lineSize = 0;
                    }
                }
            }
        }
        
        LineSegment[] result = new LineSegment[current];
//        System.arraycopy(segments, 0, result, 0, current);
        for (int i = 0; i < current; i++) {
            LineSegmentTemp segment = segments[i];
            result[i] = new LineSegment(segment.p, segment.q);
        }
        return result;
    }
    
    private boolean checkPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point a = points[i];
                Point b = points[j];
                boolean isEqual = a.compareTo(b) == 0;
                if (isEqual) {
                    return false;
                }
            }
        }
        return true;
    }

    public int numberOfSegments() {        // the number of line segments
        return this.segments.length;
    }

    public LineSegment[] segments() {               // the line segments
        return this.segments.clone();
    }
       
    public static void main(String[] args) {
//        Point a = new Point(10000, 0);
//        Point b = new Point(0, 10000);
//        Point c = new Point(3000, 7000);
//        Point d = new Point(7000, 3000);
//        Point e = new Point(20000, 21000);
//        Point f = new Point(3000, 4000);
//        Point g = new Point(14000, 15000);
//        Point h = new Point(6000, 7000);
//
//        Point[] points = new Point[] {
//            a, b, c, d, e, f, g, h
//        };

                
        
        Point p1 = new Point(10000, 0);
        Point p2 = new Point(8000, 2000);
        Point p3 = new Point(2000, 8000);
        Point p4 = new Point(0, 10000);
        
        Point p5 = new Point(20000, 0);
        Point p6 = new Point(18000, 2000);
        Point p7 = new Point(2000, 18000);

        Point p8 = new Point(10000, 20000);
        Point p9 = new Point(30000, 0);
        Point p10 = new Point(0, 30000);
        Point p11 = new Point(20000, 10000);

        Point p12 = new Point(13000, 0);
        Point p13 = new Point(11000, 3000);
        Point p14 = new Point(5000, 12000);
        Point p15 = new Point(9000, 6000);
        
        Point[] points = new Point[] {
            p1, p2, p3, p4,
            p5, p6, p7,
            p8, p9, p10, p11,
            p12, p13, p14, p15
        };

        
        
//        Point p1 = new Point(9000, 9000);
//        Point p2 = new Point(8000, 8000);
//        Point p3 = new Point(7000, 7000);
//        Point p4 = new Point(6000, 6000);
//        Point p5 = new Point(5000, 5000);
//        Point p6 = new Point(4000, 4000);
//        Point p7 = new Point(3000, 3000);
//        Point p8 = new Point(2000, 2000);
//        Point p9 = new Point(1000, 1000);
//        
//        Point[] points = new Point[] {
//            p1, p2, p3, p4,
//            p5, p6, p7, p8, p9
//        };
        
        FastCollinearPoints brute = new FastCollinearPoints(points);
        System.out.println(brute.numberOfSegments());
        System.out.println(brute.segments());
    }
}