package hu.jonat.darts2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jonat on 2015. 09. 30..
 */
public class ScoreFragment extends Fragment {

    private static final int REQUEST_CODE = 1234;
    //????
    public ScoreFragment() {
        setArguments(new Bundle());
    }

    public static final String TAG = "ScoreFragment";

    public Player player1;
    public Player player2;
    String actTag, s;
    int countOk;
    EditText editText;
    TextView playerOneName, playerTwoName, playerOneScore, playerTwoScore;
    SendPlayers mCallback;

    ArrayList<Integer> setArray;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (SendPlayers) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TextClicked");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.score_fragment, null);

        if (savedInstanceState != null) {
            actTag = savedInstanceState.getString("TAG");
            player1 = savedInstanceState.getParcelable("playerOne");
            player2 = savedInstanceState.getParcelable("playerTwo");
            countOk = savedInstanceState.getInt("countOk");
        }

        if (countOk == 0) {
            player1 = new Player("Player1");
            player2 = new Player("Player2");
        }

        editText = (EditText) v.findViewById(R.id.etScore);
        playerOneName = (TextView) v.findViewById(R.id.playerOneName);
        playerTwoName = (TextView) v.findViewById(R.id.playerTwoName);
        playerOneScore = (TextView) v.findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) v.findViewById(R.id.playerTwoScore);

        //Toast.makeText(getActivity(), player1.getName().toString(), Toast.LENGTH_LONG).show();

        update();

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        mCallback.sendPlayer(player1, player2, countOk);
    }

    public void onClickOk(View v) {
        if (v.getId() == R.id.btnOk) {

            if (countOk == 0 && editText.getText() != null) {
                player1.setName(editText.getText().toString());
                playerOneName.setText(editText.getText());
                editText.getText().clear();
                editText.setHint("Adja meg a 2. játékos nevét");
            }
            if (countOk == 1 && editText.getText() != null) {
                player2.setName(editText.getText().toString());
                playerTwoName.setText(editText.getText());
                editText.getText().clear();
                editText.setHint("Az első játékos dob!");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            if ((countOk % 2) == 0 && countOk != 0 && editText.getText() != null) {
                if (Integer.parseInt(editText.getText().toString()) > 180) {
                    showAllertMessage("3 nyílból nem lehet " + editText.getText().toString() + " dobni");
                    editText.getText().clear();
                    countOk--;
                } else {
                    if (Integer.parseInt(editText.getText().toString()) > player1.getScore()) {
                        showAllertMessage("Bust! Kevesebbje lenne 0-nál");
                        editText.getText().clear();
                        countOk--;
                    } else {
                        int point = Integer.parseInt(editText.getText().toString());
                        int score = player1.getScore() - point;
                        player1.setScore(score);
                        editText.getText().clear();
                        editText.setHint("A második játékos dob!");
                        playerOneScore.setText(String.valueOf(player1.getScore()));
                        player1.setDarts(player1.getDarts() + 3);
                        player1.setCurrentLeg((double) (501 - player1.getScore()) / (player1.getDarts() / 3));
                        setArray = player1.getSetArray();
                        setArray.add(point);
                        player1.setSetArray(setArray);
                        player1.setCurrentSet(setSet(setArray));
                        setArray = player1.getMatchArray();
                        setArray.add(point);
                        player1.setMatchArray(setArray);
                        player1.setMatch(setMatch(setArray));
                    }
                }
            }
            if (((countOk % 2) == 1) && countOk != 1 && editText.getText() != null) {
                if ((Integer.parseInt(editText.getText().toString())) > 180) {
                    showAllertMessage("3 nyílból nem lehet " + editText.getText().toString() + " dobni");
                    editText.getText().clear();
                    countOk--;
                } else {
                    if (Integer.parseInt(editText.getText().toString()) > player2.getScore()) {
                        showAllertMessage("Bust! Kevesebbje lenne 0-nál");
                        editText.getText().clear();
                        countOk--;
                    } else {
                        int point = Integer.parseInt(editText.getText().toString());
                        int score = player2.getScore() - point;
                        player2.setScore(score);
                        editText.getText().clear();
                        editText.setHint("Az elso játékos dob!");
                        playerTwoScore.setText(String.valueOf(player2.getScore()));
                        player2.setDarts(player2.getDarts() + 3);
                        player2.setCurrentLeg((double) (501 - player2.getScore()) / (player2.getDarts() / 3));
                        setArray = player2.getSetArray();
                        setArray.add(point);
                        player2.setSetArray(setArray);
                        player2.setCurrentSet(setSet(setArray));
                        setArray = player2.getMatchArray();
                        setArray.add(point);
                        player2.setMatchArray(setArray);
                        player2.setMatch(setMatch(setArray));
                    }
                }
            }
        }
        countOk++;
        endLeg();
    }

    public void onClickNumber(View v) {
        switch (v.getId()) {
            case R.id.btnNull:
                editText.append("0");
                break;
            case R.id.btnOne:
                editText.append("1");
                break;
            case R.id.btnTwo:
                editText.append("2");
                break;
            case R.id.btnThree:
                editText.append("3");
                break;
            case R.id.btnFour:
                editText.append("4");
                break;
            case R.id.btnFive:
                editText.append("5");
                break;
            case R.id.btnSix:
                editText.append("6");
                break;
            case R.id.btnSeven:
                editText.append("7");
                break;
            case R.id.btnEight:
                editText.append("8");
                break;
            case R.id.btnNine:
                editText.append("9");
                break;
        }
    }

    public double setSet(ArrayList<Integer> setArray){
        int tmp = 0;
        for (Integer i : setArray){
            tmp += i;
        }
        return ((double) tmp / setArray.size());
    }

    public double setMatch(ArrayList<Integer> setArray){
        int tmp = 0;
        for (Integer i : setArray){
            tmp += i;
        }
        return ((double) tmp / setArray.size());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            for (String i : matches){
                editText.append(i);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickClear(View v) {
        editText.getText().clear();
    }

    protected void endLeg() {
        if (player1.getScore() == 0) {
            showAllertMessage(player1.getName() + "nyerte a leget!");
            player1.setLegs(player1.getLegs() + 1);

            showAllertMessage("Legs: " + player1.getName() + "-" + player2.getName() + ":" +
                    String.valueOf(player1.getLegs()) + "-" + String.valueOf(player2.getLegs()));

            if (((player1.getLegs() + player2.getLegs()) % 2) == 0) {
                countOk = 2;
            } else {
                countOk = 3;
            }
            start();
        } else if (player2.getScore() == 0) {
            showAllertMessage(player2.getName() + "nyerte a leget!");
            player2.setLegs(player2.getLegs() + 1);

            showAllertMessage("Legs: " + player1.getName() + "-" + player2.getName() + ":" +
                    String.valueOf(player1.getLegs()) + "-" + String.valueOf(player2.getLegs()));

            if (((player1.getLegs() + player2.getLegs()) % 2) == 0) {
                countOk = 2;
            } else {
                countOk = 3;
            }
            start();
        }
        endSet();
    }

    protected void endSet() {
        if (player1.getLegs() == 3) {
            showAllertMessage(player1.getName() + "nyerte a settet!");
            player1.setSets(player1.getSets() + 1);

            player1.setLegs(0);
            player2.setLegs(0);
            setArray = player1.getSetArray();
            setArray.clear();
            player1.setSetArray(setArray);
            setArray = player2.getSetArray();
            setArray.clear();
            player2.setSetArray(setArray);
            player1.setCurrentSet(0.00);
            player2.setCurrentSet(0.00);

            showAllertMessage("Sets: " + player1.getName() + "-" + player2.getName() + ":" +
                    String.valueOf(player1.getSets()) + "-" + String.valueOf(player2.getSets()));
        } else if (player2.getLegs() == 3) {
            showAllertMessage(player2.getName() + "nyerte a settet!");
            player2.setSets(player2.getSets() + 1);

            player1.setLegs(0);
            player2.setLegs(0);
            setArray = player1.getSetArray();
            setArray.clear();
            player1.setSetArray(setArray);
            setArray = player2.getSetArray();
            setArray.clear();
            player2.setSetArray(setArray);
            player1.setCurrentSet(0.00);
            player2.setCurrentSet(0.00);

            showAllertMessage("Sets: " + player1.getName() + "-" + player2.getName() + ":" +
                    String.valueOf(player1.getSets()) + "-" + String.valueOf(player2.getSets()));
        }
    }

    protected void start() {
        player1.setPreviousLeg(player1.getCurrentLeg());
        player2.setPreviousLeg(player2.getCurrentLeg());

        if (player1.getBestleg() < player1.getCurrentLeg()){
            player1.setBestleg(player1.getCurrentLeg());
        }
        if (player2.getBestleg() < player2.getCurrentLeg()){
            player2.setBestleg(player2.getCurrentLeg());
        }

        player1.setScore(501);
        player2.setScore(501);
        player1.setDarts(0);
        player2.setDarts(0);
        player1.setCurrentLeg(0);
        player2.setCurrentLeg(0);
        update();
    }

    protected void setStatics() {

    }

    protected void update() {
        playerOneName.setText(player1.getName().toString());
        playerTwoName.setText(player2.getName().toString());
        playerOneScore.setText(String.valueOf(player1.getScore()));
        playerTwoScore.setText(String.valueOf(player2.getScore()));
        if (countOk == 1) {
            editText.setHint("Az első játékos dob!");
        }
        if ((countOk % 2) == 0 && countOk != 0) {
            editText.setHint("Az elso játékos dob!");
        }
        if ((countOk % 2) == 1 && countOk != 1) {
            editText.setHint("A második játékos dob!");
        }
        if (countOk > 1) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TAG", TAG);
        outState.putInt("countOk", countOk);
        outState.putParcelable("playerOne", player1);
        outState.putParcelable("playerTwo", player2);
    }

    // Adatok küldése a StatisticFragmentnek
    public interface SendPlayers {
        public void sendPlayer(Player playerOne, Player playerTwo, int countOk);
    }

    // Adtatok megkapása a MainActivity-ből
    public interface ActivityToFragment {
        public void sendToFragment(Player playerOne, Player playerTwo, int countOk);
    }

    // Adtatok megkapása a MainActivity-ből
    public void getMessage(Player playerOne, Player playerTwo, int countOk) {
        player1 = playerOne;
        player2 = playerTwo;
        this.countOk = countOk;
    }

    private void showAllertMessage(final String aMessage) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());
        alertbox.setMessage(aMessage);
        alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertbox.show();
    }
}