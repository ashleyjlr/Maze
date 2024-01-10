import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javalib.impworld.WorldScene;
import javalib.worldimages.ComputedPixelImage;
import javalib.worldimages.FontStyle;
import javalib.worldimages.TextImage;
import tester.Tester;

class ExamplesMaze {
  boolean testMazeGraphConstructor(Tester t) {
    return t.checkExpect(new MazeGraph(1, 1), true);
  }
}

class ExamplesMazeWorld {
  void testMakeScene(Tester t) {
    MazeWorld world = new MazeWorld(5, 5);
    world.bigBang(1000, 1000, 0.1);
  }
}

class ExamplesPosnMethods {
  Posn posnX1Y1 = new Posn(1, 1);
  Posn posnX1Y1a = new Posn(1, 1);

  Posn posnX1Y2 = new Posn(1, 2);
  Posn posnX2Y2 = new Posn(2, 2);

  void testGetX(Tester t) {
    t.checkExpect(this.posnX1Y1.getX(), 1);
    t.checkExpect(this.posnX2Y2.getX(), 2);
  }

  void testGetY(Tester t) {
    t.checkExpect(this.posnX1Y1.getY(), 1);
    t.checkExpect(this.posnX2Y2.getY(), 2);
  }

  void testEquals(Tester t) {
    t.checkExpect(this.posnX1Y1.equals(this.posnX1Y1), true);
    t.checkExpect(this.posnX1Y1.equals(this.posnX1Y1a), true);
    t.checkExpect(this.posnX1Y1.equals(this.posnX1Y2), false);
    t.checkExpect(this.posnX1Y1.equals(this.posnX2Y2), false);
  }

  void testHashCode(Tester t) {
    t.checkExpect(this.posnX1Y1.hashCode(), 1001);
    t.checkExpect(this.posnX1Y1a.hashCode(), 1001);
    t.checkExpect(this.posnX1Y2.hashCode(), 1002);
    t.checkExpect(this.posnX2Y2.hashCode(), 2002);
  }
}

class ExamplesMazeCellandEdgeandGraphandWorldMethods {
  MazeCell cellx0y0 = new MazeCell(0, 0);
  MazeCell cellx1y0 = new MazeCell(1, 0);
  MazeCell cellx2y0 = new MazeCell(2, 0);
  MazeCell cellx3y0 = new MazeCell(3, 0);
  MazeCell cellx4y0 = new MazeCell(4, 0);

  MazeCell cellx0y1 = new MazeCell(0, 1);
  MazeCell cellx1y1 = new MazeCell(1, 1);
  MazeCell cellx2y1 = new MazeCell(2, 1);
  MazeCell cellx3y1 = new MazeCell(3, 1);
  MazeCell cellx4y1 = new MazeCell(4, 1);

  MazeCell cellx0y2 = new MazeCell(0, 2);
  MazeCell cellx1y2 = new MazeCell(1, 2);
  MazeCell cellx2y2 = new MazeCell(2, 2);
  MazeCell cellx3y2 = new MazeCell(3, 2);
  MazeCell cellx4y2 = new MazeCell(4, 2);

  MazeCell cellx0y3 = new MazeCell(0, 3);
  MazeCell cellx1y3 = new MazeCell(1, 3);
  MazeCell cellx2y3 = new MazeCell(2, 3);
  MazeCell cellx3y3 = new MazeCell(3, 3);
  MazeCell cellx4y3 = new MazeCell(4, 3);

  MazeCell cellx0y4 = new MazeCell(0, 4);
  MazeCell cellx1y4 = new MazeCell(1, 4);
  MazeCell cellx2y4 = new MazeCell(2, 4);
  MazeCell cellx3y4 = new MazeCell(3, 4);
  MazeCell cellx4y4 = new MazeCell(4, 4);

  ArrayList<MazeCell> mazeCells = new ArrayList<MazeCell>();

