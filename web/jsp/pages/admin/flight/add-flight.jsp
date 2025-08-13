<%@ page import="java.util.List" %>
<%@ page import="model.Plane" %>
<%@ page import="model.City" %>
<%
    List<City> cities = (List<City>)request.getAttribute("cities");
    List<Plane> planes = (List<Plane>)request.getAttribute("planes");
%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title mb-0">Add Flight</h3>
                </div>
                <div class="card-body">
                    <form method="post" action="<%= request.getContextPath() %>/flights">
                        <div class="form-group">
                            <label for="plane">Plane <span class="text-danger">*</span></label>
                            <select name="flightArg.planeId" class="form-control" id="plane" required>
                                <option value="">-- Select Plane --</option>
                                <% if (planes != null) {
                                    for (Plane plane : planes) {
                                %>
                                    <option value="<%= plane.getId() %>"><%= plane.getModel() %> (<%= plane.getId() %>)</option>
                                <% }} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="departureCity">Departure City <span class="text-danger">*</span></label>
                            <select name="flightArg.departureCityId" class="form-control" id="departureCity" required>
                                <option value="">-- Select city --</option>
                                <% if (cities != null) {
                                    for (City city : cities) {
                                %>
                                    <option value="<%= city.getId() %>"><%= city.getLabel() %> (<%= city.getId() %>)</option>
                                <% }} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="arrivalCity">Arrival City <span class="text-danger">*</span></label>
                            <select name="flightArg.arrivalCityId" class="form-control" id="arrivalCity" required>
                                <option value="">-- Select city --</option>
                                <% if (cities != null) {
                                    for (City city : cities) {
                                %>
                                    <option value="<%= city.getId() %>"><%= city.getLabel() %> (<%= city.getId() %>)</option>
                                <% }} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="departureDatetime">Departure Datetime</label>
                            <input type="datetime-local" name="flightArg.departureDatetime" id="departureDatetime" class="form-control" >
                            
                        </div>
                        <div class="form-group">
                            <label for="arrivalDatetime">Arrival Datetime</label>
                            <input type="datetime-local" name="flightArg.arrivalDatetime" id="arrivalDatetime" class="form-control" >
                            
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus mr-2"></i>Add
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>