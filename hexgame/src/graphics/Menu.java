package src.graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import src.config.Config;
import src.config.Language;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class Menu extends JFrame {
    
    private Config configManager;
    private Language languageManager;
    private StylesManager stylesManager = new StylesManager();

    public JButton multiPlayerBtn;
    public JButton soloEasyBtn;
    public JButton soloHardBtn;
    public JButton aiVsAiBtn;
    public JButton aiVsAiOnlineBtn;


    public Menu( Config configManager, Language languageManager ) {

        this.configManager = configManager;
        this.languageManager = languageManager;
        configurateMenu();
        createButton();
        createMenu();

    }

    // cette méthode configure la fenêtre du menu
    private void configurateMenu() {

        setIconImage(
            Toolkit.getDefaultToolkit().getImage(
                getClass().getResource( "../../res/img/hexagon.png" )
            )
        );
        setTitle(this.configManager.getStringValue("title"));
        setSize(
            this.configManager.getIntValue("frameWidth"),
            this.configManager.getIntValue("frameHeight")
        );
        getContentPane().setBackground(
            this.stylesManager.convertToColor( this.configManager.getStringValue("frameBackgroundColor") )
        );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setResizable( false );

    }

    // Configure les btns du  menu
    private void createButton() {

        this.multiPlayerBtn = new JButton(
            this.languageManager.getText( "multiplayer" )
        );
        this.multiPlayerBtn.setFont(
            new Font(
                this.configManager.getStringValue( "menuMultiPlayerFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "menuMultiPlayerFontStyle" )
                ),
                this.configManager.getIntValue( "menuSoloEasyFontSize" )
            )
        );
        this.multiPlayerBtn.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuMultiPlayerBackgroundColor" )
            )
        );
        this.multiPlayerBtn.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuMultiPlayerTextColor" )
            )
        );

        this.soloEasyBtn = new JButton(
            this.languageManager.getText( "soloEasy" )
        );
        this.soloEasyBtn.setFont(
            new Font(
                this.configManager.getStringValue( "menuSoloEasyFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "menuSoloEasyFontStyle" )
                ),
                this.configManager.getIntValue( "menuSoloEasyFontSize" )
            )
        );
        this.soloEasyBtn.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuSoloEasyBackgroundColor" )
            )
        );
        this.soloEasyBtn.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuSoloEasyTextColor" )
            )
        );

        this.soloHardBtn = new JButton(
            this.languageManager.getText( "soloHard" )
        );
        this.soloHardBtn.setFont(
            new Font(
                this.configManager.getStringValue( "menuSoloHardFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "menuSoloHardFontStyle" )
                ),
                this.configManager.getIntValue( "menuSoloHardFontSize" )
            )
        );
        this.soloHardBtn.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuSoloHardBackgroundColor" )
            )
        );
        this.soloHardBtn.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuSoloHardTextColor" )
            )
        );

        this.aiVsAiBtn = new JButton(
            this.languageManager.getText( "iavsia" )
        );
        this.aiVsAiBtn.setFont(
            new Font(
                this.configManager.getStringValue( "menuAiVsAiFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "menuAiVsAiFontStyle" )
                ),
                this.configManager.getIntValue( "menuAiVsAiFontSize" )
            )
        );
        this.aiVsAiBtn.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuAiVsAiBackgroundColor" )
            )
        );
        this.aiVsAiBtn.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuAiVsAiTextColor" )
            )
        );

        this.aiVsAiOnlineBtn = new JButton(
            this.languageManager.getText( "iavsiaOnline" )
        );
        this.aiVsAiOnlineBtn.setFont(
            new Font(
                this.configManager.getStringValue( "menuAiVsAiOnlineFontFamily" ),
                this.stylesManager.convertToFont(
                    this.configManager.getStringValue( "menuAiVsAiOnlineFontStyle" )
                ),
                this.configManager.getIntValue( "menuAiVsAiOnlineFontSize" )
            )
        );
        this.aiVsAiOnlineBtn.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuAiVsAiOnlineBackgroundColor" )
            )
        );
        this.aiVsAiOnlineBtn.setForeground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "menuAiVsAiOnlineTextColor" )
            )
        );
    }

    // Création du menu
    private void createMenu() {

        JPanel container = new JPanel();
        container.setLayout(
            new GridLayout( 4, 1, 5, 5 )
        ); 
        container.setPreferredSize( new Dimension (
                this.configManager.getIntValue( "frameWidth" ) - ( this.configManager.getIntValue ("frameWidth" ) ) / 4,
                this.configManager.getIntValue( "frameHeight" ) - ( this.configManager.getIntValue ("frameHeight" ) ) / 4
            )
        );

        JPanel duo = new JPanel();
        duo.setLayout(
            new GridLayout( 1, 2, 5, 5 )
        );
        duo.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "frameBackgroundColor" )
            )
        );

        JPanel duo2 = new JPanel();
        duo2.setLayout(
            new GridLayout( 1, 2, 5, 5 )
        );
        duo2.setBackground(
            this.stylesManager.convertToColor(
                this.configManager.getStringValue( "frameBackgroundColor" )
            ) 
        );

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
        
        container.add( title );
        container.add( this.multiPlayerBtn );
        duo.add( this.soloEasyBtn );
        duo.add( this.soloHardBtn );
        duo2.add( this.aiVsAiBtn );
        duo2.add( this.aiVsAiOnlineBtn );
        container.add( duo );
        container.add( duo2 );
        add( container );

    } 
}