  void setEdges() {
    //
    this.cellx0y0.addEdge(this.cellx1y0);
    this.cellx0y0.addEdge(this.cellx0y1);

    this.cellx1y0.addEdge(this.cellx2y0);
    this.cellx1y0.addEdge(this.cellx1y1);

    this.cellx2y0.addEdge(this.cellx3y0);
    this.cellx2y0.addEdge(this.cellx2y1);

    this.cellx3y0.addEdge(this.cellx4y0);
    this.cellx3y0.addEdge(this.cellx3y1);

    this.cellx4y0.addEdge(this.cellx4y1);

    //
    this.cellx0y1.addEdge(this.cellx1y1);
    this.cellx0y1.addEdge(this.cellx0y2);
    this.cellx0y1.addEdge(this.cellx0y0);

    this.cellx1y1.addEdge(this.cellx2y1);
    this.cellx1y1.addEdge(this.cellx1y2);

    this.cellx2y1.addEdge(this.cellx3y1);
    this.cellx2y1.addEdge(this.cellx2y2);

    this.cellx3y1.addEdge(this.cellx4y1);
    this.cellx3y1.addEdge(this.cellx3y2);

    this.cellx4y1.addEdge(this.cellx4y2);

    //
    this.cellx0y2.addEdge(this.cellx1y2);
    this.cellx0y2.addEdge(this.cellx0y3);

    this.cellx1y2.addEdge(this.cellx2y2);
    this.cellx1y2.addEdge(this.cellx1y3);

    this.cellx2y2.addEdge(this.cellx3y2);
    this.cellx2y2.addEdge(this.cellx2y3);
    this.cellx2y2.addEdge(this.cellx1y2);
    this.cellx2y2.addEdge(this.cellx2y1);

    this.cellx3y2.addEdge(this.cellx4y2);
    this.cellx3y2.addEdge(this.cellx3y3);

    this.cellx4y2.addEdge(this.cellx4y3);

    //
    this.cellx0y3.addEdge(this.cellx1y3);
    this.cellx0y3.addEdge(this.cellx0y4);

    this.cellx1y3.addEdge(this.cellx2y3);
    this.cellx1y3.addEdge(this.cellx1y4);

    this.cellx2y3.addEdge(this.cellx3y3);
    this.cellx2y3.addEdge(this.cellx2y4);

    this.cellx3y3.addEdge(this.cellx4y3);
    this.cellx3y3.addEdge(this.cellx3y4);

    this.cellx4y3.addEdge(this.cellx4y4);

    //
    this.cellx0y4.addEdge(this.cellx1y4);

    this.cellx1y4.addEdge(this.cellx2y4);

    this.cellx2y4.addEdge(this.cellx3y4);

    this.cellx3y4.addEdge(this.cellx4y4);

    //
    this.mazeCells.add(this.cellx0y0);
    this.mazeCells.add(this.cellx1y0);
    this.mazeCells.add(this.cellx2y0);
    this.mazeCells.add(this.cellx3y0);
    this.mazeCells.add(this.cellx4y0);

    this.mazeCells.add(this.cellx0y1);
    this.mazeCells.add(this.cellx1y1);
    this.mazeCells.add(this.cellx2y1);
    this.mazeCells.add(this.cellx3y1);
    this.mazeCells.add(this.cellx4y1);

    this.mazeCells.add(this.cellx0y2);
    this.mazeCells.add(this.cellx1y2);
    this.mazeCells.add(this.cellx2y2);
    this.mazeCells.add(this.cellx3y2);
    this.mazeCells.add(this.cellx4y2);

    this.mazeCells.add(this.cellx0y3);
    this.mazeCells.add(this.cellx1y3);
    this.mazeCells.add(this.cellx2y3);
    this.mazeCells.add(this.cellx3y3);
    this.mazeCells.add(this.cellx4y3);

    this.mazeCells.add(this.cellx0y4);
    this.mazeCells.add(this.cellx1y4);
    this.mazeCells.add(this.cellx2y4);
    this.mazeCells.add(this.cellx3y4);
    this.mazeCells.add(this.cellx4y4);
  }

  // tests and examples for findNeighborTo
  void testFindNeighborTo(Tester t) {
    this.setEdges();

    t.checkExpect(this.cellx0y0.findNeighborTo("pizza"), null);
    t.checkExpect(this.cellx0y0.findNeighborTo("up"), null);
    t.checkExpect(this.cellx0y0.findNeighborTo("left"), null);
    t.checkExpect(this.cellx0y0.findNeighborTo("right").getTo(), this.cellx1y0);
    t.checkExpect(this.cellx0y0.findNeighborTo("down").getTo(), this.cellx0y1);
    t.checkExpect(this.cellx2y2.findNeighborTo("pizza"), null);
    t.checkExpect(this.cellx2y2.findNeighborTo("up").getTo(), this.cellx2y1);
    t.checkExpect(this.cellx2y2.findNeighborTo("left").getTo(), this.cellx1y2);
    t.checkExpect(this.cellx2y2.findNeighborTo("right").getTo(), this.cellx3y2);
    t.checkExpect(this.cellx2y2.findNeighborTo("down").getTo(), this.cellx2y3);
  }

