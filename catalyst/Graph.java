// A Java program to implement greedy algorithm for graph coloring
import java.io.*;
import java.util.*;
import java.util.LinkedList;

// This class represents an undirected graph using adjListacency list
public class Graph
{
	private int numOfVertices; // No. of vertices
	private LinkedList<Integer> adjList[]; //Adjacency List

	//Constructor
	Graph(int v)
	{
		numOfVertices = v;
		adjList = new LinkedList[v];
		for (int i=0; i<v; ++i)
			adjList[i] = new LinkedList();
	}

	//Function to add an edge into the graph
	void insertEdge(int v,int w)
	{
		adjList[v].add(w);
		adjList[w].add(v); //Graph is undirected
	}

	// Assigns colors (starting from 0) to all vertices and
	// prints the assignment of colors
	void graphColouring()
	{
		int arr[] = new int[numOfVertices];

		// Initialize all vertices as unassigned
		Arrays.fill(arr, -1);

		// Assign the first color to first vertex
		arr[0] = 0;

		// A temporary array to store the available colors. False
		// value of available[cr] would mean that the color cr is
		// assigned to one of its adjListacent vertices
		boolean available[] = new boolean[numOfVertices];
		
		// Initially, all colors are available
		Arrays.fill(available, true);

		// Assign colors to remaining numOfVertices-1 vertices
		for (int u = 1; u < numOfVertices; u++)
		{
			// Process all adjListacent vertices and flag their colors
			// as unavailable
			Iterator<Integer> it = adjList[u].iterator() ;
			while (it.hasNext())
			{
				int i = it.next();
				if (arr[i] != -1)
					available[arr[i]] = false;
			}

			// Find the first available color
			int cr;
			for (cr = 0; cr < numOfVertices; cr++){
				if (available[cr])
					break;
			}

			arr[u] = cr; // Assign the found color

			// Reset the values back to true for the next iteration
			Arrays.fill(available, true);
		}

		// print the arr
		for (int u = 0; u < numOfVertices; u++)
			System.out.println("numOfVerticesertex " + u + " ---> Color "
								+ arr[u]);
	}

	// Driver method
	public static void main(String args[])
	{
		Graph g1 = new Graph(5);
		g1.insertEdge(0, 1);
		g1.insertEdge(1, 2);
		g1.insertEdge(0, 2);
		g1.insertEdge(1, 3);
		g1.insertEdge(0, 4);
		System.out.println("Coloring of graph 1");
		g1.graphColouring();

	}
}