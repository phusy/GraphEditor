package graph.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;

public class Vertex implements Serializable{
	RectangularShape shape;
	String label;
	int form;
	boolean initial=false, terminal=false;

	Vertex(RectangularShape rs, String label, int form) {
		this.shape = rs;
		this.label = label;
		this.form = form;
	}
	
	public boolean initial() {
		return initial;
	}
	
	public void setInitial(boolean initial){
		this.initial = initial;
	}

	public boolean terminal() {
		return terminal;
	}
	
	public void setTerminal(boolean terminal){
		this.terminal = terminal;
	}

	public RectangularShape getShape() {
		return shape;
	}

	public void setShape(RectangularShape shape) {
		this.shape = shape;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getForm() {
		return form;
	}

	public void setForm(int form) {
		this.form = form;
	}
	
	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}

	void draw(Graphics2D g2) {
		Color current = g2.getColor(); 
				
		g2.fill(shape);
		g2.setColor(Color.WHITE);
		int width = g2.getFontMetrics().stringWidth(label);
	    g2.drawString(label, (int)(shape.getCenterX() - width/2) ,(int) (shape.getCenterY() + 5));
	    
	    g2.setColor(current);
	    if(initial){   	
	    	int x = (int) shape.getMinX();
			int y = (int) shape.getCenterY();
			g2.drawLine(x - 5, y - 5, x, y);
			g2.drawLine(x - 5, y + 5, x, y);
			g2.drawLine(x - 40, y, x, y);	
	    }
	    else if(terminal){
	    	int radius = (int) shape.getWidth()/2 + 5;
	    	if(form == 0) 
	    		g2.drawOval((int)shape.getCenterX() - radius, (int)shape.getCenterY() - radius ,radius*2, radius*2);
	    	else
	    		g2.drawRect((int)shape.getCenterX() - radius, (int)shape.getCenterY() - radius ,radius*2, radius*2);
	    }
	}
}
