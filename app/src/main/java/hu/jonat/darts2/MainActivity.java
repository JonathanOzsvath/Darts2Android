package hu.jonat.darts2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity
        implements ScoreFragment.SendPlayers, ScoreFragment.ActivityToFragment,
        StatisticFragment.getPlayersFromActivity{

    String actTag,s;
    public Player player1;
    public Player player2;
    int countOk;
    ScoreFragment scoreFragment;
    StatisticFragment statisticFragment;
    ScoreFragment.ActivityToFragment activityToFragment;
    StatisticFragment.getPlayersFromActivity getPlayersFromActivity;

    DatabaseHelper dbHeplper;
    List<String> listUsers;

    @Override
    protected void onStart() {
        super.onStart();
        dbHeplper = new DatabaseHelper(getApplicationContext());
        try {
            dbHeplper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listUsers = dbHeplper.getAllUsers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        if (savedInstanceState != null) {
            actTag = savedInstanceState.getString("TAG");
            player1 = savedInstanceState.getParcelable("playerOne");
            player2 = savedInstanceState.getParcelable("playerTwo");
            countOk = savedInstanceState.getInt("countOk");
        }
        if (actTag == null || actTag == ScoreFragment.TAG) {
            scoreFragment = new ScoreFragment();
            ft.replace(R.id.layoutFragment, scoreFragment, ScoreFragment.TAG);
            actTag = ScoreFragment.TAG;
            try {
                activityToFragment = this;
            } catch (ClassCastException e) {
                throw new ClassCastException(this.toString()
                        + " must implement sendToFragment");
            }
            activityToFragment.sendToFragment(player1,player2,countOk);
        } else {
            statisticFragment = new StatisticFragment();
            ft.replace(R.id.layoutFragment, statisticFragment, StatisticFragment.TAG);
            actTag = StatisticFragment.TAG;
            try {
                getPlayersFromActivity = this;
            } catch (ClassCastException e) {
                throw new ClassCastException(this.toString()
                        + " must implement getPlayerFromActivity");
            }
            getPlayersFromActivity.getPlayerFromActivity(player1,player2,countOk);
        }
        ft.commit();
    }

    // a fragmentek közötti váltás
    public void onClick(View v) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        if (v.getId() == R.id.Statistic) {
            ft.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            if (statisticFragment == null) {
                statisticFragment = new StatisticFragment();
            }
            ft.replace(R.id.layoutFragment, statisticFragment, StatisticFragment.TAG);
            ft.commit();
            actTag = StatisticFragment.TAG;
        } else if (v.getId() == R.id.Score) {
            ft.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);

            if (scoreFragment == null) {
                scoreFragment = new ScoreFragment();
            }
            ft.replace(R.id.layoutFragment, scoreFragment, ScoreFragment.TAG);
            ft.commit();
            actTag = ScoreFragment.TAG;
        }
    }

    public void onClickOk(View v){
        scoreFragment.onClickOk(v);
    }

    public void onClickNumber(View v){
        scoreFragment.onClickNumber(v);
    }

    public void onClickClear(View v){
        scoreFragment.onClickClear(v);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TAG", actTag);
        outState.putInt("countOk", countOk);
        outState.putParcelable("playerOne", player1);
        outState.putParcelable("playerTwo", player2);
    }

    // Adatok küldése a StatisticFragmentnek
    @Override
    public void sendPlayer(Player playerOne, Player playerTwo, int countOk) {
        if (statisticFragment == null) {
            statisticFragment = new StatisticFragment();
        }

        player1 = playerOne;
        player2 = playerTwo;
        this.countOk = countOk;
        statisticFragment.getPlayers(playerOne, playerTwo, countOk);
    }

    // Adatok küldése a ScoreFragmentnek
    @Override
    public void sendToFragment(Player playerOne, Player playerTwo, int countOk) {
        if (scoreFragment == null){
            scoreFragment = new ScoreFragment();
        }

        scoreFragment.getMessage(player1, player2, countOk);
    }

    @Override
    public void getPlayerFromActivity(Player playerOne, Player playerTwo, int countOk) {
        if (statisticFragment == null){
            statisticFragment = new StatisticFragment();
        }

        if (scoreFragment == null){
            scoreFragment = new ScoreFragment();
        }

        player1 = playerOne;
        player2 = playerTwo;
        this.countOk = countOk;
        statisticFragment.getPlayers(player1, player2, countOk);
        scoreFragment.getMessage(player1, player2, countOk);
    }

}
