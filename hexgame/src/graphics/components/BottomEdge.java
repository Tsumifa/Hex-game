package src.graphics.components;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Dimension;


public class BottomEdge extends JButton {

    private Color color;
    private Color borderColor;

    public BottomEdge( Color bgColor, Color borderColor, String label, int btnSize ) {

        super( label );

        this.color = bgColor;
        this.borderColor = borderColor;
        
        Dimension size = getPreferredSize();
        size.width = btnSize;
        size.height = btnSize;
        setPreferredSize( size );
        setContentAreaFilled( false );
    
    }

    int edges = 6;
    int x[] = new int[edges];
    int y[] = new int[edges];
    Polygon polygon;

    protected void paintComponent( Graphics g ) {

        g.setColor(this.color);

        x[0] = (int) getSize().width; 
        y[0] = (int) Math.round( ( getSize().width / 2 ) * Math.cos( Math.PI / 3 ) );

        x[1] = (int) getSize().width / 2; 
        y[1] = 0; 
        
        x[2] = 0; 
        y[2] = (int) Math.round( ( getSize().width / 2 ) * Math.cos( Math.PI / 3) ); 
        
        x[3] = 0; 
        y[3] = (int) getSize().height * 2/ 3; 

        x[4] = (int) getSize().width; 
        y[4] = (int) getSize().height * 2/ 3;

        x[5] = (int) getSize().width; 
        y[5] = y[1]; 

        g.fillPolygon( x, y, edges );
        super.paintComponent( g );
    
    }

    protected void paintBorder( Graphics g ) {
        
        g.setColor( this.borderColor );
        g.drawPolygon( x, y, edges );
    
    }
}
