package hu.jonat.darts2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jonat on 2015. 10. 14..
 */
public class Player implements Parcelable {

    private String name;
    private int score;
    private int sets;
    private int legs;
    private int darts;
    private double bestleg;
    private double previousLeg;
    private double currentLeg;
    private double currentSet;
    private double match;
    private ArrayList setArray;
    private ArrayList matchArray;

    public Player(String name) {
        this.name = name;
        score = 501;
        sets = 0;
        legs = 0;
        darts = 0;
        bestleg = 0;
        previousLeg = 0;
        currentLeg = 0;
        currentSet = 0;
        match = 0;
        setArray = new ArrayList();
        matchArray = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getBestleg() {
        return bestleg;
    }

    public void setBestleg(double bestleg) {
        this.bestleg = bestleg;
    }

    public double getPreviousLeg() {
        return previousLeg;
    }

    public void setPreviousLeg(double previousLeg) {
        this.previousLeg = previousLeg;
    }

    public double getCurrentLeg() {
        return currentLeg;
    }

    public void setCurrentLeg(double currentLeg) {
        this.currentLeg = currentLeg;
    }

    public double getCurrentSet() {
        return currentSet;
    }

    public void setCurrentSet(double currentSet) {
        this.currentSet = currentSet;
    }

    public double getMatch() {
        return match;
    }

    public void setMatch(double match) {
        this.match = match;
    }

    public int getDarts() {
        return darts;
    }

    public void setDarts(int darts) {
        this.darts = darts;
    }

    public ArrayList getSetArray() {
        return setArray;
    }

    public void setSetArray(ArrayList setArray) {
        this.setArray = setArray;
    }

    public ArrayList getMatchArray() {
        return matchArray;
    }

    public void setMatchArray(ArrayList matchArray) {
        this.matchArray = matchArray;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeInt(sets);
        dest.writeInt(legs);
        dest.writeInt(darts);
        dest.writeDouble(bestleg);
        dest.writeDouble(previousLeg);
        dest.writeDouble(currentLeg);
        dest.writeDouble(currentSet);
        dest.writeDouble(match);
        dest.writeArray(setArray.toArray());
        dest.writeArray(matchArray.toArray());
    }

    private Player(Parcel in){
        this.name = in.readString();
        this.score = in.readInt();
        this.sets = in.readInt();
        this.legs = in.readInt();
        this.darts = in.readInt();
        this.bestleg = in.readDouble();
        this.previousLeg = in.readDouble();
        this.currentLeg = in.readDouble();
        this.currentSet = in.readDouble();
        this.match = in.readDouble();
        this.setArray = in.readArrayList(Integer.class.getClassLoader());
        this.matchArray = in.readArrayList(Integer.class.getClassLoader());
    }

    public static final Creator<Player> CREATOR = new Creator<Player>(){
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        public Player[] newArray(int size){
            return new Player[size];
        }
    };
}
