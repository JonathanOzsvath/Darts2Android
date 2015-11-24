package hu.jonat.darts2;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import hu.jonat.darts2.Adapter.ListViewAdapter;
import hu.jonat.darts2.Data.ListViewItem;


/**
 * Created by jonat on 2015. 09. 30..
 */
public class StatisticFragment extends ListFragment {

    DecimalFormat format = new DecimalFormat("0.00");

    public static final String TAG = "StatisticFragment";

    private List<ListViewItem> mItems;

    public Player player1, player2;
    String actTag;
    int countOk;

    getPlayersFromActivity getPlayersFromActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            getPlayersFromActivity = (getPlayersFromActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement getPlayerFromActivity");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.score_fragment, null);
        return v;
    }*/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            actTag = savedInstanceState.getString("TAG");
            player1 = savedInstanceState.getParcelable("playerOne");
            player2 = savedInstanceState.getParcelable("playerTwo");
            countOk = savedInstanceState.getInt("countOk");
        }

        mItems = new ArrayList<ListViewItem>();

        Resources resources = getResources();

        mItems.add(new ListViewItem("", player1.getName(), player2.getName()));
        mItems.add(new ListViewItem("Score",String.valueOf(player1.getScore()),String.valueOf(player2.getScore())));
        mItems.add(new ListViewItem("Sets",String.valueOf(player1.getSets()),String.valueOf(player2.getSets())));
        mItems.add(new ListViewItem("Legs",String.valueOf(player1.getLegs()),String.valueOf(player2.getLegs())));
        mItems.add(new ListViewItem("Darts",String.valueOf(player1.getDarts()),String.valueOf(player2.getDarts())));
        mItems.add(new ListViewItem("Best leg",String.valueOf(String.format("%.2f",player1.getBestleg())),String.valueOf(String.format("%.2f", player2.getBestleg()))));
        mItems.add(new ListViewItem("Previous leg",String.valueOf(String.format("%.2f",player1.getPreviousLeg())), String.valueOf(String.format("%.2f", player2.getPreviousLeg()))));
        mItems.add(new ListViewItem("Current leg",String.valueOf(String.format("%.2f",player1.getCurrentLeg())), String.valueOf(String.format("%.2f", player2.getCurrentLeg()))));
        mItems.add(new ListViewItem("Current set",String.valueOf(String.format("%.2f", player1.getCurrentSet())), String.valueOf(String.format("%.2f", player2.getCurrentSet()))));
        mItems.add(new ListViewItem("Match",String.valueOf(String.format("%.2f", player1.getMatch())), String.valueOf(String.format("%.2f", player2.getMatch()))));

        setListAdapter(new ListViewAdapter(getActivity(), mItems));


        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPlayersFromActivity.getPlayerFromActivity(player1,player2,countOk);
    }

    // Adtatok megkapása a ScoreFragment-ből és MainActivity-ből
    public void getPlayers(Player playerOne, Player playerTwo, int coiuntOk){
        player1 = playerOne;
        player2 = playerTwo;
        this.countOk = coiuntOk;
    }

    public interface getPlayersFromActivity{
        public void getPlayerFromActivity(Player playerOne, Player playerTwo, int countOk);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TAG", TAG);
        outState.putInt("countOk", countOk);
        outState.putParcelable("playerOne", player1);
        outState.putParcelable("playerTwo", player2);
    }
}
