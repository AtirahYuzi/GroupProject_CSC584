<%@ page import="java.util.List" %>
<%@ page import="com.esukarelawan.model.MedalBean" %>

<%
    List<MedalBean> medals =
            (List<MedalBean>) request.getAttribute("medals");
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Medals</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg,#2f7d32,#8bc34a);
            margin: 0;
            padding: 30px;
        }

        .container {
            width: 70%;
            margin: auto;
        }

        .card {
            background: white;
            border-radius: 14px;
            padding: 25px;
            margin-bottom: 18px;
            box-shadow: 0 5px 14px rgba(0,0,0,0.20);
        }

        h1 {
            color: #006400;
            text-align: center;
        }

        .medal-name {
            color: #006400;
            font-size: 24px;
            font-weight: bold;
        }

        .earned {
            color: green;
            font-weight: bold;
        }

        .not-earned {
            color: red;
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

<div class="container">

    <div class="card">
        <h1>My Medals</h1>
    </div>

    <%
        if (medals != null && !medals.isEmpty()) {
            for (MedalBean m : medals) {
    %>

    <div class="card">
        <div class="medal-name">
            <%= m.getMedalName() %>
        </div>

        <p><%= m.getDescription() %></p>
        <p>Required Hours: <%= m.getRequiredHours() %></p>

        <%
            if (m.getDateEarned() != null) {
        %>
            <p class="earned">Earned on: <%= m.getDateEarned() %></p>
        <%
            } else {
        %>
            <p class="not-earned">Not Earned Yet</p>
        <%
            }
        %>
    </div>

    <%
            }
        }
    %>

    <a href="StudentDashboardServlet" class="back">Back to Dashboard</a>

</div>

</body>
</html>