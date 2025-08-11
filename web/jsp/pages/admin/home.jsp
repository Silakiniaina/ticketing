<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<div class="container">
    <div class="row d-flex justify-content-center align-items-center">
        <div class="col-12 text-center">
            <h1>Welcome to Ticketing Application's Admin role : <%= ((User)request.getSession().getAttribute("user")).getLastName() %> </h1>
            <p class="lead">Manage plane, reservation and flight efficiently.</p>
        </div>
    </div>
</div>