  // tests and examples for drawWall
  void testDrawWall(Tester t) {
    ComputedPixelImage original = new ComputedPixelImage(10, 10);
    ComputedPixelImage result = new ComputedPixelImage(10, 10);
    this.cellx0y0.drawWall(original, 5, new ArrayList<Edge>());
    t.checkExpect(original, result);
  }

  // tests and examples
  void testDrawCell(Tester t) {

    Edge edge1 = new Edge(this.cellx0y0, this.cellx0y1);
    Edge edge2 = new Edge(this.cellx1y1, this.cellx0y2);
    Edge edge3 = new Edge(this.cellx2y0, this.cellx3y1);
    Edge edge11 = new Edge(this.cellx0y0, this.cellx0y1);

    ArrayList<Edge> edgeList123 = new ArrayList<Edge>();
    edgeList123.add(edge1);
    edgeList123.add(edge2);
    edgeList123.add(edge3);
    edgeList123.add(edge11);

    ComputedPixelImage original = new ComputedPixelImage(10, 10);
    ComputedPixelImage result = new ComputedPixelImage(10, 10);
    this.cellx0y0.drawCell(original, 5, edgeList123);
    t.checkExpect(original, result);
  }

  // tests and examples
  void testDrawPlayer(Tester t) {
    ComputedPixelImage original = new ComputedPixelImage(10, 10);
    ComputedPixelImage result = new ComputedPixelImage(10, 10);
    result.setPixels(0, 0, 5, 5, Color.cyan);
    this.cellx0y0.drawPlayer(original, 5, Color.cyan);
    t.checkExpect(original, result);
  }

  // tests and examples for lastScene
  void testLastScene(Tester t) {
    WorldScene scene = new WorldScene(1000, 1000);
    scene.placeImageXY(new TextImage("End", 24, FontStyle.BOLD, Color.black), 500, 500);
    t.checkExpect(new MazeWorld(5, 5).lastScene("hi"), scene);
  }

  // tests and examples for shouldWorldEnd
  void testShouldWorldEnd(Tester t) {
    t.checkExpect(new MazeWorld(5, 5).shouldWorldEnd(), false);
    // the true case for this cannot be tested because it relies
    // a private field of MazeGraph that can't be observed
    // but can be seen through big bang
  }

  // tests and examples for shouldEnd
  void testShouldEnd(Tester t) {
    t.checkExpect(new MazeGraph(5, 5).shouldEnd(), false);
    // the true case for this cannot be tested because it relies
    // a private field of MazeGraph that can't be observed
    // but can be seen through big bang
  }

  // tests and examples for newRandom
  void testNewRandom(Tester t) {
    new MazeGraph(5, 5).newRandom();
    // This method can't be tested because it mutates private
    // fields of MazeGraph that can't be observed
    // but can be seen through big bang
  }

  // tests and examples for movePlayer
  void testMovePlayer(Tester t) {
    new MazeGraph(5, 5).movePlayer("d");
    // This method can't be tested because it mutates private
    // fields of MazeGraph that can't be observed
    // but can be seen through big bang
  }

  // tests and examples for newPath
  void testnewPath(Tester t) {
    new MazeGraph(5, 5).newPath("d");
    // This method can't be tested because it mutates private
    // fields of MazeGraph that can't be observed
    // but can be seen through big bang
  }

  // tests and examples for createGraph
  void testCreateGraph(Tester t) {
    new MazeGraph(5, 5).createGraph();
    // This method can't be tested because of the tester
    // library's definition of equality of objects.
    // Since the objects in an example graph are not the same
    // exact as those in the created graph by the method
    // the library sees them as different even if the
    // values are the same. The issue is further complicated
    // by edges being randomly assigned weights at creation
  }

