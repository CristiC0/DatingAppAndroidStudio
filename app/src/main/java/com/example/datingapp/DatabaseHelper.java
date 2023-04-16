package com.example.datingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.datingapp.Match.Match;
import com.example.datingapp.auth.CurrentUser;
import com.example.datingapp.auth.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    final String USER_TABLE="USERS";
    final String USER_ID_COLUMN="USER_ID";
    final String USER_USERNAME_COLUMN="USER_USERNAME";
    final String USER_GENDER_COLUMN="USER_GENDER";
    final String USER_PASSWORD_COLUMN="USER_PASSWORD";

    final String MATCHES_TABLE="MATCHES";
    final String MATCHES_ID_COLUMN="MATCHES_ID";
    final String MATCHES_USERNAME_COLUMN="MATCHES_USERNAME";
    final String MATCHES_GENDER_COLUMN="MATCHES_GENDER";
    final String MATCHES_IMAGE_COLUMN="MATCHES_IMAGE";

    final String SWIPES_TABLE="SWIPES";
    final String SWIPES_ID_COLUMN="SWIPES_ID";
    final String SWIPES_FROM_COLUMN="SWIPES_FROM";
    final String SWIPES_TO_COLUMN="SWIPES_TO";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "dating.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableStatement="CREATE TABLE "+USER_TABLE+" ( "+USER_ID_COLUMN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_USERNAME_COLUMN+" TEXT, " + USER_PASSWORD_COLUMN  +" TEXT, "+USER_GENDER_COLUMN+" TEXT)";
        String createMatchesTableStatement="CREATE TABLE "+MATCHES_TABLE+" ( "+MATCHES_ID_COLUMN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+MATCHES_USERNAME_COLUMN+" TEXT, " + MATCHES_IMAGE_COLUMN+" TEXT, "+MATCHES_GENDER_COLUMN+" TEXT)";
        String createSwipesTableStatement="CREATE TABLE "+SWIPES_TABLE+" ( "+SWIPES_ID_COLUMN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+SWIPES_FROM_COLUMN+" INTEGER, " + SWIPES_TO_COLUMN+" INTEGER)";

        db.execSQL(createUserTableStatement);
        db.execSQL(createMatchesTableStatement);
        db.execSQL(createSwipesTableStatement);
        addDefaultMatches(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean createUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(USER_USERNAME_COLUMN,user.getUsername());
        cv.put(USER_PASSWORD_COLUMN,user.getPassword());
        cv.put(USER_GENDER_COLUMN,user.getGender());

        long insert=db.insert(USER_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        return true;
    }

    public void updateUser(User newUser){
        SQLiteDatabase db=this.getWritableDatabase();
        String updateQuery="UPDATE "+USER_TABLE+" SET "+USER_USERNAME_COLUMN+"='"+newUser.getUsername()+"', "+USER_PASSWORD_COLUMN+"='"+newUser.getPassword()+"', "+USER_GENDER_COLUMN+"='"+newUser.getGender()+"' WHERE "+USER_ID_COLUMN+"= "+CurrentUser.getInstance().getUser().getId();
        db.execSQL(updateQuery);
    }

    public List<User> getListOfUsers(){
        List<User> resultList=new ArrayList<>();
        String queryList="SELECT * FROM " + USER_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(queryList,null);

        if(cursor.moveToFirst()){
            do {
                int userID=cursor.getInt(0);
                String userUsername=cursor.getString(1);
                String userPassword=cursor.getString(2);
                String userGender=cursor.getString(3);

                User newUser=new User(userID,userUsername,userPassword,userGender);
                resultList.add(newUser);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return resultList;
    }

    public boolean existsUser(User user){
        List<User> allUsers=getListOfUsers();
        for (User u: allUsers) {
            if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                CurrentUser.getInstance().setUser(u);
                return true;
            }
        }
        return false;
    }

    public boolean existsSwipe(Swipe swipe){
        List<Swipe> allSwipes=getListOfSwipes(CurrentUser.getInstance().getUser().getId());
        for (Swipe s: allSwipes) {
            if(s.getFrom()==swipe.getFrom() && s.getTo()==swipe.getTo() ) {
                return true;
            }
        }
        return false;
    }


    public boolean createSwipe(Swipe swipe){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(SWIPES_FROM_COLUMN,swipe.getFrom());
        cv.put(SWIPES_TO_COLUMN,swipe.getTo());
        if(existsSwipe(swipe)){
            return false;}
        long insert=db.insert(SWIPES_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        return true;
    }

    public void deleteSwipe(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String deleteQuery="DELETE FROM "+SWIPES_TABLE+" WHERE "+SWIPES_FROM_COLUMN+"="+CurrentUser.getInstance().getUser().getId()+" AND "+SWIPES_TO_COLUMN+"="+id;
        db.execSQL(deleteQuery);
    }

    public List<Swipe> getListOfSwipes(int userId){
        List<Swipe> resultList=new ArrayList<>();

        String queryList="SELECT * FROM " + SWIPES_TABLE+" WHERE "+SWIPES_FROM_COLUMN +"="+userId+"";
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(queryList,null);

        if(cursor.moveToFirst()){
            do {
                int swipeID=cursor.getInt(0);
                int swipeFrom=cursor.getInt(1);
                int swipeTo=cursor.getInt(2);

                Swipe newSwipe=new Swipe(swipeID,swipeFrom,swipeTo);
                resultList.add(newSwipe);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return resultList;
    }


    public List<Match> getListOfMatches(String searchedGenre){
        List<Match> resultList=new ArrayList<>();
        String whereClause="";
        if(searchedGenre!=null){
            whereClause=searchedGenre.equals("Male") ? " WHERE "+MATCHES_GENDER_COLUMN+"='Male'":
                    searchedGenre.equals("Female") ? " WHERE "+MATCHES_GENDER_COLUMN+"='Female'": "";

        }

        String queryList="SELECT * FROM " + MATCHES_TABLE+whereClause;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(queryList,null);

        if(cursor.moveToFirst()){
            do {
                int matchID=cursor.getInt(0);
                String matchUsername=cursor.getString(1);
                String matchImage=cursor.getString(2);
                String matchGender=cursor.getString(3);


                Match newMatch=new Match(matchID,matchUsername,matchImage,matchGender);
                resultList.add(newMatch);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return resultList;
    }

    public Match getMatch(int matchID){
        List<Match> matches=getListOfMatches(null);
        for (Match m:matches) {
            if(m.getId()==matchID) {
                return m;
            }
        }
        return null;
    }


    private void addDefaultMatches(SQLiteDatabase db){
        ArrayList<Match> defaultMatches=new ArrayList<>();

        defaultMatches.add(new Match("Gene Gibson","https://images.pexels.com/photos/428328/pexels-photo-428328.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Bailey Roth","https://images.pexels.com/photos/9950569/pexels-photo-9950569.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Claire Daugherty","https://images.pexels.com/photos/10057618/pexels-photo-10057618.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Leslie Murray","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Siobhan Morse","https://images.pexels.com/photos/2379004/pexels-photo-2379004.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Callum Walter","https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Hayden Booker","https://images.pexels.com/photos/614810/pexels-photo-614810.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Xavier Simon","https://images.pexels.com/photos/1516680/pexels-photo-1516680.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Malik Robles","https://images.pexels.com/photos/2182970/pexels-photo-2182970.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));
        defaultMatches.add(new Match("Zeeshan Blankenship","https://images.pexels.com/photos/868113/pexels-photo-868113.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Male"));

        defaultMatches.add(new Match("Rebecca Middleton","https://images.pexels.com/photos/2787341/pexels-photo-2787341.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Tasnim Mckay","https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Penelope Sharpe","https://images.pexels.com/photos/1239288/pexels-photo-1239288.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Kate Holloway","https://images.pexels.com/photos/2613260/pexels-photo-2613260.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Thalia Patterson","https://images.pexels.com/photos/2811089/pexels-photo-2811089.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Sydney Lindsay","https://images.pexels.com/photos/2104252/pexels-photo-2104252.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Safiyyah Crawfordn","https://images.pexels.com/photos/1572878/pexels-photo-1572878.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Anastasia Keith","https://images.pexels.com/photos/1115697/pexels-photo-1115697.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Brooke Duke","https://images.pexels.com/photos/2167673/pexels-photo-2167673.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));
        defaultMatches.add(new Match("Addie Blanchard","https://images.pexels.com/photos/3034903/pexels-photo-3034903.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2","Female"));

        defaultMatches.forEach(match -> {
            ContentValues cv=new ContentValues();

            cv.put(MATCHES_USERNAME_COLUMN,match.getName());
            cv.put(MATCHES_IMAGE_COLUMN,match.getImage());
            cv.put(MATCHES_GENDER_COLUMN,match.getGender());

            db.insert(MATCHES_TABLE,null,cv);
        });

    }
}
