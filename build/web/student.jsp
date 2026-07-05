<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.esukarelawan.model.ActivityBean" %>
<%@ page import="com.esukarelawan.model.ParticipationBean" %>

<%
    String name = (String) request.getAttribute("studentName");

    Number totalHoursObj = (Number) request.getAttribute("totalHours");
    Number totalActivitiesObj = (Number) request.getAttribute("totalActivities");

    int totalHours = totalHoursObj != null ? totalHoursObj.intValue() : 0;
    int totalActivities = totalActivitiesObj != null ? totalActivitiesObj.intValue() : 0;

    List<ActivityBean> activities =
            (List<ActivityBean>) request.getAttribute("activities");

    List<ParticipationBean> history =
            (List<ParticipationBean>) request.getAttribute("history");

    HashMap<Integer, String> joinedStatus =
            (HashMap<Integer, String>) request.getAttribute("joinedStatus");

    if (name == null) {
        name = "Student";
    }

    Number medalsEarnedObj =
        (Number) request.getAttribute("medalsEarned");

Number nextMedalHoursObj =
        (Number) request.getAttribute("nextMedalHours");

int medalsEarned =
        medalsEarnedObj != null ? medalsEarnedObj.intValue() : 0;

int nextMedalHours =
        nextMedalHoursObj != null ? nextMedalHoursObj.intValue() : 20;

int progress = 0;

