package src.IA;

public class NN {
    
    private int inputSize;
    private int hiddenSize1 = 121;
    private int hiddenSize2 = 121;
    private int hiddenSize3 = 121;
    private int hiddenSize4 = 121;
    private int hiddenSize5 = 121;
    private int hiddenSize6 = 121;
    private int hiddenSize7 = 121;
    private int hiddenSize8 = 121;
    private int hiddenSize9 = 121;
    private int hiddenSize10 = 121;
    private int outputSize;

    private double[][] W1;
    private double[][] W2;
    private double[][] W3;
    private double[][] W4;
    private double[][] W5;
    private double[][] W6;
    private double[][] W7;
    private double[][] W8;
    private double[][] W9;
    private double[][] W10;
    private double[][] W11;


    public NN( int inputSize, int outputSize, String[] paths ) {

        this.inputSize = inputSize;
        this.W1 = new double[this.inputSize][this.hiddenSize1];
        this.W2 = new double[this.hiddenSize1][this.hiddenSize2];
        this.W3 = new double[this.hiddenSize2][this.hiddenSize3];
        this.W4 = new double[this.hiddenSize3][this.hiddenSize4];
        this.W5 = new double[this.hiddenSize4][this.hiddenSize5];
        this.W6 = new double[this.hiddenSize5][this.hiddenSize6];
        this.W7 = new double[this.hiddenSize6][this.hiddenSize7];
        this.W8 = new double[this.hiddenSize7][this.hiddenSize8];
        this.W9 = new double[this.hiddenSize8][this.hiddenSize9];
        this.W10 = new double[this.hiddenSize9][this.hiddenSize10];
        this.W11 = new double[this.hiddenSize10][this.outputSize];
        loadWeights( paths );

    }

    // Fonction d'activation
    public double[][] sigmoid( double[][] M ) {

        double[][] r = new double[M.length][M[0].length];

        for ( int i = 0; i < M.length; i++ ) {
            for ( int j = 0; j < M[i].length; j++ ) {

                r[i][j] = 1 / ( 1 + Math.exp( -M[i][j] ) );

            }
        }
        return r;

    }

    private double[][] transition( double[][] m1, double[][] m2 ) {

        double[][] r = new double[m2[0].length][1];
        double sum;
        for ( int col = 0; col < r.length; col++ ) {
            sum = 0;
            for ( int row = 0; row < m1.length; row++ ) {
                sum += m1[row][0] * m2[row][col];
            }
            r[col][0] = sum;

        }
        return r;
    }


    // Calculs des prédictions
    public double[][] forward( double[][] input ) {

        // Multiplication matricielle entre la matrice des valeurs d'entrée et la matrice de poids 1
        double[][] z = transition( input, this.W1 );

        // Applicaiton de la fonction d'activation
        double[][] z1 = sigmoid( z );
        
        // Multiplication matricielle entre la matrice de la couche chachée 1 et la matrice de poids 2
        double[][] z2 = transition( z1, this.W2 );

        // Application de la fonction d'activation
        double[][] z3 = sigmoid( z2 );
        
        // Multiplication matricielle entre la matrice de la couche chachée 2 et la matrice de poids 3
        double[][] z4 = transition( z3, this.W3 );
        // Application de la fonction d'activation 
        double[][] z5 = sigmoid( z4 );
        
        // Multiplication matricielle entre la matrice de la couche chachée 3 et la matrice de poids 4
        double[][] z6 = transition( z5, this.W4 );

        // Application de la fonction d'activation 
        double[][] z7 = sigmoid( z6 );
    
        // Multiplication matricielle entre la matrice de la couche chachée 4 et la matrice de poids 5
        double[][] z8 = transition( z7, this.W5 );

        // Application de la fonction d'activation 
        double[][] z9 = sigmoid( z8 );
    
        // Multiplication matricielle entre la matrice de la couche chachée 5 et la matrice de poids 6
        double[][] z10 = transition( z9, this.W6 );

        // Application de la fonction d'activation 
        double[][] z11 = sigmoid( z10 );
    
        // Multiplication matricielle entre la matrice de la couche chachée 6 et la matrice de poids 7
        double[][] z12 = transition( z11, this.W7 );

        // Application de la fonction d'activation 
        double[][] z13 = sigmoid( z12 );
    
        // Multiplication matricielle entre la matrice de la couche chachée 7 et la matrice de poids 8
        double[][] z14 = transition( z13, this.W8 );

        // Application de la fonction d'activation 
        double[][] z15 = sigmoid( z14 );
    
        // Multiplication matricielle entre la matrice de la couche chachée 8 et la matrice de poids 9
        double[][] z16 = transition( z15, this.W9 );

        // Application de la fonction d'activation 
        double[][] z17 = sigmoid( z16 );
    
        // Multiplication matricielle entre la matrice de la couche chachée 9 et la matrice de poids 10
        double[][] z18 = transition( z17, this.W10 );

        // Application de la fonction d'activation 
        double[][] z19 = sigmoid( z18 );
    
        // Application de la fonction d'activation
        return sigmoid( z19 );
    }

