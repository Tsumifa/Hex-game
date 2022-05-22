package src.graphics.components;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Dimension;

public class RightEdge extends JButton {

    private Color color;
    private Color borderColor;

    public RightEdge( Color bgColor, Color borderColor, String label, int btnSize ) {
        
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

        g.setColor( this.color );

        x[0] = (int) getSize().width / 2; 
        y[0] = (int) Math.round( (int) Math.round( ( getSize().width / 2 ) * Math.cos( Math.PI / 3 ) ) );

        x[1] = 0; 
        y[1] = (int) Math.round( (int) Math.round( ( getSize().width / 2 ) * Math.cos( Math.PI / 3 ) ) ); 
        
        x[2] = 0; 
        y[2] = (int) Math.round( getSize().width - ( getSize().width / 2 ) * Math.cos( Math.PI / 3 ) ); 
        
        x[3] = (int) getSize().width / 2; 
        y[3] =  (int) getSize().height; 

        x[4] = (int) getSize().width; 
        y[4] = (int) getSize().height;

        x[5] = (int) getSize().width / 2; 
        y[5] = (int) Math.round( (int) Math.round( ( getSize().width / 2 ) * Math.cos( Math.PI / 3 ) ) ); 


        g.fillPolygon( x, y, edges );
        super.paintComponent( g );

    }

    protected void paintBorder( Graphics g ) {

        g.setColor(this.borderColor);
        g.drawPolygon(x, y, edges);
    
    }
}
