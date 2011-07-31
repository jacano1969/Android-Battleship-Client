package edu.neumont.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.neumont.battleship.http.BattleshipServerConnector;
import edu.neumont.battleship.http.XMLResponse;
import edu.neumont.battleship.model.Direction;
import edu.neumont.battleship.model.PlayerType;
import edu.neumont.battleship.model.ShipType;

public class BattleshipActivity extends Activity {
	public static String TAG;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TAG = getText(R.string.app_name).toString();
		TextView tv = (TextView) findViewById(R.id.textview);
		Log.v(TAG, "in oncreate");
		
		//set up button to go to Home
		Button next = (Button) findViewById(R.id.button1);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Log.i(TAG, "button1 was pressed!");
                Intent myIntent = new Intent(view.getContext(), BattleshipHome.class);
                startActivityForResult(myIntent, 0);
            }
        });

		try {
			
			XMLResponse response = BattleshipServerConnector.newGame("Joe",
					PlayerType.Edison,tv);
			Log.i(TAG, "Posted, here's response: ");
			Log.i(TAG, response.getRawXML() + "");
			tv.setText("\r\n"+tv.getText()+response.getRawXML());
			response = BattleshipServerConnector.placeShip("A1",
					Direction.DOWN, ShipType.Battleship,tv);
			Log.i(TAG, "Posted, here's response: ");
			Log.i(TAG, response.getRawXML() + "");
			tv.setText(tv.getText()+response.getRawXML());

			Log.i(TAG, "this is useless code");
			
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(TAG, "I was paused!");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "I was killed!");
	}
}
