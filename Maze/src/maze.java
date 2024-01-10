import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.ComputedPixelImage;
import javalib.worldimages.FontStyle;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

// represents a node in a graph
class MazeCell {
  private final ArrayList<Edge> outEdges;
  private final Posn position;

  MazeCell(ArrayList<Edge> outEdges, int x, int y) {
    this.outEdges = outEdges;
    this.position = new Posn(x, y);
  }

  MazeCell(int x, int y) {
    this.outEdges = new ArrayList<Edge>();
    this.position = new Posn(x, y);
  }

  // OBSERATION: used in kruskals algorithm method to add the edges to the
  // worklist, so it can then sort and unionize them
  ArrayList<Edge> getEdges() {
    return this.outEdges;
  }

  // adds an edge to the this maze cell and conversely, to the other MazeCell it
  // is connected to
  void addEdge(MazeCell other) {
    this.outEdges.add(new Edge(this, other));
    other.outEdges.add(new Edge(this, other));
  }

  // gathers all the MazeCells that are connected to this MazeCell
  // (the outgoing cells)
  ArrayList<MazeCell> allToCells() {
    ArrayList<MazeCell> allToCells = new ArrayList<MazeCell>();
    for (int e = 0; e < this.outEdges.size(); e += 1) {
      this.outEdges.get(e).addTheFrom(allToCells);
    }
    return allToCells;
  }

  // draws this cell with walls
  void drawWall(ComputedPixelImage result, int size, ArrayList<Edge> edges) {
    int edgeSize = (int) (size * 0.05) + 1;

    if ((this.findNeighborTo("up") != null) && !this.findNeighborTo("up").hasEdge(edges)) {
      result.setPixels(this.position.getX() * size, this.position.getY() * size, size, edgeSize,
          Color.darkGray);
    }
    if ((this.findNeighborTo("left") != null) && !this.findNeighborTo("left").hasEdge(edges)) {
      result.setPixels(this.position.getX() * size, this.position.getY() * size, edgeSize, size,
          Color.darkGray);
    }
  }

  // draws this cell
  void drawCell(ComputedPixelImage result, int size, ArrayList<Edge> edges) {
    if ((this.findNeighborTo("up") != null) && this.findNeighborTo("up").hasEdge(edges)) {
      result.setPixels(this.position.getX() * size, this.position.getY() * size, size, size,
          Color.cyan);
    }
    if ((this.findNeighborTo("left") != null) && this.findNeighborTo("left").hasEdge(edges)) {
      result.setPixels(this.position.getX() * size, this.position.getY() * size, size, size,
          Color.cyan);
    }
  }

  // draws this cell, which represents the player
  void drawPlayer(ComputedPixelImage result, int size, Color color) {
    result.setPixels(this.position.getX() * size, this.position.getY() * size, size, size, color);
  }

  // produces the neighbor of this cell in the given direction
  Edge findNeighborTo(String direction) {
    Posn upPos = new Posn(this.position.getX(), this.position.getY() - 1);
    Posn downPos = new Posn(this.position.getX(), this.position.getY() + 1);
    Posn leftPos = new Posn(this.position.getX() - 1, this.position.getY());
    Posn rightPos = new Posn(this.position.getX() + 1, this.position.getY());
    for (int i = 0; i < this.outEdges.size(); i += 1) {
      MazeCell neighbor = this.outEdges.get(i).getTo();
      Edge edge = this.outEdges.get(i);
      if ((direction.equals("up") && upPos.equals(neighbor.position))
          || (direction.equals("down") && downPos.equals(neighbor.position))) {
        return edge;
      }
      else if ((direction.equals("left") && leftPos.equals(neighbor.position))
          || (direction.equals("right") && rightPos.equals(neighbor.position))) {
        return edge;
      }
    }
    return null;
  }

