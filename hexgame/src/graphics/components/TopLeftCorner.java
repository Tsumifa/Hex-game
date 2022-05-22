package src.graphics.components;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Dimension;

public class TopLeftCorner extends JButton {

    private Color color;
    private Color borderColor;

    public TopLeftCorner( Color bgColor, Color borderColor, String label, int btnSize ) {

        super( label );

        this.color = bgColor;
        this.borderColor = borderColor;
        
        Dimension size = getPreferredSize();
        size.width = btnSize;
        size.height = btnSize;
        setPreferredSize( size );
        setContentAreaFilled( false );

    }

    int edges = 4;
    int x[] = new int[edges];
    int y[] = new int[edges];
    Polygon polygon;

    protected void paintComponent( Graphics g ) {

        g.setColor( this.color );

        x[0] = 0;
        y[0] = 0;

        x[1] = 0;
        y[1] = (int) getSize().height / 2;

        x[2] = (int) getSize().width / 2;
        y[2] = (int) getSize().height / 2;

        x[3] = (int) getSize().width / 2;
        y[3] = 0;

        g.fillPolygon( x, y, edges );
        super.paintComponent( g );

    }

    protected void paintBorder( Graphics g ) {
        
        g.setColor( this.borderColor );
        g.drawPolygon( x, y, edges );
    
    }
}
