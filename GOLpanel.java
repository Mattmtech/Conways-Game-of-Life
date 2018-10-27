package GameOfLife;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GOLpanel extends JPanel {
	int[][] gen;
	public static Color green = new Color(4325120);

	public GOLpanel(int[][] gen2) {
		gen = gen2;
	}

	public void setGen(int[][] nextGen) {
		gen = nextGen;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);// repaints
		g.setColor(Color.black);
		g.fillRect(0, 0, 641, 641);
		int cellWidth = this.getWidth() / gen.length;
		int cellHeight = this.getHeight() / gen.length;

//to draw grid lines:
//	for (int i = 0; i < gen.length; i++) 
//	{
//	    g.drawLine(0, i*cellHeight, this.getWidth(), i*cellHeight);
//		g.drawLine(i * cellWidth , 0, i* cellWidth, this.getHeight());	
//	}
		for (int i = 0; i < gen.length; i++) {
			for (int j = 0; j < gen.length; j++) {
				if (gen[i][j] == 1) {
					g.setColor(green);
					g.fillRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
					//

				}
			}
		}

	}

}