  // produces the neighbor of this cell in the given direction
  Edge findNeighborFrom(String direction) {
    Posn upPos = new Posn(this.position.getX(), this.position.getY() - 1);
    Posn downPos = new Posn(this.position.getX(), this.position.getY() + 1);
    Posn leftPos = new Posn(this.position.getX() - 1, this.position.getY());
    Posn rightPos = new Posn(this.position.getX() + 1, this.position.getY());
    for (int i = 0; i < this.outEdges.size(); i += 1) {
      MazeCell neighbor = this.outEdges.get(i).getFrom();
      Edge edge = this.outEdges.get(i);
      if ((direction.equals("up") && upPos.equals(neighbor.position))
          || (direction.equals("down") && downPos.equals(neighbor.position))) {
        return edge;
      }
      else if ((direction.equals("left") && leftPos.equals(neighbor.position))
          || (direction.equals("right") && rightPos.equals(neighbor.position))) {
        return edge;
      }
    }
    return null;
  }

  // finds the representative of this cell in the hashmaps of representatives
  public MazeCell findInMazeCell(HashMap<MazeCell, MazeCell> representatives) {
    return new MinimumSpanningTree().find(representatives, this);
  }

  // overrides equals for mazecell
  // delegates to equals in Posn
  public boolean equals(Object other) {
    if (other instanceof MazeCell) {
      MazeCell that = (MazeCell) other;
      return this.position.equals(that.position);
    }
    else {
      return false;
    }
  }

  // overrides hashcodes for mazecell
  // delegates to hashcode in Posn
  public int hashCode() {
    return this.position.hashCode();
  }

  // determines if this cell's posn is the same as the other posn
  boolean samePosn(int x, int y) {
    return (this.position.getX() == x) && (this.position.getY() == y);
  }

  // returns an int indicating whether or not a this cell and that cell are
  // connected
  // from the left or from the right of this cell
  public int findDirectionX(MazeCell that) {
    return this.position.getX() - that.position.getX();
  }

  // returns an int indicating whether or not a this cell and that cell are
  // connected
  // from the up or from the bottom of this cell
  public int findDirectionY(MazeCell that) {
    return this.position.getY() - that.position.getY();
  }
}

// represents a position on a cartesian plane
class Posn {
  private final int x;
  private final int y;

  Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // OBSERVATION: used when finding the position of a cell
  // cannot define a posn without the access to x field
  int getX() {
    return this.x;
  }

  // OBSERVATION: used when finding the position of a cell
  // cannot define a posn without the access to y field
  int getY() {
    return this.y;
  }

  // overrides equals of posns by ensurring it checks that this and that x values
  // and this and that y values are equal
  public boolean equals(Object other) {
    if (other instanceof Posn) {
      Posn that = (Posn) other;
      return (this.x == that.x) && (this.y == that.y);
    }
    else {
      return false;
    }
  }

  // overrides hashcodes for posns to create a new hash code
  // multiples x by 1000 and adds the y since y can never be a 4 digit number
  public int hashCode() {
    return (1000 * this.x) + this.y;
  }
}

//represents an edge in the graph
class Edge {
  private final MazeCell from;
  private MazeCell to;
  private final double weight;

  Edge(MazeCell from, MazeCell to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  Edge(MazeCell from, MazeCell to, Random random) {
    this.from = from;
    this.to = to;
    this.weight = random.nextDouble();
  }

  Edge(MazeCell from, MazeCell to) {
    this(from, to, new Random());
  }

  Edge(MazeCell from) {
    this(from, null);
  }

  // determines if this edge the same as any of the edges in the given list of
  // edges
  boolean hasEdge(ArrayList<Edge> edges) {
    for (Edge e : edges) {
      if ((e.from.equals(this.from) && e.to.equals(this.to))
          || (e.from.equals(this.to) && e.to.equals(this.from))) {
        return true;
      }
    }
    return false;
  }

  // OBSERVATION: the use of an edge is to know what its connected to
  // unable to use an edge without getting its from and to
  public MazeCell getFrom() {
    return this.from;
  }

  // OBSERVATION: the use of an edge is to know what its connected to
  // unable to use an edge without getting its from and to
  public MazeCell getTo() {
    return this.to;
  }

  // adds the this 'to' MazeCell to a list
  public void addTheFrom(ArrayList<MazeCell> allTos) {
    allTos.add(this.from);
  }

  // checks if the to and the from of an edge have the same representative
  public boolean sameRepresentative(HashMap<MazeCell, MazeCell> representatives) {
    return this.getTo().findInMazeCell(representatives)
        .equals(this.getFrom().findInMazeCell(representatives));
  }

  // calls union on this from and this to, to unionize them
  // sets this from's representative as this.to's representative
  public void unionize(HashMap<MazeCell, MazeCell> representatives) {
    new MinimumSpanningTree().union(representatives, this.from, this.to);
  }

  // compares the weight of two edges
  public int compareWeight(Edge e2) {
    double weightCompare = this.weight - e2.weight;

    if (weightCompare < 0) {
      return -1;
    }
    else if (weightCompare > 0) {
      return 1;
    }
    else {
      return 0;
    }
  }
}

//user interaction stored within class
class MazeWorld extends World {
  MazeGraph graph;
  private final int height;
  private final int width;

