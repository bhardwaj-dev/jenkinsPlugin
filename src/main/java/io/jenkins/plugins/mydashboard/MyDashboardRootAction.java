package io.jenkins.plugins.mydashboard;

import hudson.model.Hudson;
import hudson.util.HttpResponses;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerRequest;
import jenkins.model.Jenkins;
import io.jenkins.plugins.mydashboard.Messages;

import javax.persistence.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MyDashboardRootAction implements hudson.model.RootAction
{
    private List<LinkAction> links = new ArrayList<LinkAction>();
    private  static String url = "jdbc:mysql://localhost:3306/sql_hr";
    private static String user = "root";
    private static String password = "root";
    private String status ;
    ResultSet result ;
    Runner runner = new Runner();





    public MyDashboardRootAction (List<LinkAction> links){
        //set links
        this.links = links;
        //set db connection
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection =  DriverManager.getConnection(url,user,password);
//            this.status = "Connection is Successful";
//            String query = "SELECT * FROM sql_hr.employees limit 5";
//            Statement statement = connection.prepareStatement(query);
//            this.result  = statement.executeQuery(query);
//            this.result.next();
//
//        }
//        catch(ClassNotFoundException e){
//            System.console().printf("Failed");
//            e.printStackTrace();
//        }
//        catch(SQLException e){
//            e.printStackTrace();
//        }
    }





    @Override
    public String getDisplayName()
    {
        return Messages.MyDashboardRootAction_DisplayName();
    }

    @Override
    public String getIconFileName()
    {
        return "/images/48x48/terminal.png";
    }

    @Override
    public String getUrlName()
    {
        return "mydashboard"; // the url path
    }

    public HttpResponse doDynamic(StaplerRequest request) {
        List<String> params = getRequestParams(request);


        switch (params.get(0)) {
            case "get-todos":
                return getTodos();

        }

        return HttpResponses.errorJSON("not found");
    }

    private List<String> getRequestParams(StaplerRequest request) {
        String restOfPath = request.getRestOfPath();
        String[] pathTokens = restOfPath.split("/");

        List<String> params = new ArrayList<>();

        for (String pathToken : pathTokens) {
            if (pathToken.length() > 0) {
                params.add(pathToken);
            }
        }

        return params;
    }

    private HttpResponse getTodos() {
        JSONObject json = new JSONObject();
        JSONObject authentication = new JSONObject();

        authentication.element("name",Jenkins.getAuthentication().getName());

        int i= 0;
        for (LinkAction link:this.links){
            JSONObject childJson = new JSONObject();
            childJson.put("getDisplayName",link.getDisplayName());
            childJson.put("getUrlName",link.getUrlName());
            childJson.put("status",this.status);
            try {

                childJson.put("employee_id", this.result.getInt(1));
                childJson.put("FirstName",this.result.getString(2));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            json.put(Integer.toString(i),childJson);
            i++;
        }


        return HttpResponses.okJSON(runner.getCustomers());
    }
}