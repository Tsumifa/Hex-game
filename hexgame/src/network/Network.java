package src.network;

import src.config.Config;

public class Network {

    private Channel sendChannel;
    private Channel getChannel;

    public Network ( Config configManager ) {

        this.sendChannel = new Channel(
            configManager.getStringValue("serverAddress" ),
            configManager.getStringValue("nonce" ),
            configManager.getStringValue("channelPostName" ),
            configManager.getIntValue("channelScanDelay" )
        );

        this.getChannel = new Channel(
            configManager.getStringValue( "serverAddress" ),
            configManager.getStringValue( "nonce" ),
            configManager.getStringValue( "channelGetName" ),
            configManager.getIntValue( "channelScanDelay" )
        );

        this.sendChannel.connect();
        this.getChannel.connect();

    }

    // tente d'envoyer le message en boucle jusqu'à ce que ça marche
    // S'arrête au bout d'un certain nombre de répétitions
    public Boolean sendMove( int[] coordonates ) {

        String move = "" + coordonates[1] + " " + coordonates[0];
        Boolean send = false;
        int iterations = 0;

        while ( send == false ) {

            if ( this.sendChannel.send( move ) ) send = true;
            if ( iterations > 50 ) return false;
            iterations++;

        }

        return true;
    }


    private Boolean formatMove( String move ) {
        String[] split = move.split( " " );
        int[] coordonates = new int[2];

        try {
            
            coordonates[0] = Integer.parseInt( split[0] );
            coordonates[1] = Integer.parseInt( split[1] );

        } catch ( Exception e ) {

            return false;
        }

        return true;
    }

    // Récupère l'ensemble des coups placés dans le chat
    // S'arrête au bout de 0.5s 
    // Si le message est de la forme INT INT ou SWAP alors l'enregistre
    public String getMove() {

        String s = "";
        String opponentMove = "";
        
        while ( true ) {
        
            try {
            
                s = this.getChannel.getNext();
            
            } catch ( Exception e) {

            }

            System.out.println(s);
            
            if ( s.equals( "SWAP"  ) && !s.equals( opponentMove ) ) {

                opponentMove = s;

            } else if ( formatMove( s ) && !s.equals( opponentMove ) ) {

                opponentMove = s;

            } else if ( s.equals( opponentMove ) ) {

                break;
            }
        }
        return opponentMove;
    }
}
