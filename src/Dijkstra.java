/**
 * Created by Dell on 3/30/2017.
 */

import java.awt.*;
import java.util.*;

public class Dijkstra {

    Node start;
    Node end;
    Node[][] gridArea;

    // Horizontal and VerticalDistance
    double hVDistance = 1.0;

    // Diagonal Distance
    //Manhattan values.
    public static double Manhattan() {

        double dDistance = 2;
        return dDistance;
    }

    //Euclidean values.
    public static double Euclidean() {
        double dDistance = 1.4;
        return dDistance;
    }

    //Chebyshev values

    public static double Chebyshev() {
        double dDistance = 1;
        return dDistance;
    }

    ///double dDistance = 1.4;

   /* for Manhattan Distances,
    double horizontalVerticalDistance = 1.0;
    double diagonalDistance = 2.0;

    for Chebyshev Distances,
    double horizontalVerticalDistance = 1.0;
    double diagonalDistance = 1.0; */

    /**
     *
     * @param matrix The boolean matrix from the framework given
     * @param si start x value
     * @param sj start y value
     * @param ei end x value
     * @param ej end x value
     * @return The path nodes
     */
    ArrayList<Node> distance(boolean[][] matrix, int si, int sj, int ei, int ej,double dDistance,String name) {

        int size = matrix.length;
       // System.out.println("hi");


        start = new Node(si, sj);
        end = new Node(ei, ej);
        // The grid that is used to store nodes
        gridArea = new Node[size][size];
        // Creating nodes and finding blocked cells in matrix and mapping accordingly to our grid
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                gridArea[i][j] = new Node(i, j);
                if (matrix[i][j] == false) {

                    gridArea[i][j].blocked = true;


                }
            }
        }

        // setting start distance to 0.
        // All other nodes will have infinity distance at the beginning
        start.distance =0;

        // a comparator object to deal with Priority Queue
        Comparator<Node> adjacencyComparator = (left, right) -> {
            if (left.distance > (right.distance)) {
                return 1;

            }
            return -1;
        };

        // Queue to store visiting nodes
        Queue<Node> queueB = new PriorityQueue(size, adjacencyComparator);

        queueB.add(start);

        while (queueB.size() > 0) {
            Node current = queueB.remove();
            Node t;

            // Top
            if (current.x - 1 >= 0) {

                // Top Top
                t = gridArea[current.x - 1][current.y];
               // System.out.println("hiiiiiiiiiiii:"+t);
                if (!t.visited && !t.blocked && t.distance > current.distance + hVDistance) {
                    t.distance = current.distance + hVDistance;
                    t.parent = current;
                    queueB.add(t);
                }

                // Top Left
                if (current.y - 1 > 0) {
                    t = gridArea[current.x - 1][current.y - 1];
                    if (!t.visited && !t.blocked && t.distance > current.distance + dDistance) {
                        t.distance = current.distance + dDistance;
                        t.parent = current;
                        queueB.add(t);
                    }
                }

                // Top Right
                if (current.y + 1 < size) {
                    t = gridArea[current.x - 1][current.y + 1];
                    if (!t.visited && !t.blocked && t.distance > current.distance + dDistance) {
                        t.distance = current.distance + dDistance;
                        t.parent = current;
                        queueB.add(t);
                    }
                }
            }

            // Left
            if (current.y - 1 > 0) {
                t = gridArea[current.x][current.y - 1];
                if (!t.visited && !t.blocked && t.distance > current.distance + hVDistance) {
                    t.distance = current.distance + hVDistance;
                    t.parent = current;
                    queueB.add(t);
                }
            }

            // Right
            if (current.y + 1 < size) {
                t = gridArea[current.x][current.y + 1];
                if (!t.visited && !t.blocked && t.distance > current.distance + hVDistance) {
                    t.distance = current.distance + hVDistance;
                    t.parent = current;
                    queueB.add(t);
                }
            }
            // Down
            if (current.x + 1 < size) {

                // Down Down
                t = gridArea[current.x + 1][current.y];
                if (!t.visited && !t.blocked && t.distance > current.distance + hVDistance) {
                    t.distance = current.distance + hVDistance;
                    t.parent = current;
                    queueB.add(t);
                }

                // Down Left
                if (current.y - 1 >= 0) {
                    t = gridArea[current.x + 1][current.y - 1];
                    if (!t.visited && !t.blocked && t.distance > current.distance + dDistance) {
                        t.distance = current.distance + dDistance;
                        t.parent = current;
                        queueB.add(t);
                    }
                }

                // Down Right
                if (current.y + 1 < size) {
                    t = gridArea[current.x + 1][current.y + 1];
                    if (!t.visited && !t.blocked && t.distance > current.distance + dDistance) {
                        t.distance = current.distance + dDistance;
                        t.parent = current;
                        queueB.add(t);
                    }
                }
            }
            current.visited = true;
        }

        ArrayList<Node> path = new ArrayList<>();

        // Checking if a path exists
        if (!(gridArea[end.x][end.y].distance == Integer.MAX_VALUE)) {
            //Trace back the path
            Node current = gridArea[end.x][end.y];
            System.out.println(name+":"+current.distance);
            while (current.parent != null) {
                path.add(current.parent);

                current = current.parent;
            }
        } else System.out.println("No possible path");


        return path;
    }


    class Node {
        int x;
        int y;
        double distance = Integer.MAX_VALUE;
        Node parent = null;
        boolean visited;
        boolean blocked;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2,ArrayList<Node> path) {
        int N = a.length;
        int s=path.size();
        int count=0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.setPenColor(Color.GREEN);
                        StdDraw.circle(j, N - i - 1, .5);

                    }


        for (Dijkstra.Node node : path) {
            if(s-count==1){
                return;
            }
            count++;

                StdDraw.setPenColor(Color.RED);

            StdDraw.circle(node.y,  N- node.x - 1, .5);
            //path.remove(node.y);

        }
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }

    public static void main(String[] args){
        // boolean[][] open = StdArrayIO.readBoolean2D();

        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(100, 0.8);

        StdArrayIO.print(randomlyGenMatrix);
        show(randomlyGenMatrix, true);

        // Reading the coordinates for points A and B on the input squared grid.

        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Start the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis
        Stopwatch timerFlow = new Stopwatch();

        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A > ");
        int Ai = in.nextInt();

        System.out.println("Enter j for A > ");
        int Aj = in.nextInt();

        System.out.println("Enter i for B > ");
        int Bi = in.nextInt();

        System.out.println("Enter j for B > ");
        int Bj = in.nextInt();

        ArrayList<Dijkstra.Node> path1 = new Dijkstra().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Manhattan(),"Manhattan");
        ArrayList<Dijkstra.Node> path2 = new Dijkstra().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Euclidean(),"Euclidean");
        ArrayList<Dijkstra.Node> path3= new Dijkstra().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Chebyshev(),"Chebyshev");

        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path2);
       // show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path2);
       // show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path3);

    }
}
