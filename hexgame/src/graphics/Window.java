package src.graphics;

import src.config.Config;
import src.game.Game;
import src.config.Language;
import src.debug.Debug;

import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;


// Class qui gère l'interface
public class Window extends JFrame {

    Config configManager;
    StylesManager stylesManager = new StylesManager();
    Language languageManager;
    Board board;
    JLabel helper;
    private Debug debugManager;

    
    public Window( Config c, Language l, Debug debugManager ) {

        this.debugManager = debugManager;
        this.configManager = c;
        this.board = new Board( this.configManager );
        this.languageManager = l;

    }

    public void start() {

        configureWindow();
        displayComponents();
        setVisible( true );

    }

    // Configure la fenêtre
    private void configureWindow() {

        setIconImage(
            Toolkit.getDefaultToolkit().getImage(
                getClass().getResource( "../../res/img/hexagon.png" )
            )
        );
        setTitle(this.configManager.getStringValue( "title" ) );
        setSize(
            this.configManager.getIntValue( "frameWidth" ),
            this.configManager.getIntValue( "frameHeight" )
        );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        getContentPane().setBackground(
            this.stylesManager.convertToColor( this.configManager.getStringValue( "frameBackgroundColor" ) )
        );
        setResizable(
            this.configManager.getBooleanValue( "frameResizable" )
        );
        setLayout( null );

    }

    private void displayComponents() {

        displayTitle();
        displayBoard();
        displayHelper();

    }

