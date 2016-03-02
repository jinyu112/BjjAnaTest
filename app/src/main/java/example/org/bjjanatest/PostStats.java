package example.org.bjjanatest;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import example.org.bjjanatest.db.TournDataSource;


public class PostStats extends AsyncTask<String, String, String> {
    //initialize email address
    private static String emailAddress="";
    private static int tournLen=0;
    private static int totalPts=0;
    private static int totalPtsAllowed=0;
    private static double totalTime=0.0;
    private static double avgTimePerWeek=0.0;
    private static double tdSucPerc=0.0;
    private static double passSucPerc=0.0;
    private static double sweepSucPerc=0.0;
    private static double subSucPerc=0.0;

    static JSONParser jsonParser = new JSONParser();

    //php file that posts email to database on server
    private static String urlPostStats = "http://vbaexceltutorial.com/write_email.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    static SessionManagement session;

    public PostStats(String emailIn, SessionManagement sessionIn) {
        this.emailAddress = emailIn;
        this.session = sessionIn;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Posting email to server
     * */
    protected String doInBackground(String... args) {


        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", emailAddress));
        params.add(new BasicNameValuePair("tournLen", Integer.toString(tournLen)));
        params.add(new BasicNameValuePair("totalPts",Integer.toString(totalPts) ));
        params.add(new BasicNameValuePair("totalPtsAllowed",Integer.toString(totalPtsAllowed) ));
        params.add(new BasicNameValuePair("totalTime", Double.toString(Math.round(totalTime*10)/10) ));
        params.add(new BasicNameValuePair("avgTimePerWeek", Double.toString(Math.round(avgTimePerWeek*10)/10) ));
        params.add(new BasicNameValuePair("tdSucPerc", Double.toString(Math.round(tdSucPerc*1000)/1000) ));
        params.add(new BasicNameValuePair("passSucPerc", Double.toString(Math.round(passSucPerc*1000)/1000) ));
        params.add(new BasicNameValuePair("sweepSucPerc", Double.toString(Math.round(sweepSucPerc*1000)/1000) ));
        params.add(new BasicNameValuePair("subSucPerc", Double.toString(Math.round(subSucPerc*1000)/1000) ));
        //Log.i("BJJ", "passSucPerc " + passSucPerc);
        // getting JSON Object
        // Note that post email url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlPostStats,
                "POST", params);

        // check for success tag
        try {
            int success = 0;
            success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                //session.setEmailPosted();
                //Log.i("BJJ", "Successfully posted to database");
            } else {
                // failed to post email
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done
    }


    public void setEmailAddress(String emailIn) {
        this.emailAddress = emailIn;
    }
    public void setTotalPts(int totalPtsIn) {
        this.totalPts=totalPtsIn;
    }
    public void setTournLen(int tournLenIn) {
        this.tournLen=tournLenIn;
    }
    public void setTotalPtsAllowed(int totalPtsAllowedIn) {
        this.totalPtsAllowed=totalPtsAllowedIn;
    }
    public void setTotalTime(double totalTimeIn) {
        this.totalTime=totalTimeIn;
    }
    public void setAvgTimePerWeek(double avgTimePerWeekIn) {
        this.avgTimePerWeek=avgTimePerWeekIn;
    }
    public void setTdSucPerc(double tdSucPercIn) {
        this.tdSucPerc = tdSucPercIn;
    }
    public void setPassSucPerc(double passSucPercIn) {
        this.passSucPerc = passSucPercIn;
    }
    public void setSweepSucPerc(double sweepSucPercIn) {
        this.sweepSucPerc = sweepSucPercIn;
    }
    public void setSubSucPerc(double subSucPercIn) {
        this.subSucPerc = subSucPercIn;
    }

    public String getEmailAddress() {return this.emailAddress;}



}