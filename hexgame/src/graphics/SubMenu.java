package src.graphics;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import src.config.Config;
import src.config.Language;
import src.debug.Debug;

public class SubMenu extends JFrame {

    private Config configManager;
    private Language languageManager;
    private StylesManager stylesManager = new StylesManager();

    public JButton player1;
    public JButton player2;

    public SubMenu( Config configManager, Language languageManager, Debug debguManager ) {

        this.configManager = configManager;
        this.languageManager = languageManager;
        configurateWindow();
        createButtons();
        displayComponents();

    }

    // Cette méthode configure la fenêtre du sous-menu
    private void configurateWindow() {

        setIconImage(
            Toolkit.getDefaultToolkit().getImage(
                getClass().getResource( "../../res/img/hexagon.png" )
            )
        );
        setTitle (this.configManager.getStringValue( "title" ) );
        setSize(
            this.configManager.getIntValue( "frameWidth" ),
            this.configManager.getIntValue( "frameHeight" )
        );
        getContentPane().setBackground(
            this.stylesManager.convertToColor( this.configManager.getStringValue( "frameBackgroundColor" ) )
        );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setResizable( false );

    }


    private void createButtons() {

        this.player1 = new JButton(
            this.languageManager.getText( "playerOne" )
        );
        this.player1.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "subMenuPlayerOneBtnBackgroundColor" )
            )
        );
        this.player1.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "subMenuPlayerOneBtnColor" )
            )
        );
        this.player1.setFont(
            new Font(
                this.configManager.getStringValue( "subMenuPlayerOneBtnFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "subMenuPlayerOneBtnFontStyle" )
                ),
                this.configManager.getIntValue( "subMenuPlayerOneBtnFontSize" )
            )
        );

        this.player2 = new JButton(
            this.languageManager.getText( "playerTwo" )
        );
        this.player2.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "subMenuPlayerTwoBtnBackgroundColor" )
            )
        );
        this.player2.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "subMenuPlayerTwoBtnColor" )
            )
        );
        this.player2.setFont(
            new Font(
                this.configManager.getStringValue( "subMenuPlayerTwoBtnFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "subMenuPlayerTwoBtnFontStyle" )
                ),
                this.configManager.getIntValue( "subMenuPlayerTwoBtnFontSize" )
            )
        );

    }

    // Affichage des composants sur la fenêtre
    private void displayComponents() {

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout( new GridLayout( 2, 1, 5, 5 ) );
        mainContainer.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "frameBackgroundColor" )
            )
        );

        JPanel duo = new JPanel();
        duo.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "frameBackgroundColor" )
            )
        );
        duo.setLayout( new GridLayout( 1, 2, 5, 5 ) );


        JLabel title = new JLabel(
            this.languageManager.getText( "ChoosePlayer" ),
            this.stylesManager.convertToLabelAlignment(
                this.configManager.getStringValue( "alignTitle" )
            )
        );
        title.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "subMenuTitleColor" )
            )
        );
        title.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "subMenuTitleBackgroundColor" )
            )
        );
        title.setFont(
            new Font(
                this.configManager.getStringValue( "subMenuTitleFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "subMenuTitleFontStyle" )
                ),
                this.configManager.getIntValue( "subMenuTitleFontSize" )
            )
        );
        mainContainer.add( title );
        
        duo.add( this.player1 );
        duo.add( this.player2 );
        mainContainer.add( duo );
        add( mainContainer );
    }

}