    // Cette fonction retourne les prédictions de l'IA.
    // Le plateau est formaté en matrice ( 1 x 121 )
    // Pour s'assurer qu'il n'y a pas de coup illégal, le tableau de prédiction est comparé avec le plateau
    // Les coordonnées de la case ayant la meilleure prédiction est retournée
    public int[][] predict( int[][] board ) {

        double[][] dboard = new double[121][1];
        
        for ( int row = 0; row < board.length; row++ ) {
            for ( int col = 0; col < board.length; col++ ) {
        
                dboard[row + col][0] = Double.valueOf( board[row][col] );
        
            }
        
        }

        double[][] predictions = forward( dboard );
        
        for ( int row = 0; row < board.length; row++ ) {
        
            for ( int col = 0; col < board[row].length; col++ ) {
        
                if ( board[row][col] != 0 ) predictions[row + col][0] = 0;
                
            }
        }
        return readPredictions( board, predictions );

    } 

    // Cette fonction trie les coordonées des coups prédits par l'IA par ordre croissant de prédiction
    private int[][] readPredictions( int[][] board, double[][] predictions ) {

        double max = predictions[0][0];
        int[][] coords = new int[predictions.length][2];
        int loop = 0;

        // Recherche du maximum
        while ( loop < predictions.length ) {

            max = 0;
            
            for ( int row = 0; row < predictions.length; row++ ) {

                if ( predictions[row][0] > max ) max = predictions[row][0];
                
            }
            
            for ( int row = 0; row < predictions.length; row++ ) {

                if ( predictions[row][0] == max ) {

                    coords[loop][0] = (int) row  / 11;
                    coords[loop][1] = row % 11;
                    loop++;
                    predictions[row][0] = -1;

                }

            }
        }

        return coords;
    
    }

