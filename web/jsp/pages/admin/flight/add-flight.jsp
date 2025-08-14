<%@ page import="java.util.List" %>
<%@ page import="model.Plane" %>
<%@ page import="model.City" %>
<%@ page import="model.Flight" %>
<%@ page import="util.DateUtil" %>
<%@ page import="java.time.LocalDateTime" %>
<%
    Flight f = (Flight)request.getAttribute("flight");
    List<City> cities = (List<City>)request.getAttribute("cities");
    List<Plane> planes = (List<Plane>)request.getAttribute("planes");
%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <%
                        String title = "Add";
                        if(f != null){
                            title = "Update";
                        }
                    %>
                    <h3 class="card-title mb-0"><%= title %> Flight</h3>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/flights/add">
                        <% if(f != null){ %>
                            <input type="hidden" name="flightArg.id" value="<%= f.getId() %>">
                        <% } %>
                        <div class="form-group">
                            <label for="plane">Plane <span class="text-danger">*</span></label>
                            <select name="flightArg.planeId" class="form-control" id="plane" required>
                                <option value="">-- Select Plane --</option>
                                <% if (planes != null) {
                                    for (Plane plane : planes) {
                                        String selected = (f != null && f.getPlane().getId() == plane.getId()) ? "selected" : "";
                                %>
                                    <option value="<%= plane.getId() %>" <%= selected %>><%= plane.getModel() %> (<%= plane.getId() %>)</option>
                                <% }} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="departureCity">Departure City <span class="text-danger">*</span></label>
                            <select name="flightArg.departureCityId" class="form-control" id="departureCity" required>
                                <option value="">-- Select city --</option>
                                <% if (cities != null) {
                                    for (City city : cities) {
                                        String selected = (f != null && f.getDepartureCity().getId() == city.getId()) ? "selected" : "";
                                %>
                                    <option value="<%= city.getId() %>" <%= selected %>><%= city.getLabel() %> (<%= city.getId() %>)</option>
                                <% }} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="arrivalCity">Arrival City <span class="text-danger">*</span></label>
                            <select name="flightArg.arrivalCityId" class="form-control" id="arrivalCity" required>
                                <option value="">-- Select city --</option>
                                <% if (cities != null) {
                                    for (City city : cities) {
                                        String selected = (f != null && f.getArrivalCity().getId() == city.getId()) ? "selected" : "";
                                %>
                                    <option value="<%= city.getId() %>" <%= selected %>><%= city.getLabel() %> (<%= city.getId() %>)</option>
                                <% }} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="departureDatetime">Departure Datetime</label>
                            <%
                                String departureDatetimeValue = DateUtil.convertLocalDatetimeToWebDatetime(LocalDateTime.now());
                                if( f != null){
                                    departureDatetimeValue = DateUtil.convertTimestampToWebDatetime(f.getDepartureDatetime());
                                }
                            %>
                            <input 
                                type="datetime-local" 
                                name="flightArg.departureDatetime" 
                                id="departureDatetime" 
                                class="form-control" 
                                value="<%= departureDatetimeValue %>"
                            />
                            
                        </div>
                        <div class="form-group">
                            <label for="arrivalDatetime">Arrival Datetime</label>
                            <%
                                String arrivalDatetimeValue = DateUtil.convertLocalDatetimeToWebDatetime(LocalDateTime.now().plusHours(5));
                                if( f != null){
                                    arrivalDatetimeValue = DateUtil.convertTimestampToWebDatetime(f.getArrivalDatetime());
                                }
                            %>
                            <input 
                                type="datetime-local" 
                                name="flightArg.arrivalDatetime" 
                                id="arrivalDatetime" 
                                class="form-control" 
                                value="<%= arrivalDatetimeValue %>"
                            />
                            
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus mr-2"></i><%= title %>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>