  MazeWorld(int height, int width) {
    this.graph = new MazeGraph(height, width);
    this.height = height;
    this.width = width;
  }

  MazeWorld(MazeGraph graph, int height, int width) {
    this.graph = new MazeGraph(height, width);
    this.height = height;
    this.width = width;
  }

  // all the on key events for maze world
  public void onKeyEvent(String key) {
    String gameType = "manual";
    String colorType = "0";
    // traverse the maze up
    if ((key.equals("up")) && gameType.equals("manual")) {
      this.graph.movePlayer("up");
    }
    // traverse the maze down
    else if ((key.equals("down")) && gameType.equals("manual")) {
      this.graph.movePlayer("down");
    }
    // traverse the maze left
    else if ((key.equals("left")) && gameType.equals("manual")) {
      this.graph.movePlayer("left");
    }
    // traverse the maze right
    else if ((key.equals("right")) && gameType.equals("manual")) {
      this.graph.movePlayer("right");
    }
    // breadth-first search
    else if ((key.equals("b"))) {
      gameType = "b";
      // ystem.out.print(newPath);
      this.graph.newPath("b");
      // System.out.print(newPath);
    }
    //
    else if ((key.equals("d"))) {
      gameType = "d";
      this.graph.newPath("d");

    }
    // reset from initial maze
    else if (key.equals("r")) {
      System.out.println("r");
    }
    // create a new random maze
    else if (key.equals("n")) {
      System.out.println("n");
      this.graph.newRandom();
    }
    else if (key.equals("p")) {
      System.out.println("p");
      gameType = "manual";
      this.graph.movePlayer("");
    }
    // show visited paths
    else if (key.equals("1")) {
      if (colorType.equals("1")) {
        colorType = "0";
      }
      else {
        colorType = "1";
      }
    }
    // show gradient from start
    else if (key.equals("2")) {
      if (colorType.equals("2")) {
        colorType = "0";
      }
      else {
        colorType = "2";
      }
    }
    // show gradient from end
    else if (key.equals("3")) {
      if (colorType.equals("3")) {
        colorType = "0";
      }
      else {
        colorType = "3";
      }
    }
  }

  // creates an ending scene
  public WorldScene lastScene(String msg) {
    WorldScene scene = new WorldScene(1000, 1000);
    scene.placeImageXY(new TextImage("End", 24, FontStyle.BOLD, Color.black), 500, 500);
    return scene;
  }

  // tests if the world should end by checking if the "player" or the traverser is
  // at the end of the maze
  public boolean shouldWorldEnd() {
    return this.graph.shouldEnd();
  }

  // makes the scene
  public WorldScene makeScene() {
    int width;
    int height;
    int size;
    if (this.width > this.height) {
      width = (1000 / this.height) * this.height;
      size = 1000 / this.width;
      height = this.height * size;
    }
    else {
      height = (1000 / this.width) * this.width;
      size = 1000 / this.height;
      width = this.width * size;
    }
    WorldScene canvas = new WorldScene(width, height);
    ComputedPixelImage result = new ComputedPixelImage(width, height);
    result.setPixels(0, 0, width, height, Color.gray);
    canvas.placeImageXY(this.graph.drawPlayer(result, size), width / 2, height / 2);
    canvas.placeImageXY(this.graph.drawPath(result, size), width / 2, height / 2);
    canvas.placeImageXY(this.graph.drawMaze(result, size), width / 2, height / 2);
    return canvas;
  }

