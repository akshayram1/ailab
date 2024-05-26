import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class MissionariesAndCannibals {
	
	HashMap<ArrayList<Integer>, HashSet<ArrayList<Integer>>> map = new HashMap<ArrayList<Integer>, HashSet<ArrayList<Integer>>>();
	Queue<ArrayList<Integer>> searchSpace = new LinkedList<ArrayList<Integer>>();
	public int MISSIONARIES;
	public int CANNIBALS;
	
	
	/**
	 * Takes in an input of missionaries and cannibals, and tells the user all the possible states, and all the states connected to those possible states
	 * The output is formatted as so
	 * [state]: (possible connecting state 1) (possible connecting state 2) ect...
	 * The program also says if there's a possible end state where all the people reach the other end of the river
	 * @param missioanries the number of starting missionaries
	 * @param cannibals the number of starting cannibals
	 */
	public MissionariesAndCannibals(int missioanries, int cannibals) {
		MISSIONARIES = missioanries;
		CANNIBALS = cannibals;
		
		ArrayList<Integer> initalState = new ArrayList<Integer>();
		initalState.add(MISSIONARIES);
		initalState.add(CANNIBALS);
		initalState.add(1);
		searchSpace.add(initalState);
		map.put(initalState, new HashSet<ArrayList<Integer>>());
		int nextSide = -1;
		while(!searchSpace.isEmpty()) {
			ArrayList<Integer> currentState = searchSpace.poll();
			if(currentState.get(2) == 0)
				nextSide = 1;
			else
				nextSide = 0;
			for (int i = 0; i <= MISSIONARIES; i++) {
				for (int j = 0; j <= CANNIBALS; j++) {
					if(possibleState(currentState,i,j) && validState(i,j) && !(i == currentState.get(0) && j == currentState.get(1))){
						ArrayList<Integer> newSpace = new ArrayList<Integer>();
						newSpace.add(i);
						newSpace.add(j);
						newSpace.add(nextSide);
						// System.out.print("Adding: ");
						// for (int num : newSpace){
						// 	System.out.print(num + " ");
						// }
						// System.out.print(" for previous state:");
						// for (int num : currentState){
						// 	System.out.print(num + " ");
						// }
						// System.out.println();
						
						if(map.get(newSpace) == null) {
//							System.out.println("state does not exist, adding");
							HashSet<ArrayList<Integer>> toAdd = new  HashSet<ArrayList<Integer>>();
							toAdd.add(currentState);
							map.put(newSpace, toAdd);
							searchSpace.add(newSpace);
						} else {
//							System.out.println("Found existing");
							map.get(newSpace).add(currentState);
						}
					}
				}
				
			}
			
		}
		for (ArrayList<Integer> key : map.keySet()) {
			System.out.print("[ ");
			for (int num : key){
				System.out.print(num + " ");
			}
			System.out.print("]: ");
			for (ArrayList<Integer> value : map.get(key)){
				System.out.print("(");
				for (int value_num : value){
					System.out.print(value_num + " ");
				}
				System.out.print(") ");
			}
			System.out.println();
		}
	
		ArrayList<Integer> testGoal = new ArrayList<Integer>();
		testGoal.add(0);
		testGoal.add(0);
		testGoal.add(0);
		if (map.get(testGoal) != null) {
			System.out.println("Has possible goal state");
		} else
			System.out.println("No possible goal state");
	}

	/**
	 * Returns if this state is possible to get to from the current state(that is, only less than 2 difference from the current state)
	 */
	private boolean possibleState(ArrayList<Integer> currentState, int i, int j) {
		if (Math.abs(currentState.get(0) - i) + Math.abs(currentState.get(1) - j) <= 2)
			if (currentState.get(2) == 1) {
				return (i <= currentState.get(0) && j <= currentState.get(1));
			} else { //the side is 0
				return (i >= currentState.get(0) && j >= currentState.get(1));
			}
		return false;
	}
	/**
	 * Returns true if this state is a valid state(that is, missionaries will not get eaten)
	 */
	private boolean validState(int i, int j) {
		if ((i >= j || i == 0) && ((MISSIONARIES - i >= CANNIBALS - j) || (MISSIONARIES - i == 0))) //check if left side is valid AND right side is valid
			return true;
			else
			return false;
	}
	public static void main(String[] args) {
		new MissionariesAndCannibals(3,2);
	}

}