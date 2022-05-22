package src.game;

import java.awt.event.*;
import src.graphics.Window;
import src.network.Network;
import src.IA.NN;
import src.IA.RandomAI;
import src.debug.Debug;

// Class de gestion du jeu

public class Game {
    
    private Window window;
    private Debug debugManager;
    private int turn = 0;
    private int iteration = 0;
    private int winner = 0;
    private RandomAI randomIA;
    private Boolean aiFirstMove = false;
    private NN neuralNetwork;

    private int winLeftToRight;
    private int winTopToBottom;
    
    // Représente le joueur choisit face à l'IA
    private int p;

    // mode représente le mode de jeu
    // 1 = joueur vs joueur
    // 2 = joueur vs IA
    // 3 = Ia vs Ia
    // 4 = Ia vs Ia en ligne
    private int mode;

    // Le plateau est représenté par un tableau 2d d'entier
    // (11 x 11 dans  notre cas)
    // 0 représente une case libre
    // 1 représente un jeton du joueur 1
    // 2 représente un jeton du joueur 2
    public int[][] board =  {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public Game( Window window, Debug debugManager, int winLeftToRight, int winTopToBottom ) {
        
        this.iteration = 0;
        this.mode = 0;
        this.window = window;
        this.debugManager = debugManager; 
        this.winLeftToRight = winLeftToRight;
        this.winTopToBottom = winTopToBottom;

    }


    // Cette fonction convertie le texte du placement de l'adversaire en un tableau d'entier
    // Le tableau représente les coordonnées : [0] ligne | [1] colonne
    private int[] stringToMove( String s ) {

        String[] parts = s.split( " ", 2 );
        int[] move = new int[2];
        move[0] = Integer.parseInt( parts[1] );
        move[1] = Integer.parseInt( parts[0] );
        return move;

    }

    // Cette fonction créée une partie IA vs IA en ligne
    public void iaVsIaOnline( int p, Network network, NN neuralNetwork ) {

        int[][] predictedMove;
        int[] enemyMove;
        String textEnemyMove;
        Boolean running = true;
        this.window.start();
        this.window.updateBoard( this.board );
        int[] move = new int[2];

        // Si l'IA commence
        if ( p == 1 ) {

            while ( running ) {

                predictedMove = neuralNetwork.predict( this.board );
                
                // Teste les coups proposés par l'IA
                // Vérifie que le coup est valide
                for ( int m = 0; m < predictedMove.length; m++ ) {

                    if ( tileFree( predictedMove[m][0], predictedMove[m][1] ) ) {

                        placeToken( predictedMove[m][0], predictedMove[m][1] );
                        move[0] = predictedMove[m][0];
                        move[1] = predictedMove[m][1];
                        network.sendMove( move );
                        break;

                    }
                }

                this.winner = playerWin();
                if ( this.winner == 1 ) running = false;
                textEnemyMove = network.getMove();
                
                // Si le coup adverse est SWAP
                if ( textEnemyMove.equals( "SWAP" ) ) {
                    
                    if ( !swap(move[0], move[1]) ) {

                        this.winner = 1;
                        this.debugManager.log("SWAP interdit aux coordonnées  ligne : " + move[0] + " | colonne : " + move[1], "WARNING");
                        running = false;
                        break;
                    
                    }

                } else {

                    enemyMove = stringToMove( textEnemyMove );
                    
                    if ( !placeToken( enemyMove[0], enemyMove[1] ) ) {
                        
                        this.debugManager.log( "Coup invalide > ligne : " + enemyMove[0] + " | row : " + enemyMove[1], "WARNING" );
                        this.winner = p;
                        running = false;
                    }
                }

                this.winner = playerWin();
                if ( this.winner!= 0 ) running = false;
            }

        } else {

            while ( running ) {
                
                textEnemyMove = network.getMove();

                // Si le coup adverse est SWAP
                if ( textEnemyMove.equals( "SWAP" ) ) {
                    this.winner = 1;
                    this.debugManager.log("SWAP interdit car l'adversaire est le joueur 1", "WARNING");
                    running = false;
                    break;
                }

                enemyMove = stringToMove( textEnemyMove );

                if ( !placeToken( enemyMove[0], enemyMove[1] ) ) {

                    this.debugManager.log( "Coup invalide > ligne : " + enemyMove[0] + " | row : " + enemyMove[1], "WARNING" );
                    this.winner = p;
                    running = false;

                }

                this.winner = playerWin();
                if ( this.winner!= 0 ) running = false;
                predictedMove = neuralNetwork.predict( this.board );
                
                // Teste les coups proposés par l'IA
                // Vérifie que le coup est valide
                for ( int m = 0; m < predictedMove.length; m++ ) {

                    if ( tileFree( predictedMove[m][0], predictedMove[m][1] ) ) {

                        placeToken( predictedMove[m][0], predictedMove[m][1] );
                        move[0] = predictedMove[m][0];
                        move[1] = predictedMove[m][1];
                        network.sendMove( move );
                        break;
                    }
                }

                this.winner = playerWin();
                if ( this.winner == 1 ) running = false;
            }
        }
        this.window.displayWinnerScreen( this.winner );
    }

    // Cette fonction créée une partie joueur vs joueur
    public void multiplayerGame( int mode ) {
        
        this.mode = mode;
        this.window.start();
        this.window.configureBoard( this );
        this.window.updateBoard( this.board );

        if ( this.winner == 1) {

            this.debugManager.log( "Le joueur 1 a gagné !", "INFO" );

        } else {

            this.debugManager.log( "Le joueur 2 a gagné !", "INFO" );
        }
    }

    // Partie solo VS IA NN
    // p --> joueur 1 / 2
    public void soloGameNN( int mode, int p, NN neuralNetwork ) {

        this.neuralNetwork = neuralNetwork;
        this.mode = mode;
        this.p = p;
        this.window.start();
        this.window.configureBoard( this );
        this.window.updateBoard( this.board );

        // Si l'IA commence
        if ( this.p == 2 ) {

            this.aiFirstMove = true;
            int[][] firstMove = this.neuralNetwork.predict( this.board );
            placeToken( firstMove[0][0], firstMove[0][1] );

        }

    }


    // Cette fonction créée une partie entre le joueur et une IA aléatoire
    // p = 1 --> indique que le joueur commence
    // p = 2 --> indique que l'IA commence
    public void soloGameRandom ( int mode, int p ) {

        this.mode = mode;
        this.p = p;
        this.window.start();
        this.window.configureBoard( this );
        this.window.updateBoard( this.board );
        this.randomIA = new RandomAI( this.debugManager );

        // Si l'IA Random commence
        if ( this.p == 2 ) {

            this.aiFirstMove = true;
            int[] randomMove = this.randomIA.playMove( this.board );
            placeToken(randomMove[0], randomMove[1]);

        }
    }


    // Cette méthode effectue une partie entre deux IA
    public void aiVsAiGame( NN neuralNetwork1, NN neuralNetwork2 ) {

        Boolean running = true;
        int[][] aiMoves;

        while ( running ) {

            // Si le programme ne détecte pas de gagnant et que le plateau est complet
            if ( this.turn >= 121 ) {

                this.debugManager.log( "Plateau complet : aucune victoire détectée.", "ERROR" );
                running = false;
                break;

            }

            // prédiction de l'IA (joueur 1) et placement du jeton
            aiMoves = neuralNetwork1.predict( this.board );

            for ( int m = 0; m < aiMoves.length; m++ ) {

                // Dans le cas où le programme prédit une case déjà occupée
                if ( placeToken( aiMoves[m][0], aiMoves[m][1] ) ) break;
            }

            // Vérifie si l'IA 1 a gagné
            if( playerWin() == 1 ) {

                this.debugManager.log( "Victoire de l'IA (1)", "INFO" );
                running = false;
                break;

            }
        
            // Inversion du plateau pour que l'IA prédise le coup du joueur 2
            aiMoves = neuralNetwork2.predict( this.board );

            for ( int m = 0; m < aiMoves.length; m++ ) {

                // Dans le cas où le programme prédit une case déjà occupée
                if ( tileFree( aiMoves[m][0], aiMoves[m][0] ) ) {

                    placeToken( aiMoves[m][0], aiMoves[m][1] );
                    break;

                }
            }

            // Vérifie si l'IA 2 a gagné
            if( playerWin() == 2 ) {

                this.debugManager.log( "Victoire de l'IA (2)", "INFO" );
                running = false;
                break;

            }
        }
        this.window.start();
        this.window.updateBoard( this.board );
    }

    // Retourne vrai si la case est libre sinon retourne faux
    private Boolean tileFree( int row, int column ) {

        if ( this.board[row][column] == 0 ) return true;
        return false;

    }


    // Retourne 1 si c'est le tour du joueur 1
    // Retourne 2 si c'est le tour du joueur 2
    private int getToken() {

        if ( this.turn % 2 == 0 ) return 1;
        return 2;

    }

    // Effectue le swap
    // Récupère les coordonnées du coup du joueur 1 et inverse les coordonnées pour placer le jeton du joueur 2
    public Boolean swap( int row, int col ) {

        if ( this.board[row][col] == 1 && this.turn == 1 ) {

            this.board[row][col] = 0;
            this.board[col][row] = 2;

            try {

                this.window.updateHelper(turn);
                this.window.updateBoard(board);

            } catch ( Exception e ) {
                
                this.debugManager.log( "Echec de mise à jour des composants graphiques", "WARNING" );

            }

            this.turn++;
        }
        return false;
    }

    // Cette fonction place les jetons des joueurs
    // Vérifie que le coup est valide
    // Retourne true si l'opération est un succès et false si elle échoue
    public Boolean placeToken( int row, int column ) {

        int[] randomMove;
        int[][] neuralNetworkMove;

        if ( row >= this.board.length || column >= this.board[0].length ) {
            
            this.debugManager.log( "Coup illégal : les coordonnées ne sont incluses dans le tableau > ligne : " + row  + " | colonne " + column, "ERROR" );
            return false;
        }
        
        Boolean tileIsFree = tileFree( row, column );
        // Si il s'agit du premier coup du joueur 2 et qu'il veut échanger de place les jetons
        if ( this.turn == 1 && !tileIsFree ) {

            return swap( row, column );
            
            // si le coup est valide
        } else if ( tileIsFree ) {

            // S'il s'agit d'une partie Joueur vs IA
            if ( this.mode == 2 && this.aiFirstMove == true && this.turn == 0 ) {
                
                // Si c'est l'IA qui joue le premier coup
                this.board[row][column] = getToken();
                this.window.updateHelper( turn );
                this.window.updateBoard( board );
                this.turn++;
                return true;

            } else if ( this.mode == 2 && this.turn % 2 == ( this.p - 1 ) % 2 ) {

                this.board[row][column] = getToken();
                this.winner = playerWin();
                this.turn++;
                this.winner = playerWin();
                this.window.displayWinnerScreen( this.winner );

                if ( winner != 0 ) return true;
                
                randomMove = this.randomIA.playMove( this.board );
                this.board[randomMove[0]][randomMove[1]] = getToken();
                this.winner = playerWin();
                this.window.updateHelper( turn );
                this.window.updateBoard( board );
                this.window.displayWinnerScreen( this.winner );
                this.turn++;
                return true;

            }

            if ( this.mode == 3 && this.aiFirstMove == true && this.turn == 0 )  {

                // Si c'est l'IA qui joue le premier coup
                this.board[row][column] = getToken();
                this.window.updateHelper( turn );
                this.window.updateBoard( board );
                this.turn++;
                return true;

            } else if ( this.mode == 3 && this.turn % 2 == ( this.p - 1 ) % 2 ) {

                this.board[row][column] = getToken();
                this.winner = playerWin();
                this.turn++;
                this.window.displayWinnerScreen( this.winner );
                if ( winner != 0 ) return true;
                if ( this.p == 1 ) {
                    neuralNetworkMove = this.neuralNetwork.predict( this.board );
                } else {
                    neuralNetworkMove = this.neuralNetwork.predict( this.board );
                }

                for ( int m = 0; m < neuralNetworkMove.length; m++ ) {

                    if ( tileFree( neuralNetworkMove[m][0], neuralNetworkMove[m][1] ) ) {

                        this.board[ neuralNetworkMove[m][0] ][ neuralNetworkMove[m][1] ] = getToken();
                        break;

                    }

                }

                this.winner = playerWin();
                this.window.updateHelper( turn );
                this.window.updateBoard( board );
                this.window.displayWinnerScreen( this.winner );
                this.turn++;
                return true;

            }

            this.board[row][column] = getToken();
            this.winner = playerWin();
            
            // S'il d'agit d'une partie Joueur vs Joueur
            if ( this.mode == 1 ) {

                window.updateHelper( this.turn );
                window.updateBoard( this.board );
                window.displayWinnerScreen( this.winner );
            }

            this.turn++;
            return true;
        }
        return false;
    }

    public ActionListener getButtonAction( int row, int col ) {
        return new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
               placeToken( row, col );
            }
        
        };
    } 

    // Retourne vrai si il y a au moins un token player dans la ligne line
    // Retourne faux sinon
    private Boolean playerHasTokenInLine( int line, int player ) {

        for (int i = 0; i < this.board[line].length; i++) {

            if ( this.board[line][i] == player ) return true;
        
        }
        return false;
    }

    // Retourne vrai si il y a au moins un token player dans la colonne line
    // Retourne faux sinon
    private Boolean playerHasTokenInColumn( int column, int player ) {
        
        for ( int i = 0; i < this.board.length; i++ ) {

            if ( this.board[i][column] == player ) return true;

        }
        return false;
    }


    // Vérifie que la case ciblée n'a pas déjà été vérifiée
    // Evite les boucles infinies
    private Boolean tileNotChecked( int row, int col, int[][] checkedTiles ) {

        for ( int tile = 0; tile < checkedTiles.length; tile++ ) {
        
            if ( checkedTiles[tile][0] == row && checkedTiles[tile][1] == col ) return false;

        }
        
        return true;
    }
    
    private Boolean sameTilesAround( int player, int row, int col, int[][] checkedTiles) {
        // Mise à jour du tableau des cases consultées
        int[][] nCheckedTiles = new int[checkedTiles.length + 1][2]; // pour newCheckedTiles
        
        for ( int tile = 0; tile < checkedTiles.length; tile++ ) {

            nCheckedTiles[tile][0] = checkedTiles[tile][0];
            nCheckedTiles[tile][1] = checkedTiles[tile][1];
        
        }

        nCheckedTiles[checkedTiles.length][0] = row; 
        nCheckedTiles[checkedTiles.length][1] = col;
        
        
        // Dans le cas où la fonction se rapelle indéfiniement
        this.iteration++;
        if ( this.iteration > ( this.board.length * this.board.length ) / 2 + 30 ) {

            this.debugManager.log( "Iterations trop grandes pour la recherche d'un gagnant", "WARNING" );
            return false;

        }
    
        // Vérification
        if ( row == 0 ) {
    
            if ( col == 0 ) {
    
                if ( this.board[row + 1][col] == player && tileNotChecked( row + 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround(  player, row + 1, col, nCheckedTiles ) ) return true;

                }
    
                if ( this.board[row][col + 1] == player && tileNotChecked( row, col + 1, nCheckedTiles ) ) {
                    if ( sameTilesAround(  player, row, col + 1, nCheckedTiles ) ) return true;
                    
                }
    
            } else if ( col == this.board.length - 1 ) {
    
                // Il y a un parcours du coté gauche au coté droit
                if ( ( player == 1 && this.winLeftToRight == 1 ) || ( player == 2 && this.winLeftToRight == 2 ) ) return true;
    
                if ( this.board[row + 1][col] == player && tileNotChecked( row + 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row + 1, col, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row + 1][col - 1] == player && tileNotChecked( row + 1, col - 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row + 1, col - 1, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row][ col - 1] == player && tileNotChecked( row, col - 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row, col - 1, nCheckedTiles ) ) return true;
                    
                }
    
            } else {
    
                if ( this.board[row + 1][col] == player && tileNotChecked( row + 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row + 1, col, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row + 1][col - 1] == player && tileNotChecked( row + 1, col - 1, nCheckedTiles ) ) {
                    
                    if ( sameTilesAround( player, row + 1, col - 1, nCheckedTiles ) ) {
                        return true;
                    }
                }
    
                if ( this.board[row][col - 1] == player && tileNotChecked( row, col - 1, nCheckedTiles ) ) {
                    if ( sameTilesAround( player, row, col - 1, nCheckedTiles ) ) {
                        return true;
                    }
                }
    
                if ( this.board[row][col + 1] == player && tileNotChecked( row, col + 1, nCheckedTiles ) ) {
                    if ( sameTilesAround( player, row, col + 1, nCheckedTiles ) ) {
                        return true;
                    }
                }
    
            }
    
    
        } else if ( row == this.board.length - 1 ) {
    
            // Il y a un parcours du coté haut au coté bas
            if ( ( player == 1 && this.winTopToBottom == 1 ) || ( player == 2 && this.winTopToBottom == 2 ) ) {
                return true;
            }
    
            if ( col == 0 ) {
    
                if ( this.board[row - 1][col] == player && tileNotChecked( row - 1, col, nCheckedTiles ) ) {
                    if ( sameTilesAround( player, row - 1, col, nCheckedTiles ) ) {
                        return true;
                    }
                }
    
                if ( this.board[row - 1][col + 1] == player && tileNotChecked( row - 1, col + 1, nCheckedTiles ) ) {
                    if ( sameTilesAround( player, row - 1, col + 1, nCheckedTiles ) ) {
                        return true;
                    }
                }
    
                if ( this.board[row][col + 1] == player && tileNotChecked( row, col + 1, nCheckedTiles ) ) {
                    if ( sameTilesAround( player, row, col + 1, nCheckedTiles ) ) {
                        return true;
                    }
                }
    
            } else if ( col == this.board.length - 1 ) {
    
                System.out.println("Access col == 10");

                // Il y a un parcours du coté gauche au coté droit
                if ( ( player == 1 && this.winLeftToRight == 1 ) || ( player == 2 && this.winLeftToRight == 2 ) ) {
                    return true;
                }
    
                if ( this.board[row - 1][col] == player && tileNotChecked( row - 1, col, nCheckedTiles ) ) {
                    
                    if ( sameTilesAround( player, row - 1, col, nCheckedTiles ) ) return true;

                }
    
                if ( this.board[row][col - 1] == player && tileNotChecked( row, col - 1, nCheckedTiles ) ) {
                    
                    if ( sameTilesAround( player, row, col - 1, nCheckedTiles ) ) return true;
                    
                }
    
            } else {
    
                if ( this.board[row - 1][col + 1] == player && tileNotChecked( row - 1, col + 1, nCheckedTiles ) ) {
                    
                    if ( sameTilesAround( player, row - 1, col + 1, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row - 1][col] == player && tileNotChecked( row - 1, col, nCheckedTiles ) ) {
                    
                    if ( sameTilesAround( player, row - 1, col, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row][col - 1] == player && tileNotChecked( row, col - 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row, col - 1, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row][col + 1] == player && tileNotChecked( row, col + 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row, col + 1, nCheckedTiles ) ) return true;
                    
                }
    
            }
    
        } else {
    
            if ( col == 0 ) {
    
                if ( this.board[row + 1][col] == player && tileNotChecked( row + 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row + 1, col, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row][col + 1] == player && tileNotChecked( row, col + 1, nCheckedTiles ) ) {
                    
                    if ( sameTilesAround( player, row, col + 1, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row - 1][col] == player && tileNotChecked( row - 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row - 1, col, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row - 1][col + 1] == player && tileNotChecked( row - 1, col + 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row - 1, col + 1, nCheckedTiles ) ) return true;
                    
                }
    
            } else if ( col == this.board.length - 1 ) {
                

                // Il y a un parcours du coté gauche au coté droit
                if ( ( player == 1 && this.winLeftToRight == 1 ) || ( player == 2 && this.winLeftToRight == 2 ) ) return true;
    
                if ( this.board[row - 1][col] == player && tileNotChecked( row - 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row - 1, col, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row][col - 1] == player && tileNotChecked( row, col - 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row, col - 1, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row + 1][col] == player && tileNotChecked( row + 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row + 1, col, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row + 1][col - 1] == player && tileNotChecked( row + 1, col - 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row + 1, col - 1, nCheckedTiles ) ) return true;

                }
    
            } else {
    
                if ( this.board[row - 1][col] == player && tileNotChecked( row - 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row - 1, col, nCheckedTiles ) ) return true;

                }
    
                if ( this.board[row - 1][col + 1] == player && tileNotChecked( row - 1, col + 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row - 1, col + 1, nCheckedTiles ) ) return true;

                }
    
                if ( this.board[row][col - 1] == player && tileNotChecked( row, col - 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row, col - 1, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row][col + 1] == player && tileNotChecked( row, col + 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row, col + 1, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row + 1][col - 1] == player && tileNotChecked( row + 1, col - 1, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row + 1, col - 1, nCheckedTiles ) ) return true;
                    
                }
    
                if ( this.board[row + 1][col] == player && tileNotChecked( row + 1, col, nCheckedTiles ) ) {

                    if ( sameTilesAround( player, row + 1, col, nCheckedTiles ) ) return true;
                    
                }
            }
        }
        return false;
    }



    private Boolean playerOneWinsTopToBottom() {

        int[][] c = { {-1, -1} };

        for ( int i = 0; i < this.board[0].length; i++ ) {
            this.iteration = 0;
        
            if ( this.board[0][i] == 1 ) {
        
                if ( sameTilesAround(  1, 0, i, c ) ) return true;
        
            }
        
        }
        return false;
    }

    private Boolean playerOneWinsLeftToRight() {

        int[][] c = { {-1, -1} };

        for ( int i = 0; i < this.board.length; i++ ) {
        
            this.iteration = 0;
        
            if ( this.board[i][0] == 1 ) {
        
                if ( sameTilesAround(  1, i, 0, c ) ) return true;
        
            }

        }
        return false;
    }

    private Boolean playerTwoWinsLeftToRight() {

        int[][] c = { { -1, -1 } };
        
        for ( int i = 0; i < board.length; i++ ) {
        
            this.iteration = 0;
        
            if ( this.board[i][0] == 2 ) {
                
                if ( sameTilesAround(  2, i, 0, c ) ) return true;
        
            }
        
        }
        return false;
    }

    // Vérifie que chaque ligne contient au moins un jeton du joueur 2
    private Boolean playerTwoWinsTopToBottom() {

        int[][] c = { { -1, -1 } };

        for ( int i = 0; i < board[0].length; i++ ) {
            
            this.iteration = 0;
            
            if ( this.board[0][i] == 2 ) {

                if ( sameTilesAround( 2, 0, i, c) ) return true;
            
            }

        }
        return false;
    }


    // Recherche rapide d'une possible victoire:
    // 1) si le joueur 1 doit rejoindre le coté gauche et le coté droit. Pour gagner:
    //  - le joueur 1 doit avoir au moins 1 jeton par colonne
    //  - le joueur 2 doit avoir au moins un jeton par ligne
    //
    // 2) si le joueur 2 doit rejoindre le coté gauche et le coté droit. Pour gagner:
    //  - le joueur 1 doit avoir au moins 1 jeton par ligne
    //  - le joueur 2 doit avoir au moins un jeton par colonne
    private Boolean quickVerification() {

        Boolean playerOneWinPossible = true;
        Boolean playerTwoWinPossible = true;

        // Si le joueur 1 gagne en rejoignant le coté gauche et le coté droit
        if ( this.winLeftToRight == 1 ) {

            for ( int col = 0; col < this.board[0].length; col++ ) {

                if ( !playerHasTokenInColumn( col, 1 ) ) playerOneWinPossible = false;

            }

            for ( int row = 0; row < this.board.length; row++ ) {

                if ( !playerHasTokenInLine( row, 2 ) ) playerTwoWinPossible = false;

            }

        // Si le joueur 2 gagne en rejoignant le coté gauche et le coté droit
        } else {

            for ( int col = 0; col < this.board[0].length; col++ ) {

                if ( !playerHasTokenInColumn( col, 2 ) ) playerTwoWinPossible = false;

            }

            for ( int row = 0; row < this.board.length; row++ ) {

                if ( !playerHasTokenInLine( row, 1 ) ) playerOneWinPossible = false;

            }

        }
        return ( playerOneWinPossible || playerTwoWinPossible );
    }


    public int playerWin() {

        // Verfication rapide : 
        // - vérifie que pour chaque ligne (ou colonne : fichier de config) il y a un jeton du joueur 1 (ou 2)
        // - vérifie que pour chaque colonne  (ou ligne : fichier de config) il y a un jeton du joueur 2 (ou 1)
        if ( !quickVerification() ) return 0;

        // Vérification complète
        if ( this.winLeftToRight == 1 ) {

            if ( playerOneWinsLeftToRight() ) return 1;
            if ( playerTwoWinsTopToBottom() ) return 2;

        } else {      

            if ( playerOneWinsTopToBottom() ) return 1;
            if ( playerTwoWinsLeftToRight() ) return 2;

        }
        return 0;
    }

}