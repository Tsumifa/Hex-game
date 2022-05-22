import src.config.Config;
import src.config.Language;
import src.graphics.Menu;
import src.graphics.SubMenu;
import src.graphics.Window;
import src.network.Network;
import src.game.Game;
import src.IA.NN;
import java.awt.event.*;
import src.debug.Debug;

public class SetUp {

    private Debug debugManager;
    private Menu menu;
    private SubMenu subMenu;
    private Config configManager = new Config( "conf/settings.json" );
    private Language languageManager = new Language(
        "res/i18n/" + this.configManager.getStringValue( "language" ) + ".json"
    );

    // Poids les neuronnes si l'IA est le joueur 1
    private String[] paths1 = {
        "res/data/leftToRight/W1.json", 
        "res/data/leftToRight/W2.json", 
        "res/data/leftToRight/W3.json", 
        "res/data/leftToRight/W4.json",
        "res/data/leftToRight/W5.json",
        "res/data/leftToRight/W6.json",
        "res/data/leftToRight/W7.json",
        "res/data/leftToRight/W8.json",
        "res/data/leftToRight/W9.json",
        "res/data/leftToRight/W10.json",
        "res/data/leftToRight/W11.json"
    };

    // Poids des neuronnes si l'IA est le joueur 2
    private String[] paths2 = {
        "res/data/topToBottom/W1.json", 
        "res/data/topToBottom/W2.json", 
        "res/data/topToBottom/W3.json", 
        "res/data/topToBottom/W4.json",
        "res/data/topToBottom/W5.json",
        "res/data/topToBottom/W6.json",
        "res/data/topToBottom/W7.json",
        "res/data/topToBottom/W8.json",
        "res/data/topToBottom/W9.json",
        "res/data/topToBottom/W10.json",
        "res/data/topToBottom/W11.json"
    };


    public SetUp() {

        this.debugManager = new Debug(
            this.configManager.getBooleanValue( "consoleDebug" ),
            this.configManager.getBooleanValue( "fileDebug" ),
            "res/logs/logs.txt"
        );
        this.configManager.setDebug( this.debugManager );
        this.debugManager.log( "Affichage du menu", "INFO" );
        this.menu = new Menu( this.configManager, this.languageManager );
        configurateButtons();
        this.menu.setVisible( true );
    }

