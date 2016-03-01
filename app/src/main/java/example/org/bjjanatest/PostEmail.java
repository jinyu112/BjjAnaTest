package example.org.bjjanatest;

import android.os.AsyncTask;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PostEmail extends AsyncTask<String, String, String> {

    // Session Manager Class
    static SessionManagement session;

    //initialize email address
    private static String emailAddress="";

    static JSONParser jsonParser = new JSONParser();

    //php file that posts email to database on server
    private static String urlPostEmail = "http://vbaexceltutorial.com/write_email.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    public PostEmail(String emailIn, SessionManagement sessionIn) {
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

        // getting JSON Object
        // Note that post email url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlPostEmail,
                "POST", params);

        // check for success tag
        try {
            int success = 0;
            success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                session.setEmailPosted();
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

    public String getEmailAddress() {return this.emailAddress;}
}
