<!DOCTYPE html>
<html lang="en">
<%@ page import="java.util.HashMap" %>
<%@ page import="mg.dash.mvc.helper.ErrorHelper" %>
<%
    HashMap<String, String> errors = (HashMap<String, String>) request.getAttribute("validationErrors");
%>
<head>
  <%@ include file="/WEB-INF/views/partials/head.jsp" %>
</head>

<body class="hold-transition login-page">
  <!-- ------------------------------ Login box ------------------------------ -->
  <div class="login-box">
    <!-- ----------------------------- Login logo ------------------------------ -->
    <div class="login-logo">
      <a href="/"><b>Ticketing</b>-Application</a>
    </div>
    <!-- --------------------------- End login logo ---------------------------- -->

    <!-- ------------------------------ Form card ------------------------------ -->
    <div class="card">
      <div class="card-body login-card-body">
        <p class="login-box-msg">Sign in to start your session</p>

        <% if (request.getAttribute("error") !=null) { %>
          <!-- ------------------------------ Error box ------------------------------ -->
          <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= request.getAttribute("error") %>
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
          </div>
          <!-- ---------------------------- End error box ---------------------------- -->
          <% } %>
            <!-- -------------------------------- Form --------------------------------- -->
            <form action="<%= request.getContextPath() %>/auth" method="post">
              <div class="input-group">
                <input 
                  name="user.email" 
                  value="<%= ErrorHelper.getOldValue(request, "user.email") %>"
                  type="email" 
                  class="form-control <%= ErrorHelper.getError(errors, "email") != null ? "error" : "" %>" 
                  placeholder="Email"
                />
                <div class="input-group-append">
                  <div class="input-group-text">
                    <span class="fas fa-envelope"></span>
                  </div>
                </div>
              </div>
              <% if (ErrorHelper.getError(errors, "email") != null) { %>
                  <p class="text-danger"><%= ErrorHelper.getError(errors, "email") %></p>
              <% } %>
              <div class="input-group mt-3">
                <input 
                  name="user.password" 
                  type="password" 
                  class="form-control <%= ErrorHelper.getError(errors, "password") != null ? "error" : "" %>" 
                  placeholder="Password"
                  value="<%= ErrorHelper.getOldValue(request, "user.password") %>"
                />
                <div class="input-group-append">
                  <div class="input-group-text">
                    <span class="fas fa-lock"></span>
                  </div>
                </div>
              </div>
              <% if (ErrorHelper.getError(errors, "password") != null) { %>
                  <p class="text-danger"><%= ErrorHelper.getError(errors, "password") %></p>
              <% } %>
              <div class="row mt-3">
                <!-- ----------------------------- Submit col ------------------------------ -->
                <div class="col">
                  <button type="submit" class="btn btn-primary btn-block">Sign In</button>
                </div>
                <!-- --------------------------- End submit col ---------------------------- -->
              </div>
            </form>
            <!-- ------------------------------ End form ------------------------------- -->
      </div>
      <!-- ------------------------- End login card body ------------------------- -->
    </div>
    <!-- --------------------------- End login card ---------------------------- -->
  </div>
<!-- --------------------------- End login box ---------------------------- -->

  <%-- Include scripts --%>
    <%@ include file="/WEB-INF/views/partials/scripts.jsp" %>
</body>

</html>
