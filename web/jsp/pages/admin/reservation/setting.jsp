<%@ page import="model.ReservationSetting" %>
<%
    ReservationSetting setting = (ReservationSetting)request.getAttribute("setting");
%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title mb-0">Add Promotion</h3>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/reservation-setting/add">
                        <div class="form-group">
                            <label for="hourLimitReserving">Hour Limit Reserving</label>
                            <input 
                                type="number" 
                                min="0"
                                name="setting.hourLimitReserving" 
                                id="hourLimitReserving" 
                                class="form-control" 
                                value="<%= setting != null ? setting.getHourLimitReserving() : 0  %>"
                            />
                            
                        </div>
                        <div class="form-group">
                            <label for="hourLimitCanceling">Hour Limit Canceling</label>
                            <input 
                                type="number" 
                                min="0"
                                name="setting.hourLimitCanceling" 
                                id="hourLimitCanceling" 
                                class="form-control" 
                                value="<%= setting != null ? setting.getHourLimitCanceling() : 0  %>"
                            />
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