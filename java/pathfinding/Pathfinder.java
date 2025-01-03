package pathfinding;

import java.util.ArrayList;

import entity.Entity;
import rpg_sequel.GamePanel;

public class Pathfinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public Pathfinder(GamePanel gp) {

        this.gp = gp;
        instantiateNodes();
    }

    public void instantiateNodes() {

        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            
            node[col][row] = new Node(col, row);

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {
        
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
    
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNode(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {

        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            
            //Set solid node
            int tileNum = gp.tilesM.mapTilesNum[col][row];
            if(gp.tilesM.tile[tileNum].collision == true) {
                node[col][row].solid = true;
            }
 
            getCost(node[col][row]);

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }
    public void getCost (Node node) {

        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.hCost + node.gCost;
    }
    public boolean search() {

        while (goalReached == false && step < 500) {
            
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            pathList.add(currentNode);
            openList.remove(currentNode);

            // Open Up node
            if(row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }

            // Open left node
            if(col - 1 >= 0) { 
                openNode(node[col - 1][row]);
            }

            // Open down node
            if(row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]);
            }

            // Open right node
            if(col + 1 < gp.maxWorldCol) {
                openNode(node[col + 1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++){

                if(openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            //if no node, finish
            if(openList.size() == 0) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node) {

        if(node.open == false && node.checked == false && node.solid == false) {

            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    public void trackThePath() {

        Node current = goalNode;
        
        while (current != startNode) {
            
            pathList.add(0, current);
            current = current.parent;
        } 
    }
}

