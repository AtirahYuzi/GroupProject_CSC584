<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>E-Sukarelawan | Register</title>
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #1e5631 0%, #a4de02 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .register-container {
            background: rgba(255,255,255,0.9);
            padding: 2.5rem 3rem;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
            width: 450px;
            text-align: center;
            margin: 20px;
        }
        h2 { color: #1e5631; margin-bottom: 2rem; font-size: 2rem; text-transform: uppercase; }
        .input-group { text-align: left; margin-bottom: 1rem; }
        .input-group label { display: block; margin-bottom: 5px; font-size: 0.85rem; font-weight: 600; color: #444; }
        .input-group input, .input-group select {
            width: 100%; padding: 12px; border: 2px solid #eee;
            border-radius: 10px; box-sizing: border-box; font-size: 0.95rem;
        }
        .btn-register {
            width: 100%; padding: 14px; background-color: green;
            color: white; border: none; border-radius: 10px;
            cursor: pointer; font-size: 1.1rem; font-weight: bold; margin-top: 15px;
        }
        .btn-register:hover { background-color: #77bb02; }
        .footer-text { margin-top: 1.5rem; font-size: 0.9rem; color: #666; }
        .footer-text a { color: #008000; text-decoration: none; font-weight: bold; }
        .error-msg { color: red; font-size: 0.9rem; margin-bottom: 1rem; font-weight: bold; }
    </style>
</head>
<body>
    <div class="register-container">
        <form action="RegisterServlet" method="POST">
            <h2>Register Account</h2>

            <% if (request.getParameter("error") != null) { %>
                <p class="error-msg">Registration failed! Username might already be taken.</p>
            <% } %>

            <div class="input-group">
                <label>Full Name</label>
                <input type="text" name="full_name" placeholder="Enter full name" required>
            </div>
            <div class="input-group">
                <label>Username</label>
                <input type="text" name="username" placeholder="Choose a username" required>
            </div>
            <div class="input-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Choose a password" required>
            </div>
            <div class="input-group">
                <label>Email Address</label>
                <input type="email" name="email" placeholder="name@example.com" required>
            </div>
            <div class="input-group">
                <label>Phone Number</label>
                <input type="text" name="phone_number" placeholder="e.g. 0123456789" required>
            </div>
            <div class="input-group">
                <label>Who are you?</label>
                <select name="role" required>
                    <option value="" disabled selected>Select your role</option>
                    <option value="volunteer">I am a Student (Volunteer)</option>
                    <option value="organizer">I am an NGO / Club Leader</option>
                </select>
            </div>

            <button type="submit" class="btn-register">Create Account</button>
            <p class="footer-text">Already have an account? <a href="index.html">Login here</a></p>
        </form>
    </div>
</body>
</html>