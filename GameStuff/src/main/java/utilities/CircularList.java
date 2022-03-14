package utilities;

import blocks.Fighter;

public class CircularList {

	private Node head = null;
	private Node tail = null;
	private Node nextTurn = null;

	public void addNode(Fighter value){
		Node newNode = new Node(value);

		if(head == null){
			head = newNode;
			nextTurn = newNode;
		} else {
			tail.nextNode = newNode;
		}

		tail = newNode;
		tail.nextNode = head;
	}

	public void deleteNode(Fighter dead){
		Node currentNode = head;

		do {
			Node nextNode = currentNode.nextNode;
			if(nextNode.value.equals(dead)){
				currentNode.nextNode = nextNode.nextNode;
				if(head.equals(nextNode)){
					head = head.nextNode;
				}
				if(tail.equals(nextNode)){
					tail = currentNode;
				}
				return;
			}
			currentNode = currentNode.nextNode;
		} while(currentNode != head);
	}

	public Fighter getNextTurn(){
		Fighter next = nextTurn.value;
		nextTurn = nextTurn.nextNode;
		return next;
	}
}
