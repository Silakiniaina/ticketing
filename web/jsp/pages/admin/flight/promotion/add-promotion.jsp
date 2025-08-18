<%@ page import="java.util.List" %>
<%@ page import="model.TypeSeat" %>
<%@ page import="model.FlightPromotion" %>
<%
    List<TypeSeat> typeSeats = (List<TypeSeat>)request.getAttribute("typeSeats");
    List<FlightPromotion> promotions = (List<FlightPromotion>)request.getAttribute("promotions");
    Integer flightId = (Integer)request.getAttribute("flightId");
%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title mb-0">Add Promotion</h3>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/flight-promotions/add">
                        <% if(flightId != null){ %>
                            <input type="hidden" name="promotion.flightId" value="<%= flightId.intValue() %>">
                        <% } %>
                        <div class="form-group">
                            <label for="typeSeat">Type Seat <span class="text-danger">*</span></label>
                            <select name="promotion.typeSeatId" class="form-control" id="typeSeat" required>
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
                                min="0"
                                name="promotion.seatNumber" 
                                id="seatNumber" 
                                class="form-control" 
                            />
                            
                        </div>
                        <div class="form-group">
                            <label for="price">Price</label>
                            <input 
                                type="number" 
                                step="0.01"
                                min="0"
                                name="promotion.price" 
                                id="price" 
                                class="form-control" 
                            />
                            
                        </div>
                        <div class="form-group">
                            <label for="promotionDate">Promotion Date (Deadline)</label>
                            <input 
                                type="date" 
                                name="promotion.promotionDate" 
                                id="promotionDate" 
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
                    <table id="promotionTable" class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th>Flight</th>
                                <th>Type Seat</th>
                                <th>Seat Number</th>
                                <th>Price</th>
                                <th>Promotion Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (promotions !=null && !promotions.isEmpty()) { for (FlightPromotion promotion : promotions) {
                                %>
                                <tr>
                                    <td>
                                        <%= promotion.getFlight() !=null ?
                                            "Flight["+promotion.getFlight().getId()+"]" : "N/A" %>
                                    </td>
                                    <td>
                                        <%= promotion.getTypeSeat() !=null ?
                                            promotion.getTypeSeat().getLabel() : "N/A" %>
                                    </td>
                                    <td>
                                        <%= promotion.getSeatNumber() %>
                                    </td>
                                    <td>
                                        <%= promotion.getPrice() %>
                                    </td>
                                    <td>
                                        <%= promotion.getPromotionDate() %>
                                    </td>
                                </tr>
                                <% }} else { %>
                                <tr>
                                    <td colspan="8" class="text-center">No promotions found for flight number : <%= flightId != null ? flightId.intValue() : 0 %> </td>
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
        $("#promotionTable").DataTable({
            "responsive": true,
            "lengthChange": true,
            "autoWidth": false,
            "buttons": ["copy", "csv", "excel", "pdf", "print"],
            "columnDefs": [{ "orderable": false, "targets": [7] }]
        }).buttons().container().appendTo('#promotionTable_wrapper .col-md-6:eq(0)');
    });
</script>