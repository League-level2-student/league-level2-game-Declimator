package default_package;

import java.awt.Color;
import java.awt.Font;
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
	//https://ia800504.us.archive.org/33/items/TetrisThemeMusic/Tetris.mp3
	//https://github.com/dencee/Level2GameTemplate/blob/master/src/Audio.java
	String GameState = "Game";
	long updateTimer = 0;
	int TimeToUpdate = 1;
	int streak = 0;
	boolean tetrisstreak;
	GameObject object;
	Grid grid;
	String Next = "blank";
	String Hold = "blank";
	String Hold2 = "blank";
	Font font;
	int streakcolor;
	int Score = 0;
	boolean canHold;
	int endy;
	Timer t = new Timer(1000 / 3, this);
	int Level;
	int clears = 0;

	public GamePanel() {
		
		font = new Font("Arial", Font.BOLD, 24);
		t.start();
		Level = 1;
		grid = new Grid(10, 26);
		object = new LineBlock(grid);
		object.currentState = "line";
		canHold = true;
		Next = NewBlock();
		endy = MainClass.frameheight;
	}

	public void paintComponent(Graphics g) {
		g.drawString("", 100, 100);
		if (GameState.equalsIgnoreCase("Lose")) {
			g.setColor(Color.RED);
			g.fillRect(0, 0, MainClass.framewidth, MainClass.frameheight);
			if (endy > 0) {
				end(g);
			} else {
				g.setColor(Color.RED);
				g.fillRect(0, 0, MainClass.framewidth, MainClass.frameheight);
			}
		} else {
			if (clears >= 5) {
				Level++;
				clears = 0;
				t.stop();
				t = new Timer(1000 / (3 + (2 * Level / 5)), this);
				t.start();
			}

			grid.update(g, Hold, Next, font, Score, Level, 5 - clears, tetrisstreak, streakcolor);

		}
	}

	public void end(Graphics g) {
		grid.update(g, Hold, Next, font, Score, Level, clears, tetrisstreak, streakcolor);
		g.setColor(Color.RED);
		g.fillRect(0, endy, MainClass.framewidth, MainClass.frameheight);
	}

	public String NewBlock() {
		String block;
		int r = new Random().nextInt(7);
		object.rotation = 0;
		for (int i = 0; i < grid.height; i++) {
			if (grid.checkRow(i) == true) {
				grid.clear(i);
				clears++;
				streak++;
				System.out.println(streak);
			}
		}
		System.out.println("test");
		if (streak > 0) {
			if (streak == 4) {
				System.out.println("tetris");
					if (tetrisstreak == false) {
						Score += 800;
					} else if (tetrisstreak == true) {
						Score += 1200;
					}
					streak=0;
				tetrisstreak = true;
			} else {
				for (int i = 0; i < streak; i++) {
					Score += 100;
					streak--;
					if (streak == 0) {
						break;
					}
				}
				tetrisstreak = false;
			}
		}
		if (r == 0) {

			block = "line";

		} else if (r == 1) {

			block = "square";

		} else if (r == 2) {

			block = "s";

		} else if (r == 3) {

			block = "z";

		} else if (r == 4) {

			block = "j";

		} else if (r == 5) {

			block = "l";

		} else {

			block = "t";

		}
		return block;
	}

	public void SetBlock() {
		if (object.currentState.equalsIgnoreCase("t")) {
			object = new TBlock(grid);
			object.currentState = "t";
		}
		if (object.currentState.equalsIgnoreCase("line")) {
			object = new LineBlock(grid);
			object.currentState = "line";
		}
		if (object.currentState.equalsIgnoreCase("l")) {
			object = new LBlock(grid);
			object.currentState = "l";
		}
		if (object.currentState.equalsIgnoreCase("j")) {
			object = new JBlock(grid);
			object.currentState = "j";
		}
		if (object.currentState.equalsIgnoreCase("z")) {
			object = new ZBlock(grid);
			object.currentState = "z";
		}
		if (object.currentState.equalsIgnoreCase("s")) {
			object = new SBlock(grid);
			object.currentState = "s";
		}
		if (object.currentState.equalsIgnoreCase("square")) {
			object = new SquareBlock(grid);
			object.currentState = "square";
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (GameState.equalsIgnoreCase("Lose")) {
			for (int i = 0; i < 5; i++) {
				endy -= 5;
				repaint();
			}
		}
		if (GameState.equalsIgnoreCase("Game")) {

			object.update("check");

			if (object.isActive == true) {
				object.update("down");
				object.update("check");

			} else {

				for (int i1 = 0; i1 < 9; i1++) {
					if (grid.grid[i1][0]) {
						for (int i = 0; i < 4; i++) {
							if (object.xPos[i] == i1 && object.isActive == false) {
								GameState = "Lose";
								break;
							} else {

							}
						}
					}
				}
				/*
				 * for (int i = 0; i < 4; i++) { if (object.yPos[i] <= 1 &&
				 * grid.grid[object.xPos[i]][object.yPos[i]]) { GameState = "Lose"; } }
				 */
				for (int i = 0; i < 4; i++) {
					grid.grid[object.xPos[i]][object.yPos[i]] = true;
				}
				if (Next.equalsIgnoreCase(null)) {
				}
				if (Next.equalsIgnoreCase("blank")) {
					Next = NewBlock();
				}
				object.currentState = Next;
				Next = NewBlock();
				SetBlock();
				canHold = true;
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
		if (GameState.equalsIgnoreCase("Game")) {
			object.update("check");
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_LEFT && object.canMoveLeft == true) {
				object.update("check");
				if (object.canMoveLeft == true) {
					object.moveLeft();

				}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && object.canMoveRight == true) {
				object.update("check");
				if (object.canMoveRight == true) {
					object.moveRight();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				object.update("down");
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				object.update("space");
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				for (int i = 0; i < 4; i++) {
					object.checkLeft();
					object.checkRight();
					if (object.canRotate == true) {
						object.update("r");
					}
					break;
				}

			} else if (e.getKeyCode() == KeyEvent.VK_C) {
				if (canHold == true) {
					for (int i = 0; i < 4; i++)
						object.grid.grid[object.xPos[i]][object.yPos[i]] = false;
					if (Next.equalsIgnoreCase("blank")) {
						Next = NewBlock();
					}
					if (Hold.equalsIgnoreCase("blank")) {
						Hold = object.currentState;
						Hold2 = object.currentState;
						object.currentState = Next;
						Next = NewBlock();
						SetBlock();
					} else {
						Hold2 = Hold;
						Hold = object.currentState;
						object.currentState = Hold2;
						Hold2 = Hold;
						SetBlock();
					}
					canHold = false;
					for (int i = 0; i < 4; i++)
						object.grid.grid[object.xPos[i]][object.yPos[i]] = true;
				}
			}
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
