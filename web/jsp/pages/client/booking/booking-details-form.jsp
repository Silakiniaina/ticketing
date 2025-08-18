<%@ page import="java.util.List" %>
<%@ page import="model.TypeSeat" %>
<%@ page import="model.Booking" %>
<%@ page import="model.BookingPassenger" %>
<%
    List<TypeSeat> typeSeats = (List<TypeSeat>)request.getAttribute("typeSeats");
    List<BookingPassenger> bookingPassengers = (List<BookingPassenger>)request.getAttribute("bookingPassengers");
    Booking booking = (Booking)request.getAttribute("booking");
%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title mb-0">Add Booking details</h3>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/flights/booking/details">
                        <% if(booking != null){ %>
                            <input type="hidden" name="booking.bookingId" value="<%= booking.getId() %>">
                        <% } %>
                        <div class="form-group">
                            <label for="typeSeat">Type Seat <span class="text-danger">*</span></label>
                            <select name="booking.typeSeatId" class="form-control" id="typeSeat" required>
                                <option value="">-- Select Type Seat --</option>
                                <% if (typeSeats != null) {
                                    for (TypeSeat typeSeat : typeSeats) {
                                %>
                                    <option value="<%= typeSeat.getId() %>"><%= typeSeat.getLabel() %></option>
                                <% }} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="seatNumber">Seat Number</label>
                            <input 
                                type="number" 
                                min="1"
                                name="booking.seatNumber" 
                                id="seatNumber" 
                                class="form-control" 
                            />
                            
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus mr-2"></i>Add
                            </button>
                        </div>
                    </form>
                </div>
                <div class="card-body">
                    <table id="bookingTable" class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th>Booking</th>
                                <th>Type Seat</th>
                                <th>Price</th>
                                <th>Promotion</th>
                                <th>Real Price</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (bookingPassengers !=null && !bookingPassengers.isEmpty()) { for (BookingPassenger bookingPassenger : bookingPassengers) {
                                %>
                                <tr>
                                    <td>
                                        <%= bookingPassenger.getBooking() !=null ?
                                            "Booking["+bookingPassenger.getBooking().getId()+"]" : "N/A" %>
                                    </td>
                                    <td>
                                        <%= bookingPassenger.getTypeSeat() !=null ?
                                            bookingPassenger.getTypeSeat().getLabel() : "N/A" %>
                                    </td>
                                    <td>
                                        <%= bookingPassenger.getPrice() %>
                                    </td>
                                    <td>
                                        <%= bookingPassenger.getPromotion() %>
                                    </td>
                                    <td>
                                        <%= bookingPassenger.getRealPrice() %>
                                    </td>
                                    <td>
                                        <% if(bookingPassenger.getPassportFilePath() != null) { out.println(bookingPassenger.getPassportFilePath()); } else { %>
                                            <a href="<%= request.getContextPath() %>/booking/passport-upload?booking.id=<%= booking.getId() %>" class="btn btn-danger btn-sm">Upload Passport</a>
                                        <% } %>
                                    </td>
                                </tr>
                                <% }} else { %>
                                <tr>
                                    <td colspan="8" class="text-center">No booking passengers found for booking number : <%= booking.getId() %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
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