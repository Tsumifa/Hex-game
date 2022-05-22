package src.graphics.components;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Dimension;


// Cette class donne une forme d'hexagone au boutton
// Elle applique une forme (composant graphic et polygon) au boutton 
// Inspiré du cpde : https://harryjoy.me/2011/08/21/different-button-shapes-in-swing/
public class Tiles extends JButton {

    private Color color;
    private Color borderColor;
    public int row;
    public int col;


    public Tiles ( int btnSize, Color color, Color borderColor, String text, int row, int col ) {
        
        super( text );
        Dimension size = getPreferredSize();
        size.width = btnSize;
        size.height = btnSize;
        setPreferredSize( size );
        setContentAreaFilled( false );

        this.row = row;
        this.col = col;
        this.color = color;
        this.borderColor = borderColor;
    }


    int edges = 6;
    int x[] = new int[edges];
    int y[] = new int[edges];
    Polygon polygon;
    

    // Applique la forme hexagonale au boutton
    // Applique la couleur de fond
    // Les tableaux x et y contiennent les coordonnées des sommets de l'hexagone
    protected void paintComponent( Graphics g ) {

        g.setColor( this.color );

        x[0] = (int) getSize().width / 2; 
        y[0] = (int) getSize().height;

        x[1] = 0; 
        y[1] = (int) Math.round( ( getSize().height -  ( getSize().width / 2 ) * Math.cos( Math.PI / 3 ) ) ); 
        
        x[2] = 0; 
        y[2] = (int) Math.round( ( getSize().width / 2 ) * Math.cos( Math.PI / 3) ); 
        
        x[3] = (int) getSize().width / 2; 
        y[3] = 0; 

        x[4] = (int) getSize().width; 
        y[4] = y[2];

        x[5] = (int) getSize().width; 
        y[5] = y[1]; 

        g.fillPolygon( x, y, edges );
        super.paintComponent( g );

    }
    

    // Dessine les bordures
    // Applique la couleur de bordure
    protected void paintBorder( Graphics g ) {

        g.setColor( this.borderColor );
        g.drawPolygon( x, y, edges );

    }

    
    // Retounre true si le point M(x1, y1) est dans le boutton
    // Retourne false sinon
    public boolean contains( int x1, int y1  ) {
        
        if ( polygon == null || !polygon.getBounds().equals( getBounds() ) ) {
            polygon = new Polygon( x, y, edges );
        }
        return polygon.contains( x1, y1 );

    }

    // Modifie la couleur de la case
    public void updateTileColor( Color c ) {

        this.color = c;
        Graphics g = this.getGraphics();
        g.setColor( c );
        g.fillPolygon( x, y, edges );
        super.paintComponent( g );
    
    }
}
