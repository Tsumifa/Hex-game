package src.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import javax.net.ssl.HttpsURLConnection;

public class Channel {

	private int delay;
    private final String channelName;
	private Queue<byte[]> events = new ArrayDeque<>();

    private int id = 0;
	private String addr;
	private String nonce;
	private static final Random r = new Random();
	

	public Channel( String addr, String nonce, String channelName, int scanDelay ) {

        this.addr = addr;
        this.nonce = nonce;
		this.channelName = channelName;
		this.delay = scanDelay;

	}


    private String genNonce() {
		
		char[] sb = new char[64];

		for( int i = 0; i < 64; i++ ) {
		
			sb[i] = nonce.charAt( r.nextInt( 62 ) );
		}
		
		return new String(sb);

	}
	
	private byte[] serializeToString( String o ) throws IOException {

		return o.getBytes() ;

	}	
	
	private void parseEvent( byte[] bb, int i ) {

		StringBuilder sb = new StringBuilder();
		
        while( i < bb.length ) {

			byte b = bb[i];
		
            if( b == '|' ) break;
			sb.append( (char) b );
			i++;

		}

		i++;
		
        if ( i < bb.length ) {
		
            int size = Integer.parseInt( sb.toString() );
			byte[] buff = java.util.Arrays.copyOfRange( bb, i, i + size );
			events.add( buff );
			parseEvent( bb, i + size );
		
        } else {
		
            id = Integer.parseInt( sb.toString() );

		}
	}
	
	public void connect() {

		String id2 = "";
        URL u;
		
        if ( id > 0 ) {

			id2 = "?id=" + id;

		}
        
		try {
		
            u = new URL( addr + channelName + id2 );
			URLConnection uc = u.openConnection();
			InputStream is = uc.getInputStream();
			byte[] bs = is.readAllBytes();
			parseEvent( bs,0 );
		
        } catch ( IOException e ) {
			
            e.printStackTrace();

		}
	}
	
	public String getNext() {
		
		long start = System.currentTimeMillis();
		long end  = start + this.delay;
		long time = start;

        while ( events.isEmpty() && time < end ) {

			connect();
			time = System.currentTimeMillis();

		}

		return new String( events.poll() );

	}
	
	
	
	public Boolean send( String s )  {

		try {
			
            String non = genNonce();
			URL u = new URL( this.addr + this.channelName + "?nonce=" + non );

			HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();
			uc.setRequestMethod( "POST" );
			uc.setDoOutput( true );

            OutputStream os = uc.getOutputStream();
			os.write( serializeToString( s ) );
			os.close();
			uc.connect();
			uc.getInputStream();		
            return true;

		} catch ( Exception e ) {

            return false;
			
		}
    }
	
}

