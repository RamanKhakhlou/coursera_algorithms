import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] p) {   // finds all line segments containing 4 points
        if (p == null || Arrays.asList(p).contains(null)) throw new IllegalArgumentException("Points must not be null");
        if (!this.checkPoints(p)) throw new IllegalArgumentException("Points must not contain equal points");
        this.points = p.clone();
        this.segments = this.brutForce();
    }
    
    private LineSegment[] brutForce() {
        int size = this.points.length;
        LineSegment[] segments = new LineSegment[size];
        int current = 0;
        for (int p = 0; p < size - 3; p++) {
            for (int q = p + 1; q < size - 2; q++) {
                for (int r = q + 1; r < size - 1; r++) {
                    for (int s = r + 1; s < size; s++) {
                        Point pointP = this.points[p];
                        Point pointQ = this.points[q];
                        Point pointR = this.points[r];
                        Point pointS = this.points[s];
                        double slopeQ = pointP.slopeTo(pointQ);
                        double slopeR = pointP.slopeTo(pointR);
                        double slopeS = pointP.slopeTo(pointS);
                        if (slopeQ == slopeR && slopeQ == slopeS) {
                            Point[] points = new Point[] { pointP, pointQ, pointR, pointS };
                            Arrays.sort(points);
                            LineSegment line = new LineSegment(points[0], points[3]);
                            segments[current++] = line;
                        }
                    }
                }
            }
        }
        LineSegment[] result = new LineSegment[current];
        System.arraycopy(segments, 0, result, 0, current);
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
        Point a = new Point(9470, 2305);
        Point b = new Point(12116, 15570);
        Point c = new Point(16181, 11443);
        Point d = new Point(2277, 15296);
        Point e = new Point(16181, 11443);
        Point f = new Point(3000, 4000);
        Point g = new Point(14000, 15000);
        Point h = new Point(6000, 7000);

        Point[] points = new Point[] {
            a, b, c, d, e, f, g, h
        };
        System.out.println(points);
        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        System.out.println(brute.numberOfSegments());
//        System.out.println(brute.segments()[0]);
//        System.out.println(brute.segments()[1]);
    }
}