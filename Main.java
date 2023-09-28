package main;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

class Vertices{
	double x, y;
	public Vertices(double x, double y) {
		this.x = x;
		this.y = y;
	}
	void print() {
		System.out.print("("+x+","+y+")");
	}
}

class Edges{
	Vertices v1, v2;
	public Edges(Vertices v1, Vertices v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
}


public class Main {
	static int graphCount = 1;
	
	public static void printGraph(ArrayList<Edges> graph, HashMap<Vertices, Integer> vertexes) {
		System.out.print("Graph "+graphCount+" :");
		for(int i = 0; i < graph.size(); i++) {
			System.out.print("[");
			graph.get(i).v1.print() ;
			System.out.print(" --- ");
			graph.get(i).v2.print();
			System.out.print("] ");
		}
		for(Vertices v : vertexes.keySet()) {
			v.print();
		}
		System.out.println();
		graphCount++;
	}
	
	public static void findGraphs(int index, ArrayList<Edges> edgeList, ArrayList<Edges> edges, ArrayList<ArrayList<Edges>> graphs){
		if(index == edgeList.size()) {
			graphs.add(edges);
			return;			
		}
		
		ArrayList<Edges> e1 = new ArrayList<>();
		for(int i = 0; i < edges.size(); i++) e1.add(edges.get(i));
		findGraphs(index + 1, edgeList, edges, graphs);
		e1.add(edgeList.get(index));
		findGraphs(index + 1, edgeList, e1, graphs);
	}
	
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		// Number of vertices/points
		System.out.print("Enter no. of vertices : ");
		int coordinates = s.nextInt();
		
		// List of vertices
		ArrayList<Vertices> coordinateList = new ArrayList<>();
		
		//
		HashMap<Vertices, Integer> coordinateMap = new HashMap<>();
		//List of All Possible edges
		ArrayList<Edges> edgeList = new ArrayList<>();
		
		// Input for all coordinates of vertices
		for(int i = 0; i < coordinates; i++) {			
			double x = s.nextDouble();
			double y = s.nextDouble();
			coordinateList.add(new Vertices(x, y));
			coordinateMap.put(new Vertices(x, y), 0);
		}
		s.close();
		
		// Populating all edges amongst the vertices
		System.out.println("All Possible Edges :: ");
		for(int i = 0; i < coordinates-1; i++) {
			for(int j = i + 1; j < coordinates; j++) {
				coordinateList.get(i).print();
				System.out.print(" --> ");
				coordinateList.get(j).print();
				System.out.println();
				edgeList.add(new Edges(coordinateList.get(i), coordinateList.get(j)));
				
			}
		}
		System.out.println();
		
		// Finding all possible graphs 
		ArrayList<ArrayList<Edges>> graphs = new ArrayList<>();
		findGraphs(0, edgeList, new ArrayList<Edges>(), graphs);
		Collections.sort(graphs, new Comparator<ArrayList<Edges>>() {
            @Override
            public int compare(ArrayList<Edges> list1, ArrayList<Edges> list2) {
                // Compare the sizes of the two ArrayLists
                return Integer.compare(list1.size(), list2.size());
            }
        });
		
		int  si[] = new int[edgeList.size()+1];
		System.out.println("All possible Graphs : ");
		for(int i = 0 ; i < graphs.size(); i++) {
			HashMap<Vertices, Integer> graph = new HashMap<>();
			
			//Populating with vertices
			for(int m = 0; m < coordinateList.size(); m++) graph.put(new Vertices(coordinateList.get(m).x, coordinateList.get(m).y), 0);
			
			
			for(int j = 0; j < graphs.get(i).size(); j++) {
				ArrayList<Vertices> tobeRemoved = new ArrayList<>();
				for(Vertices v : graph.keySet()) {
					if(v.x == graphs.get(i).get(j).v1.x && v.y == graphs.get(i).get(j).v1.y) tobeRemoved.add(v);
					if(v.x == graphs.get(i).get(j).v2.x && v.y == graphs.get(i).get(j).v2.y) tobeRemoved.add(v);
				}
				for(Vertices vR : tobeRemoved) graph.remove(vR);
			}
			
			
			
			printGraph(graphs.get(i), graph);
		
			si[graphs.get(i).size()]++;
		}
		for(int l =0 ; l <= edgeList.size(); l++)System.out.println("No of size "+l+" graph : "+si[l]+" ");
		System.out.println("Total No of Graphs :"+ graphs.size());
	}
}


