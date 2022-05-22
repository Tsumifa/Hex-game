package src.graphics;

import src.config.Config;
import src.graphics.components.Tiles;
import src.graphics.components.TopEdge;
import src.graphics.components.BottomEdge;
import src.graphics.components.LeftEdge;
import src.graphics.components.RightEdge;
import src.graphics.components.TopLeftCorner;
import src.graphics.components.BottomRightCorner;
import src.graphics.components.TopRightCorner;
import src.graphics.components.BottomLeftCorner;
import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.Dimension;

public class Board  extends JPanel {

    public Tiles[][] board; 
    public TopEdge[] topEdges;
    public BottomEdge[] bottomEdges;
    public LeftEdge[] leftEdges;
    public RightEdge[] rightEdges;
    private Config configManager;
    private StylesManager stylesManager = new StylesManager();
    private Insets in;

    public Board( Config configManager ) {

        setLayout( null );
        this.configManager = configManager;
        this.board = new Tiles[this.configManager.getIntValue( "boardSize" )][this.configManager.getIntValue( "boardSize" )];
        this.topEdges = new TopEdge[this.configManager.getIntValue( "boardSize" )];
        this.bottomEdges = new BottomEdge[this.configManager.getIntValue( "boardSize" )];
        this.leftEdges = new LeftEdge[this.configManager.getIntValue( "boardSize" )];
        this.rightEdges = new RightEdge[this.configManager.getIntValue( "boardSize" )];
        this.in = getInsets();
        createBoard();

        setSize(
            this.configManager.getIntValue( "boardSize" ) * this.configManager.getIntValue( "btnSize" ) + 5 * this.configManager.getIntValue( "btnSize" )  + 150,
            ( this.configManager.getIntValue( "boardSize" ) - 2 ) * this.configManager.getIntValue( "btnSize" ) - this.configManager.getIntValue( "btnSize" ) / 2 + 150
        );

        drawComponents();

    }

    // Création des cases
    private void createBoard() {

        for ( int i = 0; i < this.board.length; i++ ) {
        
            for ( int j = 0; j < this.board.length; j++ ) {
        
                this.board[i][j] = new Tiles(
                    this.configManager.getIntValue( "btnSize" ),
                    this.stylesManager.convertToColor(
                        this.configManager.getStringValue( "tileColor" )
                    ),
                    this.stylesManager.convertToColor(
                        this.configManager.getStringValue( "tileBorderColor" )
                    ),
                    "",
                    i,
                    j
                );
            }
        }

    } 
    
    // Génère le composant plateau
    private void drawComponents() {
        Dimension size;
        String letters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        
        for ( int i = 0; i < this.topEdges.length; i++ ) {

            // Création de la bordure au dessus du plateau
            this.topEdges[i] =  new TopEdge(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "borderTopBackgroundColor" )
                ),
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "borderTopBorderColor" )
                ),
                letters[i],
                this.configManager.getIntValue( "btnSize" )
            );
            add( this.topEdges[i] );
            size = this.topEdges[i].getPreferredSize();
            this.topEdges[i].setBounds(
                i * size.width + size.width / 2,
                0,
                size.width,
                size.height
            );

            // Création de la bordure en dessous du plateau
            this.bottomEdges[i] = new BottomEdge(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "borderBottomBackgroundColor" )
                ),
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "borderBottomBorderColor" )
                ),
                letters[i],
                this.configManager.getIntValue( "btnSize" )
            );
            add( this.bottomEdges[i] );
            size = this.bottomEdges[i].getPreferredSize();
            this.bottomEdges[i].setBounds(
                ( size.width / 2 ) * ( this.board.length + 1 ) + i * size.width + size.width / 2,
                size.height + ( this.board.length * size.height * 3 ) / 4 - ( size.height ) / 4,
                size.width,
                size.height
            );

            // Création de la bordure gauche
            this.leftEdges[i] = new LeftEdge(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "borderLeftBackgroundColor" )
                ),
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "borderLeftBorderColor" )
                ),
                String.valueOf( i ),
                this.configManager.getIntValue( "btnSize" )
            );
            add( this.leftEdges[i] );
            size = this.leftEdges[i].getPreferredSize();
            this.leftEdges[i].setBounds(
                i * size.width / 2,
                ( i * size.height * 3 ) / 4 + ( size.height * 3 ) / 4,
                size.width,
                size.height
            );
            
            // Création de la bordre droite
            this.rightEdges[i] = new RightEdge(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "borderRightBackgroundColor" )
                ),
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "borderRightBorderColor" )
                ),
                String.valueOf( i ),
                this.configManager.getIntValue( "btnSize" )
            );
            add( this.rightEdges[i] );
            size = this.rightEdges[i].getPreferredSize();
            this.rightEdges[i].setBounds(
                size.width * ( this.board.length + 1 ) + i * size.width / 2,
                ( i * size.height * 3 ) / 4 + ( size.height * 3 ) / 4,
                size.width,
                size.height
            );
        }

        TopLeftCorner topLeftCorner = new TopLeftCorner(
            this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "cornerBackgroundColor" )
                ),
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "cornerBackgroundColor" )
                ),
                "",
                this.configManager.getIntValue( "btnSize" )
        );
        add( topLeftCorner );
        size = topLeftCorner.getPreferredSize();
        topLeftCorner.setBounds(
            0, size.height / 4, size.width, size.height
        );

        BottomRightCorner bottomRightCorner = new BottomRightCorner(
            this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "cornerBackgroundColor" )
                ),
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "cornerBackgroundColor" )
                ),
                "",
                this.configManager.getIntValue( "btnSize" )
        );
        add( bottomRightCorner );
        size = bottomRightCorner.getPreferredSize();
        bottomRightCorner.setBounds(
            ( this.board.length + ( this.board.length ) / 2 + 1 ) * size.width,
            ( ( this.board.length + 2 ) * 2 / 3 ) * size.height + ( size.height * 2 ) / 3 + 1,
            size.width, size.height
        );

        TopRightCorner topRightCorner = new TopRightCorner(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "cornerBackgroundColor" )
            ),
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "cornerBackgroundColor" )
            ),
            "",
            this.configManager.getIntValue( "btnSize" )
        );
        add( topRightCorner );
        size = topRightCorner.getPreferredSize();
        topRightCorner.setBounds(
            ( this.board.length ) * size.width + size.width / 2,
            0,
            size.width,
            size.height
        );

        BottomLeftCorner bottomLeftCorner = new BottomLeftCorner(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "cornerBackgroundColor" )
            ),
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "cornerBackgroundColor" )
            ),
            "",
            this.configManager.getIntValue( "btnSize" )
        );
        add( bottomLeftCorner );
        size = bottomLeftCorner.getPreferredSize();
        bottomLeftCorner.setBounds(
            this.board.length * size.width / 2,
            ( ( 1 + this.board.length ) * size.height * 3 ) / 4,
            size.width,
            size.height
        );

        // Placement du plateau 
        for ( int i = 0; i < board.length; i++ ) {

            for ( int j = 0; j < board.length; j++ ) {

                add( this.board[i][j] );
                size = this.board[i][j].getPreferredSize();
                this.board[i][j].setBounds(
                    j * size.width + i  * size.width / 2 + size.width / 2  + size.width / 2,
                    i * size.height + this.in.top - ( ( i ) * size.height / 4 ) + ( size.height * 3 ) / 4,
                    size.width,
                    size.height
                );
            
            }
        }
    }
}