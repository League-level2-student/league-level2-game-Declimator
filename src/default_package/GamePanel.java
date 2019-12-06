package default_package;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	String GameState = "Game";
	long updateTimer = 0;
	int TimeToUpdate = 1;
	GameObject object;
	Grid grid;
	Timer t = new Timer(1000 / 3, this);

	public GamePanel() {
		t.start();
		grid = new Grid(10, 25);
		object = new LineBlock(grid);
	}

	public void paintComponent(Graphics g) {
		if (GameState.equalsIgnoreCase("Lose")) {
			System.out.println("lose");
			grid.end(g);
		} else {
			grid.update(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (GameState.equalsIgnoreCase("Game")) {
			object.update("check");

			if (object.isActive == true) {
				object.update("down");
				object.update("check");

			} else {

				for (int i = 0; i < 4; i++) {
					if (object.yPos[i] <= 0 && grid.grid[object.xPos[i]][object.yPos[i]]) {
						GameState = "Lose";
					}
				}
				for (int i = 0; i < 4; i++) {
					grid.grid[object.xPos[i]][object.yPos[i]] = true;
				}
				int r = new Random().nextInt(7);
				object.rotation = 0;
				if (r == 0) {

					object = new LineBlock(grid);
					object.currentState = "line";

				} else if (r == 1) {

					object = new SquareBlock(grid);
					object.currentState = "square";

				} else if (r == 2) {

					object = new SBlock(grid);
					object.currentState = "s";

				} else if (r == 3) {

					object = new ZBlock(grid);
					object.currentState = "z";

				} else if (r == 4) {

					object = new JBlock(grid);
					object.currentState = "j";

				} else if (r == 5) {

					object = new LBlock(grid);
					object.currentState = "l";

				} else if (r == 6) {

					object = new TBlock(grid);
					object.currentState = "t";

				}
			}
			repaint();
		}
	}

	// TODO Auto-generated method stub
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		object.update("check");
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT && object.canMoveLeft == true) {
			object.update("check");
			if (object.canMoveLeft == true) {
				if (object.xPos[0] >= 1 && object.xPos[1] >= 1 && object.xPos[2] >= 1 && object.xPos[3] >= 1) {
					object.moveLeft();
				}

			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && object.canMoveRight == true) {
			object.update("check");
			if (object.canMoveRight == true) {
				if (object.xPos[0] <= 8 && object.xPos[1] <= 8 && object.xPos[2] <= 8 && object.xPos[3] <= 8) {
					object.moveRight();
				}

			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			object.update("down");
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			object.update("space");
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			for (int i = 0; i < 4; i++) {
					object.checkLeft();
					object.checkRight();
					if(object.canRotate == true) {
					object.update("r");
					}
					break;
				
			}

		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
