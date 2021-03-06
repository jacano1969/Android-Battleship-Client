package edu.neumont.battleship;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.neumont.battleship.exceptions.BattleshipIOException;
import edu.neumont.battleship.exceptions.InvalidXMLException;
import edu.neumont.battleship.http.BattleshipServerConnector;
import edu.neumont.battleship.http.results.ActionResult;
import edu.neumont.battleship.http.results.GameListResult;
import edu.neumont.battleship.xml.XMLGame;

public class BattleshipJoinGame extends ListActivity
{
	public static final String TAG = BattleshipActivity.TAG;
	private static final boolean LOCAL_LOGV = false;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		refresh();
	}

	private void refresh()
	{
		if (LOCAL_LOGV) {
			Log.v(TAG,"Menu Item Refresh was clicked!");
		}
		int[] games = getGames();
		if(games.length == 0)
		{
			Toast.makeText(BattleshipJoinGame.this, "No games found!", Toast.LENGTH_LONG).show();
		}
		else
		{
			String[] strGames = new String[games.length];
			for(int i=0; i<games.length; i++)
				strGames[i] = Integer.toString(games[i]);
			this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strGames));
		}
	}

	private int[] getGames()
	{
		try
		{
			GameListResult result = BattleshipServerConnector.getGameList();
			//select the gameIds
			List<XMLGame> games = result.getGameList();
			int[] gameIds = new int[result.NumOfGames()];
			for (int i=0; i<gameIds.length; i++)
			{
				gameIds[i] = games.get(i).getGameId();
			}
			return gameIds;
		} catch (Exception e)
		{
			Log.e(TAG, "Error getting game list from server", e);
		}
		return new int[0];
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.joingamemenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.refresh:
			refresh();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = new Intent(BattleshipJoinGame.this,BattleshipGameBoard.class);
		intent.putExtra(getString(R.string.selectedgame), l.getItemAtPosition(position).toString());
		startActivity(intent);
	}
}
