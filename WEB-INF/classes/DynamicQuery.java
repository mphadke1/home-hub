import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.mongodb.*;
import java.lang.reflect.Type;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import com.google.gson.reflect.TypeToken;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DynamicQuery")
public class DynamicQuery extends HttpServlet
{
    ArrayList<MatchExpression> matchList;
    ArrayList<SortExpression> sortList;
    ArrayList<ProjectExpression> projectList;
    ArrayList<GenericExpression> genericExpressions;
    DynamicQuery(ArrayList<GenericExpression> genericExpressions) {
        matchList = new ArrayList<MatchExpression>();
        sortList = new ArrayList<SortExpression>();
        projectList = new ArrayList<ProjectExpression>();
        this.genericExpressions = genericExpressions;

        for(GenericExpression expression : genericExpressions) {
            if(expression.type == "filter") {
                matchList.add(new MatchExpression(
                    expression.type,
                    expression.field,
                    expression.operator,
                    expression.value
                ));
            }
            if(expression.type == "sort") {
                sortList.add(new SortExpression(
                    expression.type,
                    expression.field,
                    expression.operator
                ));
            }
        }

    }

    public void executeQuery() {
        System.out.println("EXECUTE");
        boolean matchDone = false;
        boolean sortDone = false;
        boolean projectDone = false;

        MongoDBDataStoreUtilities.getConnection();
        ArrayList<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();

        for(GenericExpression expression : genericExpressions) {
            switch(expression.type) {
                case "project":
                    if(projectDone == false) {
                        BasicDBObject projectObject = new BasicDBObject("_id", 0);
                        for(ProjectExpression exp : projectList) {
                            projectObject.put(exp.field, 1);
                        }
                        pipeline.add(new BasicDBObject("$project", projectObject));
                        projectDone = true;
                    }
                    break;
                case "regex":
                    break;
                case "filter":
                    break;
                case "limit":
                    break;
                case "sort":
                    break;
                case "group":
                    break;
            }

            try {
                AggregationOutput output = MongoDBDataStoreUtilities.myReviews.aggregate(pipeline);
                System.out.println("RESULT");
                for (DBObject res : output.results()) {
                    System.out.println(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

class MatchExpression {
    public String type;
    public String field;
    public String operator;
    public String value;

    MatchExpression(
        String type,
        String field,
        String operator,
        String value
    ) {
        this.type = type;
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}

class SortExpression {
    public String type;
    public String field;
    public String operator;

    SortExpression(
        String type,
        String field,
        String operator
    ) {
        this.type = type;
        this.field = field;
        this.operator = operator;
    }
}

class ProjectExpression {
    public String type;
    public String field;

    ProjectExpression(
        String type,
        String field
    ) {
        this.type = type;
        this.field = field;
    }
}