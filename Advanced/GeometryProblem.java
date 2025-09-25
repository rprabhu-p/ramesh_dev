package Advanced;

import java.util.*;

// Point class
class Point {
    int x, y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Point p = (Point) o;
        return x == p.x && y == p.y;
    }
    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
}

// Line class
class Line {
    int dx, dy; // slope normalized
    Point refPoint;
    Set<Point> points;
    
    public Line(Point p1, Point p2){
        this.refPoint = p1;
        int deltaX = p2.x - p1.x;
        int deltaY = p2.y - p1.y;
        int g = gcd(deltaX, deltaY);
        if(g != 0){
            dx = deltaX / g;
            dy = deltaY / g;
        } else {
            dx = 0;
            dy = 0;
        }
        points = new HashSet<>();
        points.add(p1);
        points.add(p2);
    }
    
    public void addPoint(Point p){
        points.add(p);
    }
    
    public boolean contains(Point p){
        int deltaX = p.x - refPoint.x;
        int deltaY = p.y - refPoint.y;
        if(dx == 0) return deltaX == 0;
        if(dy == 0) return deltaY == 0;
        return deltaY * dx == deltaX * dy;
    }
    
    public Set<Point> getPoints(){
        return points;
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return dx == line.dx && dy == line.dy && contains(line.refPoint);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(dx, dy);
    }
    
    private int gcd(int a, int b){
        if(b == 0) return a;
        return gcd(b, a % b);
    }
    
    @Override
    public String toString(){
        List<Point> pts = new ArrayList<>(points);
        pts.sort(Comparator.comparingInt((Point p) -> p.x).thenComparingInt(p -> p.y));
        return pts.toString();
    }
}

// Geometry class
class Geometry {
    private List<Point> points;
    private Set<Line> lines;
    
    public Geometry(List<Point> points){
        this.points = points;
        this.lines = new HashSet<>();
        generateAllLines();
    }
    
    private void generateAllLines(){
        int n = points.size();
        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                Line line = new Line(points.get(i), points.get(j));
                for(Point p : points){
                    if(line.contains(p)) line.addPoint(p);
                }
                lines.add(line);
            }
        }
    }
    
    // Q1: check collinearity
    public boolean areCollinear(List<Point> pts){
        if(pts.size() <= 2) return true;
        Line line = new Line(pts.get(0), pts.get(1));
        for(Point p : pts){
            if(!line.contains(p)) return false;
        }
        return true;
    }
    
    // Q2: largest line through a point
    public Line largestLineThrough(Point p){
        Line maxLine = null;
        for(Line line : lines){
            if(line.getPoints().contains(p)){
                if(maxLine == null || line.getPoints().size() > maxLine.getPoints().size())
                    maxLine = line;
            }
        }
        return maxLine;
    }
    
    // Q3: points between two points
    public List<Point> pointsBetween(Point a, Point b){
        int dx = b.x - a.x;
        int dy = b.y - a.y;
        int g = gcd(Math.abs(dx), Math.abs(dy));
        List<Point> result = new ArrayList<>();
        if(g <= 1) return result;
        int stepX = dx / g;
        int stepY = dy / g;
        int cx = a.x + stepX;
        int cy = a.y + stepY;
        while(cx != b.x || cy != b.y){
            result.add(new Point(cx, cy));
            cx += stepX;
            cy += stepY;
        }
        return result;
    }
    
    // Q4: max number of points in a line
    public int maxLineSize(){
        int max = 0;
        for(Line line : lines){
            max = Math.max(max, line.getPoints().size());
        }
        return max;
    }
    
    // Q5: all lines through a point
    public List<Line> allLinesThrough(Point p){
        List<Line> result = new ArrayList<>();
        for(Line line : lines){
            if(line.getPoints().contains(p)) result.add(line);
        }
        return result;
    }
    
    private int gcd(int a, int b){
        if(b == 0) return a;
        return gcd(b, a % b);
    }
}

// Main class
public class GeometryProblem {
    public static void main(String[] args){
        List<Point> points = Arrays.asList(
                new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3),
                new Point(1,5), new Point(1,4), new Point(3,5)
        );
        Geometry geo = new Geometry(points);

        System.out.println("Q1: " + geo.areCollinear(Arrays.asList(new Point(0,0), new Point(0,1), new Point(0,3))));
        System.out.println("Q2: " + geo.largestLineThrough(new Point(0,1)));
        System.out.println("Q3: " + geo.pointsBetween(new Point(0,0), new Point(0,3)));
        System.out.println("Q4: " + geo.maxLineSize());
        System.out.println("Q5: " + geo.allLinesThrough(new Point(0,1)));
    }
}