    // Intègre la fonction a appeler lorsqu'un bouton est cliqué
    public void configurateButtons()  {

        this.menu.multiPlayerBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    duoGame();
                } 
            }
        );

        this.menu.soloEasyBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {

                    soloGameRadom();
                } 
            }
        );

        this.menu.soloHardBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    soloGameNN();
                } 
            }
        );

        this.menu.aiVsAiBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    aiVsAi();
                } 
            }
        );

        this.menu.aiVsAiOnlineBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    aiVsAiOnline();
                } 
            }
        );
    }


    public void duoGame() {

        this.debugManager.log("Création d'une partie multijoueur", "INFO");
        this.menu.dispose();

        Window window = new Window(
            configManager,
            this.languageManager,
            this.debugManager
        );
        Game game = new Game(
            window,
            this.debugManager,
            this.configManager.getIntValue("winLeftToRightSide"),
            this.configManager.getIntValue("winTopToBottomSide")
        );
        // mode 1 permet d'effectuer les vérifications
        game.multiplayerGame( 1 );

    }

    // Cette méthode affiche le sous-menu pour choisir son joueur (joueur 1 ou joueur 2)
    public void soloGameRadom() {

        this.menu.dispose();
        this.subMenu = new SubMenu( this.configManager, this.languageManager, this.debugManager );
        this.subMenu.player1.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    soloGameRandomPlayer1();
                }
            }
        );
        this.subMenu.player2.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    soloGameRandomPlayer2();
                }
            }
        );
        this.subMenu.setVisible( true );

    }

    private void soloGameRandomPlayer1() {

        this.debugManager.log( "Création d'une partie solo ( joueur 1 ) IA Aléatoire", "INFO" );
        this.subMenu.dispose();
        Window window = new Window(
            this.configManager,
            this.languageManager,
            this.debugManager
        );
        Game game = new Game(
            window,
            this.debugManager,
            this.configManager.getIntValue( "winLeftToRightSide" ),
            this.configManager.getIntValue( "winTopToBottomSide" )
        );
        game.soloGameRandom( 2, 1 );

    }

    private void soloGameRandomPlayer2() {

        this.debugManager.log( "Création d'une partie solo ( joueur 2 ) IA Aléatoire", "INFO" );
        this.subMenu.dispose();
        Window window = new Window(
            this.configManager,
            this.languageManager,
            this.debugManager
        );
        Game game = new Game(
            window,
            this.debugManager,
            this.configManager.getIntValue( "winLeftToRightSide" ),
            this.configManager.getIntValue( "winTopToBottomSide" )
        );
        game.soloGameRandom( 2, 2 );
    
    }


    public void soloGameNN() {

        this.menu.dispose();
        this.subMenu = new SubMenu( this.configManager, this.languageManager, this.debugManager );
        this.subMenu.player1.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    soloGameNNPlayer1();
                }
            }
        );
        this.subMenu.player2.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    soloGameNNPlayer2();
                }
            }
        );
        this.subMenu.setVisible( true );

    }

    private void soloGameNNPlayer1() {

        this.debugManager.log( "Création d'une partie solo ( joueur 1 ) IA NN", "INFO" );
        this.subMenu.dispose();
        
        Window window = new Window(
            this.configManager,
            this.languageManager,
            this.debugManager
        );
        Game game = new Game(
            window,
            this.debugManager,
            this.configManager.getIntValue( "winLeftToRightSide" ),
            this.configManager.getIntValue( "winTopToBottomSide" )
        );
        NN neuralNetwork = new NN(
            this.configManager.getIntValue( "neuralNetworkInputSize" ),
            this.configManager.getIntValue( "neuralNetworkOutputSize" ),
            this.paths2
        );
        game.soloGameNN( 3, 1, neuralNetwork );

    }

    private void soloGameNNPlayer2() {

        this.debugManager.log( "Création d'une partie solo ( joueur 2 ) IA NN", "INFO" );
        this.subMenu.dispose();
        Window window = new Window(
            this.configManager,
            this.languageManager,
            this.debugManager
        );
        Game game = new Game(
            window,
            this.debugManager,
            this.configManager.getIntValue( "winLeftToRightSide" ),
            this.configManager.getIntValue( "winTopToBottomSide" )
        );
        NN neuralNetwork = new NN(
            this.configManager.getIntValue( "neuralNetworkInputSize" ),
            this.configManager.getIntValue( "neuralNetworkOutputSize" ),
            this.paths1
        );
        game.soloGameNN( 3, 2, neuralNetwork );

    }


    // Cette fonction fait jouer l'ia (NN vs NN)
    // Le plateau est affiché une fois la partie terminée
    public void aiVsAi() {

        // Suppression de la fenêtre menu
        this.menu.dispose();
        this.debugManager.log( "Création d'une partie IA vs IA", "INFO" );

        Window window = new Window(
            this.configManager,
            this.languageManager,
            this.debugManager
        );
        NN neuralNetwork1 = new NN(
            this.configManager.getIntValue( "neuralNetworkInputSize" ),
            this.configManager.getIntValue( "neuralNetworkOutputSize" ),
            this.paths1
        );
        NN neuralNetwork2 = new NN(
            this.configManager.getIntValue( "neuralNetworkInputSize" ),
            this.configManager.getIntValue( "neuralNetworkOutputSize" ),
            this.paths1
        );
        Game game = new Game(
            window,
            this.debugManager,
            this.configManager.getIntValue( "winLeftToRightSide" ),
            this.configManager.getIntValue( "winTopToBottomSide" ) 
        );
        game.aiVsAiGame( neuralNetwork1, neuralNetwork2 );

    }

    public void aiVsAiOnline() {

        this.debugManager.log( "Lancement d'une partie en ligne IA vs IA", "INFO" );
        this.menu.dispose();
        this.subMenu = new SubMenu( this.configManager, this.languageManager, this.debugManager );
        this.subMenu.player1.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    aiVsAiOnlinePlayer1();
                }
            }
        );
        this.subMenu.player2.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    aiVsAiOnlinePlayer2();
                }
            }
        );
        this.subMenu.setVisible( true );

    }

    private void aiVsAiOnlinePlayer1() {

        Window window = new Window(
            this.configManager,
            this.languageManager,
            this.debugManager
        );
        NN neuralNetwork = new NN(
            this.configManager.getIntValue( "neuralNetworkInputSize" ),
            this.configManager.getIntValue( "neuralNetworkOutputSize" ),
            this.paths2
        );
        Network network = new Network( this.configManager );
        Game game = new Game(
            window,
            this.debugManager,
            this.configManager.getIntValue( "winLeftToRightSide" ),
            this.configManager.getIntValue( "winTopToBottomSide" )
        );
        game.iaVsIaOnline( 1, network, neuralNetwork );

    }

    private void aiVsAiOnlinePlayer2() {

        Window window = new Window(
            this.configManager,
            this.languageManager,
            this.debugManager
        );
        NN neuralNetwork = new NN(
            this.configManager.getIntValue( "neuralNetworkInputSize" ),
            this.configManager.getIntValue( "neuralNetworkOutputSize" ),
            this.paths1
        );
        Network network = new Network( this.configManager );
        Game game = new Game(
            window,
            this.debugManager,
            this.configManager.getIntValue( "winLeftToRightSide" ),
            this.configManager.getIntValue( "winTopToBottomSide" )    
        );
        game.iaVsIaOnline( 2, network, neuralNetwork );

    }
}