    // Affiche et place le plateau
    private void displayBoard() {

        this.board.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "frameBackgroundColor" )
            )
        );
        add(
            this.board
        );
        Dimension boardSize = this.board.getPreferredSize();
        this.board.setBounds(
           ( this.configManager.getIntValue( "frameWidth" ) - boardSize.width ) / 2,
           ( this.configManager.getIntValue( "frameHeight" ) - boardSize.height ) / 2,
           boardSize.width,
           boardSize.height
        );

    }

    // Affiche et place le titre 
    private void displayTitle() {

        // Création du conteneur
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout( new BorderLayout() );
        titleContainer.setBackground(
            this.stylesManager.convertToColor( 
                this.configManager.getStringValue( "frameBackgroundColor" )
            )
        );
        titleContainer.setPreferredSize( new Dimension(
            this.configManager.getIntValue( "frameWidth" ), 75
        ));

        // Création du titre
        JLabel title = new JLabel(
            this.languageManager.getText( "title" ),
            this.stylesManager.convertToLabelAlignment(
                this.configManager.getStringValue( "alignTitle" )
            )
        );
        title.setFont(
            new Font(
                this.configManager.getStringValue( "titleFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "titleFontStyle" )
                ),
                this.configManager.getIntValue( "titleFontSize" )
            )
        );
        title.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "titleColor" )
            )
        );

        titleContainer.add(
            title,
            this.stylesManager.convertToLabelAlignment(
                this.configManager.getStringValue( "titlePosition" )
            )
        );
        add(
            titleContainer  
        );

        // Placement du titre
        Dimension titleSize = titleContainer.getPreferredSize();
        titleContainer.setBounds(
            ( this.configManager.getIntValue( "frameWidth" ) - titleSize.width ) / 2,
            10,
            titleSize.width,
            titleSize.height
        );

    }

    // Affiche et place le helper
    private void displayHelper() {

        JPanel helperContainer = new JPanel();
        helperContainer.setLayout( new BorderLayout() );
        helperContainer.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "frameBackgroundColor" )
            )
        );
        helperContainer.setPreferredSize( new Dimension(
            this.configManager.getIntValue( "frameWidth" ), 75
        ));

        this.helper = new JLabel(
            this.languageManager.getText( "playerOneTurnText" ),
            this.stylesManager.convertToLabelAlignment(
                this.configManager.getStringValue( "alignHelper" )
            )
        );
        this.helper.setFont(
            new Font(
                this.configManager.getStringValue( "helperFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "helperFontStyle" )
                ),
                this.configManager.getIntValue( "helperFontSize" )
            )
        );
        this.helper.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "playerOneColor" )
            )
        );

        helperContainer.add(
            this.helper,
            this.stylesManager.convertToLabelAlignment(
                this.configManager.getStringValue( "helperPosition" )
            )
        );

        add(
            helperContainer
        );
        Dimension titleSize = helperContainer.getPreferredSize();
        helperContainer.setBounds(
            ( this.configManager.getIntValue( "frameWidth" ) - titleSize.width ) / 2,
            this.configManager.getIntValue( "frameHeight" ) - 150,
            titleSize.width,
            titleSize.height
        );

    }
    
    // Associe les bouttons du plateau à la fonction de placement des coups
    public void configureBoard( Game game ) {

        for ( int row = 0; row < this.board.board.length; row++ ) {

            for ( int col = 0; col < this.board.board.length; col++ ) {

                JButton tile = this.board.board[row][col];
                tile.addActionListener(
                    game.getButtonAction( row, col )
                );

            }

        }

    } 

    public void updateHelper( int turn ) {

        if ( turn % 2 == 0 ) {

            this.helper.setText(
                this.languageManager.getText( "playerOneTurnText" )
            );
            this.helper.setForeground(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue(
                        "playerOneColor"
                    )
                )
            );

        } else {

            this.helper.setText(
                this.languageManager.getText( "playerTwoTurnText" )
            );
            this.helper.setForeground(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue(
                        "playerTwoColor"
                    )
                )
            );

        }

    }

    // Cette fonction met à jour les cases du plateau (changement de la couleur)
    public void updateBoard( int[][] board ) {

        for ( int i = 0; i < board.length; i++ ) {

            for ( int j = 0; j < board[i].length; j++ )  {
                
                // Si la case est occupée par le joueur 1
                if ( board[i][j] == 1 ) {
                
                    this.board.board[i][j].updateTileColor(
                        this.stylesManager.convertToColor(
                            this.configManager.getStringValue( "playerOneColor" )
                        )
                    );
                
                }

                // Si la case est occupée par le joueur 2
                if ( board[i][j] == 2 ) {
                    
                    this.board.board[i][j].updateTileColor(
                        this.stylesManager.convertToColor(
                            this.configManager.getStringValue( "playerTwoColor" )
                        )
                    );
                
                }

                if ( board[i][j] == 0 ) {

                    this.board.board[i][j].updateTileColor(
                        this.stylesManager.convertToColor(
                            this.configManager.getStringValue( "tileColor" )
                        )
                    );

                }

            }
        }
        repaint();

    }


    // Cette fonction affiche le gagant si il y a un gagnant
    public void displayWinnerScreen( int winner ) {

        if ( winner != 0 ) {

            this.debugManager.log( "Affichage de l'écran de victoire > vainqueur : " + winner, "INFO" );

            remove( this.board );
            remove( this.helper );
            
            // Récupération du texte
            String text = "";
            if ( winner == 1 ) text = this.languageManager.getText( "playerOneWinText" );
            if ( winner == 2 ) text = this.languageManager.getText( "playerTwoWinText" );
            
            System.out.println( text );


            // Création du texte et configuration
            JPanel container = new JPanel();
            container.setBackground(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "winnerTextBackgroundColor" )
                )
            );
            JLabel winnerText = new JLabel(
                text   
            );
            winnerText.setFont(
                new Font(
                    this.configManager.getStringValue( "winnerTextFontFamily" ),
                    this.stylesManager.convertToFont(
                        this.configManager.getStringValue("winnerTextFontStyle" )
                    ),
                    this.configManager.getIntValue( "winnerTextFontSize" )
                )
            );
            winnerText.setBackground(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "winnerTextBackgroundColor" )
                )
            );
            if ( winner == 1 ) winnerText.setForeground(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "playerOneColor" )
                )
            ); 
            if ( winner == 2 ) winnerText.setForeground(
                this.stylesManager.convertToColor(
                    this.configManager.getStringValue( "playerTwoColor" )
                )
            );
            container.add( winnerText );
            
            // Placement du texte
            add( container );
            Dimension d = container.getPreferredSize();
            container.setBounds(
                ( this.configManager.getIntValue( "frameWidth" ) - d.width ) / 2,
                ( this.configManager.getIntValue( "frameHeight ") - d.height ) / 2,
                d.width,
                d.height
            );
            repaint();
        }
    }
}