// :::::: OUTPUT ::::::
/*
	Enter no. of vertices : 4
	1.0 1.0
	2.0 2.0
	3.0 3.0
	4.0 4.0
	All Possible Edges :: 
	(1.0,1.0) --> (2.0,2.0)
	(1.0,1.0) --> (3.0,3.0)
	(1.0,1.0) --> (4.0,4.0)
	(2.0,2.0) --> (3.0,3.0)
	(2.0,2.0) --> (4.0,4.0)
	(3.0,3.0) --> (4.0,4.0)
	
	All possible Graphs : 
	Graph 1 :(1.0,1.0)(2.0,2.0)(3.0,3.0)(4.0,4.0)
	Graph 2 :[(3.0,3.0) --- (4.0,4.0)] (1.0,1.0)(2.0,2.0)
	Graph 3 :[(2.0,2.0) --- (4.0,4.0)] (1.0,1.0)(3.0,3.0)
	Graph 4 :[(2.0,2.0) --- (3.0,3.0)] (4.0,4.0)(1.0,1.0)
	Graph 5 :[(1.0,1.0) --- (4.0,4.0)] (2.0,2.0)(3.0,3.0)
	Graph 6 :[(1.0,1.0) --- (3.0,3.0)] (4.0,4.0)(2.0,2.0)
	Graph 7 :[(1.0,1.0) --- (2.0,2.0)] (4.0,4.0)(3.0,3.0)
	Graph 8 :[(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] (1.0,1.0)
	Graph 9 :[(2.0,2.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] (1.0,1.0)
	Graph 10 :[(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] (1.0,1.0)
	Graph 11 :[(1.0,1.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] (2.0,2.0)
	Graph 12 :[(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (4.0,4.0)] (3.0,3.0)
	Graph 13 :[(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] 
	Graph 14 :[(1.0,1.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] (2.0,2.0)
	Graph 15 :[(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 16 :[(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (3.0,3.0)] (4.0,4.0)
	Graph 17 :[(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] (2.0,2.0)
	Graph 18 :[(1.0,1.0) --- (2.0,2.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 19 :[(1.0,1.0) --- (2.0,2.0)] [(2.0,2.0) --- (4.0,4.0)] (3.0,3.0)
	Graph 20 :[(1.0,1.0) --- (2.0,2.0)] [(2.0,2.0) --- (3.0,3.0)] (4.0,4.0)
	Graph 21 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (4.0,4.0)] (3.0,3.0)
	Graph 22 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] (4.0,4.0)
	Graph 23 :[(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] (1.0,1.0)
	Graph 24 :[(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 25 :[(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 26 :[(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 27 :[(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 28 :[(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 29 :[(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 30 :[(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] (2.0,2.0)
	Graph 31 :[(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 32 :[(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] 
	Graph 33 :[(1.0,1.0) --- (2.0,2.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 34 :[(1.0,1.0) --- (2.0,2.0)] [(2.0,2.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 35 :[(1.0,1.0) --- (2.0,2.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 36 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 37 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (4.0,4.0)] (3.0,3.0)
	Graph 38 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] 
	Graph 39 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 40 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 41 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (3.0,3.0)] (4.0,4.0)
	Graph 42 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] 
	Graph 43 :[(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 44 :[(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 45 :[(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 46 :[(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 47 :[(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 48 :[(1.0,1.0) --- (2.0,2.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 49 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 50 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 51 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 52 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 53 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 54 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 55 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 56 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 57 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] 
	Graph 58 :[(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 59 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 60 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 61 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 62 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(3.0,3.0) --- (4.0,4.0)] 
	Graph 63 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] 
	Graph 64 :[(1.0,1.0) --- (2.0,2.0)] [(1.0,1.0) --- (3.0,3.0)] [(1.0,1.0) --- (4.0,4.0)] [(2.0,2.0) --- (3.0,3.0)] [(2.0,2.0) --- (4.0,4.0)] [(3.0,3.0) --- (4.0,4.0)] 
	No of size 0 graph : 1 
	No of size 1 graph : 6 
	No of size 2 graph : 15 
	No of size 3 graph : 20 
	No of size 4 graph : 15 
	No of size 5 graph : 6 
	No of size 6 graph : 1 
	Total No of Graphs :64
*/
 