  // test and examples for drawMaze
  void testDrawMaze(Tester t) {
    new MazeGraph(1, 1).drawMaze(new ComputedPixelImage(10, 10), 5);
    // This cannot be tested because it relies on
    // a private field of MazeGraph that can't be observed
    // but can be seen through big bang
  }

  // test and examples for drawPath
  void testDrawPath(Tester t) {
    new MazeGraph(1, 1).drawMaze(new ComputedPixelImage(10, 10), 5);
    // This cannot be tested because it relies on
    // a private field of MazeGraph that can't be observed
    // but can be seen through big bang
  }

  // test and examples for drawPlayer
  void testDrawPlayer_Graph(Tester t) {
    new MazeGraph(1, 1).drawPlayer(new ComputedPixelImage(10, 10), 5);
    // This cannot be tested because it relies on
    // a private field of MazeGraph that can't be observed
    // but can be seen through big bang
  }

  // tests and examples for findNeighborFrom
  void testFindNeighborFrom(Tester t) {
    this.setEdges();

    t.checkExpect(this.cellx0y0.findNeighborFrom("pizza"), null);
    t.checkExpect(this.cellx0y0.findNeighborFrom("up"), null);
    t.checkExpect(this.cellx0y0.findNeighborFrom("left"), null);
    t.checkExpect(this.cellx0y0.findNeighborFrom("right"), null);
    t.checkExpect(this.cellx0y0.findNeighborFrom("down").getTo(), this.cellx0y0);
    t.checkExpect(this.cellx0y0.findNeighborFrom("down").getFrom(), this.cellx0y1);
    t.checkExpect(this.cellx2y2.findNeighborFrom("pizza"), null);
    t.checkExpect(this.cellx2y2.findNeighborFrom("up").getFrom(), this.cellx2y1);
    t.checkExpect(this.cellx2y2.findNeighborFrom("left").getFrom(), this.cellx1y2);
    t.checkExpect(this.cellx2y2.findNeighborFrom("right"), null);
    t.checkExpect(this.cellx2y2.findNeighborFrom("down"), null);
  }

  // tests and examples for samePosn
  void testSamePosn(Tester t) {
    t.checkExpect(this.cellx0y0.samePosn(0, 0), true);
    t.checkExpect(this.cellx0y0.samePosn(1, 5), false);
    t.checkExpect(this.cellx0y0.samePosn(0, 1), false);
    t.checkExpect(this.cellx0y0.samePosn(6, 0), false);
  }

  // tests and examples for hasEdge
  void testHasEdge(Tester t) {
    Edge edge1 = new Edge(this.cellx0y0, this.cellx0y1);
    Edge edge2 = new Edge(this.cellx1y1, this.cellx0y2);
    Edge edge3 = new Edge(this.cellx2y0, this.cellx3y1);
    Edge edge4 = new Edge(this.cellx3y1, this.cellx3y0);
    Edge edge11 = new Edge(this.cellx0y0, this.cellx0y1);
    Edge edge22 = new Edge(this.cellx1y3, this.cellx2y3);

    ArrayList<Edge> edgeList123 = new ArrayList<Edge>();
    edgeList123.add(edge1);
    edgeList123.add(edge2);
    edgeList123.add(edge3);
    edgeList123.add(edge11);

    ArrayList<Edge> emptyEdge = new ArrayList<Edge>();

    t.checkExpect(edge1.hasEdge(edgeList123), true);
    t.checkExpect(edge3.hasEdge(edgeList123), true);
    t.checkExpect(edge2.hasEdge(edgeList123), true);
    t.checkExpect(edge4.hasEdge(edgeList123), false);
    t.checkExpect(edge1.hasEdge(emptyEdge), false);
    t.checkExpect(edge11.hasEdge(edgeList123), true);
    t.checkExpect(edge22.hasEdge(edgeList123), false);
  }

  // test onKeyEvent
  void testOnKeyEvent(Tester t) {
    new MazeWorld(5, 5).onKeyEvent("hello");
    // This method can't be tested because of the tester
    // library's definition of equality of objects.
    // Since the objects in an example graph are not the same
    // exact as those in the created graph by the method
    // the library sees them as different even if the
    // values are the same. The issue is further complicated
    // by edges being randomly assigned weights at creation.
    // The effects of on key can be seen through big bang though.
  }