  // animates the path finding
  public void onTick() {

  }
}

//class for the information on the maze's graph
class MazeGraph {
  private final ArrayList<MazeCell> mazeCells;
  private final int height;
  private final int width;
  private final ArrayList<Edge> mazeEdges;
  private final ArrayList<Edge> pathSolverEdges;
  private final ArrayList<MazeCell> playerCells;

  MazeGraph(int height, int width) {
    this.height = height;
    this.width = width;
    this.mazeCells = this.createGraph();
    this.playerCells = new ArrayList<MazeCell>();
    this.mazeEdges = new MinimumSpanningTree().kruskalsAlgorithm(this.mazeCells);
    this.pathSolverEdges = new ArrayList<Edge>();
  }

  // checks if the world should end if there
  boolean shouldEnd() {
    if (this.playerCells.size() > 0) {
      return this.playerCells.get(this.playerCells.size() - 1).samePosn(this.width - 1,
          this.height - 1);
    }
    else {
      return false;
    }
  }

  // makes a new random maze
  void newRandom() {
    this.mazeEdges.clear();
    this.mazeCells.clear();
    this.mazeCells.addAll(this.createGraph());
    this.mazeEdges.addAll(new MinimumSpanningTree().kruskalsAlgorithm(this.mazeCells));
  }

  // moves the player in the indicated direction
  void movePlayer(String direction) {

    if (this.playerCells.size() == 0) {
      MazeCell start = this.mazeCells.get(0);
      this.playerCells.add(start);
    }
    else if ((this.playerCells.get(this.playerCells.size() - 1).findNeighborFrom(direction) != null)
        && this.playerCells.get(this.playerCells.size() - 1).findNeighborFrom(direction)
            .hasEdge(this.mazeEdges)) {
      this.playerCells.add(
          this.playerCells.get(this.playerCells.size() - 1).findNeighborFrom(direction).getFrom());
    }
    else if ((this.playerCells.get(this.playerCells.size() - 1).findNeighborTo(direction) != null)
        && this.playerCells.get(this.playerCells.size() - 1).findNeighborTo(direction)
            .hasEdge(this.mazeEdges)) {
      this.playerCells
          .add(this.playerCells.get(this.playerCells.size() - 1).findNeighborTo(direction).getTo());
    }
  }

  // creates a new path for dfs or bfs based on the given string indicator
  void newPath(String key) {
    this.pathSolverEdges.clear();
    MazeCell start = this.mazeCells.get(0);
    MazeCell target = this.mazeCells.get(this.mazeCells.size() - 1);
    if (key.equals("b")) {
      System.out.print(this.pathSolverEdges);
      this.bfs(start, target);
      System.out.print(this.pathSolverEdges);
    }
    else if (key.equals("d")) {
      System.out.print(this.pathSolverEdges);
      this.dfs(start, target);
      System.out.print(this.pathSolverEdges);
    }
  }

  // creates the graph
  ArrayList<MazeCell> createGraph() {
    ArrayList<ArrayList<MazeCell>> tempResult = new ArrayList<ArrayList<MazeCell>>();
    ArrayList<MazeCell> result = new ArrayList<MazeCell>();
    // iterates through all of the cells and connects them by creating the edges
    for (int y = 0; y < this.height; y += 1) {
      ArrayList<MazeCell> row = new ArrayList<MazeCell>();
      for (int x = 0; x < this.width; x += 1) {
        MazeCell center = new MazeCell(x, y);
        if (x != 0) {
          MazeCell left = row.get(x - 1);
          center.addEdge(left);
        }
        if (y != 0) {
          MazeCell up = tempResult.get(y - 1).get(x);
          center.addEdge(up);
        }
        row.add(center);
      }
      tempResult.add(row);
    }
    // adds all the results from the previous for loop into the a new, flattened
    // arraylist
    for (int i = 0; i < tempResult.size(); i += 1) {
      result.addAll(tempResult.get(i));
    }
    return result;
  }