    // Charge depuis les fichiers du répertoires "/res/data/" les valeurs des poids pour le réseau de neurones
    // Chargement généralement long
    public void loadWeights ( String[] paths )  {

        // Chargement des fichiers
        System.out.println( "Chargement des fichiers : " );
        WeightsLoarder[] weightsLoarders = new WeightsLoarder[paths.length];

        for ( int path = 0; path < paths.length; path++ ) {

            weightsLoarders[path] = new WeightsLoarder( paths[path] );
            progressBar( path, paths.length - 1 );    
        
        }
    
        // Chargement des variables
        System.out.println( "Chargement des variables :\n" );

        for ( int row = 0; row < this.W1.length; row++ )  {

            progressBar( row, this.W1.length - 1 );

            for ( int col = 0; col < this.W1[0].length; col++ ) {

                this.W1[row][col] = weightsLoarders[0].getWeight( row + "-" + col );

            }

        }

        System.out.println( "\n" );

        for ( int row = 0; row < this.W2.length; row++ )  {

            progressBar( row, this.W2.length - 1 );
        
            for ( int col = 0; col < this.W2[0].length; col++ ) {
        
                this.W2[row][col] = weightsLoarders[1].getWeight( row + "-" + col );
        
            }
        
        }
        
        System.out.println( "\n" );

        for ( int row = 0; row < this.W3.length; row++ )  {

            progressBar( row, this.W3.length - 1 );
            
            for ( int col = 0; col < this.W3[0].length; col++ ) {
            
                this.W3[row][col] = weightsLoarders[2].getWeight( row + "-" + col );
            
            }
        
        }

        System.out.println( "\n" );

        for ( int row = 0; row < this.W4.length; row++ )  {
        
            progressBar( row, this.W4.length - 1 );

            for ( int col = 0; col < this.W4[0].length; col++ ) {
            
                this.W4[row][col] = weightsLoarders[3].getWeight( row + "-" + col );
            
            }
        
        }

        System.out.println( "\n" );

        for ( int row = 0; row < this.W5.length; row++ )  {
        
            progressBar( row, this.W5.length - 1 );

            for ( int col = 0; col < this.W5[0].length; col++ ) {
            
                this.W5[row][col] = weightsLoarders[4].getWeight( row + "-" + col );
            
            }
        
        }
        
        System.out.println( "\n" );

        for ( int row = 0; row < this.W6.length; row++ )  {

            progressBar( row, this.W6.length - 1 );
        
            for ( int col = 0; col < this.W6[0].length; col++ ) {
            
                this.W6[row][col] = weightsLoarders[5].getWeight( row + "-" + col );
            
            }
        
        }
        
        System.out.println( "\n" );

        for ( int row = 0; row < this.W7.length; row++ )  {

            progressBar( row, this.W7.length - 1 );

            for ( int col = 0; col < this.W7[0].length; col++ ) {

                this.W7[row][col] = weightsLoarders[6].getWeight( row + "-" + col );
            
            }

        }

        System.out.println( "\n" );

        for ( int row = 0; row < this.W8.length; row++ )  {
        
            progressBar( row, this.W8.length - 1 );
        
            for ( int col = 0; col < this.W8[0].length; col++ ) {
        
                this.W8[row][col] = weightsLoarders[7].getWeight( row + "-" + col );
        
            }
        
        }

        System.out.println( "\n" );

        for ( int row = 0; row < this.W9.length; row++ )  {

            progressBar( row, this.W9.length - 1 );

            for ( int col = 0; col < this.W9[0].length; col++ ) {

                this.W9[row][col] = weightsLoarders[8].getWeight( row + "-" + col );

            }

        }

        System.out.println( "\n" );

        for ( int row = 0; row < this.W10.length; row++ )  {

            progressBar( row, this.W10.length - 1 );

            for ( int col = 0; col < this.W10[0].length; col++ ) {

                this.W10[row][col] = weightsLoarders[9].getWeight( row + "-" + col );

            }
        }

        System.out.println( "\n" );
        
        for ( int row = 0; row < this.W11.length; row++ )  {

            progressBar( row, this.W11.length - 1 );

            for ( int col = 0; col < this.W11[0].length; col++ ) {

                this.W11[row][col] = weightsLoarders[10].getWeight( row + "-" + col );

            }
        }

        System.out.println( "\n" );

    }


    // Affiche une bar de progression dans le terminal
    private void progressBar( int value, int max ) {

        int progress = (int) ( value * 100 ) / max;
        String empty = " ";
        String full = "█";
        String bar = "\r|";
        
        for ( int i = 0; i < progress; i++ ) {
        
            bar += full;
        
        }

        for ( int i = 0; i < 100 - progress; i++ ) {
        
            bar += empty;
        
        }
        
        bar += "| " + progress + " %";
        System.out.printf( "%s", bar );
    
    }

}
