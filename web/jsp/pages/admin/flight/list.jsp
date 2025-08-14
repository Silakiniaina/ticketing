<%@ page import="java.util.List" %>
<%@ page import="model.Flight" %>
<%
    List<Flight> flights = (List<Flight>)request.getAttribute("flights");
%>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <h3 class="card-title">Flight list</h3>
                <div class="card-tools">
                    <button type="button" class="btn btn-tool" data-card-widget="collapse">
                        <i class="fas fa-filter"></i> Filters
                    </button>
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
                        <% if (flights != null && !flights.isEmpty()) {
                            for (Flight flight : flights) { %>
                                <tr>
                                    <td><%= flight.getPlane().getModel() != null ? flight.getPlane().getModel() : "N/A" %></td>
                                    <td><%= flight.getDepartureDatetime() != null ? flight.getDepartureDatetime() : "N/A" %></td>
                                    <td><%= flight.getArrivalDatetime() != null ? flight.getArrivalDatetime() : "N/A" %></td>
                                    <td>
                                        <%= flight.getDepartureCity().getLabel() != null ? flight.getDepartureCity().getLabel() : "N/A" %>
                                        <span> <i class="fas fa-arrow-right"></i> </span>
                                        <%= flight.getArrivalCity().getLabel() != null ? flight.getArrivalCity().getLabel() : "N/A" %>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/flights/update?updateArg.id=<%= flight.getId() %>"
                                           class="btn btn-info btn-sm">
                                            <i class="fas fa-pen"></i>
                                        </a>
                                        <a href="#"
                                           class="btn btn-danger btn-sm">
                                            <i class="fas fa-trash"></i>
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