  // draws the maze
  WorldImage drawMaze(ComputedPixelImage result, int size) {
    // draws all the walls for the maze by iterating though each cells that are in
    // the maze edges
    for (int e = 0; e < this.mazeCells.size(); e += 1) {
      this.mazeCells.get(e).drawWall(result, size, this.mazeEdges);
    }
    return result;
  }

  // draws the solved path
  WorldImage drawPath(ComputedPixelImage result, int size) {
    if (this.pathSolverEdges.size() == 0) {
      this.mazeCells.get(0).drawPlayer(result, size, Color.magenta);
    }
    else {
      // iterates through all the cells and draws the ones in the pathSolverEdges
      for (int e = 0; e < this.mazeCells.size(); e += 1) {
        this.mazeCells.get(e).drawCell(result, size, this.pathSolverEdges);
      }
    }
    return result;
  }

  // draws the player
  WorldImage drawPlayer(ComputedPixelImage result, int size) {
    // iterates through all the player cells and draws the color wherever the player
    // moved
    for (int e = 0; e < (this.playerCells.size()); e += 1) {
      if (e == (this.playerCells.size() - 1)) {
        this.playerCells.get(e).drawPlayer(result, size, Color.blue);
      }
      else {
        this.playerCells.get(e).drawPlayer(result, size, Color.cyan);
      }
    }
    return result;
  }

  // method for conducting breadth first search on the maze
  public void bfs(MazeCell start, MazeCell target) {

    ArrayList<Edge> edges = new MinimumSpanningTree().kruskalsAlgorithm(this.mazeCells);
    ArrayList<MazeCell> kruskalsFroms = new ArrayList<>();

    // adds the edges from to a new list from kruskals
    for (Edge e : edges) {
      MazeCell toAdd = e.getFrom();
      kruskalsFroms.add(toAdd);
    }

    // ArrayList<MazeCell> visited = new ArrayList<MazeCell>();
    HashMap<MazeCell, Edge> cameFromEdge = new HashMap<>();
    Queue<MazeCell> worklist = new LinkedList<>();
    worklist.offer(start); // initialize worklist with start MazeCell

    Set<MazeCell> processed = new HashSet<>(); // create a set to keep track of processed MazeCells

    // MazeCell before = null; // initialize before to null

    // the worklist will become empty because we are removing an element each time
    // the loop
    // loops, so it will terminate
    while (!worklist.isEmpty()) {
      MazeCell next = worklist.poll(); // get the next item from the worklist

      if (processed.contains(next)) {

      }

      else if (next.equals(target)) {
        ArrayList<Edge> path = this.reconstructPath(next, cameFromEdge);
        this.pathSolverEdges.clear();
        this.pathSolverEdges.addAll(path);
        System.out.println(path);
        // this.drawPath(path);
      }
      else {
        for (MazeCell neighbor : next.allToCells()) {
          if (kruskalsFroms.contains(neighbor)) {
            worklist.offer(neighbor); // add the neighbor to the top of the stack
            cameFromEdge.putIfAbsent(neighbor, new Edge(next, neighbor));
          }
        }
      }

      // before = next; // update before to hold the previous node processed
      processed.add(next); // add the current node to the set of processed nodes
    }
  }

