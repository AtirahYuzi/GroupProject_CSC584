<%@ page import="java.util.List" %>
<%@ page import="com.esukarelawan.model.ActivityBean" %>
<%@ page import="com.esukarelawan.model.ParticipationBean" %>

<%
    ActivityBean activity =
            (ActivityBean) request.getAttribute("activity");

    List<ParticipationBean> participants =
            (List<ParticipationBean>) request.getAttribute("participants");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Activity Participation Details</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
            padding: 30px;
        }

        .card {
            background: white;
            border-radius: 14px;
            padding: 25px;
            margin-bottom: 20px;
            box-shadow: 0 5px 14px rgba(0,0,0,0.15);
        }

        h1, h2 {
            color: #006400;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #006400;
            color: white;
            padding: 12px;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        .completed {
            color: green;
            font-weight: bold;
        }

        .pending {
            color: orange;
            font-weight: bold;
        }

        .back {
            display: inline-block;
            background: #006400;
            color: white;
            padding: 12px 22px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>

<body>

<div class="card">
    <h1>Activity Details</h1>

    <p><b>Title:</b> <%= activity.getTitle() %></p>
    <p><b>Description:</b> <%= activity.getDescription() %></p>
    <p><b>Date:</b> <%= activity.getActivityDate() %></p>
    <p><b>Required Hours:</b> <%= activity.getHourOffered() %></p>
</div>

<div class="card">
    <h2>Students Completed</h2>

    <table>
        <tr>
            <th>Student Name</th>
            <th>Status</th>
            <th>Hours Earned</th>
        </tr>

        <%
            if (participants != null && !participants.isEmpty()) {
                for (ParticipationBean p : participants) {
                    if ("Completed".equalsIgnoreCase(p.getStatus())) {
        %>

        <tr>
            <td><%= p.getVolunteerName() %></td>
            <td class="completed"><%= p.getStatus() %></td>
            <td><%= p.getHourEarned() %></td>
        </tr>

        <%
                    }
                }
            } else {
        %>

        <tr>
            <td colspan="3">No completed students yet.</td>
        </tr>

        <%
            }
        %>
    </table>
</div>

<a href="ListActivityServlet" class="back">Back to Dashboard</a>

</body>
</html>