if (nextMedalHours > 0) {
    progress = (int) (((double) totalHours / nextMedalHours) * 100);

    if (progress > 100) {
        progress = 100;
    }
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>E-Sukarelawan Student Dashboard</title>

    <style>
        *{
            box-sizing:border-box;
        }

        body{
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg,#2f7d32,#8bc34a);
            margin:0;
            padding:25px 0;
        }

        .container{
            width:82%;
            max-width:1250px;
            margin:auto;
        }

        .card{
            background:white;
            border-radius:14px;
            padding:22px;
            margin-bottom:18px;
            box-shadow:0 5px 14px rgba(0,0,0,0.20);
        }

        .header{
            display:flex;
            justify-content:space-between;
            align-items:flex-start;
            padding:24px 22px;
        }

        .header h1{
            color:#006400;
            font-size:34px;
            letter-spacing:1px;
            margin:0 0 22px 0;
            font-weight:800;
        }

        .header h3{
            margin:0;
            font-size:18px;
            font-weight:bold;
        }

        .header p{
            margin:3px 0 0 0;
            font-size:16px;
        }

        .logout{
            background:#006400;
            color:white;
            padding:12px 24px;
            border-radius:8px;
            text-decoration:none;
            font-weight:bold;
            display:inline-block;
        }

        .logout:hover{
            background:#004d00;
        }

        .stats{
            display:grid;
            grid-template-columns:repeat(3, 1fr);
            gap:20px;
            margin-bottom:18px;
        }

        .stat-card{
            background:white;
            border-radius:14px;
            text-align:center;
            padding:26px 15px;
            box-shadow:0 5px 14px rgba(0,0,0,0.20);
        }

        .stat-card h3{
            color:#006400;
            margin:0 0 12px 0;
            font-size:18px;
        }

        .stat-card h2{
            margin:0;
            font-size:26px;
            color:#222;
        }

        .section-title{
            color:#006400;
            margin:0 0 16px 0;
            font-size:26px;
        }

        .progress-bg{
            background:#d9d9d9;
            height:25px;
            border-radius:18px;
            overflow:hidden;
            width:100%;
            margin-bottom:18px;
        }

        .progress-bar{
            background:#28a745;
            height:100%;
            width:<%= progress %>%;
            border-radius:18px 0 0 18px;
        }

        .progress-text{
            margin:0;
            font-size:16px;
        }

        table{
            width:100%;
            border-collapse:collapse;
            margin-top:10px;
            background:white;
        }

        th{
            background:#006400;
            color:white;
            padding:12px;
            font-size:16px;
            text-align:center;
        }

        td{
            padding:14px 12px;
            border:1px solid #ddd;
            text-align:center;
            font-size:16px;
        }

        .btn{
            background:#006400;
            color:white;
            border:none;
            padding:9px 22px;
            border-radius:7px;
            cursor:pointer;
            font-weight:bold;
            font-size:14px;
        }

        .btn:hover{
            background:#004d00;
        }

        .pending{
            color:orange;
            font-weight:bold;
        }

        .completed{
            color:green;
            font-weight:bold;
        }

        .empty{
            color:#777;
            font-style:italic;
        }

        form{
            margin:0;
        }

        @media(max-width:900px){
            .container{
                width:94%;
            }

            .stats{
                grid-template-columns:1fr;
            }

            .header{
                flex-direction:column;
                gap:15px;
            }

            table{
                font-size:14px;
            }
        }
    </style>
</head>

<body>

<div class="container">

    <div class="card header">
        <div>
            <h1>E-SUKARELAWAN</h1>
            <h3>Welcome, <%= name %></h3>
            <p>Student Volunteer Dashboard</p>
        </div>

        <a href="LogoutServlet" class="logout">Logout</a>
    </div>

    <div class="stats">
        <div class="stat-card">
            <h3>Total Volunteer Hours</h3>
            <h2><%= totalHours %> Hours</h2>
        </div>

        <div class="stat-card">
            <h3>Activities Joined</h3>
            <h2><%= totalActivities %></h2>
        </div>

        <div class="stat-card">
    <a href="MyMedalsServlet" style="text-decoration:none; color:inherit;">
        <h3>Medals Earned</h3>
        <h2><%= medalsEarned %></h2>
        <p style="color:#006400; font-weight:bold;">View Medal Details</p>
    </a>
</div>
    </div>

    <div class="card">
        <h2 class="section-title">Progress To Next Medal</h2>

        <div class="progress-bg">
            <div class="progress-bar"></div>
        </div>

        <p class="progress-text">
            <%= totalHours %> / <%= nextMedalHours %> Hours Completed
            (<%= progress %>%)
        </p>
    </div>

    <div class="card">
        <h2 class="section-title">Upcoming Activities</h2>

        <table>
            <tr>
                <th>Activity</th>
                <th>Description</th>
                <th>Date</th>
                <th>Hours Offered</th>
                <th>Organizer</th>
                <th>Action</th>
            </tr>

            <%
                if (activities != null && !activities.isEmpty()) {
                    for (ActivityBean a : activities) {

                        String status = null;

                        if (joinedStatus != null) {
                            status = joinedStatus.get(a.getActivityId());
                        }
            %>

            <tr>
                <td><%= a.getTitle() %></td>
                <td><%= a.getDescription() %></td>
                <td><%= a.getActivityDate() %></td>
                <td><%= a.getHourOffered() %></td>
                <td><%= a.getOrganizerName() %></td>
                <td>
                    <%
                        if (status == null) {
                    %>

                    <form action="JoinActivityServlet" method="post">
                        <input type="hidden" name="activityId" value="<%= a.getActivityId() %>">
                        <button type="submit" class="btn">Join</button>
                    </form>

                    <%
                        } else if ("Pending".equalsIgnoreCase(status)) {
                    %>

                    <span class="pending">Joined</span>

                    <%
                        } else if ("Completed".equalsIgnoreCase(status)) {
                    %>

                    <span class="completed">Completed</span>

                    <%
                        }
                    %>
                </td>
            </tr>

            <%
                    }
                } else {
            %>

            <tr>
                <td colspan="6" class="empty">No activities available.</td>
            </tr>

            <%
                }
            %>
        </table>
    </div>

    <div class="card">
        <h2 class="section-title">Participation History</h2>

        <table>
            <tr>
                <th>Activity</th>
                <th>Status</th>
                <th>Hours Earned</th>
            </tr>

            <%
                if (history != null && !history.isEmpty()) {
                    for (ParticipationBean p : history) {
            %>

            <tr>
                <td><%= p.getActivityTitle() %></td>
                <td><%= p.getStatus() %></td>
                <td><%= p.getHourEarned() %></td>
            </tr>

            <%
                    }
                } else {
            %>

            <tr>
                <td colspan="3" class="empty">No participation history yet.</td>
            </tr>

            <%
                }
            %>
        </table>
    </div>

</div>

</body>
</html>


