<%@ page import="java.util.List" %>
<%@ page import="model.Flight" %>
<%@ page import="model.Plane" %>
<%@ page import="model.City" %>
<%@ page import="dto.FlightArg" %>
<% 
    List<Flight> flights = (List<Flight>)request.getAttribute("flights");
    List<Plane> planes = (List<Plane>)request.getAttribute("planes");
    List<City> cities = (List<City>)request.getAttribute("cities");
    FlightArg f = (FlightArg)request.getAttribute("flightArg");
    List<Flight> flightsFilter = (List<Flight>)request.getAttribute("flightsFilter");
    if(flightsFilter != null){
        flights = flightsFilter;
    }
%>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <div class="card-body" id="filters">
                    <form action="${pageContext.request.contextPath}/flights-search" method="get">
                        <div class="row">
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Plane</label>
                                    <select name="flightArg.planeId" class="form-control">
                                        <option value="">All Planes</option>
                                        <% if (planes != null) {
                                            for (Plane plane : planes) {
                                                String selected = (f != null && f.getPlaneId() == plane.getId()) ? "selected" : "";
                                        %>
                                            <option value="<%= plane.getId() %>" <%= selected %>><%= plane.getModel() %> (<%= plane.getId() %>)</option>
                                        <% }} %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Departure City</label>
                                    <select name="flightArg.departureCityId" class="form-control">
                                        <option value="">All Cities</option>
                                        <% if (cities != null) {
                                            for (City city : cities) {
                                                String selected = (f != null && f.getDepartureCityId() == city.getId()) ? "selected" : "";
                                        %>
                                            <option value="<%= city.getId() %>" <%= selected %>><%= city.getLabel() %> (<%= city.getId() %>)</option>
                                        <% }} %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Arrival City</label>
                                    <select name="flightArg.arrivalCityId" class="form-control">
                                        <option value="">All Cities</option>
                                        <% if (cities != null) {
                                            for (City city : cities) {
                                                String selected = (f != null && f.getDepartureCityId() == city.getId()) ? "selected" : "";
                                        %>
                                            <option value="<%= city.getId() %>" <%= selected %>><%= city.getLabel() %> (<%= city.getId() %>)</option>
                                        <% }} %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <%
                                        String departureDatetimeValue = "";
                                        if( f != null){
                                            departureDatetimeValue = f.getDepartureDatetime();
                                        }
                                    %>
                                    <label>Departure Date</label>
                                    <input type="date" name="flightArg.departureDate" class="form-control" value="<%= departureDatetimeValue %>">
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <%
                                        String arrivalDatetimeValue = "";
                                        if( f != null){
                                            arrivalDatetimeValue = f.getArrivalDatetime();
                                        }
                                    %>
                                    <label>Arrival Date</label>
                                    <input type="date" name="flightArg.arrivalDate" class="form-control" value="<%= arrivalDatetimeValue %>">
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>&nbsp;</label>
                                    <button type="submit" class="btn btn-primary btn-block">Search</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="card-body">
                <table id="flightTable" class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Plane</th>
                            <th>Departure</th>
                            <th>Arrival</th>
                            <th>City</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (flights !=null && !flights.isEmpty()) { for (Flight flight : flights) {
                            %>
                            <tr>
                                <td>
                                    <%= flight.getPlane().getModel() !=null ?
                                        flight.getPlane().getModel() : "N/A" %>
                                </td>
                                <td>
                                    <%= flight.getDepartureDatetime() !=null ?
                                        flight.getDepartureDatetime() : "N/A" %>
                                </td>
                                <td>
                                    <%= flight.getArrivalDatetime() !=null ? flight.getArrivalDatetime()
                                        : "N/A" %>
                                </td>
                                <td>
                                    <%= flight.getDepartureCity().getLabel() !=null ?
                                        flight.getDepartureCity().getLabel() : "N/A" %>
                                        <span> <i class="fas fa-arrow-right"></i> </span>
                                        <%= flight.getArrivalCity().getLabel() !=null ?
                                            flight.getArrivalCity().getLabel() : "N/A" %>
                                </td>
                                <td>
                                    <a href="<%= request.getContextPath() %>/flights/booking-date?flight.id=<%= flight.getId() %>"
                                        class="btn btn-info btn-sm">
                                        Reserve
                                    </a>
                                </td>
                            </tr>
                            <% }} else { %>
                                <tr>
                                    <td colspan="8" class="text-center">No flights found</td>
                                </tr>
                                <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#flightTable").DataTable({
            "responsive": true,
            "lengthChange": true,
            "autoWidth": false,
            "buttons": ["copy", "csv", "excel", "pdf", "print"],
            "columnDefs": [{ "orderable": false, "targets": [7] }]
        }).buttons().container().appendTo('#flightTable_wrapper .col-md-6:eq(0)');
    });
    function resetFilters() {
        document.getElementById("filterForm").reset();
        document.getElementById("filterForm").submit();
    }
</script>