  // method for conducting depth first search on the maze
  void dfs(MazeCell start, MazeCell target) {

    ArrayList<Edge> edges = new MinimumSpanningTree().kruskalsAlgorithm(this.mazeCells);
    ArrayList<MazeCell> kruskalsFroms = new ArrayList<>();

    // adds the edges from to a new list from kruskals
    for (Edge e : edges) {
      MazeCell toAdd = e.getFrom();
      kruskalsFroms.add(toAdd);
    }

    // ArrayList<MazeCell> visited = new ArrayList<MazeCell>();
    HashMap<MazeCell, Edge> cameFromEdge = new HashMap<>();
    Stack<MazeCell> worklist = new Stack<>();
    worklist.push(start); // initialize worklist with start MazeCell

    Set<MazeCell> processed = new HashSet<>(); // create a set to keep track of processed MazeCells

    // MazeCell before = null; // initialize before to null

    // the worklist will become empty because we are removing an element each time
    // the loop
    // loops, so it will terminate
    while (!worklist.isEmpty()) {
      MazeCell next = worklist.pop(); // get the next item from the worklist

      if (processed.contains(next)) {

      }

      else if (next.equals(target)) {
        ArrayList<Edge> path = this.reconstructPath(next, cameFromEdge);
        this.pathSolverEdges.clear();
        this.pathSolverEdges.addAll(path);
        // this.drawPath(path);
      }
      else {
        for (MazeCell neighbor : next.allToCells()) {
          if (kruskalsFroms.contains(neighbor)) {
            worklist.push(neighbor); // add the neighbor to the top of the stack
            cameFromEdge.putIfAbsent(neighbor, new Edge(next, neighbor));
          }
        }
      }

      // before = next; // update before to hold the previous node processed
      processed.add(next); // add the current node to the set of processed nodes
    }
  }

  // reconstructs the path from the end cell all the way up the edges
  public ArrayList<Edge> reconstructPath(MazeCell end, HashMap<MazeCell, Edge> cameFromEdge) {
    ArrayList<Edge> path = new ArrayList<>();
    MazeCell current = end;

    // while (current.isAContainedKey(cameFromEdge)) {
    while (cameFromEdge.containsKey(current)) {
      Edge edge = cameFromEdge.get(current);
      path.add(edge);
      current = edge.getFrom();
    }

    Collections.reverse(path);
    return path;
  }
}

//all the functions to find a minimum spanning tree contained in this class
//find and union helper methods
//as well as the overall kruskal's algorithm
class MinimumSpanningTree {
  // finds the representative of the given cell
  // if a node maps to itself, it is the representative
  // else look up from the parent node
  MazeCell find(HashMap<MazeCell, MazeCell> representatives, MazeCell cell) {
    if (!(cell.equals(representatives.get(cell)))) {
      representatives.put(cell, this.find(representatives, representatives.get(cell)));
    }
    return representatives.get(cell);
  }

  // set the value of one representative’s representative to the other.
  void union(HashMap<MazeCell, MazeCell> representatives, MazeCell from, MazeCell to) {
    representatives.put(this.find(representatives, from), this.find(representatives, to));
  }

  // kruskal's algorithm
  ArrayList<Edge> kruskalsAlgorithm(ArrayList<MazeCell> cells) {
    HashMap<MazeCell, MazeCell> representatives = new HashMap<>();
    ArrayList<Edge> edgesInTree = new ArrayList<>();
    ArrayList<Edge> worklist = new ArrayList<>();

    // initialize representatives
    for (MazeCell cell : cells) {
      representatives.put(cell, cell);
    }

    // adds all the edges of each cell into the worklist
    for (MazeCell cell : cells) {
      for (Edge e : cell.getEdges()) {
        worklist.add(e);
      }
    }

    // sorts the worklist
    new SortAlgo().sort(worklist, new CompareEdgeWeights());

    // perform Kruskal's algorithm
    while ((representatives.size() > 1) && !(worklist.isEmpty())) {
      Edge edge = worklist.get(0);
      worklist.remove(0);
      if ((edge.sameRepresentative(representatives))) {
        // continue;
        // they're already connected
      }
      // record edge in tree and merge components
      else {
        edgesInTree.add(edge);
        edge.unionize(representatives);
        // union(representatives, find(representatives, edge.getTo()),
        // find(representatives, edge.getFrom()));
      }
    }
    return edgesInTree;
  }
}

//a function object for sorting arraylists and a given comparator
class SortAlgo {
  // sorts an arraylist based on a given comparator
  <T> void sort(ArrayList<T> arr, Comparator<T> compare) {
    Collections.sort(arr, compare);
  }
}

//a comparator that compares the values of the frequencies of the given leaves
class CompareEdgeWeights implements Comparator<Edge> {
  // returns the edge with the least most weight
  public int compare(Edge e1, Edge e2) {
    return e1.compareWeight(e2);
  }
}