  // test makeScene
  void testMakeScene(Tester t) {
    new MazeWorld(5, 5).makeScene();
    // This method cannot be tested because it heavily relies on
    // the private fields created automatically by the constructors
    // such as the edges and their weights used in the
    // algorithm. The result of make scene can be seen
    // through big bang though.
  }

  void testAllToCells(Tester t) {
    this.setEdges();

    ArrayList<MazeCell> expectedListForx0y0 = new ArrayList<>();
    expectedListForx0y0.add(cellx0y1);
    expectedListForx0y0.add(cellx1y0);
    // t.checkExpect(cellx0y0.allToCells(), expectedListForx0y0);

    ArrayList<MazeCell> expectedListForx2y2 = new ArrayList<>();
    expectedListForx2y2.add(cellx2y1);
    expectedListForx2y2.add(cellx1y2);
    expectedListForx2y2.add(cellx3y2);
    expectedListForx2y2.add(cellx2y3);
    // t.checkExpect(cellx2y2.allToCells(), expectedListForx2y2);

    ArrayList<MazeCell> expectedListForx4y4 = new ArrayList<>();
    expectedListForx4y4.add(cellx4y3);
    expectedListForx4y4.add(cellx3y4);
    // t.checkExpect(cellx4y4.allToCells(), expectedListForx4y4);
  }

  void testEquals(Tester t) {
    this.setEdges();
    MazeCell cellx1y1a = new MazeCell(1, 1);
    t.checkExpect(cellx1y1.equals(cellx1y1), true);
    t.checkExpect(cellx1y1.equals(cellx1y1a), true);
    t.checkExpect(cellx1y1.equals(cellx1y2), false);
    t.checkExpect(cellx1y1.equals(cellx3y2), false);
  }

  void testHashCode(Tester t) {
    this.setEdges();
    MazeCell cellx1y1a = new MazeCell(1, 1);
    t.checkExpect(cellx1y1.hashCode(), 1001);
    t.checkExpect(cellx1y1a.hashCode(), 1001);
    t.checkExpect(cellx1y2.hashCode(), 1002);
    t.checkExpect(cellx3y2.hashCode(), 3002);
  }

  void testGetFrom(Tester t) {
    this.setEdges();
    Edge x0y0edgex0y1 = new Edge(cellx0y0, cellx0y1, new Random(1));
    t.checkExpect(x0y0edgex0y1.getFrom(), cellx0y0);
  }

  void testGetTo(Tester t) {
    this.setEdges();
    Edge x0y0edgex0y1 = new Edge(cellx0y0, cellx0y1, new Random(1));
    t.checkExpect(x0y0edgex0y1.getTo(), cellx0y1);
  }

  void testAddTheFrom(Tester t) {
    this.setEdges();

    Edge x0y0edge01y1 = new Edge(cellx0y0, cellx0y1, new Random(1));

    ArrayList<MazeCell> allTosBefore = new ArrayList<>();
    x0y0edge01y1.addTheFrom(allTosBefore);

    ArrayList<MazeCell> allTosResult = new ArrayList<>();
    allTosResult.add(cellx0y1);

    // t.checkExpect(allTosBefore, allTosResult);
  }

  void testSameRepresentative(Tester t) {
    this.setEdges();
    Edge x0y0edge01y1 = new Edge(cellx0y0, cellx0y1, new Random(1));
    HashMap<MazeCell, MazeCell> representatives = new HashMap<>();
    representatives.put(cellx0y0, cellx0y1);
    representatives.put(cellx0y1, cellx0y1);

    t.checkExpect(x0y0edge01y1.sameRepresentative(representatives), true);
  }

  void testUnionize(Tester t) {
    this.setEdges();
    Edge x0y0edge01y1 = new Edge(cellx0y0, cellx0y1, new Random(1));

    HashMap<MazeCell, MazeCell> representativesResult = new HashMap<>();
    representativesResult.put(cellx0y0, cellx0y0);
    representativesResult.put(cellx0y1, cellx0y1);

    x0y0edge01y1.unionize(representativesResult);
    t.checkExpect(representativesResult.get(cellx0y0), cellx0y1);
    t.checkExpect(representativesResult.get(cellx0y1), cellx0y1);
  }
}