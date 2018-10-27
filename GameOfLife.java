package GameOfLife;

import java.awt.event.MouseListener;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class GameOfLife implements MouseListener, ActionListener, Runnable {

	private boolean running = false;// on&off
	public final int Height = 25;
	public final int Width = 25;
	public int speed = 500;
	JFrame frame = new JFrame("Conway's Game of Life");
	int[][] gen = new int[Width][Height];
	GOLpanel panel = new GOLpanel(gen);
	JButton start = new JButton("Start");
	JButton pause = new JButton("Pause");
	JSlider orient = new JSlider(0,1000,500);
	Container south = new Container();
	Thread thread;
	

	public GameOfLife() {
		frame.setSize(641, 641);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		south.setLayout(new GridLayout(1, 1));
		south.add(start);
		start.addActionListener(this);
		south.add(pause);
		pause.addActionListener(this);
		south.add(orient);
		orient.setMajorTickSpacing(50);
		orient.setPaintTicks(true);

		frame.add(south, BorderLayout.SOUTH);
		panel.addMouseListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new GameOfLife();
	}

//                        ********** RULES **********
//	Any live cell with fewer than two live neighbors dies, as if by under population.
//	Any live cell with two or three live neighbors lives on to the next generation.
//	Any live cell with more than three live neighbors dies, as if by overpopulation.
//	Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.	

	public void nextGen() {

		int rows = gen.length;
		int collumns = gen[0].length;
		int[][] nextGen = new int[rows][collumns];
		int sum = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < collumns; j++) {
				if (i == 0 && j == 0)// top left corner
				{
					sum = gen[i + 1][j] + gen[i][j + 1] + gen[i + 1][j + 1] + gen[rows - 1][j] + gen[rows - 1][j + 1];
				} else if (i == rows - 1 && j == 0)// top right corner
				{
					sum = gen[i - 1][j] + gen[i - 1][j + 1] + gen[i][j + 1] + gen[j][j] + gen[j][j + 1];
					// System.out.println(sum);
				} else if (i == 0 && j == collumns - 1)// bottom left corner
				{
					sum = gen[i][j - 1] + gen[i + 1][j - 1] + gen[i + 1][j] + gen[j][j] + gen[j][j - 1];
					// System.out.println(sum);
				} else if (i == rows - 1 && j == collumns - 1)// bottom right corner
				{
					sum = gen[i][j - 1] + gen[i - 1][j] + gen[i - 1][j - 1] + gen[i - j][i] + gen[i - j][i - 1];
					// System.out.println(sum);
				} else if (j == 0)// top row
				{
					sum = gen[i][rows - 1] + gen[i - 1][rows - 1] + gen[i + 1][rows - 1] + gen[i - 1][j] + gen[i + 1][j]
							+ gen[i][j + 1] + gen[i - 1][j + 1] + gen[i + 1][j + 1];
					// System.out.println(sum);
				} else if (j == rows - 1)// bottom row
				{
					sum = gen[i][j - 1] + gen[i - 1][j - 1] + gen[i + 1][j - 1] + gen[i - 1][j] + gen[i + 1][j]
							+ gen[i][rows - collumns] + gen[i - 1][rows - collumns] + gen[i + 1][rows - collumns];
					// System.out.println(sum);
				} else if (i == 0) // left collumn
				{
					sum = gen[i][j + 1] + gen[i][j - 1] + gen[i + 1][j] + gen[i + 1][j - 1] + gen[i + 1][j + 1]
							+ gen[rows - 1][j] + gen[rows - 1][j - 1] + gen[rows - 1][j + 1];
					// System.out.println(sum);
				} else if (i == rows - 1)// right collumn
				{
					sum = gen[rows - 1][j - 1] + gen[rows - 1][j + 1] + gen[i - 1][j] + gen[i - 1][j - 1]
							+ gen[i - 1][j + 1] + gen[rows - collumns][j] + gen[rows - collumns][j - 1]
							+ gen[rows - collumns][j + 1];
					// System.out.println(sum);
				} else {
					sum = gen[i][j + 1] + gen[i][j - 1] + gen[i - 1][j] + gen[i - 1][j - 1] + gen[i - 1][j + 1]
							+ gen[i + 1][j] + gen[i + 1][j - 1] + gen[i + 1][j + 1];
					// System.out.println(sum);
				}

				if (gen[i][j] == 1 && sum < 2) {
					nextGen[i][j] = 0;
				} else if (gen[i][j] == 1 && sum == 2) {
					nextGen[i][j] = 1;
				} else if (gen[i][j] == 1 && sum == 3) {
					nextGen[i][j] = 1;
				} else if (gen[i][j] == 1 && sum > 3) {
					nextGen[i][j] = 0;
				} else if (gen[i][j] == 0 && sum == 3) {
					nextGen[i][j] = 1;
				} else {
					nextGen[i][j] = gen[i][j];
				}

			}

		}
		gen = nextGen;
		panel.setGen(nextGen);
		frame.repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// System.out.println(e.getX() + "_" + e.getY());
		int row = e.getY() / (panel.getHeight() / Height);//
		int col = e.getX() / (panel.getWidth() / Width);
		gen[col][row] = 1;// if clicked becomes a live cell
		frame.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(start) == true) {
			thread = new Thread(this);
			running = true;
			thread.start();
			((AbstractButton) e.getSource()).setEnabled(false);
		} else if (e.getSource().equals(pause) == true) {

			running = false;
			start.setEnabled(true);
		}

	}

	@Override
	public void run() {
		while (running) {
			speed = orient.getValue();
			nextGen();
			try {

				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			frame.repaint();
		}

	}

}
