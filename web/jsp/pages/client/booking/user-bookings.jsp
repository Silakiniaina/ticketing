<%@ page import="java.util.List" %>
<%@ page import="model.Booking" %>
<%@ page import="model.Flight" %>
<%@ page import="model.Plane" %>
<%@ page import="model.City" %>
<% 
    List<Booking> bookings = (List<Booking>)request.getAttribute("bookings");
%>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-body">
                <table id="bookingTable" class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Booking ID</th>
                            <th>Booking Date</th>
                            <th>Flight ID</th>
                            <th>Plane</th>
                            <th>Departure</th>
                            <th>Arrival</th>
                            <th>From/To City</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (bookings != null && !bookings.isEmpty()) { 
                            for (Booking booking : bookings) {
                                Flight flight = booking.getFlight();
                        %>
                            <tr>
                                <td><%= booking.getId() %></td>
                                <td><%= booking.getBookingDatetime() != null ? booking.getBookingDatetime() : "N/A" %></td>
                                <td><%= flight.getId() %></td>
                                <td><%= flight.getPlane().getModel() != null ? flight.getPlane().getModel() : "N/A" %></td>
                                <td><%= flight.getDepartureDatetime() != null ? flight.getDepartureDatetime() : "N/A" %></td>
                                <td><%= flight.getArrivalDatetime() != null ? flight.getArrivalDatetime() : "N/A" %></td>
                                <td>
                                    <%= flight.getDepartureCity().getLabel() != null ? flight.getDepartureCity().getLabel() : "N/A" %>
                                    <span> <i class="fas fa-arrow-right"></i> </span>
                                    <%= flight.getArrivalCity().getLabel() != null ? flight.getArrivalCity().getLabel() : "N/A" %>
                                </td>
                                <td>
                                    <a href="<%= request.getContextPath() %>/flights/booking/details?booking.id=<%= booking.getId() %>" class="btn btn-info btn-sm">Details</a>
                                    <a href="<%= request.getContextPath() %>/flights/booking/cancel?booking.id=<%= booking.getId() %>" class="btn btn-danger btn-sm">Cancel</a>
                                </td>
                            </tr>
                        <% }} else { %>
                            <tr>
                                <td colspan="8" class="text-center">No bookings found</td>
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
        $("#bookingTable").DataTable({
            "responsive": true,
            "lengthChange": true,
            "autoWidth": false,
            "buttons": ["copy", "csv", "excel", "pdf", "print"],
            "columnDefs": [{ "orderable": false, "targets": [7] }]
        }).buttons().container().appendTo('#bookingTable_wrapper .col-md-6:eq(0)');
    });
</script>