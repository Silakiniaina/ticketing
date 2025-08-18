<%@ page import="java.util.List" %>
<%@ page import="model.Flight" %>
<%
    Flight flight = (Flight)request.getAttribute("flight");
%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title mb-0">Add Booking</h3>
                </div>
                <div class="card-body">
                    <form method="get" action="${pageContext.request.contextPath}/flights/booking">
                        <% if(flight != null){ %>
                            <input type="hidden" name="booking.flightId" value="<%= flight.getId() %>">
                        <% } %>
                        <div class="form-group">
                            <label for="bookingDate">Date booking</label>
                            <input 
                                type="date" 
                                name="booking.bookingDate" 
                                id="bookingDate" 
                                class="form-control" 
                            />
                            
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus mr-2"></i>Add booking
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>